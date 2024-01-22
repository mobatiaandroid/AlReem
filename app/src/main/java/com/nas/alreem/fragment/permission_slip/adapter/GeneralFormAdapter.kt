package com.nas.alreem.fragment.permission_slip.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.permission_slips.FormDetailActivity
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.fragment.permission_slip.model.PermissionSlipListModel


internal class GeneralFormAdapter (private var mContext: Context, var formslist:ArrayList<PermissionSlipListModel> ) :
    RecyclerView.Adapter<GeneralFormAdapter.MyViewHolder>() {
    var sts:String=""
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemName: TextView = view.findViewById(R.id.itemName)
        var linear: LinearLayout =view.findViewById(R.id.linear)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_communication_recycler, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemName.text = formslist[position].title

        holder.linear.setOnClickListener {

            if (formslist.get(position).form_url.contains(".pdf"))
            {
                val intent = Intent(mContext, PDFViewerActivity::class.java)
                intent.putExtra("Url",formslist.get(position).form_url)
                intent.putExtra("title",formslist.get(position).title)
                mContext.startActivity(intent)
            }
            else{
                val intent = Intent(mContext, WebLinkActivity::class.java)
                intent.putExtra("url",formslist.get(position).form_url)
                intent.putExtra("heading",formslist.get(position).title)
                mContext.startActivity(intent)
            }

        }

    }
    override fun getItemCount(): Int {

        return formslist.size

    }
}