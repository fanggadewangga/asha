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
            signUpBinding.edtName.setError("Full name is required!")
            signUpBinding.edtName.requestFocus()
            return
        }
        if (email.isEmpty()){
            signUpBinding.edtEmail.setError("Email is required!")
            signUpBinding.edtEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpBinding.edtEmail.setError("Please input valid email!")
            signUpBinding.edtEmail.requestFocus()
            return
        }
        if (username.isEmpty()){
            signUpBinding.edtUsername.setError("Email is required!")
            signUpBinding.edtUsername.requestFocus()
            return
        }

        if (password.isEmpty()){
            signUpBinding.edtPassword.setError("Password is required!")
            signUpBinding.edtPassword.requestFocus()
            return
        }
        if (password.length < 6){
            signUpBinding.edtPassword.setError("Minimal password length should be 6 characters!")
            signUpBinding.edtPassword.requestFocus()
            return
        }
        if (trustedContact.isEmpty()){
            signUpBinding.edtContact.setError("Password is required!")
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
                            } else{
                                Toast.makeText(this, "Failed to register, pwease try again!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Failed to sign in, pwease try again! ${task.exception}", Toast.LENGTH_LONG).show()
                }
            }
    }
}