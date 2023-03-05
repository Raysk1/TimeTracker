package com.raysk.timetracker.calendario

import android.app.AlertDialog
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.TextView
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.raysk.timetracker.R
import com.raysk.timetracker.data.Carrera
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.glide.loadImage
import java.text.SimpleDateFormat
import java.util.*

class CalendarioAdapter : WeekView.PagingAdapter<CalendarioEvent>() {

    override fun onCreateEntity(item: CalendarioEvent): WeekViewEntity {
        val style = WeekViewEntity.Style.Builder().setBackgroundColor(item.color).build()

        return WeekViewEntity.Event.Builder(item)
            .setId(item.horario.id.toLong())
            .setTitle(item.materia.nombre)
            .setSubtitle("${item.usuario.nombre} ${item.usuario.apellidos}")
            .setStartTime(item.fechaInicio)
            .setEndTime(item.fechaFin)
            .setStyle(style)
            .build()
    }

    override fun onEventClick(data: CalendarioEvent, bounds: RectF) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_calendario_event_info, null)
        val infoDialog = AlertDialog.Builder(context)
            .setView(view)
            .create()


        val avatarView = view.findViewById<AvatarView>(R.id.avatarView)
        val url = "http://192.168.8.61/timetracker/api/img/usuario/${data.usuario.username}"
        avatarView.loadImage(
            data = url,
            requestBuilder = Glide.with(avatarView).asDrawable().centerCrop().override(100, 100)
                .fitCenter()
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {

                        avatarView.avatarInitials = "${data.usuario.nombre[0]}${data.usuario.apellidos[0]}"
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                       // avatarView.background = null
                        return false
                    }

                })
        )
        val tvHoraInicio = view.findViewById<TextView>(R.id.tvEventHoraInicio)
        val tvHoraFin = view.findViewById<TextView>(R.id.tvEventHoraFin)
        val tvNombreUsuario = view.findViewById<TextView>(R.id.tvEventNombreUsuario)
        val tvNombreMateria = view.findViewById<TextView>(R.id.tvEventNombreMateria)
        val tvGrupo = view.findViewById<TextView>(R.id.tvEventGrupo)
        val tvCarrera = view.findViewById<TextView>(R.id.tvEventCarrera)

        val format = SimpleDateFormat("HH:mm", Locale.ROOT)
        val horaInicio = format.format(data.fechaInicio.time)
        val horaFin = format.format(data.fechaFin.time)
        tvHoraInicio.setText(horaInicio)
        tvHoraFin.setText(horaFin)
        tvNombreMateria.setText(data.materia.nombre)
        tvNombreUsuario.setText("${data.usuario.nombre} ${data.usuario.apellidos}")
        tvGrupo.setText(data.materia.grupo.toString())
        tvCarrera.setText(Carrera.getCarreraShortName(data.materia.idCarrera))

        infoDialog.cancel()
        infoDialog.show()

    }

}