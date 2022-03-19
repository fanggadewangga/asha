package com.nyautech.asha

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.nyautech.asha.adapter.ViewPagerAdapter
import com.nyautech.asha.databinding.ActivityOnboardingBinding
import com.nyautech.asha.model.User
import com.nyautech.asha.ui.home.HomeActivity
import com.nyautech.asha.ui.sign.SignInActivity
import com.nyautech.asha.ui.sign.SignUpActivity

class OnboardingActivity : AppCompatActivity() {

    private var titleList = mutableListOf<String>()
    private var detailList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()
    private lateinit var binding : ActivityOnboardingBinding


    private lateinit var googleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN: Int = 24434
    private lateinit var mAuth : FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            updateUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initialize firebase auth
        mAuth = Firebase.auth

        //database
        database = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").reference

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("88902840580-otli1056kp6j8ukeq8ekb2raa1o51ugr.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        addToList("Panic Button","We help you to get the evidences you need",R.drawable.panic_button)
        addToList("WriteUps","Know and learn yourself \nby exploring our writeups",R.drawable.onboarding2)
        addToList("Confab","You can always speak to us, \nwe're here to support you",R.drawable.onboarding3)

        binding.vpOnboarding.apply {
            adapter = ViewPagerAdapter(titleList,detailList,imagesList)
            orientation = ORIENTATION_HORIZONTAL
        }

        binding.indicator.setViewPager(binding.vpOnboarding)


        // click
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoogleLogin.setOnClickListener {
            signIn()
        }


    }
    private fun addToList(title : String,detail : String,image:Int){
        titleList.add(title)
        detailList.add(detail)
        imagesList.add(image)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(ContentValues.TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = mAuth.currentUser
                    val user = User(currentUser?.displayName, currentUser?.displayName,
                        currentUser?.phoneNumber
                    )
                    mAuth.currentUser?.let {
                        database.child("users").child(it.uid).setValue(user).addOnCompleteListener {
                            if (task.isSuccessful){
//                                startActivity(Intent(this, SignInActivity :: class.java))
                                Log.d(ContentValues.TAG, "signInWithCredential:success")
                                updateUI()
                            } else{
                                Toast.makeText(this, "Failed to register, pwease try again!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    updateUI()
                }
            }
    }
    private fun updateUI() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
    }
}