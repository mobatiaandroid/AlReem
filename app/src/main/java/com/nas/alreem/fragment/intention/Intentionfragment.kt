package com.nas.alreem.fragment.intention

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.cca.adapter.CCAsListActivityAdapter
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
import com.nas.alreem.fragment.intention.adapter.IntentionAdapter
import com.nas.alreem.fragment.intention.model.IntentionApiModel
import com.nas.alreem.fragment.intention.model.IntentionApiSubmit
import com.nas.alreem.fragment.intention.model.IntentionInfoResponseArray
import com.nas.alreem.fragment.intention.model.IntentionResponseModel
import com.nas.alreem.fragment.intention.model.IntentionStatusResponseModel
import com.nas.alreem.fragment.intention.model.IntentionSubmitModel
import com.nas.alreem.fragment.intention.model.IntentionstatusResponseArray
import com.nas.alreem.fragment.student_information.model.StudentInfoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class Intentionfragment : Fragment(){
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var back: ImageView

    lateinit var primaryArrayList:ArrayList<IntentionInfoResponseArray>
    lateinit var intentionstatusArray:ArrayList<IntentionstatusResponseArray>

    lateinit var optionsArray : ArrayList<String>
    lateinit var backRelative: RelativeLayout
    lateinit var logoclick: ImageView
    lateinit var progress: ProgressBar
    var studentListArrayList = ArrayList<StudentList>()

    lateinit var studentName: TextView
    lateinit var textViewYear: TextView
    lateinit var enterTextView: TextView
    var stud_id = ""
    var stud_class = ""
    var stud_name = ""
    var selectedItem = ""

    var mStudentSpinner: LinearLayout? = null
    var relativeHeader: RelativeLayout? = null
    // lateinit var student_Name: String
    // lateinit var studentImg: String
    // lateinit var studentClass: String
    lateinit var studImg: ImageView
    var stud_img = ""
    var reEnrollSaveArray: ArrayList<IntentionSubmitModel>? = null

    var tab_type = "ECA Options"
    var extras: Bundle? = null
    lateinit var recycler_review: RecyclerView
    var recyclerViewLayoutManager: GridLayoutManager? = null
    var mCCAsActivityAdapter: CCAsListActivityAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.intention_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callStudentList()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }
    private fun initializeUI()
    {
        progress = requireView().findViewById(R.id.progressDialogAdd)
        titleTextView = requireView().findViewById(R.id.titleTextView)
       // back = requireView().findViewById(R.id.btn_left)
      //  backRelative =requireView(). findViewById(R.id.backRelative)
       // logoclick = requireView().findViewById(R.id.logoClickImgView)

       // relativeHeader = requireView().findViewById<View>(R.id.relativeHeader) as RelativeLayout
        recycler_review =requireView(). findViewById<View>(R.id.weekRecyclerList) as RecyclerView
        var linearLayoutManager = LinearLayoutManager(mContext)
        recycler_review.layoutManager = linearLayoutManager
        recycler_review.itemAnimator = DefaultItemAnimator()
        studImg =requireView(). findViewById(R.id.studImg)

        mStudentSpinner =requireView(). findViewById<View>(R.id.studentSpinner) as LinearLayout
        studentName = requireView().findViewById<View>(R.id.studentName) as TextView
      //  enterTextView = requireView(). findViewById<View>(R.id.enterTextView) as TextView
      //  textViewYear = requireView().findViewById<View>(R.id.textViewYear) as TextView
        mStudentSpinner!!.setOnClickListener { showStudentsList(mContext,studentListArrayList)

          }
        recycler_review.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {
                Log.e("intention","intention")
                if (primaryArrayList.get(position).status.equals("")) {
                    showIntentionPopUp(
                        mContext,
                        primaryArrayList,position)

                }
                else{
                    val dialog = Dialog(mContext)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setContentView(R.layout.alert_intention_view)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    val name = dialog.findViewById<TextView>(R.id.nametxt)
                    val studName = dialog.findViewById<TextView>(R.id.stud_name)
                    val department = dialog.findViewById<TextView>(R.id.mailtxt)
                    val role = dialog.findViewById<TextView>(R.id.statustxt)
                    val section = dialog.findViewById<TextView>(R.id.section)
                    val imageView = dialog.findViewById<ImageView>(R.id.iconImageView)
                   // name.setText(primaryArrayList.get(position).student)
                    studName.setText(primaryArrayList.get(position).student)
                    department.setText(primaryArrayList.get(position).question)
                    role.setText(intentionstatusArray.get(position).selected_options)
                    section.setText(intentionstatusArray.get(position).className)
                    // TODO set Staff Image
                    // TODO set Staff Image
                    dialog.show()
                }

            }


        })
    }

    private fun showIntentionPopUp(
        mContext: Context,
        primaryArrayList: ArrayList<IntentionInfoResponseArray>,
        position: Int
    ) {
        val dialog = Dialog(this.mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.intention_popup_submit)
        val check = intArrayOf(0)
        val header_txt = dialog.findViewById<TextView>(R.id.header)
        val close_img = dialog.findViewById<ImageView>(R.id.close_img)
        val sub_btn = dialog.findViewById<Button>(R.id.sub_btn)
        val image_view = dialog.findViewById<ImageView>(R.id.image_view)
        val stud_img = dialog.findViewById<ImageView>(R.id.stud_img)
        val stud_name = dialog.findViewById<TextView>(R.id.stud_name)
        val stud_class = dialog.findViewById<TextView>(R.id.stud_class)
      //  val date_field = dialog.findViewById<EditText>(R.id.textField_date)
        val descrcrptn = dialog.findViewById<TextView>(R.id.descrptn_txt)
//        val parent_name = dialog.findViewById<EditText>(R.id.textField_parentName)
//        val parent_email = dialog.findViewById<EditText>(R.id.textField_parentEmail)
        val spinnerList = dialog.findViewById<Spinner>(R.id.spinnerlist)
        val option_txt = dialog.findViewById<TextView>(R.id.option_txt)
        val dropdown_btn = dialog.findViewById<ImageView>(R.id.dropdown_btn)
        val radioButton = dialog.findViewById<RadioButton>(R.id.check_btn)
        val scrollView = dialog.findViewById<ScrollView>(R.id.scroll)
        val terms_and_condtns = dialog.findViewById<Button>(R.id.terms_conditions)
        val questionTxt = dialog.findViewById<TextView>(R.id.questionTxt)
        var dropDownList: java.util.ArrayList<String> = ArrayList<String>()
        //val currentDate = LocalDate.now().toString()
        val currentYear = Calendar.YEAR.toString()
        val currentMonth = Calendar.MONTH.toString()
        val currentDay = Calendar.DAY_OF_MONTH.toString()
        var date = "$currentDay/$currentMonth/$currentYear"
     //   header_txt.setText(headingString)
     //   descrcrptn.setText(descriptionString)
      //  parent_email.setText(emailString)
      //  parent_name.setText(userNameString)
        questionTxt.setText(primaryArrayList.get(position).question)
        optionsArray=ArrayList()
        val reEnrollSubmit = IntentionSubmitModel("", "")

            optionsArray.addAll(primaryArrayList.get(position).options)
            Log.e("arraysizeoption",optionsArray.size.toString())


      //  date = AppUtils.dateConversionddmmyyyy(currentDate)
      //  date_field.setText(date)
        dropDownList =ArrayList()
        stud_name.setText(primaryArrayList.get(position).student)
        stud_class.setText(primaryArrayList.get(position).classs)
      //  val stud_photo: String = studentEnrollList.get(position).getPhoto()

      //  val stud_id: String = studentEnrollList.get(position).getId()
        dropDownList.add(0, "Please Select")
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
                        reEnrollSubmit.status=(finalDropDownList[i].toString())
                        reEnrollSubmit.student_id=
                            PreferenceManager.getCCAStudentIdPosition(mContext).toString()
                        check[0] = 1
                    } else if (selectedItem === finalDropDownList[0]) {
                        reEnrollSubmit.status=("")
                        check[0] = 0
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
       /* if (stud_photo != "") {
            Glide.with(this.mContext)
                .load(stud_photo)
                .placeholder(R.drawable.student)
                .error(R.drawable.student)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE) //					.transform(CircleCrop())
                .into(stud_img)
        } else {
            stud_img.setImageResource(R.drawable.student)
        }*/

       /* if (userNameString.equals("")) {
          //  parent_name.setText(userNameString)
        }*/
       /* if (emailString.equals("")) {
           // parent_email.setText(emailString)
        }*/
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

            Log.e("Intention_validation",selectedItem)
            if (selectedItem.equals("", ignoreCase = true) || selectedItem.equals(
                    "Please Select",
                    ignoreCase = true
                )
            ) {
                showReEnrollNoData(
                    mContext,
                    "You didn't enter any data of your child. Please Enter data and Submit",
                    "Alert",
                    dialog
                )
            } else {
                showSubmitConfirm(
                    mContext,
                    "Would you like to submit?",
                    "Alert",
                    dialog,
                    selectedItem,
                    position,
                    primaryArrayList.get(position).intension_id
                )
            }

           /* if (selectedItem.equals("", ignoreCase = true) || selectedItem.equals(
                    "Please Select",
                    ignoreCase = true
                )
            ) {
                showReEnrollNoData(
                    mActivity,
                    "You didn't enter any data of your child. Please Enter data and Submit",
                    "Alert",
                    dialog
                )
            } else {
                showSubmitConfirm(
                    mActivity,
                    "Would you like to submit?",
                    "Alert",
                    dialog,
                    selectedItem,
                    position
                )
            }*/
        }
        close_img.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    private fun showReEnrollNoData(activity: Context, s: String, alert: String, dialog: Dialog) {
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
        activity: Context, do_you_want_to_submit: String, alert: String,
        dialog: Dialog, selectedItem: String, position: Int, intensionId: Int
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
            saveIntentionApi(reEnrollSaveArray, dialog1, dialog, selectedItem, position,intensionId)
            dialog1.dismiss()
        }
        btn_Cancel.setOnClickListener { dialog1.dismiss() }
        dialog1.show()
    }

    private fun saveIntentionApi(
        reEnrollSaveArray: ArrayList<IntentionSubmitModel>?,
        dialog1: Dialog,
        dialog: Dialog,
        selectedItem: String,
        position: Int,
        intensionId: Int
    ) {

        primaryArrayList= ArrayList()
        // optionsArray = ArrayList()
        progress.visibility = View.VISIBLE
        val body = IntentionApiSubmit(stud_id,intensionId.toString(),"2","hbhhhj","1.0",selectedItem)
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<StudentInfoModel> = ApiClient.getClient.intensionstatusupdate(body,"Bearer "+token)
        call.enqueue(object : Callback<StudentInfoModel> {
            override fun onFailure(call: Call<StudentInfoModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
                progress.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentInfoModel>, response: Response<StudentInfoModel>) {
                progress.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                val responsedata = response.body()
                Log.e("size","size")
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            showSuccessReEnrollAlert(
                                mContext, " Thank you         \n" + "\n" + "Successfully submitted", "Success", dialog1, dialog)
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
        dialog1: Dialog,
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
            dialog1.dismiss()
            getIntentionListAPI(stud_id)
            getIntentionStatusAPI(stud_id)

        }
        d.show()
    }
    private fun showStudentsList(mContext: Context, mStudentArray: ArrayList<StudentList>) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogue_student_list)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogDismiss = dialog.findViewById<View>(R.id.btn_dismiss) as Button
        val iconImageView = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        iconImageView.setImageResource(R.drawable.boy)
        val socialMediaList =
            dialog.findViewById<View>(R.id.studentListRecycler) as RecyclerView
        //if(mSocialMediaArray.get())
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            dialogDismiss.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.button))
        } else {
            dialogDismiss.background = mContext.resources.getDrawable(R.drawable.button)
        }

        val divider = DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.list_divider_teal)!!)
        socialMediaList!!.addItemDecoration(divider)

        socialMediaList.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        socialMediaList.layoutManager = llm

        val studentAdapter = StudentListAdapter(mContext, mStudentArray)
        socialMediaList.adapter = studentAdapter
        dialogDismiss.setOnClickListener { dialog.dismiss() }
        socialMediaList.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                dialog.dismiss()
                studentName!!.setText(mStudentArray!!.get(position).name)
                stud_id = mStudentArray!!.get(position).id.toString()
                stud_name = mStudentArray.get(position).name.toString()
                stud_class = mStudentArray.get(position).studentClass.toString()
                stud_img = mStudentArray.get(position).photo.toString()
              //  textViewYear!!.text = "Class : " + mStudentArray.get(position).studentClass
                // PreferenceManager.setStudentID(mContext,stud_id)

                if (stud_img != "") {
                    Glide.with(mContext) //1
                        .load(stud_img)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(studImg!!)
                } else {
                    studImg!!.setImageResource(R.drawable.boy)
                }
                PreferenceManager.setCCAStudentIdPosition(
                    mContext,
                    position.toString() + ""
                )

                var internetCheck = ConstantFunctions.internetCheck(mContext)
                if (internetCheck) {
                    getIntentionListAPI(stud_id)
                    getIntentionStatusAPI(stud_id)

                } else {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }
            }

        })

        dialog.show()
    }
    private fun getIntentionListAPI(stud_id: String)
    {
        Log.e("size","size")
        primaryArrayList= ArrayList()
       // optionsArray = ArrayList()
        progress.visibility = View.VISIBLE
        val body = IntentionApiModel(stud_id,"0","20")
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<IntentionResponseModel> = ApiClient.getClient.intension(body,"Bearer "+token)
        call.enqueue(object : Callback<IntentionResponseModel> {
            override fun onFailure(call: Call<IntentionResponseModel>, t: Throwable) {
                 Log.e("Error", t.localizedMessage)
                progress.visibility = View.GONE
            }
            override fun onResponse(call: Call<IntentionResponseModel>, response: Response<IntentionResponseModel>) {
                progress.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                val responsedata = response.body()
                Log.e("size","size")
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            Log.e("size","size")
                            primaryArrayList= response.body()!!.responseArray.intension!!
                            Log.e("Arraysize",(primaryArrayList.size.toString()))
                            if (primaryArrayList.size>0)
                            {


                                var primaryAdapter= IntentionAdapter(primaryArrayList,mContext)
                                recycler_review.adapter=primaryAdapter

                            }
                            else
                            {

                                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantWords.status_132, mContext)

                            }
                        }
                        else
                        {
                            var primaryAdapter= IntentionAdapter(primaryArrayList,mContext)
                            recycler_review.adapter=primaryAdapter
                            //Toast.makeText(mContext, "No Registered Early Pickup Found", Toast.LENGTH_SHORT).show()

                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

            }

        })
    }
    private fun getIntentionStatusAPI(stud_id: String)
    {
        intentionstatusArray = ArrayList()
        progress.visibility = View.VISIBLE
        val body = IntentionApiModel(stud_id,"0","20")
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<IntentionStatusResponseModel> = ApiClient.getClient.intensionstatus(body,"Bearer "+token)
        call.enqueue(object : Callback<IntentionStatusResponseModel> {
            override fun onFailure(call: Call<IntentionStatusResponseModel>, t: Throwable) {
                // Log.e("Error", t.localizedMessage)
                progress.visibility = View.GONE
            }
            override fun onResponse(call: Call<IntentionStatusResponseModel>, response: Response<IntentionStatusResponseModel>) {
                progress.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
                    intentionstatusArray.addAll(response.body()!!.responseArray.response!!)
//
                }


            }

        })
    }
    private fun callStudentList()
    {
        progress.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                // Log.e("Error", t.localizedMessage)
                progress.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                progress.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
                    studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                    if (PreferenceManager.getStudIdForCCA(mContext).equals(""))
                    {
                        //  Log.e("studentname",student_Name)
                        stud_name=studentListArrayList.get(0).name
                        stud_img=studentListArrayList.get(0).photo
                        stud_id=studentListArrayList.get(0).id
                        stud_class=studentListArrayList.get(0).section
                        // Log.e("Student_idss",stud_id)
                        // PreferenceManager.setStudentID(mContext,studentId)
                        //  PreferenceManager.setStudentName(mContext,student_Name)
                        //PreferenceManager.setStudentPhoto(mContext,studentImg)
                        //  PreferenceManager.setStudentClass(mContext,studentClass)
                        studentName.text=stud_name
                        PreferenceManager.setCCAStudentIdPosition(mContext, "0")

                        if(!stud_img.equals(""))
                        {
                            Glide.with(mContext) //1
                                .load(stud_img)
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
                        val studentSelectPosition = Integer.valueOf(
                            PreferenceManager.getCCAStudentIdPosition(mContext)
                        )
                        stud_name= studentListArrayList[studentSelectPosition].name!!
                        stud_img= studentListArrayList[studentSelectPosition].photo!!
                        stud_id=  studentListArrayList!![studentSelectPosition].id.toString()
                        // PreferenceManager.setStudentID(mContext, studentId)
                        // PreferenceManager.setStudIdForCCA(mContext, studentId)
                        //  Log.e("Studentid1",stud_id)
                        stud_class= studentListArrayList[studentSelectPosition].studentClass!!
                        studentName.text=stud_name
                        if(!stud_img.equals(""))
                        {
                            Glide.with(mContext) //1
                                .load(stud_img)
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
                    var internetCheck = ConstantFunctions.internetCheck(mContext)
                    if (internetCheck) {
                        getIntentionListAPI(stud_id)
                        getIntentionStatusAPI(stud_id)

                    } else {
                        DialogFunctions.showInternetAlertDialog(mContext)
                    }

//
                }


            }

        })
    }

    override fun onResume() {
        super.onResume()
        //getIntentionListAPI(stud_id)

        /*mPickupListView.visibility = View.GONE
        mAbsenceListView.visibility = View.GONE
        studentNameTxt.text = PreferenceManager.getStudentName(mContext)
        studentId = PreferenceManager.getStudentID(mContext).toString()
        studentImg = PreferenceManager.getStudentPhoto(mContext)!!
        if (!studentImg.equals("")) {
            Glide.with(mContext) //1
                .load(studentImg)
                .placeholder(R.drawable.student)
                .error(R.drawable.student)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(studImg)
        } else {
            studImg.setImageResource(R.drawable.student)
        }
        if (select_val == 0) {
            progressDialogAdd.visibility = View.VISIBLE
            callStudentLeaveInfo()
        } else if (select_val == 1) {
            progressDialogAdd.visibility = View.VISIBLE
            if (ConstantFunctions.internetCheck(mContext))
            {
                callpickuplist_api()
            }
            else
            {
                DialogFunctions.showInternetAlertDialog(mContext)
            }

        }*/

    }
}