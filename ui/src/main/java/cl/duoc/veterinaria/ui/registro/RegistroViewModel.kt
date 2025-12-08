package cl.duoc.veterinaria.ui.registro

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cl.duoc.veterinaria.data.VeterinariaRepository
import cl.duoc.veterinaria.model.Consulta
import cl.duoc.veterinaria.model.Dueno
import cl.duoc.veterinaria.model.EstadoConsulta
import cl.duoc.veterinaria.model.Mascota
import cl.duoc.veterinaria.model.TipoServicio
import cl.duoc.veterinaria.service.AgendaVeterinario
import cl.duoc.veterinaria.service.ConsultaService
import java.time.Clock
import java.time.LocalDate

/**
 * RegistroViewModel es un ViewModel que almacena y gestiona los datos relacionados con el
 * proceso de registro de una nueva consulta.
 */
class RegistroViewModel : ViewModel() {
    // Estados mutables para almacenar los datos del formulario del dueño.
    var duenoNombre = mutableStateOf("")
    var duenoTelefono = mutableStateOf("")
    var duenoEmail = mutableStateOf("")

    // Estados mutables para almacenar los datos del formulario de la mascota.
    var mascotaNombre = mutableStateOf("")
    var mascotaEspecie = mutableStateOf("")
    var mascotaEdad = mutableStateOf("0")
    var mascotaPeso = mutableStateOf("0.0")
    var mascotaUltimaVacuna = mutableStateOf(LocalDate.now().toString())

    // Estado mutable para almacenar el tipo de servicio seleccionado.
    var tipoServicio = mutableStateOf<TipoServicio?>(null)

    // Estado mutable para almacenar la consulta registrada después de procesar los datos.
    var consultaRegistrada = mutableStateOf<Consulta?>(null)
    
    // Evita procesar múltiples veces si hay recomposiciones
    private var procesado = false

    /**
     * procesarRegistro() toma los datos ingresados en los formularios, interactúa con los
     * servicios de negocio para crear una nueva consulta y actualiza el estado de consultaRegistrada.
     */
    fun procesarRegistro() {
        if (procesado) return
        
        val dueno = Dueno(duenoNombre.value, duenoTelefono.value, duenoEmail.value)
        val mascota = Mascota(
            nombre = mascotaNombre.value,
            especie = mascotaEspecie.value,
            edad = mascotaEdad.value.toIntOrNull() ?: 0,
            pesoKg = mascotaPeso.value.toDoubleOrNull() ?: 0.0,
            ultimaVacunacion = LocalDate.parse(mascotaUltimaVacuna.value)
        )

        val servicio = tipoServicio.value ?: TipoServicio.CONTROL
        val minutosEstimados = 30

        // Lógica de negocio para agendar la consulta.
        val fechaSugerida = AgendaVeterinario.siguienteSlotHabil(Clock.systemDefaultZone())
        val (veterinarioAsignado, slots) = AgendaVeterinario.reservarBloque(fechaSugerida, 1)

        // Lógica de negocio para calcular el costo.
        val costoBase = ConsultaService.calcularCostoBase(servicio, minutosEstimados)
        val (costoConDescuento, _) = ConsultaService.aplicarDescuento(costoBase, 1)
        val costoFinal = ConsultaService.redondearClp(costoConDescuento)

        // Crea una nueva instancia de Consulta con los datos procesados.
        val nuevaConsulta = Consulta(
            idConsulta = "C-" + (100..999).random(),
            descripcion = "Consulta para ${mascota.nombre}",
            costoConsulta = costoFinal,
            estado = EstadoConsulta.PENDIENTE,
            fechaAtencion = slots.firstOrNull(),
            veterinarioAsignado = veterinarioAsignado
        )
        
        // Actualiza el estado con la nueva consulta registrada.
        consultaRegistrada.value = nuevaConsulta
        
        // Registrar en repositorio global
        VeterinariaRepository.registrarAtencion(dueno.nombre, 1)
        procesado = true
    }

    /**
     * clearData() restablece todos los estados del ViewModel a sus valores iniciales.
     */
    fun clearData() {
        duenoNombre.value = ""
        duenoTelefono.value = ""
        duenoEmail.value = ""
        mascotaNombre.value = ""
        mascotaEspecie.value = ""
        mascotaEdad.value = "0"
        mascotaPeso.value = "0.0"
        mascotaUltimaVacuna.value = LocalDate.now().toString()
        tipoServicio.value = null
        consultaRegistrada.value = null
        procesado = false
    }
}
