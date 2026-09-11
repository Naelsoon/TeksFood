package teksfood.com.br.feature.esquecisenha.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import teksfood.com.br.core.designsystem.theme.TaskFoodOrange
import teksfood.com.br.core.designsystem.theme.TaskFoodPastel
import teksfood.com.br.core.designsystem.theme.TextDark
import teksfood.com.br.feature.esquecisenha.presentation.EsqueciSenhaEvent
import teksfood.com.br.feature.esquecisenha.presentation.EsqueciSenhaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EsqueciSenhaScreen(
    onVoltarClick: () -> Unit,
    viewModel: EsqueciSenhaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recuperar Senha", fontWeight = FontWeight.Bold) },
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
                    text = "Esqueceu sua senha?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TaskFoodOrange
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Digite seu e-mail e enviaremos um link para você redefinir sua senha.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (uiState.isSuccessMessageVisible) {
                    Text(
                        text = "Link enviado! Verifique sua caixa de entrada.",
                        color = Color(0xFF2E7D32),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                uiState.errorMessage?.let { errorMsg ->
                    Text(
                        text = errorMsg,
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                Text("Email", color = TextDark, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
                OutlinedTextField(
                    value = uiState.emailText,
                    onValueChange = { viewModel.onEvent(EsqueciSenhaEvent.OnEmailChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.onEvent(EsqueciSenhaEvent.OnEnviarClick) },
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
                        Text("Enviar Link", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}