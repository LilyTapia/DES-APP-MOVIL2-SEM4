package cl.duoc.veterinaria.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.veterinaria.data.VeterinariaRepository
import cl.duoc.veterinaria.model.Cliente
import cl.duoc.veterinaria.model.Consulta
import cl.duoc.veterinaria.model.DetallePedido
import cl.duoc.veterinaria.model.Dueno
import cl.duoc.veterinaria.model.EstadoConsulta
import cl.duoc.veterinaria.model.Mascota
import cl.duoc.veterinaria.model.Medicamento
import cl.duoc.veterinaria.model.MedicamentoPromocional
import cl.duoc.veterinaria.model.Pedido
import cl.duoc.veterinaria.model.TipoServicio
import cl.duoc.veterinaria.service.AgendaVeterinario
import cl.duoc.veterinaria.service.ConsultaService
import cl.duoc.veterinaria.service.MascotaService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.LocalDate

/**
 * RegistroViewModel gestiona el estado del flujo de registro de consultas y pedidos.
 * Utiliza StateFlow para exponer un estado inmutable a la UI.
 */
class RegistroViewModel : ViewModel() {

    // Estado interno mutable
    private val _uiState = MutableStateFlow(RegistroUiState())
    // Estado público inmutable
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    // Catálogo de medicamentos disponibles (Hardcoded para este ejemplo)
    val catalogoMedicamentos = listOf(
        Medicamento("Antibiótico Generico", 500, 15000.0),
        Medicamento("Analgésico Básico", 200, 8000.0),
        MedicamentoPromocional("Antiinflamatorio Premium", 200, 25000.0, 0.20), // 20% dcto
        MedicamentoPromocional("Vitaminas Caninas", 100, 12000.0, 0.10) // 10% dcto
    )

    // --- Actualizadores de Estado (Eventos) ---

    fun updateDuenoNombre(nombre: String) {
        _uiState.update { it.copy(duenoNombre = nombre) }
    }

    fun updateDuenoTelefono(telefono: String) {
        _uiState.update { it.copy(duenoTelefono = telefono) }
    }

    fun updateDuenoEmail(email: String) {
        _uiState.update { it.copy(duenoEmail = email) }
    }

    fun updateMascotaNombre(nombre: String) {
        _uiState.update { it.copy(mascotaNombre = nombre) }
    }

    fun updateMascotaEspecie(especie: String) {
        _uiState.update { it.copy(mascotaEspecie = especie) }
    }

    fun updateMascotaEdad(edad: String) {
        if (edad.all { char -> char.isDigit() }) {
            _uiState.update { it.copy(mascotaEdad = edad) }
        }
    }

    fun updateMascotaPeso(peso: String) {
        if (peso.count { char -> char == '.' } <= 1 && peso.all { char -> char.isDigit() || char == '.' }) {
            _uiState.update { it.copy(mascotaPeso = peso) }
        }
    }

    fun updateMascotaUltimaVacuna(fecha: String) {
        _uiState.update { it.copy(mascotaUltimaVacuna = fecha) }
    }

    fun updateTipoServicio(servicio: TipoServicio) {
        _uiState.update { it.copy(tipoServicio = servicio) }
    }

    // --- Lógica del Carrito de Medicamentos ---

    fun agregarMedicamentoAlCarrito(medicamento: Medicamento) {
        _uiState.update { currentState ->
            val carritoActual = currentState.carrito.toMutableList()
            val index = carritoActual.indexOfFirst { it.medicamento.nombre == medicamento.nombre }

            if (index != -1) {
                // Si ya existe, aumentamos la cantidad
                val detalleExistente = carritoActual[index]
                carritoActual[index] = detalleExistente.copy(cantidad = detalleExistente.cantidad + 1)
            } else {
                // Si no existe, lo agregamos
                carritoActual.add(DetallePedido(medicamento, 1))
            }
            currentState.copy(carrito = carritoActual)
        }
    }

    fun quitarMedicamentoDelCarrito(medicamento: Medicamento) {
        _uiState.update { currentState ->
            val carritoActual = currentState.carrito.toMutableList()
            val index = carritoActual.indexOfFirst { it.medicamento.nombre == medicamento.nombre }

            if (index != -1) {
                val detalleExistente = carritoActual[index]
                if (detalleExistente.cantidad > 1) {
                    carritoActual[index] = detalleExistente.copy(cantidad = detalleExistente.cantidad - 1)
                } else {
                    carritoActual.removeAt(index)
                }
            }
            currentState.copy(carrito = carritoActual)
        }
    }

    /**
     * Procesa el registro final: Consulta + Pedido de Medicamentos.
     */
    fun procesarRegistro() {
        val currentState = _uiState.value
        // Evitar reprocesar si ya hay una consulta registrada en este estado
        if (currentState.consultaRegistrada != null) return

        viewModelScope.launch {
            // Inicia carga con mensaje "Cargando datos..."
            _uiState.update { it.copy(isProcesando = true, mensajeProgreso = "Calculando costos...") }
            delay(1500) // Simular primera etapa

            // Cambia mensaje a "Generando resumen..."
            _uiState.update { it.copy(mensajeProgreso = "Confirmando reserva y pedido...") }
            delay(1500) // Simular segunda etapa

            val dueno = Dueno(currentState.duenoNombre, currentState.duenoTelefono, currentState.duenoEmail)
            
            val nombreCliente = if (currentState.duenoNombre.isNotBlank()) currentState.duenoNombre else "Cliente Anónimo"
            val emailCliente = if (currentState.duenoEmail.isNotBlank()) currentState.duenoEmail else "anonimo@mail.com"
            val telefonoCliente = if (currentState.duenoTelefono.isNotBlank()) currentState.duenoTelefono else "00000000"
            
            val clientePedido = Cliente(nombreCliente, emailCliente, telefonoCliente)

            val mascota = Mascota(
                nombre = currentState.mascotaNombre,
                especie = currentState.mascotaEspecie,
                edad = currentState.mascotaEdad.toIntOrNull() ?: 0,
                pesoKg = currentState.mascotaPeso.toDoubleOrNull() ?: 0.0,
                ultimaVacunacion = try { LocalDate.parse(currentState.mascotaUltimaVacuna) } catch (e: Exception) { LocalDate.now() }
            )

            val servicio = currentState.tipoServicio ?: TipoServicio.CONTROL
            val minutosEstimados = 30

            // 1. Lógica de Consulta Médica
            val fechaSugerida = AgendaVeterinario.siguienteSlotHabil(Clock.systemDefaultZone())
            val (veterinarioAsignado, slots) = AgendaVeterinario.reservarBloque(fechaSugerida, 1)

            val costoBase = ConsultaService.calcularCostoBase(servicio, minutosEstimados)
            val (costoConDescuento, _) = ConsultaService.aplicarDescuento(costoBase, 1)
            val costoFinal = ConsultaService.redondearClp(costoConDescuento)

            // --- INTEGRACIÓN DE MASCOTA SERVICE ---
            var recomendacionVacuna: String? = null
            var comentariosExtra: String? = null
            
            if (servicio == TipoServicio.VACUNA) {
                // Si el servicio es Vacuna, usamos MascotaService para lógica experta
                val descripcionFreq = MascotaService.descripcionFrecuencia(mascota)
                val proximaFecha = MascotaService.calcularProximaVacunacion(mascota)
                
                // Convertimos proximaFecha (LocalDate) a LocalDateTime para que AgendaVeterinario.fmt lo acepte
                val fechaHoraVacuna = proximaFecha.atStartOfDay()
                
                recomendacionVacuna = "$descripcionFreq. Próxima dosis: ${AgendaVeterinario.fmt(fechaHoraVacuna)}"
                comentariosExtra = "Se aplicó protocolo según $descripcionFreq."
            }
            // ---------------------------------------

            val nuevaConsulta = Consulta(
                idConsulta = "C-" + (100..999).random(),
                descripcion = "Consulta para ${mascota.nombre}",
                costoConsulta = costoFinal,
                estado = EstadoConsulta.PENDIENTE,
                fechaAtencion = slots.firstOrNull(),
                veterinarioAsignado = veterinarioAsignado,
                comentarios = comentariosExtra // Guardamos la info también en la consulta
            )

            // 2. Lógica de Pedido de Medicamentos
            var nuevoPedido: Pedido? = null
            if (currentState.carrito.isNotEmpty()) {
                nuevoPedido = Pedido(clientePedido, currentState.carrito)
            }

            // Actualizar repositorio global
            VeterinariaRepository.registrarAtencion(nombreCliente, 1, mascota.nombre, mascota.especie)

            // Actualizar estado final
            _uiState.update {
                it.copy(
                    consultaRegistrada = nuevaConsulta,
                    pedidoRegistrado = nuevoPedido,
                    recomendacionVacuna = recomendacionVacuna, // Pasamos la recomendación a la UI
                    isProcesando = false
                )
            }
        }
    }

    fun clearData() {
        _uiState.value = RegistroUiState()
    }
}
