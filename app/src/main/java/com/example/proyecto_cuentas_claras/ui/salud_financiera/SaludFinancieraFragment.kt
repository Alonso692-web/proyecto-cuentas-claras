package com.example.proyecto_cuentas_claras.ui.salud_financiera

import NoticiaAdapter
import SaludFinancieraViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_cuentas_claras.R

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
}