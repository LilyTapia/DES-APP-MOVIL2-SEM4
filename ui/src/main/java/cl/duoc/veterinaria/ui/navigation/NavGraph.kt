package cl.duoc.veterinaria.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.veterinaria.ui.registro.DuenoScreen
import cl.duoc.veterinaria.ui.registro.MascotaScreen
import cl.duoc.veterinaria.ui.registro.RegistroViewModel
import cl.duoc.veterinaria.ui.registro.ResumenScreen
import cl.duoc.veterinaria.ui.registro.ServicioScreen
import cl.duoc.veterinaria.ui.screens.BienvenidaScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val viewModel: RegistroViewModel = viewModel()

    NavHost(navController = navController, startDestination = "bienvenida_screen") {
        composable("bienvenida_screen") {
            BienvenidaScreen(onNavigateToNext = {
                navController.navigate("dueno_screen")
            })
        }
        composable("dueno_screen") {
            DuenoScreen(viewModel = viewModel, onNextClicked = {
                navController.navigate("mascota_screen")
            })
        }
        composable("mascota_screen") {
            MascotaScreen(viewModel = viewModel, onNextClicked = {
                navController.navigate("servicio_screen")
            })
        }
        composable("servicio_screen") {
            ServicioScreen(viewModel = viewModel, onNextClicked = {
                navController.navigate("resumen_screen")
            })
        }
        composable("resumen_screen") {
            ResumenScreen(viewModel = viewModel, onConfirmClicked = {
                viewModel.clearData()
                navController.popBackStack("bienvenida_screen", inclusive = true)
                navController.navigate("bienvenida_screen")
            })
        }
    }
}
