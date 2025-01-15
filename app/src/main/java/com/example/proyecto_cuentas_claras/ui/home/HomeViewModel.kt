package com.example.proyecto_cuentas_claras.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Bienvenido a Cuentas claras, tu app de gestión financiera."
    }
    val text: LiveData<String> = _text
}