package com.nas.alreem.activity.shop_new

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.ProgressBarDialog
import com.nas.alreem.activity.bus_service.BusServiceDetailActivity
import com.nas.alreem.activity.canteen.adapter.ItemCategoriesAdapter
import com.nas.alreem.activity.canteen.model.add_orders.CatItemsListModel
import com.nas.alreem.activity.canteen.model.add_orders.CatListModel
import com.nas.alreem.activity.canteen.model.add_orders.CategoryListModel
import com.nas.alreem.activity.canteen.model.add_orders.ItemsListModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartApiModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CartItemsListModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.lost_card.model.GetShopCartResponseModel
import com.nas.alreem.activity.lost_card.model.ShopCartResModel
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.activity.shop_new.adapter.ItemCategoriesAdapter_new
import com.nas.alreem.activity.shop_new.adapter.PreorderItemsAdapterShop_new
import com.nas.alreem.activity.shop_new.model.ItemsListModel_new
import com.nas.alreem.activity.shop_new.model.ShopItemsApiModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.home.HomeFragment
import com.nas.alreem.fragment.shop.ShopFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Addorder_Activity_new : AppCompatActivity()  {
    lateinit var nContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview_category: RecyclerView
    lateinit var recyclerview_date: RecyclerView
    lateinit var back: ImageView
    lateinit var cart_list: ArrayList<ShopCartResModel>
    lateinit var cart_items_list: ArrayList<CartItemsListModel>
    private var id: String? = null
    lateinit var date_title: TextView
    lateinit var recyclerview_item: RecyclerView
    var studentListArrayList = ArrayList<StudentList>()


    lateinit var title: TextView
    lateinit var cart_empty: ImageView
    lateinit var progressDialogP: ProgressBarDialog
    lateinit var category_list: ArrayList<CategoryListModel>
    lateinit var item_list: ArrayList<CatItemsListModel>
    lateinit var bottomview: LinearLayout
    lateinit var basketbtn: LinearLayout

    var cat_selected: String = ""
    var date_selected: String = ""
    var def_cat_id: String = ""
    var date_string: String = ""
    var apiCall: Int = 0
    var cartTotalAmount:Int=0
    var cartTotalItem:Int=0

    lateinit var total_items: TextView
    lateinit var total_price: TextView
    var firstVisit: Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_addorder)
        firstVisit = true
        initfn()


        if (ConstantFunctions.internetCheck(nContext)) {
            item_categories()
            getcanteen_cart()

        } else {
            DialogFunctions.showInternetAlertDialog(nContext)
        }


    }

    private fun initfn() {
        nContext = this
        back = findViewById(R.id.back)
        logoClickImg=findViewById(R.id.logoclick)
        title = findViewById(R.id.titleTextView)
        id = PreferenceManager.getStudentID(nContext).toString()
        date_title = findViewById(R.id.date_title)
        title.text = "Shop-Order"
        cart_empty = findViewById(R.id.item_empty)
        progressDialogP= ProgressBarDialog(nContext)



        category_list = ArrayList()
        cart_list = ArrayList()
        cart_items_list = ArrayList()

        total_items = findViewById(R.id.itemCount)
        total_price = findViewById(R.id.totalAmount)
        bottomview = findViewById(R.id.cartLinear)
        basketbtn = findViewById(R.id.cartBtnLinear)

        recyclerview_category = findViewById(R.id.categoryRecyclerView)
        recyclerview_date = findViewById(R.id.dateRecyclerView)
        recyclerview_item = findViewById(R.id.itemRecyclerView)
        logoClickImg.setOnClickListener {
            val intent = Intent(nContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }


        back.setOnClickListener {
               finish()
        }
        basketbtn.setOnClickListener {
            finish()
            val intent = Intent(nContext, Myorderbasket_Activity_new::class.java)
            intent.putExtra("date", date_selected)
            nContext.startActivity(intent)
        }

        bottomview.setOnClickListener(View.OnClickListener {
            finish()
            val intent = Intent(nContext, Myorderbasket_Activity_new::class.java)
            intent.putExtra("date", date_selected)
            nContext.startActivity(intent)
        })
        recyclerview_item.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                //item_categories()

                val intent =Intent(nContext, ProductDetailsPage::class.java)
                intent.putExtra("item_name",item_list.get(position).item_name)
                intent.putExtra("item_desc",item_list.get(position).description)
                intent.putExtra("price",item_list.get(position).price)
                intent.putExtra("position",position)
                intent.putExtra("quantity_cart",item_list.get(position).quantityCart)
                intent.putExtra("item_cart",item_list.get(position).isItemCart)
                intent.putExtra("id",item_list.get(position).id)
                intent.putExtra("size_chart",item_list.get(position).size_chart)
                intent.putExtra("cart_id",item_list.get(position).cartId)
                intent.putExtra("available_quantity",item_list.get(position).available_quantity)
                intent.putStringArrayListExtra(
                    "array_list",
                    item_list[position].item_image
                )
                Log.e("arraysize", item_list[position].item_image.size.toString())
                startActivity(intent)
            }
        })
    }



    private fun item_categories(){
        category_list= ArrayList()
        progressDialogP.show()

        val token = PreferenceManager.getaccesstoken(nContext)
        val call: Call<CatListModel> = ApiClient.getClient.get_shop_categories("Bearer "+token)
        call.enqueue(object : Callback<CatListModel> {
            override fun onFailure(call: Call<CatListModel>, t: Throwable) {
            }
            override fun onResponse(call: Call<CatListModel>, response: Response<CatListModel>) {
                val responsedata = response.body()
                progressDialogP.hide()
                if (responsedata!!.status==100) {
                    category_list= ArrayList()


                    category_list.addAll(response.body()!!.responseArray.data)
                    def_cat_id=category_list[0].id
                    PreferenceManager.setcategoriid(nContext,def_cat_id)

                    cat_selected=def_cat_id
                    for (i in category_list.indices) {
                        category_list.get(i).isItemSelected = category_list.get(i).id.equals(def_cat_id)
                    }
                    var llm = (LinearLayoutManager(nContext))
                    llm.orientation = LinearLayoutManager.HORIZONTAL
                    recyclerview_category.layoutManager = llm
                    recyclerview_category.adapter = ItemCategoriesAdapter_new(category_list, nContext)
                    // progressDialogP.visibility=View.VISIBLE
                    items()


                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                }
            }

        })

        recyclerview_category.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                //cat_selected= category_list.get(position).id
                var foundPosition = -1
                var isFound: Boolean = false
                if (ConstantFunctions.internetCheck(nContext)) {
                    getcanteen_cart()
                } else {
                    DialogFunctions.showInternetAlertDialog(nContext)
                }

                for (i in 0..category_list.size - 1) {
                    if (category_list.get(i).isItemSelected) {
                        foundPosition = i
                        isFound = true
                        cat_selected= category_list.get(position).id
                        PreferenceManager.setcategoriid(nContext,cat_selected)
                        items_onclick()
                    }
                }
                if (isFound) {
                    if (position == foundPosition) {
                        category_list.get(foundPosition).isItemSelected = true
                        recyclerview_category.adapter =
                            ItemCategoriesAdapter_new(category_list, nContext)
                    } else {
                        category_list.get(foundPosition).isItemSelected = false
                        category_list.get(position).isItemSelected = true
                        cat_selected= category_list.get(position).id

                        recyclerview_category.adapter =
                            ItemCategoriesAdapter_new(category_list, nContext)
                        items_onclick()
                    }
                } else {
                    category_list.get(position).isItemSelected = true
                    cat_selected= category_list.get(position).id

                    recyclerview_category.adapter = ItemCategoriesAdapter_new(category_list, nContext)
                    items_onclick()
                }
                // items_onclick()
                recyclerview_category.scrollToPosition(position)
            }
        })

    }
    fun callStudentListApi()
    {
        // progressDialogAdd.visibility=View.VISIBLE
        studentListArrayList= ArrayList()
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+ PreferenceManager.getaccesstoken(nContext))
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                // progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                val responsedata = response.body()
                //  progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            studentListArrayList=ArrayList()
                            studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                            if (PreferenceManager.getStudentID(nContext).equals(""))
                            {
                                /*studentName=studentListArrayList.get(0).name
                                studentImg=studentListArrayList.get(0).photo
                                studentId=studentListArrayList.get(0).id
                                studentClass=studentListArrayList.get(0).section*/
                                PreferenceManager.setStudentID(nContext,studentListArrayList.get(0).id)
                                /* PreferenceManager.setStudentName(mContext,studentName)
                                 PreferenceManager.setStudentPhoto(mContext,studentImg)
                                 PreferenceManager.setStudentClass(mContext,studentClass)
                                 studentNameTxt.text=studentName*/
                                /* if(!studentImg.equals(""))
                                 {
                                     Glide.with(mContext) //1
                                         .load(studentImg)
                                         .placeholder(R.drawable.student)
                                         .error(R.drawable.student)
                                         .skipMemoryCache(true) //2
                                         .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                         .transform(CircleCrop()) //4
                                         .into(studImg)
                                 }
                                 else{
                                     studImg.setImageResource(R.drawable.student)
                                 }*/

                            }
                            else{
                                /* studentName= PreferenceManager.getStudentName(mContext)!!
                                 studentImg= PreferenceManager.getStudentPhoto(mContext)!!
                                 studentId= PreferenceManager.getStudentID(mContext)!!
                                 studentClass= PreferenceManager.getStudentClass(mContext)!!
                                 studentNameTxt.text=studentName
                                 if(!studentImg.equals(""))
                                 {
                                     Glide.with(mContext) //1
                                         .load(studentImg)
                                         .placeholder(R.drawable.student)
                                         .error(R.drawable.student)
                                         .skipMemoryCache(true) //2
                                         .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                         .transform(CircleCrop()) //4
                                         .into(studImg)
                                 }
                                 else{
                                     studImg.setImageResource(R.drawable.student)
                                 }*/
                            }

                        }
                        else
                        {

                            DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
    private fun items(){
        item_list= ArrayList()
        progressDialogP.show()

        val token = PreferenceManager.getaccesstoken(nContext)
        var canteenItems= ShopItemsApiModel(
            PreferenceManager.getStudentID(nContext)!!,def_cat_id)
        val call: Call<ItemsListModel_new> = ApiClient.getClient.get_shop_items(canteenItems,"Bearer "+token)
        call.enqueue(object : Callback<ItemsListModel_new> {
            override fun onFailure(call: Call<ItemsListModel_new>, t: Throwable) {
                progressDialogP.hide()
            }
            override fun onResponse(call: Call<ItemsListModel_new>, response: Response<ItemsListModel_new>) {
                val responsedata = response.body()
                progressDialogP.hide()
                if (responsedata!!.status==100) {

                    //bottomview.visibility=View.VISIBLE
                    cart_empty.visibility= View.GONE
                    item_list= ArrayList()
                    item_list.addAll(response.body()!!.responseArray.data)


                    for (i in item_list.indices){
                        var jId=item_list[i].id


                        var isFound:Boolean=false
                        var cartDatePos = 0
                        var cartItemPos = 0
                        if (cart_list.size > 0) {
                            for (n in cart_list.indices) {


                                if (jId.equals(cart_list.get(n).item_id.toString())) {
                                    isFound = true
                                    cartDatePos = n
                                    cartItemPos = n

                                }
                            }
                        }else{
                        }
                        if (isFound) {

                            item_list[i].quantityCart=cart_list.get(cartDatePos).quantity
                            item_list[i].cartId = cart_list.get(cartDatePos).id.toString()
                            item_list[i].isItemCart=true

                        } else
                        {

                            item_list[i].quantityCart=0
                            item_list[i].cartId =""
                            item_list[i].isItemCart=false


                        }
                    }
                    recyclerview_item.visibility= View.VISIBLE
                    recyclerview_item.layoutManager= LinearLayoutManager(nContext)
                    var itemAdapter= PreorderItemsAdapterShop_new(item_list,nContext,date_selected,cart_list,cartTotalAmount,
                        total_items,total_price,bottomview,cart_empty,progressDialogP)
                    recyclerview_item.adapter=itemAdapter
                }
                else if (response.body()!!.status==132)
                {
                    recyclerview_item.visibility= View.GONE
                    cart_empty.visibility= View.VISIBLE
                }
                else
                {
                    recyclerview_item.visibility= View.GONE
                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                }
            }

        })

    }
    private fun items_onclick(){
        item_list= ArrayList()
        progressDialogP.show()
        val token = PreferenceManager.getaccesstoken(nContext)
        var canteenItems= ShopItemsApiModel(
            PreferenceManager.getStudentID(nContext).toString(),cat_selected)
        val call: Call<ItemsListModel_new> = ApiClient.getClient.get_shop_items(canteenItems,"Bearer "+token)
        call.enqueue(object : Callback<ItemsListModel_new> {
            override fun onFailure(call: Call<ItemsListModel_new>, t: Throwable) {
                progressDialogP.hide()

            }
            override fun onResponse(call: Call<ItemsListModel_new>, response: Response<ItemsListModel_new>) {
                progressDialogP.hide()
                cart_empty.visibility= View.GONE
                recyclerview_item.visibility= View.VISIBLE
                val responsedata = response.body()
                if (responsedata!!.status==100) {
                    //  bottomview.visibility=View.VISIBLE
                    cart_empty.visibility= View.GONE

                    item_list= ArrayList()
                    item_list.addAll(response.body()!!.responseArray.data)
                    for (i in item_list.indices){
                        var jId=item_list[i].id


                        var isFound:Boolean=false
                        var cartDatePos = 0
                        var cartItemPos = 0
                        if (cart_list.size > 0) {
                            for (n in cart_list.indices) {




                                if (jId.equals(cart_list.get(n).item_id.toString())) {
                                    isFound = true
                                    cartDatePos = n
                                    cartItemPos = n
                                }


                            }
                        }else{
                        }
                        if (isFound) {

                            item_list[i].quantityCart=cart_list.get(cartDatePos).quantity
                            item_list[i].cartId = cart_list.get(cartDatePos).id.toString()
                            item_list[i].isItemCart=true

                        } else {
                            item_list[i].quantityCart=0
                            item_list[i].cartId =""
                            item_list[i].isItemCart=false


                        }
                    }
                    recyclerview_item.visibility= View.VISIBLE
                    recyclerview_item.layoutManager= LinearLayoutManager(nContext)
                    var itemAdapter= PreorderItemsAdapterShop_new(
                        item_list,
                        nContext,
                        date_selected,
                        cart_list,
                        cartTotalAmount,
                        total_items,
                        total_price,
                        bottomview,
                        cart_empty,
                        progressDialogP
                    )
                    recyclerview_item.adapter=itemAdapter


                }
                else if(response.body()!!.status==132)
                {
                    recyclerview_item.visibility= View.GONE
                    cart_empty.visibility= View.VISIBLE
                }
                else
                {
                    recyclerview_item.visibility= View.GONE
                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
                }
            }

        })
    }
    private fun getcanteen_cart(){
        cart_list= ArrayList()
        cartTotalAmount=0
        cartTotalItem=0
        progressDialogP.show()
        val token = PreferenceManager.getaccesstoken(nContext)
        var canteenCart= CanteenCartApiModel( PreferenceManager.getStudentID(nContext)!!)
        val call: Call<GetShopCartResponseModel> = ApiClient.getClient.get_shop_cart(canteenCart,"Bearer "+token)
        call.enqueue(object : Callback<GetShopCartResponseModel> {
            override fun onFailure(call: Call<GetShopCartResponseModel>, t: Throwable) {
                progressDialogP.hide()
            }
            override fun onResponse(call: Call<GetShopCartResponseModel>, response: Response<GetShopCartResponseModel>) {
                val responsedata = response.body()
                progressDialogP.hide()
                if (responsedata!!.status==100) {
                    bottomview.visibility= View.VISIBLE

                    cart_list=response!!.body()!!.responseArray.data
                    cartTotalAmount=0


                    cartTotalAmount=cartTotalAmount + responsedata.responseArray.total_amount

                    if (cartTotalAmount==0){
                        bottomview.visibility= View.GONE
                    }else{
                        bottomview.visibility= View.VISIBLE
                        cartTotalItem=0
                        for (i in cart_list.indices){


                            cartTotalItem=cartTotalItem + cart_list[i].quantity

                        }
                        total_items.setText(cartTotalItem.toString() + "Items")
                        total_price.setText(cartTotalAmount.toString() + "AED")
                    }

                }
                else
                {
                    bottomview.visibility= View.GONE
                }
//                else
//                {
//
//                    DialogFunctions.commonErrorAlertDialog(nContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), nContext)
//                }
            }

        })
    }
    fun showSuccessAlertnew(context: Context, message: String, msgHead: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.exclamationicon)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onResume() {
        if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
            item_categories()
            getcanteen_cart()
        } else {
            DialogFunctions.showInternetAlertDialog(nContext)
        }

        super.onResume()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}