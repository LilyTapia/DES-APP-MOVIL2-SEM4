package cl.duoc.veterinaria.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class TipoServicio(val descripcion: String) {
    CONTROL("Control"),
    VACUNA("Vacuna"),
    URGENCIA("Urgencia"),
    OTRO("Otro");

    val clave get() = descripcion.lowercase()
}

enum class EstadoReserva { CONFIRMADA, NO_CONFIRMADA }

enum class EstadoConsulta { PENDIENTE, REALIZADA }

data class Consulta(
    val idConsulta: String,
    val descripcion: String,
    var costoConsulta: Double,
    var estado: EstadoConsulta,
    var fechaAtencion: LocalDateTime? = null,
    var comentarios: String? = null,
    var veterinarioAsignado: Veterinario? = null
) {
    fun calcularCostoFinalConDescuento(porcentaje: Double): Double {
        val factor = 1 - porcentaje.coerceIn(0.0, 1.0)
        return costoConsulta * factor
    }

    fun actualizarEstado(nuevoEstado: EstadoConsulta, fecha: LocalDateTime? = null) {
        estado = nuevoEstado
        fechaAtencion = fecha
    }

    fun resumenFormateado(dueno: Dueno, mascotas: List<Mascota>): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val fechaTexto = fechaAtencion?.format(formatter) ?: "Pendiente"
        val comentariosTexto = comentarios?.takeIf { it.isNotBlank() } ?: "Sin comentarios"
        val veterinarioTexto = veterinarioAsignado?.nombre ?: "No asignado"
        val detallesMascotas = mascotas.joinToString(separator = "\n") { "  - ${it.mostrarInformacion()}" }

        return buildString {
            appendLine("Cliente    : ${dueno.nombre}")
            appendLine("Teléfono   : ${dueno.telefono}")
            appendLine("Email      : ${dueno.email}")
            appendLine("Veterinario: $veterinarioTexto")
            appendLine("Estado     : ${estado.descripcionCorta()}")
            appendLine("Fecha aten.: $fechaTexto")
            appendLine("Mascotas   :")
            appendLine(detallesMascotas)
            append("Comentarios: $comentariosTexto")
        }
    }
}

fun EstadoConsulta.descripcionCorta(): String = formatearNombreEnum(name)

fun EstadoReserva.descripcionCorta(): String = formatearNombreEnum(name)

private fun formatearNombreEnum(valor: String): String =
    valor.lowercase()
        .replace('_', ' ')
        .split(' ')
        .joinToString(" ") { palabra ->
            if (palabra.isEmpty()) palabra else palabra[0].uppercaseChar() + palabra.substring(1)
        }
