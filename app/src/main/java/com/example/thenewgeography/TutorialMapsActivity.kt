package com.example.thenewgeography

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class TutorialMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private fun setLocation(nameOfLocation:String) {
        var geoCoder:Geocoder = Geocoder(applicationContext)

        try {
            var listAddresses: MutableList<Address>? =  geoCoder.getFromLocationName(nameOfLocation, 1)
            if (listAddresses != null && listAddresses.isNotEmpty()) {

                val location = LatLng(listAddresses[0].latitude, listAddresses[0].longitude)
                mMap.addMarker(MarkerOptions().position(location).title("Marker in Tienanmen Square"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
                setMapLongClick(mMap)
                Log.i("Location: ", "${listAddresses[0].latitude}, ${listAddresses[0].longitude}")
            }
        } catch (e:Exception) {
            Log.i("Error: ", "There was an error")
            e.printStackTrace()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_maps)
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
        autocompleteFragment.setText("Tienanmen Square")
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
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        setLocation("Tienanmen Square")
    }
    private fun setMapLongClick(mMap:GoogleMap) {
        lateinit var marker: Marker

        mMap.setOnMapLongClickListener { latLng ->
            if (mMap.mapType == 2) {
                mMap.mapType = 1
                marker.isVisible = false
            } else if (mMap.mapType == 1) {
                mMap.mapType = 2
                marker = mMap.addMarker((MarkerOptions().position(LatLng(39.90409924316407, 116.39135915527343))
                    .title("Marker in REAL Tienanmen Square")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))))
                marker.isVisible = true
            }
        }
    }

}

