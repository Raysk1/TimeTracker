package com.raysk.timetracker.data.api


import com.raysk.timetracker.data.Horario
import com.raysk.timetracker.data.Materia
import com.raysk.timetracker.data.Usuario
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    // Método para crear un Usuario
    @POST("Usuario")
    fun createUsuario(@Body usuario: Usuario): Call<Usuario>

    // Método para obtener todas los Usuarios
    @GET("Usuario")
    fun getUsuarios(): Call<List<Usuario>>

    // Método para obtener un Usuario por su ID
    @GET("Usuario/{username}")
    fun getUsuario(@Path("username") username: String): Call<Usuario>

    // Método para actualizar un Usuario
    @PUT("Usuario")
    fun updateUsuario(@Body Usuario: Usuario): Call<Usuario>

    // Método para eliminar una Usuario
    @DELETE("Usuario/{username}")
    fun deleteUsuario(@Path("username") username: String): Call<Void>

    @POST("Horario")
    fun createHorario(@Body horario: Horario): Call<Horario>

    @GET("Horario")
    fun getHorarios(): Call<List<Horario>>

    @GET("Horario/{id}")
    fun getHorario(@Path("id") id: Int): Call<Horario>

    @PUT("Horario")
    fun updateHorario(@Body horario: Horario): Call<Horario>

    @DELETE("Horario/{id}")
    fun deleteHorario(@Path("id") id: Int): Call<Void>

    @POST("Materia")
    fun createMateria(@Body materia: Materia): Call<Materia>

    @GET("Materia")
    fun getMaterias(): Call<List<Materia>>

    @GET("Materia/{id}")
    fun getMateria(@Path("id") id: Int): Call<Materia>

    @PUT("Materia")
    fun updateMateria(@Body materia: Materia): Call<Materia>

    @DELETE("Materia/{id}")
    fun deleteMateria(@Path("id") id: Int): Call<Void>

    @POST("login")
    @FormUrlEncoded
    fun logIn(@Field("username") username: String, @Field("password") password: String) : Call<Usuario>
}