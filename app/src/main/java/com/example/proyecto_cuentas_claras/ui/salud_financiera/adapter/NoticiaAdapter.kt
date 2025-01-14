import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView // Importante
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_cuentas_claras.R
import com.example.proyecto_cuentas_claras.databinding.ItemNoticiaBinding
import com.example.proyecto_cuentas_claras.ui.salud_financiera.data.Noticia

class NoticiasAdapter(
    private val onNoticiaClick: (Noticia) -> Unit
) : RecyclerView.Adapter<NoticiasAdapter.NoticiaViewHolder>() {
    private var noticias: List<Noticia> = emptyList()

    class NoticiaViewHolder(
        val binding: ItemNoticiaBinding,
        val onNoticiaClick: (Noticia) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(noticia: Noticia) {
            binding.apply {
                tituloNoticia.text = noticia.titulo
                descripcionNoticia.text = noticia.descripcion

                // Configurar el click en todo el item
                root.setOnClickListener {
                    onNoticiaClick(noticia)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val binding = ItemNoticiaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoticiaViewHolder(binding, onNoticiaClick)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        holder.bind(noticias[position])
    }

    override fun getItemCount() = noticias.size

    fun actualizarNoticias(nuevasNoticias: List<Noticia>) {
        noticias = nuevasNoticias
        notifyDataSetChanged()
    }
}
/*
class NoticiaAdapter(private val listaNoticias: List<Noticia>) :
    RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder>() {

    class NoticiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloTextView: TextView = itemView.findViewById(R.id.titulo_noticia)
        val descripcionTextView: TextView = itemView.findViewById(R.id.descripcion_noticia)
        //val imagenNoticia: ImageView = itemView.findViewById(R.id.imagen_noticia) // Nuevo
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticiaActual = listaNoticias[position]
        holder.tituloTextView.text = noticiaActual.titulo
        holder.descripcionTextView.text = noticiaActual.descripcion

        // Cargar imagen con Glide (si existe una URL)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(itemView)
    }

    override fun getItemCount() = listaNoticias.size
}*/