package com.nas.alreem.activity.shop

import android.app.Activity
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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.activity.shop.Adapter.SummaryDataAdapter
import com.nas.alreem.activity.shop.Adapter.TermsAdapter
import com.nas.alreem.activity.shop.model.Instrument
import com.nas.alreem.activity.shop.model.InstrumentDataModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RegisteredShopSummaryActivity : AppCompatActivity() {
    lateinit var studentsArrayList: ArrayList<StudentList>
   // var mCCAmodelArrayList: ArrayList<CCAModel>? = null
 //   var CCADetailModelArrayList: ArrayList<CCADetailModel>? = null
   // var CCAchoiceModelArrayList: ArrayList<CCAchoiceModel>? = null
  //  var CCAchoiceModelArrayList2: ArrayList<CCAchoiceModel>? = null
    lateinit var studentName: TextView

    //    TextView textViewYear;
    var enterTextView: TextView? = null
    var stud_id = ""
    var stud_class = ""
    var stud_name = ""
    var studentSpinnerView: LinearLayout? = null
    var instrumentRecyclerView: RecyclerView? = null
    //var headermanager: HeaderManager? = null
    var relativeHeader: RelativeLayout? = null
    var back: ImageView? = null
    lateinit var studImg: ImageView
    var buttonInvoice: ImageView? = null
    var merchantOrderReference = ""

    var stud_img = ""
    var home: ImageView? = null
    var tab_type = "Summary"
    var text_content: TextView? = null
    var text_dialog: TextView? = null
    var instrumentsList: ArrayList<Instrument>? = null
    var extras: Bundle? = null
    var recyclerViewLayoutManager: GridLayoutManager? = null
    var termsAdapter: TermsAdapter? = null
    var isBeginner = ""
    var payButton: ConstraintLayout? = null
    var totalAmount: TextView? = null
    var totalValue = 0.0
    var PaymentToken = ""
    var OrderRef = ""
    var PayUrl = ""
    var AuthUrl = ""
    var orderId = ""
    var orderReference = ""
    var activity: Activity = this
    var instrumentDataList: ArrayList<InstrumentDataModel> = ArrayList<InstrumentDataModel>()
    var cartIDs = ArrayList<Int>()
    var totalCost = 0.0
    lateinit var recyclerView: RecyclerView
    private var adapter: SummaryDataAdapter? = null
    private val totalAmountTextView: TextView? = null
    lateinit var context: Context
    //var progressBarDialog: ProgressBarDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registered_summary)
        context = this
      //  progressBarDialog = ProgressBarDialog(context, R.drawable.spinner)
        //if (AppUtils.checkInternet(context)) {
            getStudentsList()
            //            getStudentsListAPI(URL_GET_STUDENT_LIST);
       /* } else {
            AppUtils.showDialogAlertDismiss(
                context as Activity?,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }*/
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
      //  headermanager = HeaderManager(this@RegisteredSummaryActivity, tab_type)
     //   headermanager.getHeader(relativeHeader, 8)
     //   back = headermanager.getLeftButton()
       // buttonInvoice = headermanager.getHistoryButton()
        buttonInvoice!!.setOnClickListener {
            println("history click")
            val `in` = Intent(
                context,
                InvoiceShopListingActivity::class.java
            )
            `in`.putExtra("studentName", studentName!!.text.toString())
            `in`.putExtra("studentImage", stud_img)
            `in`.putExtra("studentId", PreferenceManager.getStudentID(context))
            `in`.putExtra("StudentModelArray", studentsArrayList)
            startActivity(`in`)
        }
        /*headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )*/
        back!!.setOnClickListener {
         //   AppUtils.hideKeyBoard(context)
            finish()
        }
     //   home = headermanager.getLogoButton()
        home!!.setOnClickListener {
            val `in` = Intent(
                context,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        studImg = findViewById<ImageView>(R.id.studentImage)
        studentSpinnerView = findViewById<View>(R.id.studentSpinner) as LinearLayout
        studentName = findViewById<View>(R.id.studentName) as TextView

        // Example cart data (replace with your parsed data)
        studentSpinnerView!!.setOnClickListener { v: View? ->
            showStudentSelectionPopUp(
                studentsArrayList
            )
        }
    }


    fun showStudentSelectionPopUp(mStudentArray: ArrayList<StudentList>?) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogue_student_list)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogDismiss = dialog.findViewById<View>(R.id.btn_dismiss) as Button
        val iconImageView = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        iconImageView.setImageResource(R.drawable.boy)
        val studentsRecycler =
            dialog.findViewById<View>(R.id.recycler_view_social_media) as RecyclerView
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            dialogDismiss.setBackgroundDrawable(context!!.resources.getDrawable(R.drawable.button))
        } else {
            dialogDismiss.background = context!!.resources.getDrawable(R.drawable.button)
        }
        studentsRecycler.addItemDecoration(DividerItemDecoration(context!!.resources.getDrawable(R.drawable.list_divider_teal)))
        studentsRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentsRecycler.layoutManager = llm
        val studentAdapter = StudentListAdapter(context!!, mStudentArray!!)
        studentsRecycler.adapter = studentAdapter
        dialogDismiss.setOnClickListener { dialog.dismiss() }
        studentsRecycler.addOnItemTouchListener(
            RecyclerItemListener(context, studentsRecycler,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        dialog.dismiss()
                        studentName.setText(mStudentArray!![position].name)
                        stud_id = mStudentArray!![position].id
                        stud_name = mStudentArray[position].name
                        stud_class = mStudentArray[position].studentClass
                        stud_img = mStudentArray[position].photo
                        PreferenceManager.setStudentID(context, stud_id)
                        PreferenceManager.setStudentName(context, stud_name)
                        //                        textViewYear.setText("Class : " + mStudentArray.get(position).getmClass());
                        if (stud_img != "") {
                           /* Picasso.with(context).load(AppUtils.replace(stud_img))
                                .placeholder(R.drawable.boy).fit().into(studImg)*/
                        } else {
                            studImg!!.setImageResource(R.drawable.boy)
                        }
                        callRegisteredSummaryAPI(stud_id)
                        //                        callRegisteredSummary(stud_id);
                        // TODO call instruments api
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        dialog.show()
    }

    private fun getStudentsList() {
        val token = PreferenceManager.getaccesstoken(context)
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                // Log.e("Error", t.localizedMessage)
                //  progress.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                // progress.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
                    studentsArrayList.addAll(response.body()!!.responseArray.studentList)
                    if (PreferenceManager.getStudIdForCCA(context).equals(""))
                    {
                        //  Log.e("studentname",student_Name)
                        stud_name=studentsArrayList.get(0).name
                        stud_img=studentsArrayList.get(0).photo
                        stud_id=studentsArrayList.get(0).id
                        stud_class=studentsArrayList.get(0).section
                        // Log.e("Student_idss",stud_id)
                        // PreferenceManager.setStudentID(mContext,studentId)
                        //  PreferenceManager.setStudentName(mContext,student_Name)
                        //PreferenceManager.setStudentPhoto(mContext,studentImg)
                        //  PreferenceManager.setStudentClass(mContext,studentClass)
                        studentName.text=stud_name
                        PreferenceManager.setCCAStudentIdPosition(context, "0")

                        if(!stud_img.equals(""))
                        {
                            Glide.with(context) //1
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
                            PreferenceManager.getCCAStudentIdPosition(context)
                        )
                        stud_name= studentsArrayList[studentSelectPosition].name!!
                        stud_img= studentsArrayList[studentSelectPosition].photo!!
                        stud_id=  studentsArrayList!![studentSelectPosition].id.toString()
                        // PreferenceManager.setStudentID(mContext, studentId)
                        // PreferenceManager.setStudIdForCCA(mContext, studentId)
                        //  Log.e("Studentid1",stud_id)
                        stud_class= studentsArrayList[studentSelectPosition].studentClass!!
                        studentName.text=stud_name
                        if(!stud_img.equals(""))
                        {
                            Glide.with(context) //1
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
                    var internetCheck = ConstantFunctions.internetCheck(context)
                    if (internetCheck) {
                        //  getIntentionListAPI(stud_id)
                        //   getIntentionStatusAPI(stud_id)

                    } else {
                        DialogFunctions.showInternetAlertDialog(context)
                    }

//
                }


            }

        })
    }





   /* private fun addStudentDetails(dataObject: JSONObject): StudentList {
        val studentModel = StudentList()
       // studentModel.setmId(dataObject.optString(JTAG_ID))
       // studentModel.setmName(dataObject.optString(JTAG_TAB_NAME))
       // studentModel.setmClass(dataObject.optString(JTAG_TAB_CLASS))
       // studentModel.setmSection(dataObject.optString(JTAG_TAB_SECTION))
      //  studentModel.setmHouse(dataObject.optString("house"))
      //  studentModel.setmPhoto(dataObject.optString("photo"))
        return studentModel
    }*/

    private fun callRegisteredSummaryAPI(stud_id: String) {
       /* adapter = SummaryDataAdapter(ArrayList<Any?>())
        recyclerView!!.adapter = adapter
        val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", stud_id)
        val call: Call<ResponseBody> = service.postRegisteredSummaryDetails(
            "Bearer " + PreferenceManager.getAccessToken(context), paramObject
        )
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.body() != null) {
                    try {
                        val responseString = response.body()!!.string()
                        val rootObject = JSONObject(responseString)
                        val responseCode = rootObject.getString("responsecode")
                        try {
                            if (responseCode == "200") {
                                val secobj = rootObject.getJSONObject("response")
                                val dataObject = secobj.getJSONObject("data")


                                //  JSONObject responseData = rootObject.getJSONObject("response").getJSONObject("data");

                                // if (dataObject.has("data")) {
                                // Log.e("Debug", "'data' exists!");

                                // dataObject = dataObject.getJSONObject("data");

                                //Log.e("Debug", "Parsing 'instrument_data'...");
                                val instrumentDataArray = dataObject.getJSONArray("instrument_data")
                                instrumentDataList = ArrayList<InstrumentDataModel>()
                                for (i in 0 until instrumentDataArray.length()) {
                                    val instrumentDataObject = instrumentDataArray.getJSONObject(i)
                                    val instrumentId = instrumentDataObject.getInt("instrument_id")
                                    val instrumentName =
                                        instrumentDataObject.getString("instrument_name")
                                    val orderDataArray =
                                        instrumentDataObject.getJSONArray("order_data")
                                    val orderDataList: ArrayList<OrderDataModel> =
                                        ArrayList<OrderDataModel>()
                                    for (j in 0 until orderDataArray.length()) {
                                        val orderDataObject = orderDataArray.getJSONObject(j)
                                        val orderData = OrderDataModel()
                                        orderData.setOrderId(orderDataObject.getInt("order_id"))
                                        orderData.setOrderReference(orderDataObject.getString("order_reference"))
                                        orderData.setStudentId(orderDataObject.getString("student_id"))
                                        orderData.setInstrumentId(orderDataObject.getString("instrument_id"))
                                        orderData.setLessonId(orderDataObject.getString("lesson_id"))
                                        orderData.setLessonName(orderDataObject.getString("lesson_name"))
                                        orderData.setTermId(orderDataObject.getString("term_id"))
                                        orderData.setTermName(orderDataObject.getString("term_name"))
                                        orderData.setLearningLevel(orderDataObject.getString("learning_level"))
                                        orderData.setLearningLevelStatus(orderDataObject.getString("learning_level_status"))
                                        orderData.setAmount(orderDataObject.getString("amount"))
                                        orderData.setCurrency(orderDataObject.getString("currency"))
                                        orderData.setCurrencySymbol(orderDataObject.getString("currency_symbol"))
                                        orderData.setTaxType(orderDataObject.getString("tax_type"))
                                        orderData.setTaxAmount(orderDataObject.getString("tax_amount"))
                                        orderData.setTaxPercentage(orderDataObject.optString("tax_percentage"))
                                        orderData.setTotalAmount(orderDataObject.getString("total_amount"))
                                        orderData.setStatus(orderDataObject.getString("status"))
                                        orderDataList.add(orderData)
                                    }
                                    val instrumentData = InstrumentDataModel()
                                    instrumentData.setInstrumentId(instrumentId)
                                    instrumentData.setInstrumentName(instrumentName)
                                    instrumentData.setOrderData(orderDataList)
                                    instrumentDataList.add(instrumentData)
                                }

                                // Now you have parsed instrument data with order items.
                                // You can use instrumentDataList as needed.
                                adapter = SummaryDataAdapter(instrumentDataList)
                                recyclerView!!.adapter = adapter

                                *//* } else {
                                                 AppUtils.showDialogAlertDismiss((Activity) context, "Alert", "No Data Available", R.drawable.exclamationicon, R.drawable.round);
                                                 // Handle the case where "data" doesn't exist in the JSON response
                                             }*//*
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        //                        } else {
//                            AppUtils.showDialogAlertDismiss((Activity) context, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);
//                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {}
        }
        )*/
    }



}