package cl.duoc.veterinaria.model

import java.time.LocalDateTime

/**
 * Estados posibles de una consulta veterinaria.
 */
enum class EstadoConsulta {
    PENDIENTE,
    CONFIRMADA,
    REALIZADA,
    CANCELADA
}

/**
 * Tipos de servicio que ofrece la clínica.
 * Incluye una descripción legible.
 */
enum class TipoServicio(val descripcion: String) {
    CONTROL("Control Sano"),
    URGENCIA("Urgencia"),
    VACUNA("Vacunación"),
    CIRUGIA("Cirugía Menor")
}

/**
 * Representa al profesional veterinario.
 */
data class Veterinario(
    val nombre: String,
    val especialidad: String
)

/**
 * Representa una consulta veterinaria agendada.
 */
data class Consulta(
    val idConsulta: String,
    val descripcion: String,
    val costoConsulta: Double,
    val estado: EstadoConsulta = EstadoConsulta.PENDIENTE,
    val fechaAtencion: LocalDateTime? = null,
    val veterinarioAsignado: Veterinario? = null,
    val comentarios: String? = null
)
