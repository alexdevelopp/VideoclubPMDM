package com.example.videoclub.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.videoclub.ui.api.ApiService
import com.example.videoclub.ui.api.RetrofitInstance
import com.example.videoclub.ui.data.Pelicula

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeliculasViewModel : ViewModel() {

    private val _peliculasLiveData = MutableLiveData<List<Pelicula>?>()
    val peliculasLiveData: MutableLiveData<List<Pelicula>?> = _peliculasLiveData

    fun obtenerPeliculas() {
        val service = RetrofitInstance.retrofit.create(ApiService::class.java)
        val call = service.getPeliculas()

        call.enqueue(object : Callback<List<Pelicula>> {
            override fun onResponse(call: Call<List<Pelicula>>, response: Response<List<Pelicula>>) {
                if (response.isSuccessful) {
                    _peliculasLiveData.value = response.body()
                } else {
                    Log.e("error","Fallo")
                }
            }

            override fun onFailure(call: Call<List<Pelicula>>, t: Throwable) {

            }
        })
    }

    fun borrarPelicula (id: Int){
        val service = RetrofitInstance.retrofit.create(ApiService::class.java)
        val call = service.deletePelicula(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("API", "Se ha eliminado la pelicula correctamente.")
                val nuevaLista = peliculasLiveData.value?.toMutableList()
                nuevaLista?.removeAll { it.id == id }
                _peliculasLiveData.value = nuevaLista
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API","No se ha podido eliminar la pelicula: ${t.message}")
            }
        })
    }
}
