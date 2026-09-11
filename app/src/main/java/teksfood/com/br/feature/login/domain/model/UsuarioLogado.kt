package teksfood.com.br.feature.login.domain.model

// O "RG" do usuário: só guarda as informações do usuário que logou
data class UsuarioLogado(
    val id: String,
    val nome: String,
    val email: String,
    val token: String
)