package com.raysk.timetracker.data


data class Usuario(
    val username: String,
    val password: String,
    val tipo: Int,
    val nombre: String,
    val apellidos: String,
) {
    companion object {
        val ADMIN = 0
        val USER = 1
        var actual: Usuario? = null
    }
}
