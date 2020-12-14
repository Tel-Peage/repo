package com.example.peage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class UserMainPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_main_page)
        ///val welcomeText = findViewById<TextView>(R.id.welcome_text)
        ///welcomeText.text = "Bienvenue Nicolas"

        val videoView = findViewById<VideoView>(R.id.vid_main_page)
        val videoPath = "android.resource://" + getPackageName() + "/" + R.raw.anim_peage
        var uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        var mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
    }

}
