package com.example.proyecto_cuentas_claras.ui.slideshow

import SavingsEntry
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.example.proyecto_cuentas_claras.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.text.ParseException

class SlideshowFragment : Fragment() {
    private var totalSavings: Double = 0.0
    private var maxSavings: Double = 10000.0
    private val savingsHistory = mutableListOf<SavingsEntry>()
    private val currencyFormat = NumberFormat.getCurrencyInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private lateinit var savingsInput: EditText
    private lateinit var noteInput: EditText
    private lateinit var addButton: Button
    private lateinit var totalTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var lineChart: LineChart
    private lateinit var targetTextView: TextView
    private lateinit var remainingTextView: TextView
    private lateinit var editTargetButton: Button
    private lateinit var savingsListRecyclerView: RecyclerView
    private lateinit var savingsListAdapter: SavingsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        initializeViews(root)
        setupUI()
        return root
    }

    private fun initializeViews(root: View) {
        savingsInput = root.findViewById(R.id.savings_input)
        noteInput = root.findViewById(R.id.note_input)
        addButton = root.findViewById(R.id.add_button)
        totalTextView = root.findViewById(R.id.total_savings_text)
        progressBar = root.findViewById(R.id.savings_progress)
        lineChart = root.findViewById(R.id.savings_chart)
        targetTextView = root.findViewById(R.id.target_text)
        remainingTextView = root.findViewById(R.id.remaining_text)
        editTargetButton = root.findViewById(R.id.edit_target_button)
        savingsListRecyclerView = root.findViewById(R.id.savings_list_recycler_view)
        savingsListRecyclerView.layoutManager = LinearLayoutManager(context)
        savingsListAdapter = SavingsListAdapter(savingsHistory, this::onRemoveSavings)
        savingsListRecyclerView.adapter = savingsListAdapter
    }

    private fun setupUI() {
        progressBar.max = maxSavings.toInt()
        setupChart()
        updateUI()

        addButton.setOnClickListener {
            addSavings()
        }
        editTargetButton.setOnClickListener {
            showEditTargetDialog()
        }
    }

    private fun addSavings() {
        val amountText = savingsInput.text.toString()
        val note = noteInput.text.toString()

        try {
            val amount = amountText.toDouble()
            if (amount > 0) {
                addSavingsEntry(amount, note)
                savingsInput.text.clear()
                noteInput.text.clear()
            } else {
                showError("Por favor ingresa una cantidad mayor a 0")
            }
        } catch (e: NumberFormatException) {
            showError("Por favor ingresa una cantidad válida")
        }
    }

    private fun addSavingsEntry(amount: Double, note: String) {
        val entry = SavingsEntry(amount, System.currentTimeMillis(), note)
        savingsHistory.add(entry)
        totalSavings += amount
        updateUI()
        updateChart()
        savingsListAdapter.notifyDataSetChanged()
    }

    private fun setupChart() {
        with(lineChart) {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            axisRight.isEnabled = false
            xAxis.valueFormatter = DateAxisValueFormatter()
            legend.isEnabled = true
        }
    }

    private fun updateChart() {
        val entries = ArrayList<Entry>()
        var runningTotal = 0f

        savingsHistory.forEachIndexed { index, savingsEntry ->
            runningTotal += savingsEntry.amount.toFloat()
            entries.add(Entry(savingsEntry.date.toFloat(), runningTotal))
        }

        val dataSet = LineDataSet(entries, "Ahorro Total").apply {
            color = resources.getColor(R.color.purple_500)
            setCircleColor(resources.getColor(R.color.purple_700))
            valueTextSize = 10f
            lineWidth = 2f
        }

        lineChart.data = LineData(dataSet)
        lineChart.invalidate()
    }

    private fun updateUI() {
        totalTextView.text = "Total Ahorrado: ${currencyFormat.format(totalSavings)}"
        progressBar.max = maxSavings.toInt()
        progressBar.progress = totalSavings.toInt().coerceAtMost(maxSavings.toInt())

        val remaining = maxSavings - totalSavings
        targetTextView.text = "Meta: ${currencyFormat.format(maxSavings)}"
        remainingTextView.text = "Falta: ${currencyFormat.format(remaining)}"
    }

    private fun showError(message: String) {
        // Usamos requireContext() para obtener el contexto no nulo
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }

    }

    inner class DateAxisValueFormatter : com.github.mikephil.charting.formatter.ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return dateFormat.format(Date(value.toLong()))
        }
    }

    private fun onRemoveSavings(position: Int) {
        val removedSavings = savingsHistory.removeAt(position)
        totalSavings -= removedSavings.amount
        updateUI()
        updateChart()
        savingsListAdapter.notifyDataSetChanged()

    }

    private fun showEditTargetDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Editar Meta")
        val input = TextInputEditText(requireContext())
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        builder.setView(input)

        builder.setPositiveButton("Aceptar") { dialog, _ ->
            val newTargetString = input.text.toString()
            try {
                val newTarget = newTargetString.toDouble()
                if (newTarget > 0) {
                    maxSavings = newTarget
                    updateUI()
                } else {
                    showError("Ingrese una meta mayor a 0")
                }
            } catch (e: NumberFormatException) {
                showError("Ingrese una meta válida")
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }
}

class SavingsListAdapter(
    private val savingsList: List<SavingsEntry>,
    private val onRemove: (Int) -> Unit
) : RecyclerView.Adapter<SavingsListAdapter.SavingsViewHolder>() {

    inner class SavingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amountTextView: TextView = itemView.findViewById(R.id.savings_amount)
        val noteTextView: TextView = itemView.findViewById(R.id.savings_note)
        val dateTextView: TextView = itemView.findViewById(R.id.savings_date)
        val removeButton: Button = itemView.findViewById(R.id.remove_savings_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.savings_list_item, parent, false)
        return SavingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavingsViewHolder, position: Int) {
        val savings = savingsList[position]
        val currencyFormat = NumberFormat.getCurrencyInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        holder.amountTextView.text = currencyFormat.format(savings.amount)
        holder.noteTextView.text = savings.note
        holder.dateTextView.text = dateFormat.format(Date(savings.date))
        holder.removeButton.setOnClickListener {
            onRemove(position)
        }
    }

    override fun getItemCount() = savingsList.size
}