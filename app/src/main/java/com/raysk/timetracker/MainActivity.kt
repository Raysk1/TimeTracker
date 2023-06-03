package com.raysk.timetracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raysk.timetracker.data.Usuario
import com.raysk.timetracker.data.preferences.SettingPreferences
import com.raysk.timetracker.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preferences = SettingPreferences(this)
        Usuario.actual = preferences.getUsuario()
        val intent = if (Usuario.actual == null){
            Intent(this,LoginActivity::class.java)
        }else{
            Intent(this,DrawerNavActivity::class.java)
        }

        startActivity(intent)
        finish()
    }
}