# VeterinariaApp 🐾

Aplicación Android desarrollada en **Kotlin** utilizando **Jetpack Compose**, diseñada para la gestión de consultas veterinarias. Este proyecto forma parte de la evaluación **Sumativa 2** para la asignatura de Desarrollo de Apps Móviles I en DUOC.

El proyecto destaca por su arquitectura **modular**, separando claramente la lógica de negocio, la interfaz de usuario y las utilidades, integrando animaciones y componentes avanzados de Material Design 3.

## 📱 Características Principales

*   **Pantalla de Bienvenida Dinámica**:
    *   **Animaciones**: Entrada suave con `AnimatedVisibility` (FadeIn + Slide).
    *   **Menú de Navegación**: Cajón lateral (`Navigation Drawer`) funcional y Menú de Opciones (`DropdownMenu`).
    *   **Dashboard**: Resumen en tiempo real de mascotas, consultas y último cliente.
*   **Registro de Consultas (Flujo Completo)**:
    *   **Datos del Dueño**: Formulario validado.
    *   **Datos de la Mascota**: Selección de especie con lista desplegable (`ExposedDropdownMenu`).
    *   **Tipo de Servicio**: (Control, Vacuna, Urgencia, Otro) con lógica experta.
*   **Farmacia Veterinaria (Carrito de Compras)**:
    *   Selección de medicamentos con cálculo automático de costos.
    *   Aplicación de descuentos en productos promocionales.
*   **Resumen Final y Notificaciones**:
    *   **Indicadores de Progreso**: Feedback visual (`CircularProgressIndicator`) mientras se procesa la solicitud.
    *   **Confirmación**: Detalle completo de la consulta y el pedido antes de finalizar.
*   **Interfaz Moderna**: Implementada 100% con Jetpack Compose y Material Design 3.

## 🛠 Tecnología y Arquitectura

El proyecto sigue una arquitectura modular y utiliza las últimas tecnologías recomendadas por Google:

*   **Lenguaje**: [Kotlin](https://kotlinlang.org/)
*   **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
*   **Componentes Clave**:
    *   `AnimatedVisibility`: Para transiciones suaves.
    *   `DropdownMenu` / `ExposedDropdownMenuBox`: Para menús interactivos.
    *   `CircularProgressIndicator`: Para feedback de carga.
    *   `Scaffold` & `Navigation`: Para la estructura base y navegación.
*   **Gestión de Estado**: `ViewModel`, `StateFlow` y `Coroutines`.
*   **Build System**: Gradle Kotlin DSL (`.kts`).

### 📦 Estructura de Módulos

El proyecto está organizado en 4 módulos para asegurar la escalabilidad y mantenibilidad:

1.  **:app**: Módulo principal que actúa como punto de entrada (`MainActivity`). Orquesta la navegación y las dependencias.
2.  **:ui**: Contiene toda la interfaz de usuario (Screens, Components, Theme, Navigation y ViewModels).
3.  **:data**: Contiene la lógica de negocio, modelos de datos (`Mascota`, `Consulta`, `Dueno`, `Pedido`) y repositorios (`VeterinariaRepository`).
4.  **:util**: Funciones de utilidad y extensiones transversales (`InputUtils`, etc.).

## 🚀 Instalación y Ejecución

1.  Clonar el repositorio o descargar el proyecto.
2.  Abrir en **Android Studio** (Koala / Ladybug o superior recomendado).
3.  Esperar a que finalice la sincronización de Gradle.
4.  Seleccionar el módulo `app` y ejecutar en un emulador o dispositivo físico (Min SDK 24).

---
**Desarrollado por:** Liliana Tapia
**Asignatura:** Desarrollo de Apps Móviles I - DUOC UC
**Evaluación:** Sumativa 2 - Experiencia 2
