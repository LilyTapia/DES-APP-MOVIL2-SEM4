# VeterinariaApp - Refactorización MVVM (Semana 7)

## Objetivo General

Este proyecto es el resultado de la reestructuración de la aplicación "VeterinariaApp" para adoptar el patrón arquitectónico **Model-View-ViewModel (MVVM)**. El objetivo principal fue mejorar la modularidad, mantenibilidad y escalabilidad del código, aplicando las buenas prácticas de desarrollo de software **SOLID** y **KISS**.

---

## 1. Patrón Arquitectónico MVVM

La aplicación se ha organizado siguiendo una estricta separación de responsabilidades, donde cada componente tiene un rol bien definido:

### a. Model (Modelo)

Esta capa es responsable de toda la lógica de negocio y el acceso a los datos. Está compuesta por:

-   **Paquete `model`**: Contiene las clases de datos (`data class`) como `Mascota`, `Dueno`, `Consulta`, `Pedido`, etc. Son estructuras de datos simples que representan las entidades del negocio.
-   **Paquete `service`**: Agrupa objetos Singleton (`object`) que encapsulan reglas de negocio específicas, aplicando el Principio de Responsabilidad Única (SRP).
    -   `AgendaVeterinario`: Lógica para agendar citas.
    -   `ConsultaService`: Lógica para calcular costos.
    -   `MascotaService`: Lógica relacionada con la salud de la mascota (ej. vacunación).
-   **Paquete `data`**: Contiene el Repositorio.
    -   `IVeterinariaRepository`: Una **interfaz** que define el contrato para el acceso a datos, aplicando el Principio de Inversión de Dependencias (DIP).
    -   `VeterinariaRepository`: La implementación Singleton que gestiona los datos en memoria y expone la información a través de `StateFlow`.

### b. View (Vista)

La capa de la Vista es la única con la que el usuario interactúa directamente. Está implementada 100% con **Jetpack Compose**.

-   **Paquete `ui/screens` y `ui/registro`**: Contienen los `Composables` que definen las pantallas de la aplicación (`BienvenidaScreen`, `DuenoScreen`, etc.).
-   **Responsabilidad**: Su única función es mostrar el estado que reciben del `ViewModel` y notificar las acciones del usuario (como clics en botones). No contienen ninguna lógica de negocio, siguiendo el principio KISS.

### c. ViewModel (Vista-Modelo)

Es el intermediario entre el Modelo y la Vista. Su función es preparar y gestionar los datos para la UI.

-   **Paquete `ui/viewmodel`**: Contiene las clases que heredan de `ViewModel`.
    -   `MainViewModel`: Gestiona los datos de la pantalla de bienvenida.
    -   `RegistroViewModel`: Mantiene el estado del formulario de registro de varios pasos.
    -   `ConsultaViewModel`: Provee la lista de pacientes/consultas para la pantalla de listado.
-   **Comunicación**: Se comunica con la capa del Modelo (a través de la interfaz del repositorio) y expone los datos a la Vista mediante `StateFlow`, permitiendo que la UI se actualice de forma reactiva y eficiente.

---

## 2. Principios SOLID y KISS

-   **Single Responsibility Principle (SRP)**: Cada clase tiene una única razón para cambiar. Los servicios (`ConsultaService`, `MascotaService`) son un claro ejemplo, al igual que la separación entre `ViewModel`, `Repository` y `Composable`.
-   **Open/Closed Principle (OCP)**: La clase `Medicamento` está abierta a extensión (con `MedicamentoPromocional`) pero cerrada a modificación.
-   **Dependency Inversion Principle (DIP)**: Los `ViewModels` dependen de la abstracción `IVeterinariaRepository`, no de la implementación concreta `VeterinariaRepository`.
-   **Keep It Simple, Stupid (KISS)**: Las Vistas son muy simples, sin lógica compleja. Las funciones en los servicios y ViewModels son directas y fáciles de entender.

---

## 3. Estructura del Proyecto

El proyecto se ha consolidado en un único módulo `:app` para simplificar la estructura, como se solicitó en las instrucciones.

```
app/
└── src/
    └── main/
        └── java/
            └── cl/
                └── duoc/
                    └── veterinaria/
                        ├── MainActivity.kt
                        ├── data/
                        │   └── VeterinariaRepository.kt  (con su interfaz)
                        ├── model/
                        │   ├── Consulta.kt
                        │   ├── Mascota.kt
                        │   └── ... (otras entidades)
                        ├── service/
                        │   └── Services.kt
                        └── ui/
                            ├── navigation/
                            │   └── NavGraph.kt
                            ├── registro/
                            │   ├── DuenoScreen.kt
                            │   └── ... (otras pantallas de registro)
                            ├── screens/
                            │   ├── BienvenidaScreen.kt
                            │   └── ListadoScreen.kt
                            ├── theme/
                            │   └── Theme.kt
                            └── viewmodel/
                                ├── MainViewModel.kt
                                ├── RegistroViewModel.kt
                                └── ConsultaViewModel.kt
```

---

## 4. Cómo ejecutar

1.  Clonar el repositorio.
2.  Abrir el proyecto con Android Studio.
3.  Permitir que Gradle sincronice las dependencias.
4.  Ejecutar la aplicación en un emulador o dispositivo físico.

---

**Autor:**
Liliana Tapia