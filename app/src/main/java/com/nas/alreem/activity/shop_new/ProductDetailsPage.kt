package com.nas.alreem.activity.shop_new

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.model.add_to_cart.AddToCartCanteenModel
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartRemoveModel
import com.nas.alreem.activity.canteen.model.add_to_cart.CanteenCartUpdateModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartApiModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.lost_card.model.GetShopCartResponseModel
import com.nas.alreem.activity.lost_card.model.ShopCartResModel
import com.nas.alreem.activity.shop_new.adapter.PageViewShop
import com.nas.alreem.activity.shop_new.model.AddToCartShopApiModel
import com.nas.alreem.activity.shop_new.model.ShopCartRemoveApiModel
import com.nas.alreem.activity.shop_new.model.ShopCartUpdateApiModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.PreferenceManager
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
    lateinit var cart_list: ArrayList<ShopCartResModel>

    lateinit var mContext : Context
    var currentPage: Int = 0
    var position  : Int =0
    var quantityCart:Int = 0
     var isItemCart:Boolean=false
    var available_quantity : Int=0
    var cart_id :String=""
    var id : String=""
    var size_chart : String=""
    lateinit var addLinear: LinearLayout
    lateinit var multiLinear: LinearLayout
    lateinit var addLinear_cart : LinearLayout
    lateinit var backRelative: ImageView
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    var image_array = ArrayList<String>()
    lateinit var itemCount: ElegantNumberButton
    var canteen_cart_id = ""
    var quantity = ""
    lateinit var sacleImg : ImageView
    lateinit var scaletextt : TextView
    lateinit var basket : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_item_details)
        mContext = this
        initfn()
        getcanteen_cart()
    }

    private fun initfn() {
        price=intent.getStringExtra("price").toString()
        item_name=intent.getStringExtra("item_name").toString()
        item_desc=intent.getStringExtra("item_desc").toString()
        position= intent.getIntExtra("position",0)
        quantityCart= intent.getIntExtra("quantity_cart",0)
        isItemCart =  intent.getBooleanExtra("item_cart",false)
        cart_id =intent.getStringExtra("cart_id").toString()
        image_array = intent.getStringArrayListExtra("array_list")!!
        size_chart = intent.getStringExtra("size_chart").toString()
        available_quantity = intent.getIntExtra("available_quantity",0)
        Log.e("available_quantity", available_quantity.toString())

        Log.e("size_chart",size_chart)
        id=intent.getStringExtra("id").toString()
        Log.e("id",id)
        heading=findViewById(R.id.textViewtitle)
        heading.text= "Product Details"
        itemCount = findViewById(R.id.itemCount)
        backRelative=findViewById(R.id.logoclick)
        logoClickImgView=findViewById(R.id.relative_logo_header)
        basket = findViewById(R.id.basket)
       pager = findViewById<ViewPager>(R.id.bannerImagePager)
        productNameTxt = findViewById(R.id.productNameTxt)
        price_text = findViewById(R.id.price)
        product_desc = findViewById(R.id.product_desc)
        sacleImg = findViewById(R.id.sacleImg)
        scaletextt = findViewById(R.id.scaletextt)
        addLinear = findViewById(R.id.addLinear) as LinearLayout
        multiLinear = findViewById(R.id.multiLinear) as LinearLayout
        addLinear_cart = findViewById(R.id.addLinear_cart)
       // itemCount = findViewById(R.id.itemCount)
        productNameTxt.setText(item_name)
        price_text.setText(price + " AED")
        product_desc.setText(item_desc)
        basket.setOnClickListener {
            val intent = Intent(mContext, Myorderbasket_Activity_new::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        addLinear.setOnClickListener {

        }
        multiLinear.setOnClickListener {
            val intent = Intent(mContext, PDFViewerActivity::class.java)
            intent.putExtra("Url", size_chart)
            intent.putExtra("title", "Size Chart")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        sacleImg.setOnClickListener {
            val intent = Intent(mContext, PDFViewerActivity::class.java)
            intent.putExtra("Url", size_chart)
            intent.putExtra("title", "Size Chart")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        scaletextt.setOnClickListener {
            val intent = Intent(mContext, PDFViewerActivity::class.java)
            intent.putExtra("Url", size_chart)
            intent.putExtra("title", "Size Chart")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        if(available_quantity==0)
        {
            addLinear_cart.visibility = View.GONE
            addLinear.visibility = View.GONE

        }
        if (isItemCart) {
           // holder.multiLinear.visibility = View.VISIBLE
            if(available_quantity==0)
            {
                addLinear_cart.visibility = View.GONE
                addLinear.visibility = View.GONE

            }
            else
            {
                addLinear.visibility = View.VISIBLE
                addLinear_cart.visibility = View.GONE
                itemCount.setNumber(quantityCart.toString())
                itemCount.setRange(
                    0,
                    50
                )
            }

        } else {
           // holder.multiLinear.visibility = View.GONE
            addLinear_cart.visibility = View.VISIBLE
            itemCount.setNumber("1")
            addLinear.visibility = View.GONE
        }
        addLinear_cart.setOnClickListener {
            addToCart(id, price, position,addLinear_cart,addLinear)
        }
        itemCount.setOnValueChangeListener { view, oldValue, newValue ->

            var cartPos: Int = 0;
            for (i in cart_list.indices) {

                if (id.equals(cart_list.get(i).item_id.toString())) {
                    canteen_cart_id = cart_list.get(i).id.toString()
                }
            }


            //  canteen_cart_id = cart_list[cartPos].items.get(position).id
            quantity = newValue.toString()
            if (newValue != 0) {
                //progressDialogP.visibility=View.VISIBLE
                updateCart(
                   id,
                    position,
                    quantity)

            } else {

                cancelCart(position)
            }
        }
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
        if (image_array.size > 0) {
           pager.adapter = mContext?.let { PageViewShop(it, image_array) }
        } else {
           pager.setBackgroundResource(R.drawable.default_banner)
        }
    }
    fun updatedata() {
        val handler = Handler()


        val update = Runnable {
            if (currentPage == image_array.size) {
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

    private fun updateCart(
        id: String,
        position: Int,
        quant: String

    ){

        Log.e("Cart_id",id)
        val token = PreferenceManager.getaccesstoken(mContext)
        var canteenadd= ShopCartUpdateApiModel(
            PreferenceManager.getStudentID(mContext)!!,quant,
            id,canteen_cart_id)
        val call: Call<CanteenCartUpdateModel> = ApiClient.getClient.update_shop_cart(canteenadd,"Bearer "+token)
        call.enqueue(object : Callback<CanteenCartUpdateModel> {
            override fun onFailure(call: Call<CanteenCartUpdateModel>, t: Throwable) {
             //   progressDialogP.hide()

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
                    getcanteen_cart()


                }else
                {

                    if(responsedata!!.status==300)
                    {
                      //  multiLinear.visibility=View.GONE
                     //   soldout.visibility=View.VISIBLE

                    }
                    //  DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }

    private fun getcanteen_cart(){
        cart_list= ArrayList()

        val token = PreferenceManager.getaccesstoken(mContext)
        var canteenCart= CanteenCartApiModel( PreferenceManager.getStudentID(mContext)!!)
        val call: Call<GetShopCartResponseModel> = ApiClient.getClient.get_shop_cart(canteenCart,"Bearer "+token)
        call.enqueue(object : Callback<GetShopCartResponseModel> {
            override fun onFailure(call: Call<GetShopCartResponseModel>, t: Throwable) {
              //  progressDialogP.hide()
            }
            override fun onResponse(call: Call<GetShopCartResponseModel>, response: Response<GetShopCartResponseModel>) {
                val responsedata = response.body()
               // progressDialogP.hide()
                if (responsedata!!.status==100) {
                  //  bottomview.visibility= View.VISIBLE

                    cart_list=response!!.body()!!.responseArray.data
                   // cartTotalAmount=0


                  //  cartTotalAmount=cartTotalAmount + responsedata.responseArray.total_amount



                }
                else
                {
                    //bottomview.visibility= View.GONE
                }
//                else
//                {
//
//                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
//                }
            }

        })
    }

    private fun cancelCart(position: Int){
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
                    addLinear.visibility = View.GONE
                    addLinear_cart.visibility = View.VISIBLE
                    getcanteen_cart()


                }else
                {

                    // DialogFunctions.commonErrorAlertDialog(mcontext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mcontext)
                }
            }

        })
    }

    private fun addToCart(
        id: String,
        price: String,
        position: Int,
        addLinear_cart: LinearLayout,
        addLinear: LinearLayout
    ){
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

                    addLinear_cart.visibility=View.GONE
                    addLinear.visibility=View.VISIBLE
                    //  Toast.makeText(mContext,"Item Successfully added to cart",Toast.LENGTH_SHORT).show();

                    getcanteen_cart()


                }else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }
            }

        })
    }
}
