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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_location.*


class LocationFragment : Fragment(),OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private var mapFragment: SupportMapFragment?=null
    private var location:String=" "
    private var latitude:Double=0.0
    private var longitude=0.0
    private lateinit var seniorUidd:String
    private val ref= FirebaseDatabase.getInstance().getReference("seniors")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_location, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        val model= ViewModelProviders.of(activity!!).get(Communicator::class.java)
        model.message.observe(this, Observer<Any> { t ->
            seniorUidd=t.toString()
            ref.addValueEventListener(postListener)
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        Log.d("pid","Jazda on map ready")
    }

    private val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if(seniorUidd!=" "){
                Log.d("pidos", seniorUidd)
                latitude=dataSnapshot.child("$seniorUidd/now/latitude").value.toString().toDouble()
                Log.d("piddd", latitude.toString())
                longitude=dataSnapshot.child("$seniorUidd/now/longitude").value.toString().toDouble()
                location=dataSnapshot.child("$seniorUidd/now/location").value.toString()
                val myPlace = LatLng(latitude, longitude)
                map.addMarker(MarkerOptions().position(myPlace).title("My Favorite City"))
                map.animateCamera(CameraUpdateFactory.newLatLngZoom((myPlace), 15f))
                text_gps.text= "Senior znajduje się na: $location"
            }}
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("Main", "loadPost:onCancelled", databaseError.toException())
        }
    }
}


