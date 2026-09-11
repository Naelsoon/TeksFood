package teksfood.com.br.feature.home.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import teksfood.com.br.core.data.local.UserPreferencesRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserPreferencesRepository(application)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        carregarNomeLoja()
    }

    private fun carregarNomeLoja() {
        viewModelScope.launch {
            repository.nomeLoja.collect { nome ->
                _uiState.update { it.copy(nomeLoja = if (nome.isBlank()) "Sua Loja" else nome) }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnToggleLojaAberta -> {
                _uiState.update { it.copy(isLojaAberta = event.isAberta) }
            }
            HomeEvent.OnDeslogarClick -> {
                viewModelScope.launch {
                    repository.deslogar()
                }
            }
        }
    }
}