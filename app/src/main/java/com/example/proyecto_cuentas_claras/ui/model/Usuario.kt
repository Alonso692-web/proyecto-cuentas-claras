package com.example.proyecto_cuentas_claras.ui.model

class Usuario {
    private var uid: String = ""
    private var nombre: String = ""
    private var correo: String = ""
    private var imagen: String = ""
    private var buscar: String = ""


    constructor()

    constructor(
        uid: String,
        nombre: String,
        correo: String,
        imagen: String,
        buscar: String,
    ) {
        this.uid = uid
        this.nombre = nombre
        this.correo = correo
        this.imagen = imagen
        this.buscar = buscar
    }

    //getters y setters
    fun getUid(): String? {
        return uid
    }

    fun setUid(uid: String) {
        this.uid = uid
    }

    fun getNombre(): String? {
        return nombre
    }

    fun setNombre(n_usuario: String) {
        this.nombre = n_usuario
    }

    fun getCorreo(): String? {
        return correo
    }

    fun setCorreo(email: String) {
        this.correo = correo
    }

    fun getImagen(): String? {
        return imagen
    }

    fun setImagen(imagen: String) {
        this.imagen = imagen
    }

    fun getBuscar(): String? {
        return buscar
    }

    fun setBuscar(buscar: String) {
        this.buscar = buscar
    }

}