package com.example.peage

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class UserMainPage(): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_main_page_2)
        val currentUserId = intent.getStringExtra("USERNAME")
        Toast.makeText(this, currentUserId, Toast.LENGTH_SHORT).show()

        ///val videoView = findViewById<VideoView>(R.id.vid_main_page)
        ////val videoPath = "android.resource://" + getPackageName() + "/" + R.raw.anim_peage
        ///var uri = Uri.parse(videoPath)
        ///videoView.setVideoURI(uri)

        ///var mediaController = MediaController(this)
        ///videoView.setMediaController(mediaController)
        ///mediaController.setAnchorView(videoView)
    }

}
