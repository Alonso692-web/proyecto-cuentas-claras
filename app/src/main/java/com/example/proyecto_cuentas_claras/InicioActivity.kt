package com.example.proyecto_cuentas_claras

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class InicioActivity : AppCompatActivity() {

    private lateinit var btn_registrarse : Button
    private lateinit var btn_logeo : Button

    //var firebaseUser: FirebaseUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        btn_registrarse = findViewById(R.id.btn_registrarse)
        btn_logeo = findViewById(R.id.btn_logeo)

        btn_registrarse.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_logeo.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    /*private fun ComprobarSesión(){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser!=null){
            val intent = Intent(this@InicioActivity, MainActivity::class.java)
            Toast.makeText(applicationContext, "La sesión está activa", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
    }*/

    /*override fun onStart() {
        ComprobarSesión()
        super.onStart()
    }*/

}