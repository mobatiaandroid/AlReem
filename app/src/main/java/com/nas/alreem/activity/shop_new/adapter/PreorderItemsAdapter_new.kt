package com.nas.alreem.activity.shop_new.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.nas.alreem.R
import com.nas.alreem.activity.ProgressBarDialog
import com.nas.alreem.activity.canteen.model.add_orders.CatItemsListModel
import com.nas.alreem.activity.canteen.model.add_to_cart.AddToCartCanteenModel
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartRemoveModel
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartUpdateModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartApiModel
import com.nas.alreem.activity.lost_card.model.GetShopCartResponseModel
import com.nas.alreem.activity.lost_card.model.ShopCartResModel
import com.nas.alreem.activity.shop_new.model.AddToCartShopApiModel
import com.nas.alreem.activity.shop_new.model.ShopCartRemoveApiModel
import com.nas.alreem.activity.shop_new.model.ShopCartUpdateApiModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class PreorderItemsAdapter_new(
    val itemlist: ArrayList<CatItemsListModel>,
    var mcontext: Context,
    var date: String,
    var cart_list: ArrayList<ShopCartResModel>,
    var cartTotalAmount: Int = 0,
    var totalItems: TextView,
    var totalPrice: TextView,
    var bottomView: LinearLayout,
    var cart_empty: ImageView,
    var progressDialogP: ProgressBarDialog,

) :
    RecyclerView.Adapter<PreorderItemsAdapter_new.ViewHolder>() {
    // lateinit var onBottomReachedListener: OnBottomReachedListener
    lateinit var homeBannerUrlImageArray: ArrayList<String>
    var currentPage = 0
    var canteen_cart_id = ""
    var quantity = ""
    var apiCall: Int = 0
    var cartTotalItems:Int=0

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.preorder_itemlist_adapter_new, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //onBottomReachedListener.onBottomReached(position)
        //bottomView.visibility=View.GONE
        holder.itemNameTxt.text=itemlist[position].item_name
        holder.itemDescription.text = itemlist[position].description
        holder.amountTxt.text = itemlist[position].price.toString() + " AED"
        if(itemlist[position].available_quantity==0)
        {
            holder.multiLinear.visibility = View.GONE
            holder.addLinear.visibility = View.GONE
            holder.soldout.visibility=View.VISIBLE
        }
        else {


            if (itemlist[position].item_already_ordered == 0) {
                holder.confirmedTxt.visibility = View.GONE
            } else {
                holder.confirmedTxt.visibility = View.VISIBLE
            }
            if (itemlist[position].isItemCart) {
                holder.multiLinear.visibility = View.VISIBLE
                holder.addLinear.visibility = View.GONE
                holder.itemCount.setNumber(itemlist.get(position).quantityCart.toString())
                holder.itemCount.setRange(
                    0,
                    50
                )
            } else {
                holder.multiLinear.visibility = View.GONE
                holder.addLinear.visibility = View.VISIBLE
            }
            holder.addLinear.setOnClickListener {
                addToCart(itemlist[position].id, itemlist[position].price, position)
            }


            //holder.itemCount.setNumber(itemlist.get(position).quantityCart.toString())
            holder.itemCount.setOnValueChangeListener { view, oldValue, newValue ->

                var cartPos: Int = 0;
                for (i in cart_list.indices) {
                    Log.e("itemlistd", itemlist.get(position).id)
                    Log.e("cartlistidd", cart_list.get(i).item_id.toString())

                    if (itemlist.get(position).id.equals(cart_list.get(i).item_id.toString())) {
                        canteen_cart_id = cart_list.get(i).id.toString()
                        Log.e("cart_id_store", canteen_cart_id)
                    }
                }


                //  canteen_cart_id = cart_list[cartPos].items.get(position).id
                quantity = newValue.toString()
                if (newValue != 0) {
                    //progressDialogP.visibility=View.VISIBLE
                    updateCart(
                        itemlist[position].id,
                        position,
                        quantity,
                        holder.multiLinear,
                        holder.soldout
                    )
                    //  Log.e("amout",cart_list[position].item_total.toString())
                    //   holder.amountTxt.text = cart_list[position].item_total.toString() + " AED"
                    //   Log.e("amout1",cart_list[position].item_total.toString())
                } else {
                    //progressDialogP.visibility=View.VISIBLE
                    cancelCart(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var cartStatusImg: Button
        lateinit var itemNameTxt: TextView
        lateinit    var amountTxt: TextView
        lateinit    var notAvailableTxt: TextView
        lateinit    var itemDescription: TextView
        lateinit    var confirmedTxt: TextView
        lateinit var itemImage: ImageView
        lateinit var soldout: LinearLayout
        lateinit var addLinear: LinearLayout
        lateinit var multiLinear: LinearLayout
        lateinit var itemCount: ElegantNumberButton
        lateinit var bannerImagePager: ViewPager

        init {

//            cartStatusImg = (Button) view.findViewById(R.id.cartStatusImg);
            itemNameTxt =itemView.findViewById(R.id.itemNameTxt)
            amountTxt = itemView.findViewById(R.id.amountTxt)
            notAvailableTxt = itemView.findViewById(R.id.notAvailableTxt) as TextView
            // itemImage = itemView.findViewById(R.id.itemImage) as ImageView
            addLinear = itemView.findViewById(R.id.addLinear) as LinearLayout
            soldout = itemView.findViewById(R.id.soldout) as LinearLayout
            multiLinear = itemView.findViewById(R.id.multiLinear) as LinearLayout
            itemCount = itemView.findViewById(R.id.itemCount)
            itemDescription = itemView.findViewById(R.id.itemDescription)
            confirmedTxt = itemView.findViewById(R.id.confirmedTxt)
            bannerImagePager = itemView.findViewById(R.id.bannerImagePager) as ViewPager
        }
    }
    private fun getcanteen_cart(){
        cart_list= ArrayList()
        cartTotalAmount=0
        cartTotalItems=0
        progressDialogP.show()
        //progressDialogP.show()
        val token = PreferenceManager.getaccesstoken(mcontext)
        var canteenCart= CanteenCartApiModel(PreferenceManager.getStudentID(mcontext)!!)
        val call: Call<GetShopCartResponseModel> = ApiClient.getClient.get_shop_cart(canteenCart,"Bearer "+token)
        call.enqueue(object : Callback<GetShopCartResponseModel> {
            override fun onFailure(call: Call<GetShopCartResponseModel>, t: Throwable) {
                progressDialogP.hide()
                //  progressDialogP.hide()
            }
            override fun onResponse(call: Call<GetShopCartResponseModel>, response: Response<GetShopCartResponseModel>) {
                val responsedata = response.body()
                progressDialogP.hide()

                //progressDialogP.hide()
                if (responsedata!!.status==100) {
                    bottomView.visibility= View.VISIBLE
                    //progress.visibility = View.GONE
                    cart_list=response!!.body()!!.responseArray.data


                        cartTotalAmount=cartTotalAmount + responsedata.responseArray.total_amount



                    for (i in cart_list.indices){


                            cartTotalItems=cartTotalItems + cart_list[i].quantity

                    }

                    totalItems.setText(cartTotalItems.toString() + "Items")
                    totalPrice.setText(cartTotalAmount.toString() + "AED")
                    progressDialogP.hide()

                    //  progressDialogP.hide()
                    notifyDataSetChanged()
                }else
                {
                    bottomView.visibility = View.GONE
                   // DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }
    private fun addToCart(id:String,price:String,position: Int){
        progressDialogP.show()
        //   progressDialogP.show()

        val token = PreferenceManager.getaccesstoken(mcontext)
        var canteenadd= AddToCartShopApiModel(
            PreferenceManager.getStudentID(mcontext)!!,id,"1",price)
        val call: Call<AddToCartCanteenModel> = ApiClient.getClient.add_to_shop_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<AddToCartCanteenModel> {
            override fun onFailure(call: Call<AddToCartCanteenModel>, t: Throwable) {
                progressDialogP.hide()
                // progressDialogP.hide()
            }
            override fun onResponse(call: Call<AddToCartCanteenModel>, response: Response<AddToCartCanteenModel>) {
                val responsedata = response.body()
                //progressDialogP.visibility=View.GONE
                if (responsedata!!.status==100) {
                    itemlist[position].quantityCart=1
                    itemlist[position].isItemCart=true


                    //  Toast.makeText(mContext,"Item Successfully added to cart",Toast.LENGTH_SHORT).show();

                    getcanteen_cart()
                    notifyDataSetChanged()

                }else
                {

                    DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }
    private fun updateCart(
        id: String,
        position: Int,
        quant: String,
        multiLinear: LinearLayout,
        soldout: LinearLayout
    ){
        progressDialogP.show()
        // progressDialogP.show()
        Log.e("Cart_id",id)
        val token = PreferenceManager.getaccesstoken(mcontext)
        var canteenadd= ShopCartUpdateApiModel(
            PreferenceManager.getStudentID(mcontext)!!,quant,
            itemlist[position].id,canteen_cart_id)
        val call: Call<CanteenCartUpdateModel> = ApiClient.getClient.update_shop_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<CanteenCartUpdateModel> {
            override fun onFailure(call: Call<CanteenCartUpdateModel>, t: Throwable) {
                progressDialogP.hide()

                //   progressDialogP.hide()
            }
            override fun onResponse(call: Call<CanteenCartUpdateModel>, response: Response<CanteenCartUpdateModel>) {
                val responsedata = response.body()
                progressDialogP.hide()

                //progressDialogP.hide()
                if (responsedata!!.status==100) {
                    itemlist[position].quantityCart=quant.toInt()
                    itemlist[position].isItemCart=true
                    itemlist[position].cartId=canteen_cart_id

                    //Toast.makeText(mContext,"Item Successfully added to basket",Toast.LENGTH_SHORT).show();
                    /* itemlist.get(position).setQuantityCart(quantity)
                     mCartDetailArrayList.get(position).setItemCart(true)
                     mCartDetailArrayList.get(position).setCartItemId(canteen_cart_id)*/
                    //progressDialogP.visibility=View.VISIBLE
                    getcanteen_cart()
                   notifyDataSetChanged()

                }else
                {

                    if(responsedata!!.status==300)
                    {
                        multiLinear.visibility=View.GONE
                        soldout.visibility=View.VISIBLE

                    }
                  //  DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }
    private fun cancelCart(position: Int){
        progressDialogP.show()
        // progressDialogP.show()
        val token = PreferenceManager.getaccesstoken(mcontext)
        var canteenadd= ShopCartRemoveApiModel(
            PreferenceManager.getStudentID(mcontext)!!,canteen_cart_id)
        val call: Call<CanteenCartRemoveModel> = ApiClient.getClient.remove_shop_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<CanteenCartRemoveModel> {
            override fun onFailure(call: Call<CanteenCartRemoveModel>, t: Throwable) {
                progressDialogP.hide()

                //progressDialogP.hide()
            }
            override fun onResponse(call: Call<CanteenCartRemoveModel>, response: Response<CanteenCartRemoveModel>) {
                val responsedata = response.body()
                progressDialogP.hide()

                //progressDialogP.hide()
                if (responsedata!!.status==100) {

                    itemlist[position].isItemCart=false
                    itemlist[position].quantityCart=0
                    itemlist[position].cartId=canteen_cart_id

                    /* mCartDetailArrayList.get(position).setItemCart(false)
                     mCartDetailArrayList.get(position).setQuantityCart("0")
                     mCartDetailArrayList.get(position).setCartItemId(canteen_cart_id)*/
                    getcanteen_cart()
                    notifyDataSetChanged()

                }else
                {

                   // DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }



}