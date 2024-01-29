package com.nas.alreem.activity.bus_service

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.absence.model.EarlyPickupModel
import com.nas.alreem.activity.absence.model.RequestPickupApiModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.bus_service.model.RequestBusServiceModelSubmit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class RequestBusServiceActivity : AppCompatActivity() {
    lateinit var mContext: Context
    var hour:Int=0
    var min:String=""
    var hour_new:String=""
    var new_time:String=""
    lateinit var optionsArray : ArrayList<String>
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var progressDialogAdd: ProgressBar
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var studentSpinner: LinearLayout
    lateinit var studImg: ImageView
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentClass: String
    lateinit var studentNameTxt: TextView
    lateinit var enterStratDate: TextView
    lateinit var enterTime: TextView
    lateinit var pickupName: TextView
    lateinit var submitBtn: Button
    lateinit var enterMessage: EditText
    var fromDate: String=""
    var toDate: String =""
    var totime: String =""
    lateinit var submitLayout: LinearLayout
    lateinit var myCalendar : Calendar
    lateinit var currentDate: Date
    lateinit var sdate: Date
    lateinit var edate: Date
    var elapsedDays:Long = 0
    var selectedItem = ""

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_bus_service)

        mContext=this
        initfn()

        if (ConstantFunctions.internetCheck(mContext)) {
//            progressDialog.visibility= View.VISIBLE
            callStudentListApi()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }


    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun initfn() {
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        studentSpinner = findViewById(R.id.studentSpinner)
        heading=findViewById(R.id.heading)
        heading.text= "Bus Service"
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        myCalendar= Calendar.getInstance()
        currentDate= Calendar.getInstance().time
        studentNameTxt = findViewById<TextView>(R.id.studentName)
        enterStratDate = findViewById<TextView>(R.id.enterStratDate)
        enterTime = findViewById<TextView>(R.id.enterEndDate)
        pickupName=findViewById(R.id.enterPickupname)
        studImg = findViewById<ImageView>(R.id.studImg)
        enterMessage = findViewById<EditText>(R.id.enterMessage)
        submitLayout = findViewById<LinearLayout>(R.id.submitLayout)
        submitBtn = findViewById<Button>(R.id.submitBtn)
        val spinnerList =findViewById<Spinner>(R.id.spinnerlist)
        var dropDownList: java.util.ArrayList<String> = ArrayList<String>()
        optionsArray=ArrayList()
        optionsArray.add(0,"Pick Up At Morning")
        optionsArray.add(1,"Pick Up At Evening")
        dropDownList =ArrayList()
        dropDownList.add(0, "Pick up At")
        for (i in 1..optionsArray.size) {
            dropDownList.add(optionsArray.get(i - 1).toString())
        }
        val sp_adapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(mContext, R.layout.spinner_textview, dropDownList as List<Any?>)
        spinnerList.adapter = sp_adapter
        spinnerList.setSelection(0)
        val finalDropDownList: java.util.ArrayList<*> = dropDownList
        spinnerList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedItem = parent.getItemAtPosition(position).toString()
                val optionlistSize = finalDropDownList.size - 1
                for (i in 1 until optionlistSize) {
                    if (selectedItem === finalDropDownList[i].toString()) {
                      //  reEnrollSubmit.status=(finalDropDownList[i].toString())
                    //    reEnrollSubmit.student_id=
                         //   PreferenceManager.getCCAStudentIdPosition(mContext).toString()
                      //  check[0] = 1
                    } else if (selectedItem === finalDropDownList[0]) {
                      //  reEnrollSubmit.status=("")
                      //  check[0] = 0
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        studentSpinner.setOnClickListener(){
            showStudentList(mContext,studentListArrayList)
        }
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        heading.text= ConstantWords.bus_service
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        submitBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                if(enterStratDate.text.equals(""))
                {
                    DialogFunctions.commonErrorAlertDialog("Alert","Please select Date of Early Pickup",mContext)
                }
                else{
                    if (enterTime.text.equals("")){

                        DialogFunctions.commonErrorAlertDialog("Alert","Please select your Pickup Time",mContext)
                    }else{
                        Log.e("selectedItem",selectedItem)
                        if (selectedItem.equals("") || selectedItem.equals("Pick up At")
                        ) {

                            DialogFunctions.commonErrorAlertDialog("Alert","You didn't enter any data of your child. Please Enter data and Submit",mContext)

                        }
                        else{


                            if (enterMessage.text.isEmpty()){
                                DialogFunctions.commonErrorAlertDialog("Alert","Please enter reason for early pickup",mContext)

                            } else{
                                var date_entered=enterStratDate.text
                                var date=toDate
                                var time_entered=enterTime.text
                                var time=totime
                                var pickupname_entered=pickupName.text
                                var reason_entered=enterMessage.text

                                /*callPickupSubmitApi(date,time.toString(),pickupname_entered.toString(),
                                    reason_entered.toString()
                                )*/
                                callPickupSubmitApi(date,new_time,selectedItem,
                                    reason_entered.toString()
                                )
                            }
                        }
                    }


                }


            }
        })
        enterStratDate.setOnClickListener{
            cal()
        }
        enterTime.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (enterStratDate.text.equals("")){
                    DialogFunctions.commonErrorAlertDialog("Alert","Please select Date of Early Pickup",mContext)

                }
                else{
                    val mTimePicker: TimePickerDialog
                    val mcurrentTime = Calendar.getInstance()
                    val hours = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                    val minute = mcurrentTime.get(Calendar.MINUTE)
                    //var am_pm = mcurrentTime.get(Calendar.AM_PM)
                    var am_pm:String=""

                    mTimePicker = TimePickerDialog(mContext, object : TimePickerDialog.OnTimeSetListener {
                        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                            var AM_PM: String
                            hour=hourOfDay
                            min=minute.toString()
                            var hour_n=hour.toString()

                            if (minute<10){
                                min="0"+min
                            }
                            if (hourOfDay<10){
                                hour_n="0"+hour.toString()

                            }

                            new_time=hour_n + ":" + min + ":" + "00"

                            if(hour ==0) {

                                hour = 12
                                AM_PM="AM"
                            } else if(hour<12){
                                hour = hourOfDay
                                AM_PM = "AM"
                            }
                            else if (hour >12) {
                                hour -= 12
                                AM_PM = "PM"
                            } else if (hour == 12) {

                                hour = 12
                                AM_PM = "PM"
                            } else
                                AM_PM = "AM"
                            enterTime.text = hour.toString() + ":" + min + ":" + "00"+ AM_PM
                            totime=hour.toString() + ":" + min + ":" + "00"
                        }
                    }, hour, minute,false)

                    enterTime.setOnClickListener({ v ->
                        mTimePicker.show()
                    })

                }
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun cal() {
        val mcurrentTime = android.icu.util.Calendar.getInstance()
        val year = mcurrentTime.get(android.icu.util.Calendar.YEAR)
        val month = mcurrentTime.get(android.icu.util.Calendar.MONTH)
        val day = mcurrentTime.get(android.icu.util.Calendar.DAY_OF_MONTH)
        val minDate = android.icu.util.Calendar.getInstance()
        minDate.set(android.icu.util.Calendar.HOUR_OF_DAY, 0)
        minDate.set(android.icu.util.Calendar.MINUTE, 0)
        minDate.set(android.icu.util.Calendar.SECOND, 0)
        val dpd1 = DatePickerDialog(
            this,
            R.style.MyDatePickerStyle,
            object : DatePickerDialog.OnDateSetListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    var firstday:String? = String.format("%d-%d-%d", month + 1, dayOfMonth, year)


                    var date_sel: String? = String.format("%d-%d-%d", dayOfMonth, month + 1, year)
                    var date_temp = date_sel.toString()

                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
                    val inputDateStr = date_temp
                    val date: Date = inputFormat.parse(inputDateStr)
                    val outputDateStr: String = outputFormat.format(date)
                    toDate = date_sel.toString()
                    enterStratDate.text = outputDateStr

                }
            },
            year,
            month,
            day
        )
        //enterStratDate.setOnClickListener{
        dpd1.datePicker.minDate = android.icu.util.Calendar.getInstance().timeInMillis
        dpd1.show()
//}
    }
    fun callPickupSubmitApi(date:String,time:String,pickupby:String,reason:String) {
        progressDialogAdd.visibility= View.VISIBLE

        var devicename:String= (Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT]
            .name)
        var new_date:String=""
        val inputFormat: DateFormat = SimpleDateFormat("d-m-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-mm-dd")
        val inputDateStr = date
        val date: Date = inputFormat.parse(inputDateStr)
        new_date = outputFormat.format(date)
        val token = PreferenceManager.getaccesstoken(mContext)
        val pickupSuccessBody = RequestBusServiceModelSubmit(
            PreferenceManager.getStudentID(mContext).toString(),new_date,
            time,reason,pickupby,2,devicename,"1.0")
        val call: Call<EarlyPickupModel> =
            ApiClient.getClient.requestbusservice(pickupSuccessBody, "Bearer " + token)
        call.enqueue(object : Callback<EarlyPickupModel> {
            override fun onFailure(call: Call<EarlyPickupModel>, t: Throwable) {

                progressDialogAdd.visibility= View.GONE

            }

            override fun onResponse(call: Call<EarlyPickupModel>, response: Response<EarlyPickupModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility= View.GONE


                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100) {

                            commonSuccessAlertDialog("Success","Successfully submitted your Bus service request.Please wait for Approval",mContext)
                            //Toast.makeText(nContext, "Transaction successfully completed", Toast.LENGTH_SHORT).show()

                        }else if(response.body()!!.status==136){
                            DialogFunctions.commonErrorAlertDialog("Alert","Date already Registered",mContext)
                        }
                        else
                        {
                            if (response.body()!!.status==141)
                            {
                                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),
                                    PreferenceManager.getBusnotes(mContext)!!, mContext)

                            }

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })

    }
    fun callStudentListApi()
    {
        progressDialogAdd.visibility= View.VISIBLE
        studentListArrayList= ArrayList()
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+ PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                progressDialogAdd.visibility= View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility= View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            studentListArrayList=ArrayList()
                            studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                            if (PreferenceManager.getStudentID(mContext).equals(""))
                            {
                                studentName=studentListArrayList.get(0).name
                                studentImg=studentListArrayList.get(0).photo
                                studentId=studentListArrayList.get(0).id
                                studentClass=studentListArrayList.get(0).section
                                PreferenceManager.setStudentID(mContext,studentId)
                                PreferenceManager.setStudentName(mContext,studentName)
                                PreferenceManager.setStudentPhoto(mContext,studentImg)
                                PreferenceManager.setStudentClass(mContext,studentClass)
                                studentNameTxt.text=studentName
                                if(!studentImg.equals(""))
                                {
                                    Glide.with(mContext) //1
                                        .load(studentImg)
                                        .placeholder(R.drawable.student)
                                        .error(R.drawable.student)
                                        .skipMemoryCache(true) //2
                                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                        .transform(CircleCrop()) //4
                                        .into(studImg)
                                }
                                else{
                                    studImg.setImageResource(R.drawable.student)
                                }

                            }
                            else{
                                studentName= PreferenceManager.getStudentName(mContext)!!
                                studentImg= PreferenceManager.getStudentPhoto(mContext)!!
                                studentId= PreferenceManager.getStudentID(mContext)!!
                                studentClass= PreferenceManager.getStudentClass(mContext)!!
                                studentNameTxt.text=studentName
                                if(!studentImg.equals(""))
                                {
                                    Glide.with(mContext) //1
                                        .load(studentImg)
                                        .placeholder(R.drawable.student)
                                        .error(R.drawable.student)
                                        .skipMemoryCache(true) //2
                                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                        .transform(CircleCrop()) //4
                                        .into(studImg)
                                }
                                else{
                                    studImg.setImageResource(R.drawable.student)
                                }
                            }

                        }
                        else
                        {

                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
    fun showStudentList(context: Context, mStudentList : ArrayList<StudentList>)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_student_list)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var studentListRecycler = dialog.findViewById(R.id.studentListRecycler) as RecyclerView
        iconImageView.setImageResource(R.drawable.boy)
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            btn_dismiss.setBackgroundDrawable(
                mContext.resources.getDrawable(R.drawable.button_new)
            )
        } else {
            btn_dismiss.background = mContext.resources.getDrawable(R.drawable.button_new)
        }

        studentListRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentListRecycler.layoutManager = llm
        if(mStudentList.size>0)
        {
            val studentAdapter = StudentListAdapter(mContext,mStudentList)
            studentListRecycler.adapter = studentAdapter
        }

        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                //progressDialogAdd.visibility=View.VISIBLE

                studentName=studentListArrayList.get(position).name
                studentImg=studentListArrayList.get(position).photo
                studentId=studentListArrayList.get(position).id
                studentClass=studentListArrayList.get(position).section
                PreferenceManager.setStudentID(mContext,studentId)
                PreferenceManager.setStudentName(mContext,studentName)
                PreferenceManager.setStudentPhoto(mContext,studentImg)
                PreferenceManager.setStudentClass(mContext,studentClass)
                studentNameTxt.text=studentName
                if(!studentImg.equals(""))
                {
                    Glide.with(mContext) //1
                        .load(studentImg)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(studImg)
                }
                else
                {
                    studImg.setImageResource(R.drawable.student)
                }
                //progressDialogAdd.visibility = View.VISIBLE

                dialog.dismiss()
            }
        })
        dialog.show()
    }
    fun commonSuccessAlertDialog(heading:String,Message:String,context: Context)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_success_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var messageTxt = dialog.findViewById(R.id.messageTxt) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        messageTxt.text = Message
        alertHead.text = heading
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            finish()
        }
        dialog.show()

    }
}