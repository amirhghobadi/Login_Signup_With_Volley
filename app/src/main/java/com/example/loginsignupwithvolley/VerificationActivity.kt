package com.example.loginsignupwithvolley

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.loginsignupwithvolley.Utilities.*
import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import org.json.JSONObject


class VerificationActivity : AppCompatActivity() {

    lateinit var otp_view: OtpTextView
    lateinit var submit: Button
    lateinit var progress_bar: ProgressBar
    lateinit var back : ImageButton
    lateinit var request_queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        init()
        val username = intent.getStringExtra(KEY_USERNAME)
        val email = intent.getStringExtra(KEY_EMAIL)
        val password = intent.getStringExtra(KEY_PASSWORD)
        val code = intent.getStringExtra(KEY_CODE)
        val method = intent.getStringExtra(KEY_METHOD)
        var check_code = false


        otp_view.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                // fired when user types something in the Otp-box
            }

            override fun onOTPComplete(otp: String) {
                // fired when user has entered the OTP fully.
                check_code = if (code == otp) {
                    otp_view.showSuccess()
                    true
                } else {
                    otp_view.showError()
                    false
                }
            }
        }

        submit.setOnClickListener {
            if (check_code) {
                when (method) {
                    KEY_REGISTER -> {
                        show_progress_bar(submit, progress_bar)
                        val postRequest =
                            object : StringRequest(Method.POST, BASE_URL + "register.php",
                                Response.Listener { response ->
                                    val json_object = JSONObject(response)
                                    when (json_object.getString(MESSAGE)) {
                                        MESSAGE_OK -> {
                                            val intent = Intent(this, LoginActivity::class.java)
                                            intent.putExtra(KEY_EMAIL, email)
                                            intent.putExtra(KEY_PASSWORD, password)
                                            startActivity(intent)
                                            finish()
                                        }
                                        MESSAGE_FAILED ->{
                                            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
                                            hide_progress_bar(submit, progress_bar)
                                        }
                                    }
                                }, Response.ErrorListener {
                                    Toast.makeText(this, "Volley Error -> $it", Toast.LENGTH_SHORT)
                                        .show()
                                    hide_progress_bar(submit, progress_bar)
                                }) {
                                override fun getParams(): Map<String, String> {
                                    val params = HashMap<String, String>()
                                    params[KEY_USERNAME] = username.toString()
                                    params[KEY_EMAIL] = email.toString()
                                    params[KEY_PASSWORD] = password.toString()
                                    return params
                                }
                            }
                        request_queue.add(postRequest)
                    }
                    KEY_FORGOT_PASSWORD -> {
                        val intent = Intent(this, NewPasswordActivity::class.java)
                        intent.putExtra(KEY_EMAIL, email)
                        startActivity(intent)
                        finish()
                    }
                }
            }

        }

        back.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun init() {
        otp_view = findViewById(R.id.otp_view)
        submit = findViewById(R.id.button_submit_verification_avtivity)
        progress_bar = findViewById(R.id.progress_bar_verification_activity)
        back = findViewById(R.id.image_button_back_verification_activity)
        request_queue = Volley.newRequestQueue(this)
    }

}