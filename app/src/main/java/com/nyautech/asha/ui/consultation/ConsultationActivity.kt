package com.nyautech.asha.ui.consultation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nyautech.asha.ui.home.HomeActivity
import com.nyautech.asha.adapter.ConsultationAdapter
import com.nyautech.asha.databinding.ActivityConsultationBinding
import com.nyautech.asha.model.Expert
import com.nyautech.asha.ui.article.ExploreActivity
import com.nyautech.asha.util.Constanta.EXTRA_CATEGORY


private lateinit var binding : ActivityConsultationBinding
private lateinit var mAuth: FirebaseAuth
private lateinit var expertDatabase: DatabaseReference
@SuppressLint("StaticFieldLeak")
@Suppress("DEPRECATION")

class ConsultationActivity : AppCompatActivity() {

    private lateinit var expertList : ArrayList<Expert>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // firebase
        // auth
        mAuth = FirebaseAuth.getInstance()

        expertList = arrayListOf<Expert>()
        getExpertData()

        //rv
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(this@ConsultationActivity,LinearLayoutManager.VERTICAL,false)
        }

        // click
        binding.cvMentalHealth.setOnClickListener {
            val intent = Intent(this,ConsultationDetailActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY,"Mental Health")
            startActivity(intent)
        }
        binding.cvSexualHealth.setOnClickListener {
            val intent = Intent(this,ConsultationDetailActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY,"Sexual Health")
            startActivity(intent)
        }
        binding.cvKomnas.setOnClickListener {
            val intent = Intent(this,ConsultationDetailActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY,"Komnas Perempuan")
            startActivity(intent)
        }
        binding.cvLawExpert.setOnClickListener {
            val intent = Intent(this,ConsultationDetailActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY,"Law Expert")
            startActivity(intent)
        }


        // nav
        binding.icArticle.setOnClickListener {
            startActivity(Intent(this,ExploreActivity::class.java))
            finishAffinity()
        }
        binding.icHome.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finishAffinity()
        }
    }

    // fun
    private fun getExpertData(){
        expertDatabase = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("expert")
        expertDatabase.addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                expertList.clear()
                if(snapshot.exists()){
                    for (postSnapshot in snapshot.children){
                        val currentExpert = postSnapshot.getValue(Expert::class.java)
                        expertList.add(currentExpert!!)
                    }
                    //rv
                    binding.rvChat.adapter = ConsultationAdapter(this@ConsultationActivity, expertList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}