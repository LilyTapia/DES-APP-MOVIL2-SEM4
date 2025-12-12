package cl.duoc.veterinaria.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.veterinaria.ui.registro.DuenoScreen
import cl.duoc.veterinaria.ui.registro.MascotaScreen
import cl.duoc.veterinaria.ui.registro.ResumenScreen
import cl.duoc.veterinaria.ui.registro.ServicioScreen
import cl.duoc.veterinaria.ui.screens.BienvenidaScreen
import cl.duoc.veterinaria.ui.screens.ListadoScreen
import cl.duoc.veterinaria.ui.screens.PedidoScreen
import cl.duoc.veterinaria.ui.viewmodel.ConsultaViewModel
import cl.duoc.veterinaria.ui.viewmodel.MainViewModel
import cl.duoc.veterinaria.ui.viewmodel.RegistroViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    
    // ViewModels inyectados con viewModel() que sobrevive a cambios de configuración
    // Nota: Para RegistroViewModel, lo instanciamos aquí para compartirlo entre pantallas del flujo (scope compartido)
    val registroViewModel: RegistroViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "bienvenida_screen",
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        composable("bienvenida_screen") {
            // Instanciamos el MainViewModel específico para esta pantalla
            val mainViewModel: MainViewModel = viewModel()
            
            BienvenidaScreen(
                onNavigateToNext = {
                    navController.navigate("dueno_screen")
                },
                onNavigateToRegistro = {
                    navController.navigate("dueno_screen")
                },
                onNavigateToListado = {
                    navController.navigate("listado_screen")
                },
                onNavigateToPedidos = {
                    navController.navigate("pedidos_screen") 
                },
                viewModel = mainViewModel
            )
        }
        composable("dueno_screen") {
            DuenoScreen(viewModel = registroViewModel, onNextClicked = {
                navController.navigate("mascota_screen")
            })
        }
        composable("mascota_screen") {
            MascotaScreen(viewModel = registroViewModel, onNextClicked = {
                navController.navigate("servicio_screen")
            })
        }
        composable("servicio_screen") {
            ServicioScreen(viewModel = registroViewModel, onNextClicked = {
                navController.navigate("pedidos_screen")
            })
        }
        composable("pedidos_screen") {
            PedidoScreen(
                viewModel = registroViewModel,
                onNextClicked = {
                    navController.navigate("resumen_screen")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("resumen_screen") {
            ResumenScreen(viewModel = registroViewModel, onConfirmClicked = {
                registroViewModel.clearData()
                navController.popBackStack("bienvenida_screen", inclusive = true)
                navController.navigate("bienvenida_screen")
            })
        }
        composable("listado_screen") {
            // Instanciamos el ConsultaViewModel específico para el listado
            val consultaViewModel: ConsultaViewModel = viewModel()
            
            ListadoScreen(
                onBack = {
                    navController.popBackStack()
                },
                viewModel = consultaViewModel
            )
        }
    }
}
