package com.nyautech.asha.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.gdsc.gdsctoast.GDSCToast
import com.gdsc.gdsctoast.util.ToastShape
import com.gdsc.gdsctoast.util.ToastType
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
        // To Register
        signInBinding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    // get login data
    private fun signIn() {

        val email = signInBinding.edtEmail.text.toString()
        val password = signInBinding.edtPassword.text.toString()

        if (email.isEmpty()){
            signInBinding.edtEmail.error = "Email is required!"
            signInBinding.edtEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signInBinding.edtEmail.error = "Please input valid email!"
            signInBinding.edtEmail.requestFocus()
            return
        }
        if (password.isEmpty()){
            signInBinding.edtPassword.error = "Password is required!"
            signInBinding.edtPassword.requestFocus()
            return
        }
        if (password.length < 6){
            signInBinding.edtPassword.error = "Minimal password length should be 6 characters!"
            signInBinding.edtPassword.requestFocus()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    GDSCToast.showAnyToast(this) {
                        it.apply {
                            text = "Login Success!"
                            duration = Toast.LENGTH_LONG
                            showLogo = true
                            toastType = ToastType.SUCCESS
                            toastShape = ToastShape.ROUNDED
                        }
                    }
                    startActivity(Intent(this, HomeActivity::class.java))
                    finishAffinity()
                } else {
                    // If sign in fails, display a message to the user.
                    GDSCToast.showAnyToast(this) {
                        it.apply {
                            text = "User does not exist!"
                            duration = Toast.LENGTH_LONG
                            showLogo = true
                            toastType = ToastType.ERROR
                            toastShape = ToastShape.ROUNDED
                        }
                    }
                }
            }
    }
}