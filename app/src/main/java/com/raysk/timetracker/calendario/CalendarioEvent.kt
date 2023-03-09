package com.raysk.timetracker.calendario

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.raysk.timetracker.R
import com.raysk.timetracker.data.Carrera
import com.raysk.timetracker.data.Horario
import com.raysk.timetracker.data.Materia
import com.raysk.timetracker.data.Usuario
import com.raysk.timetracker.data.api.Services
import es.dmoral.toasty.Toasty
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.text.SimpleDateFormat
import java.util.*

class CalendarioEvent(val horario: Horario, val usuario: Usuario, val materia: Materia) {

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

    suspend fun deleteEvent(context: Context): Boolean {

        try {
            withContext(Dispatchers.IO) {
                val service = Services()
                service.deleteHorario(horario.id)
            }


            Toasty.success(context, "Se elimino correctamente", Toasty.LENGTH_SHORT).show()
            return true

        } catch (e: HttpException) {
            Toasty.error(context, e.message(), Toasty.LENGTH_SHORT).show()
            return false
        } catch (e: SocketTimeoutException) {
            Toasty.error(context, "Tiempo de espera agotado", Toasty.LENGTH_SHORT).show()
            return false
        }

    }

    fun getInfoDialog(
        context: Context,
        listenerPositiveDelete: DialogInterface.OnClickListener? = null,
        listenerNegativeDelete: DialogInterface.OnClickListener? = null,
        listenerEdit: View.OnClickListener? = null
    ) : AlertDialog{
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_calendario_event_info, null)
        val infoDialog = AlertDialog.Builder(context)
            .setView(view)
            .create()


        val avatarView = view.findViewById<AvatarView>(R.id.avatarView)
        val url = "http://192.168.31.117/timetracker/api/img/usuario/${usuario.username}"
        avatarView.loadImage(data = url,
            onError = { _, _ ->
                avatarView.avatarInitials = "${usuario.nombre[0]}${usuario.apellidos[0]}"
            })
        val tvHoraInicio = view.findViewById<TextView>(R.id.tvEventHoraInicio)
        val tvHoraFin = view.findViewById<TextView>(R.id.tvEventHoraFin)
        val tvNombreUsuario = view.findViewById<TextView>(R.id.tvEventNombreUsuario)
        val tvNombreMateria = view.findViewById<TextView>(R.id.tvEventNombreMateria)
        val tvGrupo = view.findViewById<TextView>(R.id.tvEventGrupo)
        val tvCarrera = view.findViewById<TextView>(R.id.tvEventCarrera)
        val btnEliminar = view.findViewById<MaterialButton>(R.id.btnEventEliminar)
        val btnEditar = view.findViewById<MaterialButton>(R.id.btnEventEditar)


        btnEliminar.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setTitle("Confirmar")
                .setMessage("¿Desea eliminar este evento?")
                .setPositiveButton("Si",listenerPositiveDelete)
                .setNegativeButton("No",listenerNegativeDelete)
                .create()
                .show()
        }

        btnEditar.setOnClickListener(listenerEdit)

        val format = SimpleDateFormat("HH:mm", Locale.ROOT)
        val horaInicio = format.format(fechaInicio.time)
        val horaFin = format.format(fechaFin.time)
        tvHoraInicio.text = horaInicio
        tvHoraFin.text = horaFin
        tvNombreMateria.text = materia.nombre
        tvNombreUsuario.text = "${usuario.nombre} ${usuario.apellidos}"
        tvGrupo.text = materia.grupo.toString()
        tvCarrera.text = Carrera.getCarreraShortName(materia.idCarrera)

        return infoDialog


    }
}
