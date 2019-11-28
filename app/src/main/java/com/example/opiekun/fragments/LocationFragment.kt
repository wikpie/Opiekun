package com.example.opiekun.fragments

import android.location.Geocoder
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
import java.util.*


class LocationFragment : Fragment(),OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private var mapFragment: SupportMapFragment?=null
    private var latitude:Double=0.0
    private var longitude=0.0
    private lateinit var geocoder:Geocoder
    private lateinit var seniorUidd:String
    private lateinit var seniorName:String



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_location, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        geocoder=Geocoder(context, Locale.getDefault())
        val model= ViewModelProviders.of(activity!!).get(Communicator::class.java)
        model.message.observe(this, Observer<Any> { t ->
            seniorUidd=t.toString()
            val ref= FirebaseDatabase.getInstance().getReference("seniors/$seniorUidd/hour_info/now")
            ref.addValueEventListener(postListener)
        })
        model.message1.observe(this, Observer<Any> { t ->
            seniorName=t.toString()
            title.text="Wybrany senior: $seniorName"
        })

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        Log.d("mapa","Mapa jest gotowa")
    }

    private val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if(seniorUidd!=" "){
                Log.d("senior", seniorUidd)
                latitude=dataSnapshot.child("latitude").value.toString().toDouble()
                Log.d("senior", latitude.toString())
                longitude=dataSnapshot.child("longitude").value.toString().toDouble()
                val myPlace = LatLng(latitude, longitude)
                val addresses=geocoder.getFromLocation(latitude,longitude,1)
                val address= addresses[0].getAddressLine(0)
                Log.d("lokalizacja", addresses.toString())
                Log.d("lokalizacja", address)
                map.addMarker(MarkerOptions().position(myPlace).title("My Favorite City"))
                map.animateCamera(CameraUpdateFactory.newLatLngZoom((myPlace), 15f))
                text_gps.text= "Senior znajduje się na: $address"
            }}
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("Main", "loadPost:onCancelled", databaseError.toException())
        }
    }
}


