package com.nas.alreem.fragment.about_us

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.about_us.AccreditationsActivity
import com.nas.alreem.activity.about_us.FacilityActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.staff_directory.StaffDirectoryActivity
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.about_us.adapter.AboutUsAdapter
import com.nas.alreem.fragment.about_us.model.AboutUsDataModel
import com.nas.alreem.fragment.about_us.model.AboutUsResponseModel
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutUsFragment  : Fragment() {

    lateinit var mContext: Context
    lateinit var aboutUsRecycler: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var titleTextView: TextView
    lateinit var aboutUsArrayList:ArrayList<AboutUsDataModel>
    lateinit var staff_rel:RelativeLayout
    lateinit var bannerImagePager: ImageView
    lateinit var contactEmail: String
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
    private val pattern = "^([a-zA-Z ]*)$"
    private var description = ""
   lateinit var descriptionTV: TextView
    lateinit var  mtitleRel: RelativeLayout
    lateinit var  sendEmail: ImageView
    var weburlString: String? = null
    lateinit var weburl: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callAboutUsApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }
    private fun initializeUI()
    {
        aboutUsRecycler=requireView().findViewById(R.id.aboutUsRecycler)
        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        bannerImagePager = requireView().findViewById<View>(R.id.bannerImagePager) as ImageView
        descriptionTV = requireView()!!.findViewById<View>(R.id.description) as TextView
        mtitleRel = requireView().findViewById<View>(R.id.title) as RelativeLayout
        sendEmail = requireView().findViewById<View>(R.id.sendEmail) as ImageView
        weburl = requireView().findViewById<View>(R.id.weburl) as TextView
        var linearLayoutManager = LinearLayoutManager(mContext)
        aboutUsRecycler.layoutManager = linearLayoutManager
        aboutUsRecycler.itemAnimator = DefaultItemAnimator()
        titleTextView.text=ConstantWords.about_us
        staff_rel=requireView().findViewById(R.id.relSub)
        staff_rel.setOnClickListener {
            val intent =Intent(activity, StaffDirectoryActivity::class.java)
            activity?.startActivity(intent)
        }

        //		mAboutUsList.setAdapter(new CustomAboutUsAdapter(getActivity(), mAboutUsListArray));
        //mAboutUsList.setOnItemClickListener(this);
        sendEmail!!.setOnClickListener {
            showSendEmailDialog(mContext!!)
        }
        weburl.setOnClickListener {
            val intent = Intent(mContext,
                WebLinkActivity::class.java
            )
            intent.putExtra("url", weburlString)
            intent.putExtra("heading", "ABOUT_US")
            mContext.startActivity(intent)
        }
        aboutUsRecycler.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {

//
//                    val intent = Intent(mContext, WebLinkActivity::class.java)
//                    intent.putExtra("url",aboutUsArrayList.get(position).url)
//                    intent.putExtra("heading",aboutUsArrayList.get(position).tab_type)
//                    startActivity(intent)

                 if (aboutUsArrayList.get(position).tab_type.equals("Facilities")) {
                val mIntent = Intent(activity, FacilityActivity::class.java)
               /* mIntent.putExtra(
                    "array",
                    aboutUsArrayList.get(position).items
                )*/
                     PreferenceManager.saveAboutsArrayList(mContext,aboutUsArrayList.get(position).items)
                mIntent.putExtra("desc", aboutUsArrayList.get(position).description)
                mIntent.putExtra("title", aboutUsArrayList.get(position).tab_type)
                mIntent.putExtra("banner_image", aboutUsArrayList.get(position).banner_image)
                mContext.startActivity(mIntent)

            } else if (aboutUsArrayList.get(position).tab_type
                    .equals("Accreditations & Examinations")
            ) {
                val mIntent = Intent(mContext, AccreditationsActivity::class.java)
                /*mIntent.putExtra(
                    "array",
                    aboutUsArrayList.get(position).items
                )*/
                     PreferenceManager.saveAboutsArrayList(mContext,aboutUsArrayList.get(position).items)

                     mIntent.putExtra("desc", aboutUsArrayList.get(position).description)
                mIntent.putExtra("title", aboutUsArrayList.get(position).tab_type)
                mIntent.putExtra("banner_image", aboutUsArrayList.get(position).banner_image)
                mContext.startActivity(mIntent)
            }

                else{
                     if (aboutUsArrayList.get(position).url.contains(".pdf"))
                     {
                         val intent = Intent(mContext, PDFViewerActivity::class.java)
                         intent.putExtra("Url",aboutUsArrayList.get(position).url)
                         intent.putExtra("title",aboutUsArrayList.get(position).tab_type)
                         startActivity(intent)
                     }
                     else
                     {
                         val intent = Intent(mContext, WebLinkActivity::class.java)
                         intent.putExtra("url",aboutUsArrayList.get(position).url)
                         intent.putExtra("heading",aboutUsArrayList.get(position).tab_type)
                         startActivity(intent)
                     }

                }
            }


        })

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

                } else if (contactEmail.matches(EMAIL_PATTERN.toRegex())) {
                    if (text_dialog.text.toString().trim ().matches(pattern.toRegex())) {
                        if (text_content.text.toString().trim ()
                                .matches(pattern.toRegex())) {

                            if (ConstantFunctions.internetCheck(mContext))
                            {
                                callSendEmailToStaffApi(text_dialog.text.toString().trim(), text_content.text.toString().trim(),
                                    contactEmail!!, dialog)
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

    fun callSendEmailToStaffApi(
        title: String, message: String, staffEmail: String, dialog: Dialog)
    {
        val sendMailBody = SendEmailApiModel( staffEmail, title, message)
        val call: Call<SignUpResponseModel> = ApiClient(mContext).getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext!!))
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
    private fun callAboutUsApi()
    {
        aboutUsArrayList= ArrayList()
        progressDialogAdd.visibility=View.VISIBLE
        val call: Call<AboutUsResponseModel> = ApiClient(mContext).getClient.aboutUs()
        call.enqueue(object : Callback<AboutUsResponseModel> {
            override fun onFailure(call: Call<AboutUsResponseModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<AboutUsResponseModel>, response: Response<AboutUsResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {

                            val bannerImage: String = response.body()!!.responseArray!!.banner_image
                            contactEmail=response.body()!!.responseArray!!.contact_email
                            description = response.body()!!.responseArray!!.description!!
                            weburlString = response.body()!!.responseArray!!.website_link

                            if (!bannerImage.equals("", ignoreCase = true)) {
                                Glide.with(mContext) //1
                                    .load(bannerImage)
                                    .into(bannerImagePager)



                            } else {
                                bannerImagePager.setBackgroundResource(R.drawable.default_banner)

                            }
                            if (description.equals("", ignoreCase = true) && contactEmail.equals(
                                    "",
                                    ignoreCase = true
                                )
                            ) {
                                mtitleRel.setVisibility(View.GONE)
                            } else {
                                mtitleRel.setVisibility(View.VISIBLE)
                            }
                            if (description.equals("", ignoreCase = true)) {
                                descriptionTV!!.visibility = View.GONE
                                //  descriptionTitle.setVisibility(View.GONE);
                            } else {
                                descriptionTV!!.text = description
                                descriptionTV!!.visibility = View.VISIBLE
                               // mtitleRel!!.visibility = View.VISIBLE
                                // mtitleRel.setVisibility(View.VISIBLE);
                            }

                            if (contactEmail.equals("", ignoreCase = true)) {
                                sendEmail.setVisibility(View.GONE)
                            } else {
                                mtitleRel.visibility = View.VISIBLE
                                sendEmail.setVisibility(View.VISIBLE)
                            }
                            if (weburlString.equals("", ignoreCase = true)) {
                                weburl.setVisibility(View.GONE)
                            } else {
                                weburl!!.text = weburlString
                                weburl.setVisibility(View.VISIBLE)
                            }
                            aboutUsArrayList=response.body()!!.responseArray!!.data
                            if (aboutUsArrayList.size>0)
                            {
                                staff_rel.visibility=View.VISIBLE
                                var adapterAboutUs=AboutUsAdapter(aboutUsArrayList,mContext)
                                aboutUsRecycler.adapter=adapterAboutUs
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
}
