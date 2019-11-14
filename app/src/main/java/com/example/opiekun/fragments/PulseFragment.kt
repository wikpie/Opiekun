package com.example.opiekun.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.opiekun.Communicator
import com.example.opiekun.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_pulse.*

class PulseFragment : Fragment() {
    private lateinit var seniorUidd:String
    private var nowPulse=0
    private var nowSteps=0
    private var eightPulse=0
    private var eightSteps=0
    private var threePulse=0
    private var threeSteps=0
    private val ref= FirebaseDatabase.getInstance().getReference("seniors")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_pulse, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model= ViewModelProviders.of(activity!!).get(Communicator::class.java)
        model.message.observe(this, Observer<Any> { t ->
            seniorUidd=t.toString()
            Log.d("piździsko", seniorUidd)
        })
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(seniorUidd!=" "){
                    nowPulse=dataSnapshot.child("$seniorUidd/now/pulse").value.toString().toInt()
                    nowSteps=dataSnapshot.child("$seniorUidd/now/steps").value.toString().toInt()
                    if(dataSnapshot.hasChild("$seniorUidd/8am")) {
                        eightPulse = dataSnapshot.child("$seniorUidd/8am/pulse").value.toString().toInt()
                        eightSteps = dataSnapshot.child("$seniorUidd/8am/steps").value.toString().toInt()
                    }
                    if(dataSnapshot.hasChild("$seniorUidd/3pm")){
                        threePulse=dataSnapshot.child("$seniorUidd/3pm/pulse").value.toString().toInt()
                        threeSteps=dataSnapshot.child("$seniorUidd/3pm/steps").value.toString().toInt()
                    }
                }
                text_top_top.text="Teraz"
                text_mid_top.text="O 8 rano"
                text_bot_top.text="O 15 po południu"
                text_top_mid.text="Puls: $nowPulse"
                text_top_bot.text="Kroki: $nowSteps"
                text_mid_mid.text="Puls: $eightPulse"
                text_mid_bot.text="Kroki: $eightSteps"
                text_bot_mid.text="Puls: $threePulse"
                text_bot_bot.text="Kroki: $threeSteps"
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Main", "loadPost:onCancelled", databaseError.toException()!!)
            }
        }
        ref.addValueEventListener(postListener)

    }
}