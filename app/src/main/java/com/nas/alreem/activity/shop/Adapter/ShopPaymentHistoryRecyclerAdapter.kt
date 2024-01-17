package com.nas.alreem.activity.shop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.shop.model.MusicPaymentHistoryModel


class ShopPaymentHistoryRecyclerAdapter(
    private val mContext: Context,
    newsLetterModelArrayList: ArrayList<MusicPaymentHistoryModel>
) :
    RecyclerView.Adapter<ShopPaymentHistoryRecyclerAdapter.MyViewHolder>() {
    private val mnNewsLetterModelArrayList: ArrayList<MusicPaymentHistoryModel>

    init {
        mnNewsLetterModelArrayList = newsLetterModelArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_payment_history_recycler_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.pdfTitle.text = "Paid by " + mnNewsLetterModelArrayList[position].getName()
        holder.tripsDateTxt.setText(mnNewsLetterModelArrayList[position].getId())
        holder.tripsAmountTxt.setText(mnNewsLetterModelArrayList[position].getAmount() + " " + "AED")
    }

    override fun getItemCount(): Int {
        return mnNewsLetterModelArrayList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //  ImageView imageIcon;
        var pdfTitle: TextView
        var tripsDateTxt: TextView
        var tripsAmountTxt: TextView
        var mainRelative: RelativeLayout
        var status: TextView
        var statusLayout: RelativeLayout

        init {

            //  imageIcon = (ImageView) view.findViewById(R.id.imageIcon);
            pdfTitle = view.findViewById<View>(R.id.pdfTitle) as TextView
            tripsDateTxt = view.findViewById<View>(R.id.tripsDateTxt) as TextView
            tripsAmountTxt = view.findViewById<View>(R.id.tripsAmountTxt) as TextView
            mainRelative = view.findViewById<View>(R.id.mainRelative) as RelativeLayout
            status = view.findViewById<TextView>(R.id.status)
            statusLayout = view.findViewById<RelativeLayout>(R.id.statusLayout)
        }
    }
}
