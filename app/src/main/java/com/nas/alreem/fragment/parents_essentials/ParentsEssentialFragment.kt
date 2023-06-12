package com.nas.alreem.fragment.parents_essentials

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
import com.nas.alreem.activity.early_years.ComingUpDetailActivity
import com.nas.alreem.activity.early_years.EarlyYearsComingUpActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.parentsessential.ParentsEssentialDetail
import com.nas.alreem.activity.primary.PrimaryActivity
import com.nas.alreem.activity.primary.PrimaryComingUpActivity
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.parents_essentials.adapter.ParentsEssentialListAdapter
import com.nas.alreem.fragment.parents_essentials.model.ParentsEssentialDataModel
import com.nas.alreem.fragment.parents_essentials.model.ParentsEssentialResponseModel
import com.nas.alreem.fragment.payments.model.PaymentResponseModel
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.fragment.primary.adapter.PrimaryAdapter
import com.nas.alreem.fragment.primary.model.PrimaryDataModel
import com.nas.alreem.fragment.primary.model.PrimaryResponseModel
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParentsEssentialFragment : Fragment() {

    lateinit var mContext: Context
    lateinit var bannerImagePager:ImageView
    lateinit var sendEmail:ImageView
    lateinit var descriptionTitle:TextView
    lateinit var descriptionTV:TextView
    lateinit var title:RelativeLayout
    lateinit var mListView:RecyclerView
    lateinit var dataArray:ArrayList<ParentsEssentialDataModel>
    lateinit var contact_email:String
    lateinit var titleTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parents_essentials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
        callParentsEssentialApi()
    }
    private fun initializeUI()
    {

        bannerImagePager=requireView().findViewById(R.id.bannerImagePager)
        sendEmail=requireView().findViewById(R.id.sendEmail)
        descriptionTV=requireView().findViewById(R.id.descriptionTV)
        mListView=requireView().findViewById(R.id.mListView)
        title=requireView().findViewById(R.id.title)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        titleTextView.text="Parents Essentials"

        var linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        mListView.itemAnimator = DefaultItemAnimator()

        mListView.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {

                val intent = Intent(mContext, ParentsEssentialDetail::class.java)
                intent.putExtra("tab_name",dataArray.get(position).name)
                intent.putExtra("banner_image",dataArray.get(position).banner_image)
                intent.putExtra("contact_email",dataArray.get(position).contact_email)
                intent.putExtra("description",dataArray.get(position).description)
                intent.putExtra("submenu",dataArray.get(position).submenu)
                startActivity(intent)
            }


        })

        sendEmail.setOnClickListener(View.OnClickListener {
            showSendEmailDialog()
        })
    }

    fun callParentsEssentialApi()
    {
        dataArray= ArrayList()
        val call: Call<ParentsEssentialResponseModel> = ApiClient.getClient.parentsEssential("Bearer "+PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<ParentsEssentialResponseModel> {
            override fun onFailure(call: Call<ParentsEssentialResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
            }
            override fun onResponse(call: Call<ParentsEssentialResponseModel>, response: Response<ParentsEssentialResponseModel>) {
                val responsedata = response.body()
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            var bannerImage=response.body()!!.responseArray!!.parentEssentialData.banner_image
                            contact_email=response.body()!!.responseArray!!.parentEssentialData.contact_email
                            var description=response.body()!!.responseArray!!.parentEssentialData.description

                            if (description.equals(""))
                            {
                                descriptionTV.visibility=View.GONE
                            }
                            else
                            {
                                descriptionTV.visibility=View.VISIBLE
                                descriptionTV.text=description
                            }

                            if (contact_email.equals(""))
                            {
                                sendEmail.visibility=View.GONE
                                title.visibility=View.GONE
                            }
                            else{
                                sendEmail.visibility=View.VISIBLE
                                title.visibility=View.VISIBLE
                            }
                            if (bannerImage.isNotEmpty()) {
                                Log.e("bann","notemp")
                                Glide.with(mContext) //1
                                    .load(bannerImage)
                                    .into(bannerImagePager)
                            } else {
                                Log.e("bann","emp")
                                Glide.with(mContext)
                                    .load(R.drawable.default_banner)
                                    .into(bannerImagePager)
                            }

                            dataArray=response.body()!!.responseArray!!.data!!
                            if (dataArray.size>0)
                            {
                                var pAdapter= ParentsEssentialListAdapter(dataArray,mContext)
                                mListView.adapter=pAdapter
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

                    sendEmail(text_dialog.text.toString().trim(), text_content.text.toString().trim(), contact_email, dialog)
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