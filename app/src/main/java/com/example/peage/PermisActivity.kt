package com.example.peage

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PermisActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permis_camera)

        val REQUEST_IMAGE_CAPTURE = 1

        val OpenCamera = findViewById<Button>(R.id.camera_permis)
        OpenCamera.setOnClickListener{
             fun dispatchTakePictureIntent() {
                try {
                    startActivityForResult(
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        REQUEST_IMAGE_CAPTURE
                    )
                } catch (e: ActivityNotFoundException) {
                    //display error that to the user
                }
            }
        }
    }
}