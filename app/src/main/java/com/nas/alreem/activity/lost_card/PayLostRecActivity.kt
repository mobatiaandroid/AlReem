package com.nas.alreem.activity.lost_card

import android.app.Activity
import android.content.Context
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.constants.ConstantFunctions
import java.io.BufferedReader
import java.io.IOException

class PayLostRecActivity :AppCompatActivity() {
    lateinit var context: Context
    var student_name: String? = null
    lateinit var activity: Activity

    fun main() {}
    fun loadWebViewWithDataPrint(
        paymentWeb: WebView, br: BufferedReader,
        parent_name: String?, email: String?, id: String?,
        student_id: String?, user_id: String?, amount: String?, bill_no: String?,
        order_reference: String?, created_on: String?,
        invoice_note: String?, payment_type: String?,
        student_name: String?, trn_no: String?
    ) {
        var student_name = student_name
        context = context
        activity=this
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
            fullHtml = fullHtml.replace("###amount###", amount!!)
            fullHtml = fullHtml.replace("###order_Id###", order_reference!!)
            fullHtml = fullHtml.replace("###ParentName###", student_name!!)
            fullHtml = fullHtml.replace("###Date###", created_on!!)
            fullHtml = fullHtml.replace("###paidBy###", invoice_note!!)
            fullHtml = fullHtml.replace("###billing_code###", bill_no!!)
            fullHtml = fullHtml.replace("###trn_no###", trn_no!!)
            fullHtml = fullHtml.replace("###payment_type###", payment_type!!)
            // fullHtml = fullHtml.replace("###paidBy###", "Done");
            fullHtml = fullHtml.replace("###title###", "Lost ID Card Payment")
            paymentWeb.loadDataWithBaseURL(
                "file:///android_asset/images/",
                fullHtml,
                "text/html; charset=utf-8",
                "utf-8",
                "about:blank"
            )
        }
    }
    override fun onResume() {
        super.onResume()
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(context)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
    }
}