package teksfood.com.br.feature.login.presentation

// A "Foto" da tela em qualquer segundo
data class LoginUiState(
    val emailText: String = "",
    val senhaText: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)