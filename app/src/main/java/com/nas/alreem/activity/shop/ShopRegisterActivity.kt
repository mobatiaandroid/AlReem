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
import com.nas.alreem.activity.shop.Adapter.TermsAdapter
import com.nas.alreem.activity.shop.model.Instrument
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
import java.text.DecimalFormat

class ShopRegisterActivity : AppCompatActivity() {
    lateinit var studentsArrayList: ArrayList<StudentList>
 //   var mCCAmodelArrayList: ArrayList<CCAModel>? = null
   // var CCADetailModelArrayList: ArrayList<CCADetailModel>? = null
  //  var CCAchoiceModelArrayList: ArrayList<CCAchoiceModel>? = null
  //  var CCAchoiceModelArrayList2: ArrayList<CCAchoiceModel>? = null
    lateinit var studentName: TextView

    //    TextView textViewYear;
    var enterTextView: TextView? = null
    var stud_id = ""
    var stud_class = ""
    var stud_name = ""
    var studentSpinnerView: LinearLayout? = null
    var instrumentRecyclerView: RecyclerView? = null
  //  var headermanager: HeaderManager? = null
    var relativeHeader: RelativeLayout? = null
    var back: ImageView? = null
    lateinit var studImg: ImageView
    var stud_img = ""
    var home: ImageView? = null
    var tab_type = "Music Academy Registration"
    var text_content: TextView? = null
    var text_dialog: TextView? = null
    var instrumentsList: ArrayList<Instrument>? = null
    var extras: Bundle? = null
    var recyclerViewLayoutManager: GridLayoutManager? = null
    var termsAdapter: TermsAdapter? = null
    lateinit var context: Context
    var isBeginner = ""
    lateinit var payButton: ConstraintLayout
    var totalAmount: TextView? = null
    var totalValue = 0.0
   // var progressBarDialog: ProgressBarDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_shop)
        context = this
        initialiseUI()
       // if (AppUtils.isNetworkConnected(context)) {
            getStudentsList()
            //            getStudentsListAPI(URL_GET_STUDENT_LIST);
       // } else {
            /*AppUtils.showDialogAlertDismiss(
                context as Activity?,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )*/
       // }
        studentSpinnerView!!.setOnClickListener { v: View? ->
            showStudentSelectionPopUp(
                studentsArrayList
            )
        }
    }


    private fun initialiseUI() {
//        extras = getIntent().getExtras();
//        if (extras != null) {
//            tab_type = extras.getString("tab_type");
//        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        instrumentRecyclerView = findViewById<View>(R.id.instrumentRecycler) as RecyclerView
        totalAmount = findViewById<TextView>(R.id.totalAmount)
        payButton = findViewById<ConstraintLayout>(R.id.payButton)
        instrumentRecyclerView!!.setHasFixedSize(true)
        recyclerViewLayoutManager = GridLayoutManager(context, 1)
     //   progressBarDialog = ProgressBarDialog(context, R.drawable.spinner)
        val spacing = 5 // 50px
     //   val itemDecoration = ItemOffsetDecoration(context, spacing)
        if (totalValue == 0.0) {
            payButton.setVisibility(View.GONE)
        } else {
            payButton.setVisibility(View.VISIBLE)
        }
        payButton.setOnClickListener(View.OnClickListener {
            val mIntent = Intent(context, SummaryActivity::class.java)
            startActivity(mIntent)
        })
        //        instrumentRecyclerView.addItemDecoration(
//                new DividerItemDecoration(context.getResources().getDrawable(R.drawable.list_divider)));
//        instrumentRecyclerView.addItemDecoration(itemDecoration);
        instrumentRecyclerView!!.layoutManager = recyclerViewLayoutManager
       // headermanager = HeaderManager(this@RegisterMusicActivity, tab_type)
       // headermanager.getHeader(relativeHeader, 0)
       // back = headermanager.getLeftButton()
       /* headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )*/
        back!!.setOnClickListener {
          //  AppUtils.hideKeyBoard(context)
            finish()
        }
      //  home = headermanager.getLogoButton()
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
        //        enterTextView = (TextView) findViewById(R.id.enterTextView);
//        textViewYear = (TextView) findViewById(R.id.textViewYear);
        instrumentRecyclerView!!.addOnItemTouchListener(
            RecyclerItemListener(
                context,
                instrumentRecyclerView!!,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (instrumentsList!![position].getInstrumentSelected() === 1) {
                            //TODO delete
//                    Intent intent = new Intent(context, InstrumentDetailView.class);
//                    intent.putExtra("instrument", instrumentsList.get(position));
//                    context.startActivity(intent);
                            showDeleteYesOrNoPopUp(
                                context,
                                instrumentsList!![position].getInstrumentId(),
                                position
                            )
                            //                    callDeleteAPI(instrumentsList.get(position).getInstrumentId());
                        } else {
//                    showInstrumentDetailsPopUp(instrumentsList.get(position));
                            if (instrumentsList!![position].getTermData().size > 0) {
                                val intent = Intent(
                                    context,
                                    ShopDetailView::class.java
                                )
                                intent.putExtra("instrument", instrumentsList!![position])
                                context!!.startActivity(intent)
                            } else {
                              /*  AppUtils.showDialogAlertDismiss(
                                    context as Activity?,
                                    "Alert",
                                    "No Classes Available",
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )*/
                            }
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
    }

    private fun showDeleteYesOrNoPopUp(context: Context?, instrumentId: String, position: Int) {
        val dialog = Dialog(this.context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        icon.setBackgroundResource(R.drawable.round)
      //  icon.setImageResource(R.drawable.remove)
        val text = dialog.findViewById<View>(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text =
            "You have selected course for this instrument. Do you want to cancel or edit the selection?"
        textHead.text = "Delete or Edit Selection?"
        val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
        dialogButton.text = "Delete"
        val cancelButton = dialog.findViewById<Button>(R.id.btn_Cancel)
        cancelButton.text = "Edit"
        cancelButton.setOnClickListener {
            val intent = Intent(
                context,
                ShopDetailView::class.java
            )
            intent.putExtra("instrument", instrumentsList!![position])
            context!!.startActivity(intent)
            dialog.dismiss()
        }
        dialogButton.setOnClickListener {
            deleteMusicSelection(instrumentId)
            //                callDeleteAPI(instrumentId);
            dialog.dismiss()
        }
        //		Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_Cancel);
//		dialogButtonCancel.setVisibility(View.GONE);
//		dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
        dialog.show()
    }

    private fun deleteMusicSelection(instrumentID: String) {

    }


   /* private fun getSelectedLessonIds(): ArrayList<Int>? {
        val selectedIds = ArrayList<Int>()
        for (term in termsAdapter.getTerms()) {
            for (lesson in term.getLessonData()) {
                if (lesson.isSelected()) {
                    selectedIds.add(lesson.getId())
                }
            }
        }
        return selectedIds
    }*/


    private fun showRefreshPopUp(
        activity: Activity,
        msgHead: String,
        msg: String,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<View>(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener { //                callInstrumentsAPI(stud_id);
            callInstrumentsListAPI(stud_id)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun callCartSummaryAPI(stud_id: String) {
        /*val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        progressBarDialog.show()
        paramObject.addProperty("student_id", stud_id)
        val call: Call<ResponseBody> = service.postMusicCartSummary(
            "Bearer " + PreferenceManager.getAccessToken(context),
            paramObject
        )
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                progressBarDialog.hide()
                if (response.body() != null) {
                    try {
                        val responseString = response.body()!!.string()
                        val rootObject = JSONObject(responseString)
                        val responseCode = rootObject.getString("responsecode")
                        val responseData =
                            rootObject.getJSONObject("response").getJSONObject("data")
                        //                        String statusCode = responseData.getString("statuscode");
//                        if (statusCode.equalsIgnoreCase("303")) {
                        try {
                            if (responseCode == "200") {
                                val responseDataObject =
                                    rootObject.getJSONObject("response").getJSONObject("data")
                                val instrumentDataArray =
                                    responseDataObject.getJSONArray("instrument_data")
                                val instrumentDataList: ArrayList<CartInstrumentDataModel> =
                                    ArrayList<CartInstrumentDataModel>()
                                for (i in 0 until instrumentDataArray.length()) {
                                    val instrumentDataObject = instrumentDataArray.getJSONObject(i)
                                    val instrumentId = instrumentDataObject.getInt("instrument_id")
                                    val instrumentName =
                                        instrumentDataObject.getString("instrument_name")
                                    val cartDataArray =
                                        instrumentDataObject.getJSONArray("cart_data")
                                    val cartItemList: ArrayList<CartItemModel> =
                                        ArrayList<CartItemModel>()
                                    for (j in 0 until cartDataArray.length()) {
                                        val cartItemObject = cartDataArray.getJSONObject(j)
                                        val cartItem = CartItemModel()
                                        cartItem.setCartId(cartItemObject.getInt("cart_id"))
                                        cartItem.setStudentId(cartItemObject.getString("student_id"))
                                        cartItem.setInstrumentId(cartItemObject.getString("instrument_id"))
                                        cartItem.setLessonId(cartItemObject.getString("lesson_id"))
                                        cartItem.setLearningLevel(cartItemObject.getString("learning_level"))
                                        cartItem.setLearningLevelStatus(cartItemObject.getString("learning_level_status"))
                                        cartItem.setAmount(cartItemObject.getString("amount"))
                                        cartItem.setCurrency(cartItemObject.getString("currency"))
                                        cartItem.setCurrencySymbol(cartItemObject.getString("currency_symbol"))
                                        cartItem.setTaxType(cartItemObject.getString("tax_type"))
                                        cartItem.setTaxAmount(cartItemObject.getString("tax_amount"))
                                        cartItem.setTaxPercentage(cartItemObject.getString("tax_percentage"))
                                        cartItem.setTotalAmount(cartItemObject.getString("total_amount"))
                                        cartItem.setLessonName(cartItemObject.getString("lesson_name"))
                                        cartItem.setTermID(cartItemObject.getString("term_id"))
                                        cartItem.setTermName(cartItemObject.getString("term_name"))
                                        cartItemList.add(cartItem)
                                    }
                                    val instrumentData = CartInstrumentDataModel()
                                    instrumentData.setInstrumentId(instrumentId)
                                    instrumentData.setInstrumentName(instrumentName)
                                    instrumentData.setCartData(cartItemList)
                                    instrumentDataList.add(instrumentData)
                                    totalValue = 0.0
                                    for (temp in instrumentDataList) {
                                        val cartData: ArrayList<CartItemModel> = temp.getCartData()
                                        for (cartItem in cartData) {
                                            val lessonTotalAmount: Double =
                                                cartItem.getTotalAmount().toDouble()
                                            totalValue += lessonTotalAmount
                                        }
                                    }
                                    payButton!!.visibility = View.GONE
                                    if (totalValue > 0) {
                                        val decimalFormat = DecimalFormat("0.00")
                                        val roundedValue = decimalFormat.format(totalValue)
                                        payButton!!.visibility = View.VISIBLE
                                        totalAmount!!.text = "$roundedValue AED"
                                    } else {
                                        payButton!!.visibility = View.GONE
                                    }
                                }
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

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                progressBarDialog.hide()
                AppUtils.showDialogAlertDismiss(
                    context as Activity?,
                    "Alert",
                    getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        })*/
    }

    private fun callCartSummary(studId: String) {
    //    val volleyWrapper = VolleyWrapper(URL_MUSIC_ACADEMY_SUMMARY)
        /*val name = arrayOf("access_token", "student_id")
        val value = arrayOf<String>(PreferenceManager.getAccessToken(context), studId)
        volleyWrapper.getResponsePOST(context, 11, name, value, object : ResponseListener() {
            fun responseSuccess(successResponse: String?) {
                if (successResponse != null) {
                    try {
                        val rootObject = JSONObject(successResponse)
                        val responseCode = rootObject.getString("responsecode")
                        if (responseCode == "200") {
                            val responseData =
                                rootObject.getJSONObject("response").getJSONObject("data")
                            val instrumentDataArray = responseData.getJSONArray("instrument_data")
                            val instrumentDataList: ArrayList<CartInstrumentDataModel> =
                                ArrayList<CartInstrumentDataModel>()
                            for (i in 0 until instrumentDataArray.length()) {
                                val instrumentDataObject = instrumentDataArray.getJSONObject(i)
                                val instrumentId = instrumentDataObject.getInt("instrument_id")
                                val instrumentName =
                                    instrumentDataObject.getString("instrument_name")
                                val cartDataArray = instrumentDataObject.getJSONArray("cart_data")
                                val cartItemList: ArrayList<CartItemModel> =
                                    ArrayList<CartItemModel>()
                                for (j in 0 until cartDataArray.length()) {
                                    val cartItemObject = cartDataArray.getJSONObject(j)
                                    val cartItem = CartItemModel()
                                    cartItem.setCartId(cartItemObject.getInt("cart_id"))
                                    cartItem.setStudentId(cartItemObject.getString("student_id"))
                                    cartItem.setInstrumentId(cartItemObject.getString("instrument_id"))
                                    cartItem.setLessonId(cartItemObject.getString("lesson_id"))
                                    cartItem.setLearningLevel(cartItemObject.getString("learning_level"))
                                    cartItem.setLearningLevelStatus(cartItemObject.getString("learning_level_status"))
                                    cartItem.setAmount(cartItemObject.getString("amount"))
                                    cartItem.setCurrency(cartItemObject.getString("currency"))
                                    cartItem.setCurrencySymbol(cartItemObject.getString("currency_symbol"))
                                    cartItem.setTaxType(cartItemObject.getString("tax_type"))
                                    cartItem.setTaxAmount(cartItemObject.getString("tax_amount"))
                                    cartItem.setTaxPercentage(cartItemObject.getString("tax_percentage"))
                                    cartItem.setTotalAmount(cartItemObject.getString("total_amount"))
                                    cartItem.setLessonName(cartItemObject.getString("lesson_name"))
                                    cartItem.setTermID(cartItemObject.getString("term_id"))
                                    cartItem.setTermName(cartItemObject.getString("term_name"))
                                    cartItemList.add(cartItem)
                                }
                                val instrumentData = CartInstrumentDataModel()
                                instrumentData.setInstrumentId(instrumentId)
                                instrumentData.setInstrumentName(instrumentName)
                                instrumentData.setCartData(cartItemList)
                                instrumentDataList.add(instrumentData)
                                totalValue = 0.0
                                for (temp in instrumentDataList) {
                                    val cartData: ArrayList<CartItemModel> = temp.getCartData()
                                    for (cartItem in cartData) {
                                        val lessonTotalAmount: Double =
                                            cartItem.getTotalAmount().toDouble()
                                        totalValue += lessonTotalAmount
                                    }
                                }
                                payButton!!.visibility = View.GONE
                                if (totalValue > 0) {
                                    val decimalFormat = DecimalFormat("0.00")
                                    val roundedValue = decimalFormat.format(totalValue)
                                    payButton!!.visibility = View.VISIBLE
                                    totalAmount!!.text = "$roundedValue AED"
                                } else {
                                    payButton!!.visibility = View.GONE
                                }
                            }

                            // Now you have parsed instrument data with cart items.
                            // You can use instrumentDataList as needed.
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            fun responseFailure(failureResponse: String?) {}
        })*/
    }

   /* private fun getSelectedLessons(): ArrayList<Lesson>? {
        val selectedLessons: ArrayList<Lesson> = ArrayList<Lesson>()
        for (term in termsAdapter.getTerms()) {
            for (lesson in term.getLessonData()) {
                if (lesson.isSelected()) {
                    selectedLessons.add(lesson)
                }
            }
        }
        return selectedLessons
    }*/

    fun showStudentSelectionPopUp(mStudentArray: ArrayList<StudentList>) {
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
        val studentAdapter = StudentListAdapter(context, mStudentArray)
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

                        } else {
                            studImg!!.setImageResource(R.drawable.boy)
                        }
                        callInstrumentsListAPI(stud_id)
                        //                        callInstrumentsAPI(stud_id);
                        // TODO call instruments api
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        dialog.show()
    }

    private fun getStudentsList() {
       // progress.visibility = View.VISIBLE
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
       /* val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val call: Call<StudentDataModel> =
            service.postStudentList("Bearer " + PreferenceManager.getAccessToken(context))
        progressBarDialog.show()
        call.enqueue(object : Callback<StudentDataModel?> {
            override fun onResponse(
                call: Call<StudentDataModel?>,
                response: Response<StudentDataModel?>
            ) {
                if (response.body() != null) {
                    progressBarDialog.hide()
                    val responseCode: String = response.body().getResponsecode()
                    val statusCode: String = response.body().getResponse().getStatuscode()
                    //                    studentDataList = new ArrayList<>();
                    if (response.body().getResponsecode().equalsIgnoreCase("200")) {
                        if (statusCode.equals("303", ignoreCase = true)) {
                            if (response.body().getResponse().getData().size() > 0) {
                                if (response.body().getResponse().getData().size() > 0) {
                                    studentsArrayList = ArrayList<StudentModel>()
                                    for (i in 0 until response.body().getResponse().getData()
                                        .size()) {
                                        val item: StudentDataModel.DataItem =
                                            response.body().getResponse().getData().get(i)
                                        val gson = Gson()
                                        val eventJson = gson.toJson(item)
                                        try {
                                            val jsonObject = JSONObject(eventJson)
                                            studentsArrayList!!.add(addStudentDetails(jsonObject)) //  Log.e("array", String.valueOf(mCCAmodelArrayList));
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                        studentName.setText(studentsArrayList!![0].getmName())
                                        stud_id = studentsArrayList!![0].getmId()
                                        stud_name = studentsArrayList!![0].getmName()
                                        stud_class = studentsArrayList!![0].getmClass()
                                        //                                textViewYear.setText("Class : " + studentsArrayList.get(0).getmClass());
                                        stud_img = studentsArrayList!![0].getmPhoto()
                                        PreferenceManager.setStudentID(context, stud_id)
                                        PreferenceManager.setStudentName(context, stud_name)
                                        if (stud_img != "") {
                                            Picasso.with(context).load(AppUtils.replace(stud_img))
                                                .placeholder(R.drawable.boy).fit().into(studImg)
                                        } else {
                                            studImg!!.setImageResource(R.drawable.boy)
                                        }


//                                    callInstrumentsAPI(stud_id);
                                    }
                                    callInstrumentsListAPI(stud_id)
                                } else {
                                    AppUtils.showDialogAlertDismiss(
                                        context as Activity?,
                                        "Alert",
                                        getString(R.string.student_not_available),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                }
                            } else {
                                AppUtils.showDialogAlertDismiss(
                                    context as Activity?,
                                    "Alert",
                                    getString(R.string.nodatafound),
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            }
                        } else {
                            AppUtils.showDialogAlertDismiss(
                                context as Activity?,
                                "Alert",
                                getString(R.string.common_error),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    } else if (response.body().getResponsecode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                        response.body().getResponsecode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                        response.body().getResponsecode().equalsIgnoreCase(RESPONSE_INVALID_TOKEN)
                    ) {
                        AppUtils.postInitParam(context, object : GetAccessTokenInterface() {
                            val accessToken: Unit
                                get() {}
                        })
                        getStudentsList()
                    } else if (response.body().getResponsecode().equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                        //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                        AppUtils.showDialogAlertDismiss(
                            context as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils.showDialogAlertDismiss(
                            context as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                } else {
                    AppUtils.showDialogAlertDismiss(
                        context as Activity?,
                        "Alert",
                        getString(R.string.common_error),
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                }
            }

            override fun onFailure(call: Call<StudentDataModel?>, t: Throwable) {
                progressBarDialog.hide()
                AppUtils.showDialogAlertDismiss(
                    context as Activity?,
                    "Alert",
                    getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        })*/
    }



    private fun callInstrumentsListAPI(studId: String) {
        /*val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", studId)
        val call: Call<InstrumentListResponseModel> = service.postMusicalInstruments(
            "Bearer " + PreferenceManager.getAccessToken(context),
            paramObject
        )
        progressBarDialog.show()
        call.enqueue(object : Callback<InstrumentListResponseModel?> {
            override fun onResponse(
                call: Call<InstrumentListResponseModel?>,
                response: Response<InstrumentListResponseModel?>
            ) {
                if (response.body() != null) {
                    progressBarDialog.hide()
                    val responseCode: String = response.body().getResponseCode()
                    val statusCode: String = response.body().getResponse().getStatusCode()
                    instrumentsList = ArrayList<Instrument>()
                    if (statusCode.equals("303", ignoreCase = true)) {
                        if (response.body().getResponse().getData().getInstrumentDataList()
                                .size() > 0
                        ) {
                            for (i in 0 until response.body().getResponse().getData()
                                .getInstrumentDataList().size()) {
                                val item: InstrumentListResponseModel.InstrumentData =
                                    response.body().getResponse().getData().getInstrumentDataList()
                                        .get(i)
                                val gson = Gson()
                                val eventJson = gson.toJson(item)
                                try {
                                    val jsonObject = JSONObject(eventJson)
                                    instrumentsList!!.add(addInstrumentDetails(jsonObject))
                                    //  Log.e("array", String.valueOf(mCCAmodelArrayList));
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                                callCartSummaryAPI(stud_id)
                                //                                callCartSummary(stud_id);
                                setInstrumentsListAdapter(instrumentsList)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<InstrumentListResponseModel?>, t: Throwable) {
                progressBarDialog.hide()
                AppUtils.showDialogAlertDismiss(
                    context as Activity?,
                    "Alert",
                    getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        })*/
    }



    /*private fun addInstrumentDetails(instrumentObject: JSONObject): Instrument {
        val instrument = Instrument()
        instrument.setInstrumentId(instrumentObject.optString("instrument_id"))
        instrument.setInstrumentName(instrumentObject.optString("instrument_name"))
        instrument.setInstrumentSelected(instrumentObject.optInt("instrument_selected"))
        val termDataArray = instrumentObject.optJSONArray("term_data")
        val termList: ArrayList<Term> = ArrayList<Term>()
        for (j in 0 until termDataArray.length()) {
            var termObject: JSONObject? = null
            termObject = try {
                termDataArray.getJSONObject(j)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
            val term = Term()
            term.setTermId(termObject.optString("term_id"))
            term.setTermName(termObject.optString("term_name"))
            term.setRemainingSlotCount(termObject.optString("remaining_slot_count"))
            val lessonDataArray = termObject.optJSONArray("lesson_data")
            val lessonList: ArrayList<Lesson> = ArrayList<Lesson>()
            for (k in 0 until lessonDataArray.length()) {
                var lessonObject: JSONObject? = null
                lessonObject = try {
                    lessonDataArray.getJSONObject(k)
                } catch (e: JSONException) {
                    throw RuntimeException(e)
                }
                val lesson = Lesson()
                lesson.setId(lessonObject.optInt("id"))
                lesson.setName(lessonObject.optString("name"))
                lesson.setCurrency(lessonObject.optString("currency"))
                lesson.setCurrencySymbol(lessonObject.optString("currency_symbol"))
                lesson.setTotalAmount(lessonObject.optString("total_amount"))
                lesson.setCourseSelected(lessonObject.optInt("course_selected"))
                lesson.setOrderSuccess(lessonObject.optInt("order_success"))
                try {
                    lesson.setTermID(lessonObject.getString("term_id"))
                } catch (e: JSONException) {
                    throw RuntimeException(e)
                }
                lessonList.add(lesson)
            }
            term.setLessonData(lessonList)
            termList.add(term)
        }
        instrument.setTermData(termList)
        return instrument
    }*/

    private fun setInstrumentsListAdapter(instrumentsList: ArrayList<Instrument>?) {
       // val adapter = InstrumentListAdapter(context as Activity?, instrumentsList)
      //  instrumentRecyclerView!!.adapter = adapter
    }


   // private fun addStudentDetails(dataObject: JSONObject): StudentList {
       // val studentModel = StudentList()
       // studentModel.setmId(dataObject.optString(JTAG_ID))
       // studentModel.setmName(dataObject.optString(JTAG_TAB_NAME))
       // studentModel.setmClass(dataObject.optString(JTAG_TAB_CLASS))
       /// studentModel.setmSection(dataObject.optString(JTAG_TAB_SECTION))
       // studentModel.setmHouse(dataObject.optString("house"))
       // studentModel.setmPhoto(dataObject.optString("photo"))
     //   return studentModel
   // }

}
