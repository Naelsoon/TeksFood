package teksfood.com.br.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import teksfood.com.br.feature.cadastro.presentation.screen.CadastroScreen
import teksfood.com.br.feature.home.presentation.screen.HomeScreen
import teksfood.com.br.feature.login.presentation.screen.LoginScreen

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Cadastro : Screen("cadastro")
    data object Home : Screen("home")
}

@Composable
fun AppNavigation(
    startDestination: String = Screen.Login.route
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // --- TELA DE LOGIN ---
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        // Limpa a tela de Login da pilha para o botão 'Voltar' não retornar para o Login
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToCadastro = {
                    navController.navigate(Screen.Cadastro.route)
                },
                onNavigateToEsqueciSenha = {
                    // Reservado para futura tela de recuperação de senha
                }
            )
        }

        // --- TELA DE CADASTRO ---
        composable(Screen.Cadastro.route) {
            CadastroScreen(
                onVoltarClick = {
                    navController.popBackStack()
                },
                onCadastroSucesso = {
                    navController.navigate(Screen.Home.route) {
                        // Limpa Login e Cadastro da pilha após cadastrar
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // --- TELA DA HOME ---
        composable(Screen.Home.route) {
            HomeScreen(
                onDeslogarSucesso = {
                    navController.navigate(Screen.Login.route) {
                        // Limpa todo o histórico de telas e volta para o Login
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}