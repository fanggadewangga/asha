package com.nyautech.asha.sign

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.nyautech.asha.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(signUpBinding.root)

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance()

        // get data from input
        val name = signUpBinding.edtName.text.toString()
        val email = signUpBinding.edtEmail.text.toString()
        val username = signUpBinding.edtUsername.text.toString()
        val password = signUpBinding.edtPassword.text.toString()
        val trustedContact = signUpBinding.edtContact.text.toString()

        // click
        signUpBinding.btnSignup.setOnClickListener {
            if (email!=null || password!=null) signUp(email,password)
            else Toast.makeText(this,"Null",Toast.LENGTH_SHORT).show()
        }
    }

    private fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Sign up Success!",Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Sign up failed!",Toast.LENGTH_SHORT).show()
                }
            }
    }
}