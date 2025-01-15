import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_cuentas_claras.ui.salud_financiera.data.Noticia
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

class SaludFinancieraViewModel : ViewModel() {

    private val _noticias = MutableLiveData<List<Noticia>>()
    val noticias: LiveData<List<Noticia>> = _noticias

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        obtenerNoticias()
    }

    private fun obtenerNoticias() {
        uiScope.launch {
            try {
                val noticiasObtenidas = withContext(Dispatchers.IO) {
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("https://api.apitube.io/v1/news/everything?language.code=es&title=business&api_key=api_live_1dTRp7oUyO6fYS0TqBj5d7OvgHxPDn0hs2ygwAPHGwrDzS7&limit=50")
                        .get()
                        .build()

                    val response = client.newCall(request).execute()
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            val gson = Gson()
                            val jsonObject = gson.fromJson(responseBody, JsonObject::class.java)

                            // Primero accedemos al array "results"
                            val resultsArray = jsonObject.getAsJsonArray("results")
                            resultsArray?.mapNotNull { jsonElement ->
                                try {
                                    val item = jsonElement.asJsonObject
                                    Noticia(
                                        titulo = item.get("title")?.asString ?: "",
                                        descripcion = item.get("description")?.asString ?: "",
                                        href = item . get ("href")?.asString ?: ""
                                    )
                                } catch (e: Exception) {
                                    Log.w(
                                        "SaludFinanciera",
                                        "Error al parsear noticia: ${e.message}"
                                    )
                                    null
                                }
                            } ?: emptyList()
                        } else {
                            emptyList()
                        }
                    } else {
                        emptyList()
                    }
                }
                _noticias.value = noticiasObtenidas
                Log.w("SaludFinanciera", "Noticias obtenidas: ${noticiasObtenidas.size}")
            } catch (e: Exception) {
                Log.w("SaludFinanciera", "Error al obtener noticias: ${e.message}")
                e.printStackTrace()
                _noticias.value = emptyList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}