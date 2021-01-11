package com.example.peage

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.UploadTask
import java.lang.Exception

class PermisActivity: AppCompatActivity() {


    lateinit var imageView: ImageView
    lateinit var button: Button
    lateinit var buttonNext: Button
    private val pickImage = 100
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permis_camera)

        val DatabaseRef = FirebaseDatabase.getInstance().getReference("Saves")

        val currentUserId = intent.getStringExtra("ID")
        val currentUser = DatabaseRef.child(currentUserId.toString())

        title = "KotlinApp"
        imageView = findViewById(R.id.imageView)
        button = findViewById(R.id.buttonLoadPicture)
        var buttonUpload = findViewById<Button>(R.id.buttonUpload)
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        buttonNext = findViewById(R.id.goToAssurance)
        buttonNext.setOnClickListener{
            val intent = Intent(baseContext, PermisActivity::class.java)
            intent.putExtra("ID", currentUserId)
            startActivity(intent)
        }

        buttonUpload.setOnClickListener{
            if(imageUri != null){
                uploadToFirebase(imageUri!!)
            }else{
                Toast.makeText(applicationContext, "Veuillez sélectionner une image", Toast.LENGTH_LONG).show()
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)

        }
    }

    private fun uploadToFirebase(uri : Uri){
        val DatabaseRef = FirebaseDatabase.getInstance().getReference("Saves")
        val StorageRef = FirebaseStorage.getInstance().getReference()
        val currentUserId = intent.getStringExtra("ID")
        val currentUser = DatabaseRef.child(currentUserId.toString())

        var fileRef = StorageRef.child("permis_images/" + System.currentTimeMillis().toString() + "." + getFileExtension(uri))
        fileRef.putFile(uri).addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
            override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(object : OnSuccessListener<Uri>{
                    override fun onSuccess(p0: Uri?) {
                        /// put url in permis_url here
                        currentUser.child("permis_url").setValue(p0.toString())
                        Toast.makeText(applicationContext, "Image envoyée", Toast.LENGTH_LONG).show()
                        val intent = Intent(baseContext, AssuranceActivity::class.java)
                        intent.putExtra("ID", currentUserId)
                        startActivity(intent)
                    }

                })
            }

        }).addOnProgressListener(object : OnProgressListener<UploadTask.TaskSnapshot>{
            override fun onProgress(snapshot: UploadTask.TaskSnapshot) {

            }

        }).addOnFailureListener(object : OnFailureListener{
            override fun onFailure(p0: Exception) {
                Toast.makeText(applicationContext, "Echec de l'upload", Toast.LENGTH_LONG).show()}

        })
    }

    private fun getFileExtension(mUri: Uri): String? {
        var cr = getContentResolver()
        var mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(mUri)).toString()
    }
}
