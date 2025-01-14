package com.example.proyecto_cuentas_claras.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Pantalla Inicio"
    }
    val text: LiveData<String> = _text
}