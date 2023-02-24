package com.raysk.timetracker.data

import com.google.gson.annotations.SerializedName

data class Materia(
    val id: Int,
    val nombre: String,
    @SerializedName("carrera")
    val idCarrera: Int,
    val grupo: Int,
)