package com.nas.alreem.activity.shop_new

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.model.add_to_cart.AddToCartCanteenModel
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartRemoveModel
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartUpdateModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.shop_new.adapter.PageViewShop
import com.nas.alreem.activity.shop_new.model.AddToCartShopApiModel
import com.nas.alreem.activity.shop_new.model.ShopCartRemoveApiModel
import com.nas.alreem.activity.shop_new.model.ShopCartUpdateApiModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.home.PageView
import com.nas.alreem.fragment.home.bannerarray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask

class ProductDetailsPage : AppCompatActivity() {
    lateinit var price:String
    lateinit var item_name:String
    lateinit var item_desc:String
    lateinit var productNameTxt:TextView
    lateinit var price_text : TextView
    lateinit var product_desc : TextView
    lateinit var pager: ViewPager
    lateinit var banner_array : ArrayList<String>
    lateinit var mContext : Context
    var currentPage: Int = 0
    var position  : Int =0
    var quantityCart:Int = 0
     var isItemCart:Boolean=false
    var cart_id :String=""
    var id : String=""
    lateinit var addLinear: LinearLayout
    lateinit var multiLinear: LinearLayout
   // lateinit var itemCount: ElegantNumberButton
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_item_details)
        mContext = this
        initfn()
    }

    private fun initfn() {
        banner_array= ArrayList()
        price=intent.getStringExtra("price").toString()
        item_name=intent.getStringExtra("item_name").toString()
        item_desc=intent.getStringExtra("item_desc").toString()
        position= intent.getIntExtra("position",0)
        quantityCart= intent.getIntExtra("quantity_cart",0)
        isItemCart =  intent.getBooleanExtra("item_cart",false)
        cart_id =intent.getStringExtra("cart_id").toString()
        id=intent.getStringExtra("id").toString()
        heading=findViewById(R.id.heading)
        heading.text= "Product Details"
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
       pager = findViewById<ViewPager>(R.id.bannerImagePager)
        productNameTxt = findViewById(R.id.productNameTxt)
        price_text = findViewById(R.id.price)
        product_desc = findViewById(R.id.product_desc)
        addLinear = findViewById(R.id.addLinear) as LinearLayout
        multiLinear = findViewById(R.id.multiLinear) as LinearLayout
       // itemCount = findViewById(R.id.itemCount)
        productNameTxt.setText(item_name)
        price_text.setText(price + " AED")
        product_desc.setText(item_desc)
        banner_array.add("http:\\/\\/gama.mobatia.in:8080\\/nas-abudhabiv2\\/public\\/\\/storage\\/banner_images\\/1706171996.jpg")
        banner_array.add("http:\\/\\/gama.mobatia.in:8080\\/nas-abudhabiv2\\/public\\/\\/storage\\/banner_images\\/1706098493.jpg")
        banner_array.add("http:\\/\\/gama.mobatia.in:8080\\/nas-abudhabiv2\\/public\\/\\/storage\\/banner_images\\/1706171996.jpg")
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        updatedata()
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

        })
        inseert()

    }
    fun inseert()
    {
        if (banner_array.size > 0) {
           pager.adapter = mContext?.let { PageViewShop(it, bannerarray) }
        } else {
           pager.setBackgroundResource(R.drawable.default_banner)
        }
    }
    fun updatedata() {
        val handler = Handler()


        val update = Runnable {
            if (currentPage == bannerarray.size) {
                currentPage = 0
               pager.setCurrentItem(
                    currentPage, true
                )
            } else {
               pager.setCurrentItem(currentPage++, true)
            }
        }
        val swipetimer = Timer()

        swipetimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 100, 1000)

    }

   /* private fun updateCart(
        id: String,
        position: Int,
        quant: String,
        multiLinear: LinearLayout,
        soldout: LinearLayout
    ){
        //progressDialogP.show()
        /// progressDialogP.show()
        Log.e("Cart_id",id)
        val token = PreferenceManager.getaccesstoken(mContext)
        var canteenadd= ShopCartUpdateApiModel(
            PreferenceManager.getStudentID(mContext)!!,quant,
            itemlist[position].id,canteen_cart_id)
        val call: Call<CanteenCartUpdateModel> = ApiClient.getClient.update_shop_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<CanteenCartUpdateModel> {
            override fun onFailure(call: Call<CanteenCartUpdateModel>, t: Throwable) {
                progressDialogP.hide()

                //   progressDialogP.hide()
            }
            override fun onResponse(call: Call<CanteenCartUpdateModel>, response: Response<CanteenCartUpdateModel>) {
                val responsedata = response.body()
               // progressDialogP.hide()

                //progressDialogP.hide()
                if (responsedata!!.status==100) {
                   quantityCart=quant.toInt()
                    isItemCart=true
                    cart_id=canteen_cart_id

                    //Toast.makeText(mContext,"Item Successfully added to basket",Toast.LENGTH_SHORT).show();
                    *//* itemlist.get(position).setQuantityCart(quantity)
                     mCartDetailArrayList.get(position).setItemCart(true)
                     mCartDetailArrayList.get(position).setCartItemId(canteen_cart_id)*//*
                    //progressDialogP.visibility=View.VISIBLE

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
    }*/

    /*private fun cancelCart(position: Int){
       // progressDialogP.show()
        // progressDialogP.show()
        val token = PreferenceManager.getaccesstoken(mContext)
        var canteenadd= ShopCartRemoveApiModel(
            PreferenceManager.getStudentID(mContext)!!,canteen_cart_id)
        val call: Call<CanteenCartRemoveModel> = ApiClient.getClient.remove_shop_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<CanteenCartRemoveModel> {
            override fun onFailure(call: Call<CanteenCartRemoveModel>, t: Throwable) {
             //   progressDialogP.hide()

                //progressDialogP.hide()
            }
            override fun onResponse(call: Call<CanteenCartRemoveModel>, response: Response<CanteenCartRemoveModel>) {
                val responsedata = response.body()
               // progressDialogP.hide()

                //progressDialogP.hide()
                if (responsedata!!.status==100) {

                   isItemCart=false
                   quantityCart=0
                   cart_id=canteen_cart_id

                    *//* mCartDetailArrayList.get(position).setItemCart(false)
                     mCartDetailArrayList.get(position).setQuantityCart("0")
                     mCartDetailArrayList.get(position).setCartItemId(canteen_cart_id)*//*


                }else
                {

                    // DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }*/

   /* private fun addToCart(id:String,price:String,position: Int){
       // progressDialogP.show()
        //   progressDialogP.show()

        val token = PreferenceManager.getaccesstoken(mContext)
        var canteenadd= AddToCartShopApiModel(
            PreferenceManager.getStudentID(mContext)!!,id,"1",price)
        val call: Call<AddToCartCanteenModel> = ApiClient.getClient.add_to_shop_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<AddToCartCanteenModel> {
            override fun onFailure(call: Call<AddToCartCanteenModel>, t: Throwable) {
              //  progressDialogP.hide()
                // progressDialogP.hide()
            }
            override fun onResponse(call: Call<AddToCartCanteenModel>, response: Response<AddToCartCanteenModel>) {
                val responsedata = response.body()
                //progressDialogP.visibility=View.GONE
                if (responsedata!!.status==100) {
                    quantityCart=1
                    isItemCart=true


                    //  Toast.makeText(mContext,"Item Successfully added to cart",Toast.LENGTH_SHORT).show();




                }else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }
            }

        })
    }*/
}
