package com.nas.alreem.constants

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Button
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ConstantFunctions {
    companion object{

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

            return msg
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
            Log.e("Date converted",strCurrentDate)
            return strCurrentDate
        }
        fun replace(str: String): String? {
            return str.replace(" ".toRegex(), "%20")
        }
    }
}