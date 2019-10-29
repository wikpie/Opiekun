package com.example.opiekun

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pawegio.kandroid.find
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_connection.*
import re.robz.bottomnavigation.circularcolorreveal.BottomNavigationCircularColorReveal

class MainActivity : AppCompatActivity() {
    private lateinit var menuLocationItem: MenuItem
    private lateinit var menuConditionItem: MenuItem
    private lateinit var menuPulseItem: MenuItem
    private lateinit var menuSeniorItem: MenuItem
    private var PRIVATE_MODE = 0
    private val sharedPrefs by lazy { getSharedPreferences("main", PRIVATE_MODE) }

  /*  override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        menuLocationItem=menu.findItem(R.id.menu_location)
        menuConditionItem=menu.findItem(R.id.menu_condition)
        menuSeniorItem=menu.findItem(R.id.menu_senior)
        menuPulseItem=menu.findItem(R.id.menu_pulse)
        return true
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val colors = resources.getIntArray(R.array.menu_colors)
        val bottomNavigationView = find<BottomNavigationView>(R.id.bottom_navigation)
        val reveal = BottomNavigationCircularColorReveal(colors)
        reveal.setuptWithBottomNavigationView(bottomNavigationView)
        if (sharedPrefs.getBoolean("main", false)) {
            toast(R.string.successfull_login)
        }
        else {
            val dialog = Dialog(this)
            val yesBtn=find<Button>(R.id.yesBtnConnect)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog .setCancelable(false)
            dialog .setContentView(R.layout.dialog_connection)
            yesBtn.setOnClickListener {
               if(email.text!=null){

                }
               dialog .dismiss()

            }
            dialog .show()
            val editor = sharedPrefs.edit()
            editor.putBoolean("main", true)
            editor.apply()
            }

        reveal.setOnNavigationItemSelectedListener {
            Log.d("pizdaogien", it.toString())
         //   Log.d("pizdao", menuLocationItem.toString())
         //   Log.d("pizdao", menuConditionItem.toString())
          //  Log.d("pizdao", menuPulseItem.toString())
         //   Log.d("pizdao", menuSeniorItem.toString())
          //  when(it){
           //     menuLocationItem -> toast("lokalizacja")
           //     menuConditionItem -> toast("kondycja")
            //    menuPulseItem -> toast("puls")
            //    menuSeniorItem ->toast("senior")

           // }
            // Do your custom operations on item selection here (e.g display a fragment)
            // ...
            // Allow selection
            true
        }
        }
    }

