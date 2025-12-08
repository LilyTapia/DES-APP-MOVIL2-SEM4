package cl.duoc.veterinaria.model

import java.time.LocalDate
import java.util.Locale

data class Mascota(
    val nombre: String,
    val especie: String,
    val edad: Int,
    val pesoKg: Double,
    val ultimaVacunacion: LocalDate?
) {
    fun mostrarInformacion(): String {
        val ultimaVacunaTexto = ultimaVacunacion?.toString() ?: "Sin registro"
        val pesoTexto = String.format(Locale.US, "%.1f", pesoKg)
        return "$nombre ($especie) | Edad: $edad años | Peso: $pesoTexto kg | Última vacuna: $ultimaVacunaTexto"
    }
}
