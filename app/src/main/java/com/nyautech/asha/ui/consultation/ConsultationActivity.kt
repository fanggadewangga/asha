package com.nyautech.asha.ui.consultation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nyautech.asha.ui.home.HomeActivity
import com.nyautech.asha.R
import com.nyautech.asha.adapter.ConsultationAdapter
import com.nyautech.asha.adapter.ExpertChategoryAdapter
import com.nyautech.asha.databinding.ActivityConsultationBinding
import com.nyautech.asha.model.Expert
import com.nyautech.asha.model.User
import com.nyautech.asha.ui.article.ExploreActivity


private lateinit var binding : ActivityConsultationBinding
private lateinit var mAuth: FirebaseAuth
private lateinit var expertDatabase: DatabaseReference
private lateinit var adapter : ConsultationAdapter

@Suppress("DEPRECATION")

class ConsultationActivity : AppCompatActivity() {

    private lateinit var expertList : ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        expertList = ArrayList()

        // firebase
        // auth
        mAuth = FirebaseAuth.getInstance()

        // database
        expertDatabase = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("user")
        expertDatabase.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                expertList.clear()

                for (postSnapshot in snapshot.children){
                    val currentExpert = postSnapshot.getValue(User::class.java)
                    expertList.add(currentExpert!!)
                }

                binding.rvChat.adapter?.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        //rv
        binding.rvChat.apply {
            adapter = ConsultationAdapter(this@ConsultationActivity,expertList)
            layoutManager = LinearLayoutManager(this@ConsultationActivity,LinearLayoutManager.VERTICAL,false)
        }

        // nav
        binding.icArticle.setOnClickListener {
            startActivity(Intent(this,ExploreActivity::class.java))
        }
        binding.icHome.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }

}