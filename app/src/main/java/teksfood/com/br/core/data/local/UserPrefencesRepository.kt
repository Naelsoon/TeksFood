package teksfood.com.br.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        private val NOME_LOJA_KEY = stringPreferencesKey("nome_loja")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val SENHA_KEY = stringPreferencesKey("senha")
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    suspend fun salvarUsuario(nomeLoja: String, email: String, senha: String) {
        context.dataStore.edit { preferences ->
            preferences[NOME_LOJA_KEY] = nomeLoja
            preferences[EMAIL_KEY] = email
            preferences[SENHA_KEY] = senha
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }

    suspend fun validarLogin(email: String, senha: String): Boolean {
        val preferences = context.dataStore.data.first()
        val emailSalvo = preferences[EMAIL_KEY]
        val senhaSalva = preferences[SENHA_KEY]

        return email == emailSalvo && senha == senhaSalva
    }

    val isUsuarioLogado: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN_KEY] ?: false
    }

    val nomeLoja: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[NOME_LOJA_KEY] ?: "Minha Loja"
    }

    suspend fun deslogar() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = false
        }
    }
}