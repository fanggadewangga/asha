@file:Suppress("DEPRECATION")

package com.nyautech.asha.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nyautech.asha.OnboardingActivity
import com.nyautech.asha.ProfileActivity
import com.nyautech.asha.R
import com.nyautech.asha.adapter.ArticleAdapter
import com.nyautech.asha.data.DataArticle
import com.nyautech.asha.databinding.ActivityHomeBinding
import com.nyautech.asha.ui.article.ExploreActivity
import com.nyautech.asha.ui.consultation.ConsultationActivity
import com.nyautech.asha.util.Constanta.REQUEST_VIDEO_CAPTURE


class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userDatabase: DatabaseReference
    private lateinit var expertDatabase: DatabaseReference
    private lateinit var profilePictureReference: StorageReference
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var userId: String
    private lateinit var imageURL: String

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
        userId = mAuth.currentUser?.uid.toString()
        userDatabase = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")
        expertDatabase = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("expert")
        //storage
        firebaseStorage = FirebaseStorage.getInstance()
        profilePictureReference = firebaseStorage.getReference("profile pictures/$userId")

        // get user data
        var userTrustedContact: String? = ""
        val currentUser: FirebaseUser? = mAuth.currentUser
        var displayName = "User";
        val userId: String? = currentUser?.uid

        //get expert data
        var category: String

        var isExpert: Boolean = true//bernilai true jika itu expert, bernilai false jika user

        //Get user database
        if (userId != null) {
            userDatabase.child(userId).get().addOnSuccessListener {
                //get displayName from database
                displayName = it.child("username").value.toString()

                //get userTrustedContact from database
                userTrustedContact = it.child("trustedContact").value.toString()

                //set tvWelcome
                binding.tvWelcome.text = "Welcome,\n$displayName"

                //set iv

                profilePictureReference.downloadUrl.addOnSuccessListener {
                    imageURL = it.toString()
                    Glide.with(this).load(imageURL).apply(RequestOptions.circleCropTransform()).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.ivUserImage)
                }

                Log.i("firebase", "Got value user ${it.value}")

            }.addOnFailureListener{
                Log.e("firebase", "Error getting data User", it)

            }.toString()
        }

//        //Get expert database
//        Log.e("status", "isExpert = $isExpert")
//        if (isExpert) {
//            if (userId != null) {
//                expertDatabase.child(userId).get().addOnSuccessListener {
//
//                    //get displayName from database
//                    displayName = it.child("username").value.toString()
//
//                    //set tvWelcome
//                    binding.tvWelcome.text = "Welcome,\n$displayName"
//
//                    //set iv
//
//                    profilePictureReference.downloadUrl.addOnSuccessListener {
//                        imageURL = it.toString()
//                        Glide.with(this).load(imageURL).apply(RequestOptions.circleCropTransform())
//                            .diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.ivUserImage)
//                    }
//
//                    Log.i("firebase", "Got value expert ${it.value}")
//
//                }.addOnFailureListener {
//
//                    Log.e("firebase", "Error getting data", it)
//
//                }.toString()
//            }
//        }

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
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
                takeVideoIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
                }
            }
            true
        }

        // nav
        binding.icArticle.setOnClickListener {
            startActivity(Intent(this,ExploreActivity::class.java))
        }
        binding.icChat.setOnClickListener {
            startActivity(Intent(this,ConsultationActivity::class.java))
        }
    }

}
