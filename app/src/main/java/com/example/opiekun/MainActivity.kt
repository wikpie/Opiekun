package com.example.opiekun

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import com.example.opiekun.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pawegio.kandroid.find
import com.pawegio.kandroid.toast
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
            /*val fragment = CreateFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                .commit()*/
            val editor = sharedPrefs.edit()
            editor.putBoolean("main", true)
            editor.apply()
            }

        reveal.setOnNavigationItemSelectedListener (mOnNavigationItemSelectedListener)

        }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.menu_location -> {
                toast("lokalizacja")
                val fragment = LocationFragment()
               supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_condition -> {
                toast("kondycja")
               val fragment = ConditionFragment()
               supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_senior -> {
                toast("senior")
               val fragment = SeniorFragment()
               supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                  .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_pulse -> {
                toast("puls")
                val fragment = PulseFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }






    }

