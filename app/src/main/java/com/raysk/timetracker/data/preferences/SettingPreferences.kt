package com.raysk.timetracker.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.raysk.timetracker.data.Usuario


class SettingPreferences(val context: Context) {
    private val USER_PREFERENCE_KEY = "USER_PREFERENCE_KEY"

    fun save(usuario: Usuario) {
        val edit: SharedPreferences.Editor =
            context.getSharedPreferences("user", Context.MODE_PRIVATE).edit()
        val json = Gson().toJson(usuario)
        edit.putString(USER_PREFERENCE_KEY, json)
        edit.apply()
    }

    fun getUsuario(): Usuario? {
        val json = context.getSharedPreferences("user", Context.MODE_PRIVATE)
            .getString(USER_PREFERENCE_KEY, null)
        return if (json != null) {
            val usuario = Gson().fromJson(json, Usuario::class.java)
            usuario
        } else {
            null
        }
    }


}