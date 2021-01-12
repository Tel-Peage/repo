package com.example.peage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.*
import java.lang.Exception
import java.nio.file.Paths

class LogInActivity: AppCompatActivity() {

    private var fileName : String = "StayConnected.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)


        val log_In_Button_Check = findViewById<Button>(R.id.button_log)

        val email = findViewById<TextView>(R.id.logIn_text_email)
        val mdp = findViewById<TextView>(R.id.logIn_text_mdp)

        log_In_Button_Check.setOnClickListener{

            checkIfUserExists()
        }

    }

    private fun checkIfUserExists(){
        var database = FirebaseDatabase.getInstance().reference
        var children = database.child("Saves")

        val emailText = findViewById<TextView>(R.id.logIn_text_email)
        val mdpText = findViewById<TextView>(R.id.logIn_text_mdp)

        if (emailText.text.toString() == "" || mdpText.text.toString() == ""){
            Toast.makeText(this@LogInActivity, "Veuillez compléter tous les champs !", Toast.LENGTH_SHORT).show()
        }else{
            var sb = children.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    var items = snapshot.getChildren().iterator()

                    for(i in items){
                        var userEmail = i.child("email").getValue().toString()
                        var userMdp = i.child("mdp").getValue().toString()

                        var userPrenom = i.child("firstname").getValue().toString()
                        var userId = i.child("id").getValue().toString()



                        val emailText = findViewById<TextView>(R.id.logIn_text_email)
                        val mdpText = findViewById<TextView>(R.id.logIn_text_mdp)

                        if(emailText.text.toString() == userEmail && mdpText.text.toString() == userMdp){
                            Toast.makeText(applicationContext, "Bienvenue $userPrenom", Toast.LENGTH_LONG).show()

                            val intent = Intent(baseContext, UserMainPage::class.java)
                            intent.putExtra("USERNAME", userId)
                            startActivity(intent)


                            return
                        }
                    }
                    Toast.makeText(this@LogInActivity, "Email ou mot de passe incorrecte !", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}