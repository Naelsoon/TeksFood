package teksfood.com.br.feature.login.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import teksfood.com.br.R
import teksfood.com.br.core.designsystem.theme.TaskFoodOrange
import teksfood.com.br.core.designsystem.theme.TaskFoodPastel
import teksfood.com.br.core.designsystem.theme.TextDark
import teksfood.com.br.feature.login.presentation.LoginEvent
import teksfood.com.br.feature.login.presentation.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToCadastro: () -> Unit,
    onNavigateToEsqueciSenha: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateToHome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TaskFoodPastel)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoginHeader()

            Spacer(modifier = Modifier.height(32.dp))

            uiState.errorMessage?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            LoginInputs(
                email = uiState.emailText,
                onEmailChange = { viewModel.onEvent(LoginEvent.OnEmailChanged(it)) },
                senha = uiState.senhaText,
                onSenhaChange = { viewModel.onEvent(LoginEvent.OnSenhaChanged(it)) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            LoginActions(
                isLoading = uiState.isLoading,
                onLoginClick = { viewModel.onEvent(LoginEvent.OnLoginClick) },
                onEsqueciSenhaClick = onNavigateToEsqueciSenha,
                onCadastroClick = onNavigateToCadastro
            )
        }
    }
}

@Composable
fun LoginHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ele),
            contentDescription = "Logo Elefante TaskFood",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "TaskFood", color = TaskFoodOrange, fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Acesse e gerencie a sua loja de forma rápida", fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun LoginInputs(
    email: String, onEmailChange: (String) -> Unit,
    senha: String, onSenhaChange: (String) -> Unit
) {
    var isSenhaVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Email", color = TextDark, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Senha", color = TextDark, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        OutlinedTextField(
            value = senha,
            onValueChange = onSenhaChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isSenhaVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isSenhaVisible = !isSenhaVisible }) {
                    Icon(
                        imageVector = if (isSenhaVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isSenhaVisible) "Ocultar senha" else "Exibir senha",
                        tint = TaskFoodOrange
                    )
                }
            }
        )
    }
}

@Composable
fun LoginActions(
    isLoading: Boolean,
    onLoginClick: () -> Unit,
    onEsqueciSenhaClick: () -> Unit,
    onCadastroClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(
            onClick = onEsqueciSenhaClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Eita, esqueci a senha", color = TaskFoodOrange, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onLoginClick,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TaskFoodOrange)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Entrar", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onCadastroClick) {
            Text("Cadastrar minha loja", color = TaskFoodOrange)
        }
    }
}