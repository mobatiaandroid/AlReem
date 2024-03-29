package com.nas.alreem.activity.canteen.adapter

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
import com.nas.alreem.activity.canteen.model.order_history.OrderHistoryDataModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords


class PreorderDatesAdapter(val preorderhis_list: ArrayList<OrderHistoryDataModel>, var mcontext: Context) :
    RecyclerView.Adapter<PreorderDatesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.orderhistory_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        viewHolder.date.text=ConstantFunctions.dateParsingToddMMMyyyyBasket(preorderhis_list.get(position).delivery_date)
        viewHolder.recyclerview.layoutManager = LinearLayoutManager(mcontext)
        var adapter = OrderHistoryPreorderDetailsAdapter(preorderhis_list.get(position).type_status,preorderhis_list.get(position).canteen_preordered_items, mcontext)
        viewHolder.recyclerview.adapter=adapter

    }

    override fun getItemCount(): Int {
        return preorderhis_list.size
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