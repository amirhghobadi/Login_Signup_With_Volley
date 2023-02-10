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

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var email : TextInputEditText
    lateinit var send_code : Button
    lateinit var progress_bar : ProgressBar
    private lateinit var request_queue: RequestQueue
    lateinit var back : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        init()

        send_code.setOnClickListener {
            if(email.text.toString() == ""){
                Toast.makeText(this,"Enter email",Toast.LENGTH_SHORT).show()
            }
            else{
                show_progress_bar(send_code,progress_bar)
                val random_code = random_code()
                val postRequest = object : StringRequest(Method.POST, BASE_URL + "forgot_pass.php",
                    Response.Listener { response ->
                        val json_object = JSONObject(response)
                        when (json_object.getString(MESSAGE)) {
                            MESSAGE_EMAIL_OK -> {
                                val intent = Intent(this,VerificationActivity::class.java)
                                intent.putExtra(KEY_EMAIL,email.text.toString())
                                intent.putExtra(KEY_CODE,random_code)
                                KEY_METHOD = KEY_FORGOT_PASSWORD
                                intent.putExtra(KEY_METHOD, KEY_METHOD)
                                startActivity(intent)
                                finish()
                            }
                            MESSAGE_EMAIL_NOT_EXISTS -> {
                                Toast.makeText(this, "Email not Exists!!!", Toast.LENGTH_SHORT).show()
                                hide_progress_bar(send_code,progress_bar)
                            }
                        }
                    }, Response.ErrorListener {
                        Toast.makeText(this, "Volley Error -> $it", Toast.LENGTH_SHORT).show()
                        hide_progress_bar(send_code,progress_bar)
                    }) {
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params[KEY_EMAIL] = email.text.toString()
                        params[KEY_CODE] = random_code
                        return params
                    }
                }
                request_queue.add(postRequest)
            }
        }

        back.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun init(){
        email = findViewById(R.id.edit_text_email_forgot_password_activity)
        send_code = findViewById(R.id.button_send_code_forgot_password_activity)
        progress_bar = findViewById(R.id.progressBar_forgot_password_activity)
        request_queue = Volley.newRequestQueue(this)
        back = findViewById(R.id.image_button_back_forgot_password_activity)
    }
}