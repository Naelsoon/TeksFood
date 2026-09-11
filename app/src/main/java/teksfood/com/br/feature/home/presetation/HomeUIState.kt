package teksfood.com.br.feature.home.presentation

data class HomeUiState(
    val nomeLoja: String = "Carregando...",
    val isLojaAberta: Boolean = true,
    val faturamentoHoje: String = "R$ 1.250,00",
    val pedidosAtivosCount: Int = 5,
    val pedidosConcluidosCount: Int = 28
)