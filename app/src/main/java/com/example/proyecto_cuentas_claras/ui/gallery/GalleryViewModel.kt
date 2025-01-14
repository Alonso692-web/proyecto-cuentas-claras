package com.example.proyecto_cuentas_claras.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _gastos = MutableLiveData<List<Pair<String, Double>>>().apply {
        value = mutableListOf(
            "Compra de comida" to 150.0,
            "Pago de servicios" to 200.0,
            "Gasolina" to 50.0
        )
    }
    val gastos: LiveData<List<Pair<String, Double>>> = _gastos

    private val _ingresos = MutableLiveData<List<Pair<String, Double>>>().apply {
        value = mutableListOf(
            "Salario" to 3000.0,
            "Venta de productos" to 500.0
        )
    }
    val ingresos: LiveData<List<Pair<String, Double>>> = _ingresos

    fun agregarGasto(concepto: String, cantidad: Double) {
        val listaActual = _gastos.value.orEmpty().toMutableList()
        listaActual.add(concepto to cantidad)
        _gastos.value = listaActual
    }

    fun agregarIngreso(concepto: String, cantidad: Double) {
        val listaActual = _ingresos.value.orEmpty().toMutableList()
        listaActual.add(concepto to cantidad)
        _ingresos.value = listaActual
    }
}
