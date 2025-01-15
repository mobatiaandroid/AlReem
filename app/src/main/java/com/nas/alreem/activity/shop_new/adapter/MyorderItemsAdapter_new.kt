package com.nas.alreem.activity.shop_new.adapter

import android.content.Context
import android.os.Build
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
import androidx.viewpager.widget.ViewPager
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.adapter.MyorderDatesAdapter
import com.nas.alreem.activity.canteen.model.myorders.CancelCanteenPreorderItemId
import com.nas.alreem.activity.canteen.model.myorders.PreOrdersModel
import com.nas.alreem.activity.canteen.model.myorders.Preorderitems_list
import com.nas.alreem.activity.canteen.model.myorders.UpdateCanteenPreorderItemApiModel
import com.nas.alreem.activity.canteen.model.order_history.OrderHistoryApiModel
import com.nas.alreem.activity.canteen.model.preorder.CanteenPreorderModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class MyorderItemsAdapter_new (val itemlist: ArrayList<Preorderitems_list>, var mcontext: Context,
                           var ordered_user_type: String, var student_id:String,
                           var parent_id: String, var staff_id:String, var totalAmount:String,
                           var  WalletAmount:Int, var dateRecyclerView: RecyclerView, var bottom: LinearLayout,
                           var item: LinearLayout, var noitem: ImageView, var progress: ProgressBar, var amountTxt: TextView
) :
    RecyclerView.Adapter<MyorderItemsAdapter_new.ViewHolder>() {

    var quantity = ""
    var canteen_cart_id = ""
    var homeBannerUrlImageArray: ArrayList<String>? = null
    var currentPage = 0
    var BalanceWalletAmount = 0f
    var BalanceConfirmWalletAmount = 0f
    var CartTotalAmount = 0f
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.myorder_items_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bannerImagePager.visibility = View.GONE
        holder.itemNameTxt.text = itemlist[position].item_name
        holder.itemDescription.text = itemlist[position].item_description
        holder.amountExceedTxt.text = itemlist[position].price.toString() + " AED "
        holder.cartitemcount.number = itemlist[position].quantity
//        holder.cartitemcount.setRange(
//            0,
//            Integer.valueOf(itemlist[position].available_quantity)
//        )
        if (itemlist[position].cancellation_time_exceed==0
        ) {
            if (itemlist[position].quantity.equals("1")) {
                holder.itemsCount.text = itemlist[position].quantity.toString() + " item"
            } else {
                holder.itemsCount.text = itemlist[position].quantity.toString() + " items"
            }
            if (itemlist[position].item_status.equals("0"))
            {
                holder.statusExceed.text = "Cancelled"
                holder.statusExceed.setTextColor(mcontext.resources.getColor(R.color.red))
                holder.itemsCount.visibility = View.VISIBLE
                holder.multiLinear.visibility = View.GONE
                holder.linearlayout.setBackgroundColor(
                    mcontext.resources.getColor(R.color.light_grey)
                )
            }
            else if (itemlist[position].item_status
                    .equals("1")
            ) {
                holder.statusExceed.text = "Active"
                holder.statusExceed.setTextColor(
                    mcontext.resources.getColor(R.color.orange_circle)
                )
                holder.itemsCount.visibility = View.GONE
                holder.multiLinear.visibility = View.VISIBLE
                holder.linearlayout.setBackgroundColor(
                    mcontext.resources.getColor(R.color.canteen_item_bg)
                )
            } else {
                holder.statusExceed.text = "Delivered"
                holder.statusExceed.setTextColor(mcontext.resources.getColor(R.color.green))
                holder.itemsCount.visibility = View.VISIBLE
                holder.multiLinear.visibility = View.GONE
                holder.linearlayout.setBackgroundColor(
                    mcontext.resources.getColor(R.color.canteen_item_bg)
                )
            }
        } else {
            holder.itemsCount.visibility = View.VISIBLE
            holder.multiLinear.visibility = View.GONE
            if (itemlist[position].quantity.equals("1")) {
                holder.itemsCount.text = itemlist[position].quantity.toString() + " Item"
            } else {
                holder.itemsCount.text = itemlist[position].quantity.toString() + " Items"
            }
            if (itemlist[position].item_status.equals("0")) {
                holder.statusExceed.text = "Cancelled"
                holder.statusExceed.setTextColor(mcontext.resources.getColor(R.color.red))
            } else if (itemlist[position].item_status
                    .equals("1")
            ) {
                holder.statusExceed.text = "Active"
                holder.statusExceed.setTextColor(
                    mcontext.resources.getColor(R.color.orange_circle)
                )
            } else {
                holder.statusExceed.text = "Delivered"
                holder.statusExceed.setTextColor(mcontext.resources.getColor(R.color.green))
            }
        }


//        if (itemlist[position].portal.equals("2")) {
//            holder.portalTxt.visibility = View.VISIBLE
//        } else {
//            holder.portalTxt.visibility = View.GONE
//        }
        holder.cartitemcount.setOnValueChangeListener { view, oldValue, newValue ->
            canteen_cart_id = itemlist[position].id
            quantity = newValue.toString()
            if (newValue != 0) {

                // Quantity Change
                UpdatePreOrderQuantity(canteen_cart_id,newValue.toString())
                //getCartUpdate(URL_CANTEEN_CONFIRMED_ORDER_EDIT,canteen_cart_id,quantity);
                //  getAddToCart(URL_CANTEEN_ADD_TO_CART,position,String.valueOf(newValue));
                //cart update
            }
            else {

                // PreOrder Item Cancel

                callCancelPreOrderItem(canteen_cart_id)
                //getCartCancel(URL_CANTEEN_CONFIRMED_ORDER_ITEM_CELL_CANCEL,canteen_cart_id);
                //cart cancel
            }
        }



    }
    override fun getItemCount(): Int {
        return itemlist.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemNameTxt: TextView
        var itemDescription: TextView
        lateinit var amountTxt: TextView
        //        var status:TextView
        var amountExceedTxt: TextView
        var itemsCount: TextView
        var statusExceed: TextView
        var portalTxt: TextView
        var cartitemcount: ElegantNumberButton
        var multiLinear: LinearLayout
        var linearlayout: LinearLayout
        var exceedLinear: LinearLayout
        var bannerImagePager: ViewPager
        init {
            itemNameTxt = itemView.findViewById(R.id.itemNameTxt)
            itemDescription = itemView.findViewById(R.id.itemDescription)
            cartitemcount = itemView.findViewById(R.id.cartitemcount)
            multiLinear = itemView.findViewById(R.id.multiLinear)
//            status = itemView.findViewById(R.id.statusExceed)
            linearlayout = itemView.findViewById(R.id.linearlayout)
            amountExceedTxt = itemView.findViewById(R.id.amountExceedTxt)
            itemsCount = itemView.findViewById(R.id.itemsCount)
            statusExceed = itemView.findViewById(R.id.statusExceed)
            exceedLinear = itemView.findViewById(R.id.exceedLinear)
            portalTxt = itemView.findViewById(R.id.portalTxt)
            bannerImagePager = itemView.findViewById(R.id.bannerImagePager)

        }
    }


    fun callCancelPreOrderItem(order_id:String)
    {
        progress.visibility= View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mcontext)
        var model= CancelCanteenPreorderItemId(student_id,order_id.toString())
        val call: Call<CanteenPreorderModel> = ApiClient(mcontext).getClient.cancelCanteenPreOrderItem(model,"Bearer "+token)
        call.enqueue(object : Callback<CanteenPreorderModel> {
            override fun onFailure(call: Call<CanteenPreorderModel>, t: Throwable) {

                progress.visibility= View.GONE
            }
            override fun onResponse(call: Call<CanteenPreorderModel>, response: Response<CanteenPreorderModel>) {
                val responsedata = response.body()
                progress.visibility= View.GONE
                if (responsedata!!.status==100) {

                    getMyOrderDetails()
                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }


    fun UpdatePreOrderQuantity(orderID:String,quantity:String)
    {
        progress.visibility= View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mcontext)
        var model= UpdateCanteenPreorderItemApiModel(student_id,quantity,orderID)
        val call: Call<CanteenPreorderModel> = ApiClient(mcontext).getClient.updateCanteenPreOrderItem(model,"Bearer "+token)
        call.enqueue(object : Callback<CanteenPreorderModel> {
            override fun onFailure(call: Call<CanteenPreorderModel>, t: Throwable) {

                progress.visibility= View.GONE
            }
            override fun onResponse(call: Call<CanteenPreorderModel>, response: Response<CanteenPreorderModel>) {
                val responsedata = response.body()
                progress.visibility= View.GONE
                if (responsedata!!.status==100) {

                    getMyOrderDetails()
                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }
    private fun getMyOrderDetails(){
        progress.visibility= View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mcontext)
        var model= OrderHistoryApiModel(student_id,"0","100")
        val call: Call<PreOrdersModel> = ApiClient(mcontext).getClient.canteen_myorder_history(model,"Bearer "+token)
        call.enqueue(object : Callback<PreOrdersModel> {
            override fun onFailure(call: Call<PreOrdersModel>, t: Throwable) {

                progress.visibility= View.GONE
            }
            override fun onResponse(call: Call<PreOrdersModel>, response: Response<PreOrdersModel>) {
                val responsedata = response.body()
                progress.visibility= View.GONE
                if (responsedata!!.status==100) {
                    amountTxt.text=response.body()!!.responseArray.whole_total.toString()
                    dateRecyclerView.layoutManager = LinearLayoutManager(mcontext)
                    dateRecyclerView.adapter = MyorderDatesAdapter(
                        response.body()!!.responseArray.data, mcontext, student_id,
                        dateRecyclerView, bottom, item, noitem, progress, amountTxt, totalAmount
                    )

                }
                else if (response.body()!!.status==132)
                {
                    bottom.visibility= View.GONE
                    item.visibility= View.GONE
                    dateRecyclerView.visibility= View.GONE
                    noitem.visibility= View.VISIBLE
                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })

        //dateRecyclerView.adapter = MyorderDatesAdapter(mMyOrderArrayList, nContext)

    }
}