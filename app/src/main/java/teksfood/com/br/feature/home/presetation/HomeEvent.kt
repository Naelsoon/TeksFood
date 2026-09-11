package teksfood.com.br.feature.home.presentation

sealed interface HomeEvent {
    data class OnToggleLojaAberta(val isAberta: Boolean) : HomeEvent
    data object OnDeslogarClick : HomeEvent
}