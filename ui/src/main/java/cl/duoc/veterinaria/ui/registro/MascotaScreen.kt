package cl.duoc.veterinaria.ui.registro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * MascotaScreen es el Composable para la pantalla donde se ingresan los datos de la mascota.
 *
 * @param viewModel El ViewModel que contiene el estado del formulario de registro.
 * @param onNextClicked La acción a ejecutar cuando se hace clic en el botón "Siguiente".
 */
@Composable
fun MascotaScreen(viewModel: RegistroViewModel, onNextClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla.
        Text("Datos de la Mascota", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        // Campo de texto para el nombre de la mascota.
        RegistroTextField(
            value = viewModel.mascotaNombre.value,
            label = "Nombre",
            onValueChange = { viewModel.mascotaNombre.value = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        // Campo de texto para la especie.
        RegistroTextField(
            value = viewModel.mascotaEspecie.value,
            label = "Especie (Perro/Gato/...)",
            onValueChange = { viewModel.mascotaEspecie.value = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        // Campo de texto para la edad.
        RegistroTextField(
            value = viewModel.mascotaEdad.value,
            label = "Edad (años)",
            onValueChange = { 
                if (it.all { char -> char.isDigit() }) {
                    viewModel.mascotaEdad.value = it 
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        // Campo de texto para el peso.
        RegistroTextField(
            value = viewModel.mascotaPeso.value,
            label = "Peso (kg)",
            onValueChange = { 
                // Permitir solo números y un punto decimal
                if (it.count { char -> char == '.' } <= 1 && it.all { char -> char.isDigit() || char == '.' }) {
                     viewModel.mascotaPeso.value = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        // Campo de texto para la última vacuna.
        RegistroTextField(
            value = viewModel.mascotaUltimaVacuna.value,
            label = "Última vacuna (yyyy-MM-dd)",
            onValueChange = { viewModel.mascotaUltimaVacuna.value = it }
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        // Botón para navegar a la siguiente pantalla.
        Button(
            onClick = onNextClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Siguiente")
        }
    }
}
