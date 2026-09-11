package teksfood.com.br.app

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Cadastro : Screen("cadastro")
    data object EsqueciSenha : Screen("esqueci_senha")
    data object Home : Screen("home")
}