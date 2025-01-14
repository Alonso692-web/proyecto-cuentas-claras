package com.example.proyecto_cuentas_claras.ui.gallery

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_cuentas_claras.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar RecyclerView de gastos
        val gastosAdapter = MyListAdapter()
        binding.recyclerGastos.adapter = gastosAdapter
        binding.recyclerGastos.layoutManager = LinearLayoutManager(context)

        // Configurar RecyclerView de ingresos
        val ingresosAdapter = MyListAdapter()
        binding.recyclerIngresos.adapter = ingresosAdapter
        binding.recyclerIngresos.layoutManager = LinearLayoutManager(context)

        // Observa datos desde ViewModel
        galleryViewModel.gastos.observe(viewLifecycleOwner) {
            gastosAdapter.submitList(it)
        }

        galleryViewModel.ingresos.observe(viewLifecycleOwner) {
            ingresosAdapter.submitList(it)
        }

        // Configurar botones
        binding.btnAgregarGastos.setOnClickListener {
            mostrarDialogo("Agregar Gasto") { concepto, cantidad ->
                galleryViewModel.agregarGasto(concepto, cantidad)
            }
        }

        binding.btnAgregarIngresos.setOnClickListener {
            mostrarDialogo("Agregar Ingreso") { concepto, cantidad ->
                galleryViewModel.agregarIngreso(concepto, cantidad)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mostrarDialogo(titulo: String, onConfirm: (String, Double) -> Unit) {
        // Crear los campos de entrada
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 16, 32, 16)
        }

        val inputConcepto = EditText(context).apply {
            hint = "Concepto"
        }

        val inputCantidad = EditText(context).apply {
            hint = "Cantidad"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        layout.addView(inputConcepto)
        layout.addView(inputCantidad)

        // Mostrar el diálogo
        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(layout)
            .setPositiveButton("Agregar") { _, _ ->
                val concepto = inputConcepto.text.toString()
                val cantidad = inputCantidad.text.toString().toDoubleOrNull()
                if (concepto.isNotBlank() && cantidad != null) {
                    onConfirm(concepto, cantidad)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
