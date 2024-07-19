package com.nas.alreem.activity.bus_service

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.adapter.BusEapDaysAdapter
import com.nas.alreem.activity.payments.model.InfoListModel
import com.nas.alreem.constants.ConstantFunctions
import java.util.Locale


class BusServiceEapRegister : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview: RecyclerView
    lateinit var back: ImageView
    lateinit var informationlist: ArrayList<InfoListModel>
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var progressDialogAdd: ProgressBar
    lateinit var eapdaysarray:ArrayList<String>
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
   // lateinit var gpslocationtext : TextView
    lateinit var gpslocation : ImageView
    lateinit var street : EditText
    lateinit var area : EditText
    lateinit var city : EditText

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_service_reg_eap_layout)
        mContext=this
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()
         initfn()
        // progressDialogAdd.visibility = View.VISIBLE
        if (ConstantFunctions.internetCheck(mContext)) {
            //  callPaymentInformation()
        } else {
            //  DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun initfn() {
        eapdaysarray = ArrayList()
        recyclerview = findViewById(R.id.daysrec)
        gpslocation = findViewById(R.id.gpslocation)
        street = findViewById(R.id.street)
        area = findViewById(R.id.area)
        city = findViewById(R.id.city)
        var linearLayoutManager = GridLayoutManager(mContext,2)
        recyclerview.layoutManager = linearLayoutManager
        eapdaysarray.add("All")
        eapdaysarray.add("Monday")
        eapdaysarray.add("Tuesday")
        eapdaysarray.add("Wednesday")
        eapdaysarray.add("Thursday")
        eapdaysarray.add("Friday")
        var newsLetterAdapter= BusEapDaysAdapter(eapdaysarray,mContext)
        recyclerview.adapter=newsLetterAdapter
        gpslocation.setOnClickListener {
            getAddressFromLocation(latitude, longitude,area,city,street)



        }
    }
    private fun shareLocation(latitude: Double, longitude: Double) {
        val uri = "https://maps.google.com/?q=$latitude,$longitude"
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Here is my location: $uri")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            getCurrentLocation()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getCurrentLocation()
            } else {
                // Permission denied. Handle accordingly.
                Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.lastLocation
                .addOnSuccessListener(this) { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                         latitude = location.latitude
                         longitude = location.longitude
                     //   getAddressFromLocation(latitude, longitude)

                        Log.e("Location", "Latitude: $latitude, Longitude: $longitude")
                    } else {
                        Log.e("Location", "Location is null")
                    }
                }
        }
    }

    private fun getAddressFromLocation(
        latitude: Double,
        longitude: Double,
        area: EditText,
        citye: EditText,
        street: EditText
    ) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            Log.e("addresses", addresses.toString())
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                // Do something with the address
                val fullAddress = address.getAddressLine(0) // Get the full address
                Log.e("Address",fullAddress)

                val city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode
                val knownName = addresses[0].featureName
                area.setText(country)
                citye.setText(state)
                street.setText(city)

                println("Address: $fullAddress")
              

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}