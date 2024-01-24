package com.nas.alreem.constants

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nas.alreem.R
import com.nas.alreem.activity.cca.model.CCADetailModel
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.shop_new.model.ShopModel
import com.nas.alreem.fragment.about_us.model.AboutusList
import java.lang.reflect.Type

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

        fun getIsEnrollmentHomeVisible(context: Context): Boolean {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getBoolean("is_enrollment_visible", false)
        }

        fun setIsEnrollmentHomeVisible(context: Context, result: Boolean) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putBoolean("is_enrollment_visible", result)
            editor.commit()
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

        fun setSurvey(context: Context, survey: Int) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("survey", survey)
            editor.commit()
        }

        fun getSurvey(context: Context): Int {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("survey", 0)
        }
        fun setIsSurveyHomeVisible(context: Context, result: Boolean) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("is_survey_visible", result)
            editor.commit()
        }

        fun getIsSurveyHomeVisible(context: Context): Boolean {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getBoolean("is_survey_visible", false)
        }


        fun setCCAStudentIdPosition(context: Context, result: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("CCAStudentIdPosition", result)
            editor.commit()
        }

        fun getCCAStudentIdPosition(context: Context): String? {
            var CCAStudentIdPosition = ""
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            CCAStudentIdPosition = prefs.getString("CCAStudentIdPosition", "").toString()
            return CCAStudentIdPosition
        }

        fun setStudClassForCCA(context: Context, result: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("StudClassForCCA", result)
            editor.commit()
        }

        fun getStudClassForCCA(context: Context): String? {
            var StudClassForCCA = ""
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            StudClassForCCA = prefs.getString("StudClassForCCA", "").toString()
            return StudClassForCCA
        }

        fun setStudNameForCCA(context: Context, result: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("StudNameForCCA", result)
            editor.commit()
        }

        fun getStudNameForCCA(context: Context): String? {
            var StudNameForCCA = ""
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            StudNameForCCA = prefs.getString("StudNameForCCA", "").toString()
            return StudNameForCCA
        }

        fun setCCATitle(context: Context, result: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("CCATitle", result)
            editor.commit()
        }

        fun getCCATitle(context: Context): String? {
            var CCATitle = ""
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            CCATitle = prefs.getString("CCATitle", "").toString()
            return CCATitle
        }
        fun setCCAItemId(context: Context, result: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("CCAItemId", result)
            editor.commit()
        }

        fun getCCAItemId(context: Context): String? {
            var CCAItemId = ""
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            CCAItemId = prefs.getString("CCAItemId", "").toString()
            return CCAItemId
        }

        fun setStudIdForCCA(context: Context, result: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("StudIdForCCA", result)
            editor.commit()
        }

        //getStudIdForCCA
        fun getStudIdForCCA(context: Context): String? {
            var StudIdForCCA = ""
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            StudIdForCCA = prefs.getString("StudIdForCCA", "").toString()
            return StudIdForCCA
        }

        fun saveDetailsArrayList(context: Context, list: ArrayList<CCADetailModel>?) {
            val prefs = context.getSharedPreferences(
                "ALGUBRA",
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            val gson = Gson()
            val json = gson.toJson(list)
            editor.putString("list", json)
            editor.apply()
        }

        fun getDetailsArrayList(context: Context): ArrayList<CCADetailModel>? {
            val prefs = context.getSharedPreferences(
                "ALGUBRA",
                Context.MODE_PRIVATE
            )
            val gson = Gson()
            val json = prefs.getString("list", null)
            val type: Type = object : TypeToken<ArrayList<CCADetailModel>?>() {}.getType()
            return gson.fromJson(json, type)
        }

        fun setCcaOptionBadge(context: Context, cca_option_badge: Int) {
            val prefs = context.getSharedPreferences(
                "ALGUBRA",
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("cca_option_badge", cca_option_badge)
            editor.commit()
        }

        fun getCcaOptionBadge(context: Context): Int {
            var cca_option_badge = 0
            val prefs = context.getSharedPreferences(
                "ALGUBRA",
                Context.MODE_PRIVATE
            )
            cca_option_badge = prefs.getInt("cca_option_badge", 0)
            return cca_option_badge
        }

        fun setCcaOptionEditedBadge(context: Context, cca_option_edited_badge: Int) {
            val prefs = context.getSharedPreferences(
                "ALGUBRA",
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("cca_option_edited_badge", cca_option_edited_badge)
            editor.commit()
        }

        fun getCcaOptionEditedBadge(context: Context): Int{
            var cca_option_edited_badge = 0
            val prefs = context.getSharedPreferences(
                "ALGUBRA",
                Context.MODE_PRIVATE)
            cca_option_edited_badge = prefs.getInt("cca_option_edited_badge", 0)
            return cca_option_edited_badge
        }

        fun setIsFirstTimeInPE(context: Context, result: Boolean) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putBoolean("is_first_pe", result)
            editor.apply()
        }
        fun getIsFirstTimeInPE(context: Context): Boolean {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getBoolean("is_first_pe", true)
        }
        fun setstaffId(context: Context, staffId: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("staffId", staffId)
            editor.commit()
        }

        fun getstaffId(context: Context): String? {
            var staffId = ""
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            staffId = prefs.getString("staffId", "").toString()
            return staffId
        }

        fun setkeyvalue(context: Context, keyvalu: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("keyval", keyvalu)
            editor.commit()
        }

        fun getkeyvalue(context: Context): String? {
            var keyvalu = ""
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            keyvalu = prefs.getString("keyval", "").toString()
            return keyvalu
        }
        fun setdetailvalue(context: Context, detailval: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("detailval", detailval)
            editor.commit()
        }

        fun getdetailvalue(context: Context): String? {
            var detailval = ""
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            detailval = prefs.getString("detailval", "").toString()
            return detailval
        }
        fun saveAboutsArrayList(context: Context, list: ArrayList<AboutusList>?) {
            val prefs = context.getSharedPreferences(
                "ALGUBRA",
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            val gson = Gson()
            val json = gson.toJson(list)
            editor.putString("about_us_list", json)
            editor.apply()
        }

        fun getAboutsArrayList(context: Context): ArrayList<AboutusList>? {
            val prefs = context.getSharedPreferences(
                "ALGUBRA",
                Context.MODE_PRIVATE
            )
            val gson = Gson()
            val json = prefs.getString("about_us_list", null)
            val type: Type = object : TypeToken<ArrayList<AboutusList>?>() {}.getType()
            return gson.fromJson(json, type)
        }
        fun setStudentArrayListModel(
            list: ArrayList<StudentList>?,
            context: Context
        ) {
            val prefs = context.getSharedPreferences(PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            val gson = Gson()
            val json = gson.toJson(list)
            editor.putString("student_list_model", json)
            editor.apply() // This line is IMPORTANT !!!
        }

        fun getStudentArrayList(context: Context): java.util.ArrayList<StudentList>? {
            val prefs = context.getSharedPreferences(
                PREFSNAME ,
                Context.MODE_PRIVATE
            )
            val gson = Gson()
            val json = prefs.getString("student_list", null)
            val type = object : TypeToken<ArrayList<StudentList>?>() {}.type
            return gson.fromJson(json, type)
        }
        fun setOrderArrayListModel(
            list: ArrayList<ShopModel>,
            context: Context
        ) {
            val prefs = context.getSharedPreferences(PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            val gson = Gson()
            val json = gson.toJson(list)
            editor.putString("student_list_model", json)
            editor.apply() // This line is IMPORTANT !!!
        }

        fun getOrderArrayList(context: Context): java.util.ArrayList<ShopModel>? {
            val prefs = context.getSharedPreferences(
                PREFSNAME ,
                Context.MODE_PRIVATE
            )
            val gson = Gson()
            val json = prefs.getString("student_list", null)
            val type = object : TypeToken<ArrayList<ShopModel>?>() {}.type
            return gson.fromJson(json, type)
        }

        fun setLostAmount(context: Context, result: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("lost_amount", result)
            editor.commit()
        }

        fun getLostAmount(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("lost_amount", "")
        }

        fun setEmail(context: Context, result: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("email", result)
            editor.commit()
        }

        fun getEmail(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("email", "")
        }

        fun setOptions(
            list: ArrayList<String>,
            context: Context
        ) {
            val prefs = context.getSharedPreferences(PREFSNAME,
                Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            val gson = Gson()
            val json = gson.toJson(list)
            editor.putString("options", json)
            editor.apply() // This line is IMPORTANT !!!
        }

        fun getoptions(context: Context): java.util.ArrayList<String>? {
            val prefs = context.getSharedPreferences(
                PREFSNAME ,
                Context.MODE_PRIVATE
            )
            val gson = Gson()
            val json = prefs.getString("options", null)
            val type = object : TypeToken<ArrayList<String>?>() {}.type
            return gson.fromJson(json, type)
        }

    }
}