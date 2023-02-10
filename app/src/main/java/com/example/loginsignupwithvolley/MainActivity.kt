package com.example.loginsignupwithvolley

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.loginsignupwithvolley.Utilities.KEY_EMAIL
import com.example.loginsignupwithvolley.Utilities.SHARED_PREFERENCES

class MainActivity : AppCompatActivity() {

    lateinit var log_out: Button
    lateinit var shared_preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        log_out.setOnClickListener {
            //clear shared preferences
            shared_preferences.edit().clear().apply()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }

    fun init() {
        log_out = findViewById(R.id.button_log_out_main_activity)
        shared_preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
    }

}