package com.nas.alreem.activity.parentsessential

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.parentsessential.adapter.ParentsEssentialSubMenuAdapter
import com.nas.alreem.activity.payments.adapter.PayCategoryListAdapter
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.*
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.parents_essentials.model.ParentsEssentialSubMenuModel
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParentsEssentialDetail : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var heading: TextView
    lateinit var descriptionTV: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var bannerImagePager: ImageView
    lateinit var mailImageView: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var mListView: RecyclerView
    lateinit var contact_email:String
    lateinit var banner_image:String
    lateinit var description:String
    lateinit var tab_name:String
    lateinit var subMenuArray:ArrayList<ParentsEssentialSubMenuModel>
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
    private val pattern = "^([a-zA-Z ]*)$"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parents_essential_detail)
        init()
    }

    private fun init(){
        mContext=this
        contact_email=intent.getStringExtra("contact_email").toString()
        banner_image=intent.getStringExtra("banner_image").toString()
        description=intent.getStringExtra("description").toString()
        tab_name=intent.getStringExtra("tab_name").toString()
        subMenuArray= intent.getSerializableExtra("submenu") as ArrayList<ParentsEssentialSubMenuModel>
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        bannerImagePager=findViewById(R.id.bannerImagePager)
        mailImageView=findViewById(R.id.mailImageView)
        descriptionTV=findViewById(R.id.descriptionTV)
        mListView=findViewById(R.id.mListView)
        heading.text=tab_name
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })

        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        if (banner_image.equals(""))
        {
            Glide.with(mContext)
                .load(R.drawable.default_banner)
                .into(bannerImagePager)
        }
        else{
            Glide.with(mContext) //1
                .load(banner_image)
                .into(bannerImagePager)
        }

        if (contact_email.equals(""))
        {
            mailImageView.visibility=View.GONE
        }
        else{
            mailImageView.visibility=View.VISIBLE
        }

        mailImageView.setOnClickListener(View.OnClickListener {
            showSendEmailDialog()
        })

        if (description.equals(""))
        {
            descriptionTV.visibility=View.GONE
        }
        else{
            descriptionTV.visibility=View.VISIBLE
            descriptionTV.text=description
        }



        var linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        mListView.itemAnimator = DefaultItemAnimator()


        if(subMenuArray.size>0)
        {
            var subAdapter= ParentsEssentialSubMenuAdapter(subMenuArray,mContext)
            mListView.adapter=subAdapter
        }
        else
        {
            ConstantFunctions.showDialogueWithOk(
                mContext,
                "No Data Found!",
                "Alert"
            )
        }


        mListView.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {

                if (subMenuArray.get(position).filename.contains(".pdf"))
                {
                    val intent = Intent(mContext, PDFViewerActivity::class.java)
                    intent.putExtra("Url",subMenuArray.get(position).filename)
                    intent.putExtra("title",subMenuArray.get(position).submenu)
                    startActivity(intent)
                }
                else{
                    if(subMenuArray.get(position).filename.contains("chat.whatsapp.com"))
                        {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(subMenuArray.get(position).filename))

                                // Set the package name to explicitly open in WhatsApp
                               // intent.setPackage("com.whatsapp")

                                // Check if WhatsApp is installed on the device
                               // if (intent.resolveActivity(packageManager) != null) {
                                    startActivity(intent)

                           // }
                        }
                    else{
                        val intent = Intent(mContext, WebLinkActivity::class.java)
                        intent.putExtra("url",subMenuArray.get(position).filename)
                        intent.putExtra("heading",subMenuArray.get(position).submenu)
                        startActivity(intent)
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

                } else if (contact_email.matches(EMAIL_PATTERN.toRegex())) {
                    if (text_dialog.text.toString().trim ().matches(pattern.toRegex())) {
                        if (text_content.text.toString().trim ()
                                .matches(pattern.toRegex())) {

                            if (ConstantFunctions.internetCheck(mContext))
                            {
                                sendEmail(text_dialog.text.toString().trim(), text_content.text.toString().trim(), contact_email, dialog)

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