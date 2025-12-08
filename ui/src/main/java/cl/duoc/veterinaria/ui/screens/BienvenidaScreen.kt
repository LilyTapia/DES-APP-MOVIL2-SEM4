package cl.duoc.veterinaria.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.duoc.veterinaria.data.VeterinariaRepository
import cl.duoc.veterinaria.ui.R

@Composable
fun BienvenidaScreen(onNavigateToNext: () -> Unit) {
    // Usamos Box para apilar capas (Fondo + Contenido)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // --- CAPA 1: FONDO ---
        Image(
            painter = painterResource(id = R.drawable.fondo1), // Actualizado a tu imagen 'fondo1'
            contentDescription = null, 
            modifier = Modifier.fillMaxSize(), 
            contentScale = ContentScale.Crop, 
            alpha = 0.1f // Opacidad reducida al 10% (0.1f) para aclarar más el fondo
        )

        // --- CAPA 2: CONTENIDO ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido a VeterinariaApp",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold // Título en negrita
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Imagen central (Logo)
            Image(
                painter = painterResource(id = R.drawable.inicio),
                contentDescription = "Logo de la veterinaria",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tarjeta de Resumen (Paso 5)
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Resumen del Sistema",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Mascotas Registradas: ${VeterinariaRepository.totalMascotasRegistradas}",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Consultas Realizadas: ${VeterinariaRepository.totalConsultasRealizadas}",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Último Dueño: ${VeterinariaRepository.nombreUltimoDueno}",
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onNavigateToNext) {
                Text("Iniciar Registro")
            }
        }
    }
}
