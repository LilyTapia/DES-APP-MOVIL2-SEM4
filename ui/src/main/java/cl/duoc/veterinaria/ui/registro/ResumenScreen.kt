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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.veterinaria.service.AgendaVeterinario

/**
 * ResumenScreen es el Composable para la pantalla que muestra el resumen de la consulta registrada.
 *
 * @param viewModel El ViewModel que contiene el estado del formulario de registro y el resultado de la consulta.
 * @param onConfirmClicked La acción a ejecutar cuando se hace clic en el botón de confirmación.
 */
@Composable
fun ResumenScreen(viewModel: RegistroViewModel, onConfirmClicked: () -> Unit) {
    // LaunchedEffect se usa para ejecutar la lógica de procesamiento del registro una sola vez
    // cuando se compone esta pantalla.
    LaunchedEffect(Unit) {
        viewModel.procesarRegistro()
    }

    // Obtiene la consulta registrada desde el ViewModel.
    val consulta = viewModel.consultaRegistrada.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Resumen de la Consulta", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Muestra los detalles de la consulta si ya ha sido registrada.
        if (consulta != null) {
            Text("ID de Consulta: ${consulta.idConsulta}")
            Text("Descripción: ${consulta.descripcion}")
            Text("Costo: $${consulta.costoConsulta}")
            Text("Estado: ${consulta.estado}")
            Text("Fecha: ${consulta.fechaAtencion?.let { AgendaVeterinario.fmt(it) } ?: "No asignada"}")
            Text("Veterinario: ${consulta.veterinarioAsignado?.nombre ?: "No asignado"}")
        } else {
            // Muestra un mensaje de carga mientras se procesa la consulta.
            Text("Generando resumen...")
        }

        Spacer(modifier = Modifier.height(24.dp))
        // Botón para confirmar y volver a la pantalla de inicio.
        Button(
            onClick = onConfirmClicked,
            modifier = Modifier.fillMaxWidth(),
            // El botón se habilita solo cuando la consulta ha sido registrada.
            enabled = consulta != null
        ) {
            Text("Confirmar y Volver")
        }
    }
}
