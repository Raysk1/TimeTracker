package com.raysk.timetracker.calendario

import android.app.AlertDialog
import android.graphics.RectF
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import com.raysk.timetracker.data.Materia
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CalendarioAdapter(
    private val calendarList: MutableList<CalendarioEvent>,
    private val materiasList: List<Materia>,
) :
    WeekView.PagingAdapter<CalendarioEvent>() {


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
        var infoDialog = AlertDialog.Builder(context).create()
        infoDialog = data.getInfoDialog(context, materiasList,
            listenerPositiveDelete = { _, _ ->
                CoroutineScope(Dispatchers.Main).launch {
                    if (data.deleteEvent(context)) {
                        calendarList.remove(data)
                        submitList(calendarList)
                        infoDialog.setCancelable(false)
                        infoDialog.dismiss()
                    }
                }

            },
            onEdit = {
                calendarList.remove(data)

                calendarList.add(it)

                submitList(calendarList)
            })
        infoDialog.show()


    }

    override fun onEmptyViewClick(time: Calendar) {
        val dialog = CalendarioFormDialog(context, time, "Nuevo evento", materiasList) {
            //onSave()
            calendarList.add(it)
            submitList(calendarList)
        }

        dialog.show()
    }

}