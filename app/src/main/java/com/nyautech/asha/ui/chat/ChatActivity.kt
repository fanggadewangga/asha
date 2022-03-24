package com.nyautech.asha.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nyautech.asha.adapter.MessageAdapter
import com.nyautech.asha.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatBinding
    private lateinit var messageList : ArrayList<com.nyautech.asha.model.Message>
    private lateinit var messageAdapter : MessageAdapter
    private lateinit var database : DatabaseReference

    // to create a unique room for peer of sender and receiver
    private var receiverRoom: String? = null
    private var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val expertName = intent.getStringExtra("name")
        val expertCategory = intent.getStringExtra("category")
        val expertId = intent.getStringExtra("eid")
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        // firebase
        database = FirebaseDatabase.getInstance("https://asha-21f6d-default-rtdb.asia-southeast1.firebasedatabase.app").reference

        // create a unique chat room for sender and receiver
        senderRoom = expertId + userId
        receiverRoom = userId + expertId


        // rv
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = messageAdapter
        }


        // adding data to recycler view
        database.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    // loop trough all messages
                    for (postSnapshot in snapshot.children){
                        // get message
                        val message = postSnapshot.getValue(com.nyautech.asha.model.Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        binding.tvChatReceiverName.text = expertName
        binding.tvChatReceiverCategory.text = expertCategory


        // adding the message to database
        binding.btnSendMessage.setOnClickListener {

            val message = binding.edtMessageBox.text.toString()
            val messageObject = com.nyautech.asha.model.Message(message,userId)

            database.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {

                    database.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)

                }
            // clear message box after send message
            binding.edtMessageBox.setText("")
        }





    }
}