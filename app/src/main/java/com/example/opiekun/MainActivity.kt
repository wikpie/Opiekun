package com.example.opiekun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val sharedPrefs by lazy { getSharedPreferences("main", PRIVATE_MODE) }
    private val sharedPrefs1 by lazy { getSharedPreferences("uid", PRIVATE_MODE) }
    private val ref= FirebaseDatabase.getInstance().getReference("caregivers")
    private lateinit var uidd: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (sharedPrefs.getBoolean("main", false)) {
            LogIn()
            Log.d("seniorita",uidd)
            toast(R.string.successfull_login)
        }
        else {
            setContentView(R.layout.activity_main)
            button_register.setOnClickListener {
                if (firstname.text != null && email.text != null && phonenumber.text != null) {
                    val name=firstname.text.toString()
                    val email=email.text.toString()
                    val phonenumber=phonenumber.text.toString()
                    val uid=ref.push().key
                    uidd=uid.toString()
                    //Log.d("senior",uidd)
                    ref.child("$uid/imie").setValue(name)
                    ref.child("$uid/email").setValue(email)
                    ref.child("$uid/phone").setValue(phonenumber)
                    val editor = sharedPrefs.edit()
                    editor.putBoolean("main", true)
                    editor.apply()
                    val editor1=sharedPrefs1.edit()
                    editor1.putString("uid",uidd)
                    editor1.apply()
                    LogIn()
                }
            }


            }
        }
    private fun LogIn() {
        uidd= sharedPrefs1.getString("uid"," ").toString()
        Log.d("seniorowo", uidd)
        val intent= Intent(this, UserActivity::class.java).also{
            it.putExtra("uid",uidd)
        }
        startActivity(intent)
    }
    }

