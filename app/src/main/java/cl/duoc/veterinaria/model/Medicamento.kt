package cl.duoc.veterinaria.model

/**
 * Clase base para un medicamento.
 * Principio SOLID (Open/Closed): Abierta a extensión (MedicamentoPromocional).
 */
open class Medicamento(
    val nombre: String,
    val dosisMg: Int,
    val precio: Double
)

/**
 * Medicamento con promoción aplicada.
 * Extiende la funcionalidad base sin modificar la clase padre.
 */
class MedicamentoPromocional(
    nombre: String,
    dosisMg: Int,
    precio: Double,
    private val descuento: Double // Porcentaje entre 0.0 y 1.0
) : Medicamento(nombre, dosisMg, precio) {
    
    fun precioConDescuento(): Double {
        return precio * (1 - descuento)
    }

    fun porcentajeDescuento(): Double = descuento
}

/**
 * Detalle de una línea de pedido.
 */
data class DetallePedido(
    val medicamento: Medicamento,
    val cantidad: Int
) {
    val subtotal: Double
        get() {
            val precioUnitario = if (medicamento is MedicamentoPromocional) 
                medicamento.precioConDescuento() 
            else 
                medicamento.precio
            return precioUnitario * cantidad
        }
}

/**
 * Representa un pedido de farmacia.
 */
data class Pedido(
    val cliente: Cliente,
    val detalles: List<DetallePedido>
) {
    val total: Double
        get() = detalles.sumOf { it.subtotal }
        
    fun totalSinPromocion(): Double = detalles.sumOf { it.medicamento.precio * it.cantidad }
}
