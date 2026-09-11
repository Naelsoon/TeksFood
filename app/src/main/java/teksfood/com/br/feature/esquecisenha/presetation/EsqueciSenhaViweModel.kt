package teksfood.com.br.feature.esquecisenha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EsqueciSenhaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EsqueciSenhaUiState())
    val uiState: StateFlow<EsqueciSenhaUiState> = _uiState.asStateFlow()

    fun onEvent(event: EsqueciSenhaEvent) {
        when (event) {
            is EsqueciSenhaEvent.OnEmailChanged -> {
                _uiState.update { it.copy(emailText = event.email, errorMessage = null) }
            }
            EsqueciSenhaEvent.OnEnviarClick -> {
                enviarLinkRecuperacao()
            }
        }
    }

    private fun enviarLinkRecuperacao() {
        val email = _uiState.value.emailText

        if (email.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Digite o seu e-mail cadastrado!") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Simula o envio do e-mail de recuperação
            delay(2000)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isSuccessMessageVisible = true
                )
            }
        }
    }
}