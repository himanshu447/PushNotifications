package com.example.applecustomer.pushnotifications

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn.setOnClickListener {


            val intent = Intent(this,Main2Activity::class.java)
            startActivity(intent)


            FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            Log.w("TAG", "getInstanceId failed", it.exception)
                            return@addOnCompleteListener;
                        }

                        // Get new Instance ID token
                        val token = it.result.token

                        // Log and toast
                        Log.e("TAG", token)

                    }

        }

    }

}
