package com.raysk.timetracker.data

import com.google.gson.annotations.SerializedName
import java.util.*



data class Horario(
    @SerializedName("fecha_fin")
    val fechaFin: Date,
    @SerializedName("fecha_inicio")
    val fechaInicio: Date,
    val id: Int,
    @SerializedName("id_usuario")
    val idUsuario: String,
    @SerializedName("materia")
    val idMateria: Int,
)