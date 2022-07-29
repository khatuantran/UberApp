package com.example.driverapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import com.example.driverapp.R

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView:NavigationView
    private  lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        initComponent()
        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_history, R.id.nav_profile, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener({ menuItem ->
            logoutBuilder()
            true
        })

    }


    private fun logoutBuilder(){
        val builder = AlertDialog.Builder(this@HomeActivity)
        builder.setTitle("SIGN OUT")
            .setMessage("Do you want to sign out?")
            .setNegativeButton("Cancle", {dialogInterface, _ -> dialogInterface.dismiss()})
            .setPositiveButton("Sign out", {dialogInterface, _ ->
                startActivity(Intent(this, LoginActivity::class.java))
                dialogInterface.dismiss()
                finish()
            })
            .create()
            .show()
    }

    private fun initComponent(){
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navController = findNavController(R.id.nav_host_fragment_content_home)
        navView = findViewById(R.id.nav_view)


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}