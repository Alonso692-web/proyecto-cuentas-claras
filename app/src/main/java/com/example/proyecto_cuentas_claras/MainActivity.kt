package com.example.proyecto_cuentas_claras

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_cuentas_claras.ui.model.Usuario
import com.example.proyecto_cuentas_claras.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var reference: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null

    private lateinit var idtvNombreNavHeader: TextView
    private lateinit var idtvCorreoNavHeader: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val navView: NavigationView = binding.navView

        // Recupera el encabezado del NavigationView
        val headerView = navView.getHeaderView(0)

        idtvNombreNavHeader = headerView.findViewById(R.id.idTvNombreNavHeader)
        idtvCorreoNavHeader = headerView.findViewById(R.id.idTvCorreoNavHeader)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Hola bienvenido", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        //val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference =
            FirebaseDatabase.getInstance().reference.child("Usuarios").child(firebaseUser!!.uid)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_inicio,
                R.id.nav_gastos_ingresos,
                R.id.nav_ahorros,
                R.id.nav_salud_financiera,
                R.id.nav_soporte
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        ObtenerDato()
    }

    fun ObtenerDato() {
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val usuario: Usuario? = snapshot.getValue(Usuario::class.java)
                    usuario?.let {
                        idtvNombreNavHeader.text = it.getNombre()
                        idtvCorreoNavHeader.text = it.getCorreo()
                        Log.w("TAG", "Nombre: ${it.getNombre()}\nCorreo: ${it.getCorreo()}")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Aquí puedes mostrar un diálogo o navegar a una nueva actividad
                mostrarInformacionApp()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun mostrarInformacionApp() {
        val creditos =
            "Desarrolladores:\n\nSalvador Trejo Martínez\nAlonso Domínguez López\nGuillermo Ordaz Rodríguez\nRaúl Cardoso Acevedo"
        AlertDialog.Builder(this)
            .setTitle("Cuentas claras")
            .setMessage(creditos + "\n\nTodos los derechos reservados.\n\nVersión 1.0")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}