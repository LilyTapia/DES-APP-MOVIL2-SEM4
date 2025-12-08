package cl.duoc.veterinaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import cl.duoc.veterinaria.ui.navigation.NavGraph
import cl.duoc.veterinaria.ui.theme.VeterinariaAppTheme

/**
 * MainActivity es la actividad principal y el punto de entrada de la aplicación.
 * Configura la interfaz de usuario utilizando Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    /**
     * onCreate se llama cuando la actividad es creada por primera vez.
     * Aquí se inicializa la interfaz de usuario de la aplicación.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita el modo de borde a borde para que la app ocupe toda la pantalla
        enableEdgeToEdge()
        // setContent define el diseño de la actividad utilizando funciones de Composable.
        setContent {
            // VeterinariaAppTheme aplica el tema personalizado de la aplicación (colores, fuentes, etc.)
            VeterinariaAppTheme {
                // Scaffold es un layout predefinido de Material Design que proporciona una estructura básica.
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    // NavGraph es el Composable que gestiona la navegación entre las diferentes pantallas de la app.
                    NavGraph()
                }
            }
        }
    }
}
