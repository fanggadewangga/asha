package com.nyautech.asha.ui.sign

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.nyautech.asha.databinding.ActivitySignUpBinding
import com.nyautech.asha.model.User

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var mAuth : FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(signUpBinding.root)

        // initialize firebase auth
        mAuth = Firebase.auth

        //instance DatabaseReference
        database = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").reference

        // click
        signUpBinding.btnSignup.setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {
        // get data from input
        val name = signUpBinding.edtName.text.toString()
        val email = signUpBinding.edtEmail.text.toString()
        val username = signUpBinding.edtUsername.text.toString()
        val password = signUpBinding.edtPassword.text.toString()
        val trustedContact = signUpBinding.edtContact.text.toString()

        if(name.isEmpty()){
            signUpBinding.edtName.error = "Full name is required!"
            signUpBinding.edtName.requestFocus()
            return
        }
        if (email.isEmpty()){
            signUpBinding.edtEmail.error = "Email is required!"
            signUpBinding.edtEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpBinding.edtEmail.error = "Please input valid email!"
            signUpBinding.edtEmail.requestFocus()
            return
        }
        if (username.isEmpty()){
            signUpBinding.edtUsername.error = "Email is required!"
            signUpBinding.edtUsername.requestFocus()
            return
        }

        if (password.isEmpty()){
            signUpBinding.edtPassword.error = "Password is required!"
            signUpBinding.edtPassword.requestFocus()
            return
        }
        if (password.length < 6){
            signUpBinding.edtPassword.error = "Minimal password length should be 6 characters!"
            signUpBinding.edtPassword.requestFocus()
            return
        }
        if (trustedContact.isEmpty()){
            signUpBinding.edtContact.error = "Password is required!"
            signUpBinding.edtContact.requestFocus()
            return
        }
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = User(name, username, trustedContact)
                    mAuth.currentUser?.let {
                        database.child("users").child(it.uid).setValue(user).addOnCompleteListener {
                            if (task.isSuccessful){
                                Toast.makeText(this, "You have been registered successfully!", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, SignInActivity :: class.java))
                                finishAffinity()
                            } else{
                                Toast.makeText(this, "Failed to register, please try again!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Failed to sign in, please try again! ${task.exception}", Toast.LENGTH_LONG).show()
                }
            }
    }
}