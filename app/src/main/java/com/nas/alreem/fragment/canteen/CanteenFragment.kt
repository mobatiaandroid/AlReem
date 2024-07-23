package com.nas.alreem.fragment.canteen

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.CanteenPaymentActivity
import com.nas.alreem.activity.canteen.InformationActivity
import com.nas.alreem.activity.canteen.PreOrderActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.lost_card.LostCardMainActivity
import com.nas.alreem.activity.shop_new.Addorder_Activity_new
import com.nas.alreem.activity.shop_new.PreOrderActivity_new
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.canteen.model.CanteenBannerResponseModel
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CanteenFragment  : Fragment() {
    lateinit var mContext: Context
 //   lateinit var progress: RelativeLayout
    lateinit var email_icon: ImageView
    lateinit var preorder_image: LinearLayout
    lateinit var information_image: LinearLayout
    lateinit var payment_image: LinearLayout
    lateinit var staffLinear: LinearLayout
    //lateinit var lostcardLinear: LinearLayout
    lateinit var bannerImg:ImageView
    lateinit var title: TextView
    lateinit var description: TextView
    lateinit var contactEmail:String
    lateinit var progress: ProgressBar
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
    private val pattern = "^([a-zA-Z ]*)$"
     var walletTopUpLimit:Int=0
     var walletTopUpLimit_str:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.canteen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFn()
        onClick()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callGetCanteenBanner()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }


    }
    private fun initFn(){
        mContext = requireContext()
        title=view?.findViewById(R.id.titleTextView)!!
        title.text = "Lunch Box"
    //    progress = view?.findViewById(R.id.progressDialog)!!
    //    progress.visibility=View.GONE
        email_icon = view?.findViewById(R.id.email_icon)!!
        progress = view?.findViewById(R.id.progress)!!
       // lostcardLinear = view?.findViewById(R.id.lostcardLinear)!!
        preorder_image = view?.findViewById(R.id.preOrderLinear)!!
        information_image = view?.findViewById(R.id.informationLinear)!!
        payment_image = view?.findViewById(R.id.paymentLinear)!!
        bannerImg = view?.findViewById(R.id.bannerimagecanteen)!!
        description = view?.findViewById(R.id.description)!!
        staffLinear = view?.findViewById(R.id.linearLayout5)!!


    }
    private fun onClick() {

       /* lostcardLinear.setOnClickListener {
            val i = Intent(mContext, LostCardMainActivity::class.java)
            PreferenceManager.setStudentID(mContext,"")

            mContext.startActivity(i)
        }*/
        email_icon.setOnClickListener {
            showSendEmailDialog()
        }
        preorder_image.setOnClickListener {
            val i = Intent(mContext, PreOrderActivity::class.java)
            PreferenceManager.setStudentID(mContext,"")

            mContext.startActivity(i)
        }
        information_image.setOnClickListener {
            val i = Intent(mContext, InformationActivity::class.java)
            mContext.startActivity(i)
        }
        payment_image.setOnClickListener {
            val i = Intent(mContext, CanteenPaymentActivity::class.java)
            i.putExtra("WALLET_TOPUP_LIMIT",walletTopUpLimit_str.toString())
            PreferenceManager.setStudentID(mContext,"")
            mContext.startActivity(i)
        }
        staffLinear.setOnClickListener(View.OnClickListener {

        })
    }


    fun callGetCanteenBanner()
    {
        progress.visibility = View.VISIBLE

        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CanteenBannerResponseModel> = ApiClient.getClient.get_canteen_banner("Bearer "+token)
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
                    walletTopUpLimit_str=response.body()!!.responseArray.data.wallet_topup_limit
                    PreferenceManager.setTopUpLimit(mContext,response.body()!!.responseArray.data.wallet_topup_limit)
                    walletTopUpLimit=walletTopUpLimit_str.toInt()
                    var banner_image=response.body()!!.responseArray.data.image
                    var trn_no=response.body()!!.responseArray.data.trn_no
                    PreferenceManager.setTrnNo(mContext,trn_no)
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
                DialogFunctions.commonErrorAlertDialog(
                    mContext.resources.getString(R.string.alert), resources.getString(R.string.enter_subject),
                    mContext
                )


            } else {
                if (text_content.text.toString().trim().equals("")) {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert), resources.getString(R.string.enter_content),
                        mContext
                    )

                } else if (contactEmail.matches(EMAIL_PATTERN.toRegex())) {
                    if (text_dialog.text.toString().trim ().matches(pattern.toRegex())) {
                        if (text_content.text.toString().trim ()
                                .matches(pattern.toRegex())) {

                            if (ConstantFunctions.internetCheck(mContext))
                            {
                                sendEmail(text_dialog.text.toString().trim(), text_content.text.toString().trim(), contactEmail, dialog)

                            }
                            else
                            {
                                DialogFunctions.showInternetAlertDialog(mContext)
                            }

                        } else {
                            val toast: Toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.enter_valid_contents), Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    } else {
                        val toast: Toast = Toast.makeText(
                            mContext,
                            mContext.getResources()
                                .getString(
                                    R.string.enter_valid_subjects
                                ),
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                } else {
                    val toast: Toast = Toast.makeText(
                        mContext,
                        mContext.getResources()
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
        val call: Call<SignUpResponseModel> = ApiClient.getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext))
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


    fun showSuccessAlertnew(context: Context, message: String, msgHead: String, mdialog: Dialog) {
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
            mdialog.dismiss()
        }
        dialog.show()
    }
}