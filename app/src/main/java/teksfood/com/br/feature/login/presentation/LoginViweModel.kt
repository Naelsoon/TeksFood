package teksfood.com.br.feature.login.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import teksfood.com.br.core.data.local.UserPreferencesRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserPreferencesRepository(application)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> _uiState.update { it.copy(emailText = event.email, errorMessage = null) }
            is LoginEvent.OnSenhaChanged -> _uiState.update { it.copy(senhaText = event.senha, errorMessage = null) }
            LoginEvent.OnLoginClick -> autenticar()
        }
    }

    private fun autenticar() {
        val email = _uiState.value.emailText
        val senha = _uiState.value.senhaText

        if (email.isBlank() || senha.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Informe e-mail e senha para entrar!") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Valida se as credenciais correspondem às salvas no DataStore
            val isLoginValido = repository.validarLogin(email, senha)

            if (isLoginValido) {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "E-mail ou senha incorretos!"
                    )
                }
            }
        }
    }
}