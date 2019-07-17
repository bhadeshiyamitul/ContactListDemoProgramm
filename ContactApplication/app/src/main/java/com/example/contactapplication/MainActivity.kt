package com.example.contactapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()!!.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#8A606D"))
        );
        var btnContactList=findViewById<Button>(R.id.btn_contact_list)

        btnContactList.setOnClickListener {

            var intent=Intent(this@MainActivity,ContactListActivity::class.java)
            startActivity(intent)
        }

    }


}
