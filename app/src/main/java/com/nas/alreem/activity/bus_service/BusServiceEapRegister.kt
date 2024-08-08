package com.nas.alreem.activity.bus_service

import MyAdapter
import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.adapter.BusEapDaysAdapter
import com.nas.alreem.activity.bus_service.model.StateVO
import com.nas.alreem.activity.payments.model.InfoListModel
import com.nas.alreem.constants.ConstantFunctions
import java.util.Locale


class BusServiceEapRegister : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var logoClickImg: ImageView
    //lateinit var recyclerview: RecyclerView
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

    lateinit var parentsdetailslinear : LinearLayout
    lateinit var parentsdetailslinear1 : LinearLayout
    lateinit var checkPassportLinear : LinearLayout
    lateinit var addresslinear : RelativeLayout
    lateinit var parentlinear : RelativeLayout
    lateinit var addressinfo_linear : LinearLayout
    lateinit var downarrowImage : ImageView
    lateinit var downarrowAddress : ImageView
    var flag:Boolean = true

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    val select_qualification: Array<String> = arrayOf(
        "Select Qualification", "10th / Below", "12th", "Diploma", "UG",
        "PG", "Phd"
    )
    lateinit var spinner:Spinner
    var listVOs: ArrayList<StateVO> = ArrayList()


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
        //recyclerview = findViewById(R.id.daysrec)
        gpslocation = findViewById(R.id.gpslocation)
        street = findViewById(R.id.street)
        area = findViewById(R.id.area)
        city = findViewById(R.id.city)
        heading=findViewById(R.id.heading)
        heading.text= "EAP Registration Form"
        parentsdetailslinear=findViewById(R.id.parentsdetailslinear)
        parentsdetailslinear1=findViewById(R.id.parentsdetailslinear1)
        checkPassportLinear=findViewById(R.id.checkPassportLinear)
        addresslinear=findViewById(R.id.addresslinear)
        addressinfo_linear=findViewById(R.id.addressinfo_linear)
        parentlinear = findViewById(R.id.parentlinear)
        downarrowImage = findViewById(R.id.downarrowImage)
        downarrowAddress = findViewById(R.id.downarrowAddress)
        spinner = findViewById(R.id.daysrec)

        for (i in 0 until select_qualification.size) {
            val stateVO = StateVO()
            stateVO.title = select_qualification[i]
            stateVO.isSelected = false
            listVOs.add(stateVO)
        }
        val myAdapter: MyAdapter = MyAdapter(
            mContext, 0,
            listVOs
        )
        spinner.adapter = myAdapter
        parentlinear.setOnClickListener {

            if(flag)
            {
                parentsdetailslinear.visibility= View.VISIBLE
                parentsdetailslinear1.visibility= View.VISIBLE
                checkPassportLinear.visibility= View.VISIBLE
                downarrowImage.rotation= 180F
                //  parentsdetailslinear1.visibility=View.VISIBLE


            }
            else
            {
                parentsdetailslinear.visibility= View.GONE
                parentsdetailslinear1.visibility= View.GONE
                checkPassportLinear.visibility= View.GONE
                downarrowImage.rotation= 0F

            }
            flag = !flag
        }
        addresslinear.setOnClickListener {
            if(flag)
            {
                addressinfo_linear.visibility= View.VISIBLE
                downarrowAddress.rotation=180f


            }
            else
            {
                addressinfo_linear.visibility= View.GONE
                downarrowAddress.rotation=0f

            }
            flag = !flag
        }


        var linearLayoutManager = GridLayoutManager(mContext,2)
       // recyclerview.layoutManager = linearLayoutManager
      //  eapdaysarray.add("All")
       // eapdaysarray.add("Monday")
       // eapdaysarray.add("Tuesday")
       // eapdaysarray.add("Wednesday")
       // eapdaysarray.add("Thursday")
       //// eapdaysarray.add("Friday")
       // var newsLetterAdapter= BusEapDaysAdapter(eapdaysarray,mContext)
       // recyclerview.adapter=newsLetterAdapter
        gpslocation.setOnClickListener {
            getAddressFromLocation(latitude, longitude,area,city,street)



        }
    }



    fun bottomSelectededDayspopup(context: Context, message: String, msgHead: String)
    {
        val dialog = BottomSheetDialog(mContext, R.style.CustomBottomSheetDialog)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_days_selected, null)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        var recyclerdys = dialog.findViewById(R.id.recyclerdys) as? RecyclerView

        dialog.show()
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