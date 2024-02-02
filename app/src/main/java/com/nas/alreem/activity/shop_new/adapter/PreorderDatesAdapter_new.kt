package com.nas.alreem.activity.shop_new.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.shop_new.model.PaymentShopWalletHistoryModel
import com.nas.alreem.activity.shop_new.model.ShopItemHistoryModel
import com.nas.alreem.activity.shop_new.model.ShopModel
import com.nas.alreem.constants.ConstantFunctions


class PreorderDatesAdapter_new(
    var mcontext: Context,
    var order_summery: ArrayList<ShopItemHistoryModel>
) :
    RecyclerView.Adapter<PreorderDatesAdapter_new.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.orderhistory_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        viewHolder.date.text= ConstantFunctions.dateParsingToddMMMyyyyBasket(order_summery.get(position).order_date)
        viewHolder.recyclerview.layoutManager = LinearLayoutManager(mcontext)
        var adapter = OrderHistoryPreorderDetailsAdapter_new(order_summery, mcontext)
        viewHolder.recyclerview.adapter=adapter

    }

    override fun getItemCount(): Int {
        Log.e("size", order_summery.size.toString())
        return order_summery.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var date: TextView
        var recyclerview: RecyclerView
        lateinit var itemlist:ArrayList<String>
        init {
            date = itemView.findViewById(R.id.date)
            recyclerview=itemView.findViewById(R.id.dates_rec)
            itemlist= ArrayList()
        }
    }

}