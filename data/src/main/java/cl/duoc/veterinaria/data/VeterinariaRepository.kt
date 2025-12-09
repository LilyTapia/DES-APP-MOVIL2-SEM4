package cl.duoc.veterinaria.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Singleton para almacenar el estado global de la aplicación en memoria.
 * Utiliza StateFlow para permitir que la UI observe los cambios de forma reactiva.
 */
object VeterinariaRepository {
    // Estado interno mutable
    private val _totalMascotasRegistradas = MutableStateFlow(0)
    private val _totalConsultasRealizadas = MutableStateFlow(0)
    private val _nombreUltimoDueno = MutableStateFlow("N/A")
    private val _listaMascotas = MutableStateFlow<List<String>>(emptyList())

    // Estado público inmutable observable
    val totalMascotasRegistradas: StateFlow<Int> = _totalMascotasRegistradas.asStateFlow()
    val totalConsultasRealizadas: StateFlow<Int> = _totalConsultasRealizadas.asStateFlow()
    val nombreUltimoDueno: StateFlow<String> = _nombreUltimoDueno.asStateFlow()
    val listaMascotas: StateFlow<List<String>> = _listaMascotas.asStateFlow()

    fun registrarAtencion(nombreDueno: String, cantidadMascotas: Int, nombreMascota: String, especieMascota: String) {
        _nombreUltimoDueno.value = nombreDueno
        _totalMascotasRegistradas.update { it + cantidadMascotas }
        _totalConsultasRealizadas.update { it + 1 }
        
        // Agregar la nueva mascota a la lista dinámica
        val nuevaEntrada = "Mascota: $nombreMascota ($especieMascota) - Dueño: $nombreDueno"
        _listaMascotas.update { it + nuevaEntrada }
    }
}
