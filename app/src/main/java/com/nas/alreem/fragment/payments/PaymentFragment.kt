package com.nas.alreem.fragment.payments

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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec
import com.nas.alreem.R
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.payments.PaymentCategoryActivity
import com.nas.alreem.activity.payments.PaymentInformationActivity
import com.nas.alreem.activity.primary.PrimaryActivity
import com.nas.alreem.activity.settings.model.TermsOfServiceResponseModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.payments.model.PaymentResponseModel
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.fragment.primary.adapter.PrimaryAdapter
import com.nas.alreem.fragment.primary.model.PrimaryResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentFragment : Fragment() {
    lateinit var mContext: Context
    lateinit var progressDialogAdd: ProgressBar
    lateinit var bannerImageViewPager: ImageView
    lateinit var sendEmail: ImageView
    lateinit var paymentRelative: RelativeLayout
    lateinit var informationRelative: RelativeLayout
    lateinit var title: RelativeLayout
    lateinit var descriptionTitle: TextView
    lateinit var titleTextView: TextView
    var contact_email:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        initializeUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callPaymentBannerApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun initializeUI() {

        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        bannerImageViewPager=requireView().findViewById(R.id.bannerImageViewPager)
        paymentRelative=requireView().findViewById(R.id.paymentRelative)
        informationRelative=requireView().findViewById(R.id.informationRelative)
        sendEmail=requireView().findViewById(R.id.sendEmail)
        descriptionTitle=requireView().findViewById(R.id.descriptionTitle)
        title=requireView().findViewById(R.id.title)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        titleTextView.text="Payment"
        sendEmail.visibility=View.GONE
        paymentRelative.setOnClickListener(View.OnClickListener {

            // Payment Activity
            PreferenceManager.setStudentID(mContext,"")
            val intent = Intent(mContext, PaymentCategoryActivity::class.java)
            startActivity(intent)
        })

        informationRelative.setOnClickListener(View.OnClickListener {

            // Information Activity
            val intent = Intent(activity, PaymentInformationActivity::class.java)
            activity?.startActivity(intent)
        })

        sendEmail.setOnClickListener(View.OnClickListener {
            // Email Popup
            showSendEmailDialog()
        })


    }
    private fun callPaymentBannerApi()
    {
        progressDialogAdd.visibility=View.VISIBLE
        val call: Call<PaymentResponseModel> = ApiClient.getClient.paymentBanner("Bearer "+PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<PaymentResponseModel> {
            override fun onFailure(call: Call<PaymentResponseModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<PaymentResponseModel>, response: Response<PaymentResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {

                            title.visibility=View.VISIBLE
                            contact_email=response.body()!!.responseArray!!.data.contact_email
                            if (!contact_email.equals(""))
                            {
                                sendEmail.visibility=View.VISIBLE

                            }
                            else{
                                sendEmail.visibility=View.GONE
                            }
                            var description=response.body()!!.responseArray!!.data.description
                            descriptionTitle.visibility=View.VISIBLE
                            descriptionTitle.text=description

                            var imageBanner=response.body()!!.responseArray!!.data.image
                            if (imageBanner.isNotEmpty()) {
                                Glide.with(mContext) //1
                                    .load(imageBanner)
                                    .into(bannerImageViewPager)
                            } else {
                                bannerImageViewPager!!.setBackgroundResource(R.drawable.default_banner)
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
                        sendEmail(text_dialog.text.toString().trim(), text_content.text.toString().trim(), contact_email, dialog)
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
                            showSuccessAlert(
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


    fun showSuccessAlert(context: Context, message: String, msgHead: String, mdialog: Dialog) {
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