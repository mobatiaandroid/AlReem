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
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartRemoveModel
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartUpdateModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartApiModel
import com.nas.alreem.activity.lost_card.model.GetShopCartResponseModel
import com.nas.alreem.activity.lost_card.model.ShopCartResModel
import com.nas.alreem.activity.shop_new.model.ShopCartRemoveApiModel
import com.nas.alreem.activity.shop_new.model.ShopCartUpdateApiModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BasketItemsAdapter_new (
    var items_list: ArrayList<ShopCartResModel>, var mcontext: Context, var ordered_user_type:String,
    var student_id:String,
    var parent_id:String,
    var staff_id:String,
    var itemtxt: TextView, var amnttxt: TextView, var itemLinear: LinearLayout, var noItemTxt: ImageView,
    var dateRec: RecyclerView, var progress: ProgressBar
) :

    RecyclerView.Adapter<BasketItemsAdapter_new.ViewHolder>() {
    lateinit var cart_list: ArrayList<ShopCartResModel>
    var cartTotalAmount:Int=0
    var cartTotalItems:Int=0
    var quantity = ""
    var canteen_cart_id = ""
    var apiCall:Int = 0
    var homeBannerUrlImageArray: java.util.ArrayList<String>? = null
    var currentPage = 0
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.basket_itemlist_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("array", items_list.toString())
        holder.itemNameTxt.text = items_list[position].item_name
        holder.itemDescription.text = items_list[position].description
        var url:String?=""
        url=items_list[position].item_image.get(0)
        if (url.equals("")) {
            holder.itemImg.setBackgroundResource(R.drawable.default_banner)

        }
        else
        {
            // holder.itemImg.setBackgroundResource(R.color.)
            mcontext.let {
                Glide.with(it)
                    .load(url)
                    .into(holder.itemImg)
            }
        }
//        if (items_list[position].quantity.equals(0)) {
//            holder.itemView.visibility = View.GONE
//        }

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
                progress.visibility= View.VISIBLE
                updateCart(items_list[position].id.toString(),position,quantity,holder.basketRelative,holder.multiLinear)
                notifyDataSetChanged()
            } else {
                progress.visibility= View.VISIBLE
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
        Log.e("size", items_list.size.toString())
        return items_list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // var itemImage: ImageView
        var itemNameTxt: TextView
        var itemDescription: TextView
        var amountTxt: TextView
        var notAvailableTxt: TextView
        var removeTxt: TextView
        var portalTxt: TextView
        var cartitemcount: ElegantNumberButton
        var multiLinear: LinearLayout
        var linearlayout: LinearLayout
        var bannerImagePager: ViewPager
         var soldout: LinearLayout
         var itemImg : ImageView
         var basketRelative : RelativeLayout

        // var Datas:String=""

        init {
            itemImg = view.findViewById<ImageView>(R.id.itemImg)
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
            soldout = view.findViewById(R.id.soldout)
            basketRelative = view.findViewById(R.id.basketRelative)

        }
    }
    private fun getcanteen_cart(){
        cart_list= ArrayList()
        cartTotalAmount=0
        cartTotalItems=0
        progress.visibility= View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mcontext)
        var canteenCart= CanteenCartApiModel(PreferenceManager.getStudentID(mcontext).toString())
        val call: Call<GetShopCartResponseModel> = ApiClient.getClient.get_shop_cart(canteenCart,"Bearer "+token)
        call.enqueue(object : Callback<GetShopCartResponseModel> {
            override fun onFailure(call: Call<GetShopCartResponseModel>, t: Throwable) {

                progress.visibility= View.GONE
                // progress.hide()
            }
            override fun onResponse(call: Call<GetShopCartResponseModel>, response: Response<GetShopCartResponseModel>) {
                val responsedata = response.body()
                progress.visibility= View.GONE
//                progress.hide()
                if (responsedata!!.status==100) {
                    //progress.visibility = View.GONE
                    itemLinear.visibility= View.VISIBLE
                    cart_list=response!!.body()!!.responseArray.data

                    items_list=cart_list


                        cartTotalAmount=cartTotalAmount + responsedata.responseArray.total_amount

                    if (cartTotalAmount==0){
                        //bottomView.visibility=View.GONE
                    }else{
                        //bottomView.visibility=View.VISIBLE
                        for (i in cart_list.indices){


                                cartTotalItems=cartTotalItems + cart_list[i].quantity

                        }

                        itemtxt.setText(cartTotalItems.toString() + "Items")
                        amnttxt.setText(cartTotalAmount.toString() + "AED")
                    }
                    dateRec.visibility= View.VISIBLE
                    dateRec.layoutManager = LinearLayoutManager(mcontext)
                    /*dateRec.adapter =
                        DatesBasketAdapter_new(cart_list, mcontext,ordered_user_type,student_id,parent_id,staff_id,
                            itemtxt,amnttxt,itemLinear,noItemTxt,dateRec,progress,cartTotalAmount)
                    notifyDataSetChanged()*/
                }
                else if (response.body()!!.status==132)
                {
                    itemLinear.visibility= View.GONE
                    noItemTxt.visibility= View.VISIBLE
                    dateRec.visibility= View.GONE
                }
                else
                {
                    dateRec.visibility= View.GONE
                    itemLinear.visibility= View.GONE
                    DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }
    private fun updateCart(
        id: String,
        position: Int,
        quant: String,
        basketrelative: RelativeLayout,
        multiLinear: LinearLayout
    ){
        progress.visibility= View.VISIBLE
        // progress.show()
        val token = PreferenceManager.getaccesstoken(mcontext)
        var canteenadd= ShopCartUpdateApiModel(
            PreferenceManager.getStudentID(mcontext).toString(),quant,
            items_list[position].item_id.toString(),items_list[position].id.toString())
        val call: Call<CanteenCartUpdateModel> = ApiClient.getClient.update_shop_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<CanteenCartUpdateModel> {
            override fun onFailure(call: Call<CanteenCartUpdateModel>, t: Throwable) {

                progress.visibility= View.GONE
                //  progress.hide()
            }
            override fun onResponse(call: Call<CanteenCartUpdateModel>, response: Response<CanteenCartUpdateModel>) {
                val responsedata = response.body()
                progress.visibility= View.GONE
                //  progress.hide()
                if (responsedata!!.status==100) {
                    items_list[position].quantity=quant.toInt()

                    items_list[position].id=canteen_cart_id.toInt()
                    //Toast.makeText(mContext,"Item Successfully added to basket",Toast.LENGTH_SHORT).show();
                    /* itemlist.get(position).setQuantityCart(quantity)
                     mCartDetailArrayList.get(position).setItemCart(true)
                     mCartDetailArrayList.get(position).setCartItemId(canteen_cart_id)*/

                    // Myorderbasket_Activity().getcanteen_cart()
                    getcanteen_cart()
                    notifyDataSetChanged()

                } else
                {
                    if(responsedata!!.status==300)
                    {

//                        multiLinear.visibility=View.GONE
//                        basketrelative.alpha=0.5f
                        Toast.makeText(mcontext, "Item maximum limit reached!", Toast.LENGTH_SHORT).show()

                    }

                   // DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }
    private fun cancelCart(position: Int){
        progress.visibility= View.VISIBLE
        //  progress.show()
        val token = PreferenceManager.getaccesstoken(mcontext)
        var canteenadd= ShopCartRemoveApiModel(
            PreferenceManager.getStudentID(mcontext).toString(),items_list[position].id.toString())
        val call: Call<CanteenCartRemoveModel> = ApiClient.getClient.remove_shop_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<CanteenCartRemoveModel> {
            override fun onFailure(call: Call<CanteenCartRemoveModel>, t: Throwable) {

                progress.visibility= View.GONE
                //  progress.hide()
            }
            override fun onResponse(call: Call<CanteenCartRemoveModel>, response: Response<CanteenCartRemoveModel>) {
                val responsedata = response.body()
                progress.visibility= View.GONE
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

}