package com.example.peage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity: AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var buttonSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        buttonSignUp = findViewById(R.id.goToPermis)

        buttonSignUp.setOnClickListener{
            Save()
        }
    }

    private fun Save(){
        val editTextName = findViewById<TextView>(R.id.editTextTextPersonName)
        val editTextName2 = findViewById<TextView>(R.id.editTextTextPersonName2)
        val editTextEmail = findViewById<TextView>(R.id.editTextTextEmailAddress2)
        val editTextNum = findViewById<TextView>(R.id.editTextPhone)
        val editTextMdp = findViewById<TextView>(R.id.editTextTextPassword2)
        val editTextConfirmMdp = findViewById<TextView>(R.id.editTextTextPassword3)

        val name = editTextName.text.toString().trim()
        val name2 = editTextName2.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val num = editTextNum.text.toString().trim()
        val mdp = editTextMdp.text.toString().trim()
        val confMdp = editTextConfirmMdp.text.toString().trim()

        val caract = listOf<String>("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "²", "&", "(", ")", "=")

        if(name.isEmpty() || name2.isEmpty() || email.isEmpty() || num.isEmpty() || mdp.isEmpty() || confMdp.isEmpty()){
            Toast.makeText(applicationContext, "Veuillez compléter tous les champs !", Toast.LENGTH_LONG).show()
            return
        }
        if (mdp != confMdp){
            Toast.makeText(applicationContext, "Les mots de passe sont différents !", Toast.LENGTH_LONG).show()
            return
        }
        for (c in caract)
        if(c in name){
            Toast.makeText(applicationContext, "Votre nom/prénom ne peut contenir de caractères spéciaux !", Toast.LENGTH_LONG).show()
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("Saves")
        val saveId = ref.push().key.toString()

        val save = Save(saveId, name, name2, email, num, mdp, confMdp)

        ref.child(saveId).setValue(save).addOnCompleteListener{
            Toast.makeText(applicationContext, "Correctement enregistrer", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PermisActivity::class.java))
        }

    }
}

