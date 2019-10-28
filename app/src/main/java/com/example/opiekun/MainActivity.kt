package com.example.opiekun

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_connection.*
import re.robz.bottomnavigation.circularcolorreveal.BottomNavigationCircularColorReveal

class MainActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val sharedPrefs by lazy { getSharedPreferences("main", PRIVATE_MODE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val colors = resources.getIntArray(R.array.menu_colors)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val reveal = BottomNavigationCircularColorReveal(colors)
        reveal.setuptWithBottomNavigationView(bottomNavigationView)
        if (sharedPrefs.getBoolean("main", false)) {
            toast(R.string.successfull_login)
        }
        else {
            val dialog = Dialog(this)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog .setCancelable(false)
            dialog .setContentView(R.layout.dialog_connection)
           // yesBtnConnect.setOnClickListener {
              //  if(email.text!=null){

             //   }
              //  dialog .dismiss()

           // }
            dialog .show()
            val editor = sharedPrefs.edit()
            editor.putBoolean("main", true)
            editor.apply()
            }
        reveal.setOnNavigationItemSelectedListener {
            // Do your custom operations on item selection here (e.g display a fragment)
            // ...
            // Allow selection
            true
        }
        }
    }

