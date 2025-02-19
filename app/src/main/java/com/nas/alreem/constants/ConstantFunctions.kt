package com.nas.alreem.constants

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Debug
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.nas.alreem.R
import com.nas.alreem.activity.canteen.model.canteen_cart.CanteenCartResModel
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.Socket
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ConstantFunctions {
    companion object{

        lateinit var cart_list: ArrayList<CanteenCartResModel>
        lateinit var idList:ArrayList<Int>
        var runMethod: String = "Dev"


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
    fun isDeveloperModeEnabled(context: Context): Boolean {
        try {
            val devMode = Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED
            )
            return devMode == 1 // 1 indicates Developer Mode is ON
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
            return false // Developer Mode setting not found, assume it's off
        }
    }
  public  fun showDeviceIsDeveloperPopUp(activity: Activity) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        val icon = dialog.findViewById<ImageView>(R.id.iconImageView)
        icon.setBackgroundResource(R.drawable.round)
        icon.setImageResource(R.drawable.alert)
        val text = dialog.findViewById<TextView>(R.id.messageTxt)
        val textHead = dialog.findViewById<TextView>(R.id.alertHead)
        text.text =
            "You have enabled Developer options/USB debugging on your phone. Please disable both to use the app for security reasons."
        textHead.text = "Disable Developer Option"

        val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
        dialogButton.setOnClickListener {
            dialog.dismiss()
            activity.finish()
        }

        dialog.show()
    }
    fun isDeviceRootedOrEmulator(context: Context): Boolean {
        return (isDeviceRooted()
                || isEmulator()
                || hasEmulatorFiles()
                || isMagiskInstalled()
                || isFridaRunning()
                || isFridaLibraryLoaded()
                || isSuspiciousProcessRunning()
                || checkForRootBinaries()
                || checkForRootApps(context)
                || detectJavaDebugger()
                || detect_threadCpuTimeNanos())
    }
    fun isEmulator(): Boolean {
        val buildFingerprint = Build.FINGERPRINT
        val model = Build.MODEL
        val manufacturer = Build.MANUFACTURER
        val brand = Build.BRAND
        val device = Build.DEVICE
        val product = Build.PRODUCT

        return (buildFingerprint.startsWith("generic")
                || buildFingerprint.startsWith("unknown")
                || model.contains("google_sdk")
                || model.contains("Emulator")
                || model.contains("Android SDK built for x86")
                || manufacturer.contains("Genymotion")
                || (brand.startsWith("generic") && device.startsWith("generic"))) || product == "google_sdk"
    }

    fun isDebuggerConnected(): Boolean {
        return Debug.isDebuggerConnected()
    }

    fun hasEmulatorFiles(): Boolean {
        val paths = arrayOf(
            "/dev/qemu_pipe",
            "/dev/socket/qemud",
            "/system/lib/libc_malloc_debug_qemu.so",
            "/sys/qemu_trace",
            "/system/bin/qemu_props"
        )

        for (path in paths) {
            if (File(path).exists()) {
                return true
            }
        }
        return false
    }
    fun isDeviceRooted(): Boolean {
        val paths = arrayOf(
            "/system/xbin/su",
            "/system/bin/su",
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/.ext/.su"
        )

        for (path in paths) {
            if (File(path).exists()) {
                return true
            }
        }
        return false
    }
    fun isMagiskInstalled(): Boolean {
        val magiskPaths = arrayOf(
            "/sbin/magisk",
            "/sbin/.magisk",
            "/data/adb/magisk",
            "/data/adb/modules",
            "/cache/magisk.log",
            "/data/magisk.img"
        )

        for (path in magiskPaths) {
            if (File(path).exists()) {
                return true
            }
        }
        return false
    }

    fun isFridaRunning(): Boolean {
        try {
            // Check for Frida default port
            val socket = Socket("127.0.0.1", 27042)
            socket.close()
            return true // Frida detected
        } catch (e: java.lang.Exception) {
            return false // No Frida
        }
    }

    fun isFridaLibraryLoaded(): Boolean {
        try {
            val process = Runtime.getRuntime().exec("cat /proc/self/maps")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var maps = reader.readLine()
            while (maps != null) {
                if (maps.contains("frida") || maps.contains("gadget")) {
                    reader.close()
                    return true // Frida library detected
                }
                maps = reader.readLine()
            }
            reader.close()
        } catch (e: java.lang.Exception) {
            // Ignore exception
        }
        return false
    }

    fun isSuspiciousProcessRunning(): Boolean {
        val suspiciousProcesses = arrayOf("frida", "objection", "gadget")
        try {
            val process = Runtime.getRuntime().exec("ps")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String
            while ((reader.readLine().also { line = it }) != null) {
                for (suspicious in suspiciousProcesses) {
                    if (line.contains(suspicious!!)) {
                        reader.close()
                        return true // Suspicious process detected
                    }
                }
            }
            reader.close()
        } catch (e: java.lang.Exception) {
            // Exception ignored
        }
        return false
    }

    // New method to check for root binaries
    fun checkForRootBinaries(): Boolean {
        val rootBinariesPaths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )

        for (path in rootBinariesPaths) {
            if (File(path).exists()) {
                return true
            }
        }
        return false
    }

    // New method to check for root apps
    fun checkForRootApps(context: Context): Boolean {
        val knownRootAppsPackages = arrayOf(
            "com.noshufou.android.su",
            "eu.chainfire.supersu",
            "com.koushikdutta.superuser",
            "com.thirdparty.superuser",
            "com.yellowes.su",
            "com.topjohnwu.magisk"
        )

        val pm = context.packageManager
        for (packageName in knownRootAppsPackages) {
            try {
                pm.getPackageInfo(packageName!!, 0)
                return true
            } catch (e: PackageManager.NameNotFoundException) {
                // Continue checking
            }
        }
        return false
    }

    // New method to detect if a Java debugger is connected
    fun detectJavaDebugger(): Boolean {
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger()
    }

    // New method to detect thread CPU time for debugging
    fun detect_threadCpuTimeNanos(): Boolean {
        val start = Debug.threadCpuTimeNanos()
        for (i in 0..999999) continue
        val stop = Debug.threadCpuTimeNanos()
        return (stop - start >= 10000000)
    }
}