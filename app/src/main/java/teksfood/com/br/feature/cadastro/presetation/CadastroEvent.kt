package teksfood.com.br.feature.cadastro.presentation

sealed interface CadastroEvent {
    data class OnNomeLojaChanged(val nome: String) : CadastroEvent
    data class OnEmailChanged(val email: String) : CadastroEvent
    data class OnSenhaChanged(val senha: String) : CadastroEvent
    data class OnConfirmarSenhaChanged(val confirmarSenha: String) : CadastroEvent
    data object OnCadastrarClick : CadastroEvent
}