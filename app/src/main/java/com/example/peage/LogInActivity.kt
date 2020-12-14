package com.example.peage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LogInActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)


        val log_In_Button_Check = findViewById<Button>(R.id.button_log)

        val email = findViewById<TextView>(R.id.logIn_text_email)

        val mdp = findViewById<TextView>(R.id.logIn_text_mdp)

        log_In_Button_Check.setOnClickListener{
            val email: String = email.text.toString()
            val mdp: String = mdp.text.toString()

            if (email  == "" || mdp == ""){
                Toast.makeText(this@LogInActivity, "Les champs doivent être complétés", Toast.LENGTH_SHORT).show()
            }
            else if("@" !in email){
                Toast.makeText(this@LogInActivity, "L'adresse mail n'est pas valide", Toast.LENGTH_SHORT).show()
            }
            else{
                checkIfUserExists(email, mdp)
            }

        }

    }

    fun checkIfUserExists(email: String, mdp: String){
        if (email == "wesh@gmail.com" && mdp == "1234"){
            Toast.makeText(this@LogInActivity, "Bienvenue Wesh Dene 59", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, UserMainPage::class.java))

        }
        else{
            Toast.makeText(this@LogInActivity, "Cet Utilisateur n'existe pas", Toast.LENGTH_SHORT).show()
        }
    }

}