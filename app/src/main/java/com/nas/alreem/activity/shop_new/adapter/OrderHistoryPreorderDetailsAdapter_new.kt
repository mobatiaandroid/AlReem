package com.nas.alreem.activity.shop_new.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.shop_new.model.ShopItemHistoryModel
import com.nas.alreem.constants.ConstantFunctions


class OrderHistoryPreorderDetailsAdapter_new ( val preorderhis_list: ArrayList<ShopItemHistoryModel>, var mcontext: Context) :
    RecyclerView.Adapter<OrderHistoryPreorderDetailsAdapter_new.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_order_history_item_details_new, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemNameTxt.text=preorderhis_list.get(position).item_name
        viewHolder.date_item.text= ConstantFunctions.dateParsingToddMMMyyyyBasket(preorderhis_list.get(position).order_date)
        viewHolder.itemDescription.text=preorderhis_list.get(position).description
        var url:String?=""
        url=preorderhis_list[position].item_image.get(0)
        if (preorderhis_list.get(position).status.equals("1"))
        {
            viewHolder.status.setText("Ordered")
            viewHolder.status.setTextColor(Color.parseColor("#FF5152"));
        }
        else
        {
            viewHolder.status.setText("Delivered")
            viewHolder.status.setTextColor(Color.parseColor("#009933"));

        }
        if (url.equals("")) {
            viewHolder.itemImg.setBackgroundResource(R.drawable.default_banner)

        }
        else
        {
            // holder.itemImg.setBackgroundResource(R.color.)
            mcontext.let {
                Glide.with(it)
                    .load(url)
                    .into(viewHolder.itemImg)
            }
        }
        viewHolder.location.setOnClickListener {
            if (preorderhis_list.get(position).status.equals("1"))
            {
                showSuccessAlertnew(mcontext,preorderhis_list.get(position).pickup_location,"Your item(s) should be delivered at");

            }
            else
            {
                showSuccessAlertnew(
                    mcontext,
                    preorderhis_list.get(position).pickup_location,
                    "Your item(s) delivered in"
                );
            }
        }
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
        var itemImg : ImageView
        var date_item : TextView
        var location : TextView
        init {
            itemNameTxt = itemView.findViewById(R.id.itemNameTxt)
            itemDescription = itemView.findViewById(R.id.itemDescription)
            amountTxt = itemView.findViewById(R.id.amountTxt)
            itemsCount = itemView.findViewById(R.id.itemsCount)
            status = itemView.findViewById(R.id.status)
            orderbadge=itemView.findViewById(R.id.orderbadge)
            itemImg = itemView.findViewById(R.id.itemImg)
            date_item = itemView.findViewById(R.id.date)
            location = itemView.findViewById(R.id.orderbadge)

        }
    }
    fun showSuccessAlertnew(context: Context, pickupLocation: String, message: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pickup_location_shop_popup)
        // var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        // var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message+" "+pickupLocation
        //   alertHead.text = msgHead
        //   iconImageView.setImageResource(R.drawable.exclamationicon)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
    }
}