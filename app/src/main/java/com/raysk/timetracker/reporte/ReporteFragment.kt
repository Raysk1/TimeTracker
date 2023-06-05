package com.raysk.timetracker.reporte

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.raysk.timetracker.R
import es.dmoral.toasty.Toasty


class ReporteFragment : Fragment() {


    private lateinit var ETTema: EditText
    private lateinit var ETContenido: EditText
    private lateinit var BTNEnviar: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ETTema = view.findViewById(R.id.ETTema)
        ETContenido = view.findViewById(R.id.ETContenido)
        BTNEnviar = view.findViewById(R.id.BTNEnviar)
        BTNEnviar.setOnClickListener{
            val bodyMail = ETContenido.text.toString()
            // Aqui debe de ir el correo del usuario
            val mail = "Correo@gmail.com"
            val subject = ETTema.getText().toString()
            if (bodyMail.isEmpty() || subject.isEmpty()) {
                Toasty.warning(
                    requireContext(),
                    "Por favor, llene todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intentMail = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$mail"))
                val intentMailFull = Intent(Intent.ACTION_SEND)
                intentMailFull.type = "message/rfc822"
                intentMailFull.putExtra(Intent.EXTRA_SUBJECT, subject)
                intentMailFull.putExtra(Intent.EXTRA_TEXT, bodyMail)
                intentMailFull.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail, mail))
                startActivity(intentMailFull)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reporte, container, false)
    }


}