package com.nas.alreem.activity.shop_new.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.model.order_history.OrderCanteenPreOrderItems
import com.nas.alreem.activity.shop_new.model.ShopModel


class OrderHistoryPreorderDetailsAdapter_new ( val preorderhis_list: ArrayList<ShopModel>, var mcontext: Context) :
    RecyclerView.Adapter<OrderHistoryPreorderDetailsAdapter_new.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_order_history_item_details_new, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemNameTxt.text=preorderhis_list.get(position).item_name
        viewHolder.itemDescription.text=preorderhis_list.get(position).description

        viewHolder.amountTxt.text=preorderhis_list.get(position).price+"AED"
        if (preorderhis_list.get(position).quantity==1)
        {
            viewHolder.itemsCount.text=preorderhis_list.get(position).quantity.toString()+" item"
        }
        else
        {
            viewHolder.itemsCount.text=preorderhis_list.get(position).quantity.toString()+" items"
        }



    }

    override fun getItemCount(): Int {

        return preorderhis_list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemNameTxt: TextView
        var itemDescription: TextView
        var amountTxt: TextView
        var itemsCount: TextView
        var status: TextView
        var orderbadge: TextView
        init {
            itemNameTxt = itemView.findViewById(R.id.itemNameTxt)
            itemDescription = itemView.findViewById(R.id.itemDescription)
            amountTxt = itemView.findViewById(R.id.amountTxt)
            itemsCount = itemView.findViewById(R.id.itemsCount)
            status = itemView.findViewById(R.id.status)
            orderbadge=itemView.findViewById(R.id.orderbadge)
        }
    }

}