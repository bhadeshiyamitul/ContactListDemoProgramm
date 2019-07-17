package com.example.contactapplication

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        var tvName=findViewById(R.id.info_name) as TextView
        var tvNumber=findViewById(R.id.info_phone) as TextView
        var tvEmail=findViewById(R.id.info_email) as TextView
        var image=findViewById(R.id.info_img) as ImageView




        var int=intent
        var extras=int.extras
        if(extras!=null) {
            var nm = extras.getString("InfoName")
            var ph = extras.getString("InfoNumber")
            var ed = extras.getString("InfoEmail")
            var b = extras.getByteArray("InfoImg")
            var bmp = BitmapFactory.decodeByteArray(b, 0, b.size)

            tvName.setText(nm)
            tvNumber.setText(ph)
            tvEmail.setText(ed)
            image.setImageBitmap(bmp)

        }
    }
}
