package com.example.driverapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.driverapp.R

class SignUpActivity : AppCompatActivity() {
    private var loginBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initComponent()

        loginBtn?.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun initComponent(){
        loginBtn = findViewById(R.id.signup_confirm_button)
    }
}