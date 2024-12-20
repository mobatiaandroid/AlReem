package com.nas.alreem.fragment.permission_slip.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.permission_slips.FormDetailActivity
import com.nas.alreem.fragment.permission_slip.model.PermissionSlipListModel


internal class FormslistAdapterOld (private var mContext: Context, var formslist:ArrayList<PermissionSlipListModel> ) :
    RecyclerView.Adapter<FormslistAdapterOld.MyViewHolder>() {
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
            if (formslist[position].status==null){
                sts="0"
                Log.e("null","true")
            }
            else{
                sts=formslist[position].status
                Log.e("null",formslist[position].status)
            }
            val intent = Intent(mContext, FormDetailActivity::class.java)
            intent.putExtra("title",formslist[position].title)
            intent.putExtra("description",formslist[position].consent)
            intent.putExtra("status",sts)
            intent.putExtra("slip_id",formslist[position].id)
            mContext.startActivity(intent)
        }

    }
    override fun getItemCount(): Int {

        return formslist.size

    }
}
