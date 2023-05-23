package com.nas.alreem.activity.payments.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.payments.model.PayCatDataList
import com.nas.alreem.activity.payments.model.PaymentCategoryListModel


class PayCategoryListAdapter (var mcontext: Context,val cat_list: ArrayList<PayCatDataList> ) :
    RecyclerView.Adapter<PayCategoryListAdapter.ViewHolder>() {
    lateinit var itemlist:ArrayList<PaymentCategoryListModel>
    var ordered_user_type = ""
    var student_id = ""
    var parent_id = ""
    var staff_id = ""
    var totalAmount = ""
    var WalletAmount = 0
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_paycattitle_list, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemlist= ArrayList()
        holder.title.setText(cat_list[position].title.trim())
        var itemlist=cat_list[position].items
        holder.item_rec.layoutManager=LinearLayoutManager(mcontext)
        var itemAdapter=PaymentItemListAdapter(mcontext,itemlist,cat_list[position].title)
        holder.item_rec.adapter=itemAdapter



    }

    override fun getItemCount(): Int {
        return cat_list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var title:TextView
         var item_rec:RecyclerView


        init {
            title=itemView.findViewById(R.id.title)
            item_rec=itemView.findViewById(R.id.reportRecycler)
        }
    }
}