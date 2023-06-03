package com.raysk.timetracker.calendario

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.jsr310.scrollToDateTime
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.raysk.timetracker.R
import com.raysk.timetracker.data.Materia
import com.raysk.timetracker.data.Usuario
import com.raysk.timetracker.data.api.Services
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.time.LocalDateTime

class CalendarioFragment : Fragment() {
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
        setHasOptionsMenu(true)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.calendario_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_today -> {
                weekView.scrollToDateTime(LocalDateTime.now())
                true
            }
            R.id.action_day_view -> {
                weekView.numberOfVisibleDays = 1
                item.isChecked = true
                true
            }
            R.id.action_three_day_view -> {
                weekView.numberOfVisibleDays = 3
                item.isChecked = true
                true
            }
            R.id.action_week_view -> {
                weekView.numberOfVisibleDays = 7
                item.isChecked = true
                true
            }
            R.id.action_reload -> {
                scope.launch { procesarHorarios() }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weekView = view.findViewById(R.id.weekView)


        scope.launch { procesarHorarios() }
        configurarFAB()

    }

    @SuppressLint("SetTextI18n")
    private fun configurarFAB() {
        val fab: FloatingActionButton = requireView().findViewById(R.id.fabCalendario)
        fab.setOnClickListener {
            if (materiaList != null) {
                val dialog = CalendarioFormDialog(
                    requireContext(),
                    "Nuevo Evento",
                    materiaList!!
                ) {
                    //onSave
                    calendarioEventList.add(it)
                    adapter.submitList(calendarioEventList)
                }

                dialog.setOnShowListener {
                    fab.hide()
                }

                dialog.setOnDismissListener {
                    fab.show()
                }

                dialog.show()
            } else {
                Toasty.info(requireContext(), "Por favor recarga el calendario", Toasty.LENGTH_SHORT).show()
            }
        }


    }


    @SuppressLint("SetTextI18n")
    private suspend fun procesarHorarios() {
        configurarPantallaDeCarga()
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

                adapter = CalendarioAdapter(calendarioEventList, materiaList!!)
                weekView.adapter = adapter
                this@CalendarioFragment.calendarioEventList = calendarioEventList
                adapter.submitList(calendarioEventList)

                withContext(Dispatchers.Main) {
                    tvMensaje.text = "Compleatdo con exito"
                }
                delay(2000)
                dialogCargando.dismiss()

            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toasty.error(requireContext(), e.message(), Toast.LENGTH_SHORT).show()
                    dialogCargando.dismiss()
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    Toasty.error(
                        requireContext(),
                        "Tiempo de espera agotado",
                        Toasty.LENGTH_SHORT
                    ).show()
                    dialogCargando.dismiss()
                }
            } catch (e: ConnectException) {
                withContext(Dispatchers.Main) {
                    Toasty.error(
                        requireContext(),
                        "Sin conexion a internet",
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
        dialogCargando = AlertDialog.Builder(context)
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