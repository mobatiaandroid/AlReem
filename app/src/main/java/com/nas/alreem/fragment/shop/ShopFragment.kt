package com.nas.alreem.fragment.shop

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.CanteenPaymentActivity
import com.nas.alreem.activity.canteen.InformationActivity
import com.nas.alreem.activity.canteen.PreOrderActivity
import com.nas.alreem.activity.canteen.model.DateModel
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.activity.shop.RegisteredShopSummaryActivity
import com.nas.alreem.activity.shop.ShopInformationActivity
import com.nas.alreem.activity.shop.ShopRegisterActivity
import com.nas.alreem.activity.shop_new.Addorder_Activity_new
import com.nas.alreem.activity.shop_new.InvoiceListingActivity
import com.nas.alreem.activity.shop_new.OrderhistoryActivityNew
import com.nas.alreem.activity.shop_new.PreOrderActivity_new
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


class ShopFragment : Fragment() {
    lateinit var mContext: Context
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
    //  lateinit var progress: ProgressBar
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.canteen_preorder_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFn()
        onClick()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callStudentListApi()
            callGetShopBanner()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }


    }
    private fun initFn(){
        mContext = requireContext()
        title=view?.findViewById(R.id.titleTextView)!!
        title.text = "Shop"
        studImg = view?.findViewById(R.id.imagicon)!!
        studentNameTxt =view?. findViewById(R.id.studentName)!!
        studentSpinner =view?. findViewById(R.id.studentSpinner)!!
        studentlist = ArrayList()
        dropdown =view?.findViewById(R.id.studentSpinner)!!
        progressDialog = view?.findViewById(R.id.progressDialog)!!
        //  progress =findViewById(R.id.progressDialogAdd)!!
        add_order =view?. findViewById(R.id.addOrderRelative)!!
        my_orders =view?. findViewById(R.id.myOrderRelative)!!
        order_history = view?.findViewById(R.id.orderHistoryRelative)!!
        buttonLinear = view?.findViewById(R.id.buttonLinear)!!

        email_icon =view?. findViewById(R.id.email_icon)!!
        description =view?.findViewById(R.id.description)!!
        bannerImg =view?. findViewById(R.id.bannerImage)!!
        PreferenceManager.setbackkey(mContext,"")

        email_icon.setOnClickListener {
            showSendEmailDialog()
        }
        studentSpinner.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                showStudentList(mContext,studentListArrayList)

            }
        })
    }
    private fun onClick() {

        add_order.setOnClickListener {

            val intent = Intent(mContext, Addorder_Activity_new::class.java)
            intent.putExtra("date_list",mDateArrayList)
            startActivity(intent)

        }

        order_history.setOnClickListener {
            PreferenceManager.setStudentID(mContext,"")
            val intent = Intent(mContext, InvoiceListingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
        my_orders.setOnClickListener {
            val intent = Intent(mContext, OrderhistoryActivityNew::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }



    fun callStudentListApi()
    {
        progressDialog.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<StudentListModel> = ApiClient(mContext).getClient.studentList("Bearer "+token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                progressDialog.visibility = View.GONE

                if (response.body()!!.status==100)
                {
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
                    add_order.visibility= View.VISIBLE
                    buttonLinear.visibility= View.VISIBLE

                }


            }

        })
    }
    fun callGetShopBanner()
    {
        progressDialog.visibility = View.VISIBLE

        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CanteenBannerResponseModel> = ApiClient(mContext).getClient.shop_banner("Bearer "+token)
        call.enqueue(object : Callback<CanteenBannerResponseModel>
        {
            override fun onFailure(call: Call<CanteenBannerResponseModel>, t: Throwable) {
                progressDialog.visibility = View.GONE


            }
            override fun onResponse(call: Call<CanteenBannerResponseModel>, response: Response<CanteenBannerResponseModel>) {
                val responsedata = response.body()
                progressDialog.visibility = View.GONE

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

                        Glide.with(mContext) //1
                            .load(banner_image)
                            .into(bannerImg)
                    } else {
                        bannerImg!!.setBackgroundResource(R.drawable.default_banner)
                    }

                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
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

                //   progressDialog.visibility= View.VISIBLE


                studentName=studentListArrayList.get(position).name
                studentImg=studentListArrayList.get(position).photo
                studentId=studentListArrayList.get(position).id
                studentClass=studentListArrayList.get(position).section
                PreferenceManager.setStudentID(mContext,studentId)
                PreferenceManager.setStudentName(mContext,studentName)
                PreferenceManager.setStudentPhoto(mContext,studentImg)
                PreferenceManager.setStudentClass(mContext,studentClass)
                studentNameTxt.text=studentName
                //studentInfoArrayList.clear()
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
                add_order.visibility= View.VISIBLE
                buttonLinear.visibility= View.VISIBLE
                //  progressDialog.visibility = View.VISIBLE


                dialog.dismiss()
            }
        })
        dialog.show()
    }
    private fun showSendEmailDialog()
    {
        val dialog = Dialog(mContext)
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
                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), resources.getString(R.string.enter_subject), mContext)


            } else {
                if (text_content.text.toString().trim().equals("")) {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), resources.getString(R.string.enter_content), mContext)

                } else {
                    // progressDialog.visibility = View.VISIBLE
                    if (ConstantFunctions.internetCheck(mContext))
                    {
                        sendEmail(text_dialog.text.toString().trim(), text_content.text.toString().trim(), contactEmail, dialog)
                    }
                    else
                    {
                        DialogFunctions.showInternetAlertDialog(mContext)
                    }

                }
            }
        }
        dialog.show()
    }


    fun sendEmail(title: String, message: String,  staffEmail: String, dialog: Dialog)
    {

        val sendMailBody = SendEmailApiModel( staffEmail, title, message)
        val call: Call<SignUpResponseModel> = ApiClient(mContext).getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext))
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
                            dialog.dismiss()
                            showSuccessAlertnew(
                                mContext,
                                "Email sent Successfully ",
                                "Success",
                                dialog
                            )
                        }else {
                            DialogFunctions.commonErrorAlertDialog(
                                mContext.resources.getString(R.string.alert),
                                ConstantFunctions.commonErrorString(response.body()!!.status),
                                mContext
                            )

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }


    fun showSuccessAlertnew(context: Context, message: String, msgHead: String, dialog1: Dialog) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.messageTxt) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.tick)
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
