package com.nas.alreem.fragment.shop

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.shop.RegisteredShopSummaryActivity
import com.nas.alreem.activity.shop.ShopInformationActivity
import com.nas.alreem.activity.shop.ShopRegisterActivity
import com.nas.alreem.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShopFragment : Fragment {
    var titleTextView: TextView? = null
    var descriptionTextView: TextView? = null
    lateinit var registerMusicButton: ConstraintLayout
    lateinit var infoButton: ConstraintLayout
    lateinit var summaryButton: ConstraintLayout
    var mtitleRel: LinearLayout? = null
    var externalCCA: RelativeLayout? = null
    var informationCCA: RelativeLayout? = null
    var bannerImagePager: ImageView? = null
    lateinit var mailImageView: ImageView
    var ccaOption: RelativeLayout? = null
    var contactEmail = ""
    var text_content: TextView? = null
    var text_dialog: TextView? = null
    private var title: String? = null
    private var tabID: String? = null
    private var rootView: View? = null
    lateinit var mContext: Context
    private val description = ""
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
    private val pattern = "^([a-zA-Z ]*)$"
   // var progressBarDialog: ProgressBarDialog? = null

    constructor()
    constructor(title: String?, tabId: String?) {
        this.title = title
        tabID = tabId
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(
            R.layout.fragment_shop_acad, container,
            false
        )
        setHasOptionsMenu(true)
        mContext=requireContext()
        initialiseUI()
        /*if (AppUtils.checkInternet(context)) {
            // call music academy banner api
            callMusicAcademyBannerAPI()
        } else {
            AppUtils.showDialogAlertDismiss(
                context as Activity?,
                context!!.getString(R.string.alert_heading),
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }*/
        return rootView
    }

    // Checking branch sanju
    // Checking branch sajna
    private fun callMusicAcademyBannerAPI() {
        // Log.e("success","sucess");




    }

    private fun initialiseUI() {
        titleTextView = rootView!!.findViewById<View>(R.id.titleTextView) as TextView
        descriptionTextView = rootView!!.findViewById<View>(R.id.descriptionTitle) as TextView
        titleTextView!!.text = "Music Academy"
        registerMusicButton = rootView!!.findViewById<ConstraintLayout>(R.id.registerMusic)
        bannerImagePager = rootView!!.findViewById<ImageView>(R.id.bannerImage)
        infoButton = rootView!!.findViewById<ConstraintLayout>(R.id.infoButton)
        mailImageView = rootView!!.findViewById<ImageView>(R.id.emailHelp)
        summaryButton = rootView!!.findViewById<ConstraintLayout>(R.id.summaryButton)
      //  progressBarDialog = ProgressBarDialog(context, R.drawable.spinner)
        registerMusicButton.setOnClickListener(View.OnClickListener { v: View? ->
            val `in` = Intent(
                context,
                ShopRegisterActivity::class.java
            )
            startActivity(`in`)
        })
        infoButton.setOnClickListener(View.OnClickListener { v: View? ->
            val `in` = Intent(
                context,
                ShopInformationActivity::class.java
            )
            startActivity(`in`)
        })
        summaryButton.setOnClickListener(View.OnClickListener { v: View? ->
            val `in` = Intent(
                context,
                RegisteredShopSummaryActivity::class.java
            )
            startActivity(`in`)
        })
        mailImageView.setOnClickListener(View.OnClickListener {
            if (PreferenceManager.getUserCode(mContext).equals("")) {
               /* AppUtils.showDialogAlertDismiss(
                    context as Activity?,
                    context!!.getString(R.string.alert_heading),
                    context!!.getString(R.string.avail_for_registered),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )*/
            }
            else {
                val dialog = Dialog(requireContext())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.alert_send_email_dialog)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val dialogCancelButton = dialog.findViewById<View>(R.id.cancelButton) as Button
                val submitButton = dialog.findViewById<View>(R.id.submitButton) as Button
                text_dialog = dialog.findViewById<View>(R.id.text_dialog) as EditText
                text_content = dialog.findViewById<View>(R.id.text_content) as EditText
              //  progressDialog = dialog.findViewById<View>(R.id.progressdialogg) as ProgressBar
                dialogCancelButton.setOnClickListener { dialog.dismiss() }
                submitButton.setOnClickListener {
                    if (text_dialog!!.text.toString().trim { it <= ' ' } == "") {
                        /*val toast = Toast.makeText(
                            context, requireContext().resources.getString(
                                R.string.enter_subjects
                            ), Toast.LENGTH_SHORT
                        )*/
                       // toast.show()
                    } else {
                        if (text_content!!.text.toString().trim { it <= ' ' } == "") {
                          /*  val toast = Toast.makeText(
                                context, requireContext().resources.getString(
                                    R.string.enter_contents
                                ), Toast.LENGTH_SHORT
                            )*/
                           // toast.show()
                        } else if (contactEmail.matches(EMAIL_PATTERN.toRegex())) {
                            if (text_dialog!!.text.toString().trim { it <= ' ' }
                                    .matches(pattern.toRegex())) {
                                if (text_content!!.text.toString().trim { it <= ' ' }
                                        .matches(pattern.toRegex())) {
                                    /*if (AppUtils.isNetworkConnected(context)) {
                                        sendEmailToStaff(dialog)
                                    } else {
                                        AppUtils.showDialogAlertDismiss(
                                            context as Activity?,
                                            context!!.getString(R.string.network_error),
                                            getString(R.string.no_internet),
                                            R.drawable.nonetworkicon,
                                            R.drawable.roundred
                                        )
                                    }*/
                                } else {
                                    /*val toast = Toast.makeText(
                                        context, context!!.resources.getString(
                                            R.string.enter_valid_contents
                                        ), Toast.LENGTH_SHORT
                                    )
                                    toast.show()*/
                                }
                            } else {
                                /*val toast = Toast.makeText(
                                    context, context!!.resources.getString(
                                        R.string.enter_valid_subjects
                                    ), Toast.LENGTH_SHORT
                                )
                                toast.show()*/
                            }
                        } else {
                            val toast = Toast.makeText(
                                context, requireContext().resources.getString(
                                    R.string.enter_valid_email
                                ), Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }
                    }
                }
                dialog.show()
            }
        })
    }

    private fun sendEmailToStaff(dialog: Dialog) {
/*val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty("email", contactEmail)
        paramObject.addProperty("title", text_dialog!!.text.toString())
        paramObject.addProperty("message", text_content!!.text.toString())
        val call: Call<SendMailStaffResponseModel> = service.sendmailtostaff(
            "Bearer " + PreferenceManager.getAccessToken(context),
            paramObject
        )
        progressDialog!!.visibility = View.VISIBLE
        call.enqueue(object : Callback<SendMailStaffResponseModel?> {
            override fun onResponse(
                call: Call<SendMailStaffResponseModel?>,
                response: Response<SendMailStaffResponseModel?>
            ) {
                progressDialog!!.visibility = View.GONE
                if (response.isSuccessful()) {
                    val apiResponse: SendMailStaffResponseModel? = response.body()
                    // Log.e("response", String.valueOf(apiResponse));
                    // System.out.println("response" + apiResponse);
                    val statuscode: String = apiResponse.getResponse().getStatuscode()
                    if (statuscode == "303") {
                        dialog.dismiss()
                        val toast = Toast.makeText(
                            context, context!!.resources.getString(
                                R.string.successfully_send_email_staff
                            ), Toast.LENGTH_SHORT
                        )
                        toast.show()
                    } else {
                        val toast = Toast.makeText(
                            context, context!!.resources.getString(
                                R.string.email_not_staff
                            ), Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                }
            }

            override fun onFailure(call: Call<SendMailStaffResponseModel?>, t: Throwable) {
                progressDialog!!.visibility = View.GONE

            }
        })*/

    }
    companion object {
        private var progressDialog: ProgressBar? = null
    }
}
