package com.example.loginsignupwithvolley.Utilities

import android.content.SharedPreferences
import android.view.View

const val BASE_URL = "https://bestapplications.ir/login_signup/"
const val SHARED_PREFERENCES = "volley"
const val KEY_USERNAME = "username"
const val KEY_EMAIL = "email"
const val KEY_PASSWORD = "password"
const val KEY_CODE = "code"
const val KEY_REGISTER = "register"
const val KEY_FORGOT_PASSWORD = "forgot_password"
var KEY_METHOD = ""
const val MESSAGE = "message"
const val MESSAGE_LOGIN_OK = "login_ok"
const val MESSAGE_FAILED_LOGIN = "failed_login"
const val MESSAGE_EMAIL_EXISTS = "email_exists"
const val MESSAGE_EMAIL_OK = "email_ok"
const val MESSAGE_OK = "OK"
const val MESSAGE_FAILED = "FAILED"
const val MESSAGE_EMAIL_NOT_EXISTS = "email_notExists"

fun show_progress_bar(button : View,progress_bar : View){
    button.visibility = View.INVISIBLE
    progress_bar.visibility = View.VISIBLE
}

fun hide_progress_bar(button : View,progress_bar : View){
    button.visibility = View.VISIBLE
    progress_bar.visibility = View.INVISIBLE
}

fun random_code(): String {
    return (((Math.random())*100000).toInt()).toString()
}