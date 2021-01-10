package com.example.peage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CardActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card)

        val goToLoginFromCard = findViewById<Button>(R.id.goToLoginFromCard)
        goToLoginFromCard.setOnClickListener{
            startActivity(Intent(this,LogInActivity::class.java))
        }
    }
}