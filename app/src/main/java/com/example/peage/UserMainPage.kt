package com.example.peage

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.peage.fragments.HistFragment
import com.example.peage.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserMainPage(): AppCompatActivity() {

    lateinit var currentUser_id : String
    lateinit var currentUser_firstname : String
    lateinit var currentUser_name : String
    lateinit var currentUser_email : String
    lateinit var currentUser_num : String
    lateinit var currentUser_mdp : String
    lateinit var currentUser_permis_url : String
    lateinit var currentUser_assurance_url : String

    private var handlerAnimation = Handler()
    private var statusAnimation = false



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

                currentUser_id = map["id"].toString()
                currentUser_firstname = map["firstname"].toString()
                currentUser_name = map["name"].toString()
                currentUser_email = map["email"].toString()
                currentUser_num = map["num"].toString()
                currentUser_mdp = map["mdp"].toString()
                currentUser_permis_url = map["permis_url"].toString()
                currentUser_assurance_url = map["assurance_url"].toString()

                LoadProfile(map["firstname"].toString())
            }

        })

        var button = findViewById<Button>(R.id.buttonblth)
        button.setOnClickListener(){
            val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            val bluetoothAdapter = bluetoothManager.adapter
            val REQUEST_ENABLE_BT = 1
            if (statusAnimation){
                stopPulse()
                button.setText("START")
            }
            else{
                startPulse()
                button.setText("STOP")
                if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
                }
            }
            statusAnimation = !statusAnimation



        }

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView2)


        val navController = findNavController(R.id.fragment4)
        bottom_navigation.setupWithNavController(navController)

        ///val videoView = findViewById<VideoView>(R.id.vid_main_page)
        ////val videoPath = "android.resource://" + getPackageName() + "/" + R.raw.anim_peage
        ///var uri = Uri.parse(videoPath)
        ///videoView.setVideoURI(uri)

        ///var mediaController = MediaController(this)
        ///videoView.setMediaController(mediaController)
        ///mediaController.setAnchorView(videoView)

        // Initializes Bluetooth adapter.

    }

    private fun LoadProfile(userFirstname : String){
        val welcome = findViewById<TextView>(R.id.welcomeText)
        welcome.text = "Bonjour $userFirstname"


    }

    private fun startPulse() {
        runnable.run()
    }

    private fun stopPulse() {
        handlerAnimation.removeCallbacks(runnable)
    }

    private var runnable = object : Runnable{
        override fun run() {
            var imgAnimation1 = findViewById<ImageView>(R.id.imgAnimation1)
            imgAnimation1.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000)
                .withEndAction{
                    imgAnimation1.scaleX = 1f
                    imgAnimation1.scaleY = 1f
                    imgAnimation1.alpha = 1f
                }
            var imgAnimation2 = findViewById<ImageView>(R.id.imgAnimation2)
            imgAnimation2.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(700)
                .withEndAction{
                    imgAnimation2.scaleX = 1f
                    imgAnimation2.scaleY = 1f
                    imgAnimation2.alpha = 1f
                }
            handlerAnimation.postDelayed(this, 1500)
        }
    }
}
