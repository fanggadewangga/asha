package com.nyautech.asha.ui.consultation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nyautech.asha.adapter.ConsultationAdapter
import com.nyautech.asha.databinding.ActivityConsultationDetailBinding
import com.nyautech.asha.model.Expert
import com.nyautech.asha.ui.article.ExploreActivity
import com.nyautech.asha.ui.home.HomeActivity
import com.nyautech.asha.util.Constanta.EXTRA_CATEGORY

private lateinit var binding : ActivityConsultationDetailBinding
private lateinit var mAuth: FirebaseAuth
private lateinit var expertDatabase: DatabaseReference
private lateinit var category : String

class ConsultationDetailActivity : AppCompatActivity() {

    private lateinit var expertList : ArrayList<Expert>

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityConsultationDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        category = intent.getStringExtra(EXTRA_CATEGORY)!!
        binding.tvCategory.text = category

        // firebase
        // auth
        mAuth = FirebaseAuth.getInstance()


        // data
        expertList = arrayListOf()
        getExpertData()


        //rv
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(this@ConsultationDetailActivity,LinearLayoutManager.VERTICAL,false)
        }

        // nav
        binding.icArticle.setOnClickListener {
            startActivity(Intent(this, ExploreActivity::class.java))
        }
        binding.icHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }


    // Fun
    private fun getExpertData(){
        expertDatabase = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("expert")
        expertDatabase.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                expertList.clear()
                if(snapshot.exists()){
                    for (postSnapshot in snapshot.children){
                        val currentExpert = postSnapshot.getValue(Expert::class.java)

                        if (category == "Mental Health"){
                            if (currentExpert != null) {
                                if (currentExpert.category.equals("Mental Health")){
                                    expertList.add(currentExpert)
                                }
                            }
                        }

                        if (category == "Sexual Health"){
                            if (currentExpert != null) {
                                if (currentExpert.category.equals("Sexual Health")){
                                    expertList.add(currentExpert)
                                }
                            }
                        }

                        if (category == "Komnas Perempuan"){
                            if (currentExpert != null) {
                                if (currentExpert.category.equals("Komnas Perempuan")){
                                    expertList.add(currentExpert)
                                }
                            }
                        }

                        if (category == "Law Expert"){
                            if (currentExpert != null) {
                                if (currentExpert.category.equals("Law Expert")){
                                    expertList.add(currentExpert)
                                }
                            }
                        }

                    }
                    //rv
                    binding.rvCategory.adapter = ConsultationAdapter(this@ConsultationDetailActivity, expertList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}