# 🐾 VeterinariaApp - Semana 4: Diagnóstico y Optimización

## 📖 Descripción del Proyecto
**VeterinariaApp** es una solución móvil integral diseñada para la gestión de atenciones veterinarias y ventas de farmacia. Durante esta cuarta semana, el enfoque principal ha sido el **diagnóstico de errores, el manejo robusto de excepciones y la optimización del rendimiento** del flujo crítico de la aplicación.

---

## 📄 Documentación de la Actividad (Semana 4)
Puedes revisar el informe detallado con las evidencias de depuración, uso de Logcat, Debugger y Profiler en el siguiente enlace:

👉 **[Ver Informe de Diagnóstico y Optimización (PDF)](./Documentacio%CC%81n/Liliana_Tapia_Diagnosticando%20errores%20y%20optimizando%20el%20rendimiento_S4.pdf)**

*También puedes encontrar la documentación técnica general aquí:*
👉 **[Ver Informe de Documentación Técnica Básica (PDF)](./Documentacio%CC%81n/Informe%20documentaci%C3%B3n%20t%C3%A9cnica%20b%C3%A1sica.pdf)**

---

## 🛠️ Avances Semana 4: Diagnóstico y Calidad

### 1. Depuración Estratégica (Logcat)
- Se implementó un sistema de trazabilidad mediante logs en `RegistroViewModel`.
- Uso de niveles de prioridad (DEBUG, INFO, WARNING, ERROR) para monitorear eventos clave como el inicio del registro, la asignación de veterinarios y la persistencia de datos en Room.

### 2. Manejo Robustecido de Excepciones
- **Bloques Try-Catch:** Implementación estratégica en procesos asíncronos para capturar y gestionar fallos en el repositorio o la lógica de negocio.
- **UI de Contingencia:** Creación de estados de error específicos en la interfaz de usuario para proporcionar feedback útil al usuario y evitar cierres forzados de la aplicación.

### 3. Inspección Activa (Android Debugger)
- Uso de **Breakpoints** para la auditoría de datos en tiempo real.
- Inspección de variables de estado (`LoginUiState`) para garantizar la integridad de la información antes de procesos de autenticación y registro.

### 4. Monitoreo de Recursos (Android Profiler)
- Análisis de **CPU y Memoria** mediante *Live Telemetry*.
- Verificación del uso eficiente de hilos y corutinas, asegurando que el hilo principal (UI Thread) permanezca responsivo durante tareas de fondo pesadas.

---

## 🏗️ Pilares Tecnológicos y Arquitectura

### 1. Arquitectura y Patrones
- **MVVM (Model-View-ViewModel):** Separación clara entre la lógica de estado y la interfaz Compose.
- **StateFlow y Coroutines:** Manejo reactivo de estados con optimización de suspensión para tareas asíncronas.
- **Repository Pattern:** Abstracción unificada de fuentes de datos locales.

### 2. Componentes Nativos
- **Services (Foreground):** Feedback mediante notificaciones persistentes.
- **Broadcast Receivers:** Monitoreo global del estado de conectividad.
- **Room Persistence:** Persistencia robusta para Mascotas, Consultas y Pedidos.
- **Content Provider:** Acceso seguro a datos para aplicaciones externas.

---

## 📂 Estructura del Proyecto
```text
cl.duoc.veterinaria
├── data             # Repositorio y persistencia (Room / Entities)
├── model            # Entidades de dominio y modelos de datos
├── service          # Lógica de agenda, costos y NotificacionService
├── ui               # Componentes de interfaz (Compose)
│   ├── registro     # Flujo de agendamiento y pantallas de resumen
│   ├── viewmodel    # Lógica de estado y diagnóstico (Logcat/Debug)
│   └── theme        # Tematización adaptativa (Material Design 3)
└── util             # Validaciones (Regex) y funciones de utilidad
```

---
**Desarrollado por:** Liliana Tapia  
**Carrera:** Desarrollo de aplicaciones II
**Institución:** DUOC UC
**Semana:** 4 - Formativa Individual
