package com.nyautech.asha.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nyautech.asha.ui.home.HomeActivity
import com.nyautech.asha.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var signInBinding: ActivitySignInBinding

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(signInBinding.root)

        // initialize firebase auth
        mAuth = Firebase.auth

        // click
        signInBinding.btnLogin.setOnClickListener {
            signIn()
        }
        //To Register
        signInBinding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        //Forgot Password

    }
    private fun signIn() {

        // get data login
        val email = signInBinding.edtEmail.text.toString()
        val password = signInBinding.edtPassword.text.toString()

        if (email.isEmpty()){
            signInBinding.edtEmail.setError("Email is required!")
            signInBinding.edtEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signInBinding.edtEmail.setError("Please input valid email!")
            signInBinding.edtEmail.requestFocus()
            return
        }
        if (password.isEmpty()){
            signInBinding.edtPassword.setError("Password is required!")
            signInBinding.edtPassword.requestFocus()
            return
        }
        if (password.length < 6){
            signInBinding.edtPassword.setError("Minimal password length should be 6 characters!")
            signInBinding.edtPassword.requestFocus()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Login Success!",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"User does not exist!",Toast.LENGTH_SHORT).show()
                }
            }
    }
}