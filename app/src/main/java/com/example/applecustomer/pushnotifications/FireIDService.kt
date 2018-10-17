package com.example.applecustomer.pushnotifications

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class FireIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().getToken()
        Log.e("TAG","token is in onreferesh $token")

    }
}