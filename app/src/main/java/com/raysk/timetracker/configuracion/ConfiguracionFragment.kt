package com.raysk.timetracker.configuracion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.raysk.timetracker.R

class ConfiguracionFragment : Fragment() {
    private lateinit var btnConfigurancionNombre: Button
    private lateinit var btnConfigurancionEmail:Button
    private lateinit var getBtnConfigurancionContraseña:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnConfigurancionNombre = view.findViewById(R.id.btnNombre)
        btnConfigurancionEmail = view.findViewById(R.id.btnConfigurancionEmail)
        getBtnConfigurancionContraseña = view.findViewById(R.id.btnConfigurancionContraseña)

        val navController = findNavController()

        btnConfigurancionNombre.setOnClickListener {
            navController.navigate(R.id.nombreFragment)
        }
        btnConfigurancionEmail.setOnClickListener{
            navController.navigate(R.id.EMailFragment)
        }
        getBtnConfigurancionContraseña.setOnClickListener {
            navController.navigate(R.id.passwordFragment)
        }
    }
}