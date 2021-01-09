package com.example.peage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        editTextName = findViewById(R.id.editTextTextPersonName)
        buttonSignUp = findViewById(R.id.goToPermis)

        buttonSignUp.setOnClickListener{
            Save()
            startActivity(Intent(this, PermisActivity::class.java))
        }
    }

    private fun Save(){
        val name = editTextName.text.toString().trim()

        if(name.isEmpty()){
            editTextName.error = "Entrez un nom"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("Saves")
        val saveId = ref.push().key.toString()

        val save = Save(saveId, name)

        ref.child(saveId).setValue(save).addOnCompleteListener{
            Toast.makeText(applicationContext, "Correctement enregistrer", Toast.LENGTH_LONG).show()
        }

    }
}

