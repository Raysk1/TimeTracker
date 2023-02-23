package com.raysk.timetracker.data.api

import com.raysk.timetracker.data.Usuario
import retrofit2.HttpException


class Services {

    private val apiService = RetrofitClient.getInstance().getApi()

    @Throws(HttpException::class)
    fun getUsuarios(): List<Usuario> {
        val response = apiService.getUsuarios().execute()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            return body
        } else {
            throw HttpException(response)
        }

    }

    @Throws(HttpException::class)
    fun getUsuario(id: Int): Usuario {
        val response = apiService.getUsuario(id).execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            return body
        } else {
            throw HttpException(response)
        }
    }

    @Throws(HttpException::class)
    fun deteleUsuario(id: Int): Boolean {
        val response = apiService.deleteUsuario(id).execute()
        if (response.isSuccessful) {
            return true
        } else {
            throw HttpException(response)
        }
    }

    @Throws(HttpException::class)
    fun createUsuario(
        username: String,
        password: String,
        nombre: String,
        apellidos: String,
    ): Usuario? {
        val user = Usuario(username, password, 1, nombre, apellidos)
        val response = apiService.createUsuario(user).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }

    @Throws(HttpException::class)
    fun updateUsuario(
        username: String,
        password: String,
        nombre: String,
        apellidos: String,
    ): Boolean {
        val user = Usuario(username, password, 1, nombre, apellidos)
        val response = apiService.updateUsuario(user).execute()
        if (response.isSuccessful) {
            return true
        } else {
            throw HttpException(response)
        }
    }

    @Throws(HttpException::class)
    fun updateUsuario(user: Usuario): Boolean {
        val response = apiService.updateUsuario(user).execute()
        if (response.isSuccessful) {
            return true
        } else {
            throw HttpException(response)
        }
    }

    @Throws(HttpException::class)
    fun createUsuario(user: Usuario): Usuario? {
        val response = apiService.createUsuario(user).execute()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    }
}