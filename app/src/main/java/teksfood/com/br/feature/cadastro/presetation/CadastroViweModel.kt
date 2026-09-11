package teksfood.com.br.feature.cadastro.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import teksfood.com.br.core.data.local.UserPreferencesRepository

class CadastroViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserPreferencesRepository(application)

    private val _uiState = MutableStateFlow(CadastroUiState())
    val uiState: StateFlow<CadastroUiState> = _uiState.asStateFlow()

    fun onEvent(event: CadastroEvent) {
        when (event) {
            is CadastroEvent.OnNomeLojaChanged -> _uiState.update { it.copy(nomeLojaText = event.nome, errorMessage = null) }
            is CadastroEvent.OnEmailChanged -> _uiState.update { it.copy(emailText = event.email, errorMessage = null) }
            is CadastroEvent.OnSenhaChanged -> _uiState.update { it.copy(senhaText = event.senha, errorMessage = null) }
            is CadastroEvent.OnConfirmarSenhaChanged -> _uiState.update { it.copy(confirmarSenhaText = event.confirmarSenha, errorMessage = null) }
            CadastroEvent.OnCadastrarClick -> cadastrarLoja()
        }
    }

    private fun cadastrarLoja() {
        val state = _uiState.value

        if (state.nomeLojaText.isBlank() || state.emailText.isBlank() || state.senhaText.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Preencha todos os campos!") }
            return
        }

        if (state.senhaText != state.confirmarSenhaText) {
            _uiState.update { it.copy(errorMessage = "As senhas não coincidem!") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Salva os dados no DataStore do celular
            repository.salvarUsuario(
                nomeLoja = state.nomeLojaText,
                email = state.emailText,
                senha = state.senhaText
            )

            _uiState.update { it.copy(isLoading = false, isSuccess = true) }
        }
    }
}