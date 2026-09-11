package teksfood.com.br.feature.esquecisenha.presentation

sealed interface EsqueciSenhaEvent {
    data class OnEmailChanged(val email: String) : EsqueciSenhaEvent
    data object OnEnviarClick : EsqueciSenhaEvent
}