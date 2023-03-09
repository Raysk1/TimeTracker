package com.raysk.timetracker.calendario

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.jsr310.scrollToDateTime
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.raysk.timetracker.R
import com.raysk.timetracker.data.Materia
import com.raysk.timetracker.data.Usuario
import com.raysk.timetracker.data.api.Services
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.time.LocalDateTime


class CalendarioActivity : AppCompatActivity() {
    private lateinit var adapter: CalendarioAdapter
    private val services = Services()
    private lateinit var weekView: WeekView
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var dialogCargando: AlertDialog
    private lateinit var progressBar: ProgressBar
    private lateinit var tvMensaje: TextView
    private var materiaList: List<Materia>? = null
    private lateinit var calendarioEventList: MutableList<CalendarioEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)
        weekView = findViewById(R.id.weekView)


        configurarToolBar()
        configurarPantallaDeCarga()
        scope.launch { procesarHorarios() }
        configurarFAB()

    }

    @SuppressLint("SetTextI18n")
    private fun configurarFAB() {
        val fab: FloatingActionButton = findViewById(R.id.fabCalendario)

        fab.setOnClickListener {
            var dialog: CalendarioFormDialog? = null
            dialog = CalendarioFormDialog(this,
                "Nuevo Evento",
                materiasList = materiaList!!,
                onSave = {
                    calendarioEventList.add(it)
                    adapter.submitList(calendarioEventList)
                    dialog?.dismiss()
                })

            dialog.setOnShowListener {
                fab.hide()
            }

            dialog.setOnDismissListener {
                fab.show()
            }

            dialog.show()

        }

    }

    private fun configurarToolBar() {
        val toolBar: MaterialToolbar = findViewById(R.id.toolBarCalendarioForm)

        toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_today -> {
                    weekView.scrollToDateTime(dateTime = LocalDateTime.now())
                    true
                }
                R.id.action_day_view -> {
                    weekView.numberOfVisibleDays = 1
                    it.isChecked = true
                    true
                }
                R.id.action_three_day_view -> {
                    weekView.numberOfVisibleDays = 3
                    it.isChecked = true
                    true
                }
                R.id.action_week_view -> {
                    weekView.numberOfVisibleDays = 7
                    it.isChecked = true
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun procesarHorarios() {
        withContext(Dispatchers.IO) {
            try {
                var horarioList = services.getHorarios()
                materiaList = services.getMaterias()
                val userList = mutableMapOf<String, Usuario>()


                if (horarioList == null || materiaList == null) {
                    return@withContext
                }

                horarioList = horarioList.reversed()
                val calendarioEventList = mutableListOf<CalendarioEvent>()
                delay(2000)
                withContext(Dispatchers.Main) {
                    progressBar.isIndeterminate = false
                    progressBar.max = horarioList.size
                }

                horarioList.forEachIndexed { index, it ->


                    val usuario: Usuario = if (userList.containsKey(it.idUsuario)) {
                        userList[it.idUsuario]!!
                    } else {
                        val usuario = services.getUsuario(it.idUsuario)!!
                        userList[it.idUsuario] = usuario
                        usuario
                    }


                    val materia = materiaList!!.find { materia -> materia.id == it.idMateria }

                    val event = CalendarioEvent(it, usuario, materia!!)

                    calendarioEventList.add(event)

                    withContext(Dispatchers.Main) {
                        tvMensaje.text = "Cargando ${index + 1} de ${horarioList.size}"
                        progressBar.progress = index + 1
                    }
                }

                adapter = CalendarioAdapter(calendarioEventList,materiaList!!)
                weekView.adapter = adapter
                this@CalendarioActivity.calendarioEventList = calendarioEventList
                adapter.submitList(calendarioEventList)

                withContext(Dispatchers.Main) {
                    tvMensaje.text = "Compleatdo con exito"
                }
                delay(2000)
                dialogCargando.dismiss()

            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toasty.error(this@CalendarioActivity, e.message(), Toast.LENGTH_SHORT).show()
                    dialogCargando.dismiss()
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    Toasty.error(
                        this@CalendarioActivity,
                        "Tiempo de espera agotado",
                        Toasty.LENGTH_SHORT
                    ).show()
                    dialogCargando.dismiss()
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun configurarPantallaDeCarga() {
        val view = layoutInflater.inflate(R.layout.dialog_cargando, null)
        dialogCargando = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()
        progressBar = view.findViewById(R.id.progressBar)
        val tvTitulo: TextView = view.findViewById(R.id.tvTitulo)
        tvMensaje = view.findViewById(R.id.tvMensajeDeCarga)

        tvTitulo.text = "Obteniendo el calendario"
        tvMensaje.text = "Por favor espere..."
        dialogCargando.show()

    }

}