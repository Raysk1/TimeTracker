package com.raysk.timetracker.calendario

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.raysk.timetracker.R
import com.raysk.timetracker.data.Horario
import com.raysk.timetracker.data.Materia
import com.raysk.timetracker.data.Usuario
import com.raysk.timetracker.data.api.Services
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarioFormDialog(
    context: Context,
    private val title: String,
    private val calendarioEvent: CalendarioEvent? = null,
    private val time: Calendar? = null,
    private val materiasList: List<Materia>,
    private val onSave: (calendarioEvent: CalendarioEvent) -> Unit,
) : AlertDialog(context) {
    private lateinit var tvTitulo: TextView
    private lateinit var tfFecha: TextInputLayout
    private lateinit var tfhoraInicio: TextInputLayout
    private lateinit var tfHoraFin: TextInputLayout
    private lateinit var tfMaterias: TextInputLayout
    private lateinit var btnGuardar: MaterialButton
    private lateinit var btnCerrar: ImageButton
    private lateinit var materia: Materia
    private lateinit var tfMateriasAcTv: AutoCompleteTextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        //super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_calendario_form)
        //val dialog = AlertDialog.Builder(this).setView(view).create()
        tvTitulo = findViewById(R.id.tvTitle)
        tfFecha = findViewById(R.id.tfFecha)
        tfhoraInicio = findViewById(R.id.tfHoraInicio)
        tfHoraFin = findViewById(R.id.tfHoraFin)
        tfMaterias = findViewById(R.id.tfMaterias)
        btnCerrar = findViewById(R.id.imgBtnCerrar)
        btnGuardar = findViewById(R.id.btnGuardar)
        tfMateriasAcTv = (tfMaterias.editText as AutoCompleteTextView?)!!

        tvTitulo.text = title

        var c = Calendar.getInstance()
        val hourEnd: Int
        val hourStart: Int


        //Configurando datos de la UI
        if (calendarioEvent != null) {
            c.time = calendarioEvent.horario.fechaInicio
            hourStart = c.get(Calendar.HOUR_OF_DAY)
            c.time = calendarioEvent.horario.fechaFin
            hourEnd = c.get(Calendar.HOUR_OF_DAY)
        } else if (time != null) {
            c = time
            hourStart = time.get(Calendar.HOUR_OF_DAY)
            hourEnd = hourStart + 1
        } else {
            hourStart = c.get(Calendar.HOUR_OF_DAY)
            hourEnd = hourStart + 1
        }
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val fecha = "${"%02d".format(day)}/${"%02d".format(month + 1)}/$year"
        tfFecha.editText?.setText(fecha)
        tfhoraInicio.editText?.setText("${"%02d".format(hourStart)}:00")
        tfHoraFin.editText?.setText("${"%02d".format(hourEnd)}:00")

        val nombresMaterias = materiasList.map { it.nombre }
        val adapter = ArrayAdapter(context, R.layout.list_item, nombresMaterias)

        tfMateriasAcTv.setAdapter(adapter)
        tfMateriasAcTv.setOnItemClickListener { _, _, pos, _ ->
            materia = materiasList[pos]
        }



        btnGuardar.setOnClickListener {

            //obteniendo datos de la fecha
            var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val fecha = LocalDate.parse(tfFecha.editText!!.text, formatter)
            formatter = DateTimeFormatter.ofPattern("HH:mm")
            val horaInicio = LocalTime.parse(tfhoraInicio.editText!!.text, formatter)
            val horaFin = LocalTime.parse(tfHoraFin.editText!!.text, formatter)
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, fecha.year)
            calendar.set(Calendar.MONTH, fecha.monthValue - 1)
            calendar.set(Calendar.DAY_OF_MONTH, fecha.dayOfMonth)
            calendar.set(Calendar.HOUR_OF_DAY, horaFin.hour)
            calendar.set(Calendar.MINUTE, horaFin.minute)
            calendar.set(Calendar.SECOND, 0)
            val fechaFin = calendar.time
            calendar.set(Calendar.HOUR_OF_DAY, horaInicio.hour)
            calendar.set(Calendar.MINUTE, horaInicio.minute)
            val fechaInicio = calendar.time

            if (!comprobarCampos(fechaInicio, fechaFin, tfMateriasAcTv)) {
                return@setOnClickListener
            }

            val services = Services()

            CoroutineScope(Dispatchers.Main).launch {

                try {
                    var message: String
                    setCancelable(false)
                    var hor: Horario
                    //TODO: una vez hecho el inicio de sesion poner el id del usuario
                    val user = Usuario("lolps0", "22", Usuario.USER, "goku", "abeles")
                    withContext(Dispatchers.IO) {

                        if (calendarioEvent == null) {
                            hor =
                                services.createHorario(
                                    fechaInicio,
                                    fechaFin,
                                    user.username,
                                    materia.id
                                )!!
                            message = "Evento creado exitosamente"
                        } else {
                            hor = services.updateHorario(
                                calendarioEvent.horario.id,
                                fechaInicio,
                                fechaFin,
                                user.username,
                                materia.id
                            )!!

                            message = "Evento actualizado correctamente"
                        }
                    }


                    val calEv = CalendarioEvent(hor, user, materia)

                    Toasty.success(context, message, Toasty.LENGTH_SHORT).show()
                    setCancelable(true)
                    onSave(calEv)
                } catch (e: HttpException) {
                    Toasty.error(context, e.message(), Toast.LENGTH_SHORT).show()
                    setCancelable(true)
                } catch (e: SocketTimeoutException) {
                    Toasty.error(context, "Tiempo de espera agotado", Toasty.LENGTH_SHORT).show()
                    setCancelable(true)
                }

            }

        }

        tfFecha.setStartIconOnClickListener {
            tfFecha.error = null
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, monthOfYear, dayOfMonth ->
                    val dat = "${"%02d".format(dayOfMonth)}/${"%02d".format(monthOfYear + 1)}/$year"
                    tfFecha.editText!!.setText(dat)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

        tfhoraInicio.setStartIconOnClickListener {
            tfhoraInicio.error = null
            val timePickerDialog = TimePickerDialog(
                context,
                { _, hour, minute ->
                    val dat = "${"%02d".format(hour)}:${"%02d".format(minute)}"
                    tfhoraInicio.editText!!.setText(dat)
                },
                hourStart,
                0,
                true
            )

            timePickerDialog.show()

        }

        tfHoraFin.setStartIconOnClickListener {
            tfHoraFin.error = null
            val timePickerDialog = TimePickerDialog(
                context,
                { _, hour, minute ->
                    val dat = "${"%02d".format(hour)}:${"%02d".format(minute)}"
                    tfHoraFin.editText!!.setText(dat)
                },
                hourEnd,
                0,
                true
            )

            timePickerDialog.show()
        }

        tfMateriasAcTv.setOnClickListener {
            tfMaterias.error = null
        }

        btnCerrar.setOnClickListener { dismiss() }

    }

    private fun comprobarCampos(
        fechaInicio: Date,
        fechaFin: Date,
        tfMateriasAcTv: AutoCompleteTextView,
    ): Boolean {
        var comprobado = true
        if (tfMateriasAcTv.text.isEmpty()) {
            tfMaterias.error = "Seleccione una opcion"
            comprobado = false
        }
        if (fechaInicio.time > fechaFin.time) {
            tfhoraInicio.error = "La hora de inicio no puede ser menor á la hora de fin"
            comprobado = false
        } else if (fechaInicio.time == fechaFin.time) {
            tfHoraFin.error = "Las horas de inicio y fin deben ser diferentes"
            comprobado = false
        }
        return comprobado
    }
}

