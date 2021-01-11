package com.example.peage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CardActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card)

        val DatabaseRef = FirebaseDatabase.getInstance().getReference("Saves")
        val currentUserId = intent.getStringExtra("ID")

        val confButton = findViewById<Button>(R.id.confirmButCard)

        confButton.setOnClickListener(){

            val nameCard = findViewById<TextView>(R.id.nameCardText)
            val numCard = findViewById<TextView>(R.id.numCardText)
            val expCard = findViewById<TextView>(R.id.dateExpText)
            val crypto = findViewById<TextView>(R.id.cryptogrammeText)

            val nameCardText = nameCard.text.toString().trim()
            val numCardText = numCard.text.toString().trim()
            val expCardText = expCard.text.toString().trim()
            val cryptoText = crypto.text.toString().trim()

            if(nameCardText.isEmpty() || numCardText.isEmpty() || expCardText.isEmpty() || cryptoText.isEmpty()){
                Toast.makeText(applicationContext, "Veuillez compléter tous les champs !", Toast.LENGTH_LONG).show()
            }
            else{
                val currentUser = DatabaseRef.child(currentUserId.toString())
                currentUser.child("nomCarte").setValue(nameCardText)
                currentUser.child("numCarte").setValue(numCardText)
                currentUser.child("dateExpiration").setValue(expCardText)
                currentUser.child("cryptogramme").setValue(cryptoText)

                startActivity(Intent(this,LogInActivity::class.java))
            }
        }

        val goToLoginFromCard = findViewById<Button>(R.id.goToLoginFromCard)
        goToLoginFromCard.setOnClickListener{
            startActivity(Intent(this,LogInActivity::class.java))
        }
    }
}