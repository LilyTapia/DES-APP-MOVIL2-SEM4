package cl.duoc.veterinaria.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.veterinaria.ui.auth.LoginScreen
import cl.duoc.veterinaria.ui.auth.LoginViewModel
import cl.duoc.veterinaria.ui.registro.DuenoScreen
import cl.duoc.veterinaria.ui.registro.MascotaScreen
import cl.duoc.veterinaria.ui.registro.ResumenScreen
import cl.duoc.veterinaria.ui.registro.ServicioScreen
import cl.duoc.veterinaria.ui.screens.AtencionesDuenoScreen
import cl.duoc.veterinaria.ui.screens.BienvenidaScreen
import cl.duoc.veterinaria.ui.screens.ListadoScreen
import cl.duoc.veterinaria.ui.screens.PedidoScreen
import cl.duoc.veterinaria.ui.viewmodel.ConsultaViewModel
import cl.duoc.veterinaria.ui.viewmodel.MainViewModel
import cl.duoc.veterinaria.ui.viewmodel.RegistroViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    
    // Instancias únicas compartidas en todo el grafo
    val loginViewModel: LoginViewModel = viewModel()
    val registroViewModel: RegistroViewModel = viewModel()
    
    val loginUiState by loginViewModel.uiState.collectAsState()

    if (!loginUiState.isLoggedIn) {
        // Pasamos el loginViewModel para que el login afecte al estado global
        LoginScreen(loginViewModel = loginViewModel, onLoginSuccess = { })
    } else {
        NavHost(
            navController = navController,
            startDestination = "bienvenida_screen",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            composable("bienvenida_screen") {
                val mainViewModel: MainViewModel = viewModel()
                BienvenidaScreen(
                    onNavigateToNext = { navController.navigate("dueno_screen") },
                    onNavigateToRegistro = { navController.navigate("dueno_screen") },
                    onNavigateToListado = { navController.navigate("listado_screen") },
                    onNavigateToPedidos = { navController.navigate("pedidos_screen") },
                    onNavigateToMisAtenciones = { navController.navigate("mis_atenciones_screen") },
                    viewModel = mainViewModel,
                    loginViewModel = loginViewModel // Pasamos la misma instancia
                )
            }
            composable("dueno_screen") {
                DuenoScreen(
                    viewModel = registroViewModel, 
                    loginViewModel = loginViewModel, // Pasamos la misma instancia para precargar datos
                    onNextClicked = { navController.navigate("mascota_screen") }
                )
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
                    onNextClicked = { navController.navigate("resumen_screen") },
                    onBack = { navController.popBackStack() }
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
                val consultaViewModel: ConsultaViewModel = viewModel()
                ListadoScreen(
                    onBack = { navController.popBackStack() },
                    onNavigateToRegistro = { navController.navigate("dueno_screen") },
                    viewModel = consultaViewModel
                )
            }
            composable("mis_atenciones_screen") {
                val consultaViewModel: ConsultaViewModel = viewModel()
                val currentUser = loginUiState.currentUser
                AtencionesDuenoScreen(
                    duenoNombre = currentUser?.nombreUsuario ?: "Invitado",
                    onBack = { navController.popBackStack() },
                    viewModel = consultaViewModel
                )
            }
        }
    }
}
