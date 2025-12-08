package cl.duoc.veterinaria.service

import cl.duoc.veterinaria.model.TipoServicio
import kotlin.math.round

/**
 * Servicio encargado de la lógica financiera y de costos de las consultas.
 */
object ConsultaService {
    
    /**
     * Calcula el costo base de una consulta según el tipo de servicio y la duración.
     */
    fun calcularCostoBase(tipo: TipoServicio, minutos: Int): Double {
        val base = when (tipo) {
            TipoServicio.CONTROL -> 15000.0
            TipoServicio.VACUNA -> 12000.0
            TipoServicio.URGENCIA -> 25000.0
            TipoServicio.OTRO -> 18000.0
        }
        val recargo = (minutos / 30) * 2000.0
        return base + recargo
    }

    /**
     * Aplica descuentos según la cantidad de mascotas o servicios.
     */
    fun aplicarDescuento(costo: Double, cantidad: Int): Pair<Double, Boolean> =
        if (cantidad > 1) costo * 0.85 to true else costo to false

    /**
     * Redondea el monto a un valor entero para CLP.
     */
    fun redondearClp(monto: Double) = round(monto)
}
