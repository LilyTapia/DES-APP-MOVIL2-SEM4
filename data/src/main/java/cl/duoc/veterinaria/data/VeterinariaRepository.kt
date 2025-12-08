package cl.duoc.veterinaria.data

/**
 * Singleton para almacenar el estado global de la aplicación en memoria.
 * Permite cumplir con el requerimiento de mostrar un resumen en la pantalla inicial.
 */
object VeterinariaRepository {
    var totalMascotasRegistradas: Int = 0
        private set
    
    var totalConsultasRealizadas: Int = 0
        private set
    
    var nombreUltimoDueno: String = "N/A"
        private set

    fun registrarAtencion(nombreDueno: String, cantidadMascotas: Int) {
        nombreUltimoDueno = nombreDueno
        totalMascotasRegistradas += cantidadMascotas
        totalConsultasRealizadas++
    }
}
