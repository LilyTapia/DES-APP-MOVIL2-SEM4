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
import cl.duoc.veterinaria.ui.registro.RegistroViewModel
import cl.duoc.veterinaria.ui.registro.ResumenScreen
import cl.duoc.veterinaria.ui.registro.ServicioScreen
import cl.duoc.veterinaria.ui.screens.BienvenidaScreen
import cl.duoc.veterinaria.ui.screens.ListadoScreen
import cl.duoc.veterinaria.ui.screens.PedidoScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val viewModel: RegistroViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "bienvenida_screen",
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        composable("bienvenida_screen") {
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
                    // Acceso directo (demo) al flujo de pedidos pero dentro del contexto del viewmodel actual
                    // Nota: Si se entra directo, faltarán datos de dueño/mascota, pero sirve para probar la UI
                    navController.navigate("pedidos_screen") 
                }
            )
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
                // CAMBIO: Ahora vamos a la selección de medicamentos (Pedido) antes del resumen
                navController.navigate("pedidos_screen")
            })
        }
        composable("pedidos_screen") {
            PedidoScreen(
                viewModel = viewModel,
                onNextClicked = {
                    // CAMBIO: Al confirmar el pedido, vamos al resumen final
                    navController.navigate("resumen_screen")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("resumen_screen") {
            ResumenScreen(viewModel = viewModel, onConfirmClicked = {
                viewModel.clearData()
                navController.popBackStack("bienvenida_screen", inclusive = true)
                navController.navigate("bienvenida_screen")
            })
        }
        composable("listado_screen") {
            ListadoScreen(onBack = {
                navController.popBackStack()
            })
        }
    }
}
