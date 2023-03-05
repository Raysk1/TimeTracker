package com.raysk.timetracker.calendario

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.jsr310.scrollToDateTime
import com.raysk.timetracker.R
import com.raysk.timetracker.data.Materia
import com.raysk.timetracker.data.Usuario
import com.raysk.timetracker.data.api.Services
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.time.LocalDateTime

class CalendarioActivity : AppCompatActivity() {
    private val adapter = CalendarioAdapter()
    private val services = Services()
    private lateinit var weekView: WeekView
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var dialog: AlertDialog
    private lateinit var progressBar: ProgressBar
    private lateinit var tvTitulo: TextView
    private lateinit var tvMensaje: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)
        weekView = findViewById(R.id.weekView)
        weekView.adapter = adapter
        configurarPantallaDeCarga()

        scope.launch { procesarHorarios() }

    }

    private suspend fun procesarHorarios() {
        withContext(Dispatchers.IO) {
            try {
                var horarioList = services.getHorarios()
                val userList = mutableMapOf<String, Usuario>()
                val materiaList = mutableMapOf<Int, Materia>()

                if (horarioList == null) {
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

                    val materia: Materia = if (materiaList.containsKey(it.idMateria)) {
                        materiaList[it.idMateria]!!
                    } else {
                        val materia = services.getMateria(it.idMateria)!!
                        materiaList[it.idMateria] = materia
                        materia
                    }


                    val event = CalendarioEvent(it,usuario,materia)

                    calendarioEventList.add(event)

                    withContext(Dispatchers.Main) {
                        tvMensaje.setText("Cargando ${index + 1} de ${horarioList.size}")
                        progressBar.progress = index + 1
                    }
                }

                adapter.submitList(calendarioEventList)

                withContext(Dispatchers.Main) {
                    tvMensaje.setText("Compleatdo con exito")
                }
                delay(2000)
                dialog.dismiss()

            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CalendarioActivity, e.message(), Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@CalendarioActivity,
                        "Tiempo de espera agotado",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.calendario_menu, menu)
        menu?.findItem(R.id.action_today)?.setOnMenuItemClickListener {
            weekView.scrollToDateTime(dateTime = LocalDateTime.now())
            true
        }

        menu?.findItem(R.id.action_day_view)?.setOnMenuItemClickListener {
            weekView.numberOfVisibleDays = 1
            it.isChecked = true
            true
        }

        menu?.findItem(R.id.action_three_day_view)?.setOnMenuItemClickListener {
            weekView.numberOfVisibleDays = 3
            it.isChecked = true
            true
        }

        menu?.findItem(R.id.action_week_view)?.setOnMenuItemClickListener {
            weekView.numberOfVisibleDays = 7
            it.isChecked = true
            true
        }

        return true
    }

    private fun configurarPantallaDeCarga() {
        val view = layoutInflater.inflate(R.layout.dialog_cargando, null)
        dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()
        progressBar = view.findViewById(R.id.progressBar)
        tvTitulo = view.findViewById(R.id.tvTitulo)
        tvMensaje = view.findViewById(R.id.tvMensajeDeCarga)

        tvTitulo.setText("Obteniendo el calendario")
        tvMensaje.setText("Por favor espere...")
        dialog.show()

    }

}