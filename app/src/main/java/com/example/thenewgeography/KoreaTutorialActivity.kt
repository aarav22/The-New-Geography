package com.example.thenewgeography

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment

class KoreaTutorialActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_korea)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        try {
            Places.initialize(applicationContext, getString(R.string.api_key));
            // Specify the types of place data to return.
            autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
            autocompleteFragment.setText("South Korea")
            autocompleteFragment.view?.isEnabled = false

        } catch (e:Exception) {
            e.printStackTrace()
        }

        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val southKorea = LatLng(35.9078, 127.7669)
        mMap.addMarker(MarkerOptions().position(southKorea).title("Marker in South Korea"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(southKorea))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7f))
        setMapLongClick(mMap)
    }

    private fun setMapLongClick(mMap: GoogleMap) {
        mMap.setOnMapLongClickListener { latLng ->
            if (mMap.mapType == 2) {
                mMap.mapType = 1
            } else if (mMap.mapType == 1) {
                mMap.mapType = 2
            }
        }
    }

}