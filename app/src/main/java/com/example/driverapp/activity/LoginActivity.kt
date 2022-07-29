package com.example.driverapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.driverapp.R

class LoginActivity : AppCompatActivity() {
    private var loginBtn: Button? = null
    private var signUpBtn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initComponent()

        loginBtn?.setOnClickListener {
            finishAffinity();
            startActivity(Intent(this, HomeActivity::class.java))
        }

        signUpBtn?.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun initComponent(){
        loginBtn = findViewById(R.id.signin_confirm_button)
        signUpBtn = findViewById(R.id.signup_prompt)
    }
}