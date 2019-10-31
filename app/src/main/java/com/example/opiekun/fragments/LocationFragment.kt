package com.example.opiekun.fragments

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.opiekun.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.OnMapReadyCallback

import java.util.*

class LocationFragment : Fragment(),OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private var mapFragment: SupportMapFragment?=null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    var mLastLocation: Location? = null
    private var address=""
    private var city=""
    private var addresses= listOf<Address>()
    private var latitude:Double=0.0
    private var longitude=0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_location, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        /*mFusedLocationClient.lastLocation
            .addOnSuccessListener{ location: Location? ->
                if(location!=null){
                    mLastLocation=location
                    latitude =  location.latitude
                    longitude = location.longitude
                    Log.d("Main",latitude.toString())
                    Log.d("Main",longitude.toString())*/
                    val myPlace = LatLng(153.0, 123.0)  // this is New York
                    map.addMarker(MarkerOptions().position(myPlace).title("My Favorite City"))
                    map.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) )
                    map.moveCamera(CameraUpdateFactory.newLatLng(myPlace))
                    //addresses=geocoder.getFromLocation(latitude,longitude,1)
                    //address= addresses[0].getAddressLine(0)
                    //city= addresses[0].locality
                    //text_gps.text= "$address , $city"
                //}

            }

    }
