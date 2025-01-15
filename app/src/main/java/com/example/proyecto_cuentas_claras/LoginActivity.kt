package com.example.proyecto_cuentas_claras

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var etCorreoIS: EditText
    private lateinit var etContraseñaIS: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegresarIS: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        InicializarVariables()

        btnIniciarSesion.setOnClickListener {
            ValidarDatos()
        }

        btnRegresarIS.setOnClickListener {
            val intentRegresarIS = Intent(this, InicioActivity::class.java)
            startActivity(intentRegresarIS)
        }

    }

    private fun InicializarVariables() {
        etCorreoIS = findViewById(R.id.etCorreoIS)
        etContraseñaIS = findViewById(R.id.etContraseñaIS)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesionAct)
        btnRegresarIS = findViewById(R.id.btnRegresarIS)
        auth = FirebaseAuth.getInstance()
    }

    private fun ValidarDatos() {
        val correoIS: String = etCorreoIS.text.toString()
        val contraseñaIS: String = etContraseñaIS.text.toString()

        if (correoIS.isEmpty()) {
            Toast.makeText(this@LoginActivity, "Ingrese su correo electrónico", Toast.LENGTH_SHORT)
                .show()
        }
        if (contraseñaIS.isEmpty()) {
            Toast.makeText(this@LoginActivity, "Ingrese su contraseña", Toast.LENGTH_SHORT).show()
        } else {
            LoginUsuario(correoIS, contraseñaIS)
        }
    }

    private fun LoginUsuario(correoIS: String, contraseñaIS: String) {
        auth.signInWithEmailAndPassword(correoIS, contraseñaIS)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intentIS = Intent(this@LoginActivity, MainActivity::class.java)
                    Toast.makeText(this@LoginActivity, "Ha iniciado sesión", Toast.LENGTH_SHORT)
                        .show()
                    val nombre = auth.currentUser?.displayName
                    startActivity(intentIS)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Ha ocurrido un error", Toast.LENGTH_SHORT)
                        .show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}