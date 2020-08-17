package com.example.thenewgeography

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class SolutionMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapSwitch:Switch

    private fun getLatAndLang(lat:Double, lng:Double): ArrayList<Double> {
        var obj = EvilTransform()
        return obj.gcj2wgs_exact(lat, lng)
    }

    private fun setLocation(nameOfLocation:String, isChecked:Boolean) {
        var geoCoder: Geocoder = Geocoder(applicationContext)
        try {
            var listAddresses: MutableList<Address>? =  geoCoder.getFromLocationName(nameOfLocation, 1)
            if (listAddresses != null && listAddresses.isNotEmpty()) {
                var coordinates : ArrayList<Double> = if (listAddresses[0].countryName == "China") {
                    getLatAndLang(listAddresses[0].latitude, listAddresses[0].longitude)
                } else {
                    var sameLocation = ArrayList<Double> (2)
                    sameLocation.add(listAddresses[0].latitude)
                    sameLocation.add(listAddresses[0].longitude)
                    sameLocation
                }
                Log.i("Coordinates", "Real Ones: ${listAddresses[0].latitude}, ${listAddresses[0].longitude} and Calculated Ones: ${coordinates[0]}, ${coordinates[1]}")

                lateinit var location:LatLng
                var color:Float;
                Log.i("Array Size: ", coordinates.size.toString())
                if (!isChecked) {
                    location =  LatLng(coordinates[0], coordinates[1])
                    color = BitmapDescriptorFactory.HUE_BLUE
                } else {
                    location = LatLng(listAddresses[0].latitude, listAddresses[0].longitude)
                    color = BitmapDescriptorFactory.HUE_RED

                }

                Log.i("Marker Location: ", nameOfLocation)
                mMap.addMarker(MarkerOptions().position(location)
                    .title("Marker in $nameOfLocation")
                    .icon(BitmapDescriptorFactory.defaultMarker(color)))

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
        setContentView(R.layout.activity_solution_maps)
        mapSwitch = findViewById(R.id.mapSwitch)

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


            // Set up a PlaceSelectionListener to handle the response.
            autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    // TODO: Get info about the selected place.
                    Log.i("Entered Location", "Place: ${place.name}, ${place.id}")
                    place.name?.let { setLocation(it, mapSwitch.isChecked) }
                }

                override fun onError(status: Status) {
                    // TODO: Handle the error.
                    Log.i("Error Code: ", "An error occurred: $status")
                }
            })
        } catch (e:Exception) {
            e.printStackTrace()
        }
        autocompleteFragment.view!!.isEnabled = false
        mapFragment.getMapAsync(this)

        val searchDemoButton = findViewById<Button>(R.id.searchDemoButton)
        val searchShowcase = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(searchDemoButton))
            .setStyle(R.style.ShowcaseView)
            .setContentTitle("Search Bar")
            .setContentText("Use this to search any location")
            .hideOnTouchOutside()
            .build()
        searchShowcase.setButtonText("")

        searchShowcase.overrideButtonClick() {view: View? ->
            searchShowcase.hide()
            val enableDemoButton = findViewById<Switch>(R.id.mapSwitch)
            val switchShowcase = ShowcaseView.Builder(this)
                .setTarget(ViewTarget(enableDemoButton))
                .setStyle(R.style.ShowcaseView)
                .setContentTitle("Switch")
                .setContentText("You can enable this to see the actual location shown by google maps i.e without correction")
                .hideOnTouchOutside()
                .build()
            switchShowcase.setButtonText("")

            switchShowcase.overrideButtonClick() {view: View? ->
                switchShowcase.hide()
                val mapsShowcase = ShowcaseView.Builder(this)
                    .setStyle(R.style.ShowcaseView)
                    .setContentTitle("Map")
                    .setContentText("After inputting a location, Long press on the map to enable satellite view")
                    .hideOnTouchOutside()
                    .build()
                mapsShowcase.setButtonText("")

            }
        }
        autocompleteFragment.view!!.isEnabled = true
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
        // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun setMapLongClick(mMap:GoogleMap) {
        mMap.setOnMapLongClickListener { latLng ->
            if (mMap.mapType == 2) {
                mMap.mapType = 1
            } else if (mMap.mapType == 1) {
                mMap.mapType = 2
            }
        }
    }

}