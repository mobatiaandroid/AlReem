package com.nas.alreem.activity.intentions

import android.app.Dialog
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
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.intention.model.IntentionApiSubmit
import com.nas.alreem.fragment.intention.model.IntentionInfoResponseArray
import com.nas.alreem.fragment.intention.model.IntentionListAPIResponseModel
import com.nas.alreem.fragment.intention.model.IntentionSubmitModel
import com.nas.alreem.fragment.intention.model.IntentionstatusResponseArray
import com.nas.alreem.fragment.student_information.model.StudentInfoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Date

class IntentionRegisterActivity : AppCompatActivity(){
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
    lateinit var primaryArrayList:ArrayList<IntentionInfoResponseArray>
    lateinit var intentionstatusArray:ArrayList<IntentionstatusResponseArray>
    lateinit var recycler_review: RecyclerView
    lateinit var subQuestionLinearLayout: LinearLayout
    lateinit var answerTV: TextView
    lateinit var questionTV: TextView
    var reEnrollSaveArray: ArrayList<IntentionSubmitModel>? = null

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
    var question = ""
    var student_name = ""
    var classs = ""
    var intention_id: Int = 0
    var receivedOptions: ArrayList<IntentionListAPIResponseModel.Option> = ArrayList()
    lateinit var optionArray: ArrayList<String>
    lateinit var subQuestion: ArrayList<String>
    var position :Int=0


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intention_popup_submit)

        mContext=this
        initfn()

        if (ConstantFunctions.internetCheck(mContext)) {
//            progressDialog.visibility= View.VISIBLE
            //callStudentListApi()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }


    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun initfn() {
        optionArray = ArrayList()
        heading = findViewById(R.id.heading)
        subQuestionLinearLayout = findViewById(R.id.linear_choose1)
        answerTV = findViewById(R.id.answerTxt2)
        questionTV = findViewById(R.id.questionTxt2)
        heading.text = "Intention"
        question = intent.getStringExtra("question")!!.toString()
        student_name = intent.getStringExtra("student")!!.toString()
        classs = intent.getStringExtra("class")!!.toString()
        receivedOptions = intent.getParcelableArrayListExtra("options")!!
        for (i in receivedOptions.indices){
            optionArray.add(receivedOptions[i].option)
        }
        subQuestion = ArrayList()
        subQuestion.add("")
        for (i in 1..receivedOptions.size) {
            if (receivedOptions[i-1].optionQuestion == null){
                subQuestion.add("")
            }else{
                subQuestion.add(receivedOptions[i-1].optionQuestion.toString())
            }

        }
        Log.e("option",optionArray.toString())
        Log.e("subquestion",subQuestion.toString())
        intention_id = intent.getIntExtra("intent_id", 0)
//       optionArray= PreferenceManager.getoptions(mContext)!!
        position = intent.getIntExtra("position", 0)
        val check = intArrayOf(0)
        Log.e("option", receivedOptions[0].option + " " + receivedOptions[0].optionQuestion)
        backRelative = findViewById(R.id.backRelative)
        logoClickImgView = findViewById(R.id.logoClickImgView)

        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        val header_txt = findViewById<TextView>(R.id.header)
        val close_img = findViewById<ImageView>(R.id.close_img)
        val sub_btn = findViewById<Button>(R.id.sub_btn)
        val image_view = findViewById<ImageView>(R.id.image_view)
        val stud_img = findViewById<ImageView>(R.id.stud_img)
        val stud_name = findViewById<TextView>(R.id.stud_name)
        val stud_class = findViewById<TextView>(R.id.stud_class)
        //  val date_field = dialog.findViewById<EditText>(R.id.textField_date)
        val descrcrptn = findViewById<TextView>(R.id.descrptn_txt)
//        val parent_name = dialog.findViewById<EditText>(R.id.textField_parentName)
//        val parent_email = dialog.findViewById<EditText>(R.id.textField_parentEmail)
        val spinnerList = findViewById<Spinner>(R.id.spinnerlist)
        val option_txt = findViewById<TextView>(R.id.option_txt)
        val dropdown_btn = findViewById<ImageView>(R.id.dropdown_btn)
        val radioButton = findViewById<RadioButton>(R.id.check_btn)
        val scrollView = findViewById<ScrollView>(R.id.scroll)
        val terms_and_condtns = findViewById<Button>(R.id.terms_conditions)
        val questionTxt = findViewById<TextView>(R.id.questionTxt)
        var dropDownList: java.util.ArrayList<String> = ArrayList<String>()
        //val currentDate = LocalDate.now().toString()
        val answerTxt2 = findViewById<EditText>(R.id.answerTxt2)
        val questionTxt2 = findViewById<TextView>(R.id.questionTxt2)
        val currentYear = Calendar.YEAR.toString()
        val currentMonth = Calendar.MONTH.toString()
        val currentDay = Calendar.DAY_OF_MONTH.toString()
        var date = "$currentDay/$currentMonth/$currentYear"

        questionTxt.text = question
        optionsArray = ArrayList()
        val reEnrollSubmit = IntentionSubmitModel("", "")

        optionsArray.addAll(optionArray)
        Log.e("arraysizeoption", optionsArray.size.toString())

//        answerTxt2.isFocusable = false
//        answerTxt2.isFocusableInTouchMode = false
//        answerTxt2.isClickable = false
//        if (optionsArray.contains("YES")) {
//            questionTxt2.setTextColor(mContext.resources.getColor(R.color.black))
//            answerTxt2.isEnabled
//            answerTxt2.isFocusable = true
//            answerTxt2.isFocusableInTouchMode = true
//            answerTxt2.isClickable = true
//        } else {
//            questionTxt2.setTextColor(mContext.resources.getColor(R.color.grey))
//            answerTxt2.isFocusable = false
//            answerTxt2.isFocusableInTouchMode = false
//            answerTxt2.isClickable = false
//        }

        dropDownList = ArrayList()
        stud_name.text = student_name
        stud_class.text = classs
        //  val stud_photo: String = studentEnrollList.get(position).getPhoto()

        //  val stud_id: String = studentEnrollList.get(position).getId()
        dropDownList.add(0, "Please Select")
        for (i in 1..optionsArray.size) {
//        for (i in optionsArray.indices) {
            dropDownList.add(optionsArray.get(i-1).toString())
        }

        val sp_adapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(mContext, R.layout.spinner_textview, dropDownList as List<Any?>)
        spinnerList.adapter = sp_adapter
//        spinnerList.setSelection(0)
        val finalDropDownList: java.util.ArrayList<*> = dropDownList
        spinnerList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedItem = parent.getItemAtPosition(position).toString()
                for (i in subQuestion.indices){
                    if (subQuestion[position].isNotEmpty()){
                        // show sub question
                        subQuestionLinearLayout.visibility = View.VISIBLE
                        questionTV.text = subQuestion[position]
                    }else{
                        subQuestionLinearLayout.visibility = View.GONE
                        // continus
                    }
                }
//                val optionlistSize = finalDropDownList.size - 1
//                for (i in 1 until optionlistSize) {
//                    if (selectedItem === finalDropDownList[i].toString()) {
//                        reEnrollSubmit.status = (finalDropDownList[i].toString())
//                        reEnrollSubmit.student_id =
//                            PreferenceManager.getCCAStudentIdPosition(mContext).toString()
//                        check[0] = 1
//                    } else if (selectedItem === finalDropDownList[0]) {
//                        reEnrollSubmit.status = ("")
//                        check[0] = 0
//                    }
//                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        dropdown_btn.setOnClickListener { dropdown_btn.visibility = View.GONE }
        option_txt.setOnClickListener {
            option_txt.visibility = View.GONE
            spinnerList.visibility = View.VISIBLE
        }
        terms_and_condtns.setOnClickListener {
            /* val intent = Intent(mContext, WebViewActivity::class.java)
             intent.putExtra("Url", tAndCString)
             mContext.startActivity(intent)*/
        }
        sub_btn.setOnClickListener {


            if (selectedItem.equals("") || selectedItem.equals("Please Select"))
            {

                Toast.makeText(mContext, " Please Select Options and Submit", Toast.LENGTH_SHORT).show()
               /* showReEnrollNoData(
                    mContext,
                    "You didn't enter any data of your child. Please Enter data and Submit", "Alert")*/
            } else {
                if(answerTV.text.trim().equals(""))
                {
                    Toast.makeText(mContext, " Please Enter The Contents", Toast.LENGTH_SHORT).show()
                }
                else{
                    showSubmitConfirm(
                        mContext,
                        "Would you like to submit?",
                        "Alert", selectedItem, position,intention_id)
                }

            }
        }

    }
    private fun showReEnrollNoData(activity: Context, s: String, alert: String) {
        val dialog1 = Dialog(com.nas.alreem.fragment.home.mContext)
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog1.setCancelable(false)
        dialog1.setContentView(R.layout.dialog_common_error_alert)
        val iconImageView = dialog1.findViewById<ImageView>(R.id.iconImageView)
        iconImageView.setImageResource(R.drawable.exclamationicon)
        val alertHead = dialog1.findViewById<TextView>(R.id.alertHead)
        val text_dialog = dialog1.findViewById<TextView>(R.id.messageTxt)
        val btn_Ok = dialog1.findViewById<Button>(R.id.btn_Ok)
        // var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as Button
        text_dialog.text = s
        alertHead.text = alert
        btn_Ok.setOnClickListener { dialog1.dismiss() }

        /* btn_Cancel.setOnClickListener {
              dialog.dismiss()
          }*/dialog1.show()
    }
    private fun showSubmitConfirm(
        activity: Context, do_you_want_to_submit: String, alert: String, selectedItem: String, position: Int, intensionId: Int
    ) {
        val dialog1 = Dialog(mContext)
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog1.setCancelable(false)
        dialog1.setContentView(R.layout.dialog_ok_cancel)
        val iconImageView = dialog1.findViewById<ImageView>(R.id.iconImageView)
        val alertHead = dialog1.findViewById<TextView>(R.id.alertHead)
        val text_dialog = dialog1.findViewById<TextView>(R.id.text_dialog)
        val btn_Ok = dialog1.findViewById<Button>(R.id.btn_Ok)
        val btn_Cancel = dialog1.findViewById<Button>(R.id.btn_Cancel)
        text_dialog.text = do_you_want_to_submit
        alertHead.text = alert
        btn_Ok.setOnClickListener {
            saveIntentionApi(reEnrollSaveArray, dialog1,selectedItem, position,intensionId)
            dialog1.dismiss()
        }
        btn_Cancel.setOnClickListener { dialog1.dismiss() }
        dialog1.show()
    }

    private fun saveIntentionApi(
        reEnrollSaveArray: ArrayList<IntentionSubmitModel>?,
        dialog1: Dialog,
        selectedItem: String,
        position: Int,
        intensionId: Int
    ) {

        primaryArrayList= ArrayList()
        // optionsArray = ArrayList()
       // progress.visibility = View.VISIBLE
        val body = IntentionApiSubmit(PreferenceManager.getStudentID(mContext)!!,intensionId.toString(),"2","hbhhhj","1.0",selectedItem,answerTV.text.trim().toString())
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<StudentInfoModel> = ApiClient.getClient.intensionstatusupdate(body,"Bearer "+token)
        call.enqueue(object : Callback<StudentInfoModel> {
            override fun onFailure(call: Call<StudentInfoModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
              //  progress.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentInfoModel>, response: Response<StudentInfoModel>) {
               // progress.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                val responsedata = response.body()
                Log.e("size","size")
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            showSuccessReEnrollAlert(
                                mContext, " Thank you\n" + "\n" + "Successfully submitted", "Success", dialog1)
                        }
                        else
                        {

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }

        })
    }

    private fun showSuccessReEnrollAlert(
        mContext: Context,
        successfully_submitted_: String,
        success: String,
        dialog: Dialog
    ) {
        val d = Dialog(mContext)
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)
        d.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        d.setCancelable(false)
        d.setContentView(R.layout.dialog_common_error_alert)
        val iconImageView = d.findViewById<ImageView>(R.id.iconImageView)
        val alertHead = d.findViewById<TextView>(R.id.alertHead)
        val text_dialog = d.findViewById<TextView>(R.id.messageTxt)
        val btn_Ok = d.findViewById<Button>(R.id.btn_Ok)
        text_dialog.text = successfully_submitted_
        alertHead.text = success
        iconImageView.setImageResource(R.drawable.tick)
        btn_Ok.setOnClickListener {
            d.dismiss()
            dialog.dismiss()
            finish()
           // getIntentionListAPI(stud_id)
          //  getIntentionStatusAPI(stud_id)

        }
        d.show()
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