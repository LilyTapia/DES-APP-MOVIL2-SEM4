package cl.duoc.veterinaria.model

open class Usuario(
    open val nombre: String,
    open val telefono: String,
    open val email: String
) {
    fun emailValidoOrNull(): String? =
        email.takeIf { EMAIL_REGEX.matches(it) && it != EMAIL_POR_DEFECTO }

    fun emailSeguro(): String = emailValidoOrNull() ?: EMAIL_POR_DEFECTO

    companion object {
        const val EMAIL_POR_DEFECTO = "correo@invalido.com"
        private val EMAIL_REGEX = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    }
}

data class Dueno(
    override val nombre: String,
    override val telefono: String,
    override val email: String
) : Usuario(nombre, telefono, email)

data class Veterinario(
    override val nombre: String,
    override val telefono: String,
    override val email: String,
    val especialidad: String
) : Usuario(nombre, telefono, email)
