package com.example.peage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        val goToPermis = findViewById<Button>(R.id.goToPermis)
        goToPermis.setOnClickListener{
            startActivity(Intent(this, PermisActivity::class.java))
        }
    }
    }

