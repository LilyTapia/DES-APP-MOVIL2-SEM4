package cl.duoc.veterinaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.veterinaria.data.IVeterinariaRepository
import cl.duoc.veterinaria.data.VeterinariaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * MainViewModel gestiona los datos mostrados en la pantalla de inicio (Bienvenida).
 * @param repository Inyección de dependencia del repositorio (por defecto usa la implementación Singleton).
 */
class MainViewModel(
    private val repository: IVeterinariaRepository = VeterinariaRepository
) : ViewModel() {

    // Exponemos los flujos del repositorio a través de la interfaz
    val totalMascotas: StateFlow<Int> = repository.totalMascotasRegistradas
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalConsultas: StateFlow<Int> = repository.totalConsultasRealizadas
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val ultimoDueno: StateFlow<String> = repository.nombreUltimoDueno
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "N/A")
}
