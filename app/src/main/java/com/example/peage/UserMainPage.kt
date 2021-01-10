package com.example.peage

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserMainPage(): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_main_page_2)
        val currentUserId = intent.getStringExtra("USERNAME")

        var database = FirebaseDatabase.getInstance().getReference("Saves")
        var currentUser = database.child(currentUserId.toString())

        currentUser.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var map = snapshot.value as Map<String, Any>
                LoadProfile(map["prenom"].toString())
            }

        })

        ///val videoView = findViewById<VideoView>(R.id.vid_main_page)
        ////val videoPath = "android.resource://" + getPackageName() + "/" + R.raw.anim_peage
        ///var uri = Uri.parse(videoPath)
        ///videoView.setVideoURI(uri)

        ///var mediaController = MediaController(this)
        ///videoView.setMediaController(mediaController)
        ///mediaController.setAnchorView(videoView)
    }

    private fun LoadProfile(userFirstname : String){
        val welcome = findViewById<TextView>(R.id.welcomeText)
        welcome.text = "Bonjour, $userFirstname"
    }

}
