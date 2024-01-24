package com.nas.alreem.activity.shop_new

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.Addorder_Activity
import com.nas.alreem.activity.canteen.model.DateModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.canteen.model.CanteenBannerResponseModel
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PreOrderActivity_new :AppCompatActivity() {
    lateinit var nContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview: RecyclerView
    lateinit var back: ImageView
    lateinit var studImg: ImageView
    lateinit var studentNameTxt: TextView
    lateinit var studentSpinner: LinearLayout
    lateinit var buttonLinear: LinearLayout
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentClass: String
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var studentlist: ArrayList<String>
    lateinit var studentname: TextView
    lateinit var dropdown: LinearLayout
    lateinit var title: TextView
    lateinit var add_order: RelativeLayout
    lateinit var my_orders: RelativeLayout
    lateinit var order_history: RelativeLayout
    lateinit var progressDialog: ProgressBar
    lateinit var progress: ProgressBar
    lateinit var contactEmail:String
    lateinit var email_icon: ImageView
    lateinit var description: TextView
    lateinit var bannerImg:ImageView
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
    private val pattern = "^([a-zA-Z ]*)$"

    var time_exeed: String = ""
    var datetime: String = ""
    var apiCall:Int=0
    var mDateArrayList = ArrayList<DateModel>()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canteen_preorder_new)
        initfn()
        if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
            callStudentListApi()
            callGetShopBanner()
        } else {
            DialogFunctions.showInternetAlertDialog(nContext)
        }


        onclick()
    }
    private fun initfn() {
        nContext = this
        logoClickImg=findViewById(R.id.logoclick)
        back = findViewById(R.id.back)
        studImg = findViewById<ImageView>(R.id.imagicon)
        studentNameTxt = findViewById<TextView>(R.id.studentName)
        studentSpinner = findViewById<LinearLayout>(R.id.studentSpinner)
        studentlist = ArrayList()
        dropdown = findViewById(R.id.studentSpinner)
        progressDialog = findViewById(R.id.progressDialog)
        progress =findViewById(R.id.progressDialogAdd)!!
        add_order = findViewById(R.id.addOrderRelative)
       my_orders = findViewById(R.id.myOrderRelative)
        order_history = findViewById(R.id.orderHistoryRelative)
        buttonLinear = findViewById(R.id.buttonLinear)
        title = findViewById(R.id.textViewtitle)
        email_icon = findViewById(R.id.email_icon)!!
        description =findViewById(R.id.description)!!
        bannerImg = findViewById(R.id.bannerImage)!!


        title.text = "Shop-Order"
        email_icon.setOnClickListener {
            showSendEmailDialog()
        }
        back.setOnClickListener {
            finish()
        }
        logoClickImg.setOnClickListener {
            val intent = Intent(nContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }


        studentSpinner.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                showStudentList(nContext,studentListArrayList)

            }
        })

    }
    private fun onclick() {
        add_order.setOnClickListener {

            val intent = Intent(nContext, Addorder_Activity_new::class.java)
            intent.putExtra("date_list",mDateArrayList)
            startActivity(intent)

        }

        order_history.setOnClickListener {
            PreferenceManager.setStudentID(nContext,"")
                val intent = Intent(nContext, InvoiceListingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)

        }
        my_orders.setOnClickListener {
            val intent = Intent(nContext, OrderhistoryActivityNew::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
    private fun showSendEmailDialog()
    {
        val dialog = Dialog(nContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_send_email)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val btn_submit = dialog.findViewById<Button>(R.id.submitButton)
        val btn_cancel = dialog.findViewById<Button>(R.id.cancelButton)
        val text_dialog = dialog.findViewById<EditText?>(R.id.text_dialog)
        val text_content = dialog.findViewById<EditText>(R.id.text_content)

        btn_cancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        btn_submit.setOnClickListener {
            if (text_dialog.text.toString().trim().equals("")) {
                DialogFunctions.commonErrorAlertDialog(
                    nContext.resources.getString(R.string.alert), resources.getString(R.string.enter_subject),
                    nContext
                )


            } else {
                if (text_content.text.toString().trim().equals("")) {
                    DialogFunctions.commonErrorAlertDialog(
                        nContext.resources.getString(R.string.alert), resources.getString(R.string.enter_content),
                        nContext
                    )

                } else if (contactEmail.matches(EMAIL_PATTERN.toRegex())) {
                    if (text_dialog.text.toString().trim ().matches(pattern.toRegex())) {
                        if (text_content.text.toString().trim ()
                                .matches(pattern.toRegex())) {

                            if (ConstantFunctions.internetCheck(nContext))
                            {
                                sendEmail(text_dialog.text.toString().trim(), text_content.text.toString().trim(), contactEmail, dialog)

                            }
                            else
                            {
                                DialogFunctions.showInternetAlertDialog(nContext)
                            }

                        } else {
                            val toast: Toast = Toast.makeText(nContext, nContext.getResources().getString(R.string.enter_valid_contents), Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    } else {
                        val toast: Toast = Toast.makeText(
                            nContext,
                            nContext.getResources()
                                .getString(
                                    R.string.enter_valid_subjects
                                ),
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                } else {
                    val toast: Toast = Toast.makeText(
                        nContext,
                        nContext.getResources()
                            .getString(
                                R.string.enter_valid_email
                            ),
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            }


        }
        dialog.show()
    }
    fun sendEmail(title: String, message: String,  staffEmail: String, dialog: Dialog)
    {

        val sendMailBody = SendEmailApiModel( staffEmail, title, message)
        val call: Call<SignUpResponseModel> = ApiClient.getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(nContext))
        call.enqueue(object : Callback<SignUpResponseModel> {
            override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
                //progressDialog.visibility = View.GONE
            }

            override fun onResponse(call: Call<SignUpResponseModel>, response: Response<SignUpResponseModel>) {
                val responsedata = response.body()
                //progressDialog.visibility = View.GONE
                if (responsedata != null) {
                    try {


                        if (response.body()!!.status==100) {

                            showSuccessAlertnew(
                                nContext,
                                "Email sent Successfully ",
                                "Success",
                                dialog
                            )
                            dialog.dismiss()
                        }else {
                            DialogFunctions.commonErrorAlertDialog(
                                nContext.resources.getString(R.string.alert),
                                ConstantFunctions.commonErrorString(response.body()!!.status),
                                nContext
                            )

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
    fun callGetShopBanner()
    {
        progress.visibility = View.VISIBLE

        val token = PreferenceManager.getaccesstoken(nContext)
        val call: Call<CanteenBannerResponseModel> = ApiClient.getClient.shop_banner("Bearer "+token)
        call.enqueue(object : Callback<CanteenBannerResponseModel>
        {
            override fun onFailure(call: Call<CanteenBannerResponseModel>, t: Throwable) {
                progress.visibility = View.GONE


            }
            override fun onResponse(call: Call<CanteenBannerResponseModel>, response: Response<CanteenBannerResponseModel>) {
                val responsedata = response.body()
                progress.visibility = View.GONE

                if (responsedata!!.status==100) {

                    contactEmail=response.body()!!.responseArray.data.contact_email

                    var banner_image=response.body()!!.responseArray.data.image
                    var trn_no=response.body()!!.responseArray.data.trn_no
                    if (contactEmail.equals(""))
                    {
                        email_icon.visibility=View.GONE
                    }
                    else{
                        email_icon.visibility=View.VISIBLE
                    }
                    if(response.body()!!.responseArray.data.description.equals(""))
                    {
                        description.visibility=View.GONE
                    }
                    else{
                        description.visibility=View.VISIBLE
                        description.text=response.body()!!.responseArray.data.description
                    }
                    if (banner_image != "") {

                        Glide.with(nContext) //1
                            .load(banner_image)
                            .into(bannerImg)
                    } else {
                        bannerImg!!.setBackgroundResource(R.drawable.default_banner)
                    }

                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
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
                nContext.resources.getDrawable(R.drawable.button_new)
            )
        } else {
            btn_dismiss.background = nContext.resources.getDrawable(R.drawable.button_new)
        }

        studentListRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(nContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentListRecycler.layoutManager = llm
        if(mStudentList.size>0)
        {
            val studentAdapter = StudentListAdapter(nContext,mStudentList)
            studentListRecycler.adapter = studentAdapter
        }

        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                progressDialog.visibility= View.VISIBLE


                studentName=studentListArrayList.get(position).name
                studentImg=studentListArrayList.get(position).photo
                studentId=studentListArrayList.get(position).id
                studentClass=studentListArrayList.get(position).section
                PreferenceManager.setStudentID(nContext,studentId)
                PreferenceManager.setStudentName(nContext,studentName)
                PreferenceManager.setStudentPhoto(nContext,studentImg)
                PreferenceManager.setStudentClass(nContext,studentClass)
                studentNameTxt.text=studentName
                //studentInfoArrayList.clear()
                if(!studentImg.equals(""))
                {
                    Glide.with(nContext) //1
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
                add_order.visibility= View.VISIBLE
                buttonLinear.visibility= View.VISIBLE
                progressDialog.visibility = View.VISIBLE


                dialog.dismiss()
            }
        })
        dialog.show()
    }
    fun callStudentListApi()
    {
        progressDialog.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(nContext)
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                progressDialog.visibility = View.GONE

                if (response.body()!!.status==100)
                {
                    studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                    if (PreferenceManager.getStudentID(nContext).equals(""))
                    {
                        studentName=studentListArrayList.get(0).name
                        studentImg=studentListArrayList.get(0).photo
                        studentId=studentListArrayList.get(0).id
                        studentClass=studentListArrayList.get(0).section
                        PreferenceManager.setStudentID(nContext,studentId)
                        PreferenceManager.setStudentName(nContext,studentName)
                        PreferenceManager.setStudentPhoto(nContext,studentImg)
                        PreferenceManager.setStudentClass(nContext,studentClass)
                        studentNameTxt.text=studentName
                        if(!studentImg.equals(""))
                        {
                            Glide.with(nContext) //1
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
                        studentName= PreferenceManager.getStudentName(nContext)!!
                        studentImg= PreferenceManager.getStudentPhoto(nContext)!!
                        studentId= PreferenceManager.getStudentID(nContext)!!
                        studentClass= PreferenceManager.getStudentClass(nContext)!!
                        studentNameTxt.text=studentName
                        if(!studentImg.equals(""))
                        {
                            Glide.with(nContext) //1
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
                    add_order.visibility= View.VISIBLE
                    buttonLinear.visibility= View.VISIBLE

                }


            }

        })
    }
    private fun calendarpopup() {
        mDateArrayList= ArrayList()
        val dialog = Dialog(nContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.calendar_canteen_popup)
        var close_img = dialog.findViewById(R.id.imgClose) as? ImageView
        var dummyClose = dialog.findViewById(R.id.dummyClose) as? ImageView
        var btn_submit = dialog.findViewById(R.id.GetDate) as Button
        val calendarView = dialog.findViewById<CalendarView>(R.id.MCalendar)
        close_img!!.setOnClickListener {
            dialog.dismiss()
        }
        dummyClose!!.setOnClickListener()
        {
            dialog.dismiss()
        }
        if (time_exeed.equals("1")) {
            val c = Calendar.getInstance()
            c.add(Calendar.DATE,1)
            calendarView.setMinimumDate(c)
        } else {
            val c = Calendar.getInstance()
            c.add(Calendar.DATE, 0)
            calendarView.setMinimumDate(c)
        }
        calendarView.setPreviousButtonImage(
            nContext.resources.getDrawable(R.drawable.arrow_list_back)
        )
        calendarView.setForwardButtonImage(
            nContext.resources.getDrawable(R.drawable.arrow_list)
        )



        btn_submit.setOnClickListener()
        {
            for (calendar in calendarView.selectedDates) {
                /* println("GetDate: " + calendar.time.toString())
                 println("GetDate: " + calendar.timeInMillis)*/
                val DateF: String = calendar.get(Calendar.DATE).toString()
                val Day: String = calendar.time.toString()
                var strCurrentDate = ""
                var format = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH)
                var newDate: Date? = null
                try {
                    newDate = format.parse(calendar.time.toString())
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                format = SimpleDateFormat("EEE", Locale.ENGLISH)
                strCurrentDate = format.format(newDate)
                val DayF:String=strCurrentDate
                //val DayF: String = CommonMethods.dateParsingToEEE(calendar.time.toString()).toString()

                var strCurrentMonth = ""
                var format2 = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH)
                var newDate2: Date? = null
                try {
                    newDate2 = format2.parse(calendar.time.toString())
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                format2 = SimpleDateFormat("MMM", Locale.ENGLISH)
                strCurrentMonth = format2.format(newDate2)

                val MonthF:String=strCurrentMonth
                // val MonthF: String = CommonMethods.dateParsingTomm(calendar.time.toString()).toString()

                val Year: String = calendar.time.toString()
                var strCurrentYear = ""
                var format3 = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH)
                var newDate3: Date? = null
                try {
                    newDate3 = format3.parse(calendar.time.toString())
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                format3 = SimpleDateFormat("yyyy", Locale.ENGLISH)
                strCurrentYear = format3.format(newDate)
                val YearF:String=strCurrentYear
                //val YearF: String = CommonMethods.dateParsingToyyy(calendar.time.toString()).toString()

                var strCurrentMmyear = ""
                var format4 = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH)
                var newDate4: Date? = null
                try {
                    newDate4 = format4.parse(calendar.time.toString())
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                format4 = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                strCurrentMmyear = format4.format(newDate)
                val numberDate:String=strCurrentMmyear
                // val numberDate: String = CommonMethods.dateParsingToyymmdd(calendar.time.toString()).toString()
                val model = DateModel(DateF, DayF, MonthF, YearF, numberDate, false, false)

                mDateArrayList.add(model)
                // PreferenceManager().setdate_list(nContext,mDateArrayList)
            }
            if (mDateArrayList.size == 0) {
                alert_validemail(nContext,"Alert","Please select any date")
            } else {
                var isFound = false
                var foundPosition = -1
                for (i in mDateArrayList.indices) {
                    if (datetime.equals(mDateArrayList.get(i).date)
                    ) {
                        // val timeExceed: String = time_exeed()
                        if (time_exeed.equals("1")) {
                            isFound = true
                            foundPosition = i
                        }
                    }
                }
                if (isFound) {


                } else {
                    dialog.dismiss()
                    val intent = Intent(nContext, Addorder_Activity::class.java)
                    intent.putExtra("date_list",mDateArrayList)
                    startActivity(intent)
                }
            }



        }

        dialog.dismiss()

        close_img.setOnClickListener()
        {
            dialog.dismiss()
        }

        dialog.show()
    }





    fun showSuccessAlertnew(context: Context, message: String, msgHead: String, dialog1: Dialog) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.exclamationicon)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            dialog1.dismiss()
        }
        dialog.show()
    }
    fun alert_validemail(context: Context, title: String, description: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val btn_Ok = dialog.findViewById<Button>(R.id.btn_Ok)
        val descriptionTxt = dialog.findViewById<TextView>(R.id.messageTxt)
        val titleTxt = dialog.findViewById<TextView>(R.id.alertHead)
        titleTxt.text = title
        descriptionTxt.text = description
        btn_Ok.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.show()


    }

}