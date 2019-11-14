package com.example.opiekun

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.opiekun.fragments.LocationFragment
import com.example.opiekun.fragments.PulseFragment
import com.example.opiekun.fragments.SeniorFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pawegio.kandroid.find
import com.pawegio.kandroid.toast
import re.robz.bottomnavigation.circularcolorreveal.BottomNavigationCircularColorReveal

class UserActivity : AppCompatActivity() {
    private var PRIVATE_MODE = 0
    private val sharedPrefsUserActivity by lazy { getSharedPreferences("main", PRIVATE_MODE) }
    private val sharedPrefsUserActivity1 by lazy { getSharedPreferences("uid", PRIVATE_MODE) }
    var uid=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)
        val colors = resources.getIntArray(R.array.menu_colors)
        val bottomNavigationView = find<BottomNavigationView>(R.id.bottom_navigation)
        val reveal = BottomNavigationCircularColorReveal(colors)
        reveal.setuptWithBottomNavigationView(bottomNavigationView)
        reveal.setOnNavigationItemSelectedListener (mOnNavigationItemSelectedListener)
        if (sharedPrefsUserActivity.getBoolean("main", false)) {
            uid= sharedPrefsUserActivity1.getString("uid"," ")!!
            Log.d("senior", uid)
        }
        else {
            val editor=sharedPrefsUserActivity.edit()
            editor.putBoolean("main", true)
            editor.apply()
            uid = intent.getStringExtra("uid")
            Log.d("seniorzysko", uid)
            val editor1 = sharedPrefsUserActivity1.edit()
            editor1.putString("uid", uid)
            editor1.apply()
        }
        val fragment = SeniorFragment.newInstance(uid)
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commit()
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.menu_location -> {
                toast("lokalizacja")
                val fragment = LocationFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_senior -> {
                toast("senior")
                val fragment = SeniorFragment.newInstance(uid)
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_pulse -> {
                toast("puls")
                val fragment = PulseFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
