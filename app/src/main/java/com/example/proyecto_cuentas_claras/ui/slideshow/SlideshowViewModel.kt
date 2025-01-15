package com.example.proyecto_cuentas_claras.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideshowViewModel : ViewModel() {

    private val _totalSavings = MutableLiveData<Int>().apply { value = 0 }
    val totalSavings: LiveData<Int> = _totalSavings

    fun addSavings(amount: Int) {
        _totalSavings.value = (_totalSavings.value ?: 0) + amount
    }
}
