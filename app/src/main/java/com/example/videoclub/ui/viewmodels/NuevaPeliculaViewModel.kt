package com.example.videoclub.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.videoclub.ui.api.ApiService
import com.example.videoclub.ui.api.RetrofitInstance
import com.example.videoclub.ui.data.Pelicula
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevaPeliculaViewModel : ViewModel() {

    fun añadirPelicula(pelicula: Pelicula) {
        val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)
        apiService.addPelicula(pelicula).enqueue(object: Callback<Pelicula> {
            override fun onResponse(call: Call<Pelicula>, response: Response<Pelicula>) {
                if (response.isSuccessful) {
                   Log.d("Exito","La pelicula se ha creado correctamente.")
                }
            }

            override fun onFailure(call: Call<Pelicula>, t: Throwable) {
                Log.e("error","Ha habido un error.")
            }
        })
    }

    fun editarPelicula(pelicula: Pelicula) {
        val service = RetrofitInstance.retrofit.create(ApiService::class.java)
        val call = service.actualizarPelicula(pelicula.id ?: -1, pelicula)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("Exito","Pelicula modificada correctamente.")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Error","La pelicula no se ha podido modificar.")
            }

        })
    }
}

