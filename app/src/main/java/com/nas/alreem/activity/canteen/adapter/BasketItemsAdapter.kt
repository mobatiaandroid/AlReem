package com.nas.alreem.activity.canteen.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.CanteenPaymentActivity
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartRemoveApiModel
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartRemoveModel
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartUpdateApiModel
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartUpdateModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartApiModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartResModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CartItemsListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasketItemsAdapter(
    var items_list: ArrayList<CartItemsListModel>,
    var mcontext: Context,
    var ordered_user_type: String,
    var student_id: String,
    var parent_id: String,
    var staff_id: String,
    var delivery_date: String,
    var itemtxt: TextView,
    var amnttxt: TextView,
    var itemLinear: LinearLayout,
    var noItemTxt: ImageView,
    var dateRec: RecyclerView,
    var progress: ProgressBar,
    var totalAmount: String,
    var WalletAmount: Int,
    var TotalOrderedAmount: Int,
    var cart_totoal: Int
) :

    RecyclerView.Adapter<BasketItemsAdapter.ViewHolder>() {
   // lateinit var cart_list: ArrayList<CanteenCartResModel>
    var cartTotalAmount:Int=0
    var cartTotalItems:Int=0
    var quantity = ""
    var canteen_cart_id = ""
    var apiCall:Int = 0
    var homeBannerUrlImageArray: java.util.ArrayList<String>? = null
    var currentPage = 0
    var BalanceWalletAmount = 0f
    var BalanceConfirmWalletAmount = 0f
    var CartTotalAmount = 0f
    var carttotalfull = 0
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.basket_itemlist_adapter_canteen, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemNameTxt.text = items_list[position].item_name
        holder.itemDescription.text = items_list[position].description
        holder.amountTxt.text = items_list[position].price.toString() + "AED"
        holder.notAvailableTxt.visibility = View.GONE
        holder.removeTxt.visibility = View.GONE
        holder.multiLinear.visibility = View.VISIBLE
        holder.cartitemcount.setNumber(items_list.get(position).quantity.toString())
        holder.cartitemcount.setRange(
            0,
            50)
        holder.cartitemcount.setOnValueChangeListener { view, oldValue, newValue ->
            canteen_cart_id = items_list[position].id.toString()
            quantity = newValue.toString()

            if (newValue != 0) {
               // Log.e("newValue", newValue.toString())
                //Log.e("totalamount",totalAmount)
                //Log.e("TotalOrderedAmount", TotalOrderedAmount.toString())
               // Log.e("cart_totoal", cart_totoal.toString())

                if (newValue > oldValue) {
                    progress.visibility = View.VISIBLE

                    val totalAmt = java.lang.Float.valueOf(totalAmount)
                    CartTotalAmount =
                        cart_totoal + items_list.get(position).price.toFloat()
                    carttotalfull=TotalOrderedAmount+CartTotalAmount.toInt()
                   // Log.e("carttotalfull", carttotalfull.toString())

                    BalanceWalletAmount = PreferenceManager().getWalletAmount(mcontext) - totalAmt
                    if (PreferenceManager().getWalletAmount(mcontext) > carttotalfull) {
                        BalanceConfirmWalletAmount = BalanceWalletAmount - CartTotalAmount
                       // Log.e("BalanceWalletAmount", BalanceWalletAmount.toString())
                       // Log.e("BalanceConfirmWalletAmount", BalanceConfirmWalletAmount.toString())
                       // Log.e("CartAmount", CartTotalAmount.toString())
                      //  Log.e("wallet", PreferenceManager().getWalletAmount(mcontext).toString())


                        if (PreferenceManager().getWalletAmount(mcontext) > CartTotalAmount) {
                            updateCart(items_list[position].id.toString(), position, quantity)
                            notifyDataSetChanged()
                        } else {
                            holder.cartitemcount.number = oldValue.toString()
                            showInsufficientBal(
                                mcontext,
                                "Alert",
                                "Insufficient balance please top up wallet",
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    } else {
                        holder.cartitemcount.number = oldValue.toString()
                        showInsufficientBal(
                            mcontext,
                            "Alert",
                            "Insufficient balance please top up wallet",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                }
                else {
                    updateCart(items_list[position].id.toString(), position, quantity)
                    notifyDataSetChanged()
                }
            } else {
                progress.visibility=View.VISIBLE
                cancelCart(position)
                notifyDataSetChanged()
            }
        }






       /* if (items_list[position].available_quantity.equals("0")) {
            holder.notAvailableTxt.visibility = View.VISIBLE
            holder.removeTxt.visibility = View.VISIBLE
            holder.multiLinear.visibility = View.GONE
        } else {
            if (items_list[position].portal.equals("1")) {
                holder.portalTxt.visibility = View.GONE
            } else {
                holder.portalTxt.visibility = View.VISIBLE
            }
            holder.notAvailableTxt.visibility = View.GONE
            holder.removeTxt.visibility = View.GONE
            holder.multiLinear.visibility = View.VISIBLE
            holder.cartitemcount.number = items_list[position].quantity
            holder.cartitemcount.setRange(
                0,
                Integer.valueOf(items_list[position].available_quantity)
            )
        }*/
        holder.removeTxt.setOnClickListener {
            //cart cancel
        }
       /* holder.cartitemcount.setOnValueChangeListener { view, oldValue, newValue ->
            canteen_cart_id = items_list[position].item_id
            quantity = newValue.toString()
            println("Click works")
            if (InternetCheckClass.isInternetAvailable(mcontext)) {
                if (newValue != 0) {
                    if (newValue == Integer.valueOf(
                            items_list[position].available_quantity
                        )
                    ) {
                        Toast.makeText(
                            mcontext,
                            "You have reached the Pre-Order limit for this item",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    //getCartUpdate(URL_CANTEEN_CONFIRMED_ORDER_EDIT,canteen_cart_id,quantity);
                    //  getAddToCart(URL_CANTEEN_ADD_TO_CART,position,String.valueOf(newValue));
                    //cart update
                } else {
                    //getCartCancel(URL_CANTEEN_CONFIRMED_ORDER_ITEM_CELL_CANCEL,canteen_cart_id);
                    //cart cancel
                }
            } else {
                InternetCheckClass.showSuccessInternetAlert(com.mobatia.bisad.fragment.home.mContext)
            }
        }*/
       /* homeBannerUrlImageArray = items_list[position].item_image
        if (homeBannerUrlImageArray != null) {
            val handler = Handler()
            val Update = Runnable {
                if (currentPage == homeBannerUrlImageArray!!.size) {
                    currentPage = 0
                    holder.bannerImagePager.setCurrentItem(
                        currentPage,
                        false
                    )
                } else {
                    holder.bannerImagePager
                        .setCurrentItem(currentPage++, true)
                }
            }
            val swipeTimer = Timer()
            swipeTimer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(Update)
                }
            }, 100, 3000)
            holder.bannerImagePager.adapter =
                ImagePagerDrawableAdapter(homeBannerUrlImageArray!!, mcontext)
        } else {
            holder.bannerImagePager.setBackgroundResource(R.drawable.noitemscanteen)
        }*/

    }



    override fun getItemCount(): Int {
        return items_list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

       // var itemImage: ImageView
        var itemNameTxt: TextView
        var itemDescription:TextView
        var amountTxt:TextView
        var notAvailableTxt:TextView
        var removeTxt:TextView
        var portalTxt:TextView
        var cartitemcount: ElegantNumberButton
        var multiLinear: LinearLayout
        var linearlayout: LinearLayout
        var bannerImagePager: ViewPager
        // var Datas:String=""

        init {
            //itemImage = view.findViewById<ImageView>(R.id.itemImage)
            itemNameTxt = view.findViewById<TextView>(R.id.itemNameTxt)
            itemDescription = view.findViewById<TextView>(R.id.itemDescription)
            amountTxt = view.findViewById<TextView>(R.id.amountTxt)
            cartitemcount = view.findViewById<ElegantNumberButton>(R.id.cartitemcount)
            multiLinear = view.findViewById<LinearLayout>(R.id.multiLinear)
            linearlayout = view.findViewById<LinearLayout>(R.id.linearlayout)
            notAvailableTxt = view.findViewById<TextView>(R.id.notAvailableTxt)
            removeTxt = view.findViewById<TextView>(R.id.removeTxt)
            portalTxt = view.findViewById<TextView>(R.id.portalTxt)
            bannerImagePager = view.findViewById(R.id.bannerImagePager)
        }
    }
    private fun getcanteen_cart(){
        ConstantFunctions.cart_list= ArrayList()
        cartTotalAmount=0
        cartTotalItems=0
        progress.visibility=View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mcontext)
        var canteenCart= CanteenCartApiModel(PreferenceManager.getStudentID(mcontext).toString())
        val call: Call<CanteenCartModel> = ApiClient(mcontext).getClient.get_canteen_cart(canteenCart,"Bearer "+token)
        call.enqueue(object : Callback<CanteenCartModel> {
            override fun onFailure(call: Call<CanteenCartModel>, t: Throwable) {

                progress.visibility=View.GONE
               // progress.hide()
            }
            override fun onResponse(call: Call<CanteenCartModel>, response: Response<CanteenCartModel>) {
                val responsedata = response.body()
                progress.visibility=View.GONE
//                progress.hide()
                if (responsedata!!.status==100) {
                    //progress.visibility = View.GONE
                    itemLinear.visibility=View.VISIBLE
                    cart_totoal =
                        response!!.body()!!.responseArray.cart_totoal
                   PreferenceManager().setcartamounttotal(mcontext,cart_totoal)
                    TotalOrderedAmount =
                        response!!.body()!!.responseArray.previous_orders_total
                    ConstantFunctions. cart_list=response!!.body()!!.responseArray.data
                    for (i in ConstantFunctions.cart_list.indices){

                        cartTotalAmount=cartTotalAmount + ConstantFunctions.cart_list[i].total_amount
                    }
                    if (cartTotalAmount==0){
                        //bottomView.visibility=View.GONE
                    }else{
                        //bottomView.visibility=View.VISIBLE
                        for (i in ConstantFunctions.cart_list.indices){

                            for (j in ConstantFunctions.cart_list[i].items.indices){
                                cartTotalItems=cartTotalItems +ConstantFunctions. cart_list[i].items[j].quantity
                            }
                        }

                        itemtxt.setText(cartTotalItems.toString() + "Items")
                        amnttxt.setText(cartTotalAmount.toString() + "AED")
                    }
                    dateRec.visibility=View.VISIBLE
                    dateRec.layoutManager = LinearLayoutManager(mcontext)
                    dateRec.adapter =
                        DatesBasketAdapter(
                            ConstantFunctions. cart_list,
                            mcontext,
                            ordered_user_type,
                            student_id,
                            parent_id,
                            staff_id,
                            itemtxt,
                            amnttxt,
                            itemLinear,
                            noItemTxt,
                            dateRec,
                            progress,
                            WalletAmount,
                            TotalOrderedAmount,
                            cart_totoal
                        )
                    notifyDataSetChanged()
                }
                else if (response.body()!!.status==132)
                {
                    itemLinear.visibility=View.GONE
                    noItemTxt.visibility=View.VISIBLE
                    dateRec.visibility=View.GONE
                }
                else
                {
                    dateRec.visibility=View.GONE
                    itemLinear.visibility=View.GONE
                    DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }
    private fun updateCart(id:String,position: Int,quant:String){
        progress.visibility=View.VISIBLE
       // progress.show()
        val token = PreferenceManager.getaccesstoken(mcontext)
        var canteenadd= CanteenCartUpdateApiModel(
            PreferenceManager.getStudentID(mcontext).toString(),delivery_date,quant,
            items_list[position].item_id.toString(),items_list[position].id.toString())
        val call: Call<CanteenCartUpdateModel> = ApiClient(mcontext).getClient.update_canteen_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<CanteenCartUpdateModel> {
            override fun onFailure(call: Call<CanteenCartUpdateModel>, t: Throwable) {

                progress.visibility=View.GONE
              //  progress.hide()
            }
            override fun onResponse(call: Call<CanteenCartUpdateModel>, response: Response<CanteenCartUpdateModel>) {
                val responsedata = response.body()
                progress.visibility=View.GONE
              //  progress.hide()
                if (responsedata!!.status==100) {
                    items_list[position].quantity=quant.toInt()

                    items_list[position].id=canteen_cart_id.toInt()
                    //Toast.makeText(mContext,"Item Successfully added to basket",Toast.LENGTH_SHORT).show();
                    /* itemlist.get(position).setQuantityCart(quantity)
                     mCartDetailArrayList.get(position).setItemCart(true)
                     mCartDetailArrayList.get(position).setCartItemId(canteen_cart_id)*/

                 //  Myorderbasket_Activity().getcanteen_cart_new(progress,mcontext)
                   getcanteen_cart()
                    notifyDataSetChanged()

                } else
                {

                    DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }
    private fun cancelCart(position: Int){
        progress.visibility=View.VISIBLE
      //  progress.show()
        val token = PreferenceManager.getaccesstoken(mcontext)
        var canteenadd= CanteenCartRemoveApiModel(
            PreferenceManager.getStudentID(mcontext).toString(),items_list[position].id.toString())
        val call: Call<CanteenCartRemoveModel> = ApiClient(mcontext).getClient.remove_canteen_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<CanteenCartRemoveModel> {
            override fun onFailure(call: Call<CanteenCartRemoveModel>, t: Throwable) {

                progress.visibility=View.GONE
              //  progress.hide()
            }
            override fun onResponse(call: Call<CanteenCartRemoveModel>, response: Response<CanteenCartRemoveModel>) {
                val responsedata = response.body()
                progress.visibility=View.GONE
                //progress.hide()
                if (responsedata!!.status==100) {
                    items_list[position].quantity=0

                    items_list[position].id=canteen_cart_id.toInt()
                  /*  items_list[position].isItemCart=false
                    items_list[position].quantityCart=0
                    itemlist[position].cartId=canteen_cart_id*/

                    /* mCartDetailArrayList.get(position).setItemCart(false)
                     mCartDetailArrayList.get(position).setQuantityCart("0")
                     mCartDetailArrayList.get(position).setCartItemId(canteen_cart_id)*/
                    //Myorderbasket_Activity().getcanteen_cart()
                    getcanteen_cart()
                    notifyDataSetChanged()

                } else
                {

                    DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }
    fun showInsufficientBal(
        activity: Context?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<View>(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener {
            val intent = Intent(
                mcontext,
                CanteenPaymentActivity::class.java
            )
            dialog.dismiss()
            mcontext.startActivity(intent)

        }
        val dialogButtonCancel = dialog.findViewById<View>(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener { dialog.dismiss()
            progress.visibility=View.GONE}
        dialog.show()
    }
}