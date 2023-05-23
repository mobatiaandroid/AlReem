package com.nas.alreem.constants

import android.content.Context
import com.nas.alreem.R

class ValidationFunctions {
    companion object{

        fun loginValidation(email:String,password:String,context: Context):Boolean
        {
            if(email.equals(""))
            {
                //enter email
                DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(
                    R.string.enter_email),context)
                return false
            }
            else
            {
                if(!ConstantFunctions.isEmailValid(email))
                {
                    //enter valid email
                    DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(
                        R.string.enter_valid_email),context)
                    return false

                }
                else
                {
                    if(password.equals(""))
                    {
                        //enter password
                        DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(
                            R.string.enter_password),context)
                        return false

                    }
                    else
                    {
                       return true

                    }
                }
            }
        }


        fun SignUpForgetValidation(email:String,context: Context):Boolean
        {
            if(email.equals(""))
            {
                //enter email
                DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(
                    R.string.enter_email),context)
                return false
            }
            else
            {
                if(!ConstantFunctions.isEmailValid(email))
                {
                    //enter valid email
                    DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(
                        R.string.enter_valid_email),context)
                    return false

                }
                else
                {
                    return true
                }
            }
        }
    }
}