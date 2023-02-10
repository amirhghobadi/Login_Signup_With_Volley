package com.example.loginsignupwithvolley

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.loginsignupwithvolley.Utilities.*
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class NewPasswordActivity : AppCompatActivity() {

    lateinit var password: TextInputEditText
    lateinit var confirm_password: TextInputEditText
    lateinit var submit: Button
    lateinit var progress_bar: ProgressBar
    lateinit var request_queue: RequestQueue
    lateinit var back : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)
        init()

        val email = intent.getStringExtra(KEY_EMAIL)


        submit.setOnClickListener {
            if ((password.text.toString() == "") || (confirm_password.text.toString() == "")) {
                Toast.makeText(this, "please fill values!!!", Toast.LENGTH_SHORT).show()
            } else {
                if (password.text.toString() == confirm_password.text.toString()) {
                    if (password.text?.length!! >= 6) {
                        show_progress_bar(submit, progress_bar)
                        val main_password = password.text.toString().trim()
                        val postRequest =
                            object : StringRequest(Method.POST, BASE_URL + "change_pass.php",
                                Response.Listener { response ->
                                    val json_object = JSONObject(response)
                                    when (json_object.getString(MESSAGE)) {
                                        MESSAGE_OK -> {
                                            Toast.makeText(this, "Changed Password Successfully", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(this, LoginActivity::class.java)
                                            intent.putExtra(KEY_EMAIL, email)
                                            intent.putExtra(KEY_PASSWORD, main_password)
                                            startActivity(intent)
                                            finish()

                                        }
                                        MESSAGE_FAILED -> {
                                            Toast.makeText(this, "failed", Toast.LENGTH_SHORT)
                                                .show()
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
                                    params[KEY_EMAIL] = email.toString().trim()
                                    params[KEY_PASSWORD] = main_password
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
                        hide_progress_bar(submit, progress_bar)
                    }

                } else {
                    Toast.makeText(this, "password does not match!!!", Toast.LENGTH_SHORT).show()
                    hide_progress_bar(submit, progress_bar)
                }

            }
        }

        back.setOnClickListener{
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun init() {
        password = findViewById(R.id.edit_text_password_new_password_activity)
        confirm_password = findViewById(R.id.edit_text_confirm_password_new_password_activity)
        submit = findViewById(R.id.button_submit_new_password_activity)
        progress_bar = findViewById(R.id.progress_bar_new_password_activity)
        back = findViewById(R.id.image_button_back_new_password_activity)
        request_queue = Volley.newRequestQueue(this)
    }
}