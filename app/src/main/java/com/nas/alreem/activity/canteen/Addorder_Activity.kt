package com.nas.alreem.activity.canteen

import android.app.Activity
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.ProgressBarDialog
import com.nas.alreem.activity.canteen.adapter.DateAdapter
import com.nas.alreem.activity.canteen.adapter.ItemCategoriesAdapter
import com.nas.alreem.activity.canteen.adapter.PreorderItemsAdapter
import com.nas.alreem.activity.canteen.model.AllergyContentModel
import com.nas.alreem.activity.canteen.model.CatItemsListModel_new
import com.nas.alreem.activity.canteen.model.DateModel
import com.nas.alreem.activity.canteen.model.add_orders.CanteenItemsApiModel
import com.nas.alreem.activity.canteen.model.add_orders.CatListModel
import com.nas.alreem.activity.canteen.model.add_orders.CategoryListModel
import com.nas.alreem.activity.canteen.model.add_orders.ItemsListModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartApiModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartResModel
import com.nas.alreem.activity.canteen.model.canteen_cart.CartItemsListModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Addorder_Activity : AppCompatActivity() {
    lateinit var nContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview_category: RecyclerView
    lateinit var recyclerview_date: RecyclerView
    lateinit var back: ImageView
    lateinit var cart_list: ArrayList<CanteenCartResModel>
    lateinit var cart_items_list: ArrayList<CartItemsListModel>
    private var id: String? = null
    lateinit var date_title: TextView
    lateinit var date_list: ArrayList<DateModel>
    lateinit var recyclerview_item: RecyclerView

    //lateinit var selected:ImageView
    // lateinit var progress: RelativeLayout
    lateinit var title: TextView
    lateinit var cart_empty: ImageView

    // lateinit var progressDialog: ProgressBar
    lateinit var progressDialogP: ProgressBarDialog
    lateinit var category_list: ArrayList<CategoryListModel>
    lateinit var item_list: ArrayList<CatItemsListModel_new>
    lateinit var allergycontentlist: ArrayList<AllergyContentModel>

    lateinit var bottomview: LinearLayout
    lateinit var basketbtn: LinearLayout

    var cat_selected: String = ""
    var date_selected: String = ""
    var def_cat_id: String = ""
    var date_string: String = ""
    var apiCall: Int = 0
    var cartTotalAmount: Int = 0
    var cartTotalItem: Int = 0
    var studentAllergy=0

    lateinit var total_items: TextView
    lateinit var total_price: TextView
    var firstVisit: Boolean? = null
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canteen_addorder)
        firstVisit = true
        initfn()
        setdate()
        //progressDialog.visibility=View.VISIBLE
        if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
            item_categories()
            getcanteen_cart()
        } else {
            DialogFunctions.showInternetAlertDialog(nContext)
        }


    }

    private fun initfn() {
        nContext = this
        activity = this
        back = findViewById(R.id.back)
        logoClickImg = findViewById(R.id.logoclick)
        title = findViewById(R.id.titleTextView)
        id = PreferenceManager.getStudentID(nContext).toString()
        date_title = findViewById(R.id.date_title)
        // progress=findViewById(R.id.progressDialog)
        title.text = "Pre-Order"
        cart_empty = findViewById(R.id.item_empty)
        // progressDialog = findViewById(R.id.progressDialogM)
        progressDialogP = ProgressBarDialog(nContext)

        //   val aniRotate: Animation =
        //  AnimationUtils.loadAnimation(nContext, R.anim.linear_interpolator)
        // progress.startAnimation(aniRotate)
        category_list = ArrayList()
        cart_list = ArrayList()
        cart_items_list = ArrayList()
        date_list = intent.getSerializableExtra("date_list") as ArrayList<DateModel>
        //date_list=PreferenceManager().getdate_list(nContext)
        var year = date_list[0].year
        var strCurrentDate = ""
        var format = SimpleDateFormat("MMM", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date_list[0].month)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("MM", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        var month = strCurrentDate
        //var month = CommonMethods.dateParsingTommm(date_list[0].month)
        var date = date_list[0].numberDate
        date_string = date_list[0].numberDate
        date_selected = date_string.toString()
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
            val intent = Intent(nContext, Myorderbasket_Activity::class.java)
            intent.putExtra("date", date_selected)
            finish()
            nContext.startActivity(intent)
        }

        bottomview.setOnClickListener(View.OnClickListener {
            val intent = Intent(nContext, Myorderbasket_Activity::class.java)
            intent.putExtra("date", date_selected)
            finish()
            nContext.startActivity(intent)
        })

    }

    private fun setdate() {
        var one_date =
            date_list[0].day + "," + date_list[0].date + " " + date_list[0].month + " " + date_list[0].year
        if (date_list.size == 1) {
            date_title.visibility = View.VISIBLE
            date_title.text = one_date
        } else {
            var first_date = date_list[0].numberDate
            date_title.visibility = View.GONE
            for (i in date_list.indices) {
                date_list.get(i).isDateSelected = date_list.get(i).numberDate.equals(first_date)
            }
            recyclerview_date.visibility = View.VISIBLE
            var llm2 = (LinearLayoutManager(nContext))
            llm2.orientation = LinearLayoutManager.HORIZONTAL
            recyclerview_date.layoutManager = llm2
            recyclerview_date.adapter = DateAdapter(date_list, nContext)

            recyclerview_date.addOnItemClickListener(object : OnItemClickListener {
                override fun onItemClicked(position: Int, view: View) {
                    date_list.get(0).isDateSelected = false
                    date_selected = date_list[position].numberDate
                    var foundPosition = -1
                    var isFound: Boolean = false


                    for (i in 0..date_list.size - 1) {
                        if (date_list.get(i).isDateSelected) {
                            foundPosition = i
                            isFound = true
                            date_selected = date_list.get(position).numberDate
                            items_onclick()
                        }
                    }
                    if (isFound) {
                        if (position == foundPosition) {
                            date_list.get(foundPosition).isDateSelected = true
                            recyclerview_date.adapter = DateAdapter(date_list, nContext)
                        } else {
                            date_list.get(foundPosition).isDateSelected = false
                            date_list.get(position).isDateSelected = true
                            date_selected = date_list.get(position).numberDate
                            items_onclick()
                            recyclerview_date.adapter = DateAdapter(date_list, nContext)
                        }
                    } else {
                        date_list.get(position).isDateSelected = true
                        date_selected = date_list.get(position).numberDate
                        items_onclick()
                        recyclerview_date.adapter = DateAdapter(date_list, nContext)
                    }
                    //items_onclick()
                }
            })
        }
    }


    private fun item_categories() {
        category_list = ArrayList()
        progressDialogP.show()

        val token = PreferenceManager.getaccesstoken(nContext)
        val call: Call<CatListModel> =
            ApiClient(nContext).getClient.get_canteen_categories("Bearer " + token)
        call.enqueue(object : Callback<CatListModel> {
            override fun onFailure(call: Call<CatListModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<CatListModel>, response: Response<CatListModel>) {
                val responsedata = response.body()
                progressDialogP.hide()
                if (responsedata!!.status == 100) {
                    category_list = ArrayList()


                    category_list.addAll(response.body()!!.responseArray.data)
                    def_cat_id = category_list[0].id
                    cat_selected = def_cat_id
                    for (i in category_list.indices) {
                        category_list.get(i).isItemSelected =
                            category_list.get(i).id.equals(def_cat_id)
                    }
                    var llm = (LinearLayoutManager(nContext))
                    llm.orientation = LinearLayoutManager.HORIZONTAL
                    recyclerview_category.layoutManager = llm
                    recyclerview_category.setItemViewCacheSize(category_list.size);

                    recyclerview_category.adapter = ItemCategoriesAdapter(category_list, nContext)
                    // progressDialogP.visibility=View.VISIBLE
                    items()


                } else {

                    DialogFunctions.commonErrorAlertDialog(
                        nContext.resources.getString(R.string.alert),
                        ConstantFunctions.commonErrorString(response.body()!!.status),
                        nContext
                    )
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
                        cat_selected = category_list.get(position).id
                        items_onclick()
                    }
                }
                if (isFound) {
                    if (position == foundPosition) {
                        category_list.get(foundPosition).isItemSelected = true
                        recyclerview_category.setItemViewCacheSize(category_list.size);

                        recyclerview_category.adapter =
                            ItemCategoriesAdapter(category_list, nContext)
                    } else {
                        category_list.get(foundPosition).isItemSelected = false
                        category_list.get(position).isItemSelected = true
                        cat_selected = category_list.get(position).id
                        recyclerview_category.setItemViewCacheSize(category_list.size);

                        recyclerview_category.adapter =
                            ItemCategoriesAdapter(category_list, nContext)
                        items_onclick()
                    }
                } else {
                    category_list.get(position).isItemSelected = true
                    cat_selected = category_list.get(position).id
                    recyclerview_category.setItemViewCacheSize(category_list.size);

                    recyclerview_category.adapter = ItemCategoriesAdapter(category_list, nContext)
                    items_onclick()
                }
                // items_onclick()
                recyclerview_category.scrollToPosition(position)
            }
        })

    }

    private fun items() {
        item_list = ArrayList()
        progressDialogP.show()

        val token = PreferenceManager.getaccesstoken(nContext)
        var canteenItems = CanteenItemsApiModel(
            PreferenceManager.getStudentID(nContext).toString(), def_cat_id,
            date_selected, "0", "50"
        )
        val call: Call<ItemsListModel> =
            ApiClient(nContext).getClient.get_canteen_items(canteenItems, "Bearer " + token)
        call.enqueue(object : Callback<ItemsListModel> {
            override fun onFailure(call: Call<ItemsListModel>, t: Throwable) {
                progressDialogP.hide()
            }

            override fun onResponse(
                call: Call<ItemsListModel>,
                response: Response<ItemsListModel>
            ) {
                val responsedata = response.body()
                progressDialogP.hide()
                if (responsedata!!.status == 100) {

                    //bottomview.visibility=View.VISIBLE
                    cart_empty.visibility = View.GONE
                    item_list = ArrayList()
                    item_list.addAll(response.body()!!.responseArray.data)

                    for (i in item_list.indices) {
                        var jId = item_list[i].id


                        var isFound: Boolean = false
                        var cartDatePos = 0
                        var cartItemPos = 0
                        /*if (cart_list.size > 0) {
                            for (n in cart_list.indices) {
                                if (cart_list.get(n).delivery_date.equals(date_selected)) {

                                    for (m in 0 until cart_list.get(n).items.size) {


                                        if (jId.equals(cart_list.get(n).items.get(m).item_id.toString())) {
                                            isFound = true
                                            cartDatePos = n
                                            cartItemPos = m
                                        }
                                    }
                                }
                            }
                        } else {
                        }
                        if (isFound) {

                            item_list[i].quantityCart =
                                cart_list.get(cartDatePos).items.get(cartItemPos).quantity
                            item_list[i].cartId =
                                cart_list.get(cartDatePos).items.get(cartItemPos).id.toString()
                            item_list[i].isItemCart = true

                        } else {

                            item_list[i].quantityCart = 0
                            item_list[i].cartId = ""
                            item_list[i].isItemCart = false


                        }*/
                        allergycontentlist = java.util.ArrayList()

                        allergycontentlist.addAll(item_list.get(i).allergy_contents)
                        studentAllergy=item_list.get(i).student_allergy
                       // Log.e("studentAllergy", studentAllergy.toString())

                        if (allergycontentlist.size > 0) {
                            item_list.get(i).isAllergic = true
                        } else {
                            item_list.get(i).isAllergic = false
                        }

                    }


                    recyclerview_item.visibility = View.VISIBLE
                    recyclerview_item.layoutManager = LinearLayoutManager(nContext)
                    var itemAdapter = PreorderItemsAdapter(
                        item_list,
                        nContext,
                        date_selected,
                        cart_list,
                        cartTotalAmount,
                        total_items,
                        total_price,
                        bottomview,
                        cart_empty,
                        progressDialogP,
                        allergycontentlist
                    )
                    recyclerview_item.adapter = itemAdapter
                } else if (response.body()!!.status == 132) {
                    recyclerview_item.visibility = View.GONE
                    cart_empty.visibility = View.VISIBLE
                } else {
                    recyclerview_item.visibility = View.GONE
                    DialogFunctions.commonErrorAlertDialog(
                        nContext.resources.getString(R.string.alert),
                        ConstantFunctions.commonErrorString(response.body()!!.status),
                        nContext
                    )
                }
            }

        })

    }

    private fun items_onclick() {
        item_list = ArrayList()
        progressDialogP.show()
        val token = PreferenceManager.getaccesstoken(nContext)
        var canteenItems = CanteenItemsApiModel(
            PreferenceManager.getStudentID(nContext).toString(), cat_selected,
            date_selected, "0", "200"
        )
        val call: Call<ItemsListModel> =
            ApiClient(nContext).getClient.get_canteen_items(canteenItems, "Bearer " + token)
        call.enqueue(object : Callback<ItemsListModel> {
            override fun onFailure(call: Call<ItemsListModel>, t: Throwable) {
                progressDialogP.hide()

            }

            override fun onResponse(
                call: Call<ItemsListModel>,
                response: Response<ItemsListModel>
            ) {
                progressDialogP.hide()
                cart_empty.visibility = View.GONE
                recyclerview_item.visibility = View.VISIBLE
                val responsedata = response.body()
                if (responsedata!!.status == 100) {
                    //  bottomview.visibility=View.VISIBLE
                    cart_empty.visibility = View.GONE

                    item_list = ArrayList()
                    item_list.addAll(response.body()!!.responseArray.data)
                    for (i in item_list.indices) {
                        var jId = item_list[i].id


                        var isFound: Boolean = false
                        var cartDatePos = 0
                        var cartItemPos = 0
                        if (cart_list.size > 0) {
                            for (n in cart_list.indices) {

                                if (cart_list.get(n).delivery_date.equals(date_selected)) {
                                    for (m in 0 until cart_list.get(n).items.size) {


                                        if (jId.equals(cart_list.get(n).items.get(m).item_id.toString())) {
                                            isFound = true
                                            cartDatePos = n
                                            cartItemPos = m
                                        }
                                    }
                                }
                            }
                        } else {
                        }
                        if (isFound) {

                            item_list[i].quantityCart =
                                cart_list.get(cartDatePos).items.get(cartItemPos).quantity
                            item_list[i].cartId =
                                cart_list.get(cartDatePos).items.get(cartItemPos).id.toString()
                            item_list[i].isItemCart = true

                        } else {
                            item_list[i].quantityCart = 0
                            item_list[i].cartId = ""
                            item_list[i].isItemCart = false


                        }

                        allergycontentlist = java.util.ArrayList()
                        allergycontentlist.addAll(item_list.get(i).allergy_contents)
                        studentAllergy=item_list.get(i).student_allergy

                        if (allergycontentlist.size > 0) {
                            item_list.get(i).isAllergic = true
                        } else {
                            item_list.get(i).isAllergic = false
                        }
                       // Log.e("itemlist", item_list.get(i).allergy_contents.size.toString())
                    }


                    recyclerview_item.visibility = View.VISIBLE
                    recyclerview_item.layoutManager = LinearLayoutManager(nContext)
                    var itemAdapter = PreorderItemsAdapter(
                        item_list,
                        nContext,
                        date_selected,
                        cart_list,
                        cartTotalAmount,
                        total_items,
                        total_price,
                        bottomview,
                        cart_empty,
                        progressDialogP,
                        allergycontentlist
                    )
                    recyclerview_item.adapter = itemAdapter


                } else if (response.body()!!.status == 132) {
                    recyclerview_item.visibility = View.GONE
                    cart_empty.visibility = View.VISIBLE
                } else {
                    recyclerview_item.visibility = View.GONE
                    DialogFunctions.commonErrorAlertDialog(
                        nContext.resources.getString(R.string.alert),
                        ConstantFunctions.commonErrorString(response.body()!!.status),
                        nContext
                    )
                }
            }

        })
    }

    private fun getcanteen_cart() {
        cart_list = ArrayList()
        cartTotalAmount = 0
        cartTotalItem = 0
        progressDialogP.show()
        val token = PreferenceManager.getaccesstoken(nContext)
        var canteenCart = CanteenCartApiModel(PreferenceManager.getStudentID(nContext).toString())
        val call: Call<CanteenCartModel> =
            ApiClient(nContext).getClient.get_canteen_cart(canteenCart, "Bearer " + token)
        call.enqueue(object : Callback<CanteenCartModel> {
            override fun onFailure(call: Call<CanteenCartModel>, t: Throwable) {
                progressDialogP.hide()
            }

            override fun onResponse(
                call: Call<CanteenCartModel>,
                response: Response<CanteenCartModel>
            ) {
                val responsedata = response.body()
                progressDialogP.hide()
                if (responsedata!!.status == 100) {
                    bottomview.visibility = View.VISIBLE

                    cart_list = response!!.body()!!.responseArray.data
                    cartTotalAmount = 0
                    for (i in cart_list.indices) {

                        cartTotalAmount = cartTotalAmount + cart_list[i].total_amount
                    }
                    if (cartTotalAmount == 0) {
                        bottomview.visibility = View.GONE
                    } else {
                        bottomview.visibility = View.VISIBLE
                        cartTotalItem = 0
                        for (i in cart_list.indices) {

                            for (j in cart_list[i].items.indices) {
                                cartTotalItem = cartTotalItem + cart_list[i].items[j].quantity
                            }
                        }
                        total_items.setText(cartTotalItem.toString() + "Items")
                        total_price.setText(cartTotalAmount.toString() + "AED")
                    }

                } else {
                    bottomview.visibility = View.GONE
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
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(nContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
        if (ConstantFunctions.internetCheck(nContext)) {
//            progressDialog.visibility= View.VISIBLE
            item_categories()
            getcanteen_cart()
        } else {
            DialogFunctions.showInternetAlertDialog(nContext)
        }

        super.onResume()
    }

}
