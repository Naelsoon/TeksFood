package teksfood.com.br.feature.esquecisenha.presentation

data class EsqueciSenhaUiState(
    val emailText: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccessMessageVisible: Boolean = false
)