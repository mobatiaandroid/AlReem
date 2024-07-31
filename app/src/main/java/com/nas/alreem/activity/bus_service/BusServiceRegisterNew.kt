package com.nas.alreem.activity.bus_service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nas.alreem.R
import com.nas.alreem.activity.payments.model.InfoListModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import java.util.Locale

class BusServiceRegisterNew : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview: RecyclerView
    lateinit var back: ImageView
    lateinit var informationlist: ArrayList<InfoListModel>
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var progressDialogAdd: ProgressBar
    lateinit var parentsdetailslinear : LinearLayout
    lateinit var parentsdetailslinear1 : LinearLayout
    lateinit var checkPassportLinear : LinearLayout
    lateinit var addresslinear : RelativeLayout
    lateinit var parentlinear : RelativeLayout
    lateinit var addressinfo_linear : LinearLayout
    lateinit var downarrowImage : ImageView
    lateinit var downarrowAddress : ImageView
    lateinit var droppoint : EditText
    lateinit var pickuppoint : EditText
    lateinit var gpslocation : ImageView
    lateinit var street : EditText
    lateinit var area : EditText
    lateinit var city : EditText
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1



    var flag:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_reg_details_forms)
        mContext=this
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermission()
        initfn()

        if (ConstantFunctions.internetCheck(mContext)) {

        } else {

        }

    }

    private fun initfn() {
        heading=findViewById(R.id.heading)
        heading.text= "Regular Bus Service Registration Form"
        parentsdetailslinear=findViewById(R.id.parentsdetailslinear)
        parentsdetailslinear1=findViewById(R.id.parentsdetailslinear1)
        checkPassportLinear=findViewById(R.id.checkPassportLinear)
        addresslinear=findViewById(R.id.addresslinear)
        addressinfo_linear=findViewById(R.id.addressinfo_linear)
        parentlinear = findViewById(R.id.parentlinear)
        downarrowImage = findViewById(R.id.downarrowImage)
        downarrowAddress = findViewById(R.id.downarrowAddress)
        droppoint = findViewById(R.id.drop)
        pickuppoint = findViewById(R.id.pickup)
        gpslocation = findViewById(R.id.gpslocation)
        street = findViewById(R.id.street)
        area = findViewById(R.id.area)
        city = findViewById(R.id.city)

        val yesNoRadioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val yesButton = findViewById<RadioButton>(R.id.radioYes)
        val noButton = findViewById<RadioButton>(R.id.radioNo)

        yesButton.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                pickuppoint.visibility=View.VISIBLE
                droppoint.visibility=View.GONE
            }
        }
        noButton.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                pickuppoint.visibility=View.VISIBLE
                droppoint.visibility=View.VISIBLE
            }
        }
        gpslocation.setOnClickListener {
            getAddressFromLocation(latitude, longitude,area,city,street)



        }
        parentlinear.setOnClickListener {

            if(flag)
            {
                parentsdetailslinear.visibility=View.VISIBLE
                parentsdetailslinear1.visibility=View.VISIBLE
                checkPassportLinear.visibility=View.VISIBLE
                downarrowImage.rotation= 180F
              //  parentsdetailslinear1.visibility=View.VISIBLE


            }
            else
            {
                parentsdetailslinear.visibility=View.GONE
                parentsdetailslinear1.visibility=View.GONE
                checkPassportLinear.visibility=View.GONE
                downarrowImage.rotation= 0F

            }
            flag = !flag
        }
        addresslinear.setOnClickListener {
            if(flag)
            {
                addressinfo_linear.visibility=View.VISIBLE
                downarrowAddress.rotation=180f


            }
            else
            {
                addressinfo_linear.visibility=View.GONE
                downarrowAddress.rotation=0f

            }
            flag = !flag
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