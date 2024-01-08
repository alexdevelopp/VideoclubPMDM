package com.example.videoclub.ui.api

import com.example.videoclub.ui.data.Pelicula
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("api/peliculas")
    fun getPeliculas(): Call<List<Pelicula>>

    @POST("api/peliculas")
    fun addPelicula(@Body pelicula: Pelicula): Call<Pelicula>

    @DELETE("api/peliculas/{id}")
    fun deletePelicula(@Path("id") id: Int): Call<Void>

    @PUT("api/peliculas/{id}")
    fun actualizarPelicula(
        @Path("id") id: Int,
        @Body pelicula: Pelicula
    ): Call<Void>

}
