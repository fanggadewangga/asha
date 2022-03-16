package com.nyautech.asha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.nyautech.asha.databinding.ActivityHomeBinding
import com.nyautech.asha.databinding.ActivityOnboardingBinding
import com.nyautech.asha.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // firebase
        // auth & database
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")
        val currentUser: FirebaseUser? = mAuth.currentUser
        val userId: String? = currentUser?.uid

        var name: String?
        var userName: String?
        var email: String?

        if (userId != null) {
            database.child(userId).get().addOnSuccessListener {

                //get name from database
                name = it.child("name").value.toString()

                //get userName from database
                userName = it.child("username").value.toString()

                //get email from database
                email = currentUser.email.toString()


                //set tv
                binding.tvUsersName.text = name
                binding.tvUsersUsername.text = userName
                binding.tvUsersEmail.text = email

                Log.i("firebase", "Got value ${it.value}")

            }.addOnFailureListener{

                Log.e("firebase", "Error getting data", it)

            }.toString()
        }
        // logout
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            intent = Intent(this , OnboardingActivity::class.java)
            startActivity(intent)
        }
    }

}
