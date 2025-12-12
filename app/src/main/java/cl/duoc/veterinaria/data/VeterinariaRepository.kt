package cl.duoc.veterinaria.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Define el contrato para el repositorio de la veterinaria.
 * Abstrae el origen de los datos (en este caso, en memoria) para que los ViewModels
 * no dependan de una implementación concreta, facilitando pruebas y futuros cambios.
 */
interface IVeterinariaRepository {
    val totalMascotasRegistradas: StateFlow<Int>
    val totalConsultasRealizadas: StateFlow<Int>
    val nombreUltimoDueno: StateFlow<String>
    val listaMascotas: StateFlow<List<String>>

    fun registrarAtencion(nombreDueno: String, cantidadMascotas: Int, nombreMascota: String, especieMascota: String)
}

/**
 * Implementación Singleton del repositorio que guarda los datos de la app en memoria.
 * Utiliza StateFlow para que la UI pueda reaccionar a los cambios en los datos.
 */
object VeterinariaRepository : IVeterinariaRepository {
    // Estado interno mutable, privado al repositorio.
    private val _totalMascotasRegistradas = MutableStateFlow(0)
    private val _totalConsultasRealizadas = MutableStateFlow(0)
    private val _nombreUltimoDueno = MutableStateFlow("N/A")
    private val _listaMascotas = MutableStateFlow<List<String>>(emptyList())

    // Se exponen los datos como StateFlow inmutables para que los observadores no puedan modificarlos.
    override val totalMascotasRegistradas: StateFlow<Int> = _totalMascotasRegistradas.asStateFlow()
    override val totalConsultasRealizadas: StateFlow<Int> = _totalConsultasRealizadas.asStateFlow()
    override val nombreUltimoDueno: StateFlow<String> = _nombreUltimoDueno.asStateFlow()
    override val listaMascotas: StateFlow<List<String>> = _listaMascotas.asStateFlow()

    override fun registrarAtencion(nombreDueno: String, cantidadMascotas: Int, nombreMascota: String, especieMascota: String) {
        _nombreUltimoDueno.value = nombreDueno
        _totalMascotasRegistradas.update { it + cantidadMascotas }
        _totalConsultasRealizadas.update { it + 1 }
        
        // Se agrega la nueva mascota al listado histórico.
        val nuevaEntrada = "Mascota: $nombreMascota ($especieMascota) - Dueño: $nombreDueno"
        _listaMascotas.update { it + nuevaEntrada }
    }
}
