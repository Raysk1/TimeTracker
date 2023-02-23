package com.raysk.timetracker.data.api


import com.raysk.timetracker.data.Usuario
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    // Método para crear un Usuario
    @POST("Usuario")
    @Headers("Accept: application/x-www-form-urlencoded")
    fun createUsuario(@Body usuario: Usuario): Call<Usuario>

    // Método para obtener todas los Usuarios
    @GET("Usuario")
    fun getUsuarios(): Call<List<Usuario>>

    // Método para obtener un Usuario por su ID
    @GET("Usuario/{id}")
    fun getUsuario(@Path("id") id: Int): Call<Usuario>

    // Método para actualizar un Usuario
    @PUT("Usuario")
    fun updateUsuario(@Body Usuario: Usuario): Call<Void>

    // Método para eliminar una Usuario
    @DELETE("Usuario/{id}")
    fun deleteUsuario(@Path("id") id: Int): Call<Void>
}