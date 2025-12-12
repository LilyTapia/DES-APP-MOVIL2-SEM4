package cl.duoc.veterinaria.service

import cl.duoc.veterinaria.model.Mascota
import cl.duoc.veterinaria.model.TipoServicio
import cl.duoc.veterinaria.model.Veterinario
import java.time.Clock
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.round

/**
 * Servicio encargado de la lógica de agenda y asignación de veterinarios.
 * Principio SOLID: Single Responsibility Principle (SRP).
 */
object AgendaVeterinario {
    private val veterinarios = listOf(
        Veterinario("Dr. Pérez", "General"),
        Veterinario("Dra. González", "Cirugía"),
        Veterinario("Dr. Soto", "Dermatología")
    )

    private val FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    fun siguienteSlotHabil(clock: Clock): LocalDateTime {
        var fecha = LocalDateTime.now(clock).plusHours(1)
        // Lógica simple: Buscar siguiente hora hábil (9 a 18 hrs, Lunes a Viernes)
        while (fecha.hour < 9 || fecha.hour >= 18 || fecha.dayOfWeek == DayOfWeek.SATURDAY || fecha.dayOfWeek == DayOfWeek.SUNDAY) {
            fecha = fecha.plusHours(1)
            if (fecha.hour >= 18) {
                fecha = fecha.plusDays(1).withHour(9).withMinute(0)
            }
        }
        return fecha.withMinute(0).withSecond(0).withNano(0)
    }

    fun reservarBloque(fechaInicio: LocalDateTime, bloques: Int): Pair<Veterinario, List<LocalDateTime>> {
        val slots = mutableListOf<LocalDateTime>()
        var actual = fechaInicio
        repeat(bloques) {
            slots.add(actual)
            actual = actual.plusMinutes(30)
        }
        return Pair(veterinarios.random(), slots)
    }

    fun fmt(fecha: LocalDateTime): String = fecha.format(FORMATO_FECHA_HORA)
}

/**
 * Servicio encargado del cálculo de costos de consultas.
 */
object ConsultaService {
    private const val COSTO_BASE_MINUTO = 1000.0

    fun calcularCostoBase(tipo: TipoServicio, duracionMinutos: Int): Double {
        val factor = when (tipo) {
            TipoServicio.URGENCIA -> 2.5
            TipoServicio.CIRUGIA -> 3.0
            else -> 1.0
        }
        return duracionMinutos * COSTO_BASE_MINUTO * factor
    }

    fun aplicarDescuento(costo: Double, cantidadMascotas: Int): Pair<Double, Boolean> {
        return if (cantidadMascotas > 1) {
            Pair(costo * 0.9, true) // 10% descuento
        } else {
            Pair(costo, false)
        }
    }

    fun redondearClp(valor: Double): Double = round(valor)
}

/**
 * Servicio encargado de la lógica de salud de la mascota.
 */
object MascotaService {
    fun calcularProximaVacunacion(mascota: Mascota): LocalDate {
        return if (mascota.edad < 1) {
            mascota.ultimaVacunacion.plusMonths(1)
        } else {
            mascota.ultimaVacunacion.plusYears(1)
        }
    }

    fun descripcionFrecuencia(mascota: Mascota): String {
        return if (mascota.edad < 1) "Mensual (Cachorro)" else "Anual (Adulto)"
    }
    
    fun calcularEdad(fechaNacimiento: LocalDate): Int {
        return ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now()).toInt()
    }
}
