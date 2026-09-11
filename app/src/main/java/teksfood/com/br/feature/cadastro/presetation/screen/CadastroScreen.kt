package teksfood.com.br.feature.cadastro.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import teksfood.com.br.core.designsystem.theme.TaskFoodOrange
import teksfood.com.br.core.designsystem.theme.TaskFoodPastel
import teksfood.com.br.core.designsystem.theme.TextDark
import teksfood.com.br.feature.cadastro.presentation.CadastroEvent
import teksfood.com.br.feature.cadastro.presentation.CadastroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(
    onVoltarClick: () -> Unit,
    onCadastroSucesso: () -> Unit,
    viewModel: CadastroViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Controle de visibilidade das senhas
    var isSenhaVisible by remember { mutableStateOf(false) }
    var isConfirmarSenhaVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onCadastroSucesso()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastro", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVoltarClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TaskFoodPastel)
            )
        },
        containerColor = TaskFoodPastel
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Crie sua conta parceira",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TaskFoodOrange
                )

                Spacer(modifier = Modifier.height(16.dp))

                uiState.errorMessage?.let { errorMsg ->
                    Text(
                        text = errorMsg,
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                // Nome da Loja
                Text("Nome da Loja", color = TextDark, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
                OutlinedTextField(
                    value = uiState.nomeLojaText,
                    onValueChange = { viewModel.onEvent(CadastroEvent.OnNomeLojaChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Email
                Text("Email", color = TextDark, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
                OutlinedTextField(
                    value = uiState.emailText,
                    onValueChange = { viewModel.onEvent(CadastroEvent.OnEmailChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Senha
                Text("Senha", color = TextDark, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
                OutlinedTextField(
                    value = uiState.senhaText,
                    onValueChange = { viewModel.onEvent(CadastroEvent.OnSenhaChanged(it)) },
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

                Spacer(modifier = Modifier.height(12.dp))

                // Confirmar Senha
                Text("Confirmar Senha", color = TextDark, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
                OutlinedTextField(
                    value = uiState.confirmarSenhaText,
                    onValueChange = { viewModel.onEvent(CadastroEvent.OnConfirmarSenhaChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (isConfirmarSenhaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isConfirmarSenhaVisible = !isConfirmarSenhaVisible }) {
                            Icon(
                                imageVector = if (isConfirmarSenhaVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (isConfirmarSenhaVisible) "Ocultar senha" else "Exibir senha",
                                tint = TaskFoodOrange
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.onEvent(CadastroEvent.OnCadastrarClick) },
                    enabled = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TaskFoodOrange)
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Finalizar Cadastro", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}