package cl.duoc.veterinaria.ui.registro

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Componente reutilizable para campos de texto en el registro.
 *
 * @param value El valor actual del texto.
 * @param label La etiqueta a mostrar en el campo.
 * @param onValueChange La acción a ejecutar cuando el texto cambia.
 * @param keyboardOptions Opciones de teclado (por defecto vacío).
 */
@Composable
fun RegistroTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions
    )
}
