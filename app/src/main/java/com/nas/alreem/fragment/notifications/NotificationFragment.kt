package com.nas.alreem.fragment.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.notifications.AudioPlayerDetailNew
import com.nas.alreem.activity.notifications.ImageMessageActivity
import com.nas.alreem.activity.notifications.TextMessageActivity
import com.nas.alreem.activity.notifications.VideoMessageActivity
import com.nas.alreem.activity.notifications.VideoMessageActivityNew
import com.nas.alreem.activity.shop_new.model.StudentShopCardResponseModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.notifications.adapter.NotificationListAdapter
import com.nas.alreem.fragment.notifications.model.NotificationApiModel
import com.nas.alreem.fragment.notifications.model.NotificationModel
import com.nas.alreem.fragment.notifications.model.NotificationResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : Fragment() {

    lateinit var mContext: Context
    lateinit var notificationRecycler: RecyclerView
    lateinit var titleTextView: TextView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var notificationList:ArrayList<NotificationModel>
    lateinit var notificationAdapter:NotificationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callNotificationApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }
    private fun initializeUI()
    {
        notificationRecycler=requireView().findViewById(R.id.notificationRecycler)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        titleTextView.text=ConstantWords.notification
        notificationRecycler.visibility=View.GONE
        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        var linearLayoutManager = LinearLayoutManager(mContext)
        notificationRecycler.layoutManager = linearLayoutManager
        notificationRecycler.itemAnimator = DefaultItemAnimator()

        notificationRecycler.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                if (notificationList.get(position).read_unread_status
                        .equals("0") || notificationList.get(position).read_unread_status
                        .equals("2")
                ) {
                    callStatusChangeApi(
                        notificationList.get(position).id,position,
                        notificationList.get(position).read_unread_status)
                }
                if(notificationList.get(position).alert_type.equals("Text"))
                {
                    val intent = Intent(activity, TextMessageActivity::class.java)
                    intent.putExtra("id",notificationList.get(position).id)
                    intent.putExtra("title",notificationList.get(position).title)
                    activity?.startActivity(intent)
                }
                else if (notificationList.get(position).alert_type.equals("Video"))
                {
                    val intent = Intent(activity, VideoMessageActivityNew::class.java)
                    intent.putExtra("id",notificationList.get(position).id)
                    intent.putExtra("title",notificationList.get(position).title)
                    activity?.startActivity(intent)
                }
                else if (notificationList.get(position).alert_type.equals("Image"))
                {
                    val intent = Intent(activity, ImageMessageActivity::class.java)
                    intent.putExtra("id",notificationList.get(position).id)
                    intent.putExtra("title",notificationList.get(position).title)
                    activity?.startActivity(intent)
                }
                else if (notificationList.get(position).alert_type.equals("Voice"))
                {
                    val intent = Intent(activity, AudioPlayerDetailNew::class.java)
                    intent.putExtra("audio_title", notificationList[position].title)
                    intent.putExtra("audio_id", notificationList[position].id)
                    intent.putExtra("audio_updated", notificationList[position].created_at)
                    activity?.startActivity(intent)
                }

            }
        })
    }

    private fun callStatusChangeApi(ccaDaysId: String,eventposition:Int, status: String) {
        progressDialogAdd.visibility=View.VISIBLE
      //  notificationList= ArrayList()
        var token="Bearer "+PreferenceManager.getaccesstoken(mContext)
        var model=NotificationApiModel(0,500)
        Log.e("id",ccaDaysId)
        val paramObject = JsonObject()
        paramObject.addProperty("id", ccaDaysId)
        paramObject.addProperty("type", "notification")
        val call: Call<StudentShopCardResponseModel> = ApiClient(mContext).getClient.status_changeAPI(token,paramObject)
        call.enqueue(object : Callback<StudentShopCardResponseModel> {
            override fun onFailure(call: Call<StudentShopCardResponseModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<StudentShopCardResponseModel>, response: Response<StudentShopCardResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {


                        if (status.equals("0", ignoreCase = true) || status.equals(
                                "2",
                                ignoreCase = true
                            )
                        ) {
                            notificationList.get(eventposition).read_unread_status="1"
                            notificationAdapter.notifyDataSetChanged()
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }

    private fun callNotificationApi()
    {
        progressDialogAdd.visibility=View.VISIBLE
        notificationList= ArrayList()
        var token="Bearer "+PreferenceManager.getaccesstoken(mContext)
        var model=NotificationApiModel(0,500)
        val call: Call<NotificationResponseModel> = ApiClient(mContext).getClient.notificationList(model,token)
        call.enqueue(object : Callback<NotificationResponseModel> {
            override fun onFailure(call: Call<NotificationResponseModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<NotificationResponseModel>, response: Response<NotificationResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {

                            notificationList= response.body()!!.responseArray!!.notifications!!
                            if (notificationList.size>0)
                            {
                                notificationRecycler.visibility=View.VISIBLE
                                 notificationAdapter= NotificationListAdapter(notificationList)
                                notificationRecycler.adapter=notificationAdapter
                            }
                            else
                            {
                                notificationRecycler.visibility=View.GONE
                                DialogFunctions.commonErrorAlertDialog(resources.getString(R.string.alert),ConstantWords.no_notification_found,mContext)
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