package com.raysk.timetracker.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.kusu.loadingbutton.LoadingButton
import com.raysk.timetracker.DrawerNavActivity
import com.raysk.timetracker.R
import com.raysk.timetracker.data.Usuario
import com.raysk.timetracker.data.api.Services
import com.raysk.timetracker.data.preferences.SettingPreferences
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    lateinit var tilUsername: TextInputLayout
    lateinit var tilPassword: TextInputLayout
    lateinit var loginButton: LoadingButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tilPassword = findViewById(R.id.tilPassword)
        tilUsername = findViewById(R.id.tilUsername)
        loginButton = findViewById(R.id.loginButton)
        val services = Services()

        loginButton.setOnClickListener {
            val username = tilUsername.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        if (username.isEmpty() || password.isEmpty()) {
                            withContext(Dispatchers.Main) {
                                Toasty.warning(
                                    this@LoginActivity,
                                    "Por favor llena todos los espacios",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                services.logIn(username, password)
                                val intent =
                                    Intent(this@LoginActivity, DrawerNavActivity::class.java)
                                startActivity(intent)
                                val preferences = SettingPreferences(this@LoginActivity)
                                preferences.save(Usuario.actual!!)
                                this@LoginActivity.finish()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toasty.error(this@LoginActivity, e.message!!, Toasty.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}