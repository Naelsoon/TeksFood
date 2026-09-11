package teksfood.com.br.feature.login.presentation

// Sealed Interface é só uma "lista fechada" de coisas que o usuário pode fazer
sealed interface LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent
    data class OnSenhaChanged(val senha: String) : LoginEvent
    data object OnLoginClick : LoginEvent
}