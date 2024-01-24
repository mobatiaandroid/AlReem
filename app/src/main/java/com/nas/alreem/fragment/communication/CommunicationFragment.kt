package com.nas.alreem.fragment.communication

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.ProgressBarDialog
import com.nas.alreem.activity.communication.commingup.ComingUpWholeSchool
import com.nas.alreem.activity.communication.information.CommunicationInformationActivity
import com.nas.alreem.activity.communication.newesletters.NewsLetterActivity
import com.nas.alreem.activity.communication.socialmedia.SocialMediaActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.communication.adapter.CommunicationAdapter
import com.nas.alreem.fragment.communication.model.CommunicationBannerResponseModel
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunicationFragment : Fragment() {

    lateinit var mContext: Context
    lateinit var mEarlyYearsRecycler: RecyclerView
    lateinit var titleTextView: TextView
    lateinit var bannerImageView: ImageView
    lateinit var descriptionTextView: TextView
    lateinit var progressDialogAdd: ProgressBarDialog
    lateinit var mCommunicationList: ArrayList<String>
    lateinit var emailIcon: ImageView
    var contactEmail: String = ""
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
    private val pattern = "^([a-zA-Z ]*)$"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_communications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        initializeUI()
        //mListView.setAdapter(new CommunicationsAdapter(getActivity(), mListViewArray));
        if (ConstantFunctions.internetCheck(mContext)) {
            getCommunicationBanner()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun initializeUI() {
        mEarlyYearsRecycler = requireView().findViewById(R.id.mEarlyYearsRecycler)
//        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        progressDialogAdd = ProgressBarDialog(mContext)
        titleTextView = requireView().findViewById(R.id.titleTextView)
        titleTextView.text = ConstantWords.communications
        descriptionTextView = requireView().findViewById(R.id.description)
        bannerImageView = requireView().findViewById(R.id.bannerImagePager)
        emailIcon = requireView().findViewById(R.id.email_icon)
        var linearLayoutManager = LinearLayoutManager(mContext)
        mEarlyYearsRecycler.layoutManager = linearLayoutManager
        mEarlyYearsRecycler.itemAnimator = DefaultItemAnimator()
        mCommunicationList = ArrayList()
        mCommunicationList.add("Coming Up - Whole School ")
        mCommunicationList.add("Information")
        mCommunicationList.add("Newsletters")
        mCommunicationList.add("Social Media")
        var communicationAdapter = CommunicationAdapter(mCommunicationList, mContext)
        mEarlyYearsRecycler.adapter = communicationAdapter

        mEarlyYearsRecycler.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View) {

                if (position == 0) {
                    val intent = Intent(mContext, ComingUpWholeSchool::class.java)
                    startActivity(intent)
                } else if (position == 1) {
                    val intent = Intent(mContext, CommunicationInformationActivity::class.java)
                    startActivity(intent)
                } else if (position == 2) {
                    val intent = Intent(mContext, NewsLetterActivity::class.java)
                    startActivity(intent)
                } else if (position == 3) {
                    val intent = Intent(mContext, SocialMediaActivity::class.java)
                    startActivity(intent)

                }
            }

        })
        emailIcon.setOnClickListener {
            showSendEmailDialog(mContext)
        }


    }

    private fun showSendEmailDialog(mContext: Context) {
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
                    mContext.resources.getString(R.string.alert),
                    resources.getString(R.string.enter_subject),
                    mContext
                )


            } else {
                if (text_content.text.toString().trim().equals("")) {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert),
                        resources.getString(R.string.enter_content),
                        mContext
                    )

                } else if (contactEmail.matches(EMAIL_PATTERN.toRegex())) {
                    if (text_dialog.text.toString().trim().matches(pattern.toRegex())) {
                        if (text_content.text.toString().trim()
                                .matches(pattern.toRegex())
                        ) {

                            if (ConstantFunctions.internetCheck(mContext)) {
                                callSendEmailToStaffApi(
                                    text_dialog.text.toString().trim(),
                                    text_content.text.toString().trim(),
                                    contactEmail,
                                    dialog
                                )
                            } else {
                                DialogFunctions.showInternetAlertDialog(mContext)
                            }

                        } else {
                            val toast: Toast = Toast.makeText(
                                mContext,
                                mContext.resources.getString(R.string.enter_valid_contents),
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }
                    } else {
                        val toast: Toast = Toast.makeText(
                            mContext,
                            mContext.resources
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
                        mContext.resources
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

    fun callSendEmailToStaffApi(
        title: String, message: String, staffEmail: String, dialog: Dialog
    ) {
        progressDialogAdd.show()
        val sendMailBody = SendEmailApiModel(staffEmail, title, message)
        val call: Call<SignUpResponseModel> = ApiClient.getClient.sendEmailStaff(
            sendMailBody,
            "Bearer " + PreferenceManager.getaccesstoken(mContext)
        )
        call.enqueue(object : Callback<SignUpResponseModel> {
            override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
                progressDialogAdd.dismiss()
            }

            override fun onResponse(
                call: Call<SignUpResponseModel>,
                response: Response<SignUpResponseModel>
            ) {
                val responsedata = response.body()
                progressDialogAdd.dismiss()
                if (responsedata != null) {
                    try {


                        if (response.body()!!.status == 100) {
                            dialog.dismiss()
                            showSuccessAlert(
                                mContext,
                                "Email sent Successfully ",
                                "Success",
                                dialog
                            )
                        } else {
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

    private fun getCommunicationBanner() {
        progressDialogAdd.show()
        val service: ApiInterface = ApiClient.getClient
        val call: Call<CommunicationBannerResponseModel> = service.communication_banner_images(
            "Bearer " + PreferenceManager.getaccesstoken(mContext)
        )
        call.enqueue(object : Callback<CommunicationBannerResponseModel> {
            override fun onResponse(
                call: Call<CommunicationBannerResponseModel>,
                response: Response<CommunicationBannerResponseModel>
            ) {
                progressDialogAdd.dismiss()
                if (response.isSuccessful) {
                    if (response.body()!!.status == "100") {
                        val bannerImage: String = response.body()!!.responseArray.bannerImage
                        val description: String = response.body()!!.responseArray.description
                        contactEmail = response.body()!!.responseArray.contactEmail
                        if (bannerImage != "") {

                            Glide.with(mContext) //1
                                .load(bannerImage)
                                .into(bannerImageView)
                        } else {
                            bannerImageView.setBackgroundResource(R.drawable.default_banner)
                        }
                        if (description.equals("", ignoreCase = true)) {
                            descriptionTextView.visibility = View.GONE
                        } else {
                            descriptionTextView.visibility = View.VISIBLE
                            descriptionTextView.text = description
                        }
                        if (contactEmail.isEmpty()) {
                            emailIcon.visibility = View.GONE
                        } else {
                            emailIcon.visibility = View.VISIBLE
                        }
                    } else {
                        Toast.makeText(mContext, "Failed to connect to server", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(mContext, "Failed to connect to server", Toast.LENGTH_SHORT)
                        .show()

                }
            }

            override fun onFailure(call: Call<CommunicationBannerResponseModel>, t: Throwable) {
                progressDialogAdd.dismiss()
                Toast.makeText(mContext, "Failed to connect to server", Toast.LENGTH_SHORT).show()
            }
        })
    }
}