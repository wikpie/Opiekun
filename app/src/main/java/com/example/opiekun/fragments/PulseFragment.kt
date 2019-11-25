package com.example.opiekun.fragments

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.opiekun.Communicator
import com.example.opiekun.R
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_pulse.*

class PulseFragment : Fragment() {
    private lateinit var seniorUidd:String
    private lateinit var seniorName:String
    private var pulseList= arrayListOf<Int>()
    private var homeList=arrayListOf<Int>()
    private var entryListPulse=ArrayList<Entry>()
    private var entryListHome=ArrayList<Entry>()
    private var seniorHome=""
    private lateinit var seniorLocationNow:String
    private var nowPulse=0
    private var nowSteps=0
    private var eightPulse=0
    private var eightSteps=0
    private var threePulse=0
    private var threeSteps=0
    private val minPulse=50
    private val maxPulse=80
    private val ref= FirebaseDatabase.getInstance().getReference("seniors")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_pulse, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pulseChart=this.chart_pulse
        val homeChart=this.chart_home
        val model= ViewModelProviders.of(activity!!).get(Communicator::class.java)
        model.message.observe(this, Observer<Any> { t ->
            seniorUidd=t.toString()
        })
        model.message1.observe(this, Observer<Any> { t ->
            seniorName=t.toString()
            title.text="Wybrany senior: $seniorName"
        })
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(seniorUidd!=" "){
                    //te są najważniejsze
                    seniorHome=dataSnapshot.child("$seniorUidd/dom").value.toString()
                    nowPulse=dataSnapshot.child("$seniorUidd/now/pulse").value.toString().toInt()
                    nowSteps=dataSnapshot.child("$seniorUidd/now/steps").value.toString().toInt()
                    Log.d("henio_puls_teraz",nowPulse.toString())
                    Log.d("henio_dom",seniorHome)
                    Log.d("henio_korki_teraz",nowSteps.toString())
                    //tu kolejno
                    for(i in 0..23){
                        var j=i.toString()
                        pulseList.add(dataSnapshot.child("$seniorUidd/$j/pulse").value.toString().toInt())
                        if((dataSnapshot.child("$seniorUidd/$i/location").value.toString()) ==seniorHome){
                            homeList.add(0)
                        }
                        else homeList.add(1)
                        Log.d("henio_puls",pulseList.toString())
                        Log.d("henio_dom",homeList.toString())
                    }
                    if(nowPulse<minPulse || nowPulse>maxPulse){
                        text_top_mid.setTextColor(Color.parseColor("#FF0000"))
                        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        if (vibrator.hasVibrator()) { // Vibrator availability checking
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)) // New vibrate method for API Level 26 or higher
                            } else {
                                vibrator.vibrate(500) // Vibrate method for below API Level 26
                            }
                        }
                    }
                    else text_top_mid.setTextColor(Color.parseColor("#00FF00"))
                    text_top_top.text="Teraz"
                    text_top_mid.text="Puls: $nowPulse"
                    text_top_bot.text="Kroki: $nowSteps"
                    for(i in 0..23){
                        var entriesPulse=Entry(i.toFloat(),pulseList[i].toFloat())
                        var entriesHome=Entry(i.toFloat(),homeList[i].toFloat())
                        entryListPulse.add(entriesPulse)
                        entryListHome.add(entriesHome)
                    }
                    val dataSetPulse= LineDataSet(entryListPulse,"Wykres puls")
                    dataSetPulse.color = Color.RED
                    dataSetPulse.setDrawValues(false)
                    dataSetPulse.axisDependency = YAxis.AxisDependency.LEFT
                    val dataSetHome= LineDataSet(entryListHome,"Wykres dom")
                    dataSetHome.color = Color.GREEN
                    dataSetHome.setDrawValues(false)
                    dataSetHome.axisDependency = YAxis.AxisDependency.LEFT
                    val lineDataPulse= LineData(dataSetPulse)
                    val lineDataHome=LineData(dataSetHome)
                    pulseChart.data=lineDataPulse
                    homeChart.data=lineDataHome
                    pulseChart.invalidate()
                    homeChart.invalidate()
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Main", "loadPost:onCancelled", databaseError.toException()!!)
            }
        }
        ref.addValueEventListener(postListener)


    }
}