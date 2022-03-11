@file:Suppress("DEPRECATION")

package com.nyautech.asha

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nyautech.asha.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // firebase
        // auth & database
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("users")



        // get user data
        val userTrustedContact = "081330723755"
        val currentUser: FirebaseUser? = mAuth.currentUser
        var displayName = "User"
        val userId: String? = mAuth.currentUser?.uid

        if (mAuth.currentUser?.displayName == null){
            displayName = userId?.let { database.child(it).child("username").get()}
                .toString()
        } else {
            if (currentUser != null) {
                displayName = currentUser.displayName.toString()
            }
        }


        // click
        // user image
        binding.ivUserImage.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnEmergencyCall.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$userTrustedContact"))
            startActivity(intent)
            true
        }

        binding.btnEmergencyCamera.setOnLongClickListener {
            val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
            true
        }

        // nav
        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.nav_article -> startActivity(Intent(this,ExploreActivity::class.java))
                R.id.nav_home -> startActivity(Intent(this,HomeActivity::class.java))
                R.id.nav_concultation -> startActivity(Intent(this,ConsultationActivity::class.java))
            }
        }


        // logout
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            intent = Intent(this , OnboardingActivity::class.java)
            startActivity(intent)
        }

        binding.tvWelcome.text = "Welcome,  $displayName!"
    }
}
