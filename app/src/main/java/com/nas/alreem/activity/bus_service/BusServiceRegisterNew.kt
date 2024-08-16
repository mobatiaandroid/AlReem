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
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nas.alreem.BuildConfig
import com.nas.alreem.R
import com.nas.alreem.activity.absence.model.EarlyPickupModel
import com.nas.alreem.activity.bus_service.model.DetailsResponseModel
import com.nas.alreem.activity.bus_service.model.RegularBusSubmitModel
import com.nas.alreem.activity.bus_service.model.StudentDetailsModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.model.InfoListModel
import com.nas.alreem.activity.primary.model.ComingUpResponseModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.PreferenceManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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
    lateinit var parentsdetailslinear: LinearLayout

    //  lateinit var parentsdetailslinear1 : LinearLayout
    // lateinit var checkPassportLinear : LinearLayout
    lateinit var addresslinear: RelativeLayout
    lateinit var parentlinear: RelativeLayout
    lateinit var addressinfo_linear: LinearLayout
    lateinit var downarrowImage: ImageView
    lateinit var downarrowAddress: ImageView

    //   lateinit var droppoint : EditText
    //   lateinit var pickuppoint : EditText
    lateinit var gpslocation: ImageView
    lateinit var street: EditText
    lateinit var area: EditText
    lateinit var city: EditText
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
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    lateinit var spinnerList: Spinner
    lateinit var dropDownList: ArrayList<String>
    lateinit var optionsArray: ArrayList<String>
    lateinit var optionArray: ArrayList<String>
    lateinit var submit: Button
    var relationship = ""
    lateinit var contact1: EditText

    var selectedItem = ""
    lateinit var signature_pad: SignaturePad
    lateinit var signatureBitmap: Bitmap
    lateinit var signatureFile: File
    var flag: Boolean = true
    lateinit var btn_left: ImageView
    lateinit var landmarkedittext: EditText
    lateinit var otherapprovered: EditText
    lateinit var buildingname: EditText
    lateinit var doorno: EditText
    lateinit var selectroot: TextView
    var onway = ""
    var twoway = ""
    lateinit var termsconditionImg: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_reg_details_forms)
        mContext = this
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermission()
        initfn()
        if (ConstantFunctions.internetCheck(mContext)) {
            callStudentDetails()
        } else {

        }

    }

    private fun callStudentDetails() {
        progressDialogAdd.visibility = View.VISIBLE

        optionArray = ArrayList()

        var studentdetailsmodel = StudentDetailsModel(PreferenceManager.getStudentID(mContext)!!)
        val call: Call<DetailsResponseModel> = ApiClient.getClient.student_details(
            "Bearer " + PreferenceManager.getaccesstoken(mContext),
            studentdetailsmodel
        )
        call.enqueue(object : Callback<DetailsResponseModel> {
            override fun onFailure(call: Call<DetailsResponseModel>, t: Throwable) {
                progressDialogAdd.visibility = View.GONE

            }

            override fun onResponse(
                call: Call<DetailsResponseModel>,
                response: Response<DetailsResponseModel>
            ) {
                val responsedata = response.body()
                progressDialogAdd.visibility = View.GONE

                if (response.isSuccessful()) {
//
                    val apiResponse: DetailsResponseModel? = response.body()

                    student_name_text.setText(responsedata!!.responseArray.student_detail.name)
                    student_year_text.setText(responsedata!!.responseArray.student_detail.section)
                    student_section_text.setText(responsedata!!.responseArray.student_detail.classs)
                    student_date_text.setText(responsedata!!.responseArray.student_detail.enrolmentDate)
                    student_esis_text.setText(responsedata!!.responseArray.student_detail.esis_number)
                    parenr1name.setText(responsedata!!.responseArray.parent1_name)
                    parent1mobNo.setText(responsedata!!.responseArray.parent1_mobile)
                    parent1email.setText(responsedata!!.responseArray.parent1_email)
                    //   parent2name.setText(responsedata!!.responseArray.parent2_name)
                    //   parent2mobno.setText(responsedata!!.responseArray.parent2_mobile)
                    //  parent2email.setText(responsedata!!.responseArray.parent2_email)

                    relationship = responsedata!!.responseArray.parent1_relationship
                    area.setText(responsedata!!.responseArray.parent1_country)
                    street.setText(responsedata!!.responseArray.parent1_country)
                    contact1.setText(responsedata!!.responseArray.parent1_additionaltelephone)
                    if (responsedata.responseArray.terms.size > 0) {
                        for (i in responsedata!!.responseArray.terms.indices) {
                            optionArray.add(responsedata.responseArray.terms[i].name)
                        }
                        Log.e("response", optionArray.size.toString())
                        optionsArray = ArrayList()
                        optionsArray.addAll(optionArray)
                        Log.e("arraysizeoption", optionsArray.size.toString())

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
                                    /* for (i in subQuestion.indices){
                                 if (subQuestion[position].equals("")){

                                 }else{

                                 }
                             }*/
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            }


                    } else {
                        showDialogueWithOkSuccess(mContext, "No Term Found", "Alert")

                    }
                }

            }

        })
    }

    private fun initfn() {

        heading = findViewById(R.id.heading)
        heading.text = "Regular Bus Service Registration Form"
        parentsdetailslinear = findViewById(R.id.parentsdetailslinear)
        progressDialogAdd = findViewById(R.id.progressDialogAdd)

        //parentsdetailslinear1=findViewById(R.id.parentsdetailslinear1)
        //checkPassportLinear=findViewById(R.id.checkPassportLinear)
        addresslinear = findViewById(R.id.addresslinear)
        addressinfo_linear = findViewById(R.id.addressinfo_linear)
        parentlinear = findViewById(R.id.parentlinear)
        downarrowImage = findViewById(R.id.downarrowImage)
        downarrowAddress = findViewById(R.id.downarrowAddress)
        //  droppoint = findViewById(R.id.drop)
        //  pickuppoint = findViewById(R.id.pickup)
        gpslocation = findViewById(R.id.gpslocation)
        parenr1name = findViewById(R.id.fathersname)
        parent1mobNo = findViewById(R.id.fathersno)
        parent1email = findViewById(R.id.fathersmail)
        contact1 = findViewById(R.id.contact1)

        //parent2name = findViewById(R.id.mothersname)
        // parent2mobno = findViewById(R.id.mothersno)
        // parent2email = findViewById(R.id.mothersemail)
        submit = findViewById(R.id.submit)

        student_name_text = findViewById(R.id.student_name_text)
        student_year_text = findViewById(R.id.student_year_text)
        student_section_text = findViewById(R.id.student_section_text)
        student_date_text = findViewById(R.id.student_date_text)
        student_esis_text = findViewById(R.id.student_esis_text)
        street = findViewById(R.id.street)
        area = findViewById(R.id.area)
        city = findViewById(R.id.city)
        spinnerList = findViewById<Spinner>(R.id.spinnerlist)
        btn_left = findViewById(R.id.btn_left)
        logoClickImgView = findViewById(R.id.logoClickImgView)

        landmarkedittext = findViewById(R.id.landmarkedittext)
        otherapprovered = findViewById(R.id.otherapprovered)
        buildingname = findViewById(R.id.buildingname)
        doorno = findViewById(R.id.doorno)
        selectroot = findViewById(R.id.selectroot)
        termsconditionImg = findViewById(R.id.termsconditionImg)
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        val yesNoRadioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val yesButton = findViewById<RadioButton>(R.id.radioYes)
        val noButton = findViewById<RadioButton>(R.id.radioNo)
        val radiooneway = findViewById<RadioGroup>(R.id.radiooneway)
        val radiopickup = findViewById<RadioButton>(R.id.radiopickup)
        val radiodrop = findViewById<RadioButton>(R.id.radiodrop)

        yesButton.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                onway = "0"
                selectroot.visibility = View.VISIBLE
                radiooneway.visibility = View.VISIBLE
            }
        }
        noButton.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                twoway = "1"
                selectroot.visibility = View.GONE
                radiooneway.visibility = View.GONE
            }
        }
        gpslocation.setOnClickListener {
            getAddressFromLocation(latitude, longitude, area, city, street)


        }
        parentlinear.setOnClickListener {

            if (flag) {
                parentsdetailslinear.visibility = View.VISIBLE
                // parentsdetailslinear1.visibility=View.VISIBLE
                // checkPassportLinear.visibility=View.VISIBLE
                downarrowImage.rotation = 180F
                //  parentsdetailslinear1.visibility=View.VISIBLE


            } else {
                parentsdetailslinear.visibility = View.GONE
                // parentsdetailslinear1.visibility=View.GONE
                // checkPassportLinear.visibility=View.GONE
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

        signature_pad = findViewById(R.id.signature_pad)


        submit.setOnClickListener {
            if (signature_pad.isEmpty()) {
                // Prompt the user to enter a signature
                Toast.makeText(
                    mContext,
                    getString(R.string.enter_signature_prompt),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (selectedItem.equals("Please Select")) {
                Toast.makeText(mContext, "Please Select Term", Toast.LENGTH_SHORT).show()

            }
            /* else if(droppoint.text.isEmpty())
             {
                 Toast.makeText(mContext, "Please Enter Drop point", Toast.LENGTH_SHORT).show()

             }
             else if(pickuppoint.text.isEmpty())
             {
                 Toast.makeText(mContext, "Please Enter Pickup point", Toast.LENGTH_SHORT).show()

             }*/
            else if (!termsconditionImg.isChecked) {
                Toast.makeText(
                    mContext,
                    "Please agree to terms and conditions",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!yesButton.isChecked || !noButton.isChecked) {
                Toast.makeText(mContext, "Please Select One Way Or Two Way", Toast.LENGTH_SHORT)
                    .show()
            } else if (landmarkedittext.text.isEmpty()) {
                Toast.makeText(mContext, "Please Enter LandMark ", Toast.LENGTH_SHORT).show()

            } else if (buildingname.text.isEmpty()) {
                Toast.makeText(mContext, "Please Enter Building Name ", Toast.LENGTH_SHORT).show()

            } else if (doorno.text.isEmpty()) {
                Toast.makeText(mContext, "Please Enter Door No ", Toast.LENGTH_SHORT).show()

            } else if (city.text.isEmpty()) {
                Toast.makeText(mContext, "Please Enter Door No ", Toast.LENGTH_SHORT).show()

            } else if (area.text.isEmpty()) {
                Toast.makeText(mContext, "Please Enter Door No ", Toast.LENGTH_SHORT).show()

            } else if (street.text.isEmpty()) {
                Toast.makeText(mContext, "Please Enter Door No ", Toast.LENGTH_SHORT).show()

            } else {
                if (yesButton.isChecked) {
                    if (!radiopickup.isChecked || !radiodrop.isChecked) {
                        Toast.makeText(
                            mContext,
                            "Please Select your one way path",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        signatureBitmap = signature_pad.getSignatureBitmap()
                        signatureFile = bitmapToFile(signatureBitmap)
                        submitApi(signatureFile)
                    }
                } else if (noButton.isChecked) {
                    signatureBitmap = signature_pad.getSignatureBitmap()
                    signatureFile = bitmapToFile(signatureBitmap)
                    submitApi(signatureFile)
                }


            }

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

    private fun submitApi(signatureFile: File) {
        progressDialogAdd.visibility = View.VISIBLE

        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        var device = manufacturer + model
        val versionName: String = BuildConfig.VERSION_NAME

        var attachment1: MultipartBody.Part? = null
        val pickuptext =
            RequestBody.create("text/plain".toMediaTypeOrNull(), " pickuppoint.text.toString()")
        val droptext =
            RequestBody.create("text/plain".toMediaTypeOrNull(), "droppoint.text.toString()")
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
            RequestBody.create("text/plain".toMediaTypeOrNull(), area.text.toString())
        val parentaddress =
            RequestBody.create("text/plain".toMediaTypeOrNull(), street.text.toString())
        val term = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedItem)
        val tyeppp = RequestBody.create("text/plain".toMediaTypeOrNull(), "1")
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


        val call: Call<EarlyPickupModel> = ApiClient.getClient.request_for_bus_service(
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
            term,
            tyeppp,
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
                progressDialogAdd.visibility = View.GONE

                if (response.isSuccessful) {
                    if (response.body()!!.status == 100) {
                        showDialogueWithOkSuccess(mContext, "Successfully Submitted", "Alert")

                    } else if (response.body()!!.status == 136) {
                        showDialogueWithOkSuccess(mContext, "Already Submitted", "Alert")

                    }
                }
            }

            override fun onFailure(call: Call<EarlyPickupModel>, t: Throwable) {
                progressDialogAdd.visibility = View.GONE

                //  progressDialogP.dismiss()

            }
        })
    }

    fun showDialogueWithOkSuccess(
        context: Context,
        message: String,
        msgHead: String,
        status: String
    ) {
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