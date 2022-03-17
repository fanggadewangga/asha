@file:Suppress("DEPRECATION")

package com.nyautech.asha.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.nyautech.asha.OnboardingActivity
import com.nyautech.asha.ProfileActivity
import com.nyautech.asha.R
import com.nyautech.asha.adapter.ArticleAdapter
import com.nyautech.asha.data.DataArticle
import com.nyautech.asha.databinding.ActivityHomeBinding
import com.nyautech.asha.ui.article.ExploreActivity
import com.nyautech.asha.ui.consultation.ConsultationActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //rv
        val articleAdapter = ArticleAdapter()
        articleAdapter.saveAllData(DataArticle.listOfArticle(this))
        binding.rvHomeArticle.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity,LinearLayoutManager.VERTICAL,false)
        }

        // firebase
        // auth & database
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")

        // get user data
        var userTrustedContact = ""
        val currentUser: FirebaseUser? = mAuth.currentUser
        var displayName = "User"
        val userId: String? = currentUser?.uid

        if (userId != null) {
            database.child(userId).get().addOnSuccessListener {

                //get displayName from database
                displayName = it.child("username").value.toString()

                //get userTrustedContact from database
                userTrustedContact = it.child("trustedContact").value.toString()

                //set tvWelcome
                binding.tvWelcome.text = "Welcome,\n$displayName"

                Log.i("firebase", "Got value ${it.value}")

            }.addOnFailureListener{

                Log.e("firebase", "Error getting data", it)

            }.toString()
        }

        // click
        // user image
        binding.ivUserImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
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
                R.id.nav_article -> startActivity(Intent(this, ExploreActivity::class.java))
                R.id.nav_home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.nav_concultation -> startActivity(Intent(this, ConsultationActivity::class.java))
            }
        }
    }
}
