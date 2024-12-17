package com.nas.alreem.constants

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartResModel
import com.nas.alreem.activity.canteen.model.canteen_cart.ItemsModel
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ConstantFunctions {
    companion object{

        lateinit var cart_list: ArrayList<CanteenCartResModel>
        lateinit var idList:ArrayList<Int>


        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun showEmailView(email:String,context: Context,helpButton: Button)
        {
            val intent = Intent(Intent.ACTION_SEND)
            val recipients = arrayOf("It.help@nasabudhabi.ae")
            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.type = "text/html"
            intent.setPackage("com.google.android.gm")
            context.startActivity(Intent.createChooser(intent, "Send mail"))
        }


        fun internetCheck(context:Context):Boolean
        {
            var result = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result
        }

        fun commonErrorString(status:Int):String
        {
            var msg:String=""

            if(status==101)
            {
                msg=ConstantWords.status_101
            }
            else if (status==102)
            {
                msg=ConstantWords.status_102
            }
            else if (status==103)
            {
                msg=ConstantWords.status_103
            }
            else if (status==110)
            {
                msg=ConstantWords.status_110
            }
            else if (status==113)
            {
                msg=ConstantWords.status_113
            }
            else if (status==141)
            {
                msg=ConstantWords.status_141
            }
            else if (status==114)
            {
                msg=ConstantWords.status_114
            } else if (status==116)
            {
                msg=ConstantWords.status_116
            } else if (status==123)
            {
                msg=ConstantWords.status_123
            }
            else if (status==124)
            {
                msg=ConstantWords.status_124
            }
             else if (status==125)
            {
                msg=ConstantWords.status_125
            }
            else if (status==118)
            {
                msg=ConstantWords.status_118
            }
             else if (status==130)
            {
                msg=ConstantWords.status_130
            }
             else if (status==131)
            {
                msg=ConstantWords.status_131
            } else if (status==132)
            {
                msg=ConstantWords.status_132
            } else if (status==133)
            {
                msg=ConstantWords.status_133
            } else if (status==134)
            {
                msg=ConstantWords.status_134
            } else if (status==135)
            {
                msg=ConstantWords.status_135
            }
            else if(status==121)
            {
                msg=ConstantWords.status_121
            }
            else if(status==510)
            {
                msg=ConstantWords.status_510
            }
            return msg
        }


        fun showDialogueWithOk(context: Context, message: String, msgHead: String)
        {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_common_error_alert)
            var iconImageView = dialog.findViewById(R.id.iconImageView) as? ImageView
            var alertHead = dialog.findViewById(R.id.alertHead) as? TextView
            var text_dialog = dialog.findViewById(R.id.messageTxt) as? TextView
            var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
            text_dialog?.text = message
            alertHead?.text = msgHead
            btn_Ok.setOnClickListener()
            {
                dialog.dismiss()
            }
            dialog.show()
        }
        fun htmlparsing(text: String): String? {
            var encodedString: String = text.replace("&lt;".toRegex(), "<")
            encodedString = encodedString.replace("&gt;".toRegex(), ">")
            encodedString = encodedString.replace("&amp;".toRegex(), "")
            encodedString = encodedString.replace("amp;".toRegex(), "")
            return encodedString
        }

        fun dateParsingToddMMMyyyyBasket(date: String?): String? {
            var strCurrentDate = ""
            var format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            strCurrentDate = format.format(newDate)
            return strCurrentDate
        }
        fun replace(str: String): String? {
            return str.replace(" ".toRegex(), "%20")
        }
    }

    fun replaceamdot(str: String): String? {
        return str.replace("a.m.".toRegex(), " ")
    }
    fun replacepmdot(str: String): String? {
        return str.replace("p.m.".toRegex(), " ")
    }
    fun dateConversionYY(inputDate: String?): String? {
        var mDate = ""
        try {
            val date: Date
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            date = formatter.parse(inputDate)
            //Subtracting 6 hours from selected time
            val time = date.time

            //SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMMM yyyy");
            val formatterFullDate = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            mDate = formatterFullDate.format(time)
        } catch (e: Exception) {
//			Log.d("Exception", "" + e);
        }
        return mDate
    }
    fun dateConversionYYY(inputDate: String?): String? {
        var mDate = ""
        try {
            val date: Date
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            date = formatter.parse(inputDate)
            //Subtracting 6 hours from selected time
            val time = date.time

            //SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMMM yyyy");
            val formatterFullDate = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            mDate = formatterFullDate.format(time)
        } catch (e: Exception) {
//			Log.d("Exception", "" + e);
        }
        return mDate
    }
    fun dateParsingTodd_MMM_yyyy(date: String?): String? {
        var strCurrentDate = ""
        var format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
        strCurrentDate = format.format(newDate)
        return strCurrentDate
    }
    fun dateConversionddmmyyyy(inputDate: String?): String {
        var mDate = ""
        try {
            val date: Date
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            date = formatter.parse(inputDate)
            //Subtracting 6 hours from selected time
            val time = date.time

            //SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMMM yyyy");
            val formatterFullDate = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
            mDate = formatterFullDate.format(time)
        } catch (e: Exception) {
//			Log.d("Exception", "" + e);
        }
        return mDate
    }

    fun showDialogAlertDismiss(
        activity: Activity?,
        msgHead: String?,
        msg: String?,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<View>(R.id.messageTxt) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener { dialog.dismiss() }
        //		Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_Cancel);
//		dialogButtonCancel.setVisibility(View.GONE);
//		dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
        dialog.show()
    }
}