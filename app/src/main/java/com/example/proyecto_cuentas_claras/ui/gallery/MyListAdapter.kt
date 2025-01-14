package com.example.proyecto_cuentas_claras.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyListAdapter : RecyclerView.Adapter<MyListAdapter.MyViewHolder>() {

    private var items: List<Pair<String, Double>> = emptyList()

    fun submitList(newItems: List<Pair<String, Double>>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (concepto, cantidad) = items[position]
        holder.bind(concepto, cantidad)
    }

    override fun getItemCount(): Int = items.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textConcepto: TextView = itemView.findViewById(android.R.id.text1)
        private val textCantidad: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(concepto: String, cantidad: Double) {
            textConcepto.text = concepto
            textCantidad.text = "$${cantidad}"
        }
    }
}
