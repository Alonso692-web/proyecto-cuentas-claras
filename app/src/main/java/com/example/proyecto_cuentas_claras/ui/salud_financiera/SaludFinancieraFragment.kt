package com.example.proyecto_cuentas_claras.ui.salud_financiera

import NoticiasAdapter
import SaludFinancieraViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_cuentas_claras.R
import com.example.proyecto_cuentas_claras.databinding.FragmentSaludFinancieraBinding
import com.example.proyecto_cuentas_claras.ui.salud_financiera.data.Noticia


class SaludFinancieraFragment : Fragment() {
    private lateinit var binding: FragmentSaludFinancieraBinding
    private val viewModel: SaludFinancieraViewModel by viewModels()
    private lateinit var adapter: NoticiasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaludFinancieraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeNoticias()
    }

    private fun setupRecyclerView() {
        adapter = NoticiasAdapter { noticia ->
            // Aquí manejas el clic en cada noticia
            mostrarDetalleNoticia(noticia)
        }

        binding.recyclerViewNoticias.apply {
            adapter = this@SaludFinancieraFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun mostrarDetalleNoticia(noticia: Noticia) {
        AlertDialog.Builder(requireContext())
            .setTitle(noticia.titulo)
            .setMessage("${noticia.descripcion}\n\nEnlace: ${noticia.href}")
            .setPositiveButton("Abrir enlace") { dialog, _ ->
                // Abrir el enlace en el navegador
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(noticia.href))
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "No se pudo abrir el enlace", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cerrar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    /*
    private fun mostrarDetalleNoticia(noticia: Noticia) {
        // Por ejemplo, mostrar un diálogo con más información
        AlertDialog.Builder(requireContext())
            .setTitle(noticia.titulo)
            .setMessage(noticia.descripcion)
            .setPositiveButton("Cerrar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

        // O navegar a un nuevo fragmento/actividad de detalle
        // findNavController().navigate(
        //     SaludFinancieraFragmentDirections.actionToDetalleNoticia(noticia)
        // )
    }*/

    private fun observeNoticias() {
        viewModel.noticias.observe(viewLifecycleOwner) { noticias ->
            adapter.actualizarNoticias(noticias)
        }
    }
}

/*

class SaludFinancieraFragment : Fragment() {

    private val viewModel: SaludFinancieraViewModel by viewModels() // Delegado para instanciar el ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_salud_financiera, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewNoticias)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.noticias.observe(viewLifecycleOwner, Observer { noticias ->
            recyclerView.adapter = NoticiaAdapter(noticias)
        })

        return view
    }
}*/