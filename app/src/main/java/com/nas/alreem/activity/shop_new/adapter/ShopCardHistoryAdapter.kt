package com.nas.alreem.activity.shop_new.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.payment_history.PaymentWalletHistoryModel
import com.nas.alreem.activity.shop_new.model.PaymentShopWalletHistoryModel
import com.nas.alreem.constants.ConstantFunctions


class ShopCardHistoryAdapter(
    private val mContext: Context,
    mnNewsLetterModelArrayList: ArrayList<PaymentShopWalletHistoryModel>
) :
    RecyclerView.Adapter<ShopCardHistoryAdapter.MyViewHolder>() {
    private val mnNewsLetterModelArrayList: ArrayList<PaymentShopWalletHistoryModel>
    var dept: String? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //  ImageView imageIcon;
        var pdfTitle: TextView
        var tripsDateTxt: TextView
        var tripsAmountTxt: TextView
        var mainRelative: RelativeLayout
        var status: TextView
        //  var statusLayout: RelativeLayout

        init {

            //  imageIcon = (ImageView) view.findViewById(R.id.imageIcon);
            pdfTitle = view.findViewById<View>(R.id.pdfTitle) as TextView
            tripsDateTxt = view.findViewById<View>(R.id.tripsDateTxt) as TextView
            tripsAmountTxt = view.findViewById<View>(R.id.tripsAmountTxt) as TextView
            mainRelative = view.findViewById<View>(R.id.mainRelative) as RelativeLayout
            status = view.findViewById<TextView>(R.id.status)
            //   statusLayout = view.findViewById<RelativeLayout>(R.id.statusLayout)
        }
    }

    init {
        this.mnNewsLetterModelArrayList = mnNewsLetterModelArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.payment_wallet_history_recycler_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.pdfTitle.text = "Paid by " + mnNewsLetterModelArrayList[position].student_name
        // holder.imageIcon.setVisibility(View.GONE);
        Log.e("date",mnNewsLetterModelArrayList[position].created_on)
        holder.tripsDateTxt.setText(ConstantFunctions().dateConversionYYY(mnNewsLetterModelArrayList[position].created_on))
        holder.tripsAmountTxt.text =
            "Credit " + mnNewsLetterModelArrayList[position].order_total + " " + "AED"
        /*  if (mnNewsLetterModelArrayList.get(position).getCompleted_date().equalsIgnoreCase("") && mnNewsLetterModelArrayList.get(position).getLast_payment_status().equalsIgnoreCase("0")) {
            holder.mainRelative.setBackgroundColor(mContext.getResources().getColor(R.color.term_button_bg));
        } else if (mnNewsLetterModelArrayList.get(position).getCompleted_date().equalsIgnoreCase("") && mnNewsLetterModelArrayList.get(position).getLast_payment_status().equalsIgnoreCase("1")) {
            holder.mainRelative.setBackgroundColor(mContext.getResources().getColor(R.color.rel_nine));
        } else if (!(mnNewsLetterModelArrayList.get(position).getCompleted_date().equalsIgnoreCase("")) && mnNewsLetterModelArrayList.get(position).getLast_payment_status().equalsIgnoreCase("0")) {
            holder.mainRelative.setBackgroundColor(mContext.getResources().getColor(R.color.trip_green));
        } else if (!(mnNewsLetterModelArrayList.get(position).getCompleted_date().equalsIgnoreCase("")) && mnNewsLetterModelArrayList.get(position).getLast_payment_status().equalsIgnoreCase("1")) {
            holder.mainRelative.setBackgroundColor(mContext.getResources().getColor(R.color.trip_green));
        } else {
            holder.mainRelative.setBackgroundColor(mContext.getResources().getColor(R.color.term_button_bg));
        }*//*if (mnNewsLetterModelArrayList[position].getStatus().equals("1")) {
            holder.status.text = "(ID Card Pending)"
            holder.status.setTextColor(mContext.resources.getColor(R.color.red))
        } else {
            holder.status.text = "(ID Card Approved)"
            holder.status.setTextColor(mContext.resources.getColor(R.color.trip_green))
        }*/
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        Log.e("lostcardsize", mnNewsLetterModelArrayList.size.toString())
        return mnNewsLetterModelArrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}