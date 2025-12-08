package cl.duoc.veterinaria.ui.registro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.veterinaria.model.TipoServicio

/**
 * ServicioScreen es el Composable para la pantalla donde se selecciona el tipo de servicio.
 *
 * @param viewModel El ViewModel que contiene el estado del formulario de registro.
 * @param onNextClicked La acción a ejecutar cuando se selecciona un servicio.
 */
@Composable
fun ServicioScreen(viewModel: RegistroViewModel, onNextClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla.
        Text("Seleccione un Servicio", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        // Itera sobre todos los valores del enum TipoServicio para crear un botón para cada uno.
        TipoServicio.values().forEach { servicio ->
            Button(
                onClick = { 
                    // Actualiza el tipo de servicio en el ViewModel.
                    viewModel.tipoServicio.value = servicio
                    // Navega a la siguiente pantalla.
                    onNextClicked()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(servicio.descripcion)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
