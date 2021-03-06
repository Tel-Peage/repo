 package com.example.peage

import android.content.Intent
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.regex.Pattern
import kotlin.random.Random
import kotlin.random.nextInt

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
        val Maj = listOf<String>()

        var c: Char = 'A'
        while (c <= 'Z') {
            Maj.toMutableList().add(c.toString())
            ++c
        }

        if(name.isEmpty() || name2.isEmpty() || email.isEmpty() || num.isEmpty() || mdp.isEmpty() || confMdp.isEmpty()){
            Toast.makeText(applicationContext, "Veuillez compléter tous les champs !", Toast.LENGTH_LONG).show()
            return
        }

        if (!isValidPassword(mdp)){
            Toast.makeText(applicationContext, "Le mot de passe doit contenir au minimum 8 caractères avec au moins une majuscule, un chiffre et un caractère spécial !", Toast.LENGTH_LONG).show()
            return
        }

        if (num.length != 10 || !num.startsWith("0")){
            Toast.makeText(applicationContext, "Numéro de téléphone incorrecte !", Toast.LENGTH_LONG).show()
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
        if ("@" !in email){
            Toast.makeText(applicationContext, "Adresse mail non valide !", Toast.LENGTH_LONG).show()
            return
        }

        var database = FirebaseDatabase.getInstance().reference
        var children = database.child("Saves")
        var exists = false
        var sb = children.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var items = snapshot.getChildren().iterator()

                for(i in items){
                    var userEmail = i.child("email").getValue().toString()
                    var userNum = i.child("num").getValue().toString()

                    if(email != userEmail){
                        exists = false
                        if (num == userNum){
                            Toast.makeText(applicationContext, "Un compte avec ce numéro de téléphone existe déjà !", Toast.LENGTH_LONG).show()
                            exists = true
                            break
                        }
                    } else{
                        Toast.makeText(applicationContext, "Un compte avec cette adresse mail existe déjà !", Toast.LENGTH_LONG).show()
                        exists = true
                        break
                    }
                }

                if (!exists){

                    var randomCode = Random.nextInt(1000..9999).toString()

                    val ref = FirebaseDatabase.getInstance().getReference("Saves")
                    val saveId = ref.push().key.toString()
                    val save = Save(saveId, name, name2, email, num, mdp, confMdp)

                    ref.child(saveId).setValue(save).addOnCompleteListener{
                        sendMail(saveId, email, randomCode)

                        setContentView(R.layout.email_verification)


                        var confButton = findViewById<Button>(R.id.confirmBut)

                        confButton.setOnClickListener(){
                            var textCode = findViewById<TextView>(R.id.passwordCheck).text.toString()
                            if (textCode == randomCode){
                                Toast.makeText(applicationContext, "Correctement enregistrer", Toast.LENGTH_LONG).show()
                                val intent = Intent(baseContext, PermisActivity::class.java)
                                intent.putExtra("ID", saveId)
                                startActivity(intent)
                            }else{
                                Toast.makeText(applicationContext, "Code incorrecte", Toast.LENGTH_LONG).show()
                            }
                        }


                    }

                }
            }
        })
    }

    private fun sendMail(ID : String, email : String, code : String){

        var message: String = "Bienvenue chez Tel & Péage !" + "\n" + "Afin de confirmer votre compte, merci de rentrer le code ci dessous dans l'application." + "\n\n" + "Code : $code"


        var javaMailAPI : JavaMailAPI = JavaMailAPI(this, email, "Confirmez votre adresse mail !", message)
        javaMailAPI.execute()
    }

    private fun getText(data: Any): String {
        var str = ""
        if (data is EditText) {
            str = data.text.toString()
        } else if (data is String) {
            str = data
        }
        return str
    }

    fun isValidPassword(data: Any, updateUI: Boolean = true): Boolean {
        val str = getText(data)
        var valid = true

        // Password policy check
        // Password should be minimum minimum 8 characters long
        if (str.length < 8) {
            valid = false
        }
        // Password should contain at least one number
        var exp = ".*[0-9].*"
        var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        var matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one capital letter
        exp = ".*[A-Z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one small letter
        exp = ".*[a-z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one special character
        // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
        exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(str)
        if (!matcher.matches()) {
            valid = false
        }

        return valid
    }
}

