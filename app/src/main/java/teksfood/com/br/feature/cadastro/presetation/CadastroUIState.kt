package teksfood.com.br.feature.cadastro.presentation

data class CadastroUiState(
    val nomeLojaText: String = "",
    val emailText: String = "",
    val senhaText: String = "",
    val confirmarSenhaText: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)