package cl.duoc.veterinaria.ui.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.util.Patterns
import cl.duoc.veterinaria.model.Usuario

enum class RecoveryStatus { IDLE, SUCCESS, ERROR }

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _usuarios = MutableStateFlow(mutableListOf(
        Usuario(1, "liliana", "liliana@gmail.com", "123456"),
        Usuario(2, "Colomba", "Colomba@gmail.com", "colomba123"),
        Usuario(3, "Wilda", "Wilda@gmail.com", "wilda1")
    ))

    fun logout() {
        _uiState.update { 
            it.copy(
                isLoggedIn = false, 
                isRegisterMode = false, // Aseguramos que vuelva al Inicio de Sesión
                currentUser = null,
                user = "",
                pass = "",
                loginError = null,
                registerError = null
            ) 
        }
    }

    fun onLoginChange(user: String, pass: String) {
        _uiState.update {
            it.copy(
                user = user,
                pass = pass,
                userError = null,
                passError = null,
                loginError = null
            )
        }
    }

    fun onRegisterDataChange(nombre: String, email: String, pass: String) {
        _uiState.update {
            it.copy(
                registerNombre = nombre,
                registerEmail = email,
                registerPass = pass,
                registerError = null
            )
        }
    }

    fun toggleAuthMode() {
        _uiState.update { it.copy(isRegisterMode = !it.isRegisterMode, loginError = null, registerError = null) }
    }

    fun login() {
        val userInput = _uiState.value.user
        val passInput = _uiState.value.pass
        var userError: String? = null
        var passError: String? = null
        var formatIsValid = true

        if (userInput.isBlank()) {
            userError = "El campo no puede estar vacío"
            formatIsValid = false
        }

        if (passInput.length < 6) {
            passError = "Mínimo 6 caracteres"
            formatIsValid = false
        }

        if (!formatIsValid) {
            _uiState.update { it.copy(userError = userError, passError = passError) }
            return
        }

        val usuarioEncontrado = _usuarios.value.find { 
            it.nombreUsuario.equals(userInput, ignoreCase = true) || it.email.equals(userInput, ignoreCase = true) 
        }

        if (usuarioEncontrado != null && usuarioEncontrado.pass == passInput) {
            _uiState.update { it.copy(isLoggedIn = true, currentUser = usuarioEncontrado) }
        } else {
            _uiState.update { it.copy(loginError = "Usuario o contraseña incorrectos") }
        }
    }

    fun register() {
        val nombre = _uiState.value.registerNombre
        val email = _uiState.value.registerEmail
        val pass = _uiState.value.registerPass

        if (nombre.isBlank() || email.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(registerError = "Todos los campos son obligatorios") }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(registerError = "Email no válido") }
            return
        }

        if (_usuarios.value.any { it.email.equals(email, ignoreCase = true) }) {
            _uiState.update { it.copy(registerError = "El correo ya está registrado") }
            return
        }

        val nuevoUsuario = Usuario(
            id = _usuarios.value.size + 1,
            nombreUsuario = nombre,
            email = email,
            pass = pass
        )

        _usuarios.value.add(nuevoUsuario)
        _uiState.update { it.copy(isLoggedIn = true, currentUser = nuevoUsuario, isRegisterMode = false) }
    }

    fun onRecoveryEmailChange(email: String) {
        _uiState.update {
            it.copy(
                recoveryEmail = email,
                recoveryEmailError = null,
                recoveryStatus = RecoveryStatus.IDLE
            )
        }
    }

    fun requestPasswordRecovery() {
        val email = _uiState.value.recoveryEmail
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(recoveryEmailError = "Formato de correo inválido") }
            return
        }

        val emailExists = _usuarios.value.any { it.email == email }
        if (emailExists) {
            _uiState.update { it.copy(recoveryStatus = RecoveryStatus.SUCCESS) }
        } else {
            _uiState.update { it.copy(recoveryStatus = RecoveryStatus.ERROR) }
        }
    }

    fun resetRecoveryStatus() {
        _uiState.update { it.copy(recoveryStatus = RecoveryStatus.IDLE, recoveryEmail = "", recoveryEmailError = null) }
    }
}

data class LoginUiState(
    val user: String = "",
    val pass: String = "",
    val isLoggedIn: Boolean = false,
    val isRegisterMode: Boolean = false,
    val currentUser: Usuario? = null,
    val userError: String? = null,
    val passError: String? = null,
    val loginError: String? = null,
    val registerNombre: String = "",
    val registerEmail: String = "",
    val registerPass: String = "",
    val registerError: String? = null,
    val recoveryEmail: String = "",
    val recoveryEmailError: String? = null,
    val recoveryStatus: RecoveryStatus = RecoveryStatus.IDLE
)
