package stream.alchemists.simplenoteapp.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import stream.alchemists.simplenoteapp.SimpleNoteApp

class LoginViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    fun login() = save(true)
    fun logout() = save(false)
    fun isLogged() = sharedPreferences.getBoolean("isLogged", false)

    private fun save(isLogged: Boolean) {
        sharedPreferences.edit().putBoolean("isLogged", isLogged).apply()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val sharedPreferences = (application as SimpleNoteApp).sharedPreferences
                return LoginViewModel(sharedPreferences) as T
            }
        }
    }
}