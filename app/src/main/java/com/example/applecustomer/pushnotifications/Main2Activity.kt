package com.example.applecustomer.pushnotifications

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        onNewIntent(intent)

    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val extras = intent?.extras

        if (extras != null) {

            Log.e("NAME ACTIVTY","is${extras.getString("name")}")

            val name = extras.getString("name")
            val address = extras.getString("address")
            val id = extras.getInt("id")
            val pincode = extras.getString("pin")

            val nomanager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nomanager.cancel(id)

            nameTV.text = name
            addressTV.text = address
            pincodeTV.text = pincode
        }
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val upIntent = Intent(this, Main2Activity::class.java)
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is not part of the application's task, so
                    // create a new task
                    // with a synthesized back stack.
                    TaskStackBuilder
                            .from(this)
                            .addNextIntent(Intent(this, MainActivity::class.java))
                            .addNextIntent(upIntent).startActivities()
                    finish()
                } else {
                    // This activity is part of the application's task, so simply
                    // navigate up to the hierarchical parent activity.
                    NavUtils.navigateUpTo(this, upIntent)
                }
                return true
            }
        }
        else -> super.onOptionsItemSelected(item)
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                val upIntent: Intent? = NavUtils.getParentActivityIntent(this)

                when {
                    upIntent == null -> throw IllegalStateException("No Parent Activity Intent")
                    NavUtils.shouldUpRecreateTask(this, upIntent) -> {
                        // This activity is NOT part of this app's task, so create a new task
                        // when navigating up, with a synthesized back stack.
                        TaskStackBuilder.create(this)
                                .addNextIntent(Intent(this, MainActivity::class.java))
                                .addNextIntent(upIntent).startActivities()
                        finish()
                    }
                    else -> {
                        // This activity is part of this app's task, so simply
                        // navigate up to the logical parent activity.
                        NavUtils.navigateUpTo(this, upIntent)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    /* override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when (item.itemId) {
             android.R.id.home -> {
                 // Respond to the action bar's Up/Home button
                 NavUtils.navigateUpFromSameTask(this)
                 return true
             }
         }
         return super.onOptionsItemSelected(item)
     }*/
}
