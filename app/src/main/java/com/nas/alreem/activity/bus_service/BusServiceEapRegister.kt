package com.nas.alreem.activity.bus_service

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.nas.alreem.BuildConfig
import com.nas.alreem.R
import com.nas.alreem.activity.absence.model.EarlyPickupModel
import com.nas.alreem.activity.bus_service.adapter.EapDaysListAdapter
import com.nas.alreem.activity.bus_service.model.DateListArray
import com.nas.alreem.activity.bus_service.model.DeatilsResponseArray
import com.nas.alreem.activity.bus_service.model.DetailsResponseModel
import com.nas.alreem.activity.bus_service.model.EAPList
import com.nas.alreem.activity.bus_service.model.StateVO
import com.nas.alreem.activity.bus_service.model.StateVj
import com.nas.alreem.activity.bus_service.model.StudentDetailsModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.model.InfoListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.PreferenceManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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
    lateinit var eapdaysarray: ArrayList<String>
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // lateinit var gpslocationtext : TextView
    lateinit var gpslocation: ImageView
    lateinit var street: EditText
    lateinit var area: EditText
    lateinit var city: EditText
    lateinit var landmarkedittext:EditText
    lateinit var otherapprovered:EditText
    lateinit var buildingname:EditText
    lateinit var doorno:EditText

    lateinit var parentsdetailslinear: LinearLayout

    // lateinit var parentsdetailslinear1 : LinearLayout
    // lateinit var checkPassportLinear : LinearLayout
    lateinit var addresslinear: RelativeLayout
    lateinit var parentlinear: RelativeLayout
    lateinit var addressinfo_linear: LinearLayout
    lateinit var downarrowImage: ImageView
    lateinit var downarrowAddress: ImageView
    var flag: Boolean = true

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    lateinit var select_qualification: ArrayList<DateListArray>
    var days: ArrayList<String> = ArrayList()

    lateinit var selecteapdays: TextView
    lateinit var selecteddayarel: RelativeLayout
    var listVOs: ArrayList<StateVO> = ArrayList()
    var listVOy: ArrayList<StateVj> = ArrayList()

    lateinit var spinnerList: Spinner
    lateinit var dropDownList: ArrayList<String>
    lateinit var optionsArray: ArrayList<String>
    lateinit var optionArray: ArrayList<String>
    var selectedItem = ""
    var selectedItemid = ""

    lateinit var student_name_text: TextView
    lateinit var student_year_text: TextView
    lateinit var student_section_text: TextView
    lateinit var student_date_text: TextView
    lateinit var student_esis_text: TextView
    lateinit var parenr1name: EditText
    lateinit var parent1mobNo: EditText
    lateinit var parent1email: EditText

    // lateinit var parent2name : EditText
    // lateinit var parent2email : EditText
    // lateinit var parent2mobno : EditText
    lateinit var droppoint: EditText
    lateinit var pickuppoint: EditText
    lateinit var submit: Button
    lateinit var signature_pad: SignaturePad
    lateinit var signatureBitmap: Bitmap
    lateinit var signatureFile: File
    lateinit var subQuestion: ArrayList<String>
    lateinit var btn_left: ImageView
    var relationship = ""
    lateinit var contact1: EditText
    var dateListArrayTemp: ArrayList<DateListArray> = ArrayList()
    var eapDetailsArray: ArrayList<EAPList> = ArrayList()
    lateinit var termsconditionImg: CheckBox



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_service_reg_eap_layout)
        mContext = this
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()
        initfn()
        // progressDialogAdd.visibility = View.VISIBLE
        if (ConstantFunctions.internetCheck(mContext)) {
            callEapBusDetails()
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
        heading = findViewById(R.id.heading)
        heading.text = "EAP Registration Form"
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        termsconditionImg = findViewById(R.id.termsconditionImg)

        parentsdetailslinear = findViewById(R.id.parentsdetailslinear)
        contact1 = findViewById(R.id.contact1)
        // parentsdetailslinear1=findViewById(R.id.parentsdetailslinear1)
        //checkPassportLinear=findViewById(R.id.checkPassportLinear)
        addresslinear = findViewById(R.id.addresslinear)
        addressinfo_linear = findViewById(R.id.addressinfo_linear)
        parentlinear = findViewById(R.id.parentlinear)
        downarrowImage = findViewById(R.id.downarrowImage)
        downarrowAddress = findViewById(R.id.downarrowAddress)
        selecteapdays = findViewById(R.id.selecteapdays)
        selecteddayarel = findViewById(R.id.relative)
        spinnerList = findViewById<Spinner>(R.id.spinnerlist)

        droppoint = findViewById(R.id.drop)
        pickuppoint = findViewById(R.id.pickup)
        gpslocation = findViewById(R.id.gpslocation)
        parenr1name = findViewById(R.id.fathersname)
        parent1mobNo = findViewById(R.id.fathersno)
        parent1email = findViewById(R.id.fathersmail)
        // parent2name = findViewById(R.id.mothersname)
        //  parent2mobno = findViewById(R.id.mothersno)
        // parent2email = findViewById(R.id.mothersemail)

        student_name_text = findViewById(R.id.student_name_text)
        student_year_text = findViewById(R.id.student_year_text)
        student_section_text = findViewById(R.id.student_section_text)
        student_date_text = findViewById(R.id.student_date_text)
        student_esis_text = findViewById(R.id.student_esis_text)
        submit = findViewById(R.id.submit)
        signature_pad = findViewById(R.id.signature_pad)
        btn_left = findViewById(R.id.btn_left)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        landmarkedittext= findViewById(R.id.landmarkedittext)
        otherapprovered=findViewById(R.id.otherapprovered)
        buildingname=findViewById(R.id.buildingname)
        doorno=findViewById(R.id.doorno)
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        selecteddayarel.setOnClickListener {
            if (selectedItem.equals("Please Select")) {
                Toast.makeText(mContext, "Please Select Eap", Toast.LENGTH_SHORT).show()
            } else {
                //selecteapdays.setText("")
                bottomSelectededDayspopup(mContext)

            }
        }

        parentlinear.setOnClickListener {

            if (flag) {
                parentsdetailslinear.visibility = View.VISIBLE
                //   parentsdetailslinear1.visibility= View.VISIBLE
                //checkPassportLinear.visibility= View.VISIBLE
                downarrowImage.rotation = 180F
                //  parentsdetailslinear1.visibility=View.VISIBLE


            } else {
                parentsdetailslinear.visibility = View.GONE
                // parentsdetailslinear1.visibility= View.GONE
                // checkPassportLinear.visibility= View.GONE
                downarrowImage.rotation = 0F

            }
            flag = !flag
        }
        addresslinear.setOnClickListener {
            if (flag) {
                addressinfo_linear.visibility = View.VISIBLE
                downarrowAddress.rotation = 180f


            } else {
                addressinfo_linear.visibility = View.GONE
                downarrowAddress.rotation = 0f

            }
            flag = !flag
        }


        submit.setOnClickListener {
            if (selectedItem.equals("Please Select")) {
                Toast.makeText(mContext, "Please Select Eap", Toast.LENGTH_SHORT).show()

            } else if (droppoint.text.isEmpty()) {
                Toast.makeText(mContext, "Please Enter Drop point", Toast.LENGTH_SHORT).show()

            } else if (pickuppoint.text.isEmpty()) {
                Toast.makeText(mContext, "Please Enter Pickup point", Toast.LENGTH_SHORT).show()

            }
            else if (selecteapdays.text.equals("Please Select")) {
                Toast.makeText(mContext, "Please Selected Dates", Toast.LENGTH_SHORT).show()

            }


            else if(doorno.text.isEmpty())
            {
                Toast.makeText(mContext, "Please Enter Door No ", Toast.LENGTH_SHORT).show()

            }
            else if(buildingname.text.isEmpty())
            {
                Toast.makeText(mContext, "Please Enter Building Name ", Toast.LENGTH_SHORT).show()

            }
            else if(street.text.isEmpty())
            {
                Toast.makeText(mContext, "Please Enter Street ", Toast.LENGTH_SHORT).show()

            }
            else if(area.text.isEmpty())
            {
                Toast.makeText(mContext, "Please Enter Area ", Toast.LENGTH_SHORT).show()

            }
            else if(city.text.isEmpty())
            {
                Toast.makeText(mContext, "Please Enter City ", Toast.LENGTH_SHORT).show()

            }
            else if(landmarkedittext.text.isEmpty())
            {
                Toast.makeText(mContext, "Please Enter LandMark ", Toast.LENGTH_SHORT).show()

            }
            else if (!termsconditionImg.isChecked) {
                Toast.makeText(
                    mContext,
                    "Please agree to terms and conditions",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (signature_pad.isEmpty()) {
                // Prompt the user to enter a signature
                Toast.makeText(
                    mContext,
                    getString(R.string.enter_signature_prompt),
                    Toast.LENGTH_SHORT
                ).show()
            }

            else {
                signatureBitmap = signature_pad.getSignatureBitmap()
                signatureFile = bitmapToFile(signatureBitmap)

                submitApi(signatureFile)

            }

        }
        gpslocation.setOnClickListener {
            getAddressFromLocation(latitude, longitude, area, city, street)


        }
    }

    private fun bitmapToFile(bitmap: Bitmap): File {
        val signatureFile = File(mContext.externalCacheDir, "signature.png")
        try {
            // Write the bitmap to the file
            val fos = FileOutputStream(signatureFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return signatureFile
    }

    private fun bottomSelectededDayspopup(activity: Context) {
        val bottomSheetDialog = BottomSheetDialog(activity, R.style.BottomSheetDialogTheme)
        val layout: View =
            LayoutInflater.from(activity).inflate(R.layout.bottom_sheet_days_selected, null)
        bottomSheetDialog.setContentView(layout)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        //  val payTotalView = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.payTotalView)
        var recyclerdys = bottomSheetDialog.findViewById(R.id.recyclerdys) as? RecyclerView
        var close = bottomSheetDialog.findViewById(R.id.close) as? ImageView


        var linearLayoutManager = LinearLayoutManager(mContext)
        recyclerdys!!.layoutManager = linearLayoutManager

        var newsLetterAdapter = EapDaysListAdapter(mContext, listVOs, selecteapdays)
        recyclerdys!!.adapter = newsLetterAdapter
        newsLetterAdapter.notifyDataSetChanged()
        close!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        recyclerdys!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    fun showDialogueWithOkSuccess(context: Context, message: String, msgHead: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as? ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as? TextView
        var text_dialog = dialog.findViewById(R.id.messageTxt) as? TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog?.text = message
        alertHead?.text = msgHead
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            val intent = Intent(mContext, BusServiceRegisterupdate::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
        dialog.show()
    }

    private fun callEapBusDetails() {
        progressDialogAdd.visibility=View.VISIBLE

        optionArray = ArrayList()
        select_qualification = ArrayList()
        // select_qualification= ArrayList()
        // days = ArrayList()

        var studentdetailsmodel = StudentDetailsModel(PreferenceManager.getStudentID(mContext)!!)
        val call: Call<DetailsResponseModel> = ApiClient.getClient.eap_bus_details(
            "Bearer " + PreferenceManager.getaccesstoken(mContext),
            studentdetailsmodel
        )
        call.enqueue(object : Callback<DetailsResponseModel> {
            override fun onFailure(call: Call<DetailsResponseModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE

            }

            override fun onResponse(
                call: Call<DetailsResponseModel>,
                response: Response<DetailsResponseModel>
            ) {
                val responsedata = response.body()
                if (response.isSuccessful()) {
//
                    progressDialogAdd.visibility = View.GONE

                    val apiResponse: DetailsResponseModel? = response.body()

                    Log.e("responseArray", response.body()!!.responseArray.toString())

                    student_name_text.setText(responsedata!!.responseArray.student_detail.name)
                    student_year_text.setText(responsedata!!.responseArray.student_detail.section)
                    student_section_text.setText(responsedata!!.responseArray.student_detail.classs)
                    student_date_text.setText(responsedata!!.responseArray.student_detail.enrolmentDate)
                    student_esis_text.setText(responsedata!!.responseArray.student_detail.esis_number)
                    parenr1name.setText(responsedata!!.responseArray.parent1_name)
                    parent1mobNo.setText(responsedata!!.responseArray.parent1_mobile)
                    parent1email.setText(responsedata!!.responseArray.parent1_email)
                    otherapprovered.setText(responsedata!!.responseArray.parent1_relationship)
                    //parent2name.setText(responsedata!!.responseArray.parent2_name)
                    //parent2mobno.setText(responsedata!!.responseArray.parent2_mobile)
                    // parent2email.setText(responsedata!!.responseArray.parent2_email)
                    if (responsedata.responseArray.eap_details.size > 0) {
                        eapDetailsArray.add(EAPList(0, "", "", "", ArrayList()))
                        for (i in responsedata!!.responseArray.eap_details.indices) {
                            eapDetailsArray.add(responsedata!!.responseArray.eap_details[i])
                        }
                        // Log.e("response", apiResponse.toString())
                        //  Log.e("response", responsedata!!.responseArray.terms.size.toString())
                        relationship = responsedata!!.responseArray.parent1_relationship
                        area.setText(responsedata!!.responseArray.parent1_country)
                        street.setText(responsedata!!.responseArray.parent1_address1)
                        contact1.setText(responsedata!!.responseArray.parent1_additionaltelephone)
                        for (i in responsedata!!.responseArray.eap_details.indices) {
                            optionArray.add(responsedata.responseArray.eap_details[i].title)

                        }

                        subQuestion = ArrayList()
                        subQuestion.add("")
                        for (i in 1..responsedata!!.responseArray.eap_details.size) {
                            if (responsedata!!.responseArray.eap_details[i - 1].cca_days_id == null) {
                                subQuestion.add("")
                            } else {
                                subQuestion.add(responsedata!!.responseArray.eap_details[i - 1].cca_days_id.toString())
                            }

                        }
                        // Log.e("response", optionArray.size.toString())
                        optionsArray = ArrayList()
                        optionsArray.addAll(optionArray)
                        // Log.e("arraysizeoption", optionsArray.size.toString())

                        dropDownList = ArrayList()

                        dropDownList.add(0, "Please Select")
                        for (i in 1..optionsArray.size) {
//        for (i in optionsArray.indices) {
                            dropDownList.add(optionsArray.get(i - 1).toString())
                        }
                        val sp_adapter: ArrayAdapter<*> =
                            ArrayAdapter<Any?>(
                                mContext,
                                R.layout.spinner_textview,
                                dropDownList as List<Any?>
                            )
                        spinnerList.adapter = sp_adapter

                        spinnerList.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View,
                                    position: Int,
                                    id: Long
                                ) {
                                    selectedItem = parent.getItemAtPosition(position).toString()
                                    selectedItemid = subQuestion[position]
                                    select_qualification.clear()
//                                dateListArrayTemp = responsedata.responseArray.eap_details[position].date_lists
                                    dateListArrayTemp = eapDetailsArray[position].date_lists

                                    for (j in dateListArrayTemp.indices) {
                                        select_qualification.add(dateListArrayTemp[j])
                                        Log.e(
                                            "select_qualification",
                                            select_qualification.size.toString()
                                        )
                                        Log.e("days", days.size.toString())
                                    }

                                    /*  for (n in 0 until days.size) {
                                          val stateVj = StateVj()
                                          stateVj.title = days[n]
                                          listVOy.add(stateVj)
                                      }*/
                                    listVOs.clear()
                                    for (i in 0 until select_qualification.size) {
                                        val stateVO = StateVO()
                                        stateVO.title = select_qualification[i].date
                                        stateVO.dates = select_qualification[i].day
                                        stateVO.isSelected = false
                                        listVOs.add(stateVO)
                                    }

                                    //  }


                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            }

                    }

                    else{
                        showDialogueWithOkSuccess(mContext,"No Eap Found","Alert")

                    }

                }

            }

        })
    }

    private fun submitApi(signatureFile: File) {
        progressDialogAdd.visibility=View.VISIBLE

        val gson = Gson()
        val jsonArrayString = gson.toJson(PreferenceManager.geteapselecteddates(mContext))
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        var device = manufacturer + model
        val versionName: String = BuildConfig.VERSION_NAME

// Use the jsonArrayString
        println(jsonArrayString)
        var attachment1: MultipartBody.Part? = null
        val pickuptext =
            RequestBody.create("text/plain".toMediaTypeOrNull(), pickuppoint.text.toString())
        val droptext =
            RequestBody.create("text/plain".toMediaTypeOrNull(), droppoint.text.toString())
        val classname = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            student_year_text.text.toString()
        )
        val email =
            RequestBody.create("text/plain".toMediaTypeOrNull(), parent1email.text.toString())
        val parent1name =
            RequestBody.create("text/plain".toMediaTypeOrNull(), parenr1name.text.toString())
        val parent1relationship = RequestBody.create("text/plain".toMediaTypeOrNull(), relationship)
        val parentmobile =
            RequestBody.create("text/plain".toMediaTypeOrNull(), parent1mobNo.text.toString())
        val parent1additionaltele =
            RequestBody.create("text/plain".toMediaTypeOrNull(), contact1.text.toString())
        val parent1country =
            RequestBody.create("text/plain".toMediaTypeOrNull(), landmarkedittext.text.toString())
        val parentaddress =
            RequestBody.create("text/plain".toMediaTypeOrNull(), doorno.text.toString()+","+ street.text.toString()+","+buildingname.text.toString()+
                    "," +area.text.toString()+","+city.text.toString())
        val item = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedItem)

        val cca_id = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedItemid)
        val cca_dates = RequestBody.create("application/json".toMediaTypeOrNull(), jsonArrayString)
        println("ccadates" + cca_dates)
        Log.e("array", cca_dates.toString())
        val tyeppp = RequestBody.create("text/plain".toMediaTypeOrNull(), "2")
        val device_type = RequestBody.create("text/plain".toMediaTypeOrNull(), "2")
        val device_name = RequestBody.create("text/plain".toMediaTypeOrNull(), device)
        val app_version = RequestBody.create("text/plain".toMediaTypeOrNull(), versionName)


        val student_id = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            PreferenceManager.getStudentID(mContext).toString()
        )
        if (signatureFile.length() > 0) {
            val requestFile1 =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), signatureFile)
            attachment1 =
                MultipartBody.Part.createFormData("signature", signatureFile.name, requestFile1)
        }


        val frontImagePart: MultipartBody.Part? = attachment1


        val call: Call<EarlyPickupModel> = ApiClient.getClient.request_for_bus_service_eap(
            "Bearer " + PreferenceManager.getaccesstoken(mContext),
            student_id,
            pickuptext,
            droptext,
            classname,
            parent1name,
            email,
            parent1relationship,
            parentmobile,
            parent1additionaltele,
            parent1country,
            parentaddress,
            tyeppp,
            item,
            cca_dates,
            cca_id,
            device_type,
            device_name,
            app_version,
            frontImagePart
        )
        call.enqueue(object : Callback<EarlyPickupModel> {
            override fun onResponse(
                call: Call<EarlyPickupModel>,
                response: Response<EarlyPickupModel>
            ) {
                progressDialogAdd.visibility=View.GONE

                if (response.isSuccessful) {
                    if (response.body()!!.status == 100) {
                        showDialogueWithOkSuccess(mContext, "Successfully Submitted", "Alert")

                    } else if (response.body()!!.status == 136) {
                        showDialogueWithOkSuccess(mContext, "Already Submitted", "Alert")

                    }
                }
            }

            override fun onFailure(call: Call<EarlyPickupModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE

                //  progressDialogP.dismiss()

            }
        })
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
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getCurrentLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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
            == PackageManager.PERMISSION_GRANTED
        ) {

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
                Log.e("Address", fullAddress)

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