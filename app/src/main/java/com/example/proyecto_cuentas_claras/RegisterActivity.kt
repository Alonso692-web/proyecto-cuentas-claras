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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombreR: EditText
    private lateinit var etCorreoR: EditText
    private lateinit var etContraseñaR: EditText
    private lateinit var btnRegistrarR: Button
    private lateinit var btnRegresarR: Button

    private lateinit var auth: FirebaseAuth

    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        InicializarVariables()
        btnRegistrarR.setOnClickListener {
            ValidarDatos()
        }

        btnRegresarR.setOnClickListener {
            val intentRegresar = Intent(this, LoginActivity::class.java)
            startActivity(intentRegresar)
        }

    }

    private fun ValidarDatos() {
        val nombre: String = etNombreR.text.toString()
        val correo: String = etCorreoR.text.toString()
        val contraseña: String = etContraseñaR.text.toString()

        if (nombre.isEmpty()) {
            Toast.makeText(applicationContext, "Ingrese un nombre", Toast.LENGTH_SHORT).show()
        } else if (correo.isEmpty()) {
            Toast.makeText(applicationContext, "Ingrese un correo", Toast.LENGTH_SHORT).show()
        } else if (contraseña.isEmpty()) {
            Toast.makeText(applicationContext, "Ingrese una contraseña", Toast.LENGTH_SHORT).show()
        } else {
            RegistrarUsuario(correo, contraseña)
        }
    }

    private fun RegistrarUsuario(correo: String, contraseña: String) {
        auth.createUserWithEmailAndPassword(correo, contraseña)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var uid: String = ""
                    uid = auth.currentUser!!.uid
                    reference =
                        FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)

                    val hashmap = HashMap<String, Any>()
                    val hash_nombre: String = etNombreR.text.toString()
                    val hash_correo: String = etCorreoR.text.toString()

                    hashmap["uid"] = uid
                    hashmap["nombre"] = hash_nombre
                    hashmap["correo"] = hash_correo
                    hashmap["imagen"] = ""
                    hashmap["buscar"] = hash_nombre.lowercase()

                    reference.updateChildren(hashmap).addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            Toast.makeText(
                                applicationContext,
                                "Se ha registrado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(intent)
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Ha ocurrido un error", Toast.LENGTH_SHORT)
                        .show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun InicializarVariables() {
        etNombreR = findViewById(R.id.etNombreR)
        etCorreoR = findViewById(R.id.etCorreoR)
        etContraseñaR = findViewById(R.id.etContraseñaR)
        btnRegistrarR = findViewById(R.id.btnRegistrarseR)
        btnRegresarR = findViewById(R.id.btnRegresarR)
        auth = FirebaseAuth.getInstance()
    }

}