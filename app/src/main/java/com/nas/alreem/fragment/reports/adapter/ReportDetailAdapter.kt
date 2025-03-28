package com.nas.alreem.fragment.reports.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.shop_new.model.StudentShopCardResponseModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.fragment.notifications.model.NotificationApiModel
import com.nas.alreem.fragment.reports.model.ReportListDetailModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ReportDetailAdapter(private var mContext:Context, private var repoetDetailArray: ArrayList<ReportListDetailModel>) :
    RecyclerView.Adapter<ReportDetailAdapter.MyViewHolder>() {
    lateinit var clickedurl:String

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var report_cycle: TextView = view.findViewById(R.id.termname)
        var relativeclick: LinearLayout = view.findViewById(R.id.clickLinear)
        var statusLayout : RelativeLayout = view.findViewById(R.id.statusLayout)
        var status : TextView = view.findViewById(R.id.status)



    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_report_cycle, parent, false)
        return MyViewHolder(itemView)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val summary = repoetDetailArray[position]
        holder.report_cycle.text = repoetDetailArray[position].report_cycle
        if (repoetDetailArray.get(position).read_unread_status.equals("0")) {
            holder.statusLayout.setVisibility(View.VISIBLE)
            holder.status.setBackgroundResource(R.drawable.rectangle_red)
            holder.status.setText("New")
        } else if (repoetDetailArray.get(position).read_unread_status.equals("2")) {
            holder.statusLayout.setVisibility(View.VISIBLE)
            holder.status.setBackgroundResource(R.drawable.rectangle_orange)
            holder.status.setText("Updated")
        } else if (repoetDetailArray.get(position).read_unread_status.equals("")) {
            holder.statusLayout.setVisibility(View.GONE)
        } else if (repoetDetailArray.get(position).read_unread_status.equals("1")) {
            holder.statusLayout.setVisibility(View.GONE)
        }
        holder.relativeclick.setOnClickListener {

            clickedurl = repoetDetailArray[position].file

            if (repoetDetailArray.get(position).read_unread_status.equals("0") || repoetDetailArray.get(
                    position
                ).read_unread_status.equals("2")
            ) {
                callStatusChangeApi(
                    repoetDetailArray.get(position).id,
                            position,
                    repoetDetailArray.get(position).read_unread_status
                )
            }
            else
            {
                if (repoetDetailArray.get(position).file.endsWith(".pdf")) {
                    val intent = Intent(mContext, PDFViewerActivity::class.java)
                    intent.putExtra("Url", repoetDetailArray.get(position).file)
                    intent.putExtra("title", repoetDetailArray.get(position).report_cycle)

                    mContext.startActivity(intent)
                } else {
                    val intent = Intent(mContext, WebLinkActivity::class.java)
                    intent.putExtra("url", repoetDetailArray.get(position).file)
                    mContext.startActivity(intent)
                }
            }
          // mContext.startActivity(Intent(mContext, WebviewLoad::class.java).putExtra("Url",repoetDetailArray[position].file))


        }


    }

    private fun callStatusChangeApi(ccaDaysId: Int,event_position:Int, status: String) {

        var token="Bearer "+ PreferenceManager.getaccesstoken(mContext)
        var model= NotificationApiModel(0,500)
        val paramObject = JsonObject()
        paramObject.addProperty("id", ccaDaysId)
        paramObject.addProperty("type", "report")
        val call: Call<StudentShopCardResponseModel> = ApiClient(mContext).getClient.status_changeAPI(token,paramObject)
        call.enqueue(object : Callback<StudentShopCardResponseModel> {
            override fun onFailure(call: Call<StudentShopCardResponseModel>, t: Throwable) {

            }
            override fun onResponse(call: Call<StudentShopCardResponseModel>, response: Response<StudentShopCardResponseModel>) {
                val responsedata = response.body()

                if (responsedata != null) {
                    try {


                        if (status.equals("0", ignoreCase = true) || status.equals(
                                "2",
                                ignoreCase = true
                            )
                        ) {
                            repoetDetailArray.get(event_position).read_unread_status="1"
                            notifyDataSetChanged()
                            if (repoetDetailArray.get(event_position).file.endsWith(".pdf")) {
                                val intent = Intent(mContext, PDFViewerActivity::class.java)
                                intent.putExtra("Url", repoetDetailArray.get(event_position).file)
                                intent.putExtra("title", repoetDetailArray.get(event_position).report_cycle)

                                mContext.startActivity(intent)
                            } else {
                                val intent = Intent(mContext, WebLinkActivity::class.java)
                                intent.putExtra("url", repoetDetailArray.get(event_position).file)
                                mContext.startActivity(intent)
                            }
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
    override fun getItemCount(): Int {

        return repoetDetailArray.size

    }
}