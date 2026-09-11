package teksfood.com.br.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import teksfood.com.br.feature.cadastro.presentation.screen.CadastroScreen
import teksfood.com.br.feature.esquecisenha.presentation.screen.EsqueciSenhaScreen
import teksfood.com.br.feature.home.presentation.screen.HomeScreen
import teksfood.com.br.feature.login.presentation.screen.LoginScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // 1. Tela de Login
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToCadastro = {
                    navController.navigate(Screen.Cadastro.route)
                },
                onNavigateToEsqueciSenha = {
                    navController.navigate(Screen.EsqueciSenha.route)
                }
            )
        }

        // 2. Tela de Cadastro
        composable(Screen.Cadastro.route) {
            CadastroScreen(
                onVoltarClick = {
                    navController.popBackStack()
                },
                onCadastroSucesso = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // 3. Tela de Esqueci a Senha
        composable(Screen.EsqueciSenha.route) {
            EsqueciSenhaScreen(
                onVoltarClick = {
                    navController.popBackStack()
                }
            )
        }

        // 4. Tela Home da Loja
        composable(Screen.Home.route) {
            HomeScreen(
                onDeslogarSucesso = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}