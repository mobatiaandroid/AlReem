package com.nas.alreem.constants

import android.content.Context
import com.nas.alreem.R

class PreferenceManager {

    companion object{

        private val PREFSNAME = "ALREEM"


        fun setaccesstoken(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("access_token", id)
            editor.apply()
        }

        fun getaccesstoken(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("access_token", "")
        }
        fun setFirtTime(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("first", id)
            editor.apply()
        }

        fun getFirstTime(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("first", "")
        }
        fun setNoticeFirtTime(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("notice_first", id)
            editor.apply()
        }

        fun getNoticeFirstTime(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("notice_first", "")
        }
        fun setEmailId(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("email_id", id)
            editor.apply()
        }

        fun getEmailId(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("email_id", "")
        }

        /*SET STUDENT_ID*/
        fun setStudentID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("student_id", id)
            editor.apply()
        }

        /*GET STUDENT_ID*/
        fun getStudentID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("student_id", "")
        }
        /*SET STUDENT_NAME*/
        fun setStudentName(context: Context, name: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("student_name", name)
            editor.apply()
        }

        /*GET STUDENT_NAME*/
        fun getStudentName(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("student_name", "")
        }
        /*SET STUDENT_PHOTO*/
        fun setStudentPhoto(context: Context, photo: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("student_img", photo)
            editor.apply()
        }

        /*GET STUDENT_NAME*/
        fun getStudentPhoto(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("student_img", "")
        }
        /*SET STUDENT_PHOTO*/
        fun setStudentClass(context: Context, studClass: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("student_class", studClass)
            editor.apply()
        }

        /*GET STUDENT_NAME*/
        fun getStudentClass(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("student_class", "")
        }
        fun setTrnNo(context: Context, trn_no: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("trn_no", trn_no)
            editor.apply()
        }

        fun getTrnNo(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("trn_no", "")
        }
        fun setTrnPayment(context: Context, trn_pay: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("trn_pay", trn_pay)
            editor.apply()
        }

        fun getTrnPayment(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("trn_pay", "")
        }


        fun setTopUpLimit(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("top_up_limit", id)
            editor.apply()
        }


        /**
         * GET ACCESS TOKEN
         */

        fun getTopUpLimit(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("top_up_limit", "")
        }

        fun setUserEmail(context: Context, email: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("email", email)
            editor.apply()
        }


        fun getUserEmail(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("email", "")
        }
        fun setUserCode(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("user_code", id)
            editor.apply()
        }

        fun getUserCode(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("user_code", "")
        }


        /**
         * SET BUTTON ONE TAB ID
         */

        fun setbuttononetabid(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_onetabid", id)
            editor.apply()
        }

        /**
         * GET BUTTON ONE TAB ID
         */

        fun getbuttononetabid(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_onetabid", "1")
        }

        fun setbuttontwotabid(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_twotabid", id)
            editor.apply()
        }

        /**
         * GET BUTTON TWO TAB ID
         */

        fun getbuttontwotabid(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_twotabid", "2")
        }

        fun setbuttonthreetabid(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_threetabid", id)
            editor.apply()
        }

        /**
         * GET BUTTON THREE TAB ID
         */

        fun getbuttonthreetabid(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_threetabid", "3")
        }

        fun setbuttonfourtabid(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_fourtabid", id)
            editor.apply()
        }

        /**
         * GET BUTTON FOUR TAB ID
         */

        fun getbuttonfourtabid(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_fourtabid", "4")
        }

        fun setbuttonfivetabid(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_fivetabid", id)
            editor.apply()
        }

        /**
         * GET BUTTON FIVE TAB ID
         */

        fun getbuttonfivetabid(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_fivetabid", "5")
        }

        fun setbuttonsixtabid(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_sixtabid", id)
            editor.apply()
        }

        /**
         * GET BUTTON SIX TAB ID
         */

        fun getbuttonsixtabid(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_sixtabid", "6")
        }

        fun setbuttonseventabid(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_seventabid", id)
            editor.apply()
        }

        /**
         * GET BUTTON SEVEN TAB ID
         */

        fun getbuttonseventabid(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_seventabid", "7")
        }

        fun setbuttoneighttabid(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_eighttabid", id)
            editor.apply()
        }

        /**
         * GET BUTTON EIGHT TAB ID
         */

        fun getbuttoneighttabid(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_eighttabid", "8")
        }

        fun setbuttonninetabid(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_ninetabid", id)
            editor.apply()
        }

        /**
         * GET BUTTON NINE TAB ID
         */

        fun getbuttonninetabid(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_ninetabid", "9")
        }


        /**
         * SET BUTTON ONE TEXT IMAGE
         */

        fun setbuttononetextimage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_onetextimage", id)
            editor.apply()
        }

        /**
         * GET BUTTON ONE TEXT IMAGE
         */

        fun getbuttononetextimage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_onetextimage", "1")
        }

        fun setbuttontwotextimage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_twotextimage", id)
            editor.apply()
        }

        /**
         * GET BUTTON TWO TEXT IMAGE
         */

        fun getbuttontwotextimage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_twotextimage", "2")
        }

        fun setbuttonthreetextimage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_threetextimage", id)
            editor.apply()
        }

        /**
         * GET BUTTON THREE TEXT IMAGE
         */

        fun getbuttonthreetextimage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_threetextimage", "3")
        }

        fun setbuttonfourtextimage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_fourtextimage", id)
            editor.apply()
        }

        /**
         * GET BUTTON FOUR TEXT IMAGE
         */

        fun getbuttonfourtextimage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_fourtextimage", "4")
        }

        fun setbuttonfivetextimage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_fivetextimage", id)
            editor.apply()
        }

        /**
         * GET BUTTON FIVE TEXT IMAGE
         */

        fun getbuttonfivetextimage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_fivetextimage", "5")
        }

        fun setbuttonsixtextimage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_sixtextimage", id)
            editor.apply()
        }

        /**
         * GET BUTTON SIX TEXT IMAGE
         */

        fun getbuttonsixtextimage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_sixtextimage", "6")
        }

        fun setbuttonseventextimage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_seventextimage", id)
            editor.apply()
        }

        /**
         * GET BUTTON SEVEN TEXT IMAGE
         */

        fun getbuttonseventextimage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_seventextimage", "7")
        }

        fun setbuttoneighttextimage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_eighttextimage", id)
            editor.apply()
        }

        /**
         * GET BUTTON EIGHT TEXT IMAGE
         */

        fun getbuttoneighttextimage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_eighttextimage", "8")
        }

        fun setbuttonninetextimage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_ninetextimage", id)
            editor.apply()
        }

        /**
         * GET BUTTON NINE TEXT IMAGE
         */

        fun getbuttonninetextimage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_ninetextimage", "9")
        }

        fun setButtonOneGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttononeguestbg", color)
            editor.apply()
        }

        fun getButtonOneGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttononeguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtontwoGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttontwoguestbg", color)
            editor.apply()
        }

        fun getButtontwoGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttontwoguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonthreeGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonthreeguestbg", color)
            editor.apply()
        }

        fun getButtonthreeGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonthreeguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonfourGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonfourguestbg", color)
            editor.apply()
        }

        fun getButtonfourGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonfourguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonfiveGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonfiveguestbg", color)
            editor.apply()
        }

        fun getButtonfiveGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonfiveguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonsixGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonsixguestbg", color)
            editor.apply()
        }

        fun getButtonsixGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonsixguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonsevenGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonsevenguestbg", color)
            editor.apply()
        }

        fun getButtonsevenGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonsevenguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtoneightGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttoneightguestbg", color)
            editor.apply()
        }

        fun getButtoneightGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttoneightguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonnineGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonnineguestbg", color)
            editor.apply()
        }

        fun getButtonnineGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonnineguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }



        fun isCalendarFirstLaunch(context: Context, isFirstLaunch: Boolean)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("is_cal_first_launch", isFirstLaunch)
            editor.apply()
        }
        fun getIsCalendarFirstLaunch(context: Context): Boolean
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getBoolean("is_cal_first_launch", false)
        }
        fun setCalendarEventNames(context: Context, usercode: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("cal_event", usercode)
            editor.apply()
        }
        fun getCalendarEventNames(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("cal_event", "")
        }
        fun setCalendarBadge(context: Context, calendarbadge: Int)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("calendar_badge", calendarbadge)
            editor.apply()
        }
        fun getCalendarBadge(context: Context): Int
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("calendar_badge", 0)
        }
        fun setCalendarEditedBadge(context: Context, calendarbadge: Int)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("calendar_edited_badge", calendarbadge)
            editor.apply()
        }
        fun getCalendarEditedBadge(context: Context): Int
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("calendar_edited_badge", 0)
        }
    }
}