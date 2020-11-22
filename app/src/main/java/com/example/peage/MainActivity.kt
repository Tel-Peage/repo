package com.example.peage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goToLogin = findViewById<Button>(R.id.goToLogin)
        goToLogin.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))

        }

        val goToSignup = findViewById<Button>(R.id.goToSignup)
        goToSignup.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}