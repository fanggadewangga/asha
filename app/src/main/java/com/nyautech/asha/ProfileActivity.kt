package com.nyautech.asha

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.gdsc.gdsctoast.GDSCToast
import com.gdsc.gdsctoast.util.ToastShape
import com.gdsc.gdsctoast.util.ToastType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nyautech.asha.databinding.ActivityProfileBinding

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var imageURI : Uri
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var profilePictureReference: StorageReference
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var userId: String
    private lateinit var imageURL: String
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // firebase
        // auth & database
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")
        val currentUser: FirebaseUser? = mAuth.currentUser
        userId = currentUser?.uid.toString()

        //storage
        firebaseStorage = FirebaseStorage.getInstance()
        profilePictureReference = firebaseStorage.getReference("profile pictures/$userId")

        // gso
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("88902840580-otli1056kp6j8ukeq8ekb2raa1o51ugr.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        var name: String?
        var userName: String?
        var email: String?
        var trustedContact : String?

        database.child(userId).get().addOnSuccessListener { it ->

            //get name from database
            name = it.child("name").value.toString()

            //get userName from database
            userName = it.child("username").value.toString()

            //get email from database
            email = currentUser!!.email.toString()

            // get trusted contact
            trustedContact = it.child("trustedContact").value.toString()


            //set tv
            binding.tvUsersName.text = name
            binding.tvUsersUsername.text = userName
            binding.tvUsersEmail.text = email
            binding.tvUsersTrustedContact.text = trustedContact

            //set iv
            profilePictureReference.downloadUrl.addOnSuccessListener {
                imageURL = it.toString()
                Glide.with(this).load(imageURL).apply(RequestOptions.circleCropTransform()).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.ivUser)
            }


            Log.i("firebase", "Got value ${it.value}")

        }.addOnFailureListener{

            Log.e("firebase", "Error getting data", it)

        }.toString()


        // click
        // user photo
        binding.ivAddPhoto.setOnClickListener {
            selectImage()
        }

        // upload photo

        binding.btnUploadImg.setOnClickListener {
            uploadImage()
        }

        // logout
        binding.btnLogout.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                startActivity(Intent(this , OnboardingActivity::class.java))
                finishAffinity()
            }

            Firebase.auth.signOut()
            intent = Intent(this , OnboardingActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    // fun
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }

        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==100 && resultCode == RESULT_OK){

            imageURI = data?.data!! //
            binding.ivUser.setImageURI(imageURI) // set ImageView with the selected photo
            binding.btnUploadImg.visibility = View.VISIBLE //show upload button
        }
    }

    private fun uploadImage() {
        val fileName = userId
        profilePictureReference = firebaseStorage.getReference("profile pictures/$fileName")
        profilePictureReference.putFile(imageURI).addOnSuccessListener {
            Log.d(ContentValues.TAG, "uploadImageSuccess:$fileName")
            GDSCToast.showAnyToast(this) {
                it.apply {
                    text = "Upload Success"
                    duration = Toast.LENGTH_LONG
                    showLogo = true
                    toastType = ToastType.SUCCESS
                    toastShape = ToastShape.ROUNDED
                }
            }
        }.addOnFailureListener{
            Log.w(ContentValues.TAG, "uploadImageFailed:", it)
        }
    }
}