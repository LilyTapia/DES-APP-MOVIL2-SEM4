package cl.duoc.veterinaria.ui.registro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cl.duoc.veterinaria.ui.R

/**
 * DuenoScreen es el Composable para la pantalla donde se ingresan los datos del dueño de la mascota.
 *
 * @param viewModel El ViewModel que contiene el estado del formulario de registro.
 * @param onNextClicked La acción a ejecutar cuando se hace clic en el botón "Siguiente".
 */
@Composable
fun DuenoScreen(viewModel: RegistroViewModel, onNextClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Muestra una imagen en la parte superior de la pantalla.
        Image(
            painter = painterResource(id = R.drawable.perrito),
            contentDescription = "Logo Veterinaria",
            modifier = Modifier.size(120.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        // Título de la pantalla.
        Text("Datos del Dueño", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        // Campo de texto para el nombre del cliente.
        RegistroTextField(
            value = viewModel.duenoNombre.value,
            label = "Nombre del cliente",
            onValueChange = { viewModel.duenoNombre.value = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        // Campo de texto para el teléfono de contacto.
        RegistroTextField(
            value = viewModel.duenoTelefono.value,
            label = "Teléfono de contacto",
            onValueChange = { viewModel.duenoTelefono.value = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        // Campo de texto para el email.
        RegistroTextField(
            value = viewModel.duenoEmail.value,
            label = "Email (para recordatorios)",
            onValueChange = { viewModel.duenoEmail.value = it }
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
