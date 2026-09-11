package teksfood.com.br.feature.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import teksfood.com.br.core.designsystem.theme.TaskFoodOrange
import teksfood.com.br.core.designsystem.theme.TaskFoodPastel
import teksfood.com.br.core.designsystem.theme.TextDark
import teksfood.com.br.feature.home.presentation.HomeEvent
import teksfood.com.br.feature.home.presentation.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onDeslogarSucesso: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.nomeLoja, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(HomeEvent.OnDeslogarClick)
                        onDeslogarSucesso()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Sair",
                            tint = Color.Red
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TaskFoodPastel)
            )
        },
        containerColor = TaskFoodPastel
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Status da Loja (Aberta/Fechada)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Status do Estabelecimento", fontSize = 12.sp, color = Color.Gray)
                        Text(
                            text = if (uiState.isLojaAberta) "LOJA ABERTA" else "LOJA FECHADA",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (uiState.isLojaAberta) Color(0xFF2E7D32) else Color.Red
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Resumo de Métricas
            Text("Resumo de Hoje", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricCard("Faturamento", uiState.faturamentoHoje, TaskFoodOrange, Modifier.weight(1f))
                MetricCard("Ativos", "${uiState.pedidosAtivosCount}", Color(0xFF1976D2), Modifier.weight(1f))
                MetricCard("Entregues", "${uiState.pedidosConcluidosCount}", Color(0xFF388E3C), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Ações Rápidas
            Text("Atalhos da Operação", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton("Novo Pedido", Icons.Default.Add, Modifier.weight(1f)) {}
                ActionButton("Cardápio", Icons.Default.RestaurantMenu, Modifier.weight(1f)) {}
                ActionButton("Tarefas", Icons.Default.Task, Modifier.weight(1f)) {}
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, accentColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontSize = 11.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = accentColor)
        }
    }
}

@Composable
fun ActionButton(label: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.height(75.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = label, tint = TaskFoodOrange)
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontSize = 12.sp, color = TextDark, fontWeight = FontWeight.Medium)
        }
    }
}