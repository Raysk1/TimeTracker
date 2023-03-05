package com.raysk.timetracker.calendario

import android.graphics.Color
import com.raysk.timetracker.data.Horario
import com.raysk.timetracker.data.Materia
import com.raysk.timetracker.data.Usuario
import java.util.*

class CalendarioEvent(val horario: Horario,val usuario: Usuario,val materia: Materia )

{

    var fechaFin: Calendar = Calendar.getInstance()
    var fechaInicio: Calendar = Calendar.getInstance()
    var color: Int = 0

    private val colores = arrayOf(
        "#0F4C81",
        "#6EA4BF",
        "#F6AE2D",
        "#F26419",
        "#D7263D",
        "#0EAD69",
        "#A7FF83",
        "#FFB6B9",
        "#C77DFF",
        "#8EA8C3",
        "#FF0000",
        "#03C03C",
        "#0000FF",
        "#008000",
        "#FFFF00",
        "#800080",
        "#00FFFF",
        "#FFA500",
        "#DC143C",
        "#00CED1",
        "#CD5C5C",
        "#9370DB",
        "#4169E1",
        "#32CD32",
        "#F0E68C",
        "#BA55D3",
        "#87CEEB",
        "#90EE90",
        "#FFFACD",
        "#DA70D6"
    )

    init {

        this.fechaInicio.time = horario.fechaInicio
        this.fechaFin.time = horario.fechaFin
        color = Color.parseColor(colores[materia.id])

    }
}
