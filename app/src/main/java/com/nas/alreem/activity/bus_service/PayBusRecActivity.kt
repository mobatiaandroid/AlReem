package com.nas.alreem.activity.bus_service

import android.content.Context
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException

class PayBusRecActivity: AppCompatActivity() {
     var context: Context?=null
    var student_name: String? = null
    //var itemsList: ArrayList<ShopModel>? = ArrayList<ShopModel>()

    fun main() {}
    fun loadWebViewWithDataPrint(
        paymentWeb: WebView,
        br: BufferedReader,
        parent_name: String?,
        order_id: String,
        student_id: String?,
        amount: String?,
        created_on: String?,
        invoice_note: String?,
        payment_type: String?,
        student_name: String?,
        trn_no: String?,
        title: String,
        way: String?,
        pickuppoint: String?,
        droppint: String?,
        type: String?
    ) {
        var student_name = student_name

        context = context
       // itemsList = PreferenceManager.getOrderArrayList(context!!)

        student_name = student_name
        var sb = StringBuffer()
        var eachLine: String? = ""
        try {
            sb = StringBuffer()
            eachLine = br.readLine()
            while (eachLine != null) {
                sb.append(eachLine)
                sb.append("\n")
                eachLine = br.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var fullHtml = sb.toString()
        if (fullHtml.length > 0) {
            if (type.equals("EAP"))
            {
                fullHtml = fullHtml.replace("###amount###", amount!!)
                fullHtml = fullHtml.replace("###order_Id###", order_id!!)
                fullHtml = fullHtml.replace("###ParentName###", student_name!!)
                fullHtml = fullHtml.replace("###Date###", created_on!!)
                fullHtml = fullHtml.replace("###paidBy###", invoice_note!!)
                // fullHtml = fullHtml.replace("###quantity###", itemsList!!.get(0).quantity.toString())
                fullHtml = fullHtml.replace("###droptext###", droppint!!)
                fullHtml = fullHtml.replace("###drop###", "Drop Point"!!)

                fullHtml = fullHtml.replace("###trn_no###", trn_no!!)
                fullHtml = fullHtml.replace("###payment_type###", payment_type!!)
                // fullHtml = fullHtml.replace("###paidBy###", "Done");
                fullHtml = fullHtml.replace("###title###", title!!)
                paymentWeb.loadDataWithBaseURL(
                    "file:///android_asset/images/",
                    fullHtml,
                    "text/html; charset=utf-8",
                    "utf-8",
                    "about:blank"
                )
            }
            else
            {
                fullHtml = fullHtml.replace("###amount###", amount!!)
                fullHtml = fullHtml.replace("###order_Id###", order_id!!)
                fullHtml = fullHtml.replace("###ParentName###", student_name!!)
                fullHtml = fullHtml.replace("###Date###", created_on!!)
                fullHtml = fullHtml.replace("###paidBy###", invoice_note!!)
                fullHtml = fullHtml.replace("###droptext###", way!!)
                fullHtml = fullHtml.replace("###trn_no###", trn_no!!)
                fullHtml = fullHtml.replace("###payment_type###", payment_type!!)
                fullHtml = fullHtml.replace("###drop###", "Root"!!)
                // fullHtml = fullHtml.replace("###paidBy###", "Done");
                fullHtml = fullHtml.replace("###title###", title!!)
                paymentWeb.loadDataWithBaseURL(
                    "file:///android_asset/images/",
                    fullHtml,
                    "text/html; charset=utf-8",
                    "utf-8",
                    "about:blank"
                )
            }

        }
    }
}