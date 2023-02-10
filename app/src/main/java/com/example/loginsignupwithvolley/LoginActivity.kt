package com.example.loginsignupwithvolley

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.loginsignupwithvolley.Utilities.*
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var remember_me: CheckBox
    lateinit var forgot_password: TextView
    lateinit var login: Button
    lateinit var progress_bar: ProgressBar
    lateinit var sign_up: TextView
    lateinit var request_queue: RequestQueue
    lateinit var shared_preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

        // get intent from verification activity
        email.setText(intent.getStringExtra(KEY_EMAIL))
        password.setText(intent.getStringExtra(KEY_PASSWORD))


        // check remember me with shared preferences
        val email_shared_preferences = shared_preferences.getString(KEY_EMAIL, "")
        if (email_shared_preferences != "") {
            startActivity(Intent(this, MainActivity::class.java))
        }
        login.setOnClickListener {
            if ((email.text.toString() == "") || (password.text.toString() == "")) {
                Toast.makeText(this, "please fill values!!!", Toast.LENGTH_SHORT).show()
            } else {

                show_progress_bar(login, progress_bar)
                val postRequest = object : StringRequest(Method.POST, BASE_URL + "login.php",
                    Response.Listener { response ->
                        val json_object = JSONObject(response)
                        when (json_object.getString(MESSAGE)) {
                            MESSAGE_LOGIN_OK -> {
                                Toast.makeText(this, "login Successful", Toast.LENGTH_SHORT).show()
                                if (remember_me.isChecked) {
                                    shared_preferences.edit()
                                        .putString(KEY_EMAIL, email.text.toString().trim()).apply()
                                } else {
                                    shared_preferences.edit()
                                        .putString(KEY_EMAIL, "").apply()
                                }
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                            MESSAGE_FAILED_LOGIN -> {
                                Toast.makeText(this, "Failed Login", Toast.LENGTH_SHORT).show()
                                hide_progress_bar(login, progress_bar)
                            }
                        }
                    }, Response.ErrorListener {
                        Toast.makeText(this, "Volley Error -> $it", Toast.LENGTH_SHORT).show()
                        hide_progress_bar(login, progress_bar)
                    }) {
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params[KEY_EMAIL] = email.text.toString()
                        params[KEY_PASSWORD] = password.text.toString().trim()
                        return params
                    }
                }
                request_queue.add(postRequest)
            }
        }

        forgot_password.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
            finish()
        }
        sign_up.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
    }


    private fun init() {
        email = findViewById(R.id.edit_text_email_login_activity)
        password = findViewById(R.id.edit_text_password_login_activity)
        remember_me = findViewById(R.id.check_box_login_activity)
        forgot_password = findViewById(R.id.text_view_forgot_password_login_activity)
        login = findViewById(R.id.button_login_login_activity)
        sign_up = findViewById(R.id.textview_sign_up_login_activity)
        progress_bar = findViewById(R.id.progressBar_login_activty)
        request_queue = Volley.newRequestQueue(this)
        shared_preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)

    }

}