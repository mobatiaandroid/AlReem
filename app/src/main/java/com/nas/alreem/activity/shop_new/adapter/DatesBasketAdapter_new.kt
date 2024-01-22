package com.nas.alreem.activity.shop_new.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.lost_card.model.ShopCartResModel
import com.nas.alreem.constants.ConstantFunctions


class DatesBasketAdapter_new(
    var cartdate_list: ArrayList<ShopCartResModel>, var nContext: Context
    , var ordered_user_type: String, var student_id: String, var parent_id: String,
    var staff_id: String, var itemtxt: TextView, var amnttxt: TextView,
    var itemLinear: LinearLayout, var noItemTxt: ImageView,
    var dateRec: RecyclerView, var progress: ProgressBar,
    var cartTotalAmount: Int
) :
    RecyclerView.Adapter<DatesBasketAdapter_new.ViewHolder>() {
   // lateinit var itemslist:ArrayList<ShopCartResModel>
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.itemsbasket_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       // itemslist=ArrayList()
       // itemslist=cartdate_list[position]
        var deliverydate:String=""

        holder.closeImg.visibility = View.GONE
       holder.cartItemRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(nContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        val spacing = 5 // 50px
Log.e("TotalAmout", cartTotalAmount.toString())

        holder.totalAmountTxt.text = "Total      " + cartTotalAmount+ "AED"
       // holder.itemDateTxt.text = ConstantFunctions.dateParsingToddMMMyyyyBasket(cartdate_list[position].delivery_date)
        holder.closeImg.setOnClickListener(View.OnClickListener { })
        holder.cartItemRecycler.layoutManager= LinearLayoutManager(nContext)
        Log.e("cartsize", cartdate_list.get(position).item_name.toString())
        var adptr= BasketItemsAdapter_new(cartdate_list,
            nContext, ordered_user_type,
            student_id,
            parent_id,
            staff_id,
            itemtxt,amnttxt,itemLinear,noItemTxt,dateRec,progress)
        holder.cartItemRecycler.adapter=adptr


    }
    override fun getItemCount(): Int {
        return cartdate_list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemDateTxt: TextView
        var totalAmountTxt: TextView
        var closeImg: ImageView
        var cartItemRecycler: RecyclerView


        init {
            cartItemRecycler = itemView.findViewById<RecyclerView>(R.id.cartItemRecycler)
            itemDateTxt = itemView.findViewById<TextView>(R.id.itemDateTxt)
            totalAmountTxt = itemView.findViewById<TextView>(R.id.totalAmountTxt)
            closeImg = itemView.findViewById<ImageView>(R.id.imgClose)
        }
    }

}