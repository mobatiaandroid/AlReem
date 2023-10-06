package com.nas.alreem.fragment.cca

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.cca.CCA_Activity
import com.nas.alreem.activity.cca.ExternalProviderActivity
import com.nas.alreem.activity.cca.InformationCCAActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.appcontroller.AppController
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.cca.model.BannerResponseModelCCa
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


  class CCAFragment : Fragment() {
    var mTitleTextView: TextView? = null
    var descriptionTV: TextView? = null
    var ccaDot: TextView? = null
    private var mRootView: View? = null
    lateinit var mContext: Context
    private val mTitle: String? = null
    private val mTabId: String? = null
    var mtitleRel: LinearLayout? = null
    var externalCCA: RelativeLayout? = null
    var informationCCA: RelativeLayout? = null
    var bannerImagePager: ImageView? = null
    var mailImageView: ImageView? = null
    var ccaOption: RelativeLayout? = null
    var contactEmail = ""
    private var description = ""
    var text_content: TextView? = null
    var text_dialog: TextView? = null
    lateinit var progress: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(
            R.layout.fragment_cca_main, container,
            false
        )

        mContext = requireActivity()
        initialiseUI()

        return mRootView
    }

    private fun initialiseUI() {
        mTitleTextView = mRootView!!.findViewById<View>(R.id.titleTextView) as TextView
        descriptionTV = mRootView!!.findViewById<View>(R.id.descriptionTitle) as TextView
        ccaDot = mRootView!!.findViewById<View>(R.id.ccaDot) as TextView
       // AppController.ccdots=ccaDot
        mTitleTextView!!.setText(R.string.Enrichment)
        mtitleRel = mRootView!!.findViewById<View>(R.id.title) as LinearLayout
        progress = mRootView!!.findViewById(R.id.progress)

        externalCCA = mRootView!!.findViewById<View>(R.id.epRelative) as RelativeLayout
        ccaOption = mRootView!!.findViewById<View>(R.id.CcaOptionRelative) as RelativeLayout
        informationCCA = mRootView!!.findViewById<View>(R.id.informationRelative) as RelativeLayout
        bannerImagePager = mRootView!!.findViewById<View>(R.id.bannerImagePager) as ImageView
        mailImageView = mRootView!!.findViewById<View>(R.id.mailImageView) as ImageView

        if (ConstantFunctions.internetCheck(mContext!!))
        {
            getList()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext!!)
        }

        externalCCA!!.setOnClickListener {
            val intent = Intent(mContext, ExternalProviderActivity::class.java)
            intent.putExtra("tab_type", "External Providers")
            startActivity(intent)
        }
        informationCCA!!.setOnClickListener {
            val intent = Intent(mContext, InformationCCAActivity::class.java)
            intent.putExtra("tab_type", "Information")
            startActivity(intent)
        }
        ccaOption!!.setOnClickListener {
            if (!PreferenceManager.getUserCode(mContext!!).equals("")) {
                PreferenceManager.setStudIdForCCA(mContext!!, "")
               // PreferenceManager.setStudentID(mContext!!, "")

                val intent = Intent(mContext, CCA_Activity::class.java)
                intent.putExtra("tab_type", "ECA Options")
                startActivity(intent)
            } else {
               ConstantFunctions.showDialogueWithOk(mContext!!,"This feature is available for Registered users only","Alert")
            }
        }
        mailImageView!!.setOnClickListener {
            showSendEmailDialog(mContext!!)
        }


    }


    private fun showSendEmailDialog(mContext: Context)
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

                } else {
                    // progressDialog.visibility = View.VISIBLE
                    if (ConstantFunctions.internetCheck(mContext))
                    {
                        callSendEmailToStaffApi(text_dialog.text.toString().trim(), text_content.text.toString().trim(), contactEmail, dialog)
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
    fun callSendEmailToStaffApi(
        title: String, message: String, staffEmail: String, dialog: Dialog)
    {
        val sendMailBody = SendEmailApiModel( staffEmail, title, message)
        val call: Call<SignUpResponseModel> = ApiClient.getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext!!))
        call.enqueue(object : Callback<SignUpResponseModel> {
            override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                //progressDialog.visibility = View.GONE
            }

            override fun onResponse(call: Call<SignUpResponseModel>, response: Response<SignUpResponseModel>) {
                val responsedata = response.body()
                //progressDialog.visibility = View.GONE
                Log.e("Response Signup", responsedata.toString())
                if (responsedata != null) {
                    try {


                        if (response.body()!!.status==100) {
                            dialog.dismiss()
                            showSuccessAlert(
                                mContext!!,
                                "Email sent Successfully ",
                                "Success",
                                dialog
                            )
                        }else {
                            DialogFunctions.commonErrorAlertDialog(
                                mContext!!.resources.getString(R.string.alert),
                                ConstantFunctions.commonErrorString(response.body()!!.status), mContext!!
                            )

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }

    private fun getList() {
        progress.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext!!)
        val call: Call<BannerResponseModelCCa> =
            ApiClient.getClient.getBanner( "Bearer $token")
        call.enqueue(object : Callback<BannerResponseModelCCa> {
            override fun onResponse(
                call: Call<BannerResponseModelCCa>,
                response: Response<BannerResponseModelCCa>
            ) {
                progress.visibility = View.GONE

                if (response.isSuccessful){
                    if (response.body() != null){
                        if (response.body()!!.status.toString() == "100"){
                            val bannerImage: String = response.body()!!.data!!.banner_image!!
                            description = response.body()!!.data!!.description!!
                            contactEmail = response.body()!!.data!!.contact_email!!
//
                            PreferenceManager.setCcaOptionBadge(
                                mContext!!,
                                response.body()!!.data!!.cca_badge
                            )
                            PreferenceManager.setCcaOptionEditedBadge(
                                mContext!!,
                                response.body()!!.data!!.cca_edited_badge
                            )
                            if (PreferenceManager.getCcaOptionBadge(mContext!!)!!.equals(0) &&
                                PreferenceManager.getCcaOptionEditedBadge(mContext!!)!!.equals(0)
                            ) {
                                ccaDot!!.setVisibility(View.GONE)
                            } else if (PreferenceManager.getCcaOptionBadge(mContext!!)!!.equals(0) &&
                                !PreferenceManager.getCcaOptionEditedBadge(mContext!!)!!.equals(0)
                            ) {
                                ccaDot!!.setVisibility(View.VISIBLE)
                               ccaDot!!.setText(response.body()!!.data!!.cca_edited_badge)
                               ccaDot!!.setBackgroundResource(R.drawable.shape_circle_navy)
                            } else if (!PreferenceManager.getCcaOptionBadge(mContext!!)!!.equals(0)
                                && PreferenceManager.getCcaOptionEditedBadge(
                                    mContext!!).equals(0)
                            ) {
                                ccaDot!!.setVisibility(View.VISIBLE)
                               ccaDot!!.setText(response.body()!!.data!!.cca_badge.toString())
                                ccaDot!!.setBackgroundResource(R.drawable.shape_circle_red)
                            } else if (!PreferenceManager.getCcaOptionBadge(mContext!!).equals(0)
                                && !PreferenceManager.getCcaOptionEditedBadge(mContext!!)!!.equals(0)
                            ) {
                               ccaDot!!.setVisibility(View.VISIBLE)
                                ccaDot!!.setText(response.body()!!.data!!.cca_badge)
                                ccaDot!!.setBackgroundResource(
                                    R.drawable.shape_circle_red
                                )
                            }
                            if (!bannerImage.equals("", ignoreCase = true)) {
                                Glide.with(mContext!!).load(ConstantFunctions.replace(bannerImage)).fitCenter()

                                    .centerCrop().into(bannerImagePager!!)

//											bannerUrlImageArray = new ArrayList<>();
//											bannerUrlImageArray.add(bannerImage);
//											bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, getActivity()));
                            } else {
                                bannerImagePager!!.setBackgroundResource(R.drawable.default_banner)
//											bannerImagePager.setBackgroundResource(R.drawable.ccas_banner);
                            }
                            println("contact mail$contactEmail")
                            if (description.equals("", ignoreCase = true) && contactEmail.equals(
                                    "",
                                    ignoreCase = true
                                )
                            ) {
                                mtitleRel!!.visibility = View.GONE
                            } else {
                                mtitleRel!!.visibility = View.VISIBLE
                            }
                            if (description.equals("", ignoreCase = true)) {
                                descriptionTV!!.visibility = View.GONE
                                //  descriptionTitle.setVisibility(View.GONE);
                            } else {
                                descriptionTV!!.text = description
                                descriptionTV!!.visibility = View.VISIBLE
                                mtitleRel!!.visibility = View.VISIBLE
                                // mtitleRel.setVisibility(View.VISIBLE);
                            }
                            if (contactEmail.equals("", ignoreCase = true)) {
                                println("contact mail1")
                                mailImageView!!.visibility = View.GONE
                            } else {
                                println("contact mail2")
                                mtitleRel!!.visibility = View.VISIBLE
                                mailImageView!!.visibility = View.VISIBLE
                            }
                            // CCAFRegisterRel.setVisibility(View.VISIBLE);
                            // CCAFRegisterRel.setVisibility(View.VISIBLE);

                        }else{
                            ConstantFunctions.showDialogueWithOk(mContext!!,getString(R.string.common_error),"Alert")
                        }
                    }else{
                        ConstantFunctions.showDialogueWithOk(mContext!!,getString(R.string.common_error),"Alert")
                    }
                }else{
                    ConstantFunctions.showDialogueWithOk(mContext!!,getString(R.string.common_error),"Alert")
                }
            }

            override fun onFailure(call: Call<BannerResponseModelCCa>, t: Throwable) {
                progress.visibility = View.GONE

                 ConstantFunctions.showDialogueWithOk(mContext!!,getString(R.string.common_error),"Alert")
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
        var text_dialog = dialog.findViewById(R.id.messageTxt) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.tick)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            mdialog.dismiss()
        }
        dialog.show()
    }

      override fun onResume() {
          super.onResume()
          Log.e("TEST","call 1")
          if (ConstantFunctions.internetCheck(mContext!!))
          {
              getList()
          }
          else
          {
              //DialogFunctions.showInternetAlertDialog(mContext!!)
          }
          /*if (!PreferenceManager.getCcaOptionBadge(mContext).equals("0")) {
              ccaDot!!.setText(PreferenceManager.getCcaOptionBadge(mContext))
          } else if (!PreferenceManager.getCcaOptionEditedBadge(mContext).equals("0")) {
              ccaDot!!.setText(PreferenceManager.getCcaOptionEditedBadge(mContext))
          } else {
              ccaDot!!.setVisibility(View.GONE)
          }*/

      }
}