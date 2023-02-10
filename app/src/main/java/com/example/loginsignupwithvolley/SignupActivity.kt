package com.example.loginsignupwithvolley

import android.content.Intent
import android.media.Image
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
import org.json.JSONException
import org.json.JSONObject

class SignupActivity : AppCompatActivity() {

    lateinit var username: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var confirm_password: TextInputEditText
    lateinit var signup: Button
    lateinit var progress_bar: ProgressBar
    lateinit var login: TextView
    lateinit var request_queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        init()

        signup.setOnClickListener {
            if ((username.text.toString() == "") || (email.text.toString() == "") || (password.text.toString() == "") || (confirm_password.text.toString() == "")) {
                Toast.makeText(this, "please fill values!!!", Toast.LENGTH_SHORT).show()
            } else {
                if (password.text.toString() == confirm_password.text.toString()) {
                    if (password.text?.length!! >= 6) {
                        show_progress_bar(signup, progress_bar)
                        val random_code = random_code()

                        val postRequest =
                            object : StringRequest(Method.POST, BASE_URL + "check_email.php",
                                Response.Listener { response ->
                                    val json_object = JSONObject(response)
                                    when (json_object.getString(MESSAGE)) {
                                        MESSAGE_EMAIL_OK -> {
                                            val intent =
                                                Intent(this, VerificationActivity::class.java)
                                            intent.putExtra(KEY_USERNAME, username.text.toString())
                                            intent.putExtra(KEY_EMAIL, email.text.toString())
                                            intent.putExtra(KEY_PASSWORD, password.text.toString())
                                            intent.putExtra(KEY_CODE, random_code)
                                            KEY_METHOD = KEY_REGISTER
                                            intent.putExtra(KEY_METHOD, KEY_METHOD)
                                            startActivity(intent)
                                            finish()
                                        }
                                        MESSAGE_EMAIL_EXISTS -> {
                                            Toast.makeText(this, "Email Exists", Toast.LENGTH_SHORT)
                                                .show()
                                            hide_progress_bar(signup, progress_bar)
                                        }
                                    }
                                }, Response.ErrorListener {
                                    Toast.makeText(this, "Volley Error -> $it", Toast.LENGTH_SHORT)
                                        .show()
                                    hide_progress_bar(signup, progress_bar)
                                }) {
                                override fun getParams(): Map<String, String> {
                                    val params = HashMap<String, String>()
                                    params[KEY_EMAIL] = email.text.toString()
                                    params[KEY_CODE] = random_code
                                    return params
                                }
                            }
                        request_queue.add(postRequest)

                    } else {
                        Toast.makeText(
                            this,
                            "password is equal or grater than 6 characters",
                            Toast.LENGTH_SHORT
                        ).show()
                        hide_progress_bar(signup, progress_bar)
                    }

                } else {
                    Toast.makeText(this, "password does not match!!!", Toast.LENGTH_SHORT).show()
                    hide_progress_bar(signup, progress_bar)
                }

            }
        }

        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun init() {
        username = findViewById(R.id.edit_text_user_name_signup_activity)
        email = findViewById(R.id.edit_text_email_signup_activity)
        password = findViewById(R.id.edit_text_password_signup_activity)
        confirm_password = findViewById(R.id.edit_text_confirm_password_signup_activity)
        signup = findViewById(R.id.button_singup_signup_activity)
        progress_bar = findViewById(R.id.progressBar_signup_activity)
        login = findViewById(R.id.textview_login_signup_activity)
        request_queue = Volley.newRequestQueue(this)
    }


}