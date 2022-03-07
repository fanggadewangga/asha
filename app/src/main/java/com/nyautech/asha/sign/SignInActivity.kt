package com.nyautech.asha.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nyautech.asha.R
import com.nyautech.asha.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var signInBinding: ActivitySignInBinding
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(signInBinding.root)

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance()

        // get data login
        val email = signInBinding.edtEmail.text.toString()
        val password = signInBinding.edtPassword.text.toString()

        // click
        signInBinding.btnLogin.setOnClickListener {
            signIn(email,password)
        }
    }

    private fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Login Success!",Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"User does not exist!",Toast.LENGTH_SHORT).show()
                }
            }
    }
}