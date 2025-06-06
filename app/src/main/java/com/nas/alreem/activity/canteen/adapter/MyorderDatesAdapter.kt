package com.nas.alreem.activity.canteen.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.model.myorders.CancelCanteenPreOrderApiModel
import com.nas.alreem.activity.canteen.model.myorders.PreOrdersListModel
import com.nas.alreem.activity.canteen.model.myorders.PreOrdersModel
import com.nas.alreem.activity.canteen.model.order_history.OrderHistoryApiModel
import com.nas.alreem.activity.canteen.model.preorder.CanteenPreorderModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyorderDatesAdapter(
    val preorders_list: ArrayList<PreOrdersListModel>,
    var mcontext: Context,
    var studentID: String,
    var dateRecyclerView: RecyclerView,
    var bottom: LinearLayout,
    var item: LinearLayout,
    var noitem: ImageView,
    var progress: ProgressBar,
    var amountTxt: TextView,
    var totalAmount: String
) :
    RecyclerView.Adapter<MyorderDatesAdapter.ViewHolder>() {
   lateinit var mAdapter: MyorderItemsAdapter
    var ordered_user_type = ""
    var student_id = ""
    var parent_id = ""
    var staff_id = ""
    var WalletAmount = 0
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.myorder_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       var itemlist=preorders_list[position].canteen_preordered_items

        if (preorders_list[position].status.equals("0")) {
            holder.totalAmountTxt.visibility = View.GONE
        } else {
            holder.totalAmountTxt.visibility = View.VISIBLE
            holder.totalAmountTxt.text = "Total     " + preorders_list[position].total_amount.toString() + "AED"
        }

        if (preorders_list[position].cancellation_time_exceed.equals("0"))
        {
            if (preorders_list[position].status.equals("0")) {
                holder.closeImg.visibility = View.GONE
            } else {
                holder.closeImg.visibility = View.VISIBLE
            }
        } else {
            holder.closeImg.visibility = View.GONE
        }
        holder.itemDateTxt.text = ConstantFunctions.dateParsingToddMMMyyyyBasket(preorders_list[position].delivery_date)
        holder.cartItemRecycler.layoutManager=LinearLayoutManager(mcontext)
        holder.cartItemRecycler.adapter = MyorderItemsAdapter(itemlist , mcontext,
            ordered_user_type,
            studentID,
            parent_id,
            staff_id,
            totalAmount,
            WalletAmount,dateRecyclerView,bottom,item,noitem,progress,amountTxt,preorders_list[position].total_amount)


        holder.closeImg.setOnClickListener {
            showDialogAlertLogout(
                mcontext,
                "Confirm",
                "Do you want to cancel the order?",
                R.drawable.questionmark_icon,
                R.drawable.round,
                preorders_list[position].id
            )
        }
    }

    override fun getItemCount(): Int {
        return preorders_list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemDateTxt: TextView
        var totalAmountTxt:TextView
        var pickUpLocation:TextView
        var closeImg: ImageView
        var cartItemRecycler: RecyclerView



        init {
            cartItemRecycler = itemView.findViewById(R.id.cartItemRecycler)
            itemDateTxt = itemView.findViewById(R.id.itemDateTxt)
            totalAmountTxt = itemView.findViewById(R.id.totalAmountTxt)
            pickUpLocation = itemView.findViewById(R.id.pickUpLocation)
            closeImg = itemView.findViewById(R.id.imgClose)
        }
    }

    fun showDialogAlertLogout(
        activity: Context?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int,
        id: String?
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
           //cart cancel

            callCancelOrder(id)
            dialog.dismiss()
        }


        val dialogButtonCancel = dialog.findViewById<View>(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun callCancelOrder(order_id:String?)
    {
        val token = PreferenceManager.getaccesstoken(mcontext)
        var model= CancelCanteenPreOrderApiModel(studentID,order_id.toString())
        val call: Call<CanteenPreorderModel> = ApiClient(mcontext).getClient.cancelCanteenPreOrder(model,"Bearer "+token)
        call.enqueue(object : Callback<CanteenPreorderModel> {
            override fun onFailure(call: Call<CanteenPreorderModel>, t: Throwable) {
            }
            override fun onResponse(call: Call<CanteenPreorderModel>, response: Response<CanteenPreorderModel>) {
                val responsedata = response.body()
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


        val token = PreferenceManager.getaccesstoken(mcontext)
        var model= OrderHistoryApiModel(studentID,"0","100")
        val call: Call<PreOrdersModel> = ApiClient(mcontext).getClient.canteen_myorder_history(model,"Bearer "+token)
        call.enqueue(object : Callback<PreOrdersModel> {
            override fun onFailure(call: Call<PreOrdersModel>, t: Throwable) {

            }
            override fun onResponse(call: Call<PreOrdersModel>, response: Response<PreOrdersModel>) {
                val responsedata = response.body()

                if (responsedata!!.status==100) {
                    amountTxt.text=response.body()!!.responseArray.whole_total.toString()
                    dateRecyclerView.layoutManager = LinearLayoutManager(mcontext)
                    dateRecyclerView.adapter = MyorderDatesAdapter(
                        response.body()!!.responseArray.data,
                        mcontext,
                        studentID,
                        dateRecyclerView,
                        bottom,
                        item,
                        noitem,
                        progress,
                        amountTxt,
                        totalAmount
                    )

                }
                else if (response.body()!!.status==132)
                {
                    bottom.visibility= View.GONE
                    item.visibility= View.GONE
                    dateRecyclerView.visibility=View.GONE
                    noitem.visibility=View.VISIBLE
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