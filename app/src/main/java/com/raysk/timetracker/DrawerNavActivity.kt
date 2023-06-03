package com.raysk.timetracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.raysk.timetracker.data.preferences.SettingPreferences
import com.raysk.timetracker.databinding.ActivityDrawerNavBinding
import com.raysk.timetracker.login.LoginActivity

class DrawerNavActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDrawerNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawerNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDrawerNav.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView



        val navController = findNavController(R.id.nav_host_fragment_content_drawer_nav)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_calendar
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            if (navView.checkedItem!!.itemId   == item.itemId){
                drawerLayout.closeDrawers()
                return@OnNavigationItemSelectedListener true
            }

            when (item.itemId) {
                R.id.nav_logout -> {
                    val preferences = SettingPreferences(this@DrawerNavActivity)
                    preferences.delete()
                    startActivity(Intent(this@DrawerNavActivity,LoginActivity::class.java))
                    finish()
                }
                R.id.nav_calendar -> {
                    navController.navigate(R.id.nav_calendar)
                    drawerLayout.closeDrawers()
                }
            }
            true
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_drawer_nav)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}