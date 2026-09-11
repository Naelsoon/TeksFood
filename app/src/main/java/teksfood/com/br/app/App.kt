package teksfood.com.br.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import teksfood.com.br.core.designsystem.theme.TeksFoodTheme

@Composable
fun App() {
    TeksFoodTheme {
        val navController = rememberNavController()

        AppNavHost(
            navController = navController
        )
    }
}