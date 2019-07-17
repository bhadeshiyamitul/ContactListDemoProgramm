package com.example.contactapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import com.example.contactapplication.Dataclasses.ContactBook
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.*
import java.io.ByteArrayOutputStream


class ContactListActivity : AppCompatActivity() {

    lateinit var customListView: CustomListView
    lateinit var listView: ListView
    var name: String = ""
    var number: String = ""
    var email:String=""
    var bmp:Bitmap?=null
    var nm = ""
    var no = ""
    var ed=""
    var arrayList_details: ArrayList<ContactBook> = ArrayList();
    lateinit var tvName: TextView
    lateinit var tvNumber: TextView
    lateinit var tvEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)
        listView = findViewById(R.id.contact_list_view) as ListView


        getSupportActionBar()!!.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#8A606D"))
        );



        listView.setOnItemClickListener { parent, view, position, id ->

            var linearLayoutParent = view as LinearLayout
            var c = ContactBook()
            var itemPosition = position
            var value = listView.getItemAtPosition(position)
            var hexaImageView=linearLayoutParent.getChildAt(0) as HexagonMaskView
            val linearLayoutChild = linearLayoutParent.getChildAt(1) as LinearLayout

            tvName = linearLayoutChild.findViewById(R.id.txt_name) as TextView
            tvNumber = linearLayoutChild.findViewById(R.id.txt_phone) as TextView
            tvEmail=linearLayoutChild.findViewById(R.id.txt_email) as TextView
            var btnInfo=linearLayoutParent.findViewById(R.id.btn_info) as ImageView
            var hexaImage=linearLayoutParent.findViewById(R.id.image) as HexagonMaskView

            //hexaImage.setImageResource(R.drawable.back)



            tvName = linearLayoutChild.getChildAt(0) as TextView
            tvNumber = linearLayoutChild.getChildAt(1) as TextView
            tvEmail=linearLayoutChild.getChildAt(2) as TextView

            Toast.makeText(applicationContext, "$itemPosition \n$value", Toast.LENGTH_SHORT).show()
            val drawable = hexaImage.getDrawable()
            val bitmap = (drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            var i = Intent(this@ContactListActivity, EditActivity::class.java)
            i.putExtra("EditName", tvName.text.toString())
            i.putExtra("EditNumber", tvNumber.text.toString())
            i.putExtra("Position", itemPosition)
            i.putExtra("Image",b)
            i.putExtra("Email",tvEmail.text.toString())
            startActivityForResult(i, 200)
            //customListView.notifyDataSetChanged()
            Log.e("edit data", "${nm} and ${no}")
            Log.e("get data", tvName.text.toString())

            btnInfo.setOnClickListener {
                val drawable = hexaImage.getDrawable()
                val bitmap = (drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val b = baos.toByteArray()
                var intentInfo=Intent(this@ContactListActivity,InfoActivity::class.java)
                intentInfo.putExtra("InfoName",tvName.text.toString())
                intentInfo.putExtra("InfoNumber",tvNumber.text.toString())
                intentInfo.putExtra("InfoImg",b)
                intentInfo.putExtra("InfoEmail",tvEmail.text.toString())
                startActivity(intentInfo)
                Toast.makeText(applicationContext,"Info button Clicked",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.contact_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.menu_add) {

            var intent = Intent(this@ContactListActivity, AddContactActivity::class.java)
            startActivityForResult(intent, 100)
        }
        if (item!!.itemId == R.id.menu_info) {
            Toast.makeText(applicationContext, "info data", Toast.LENGTH_LONG).show()
            // listView.adapter=customListView
            // Log.e("custom list view","$arrayList_details")
        }
        return super.onOptionsItemSelected(item);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        var c = ContactBook()
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                var b = data!!.getByteArrayExtra("UserImg")
                bmp = BitmapFactory.decodeByteArray(b, 0, b.size)
                name = data!!.getStringExtra("Name")
                number = data!!.getStringExtra("PhoneNo")
                email=data!!.getStringExtra("Email")
                c.pName = name
                c.pNumber = number
                c.pEmail=email
                c.PImage=bmp
                arrayList_details.add(c)
                customListView = CustomListView(this@ContactListActivity, arrayList_details)
                listView.adapter = customListView
                // listView.invalidateViews()
                customListView.notifyDataSetChanged()
            }
        }
        if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {
                nm = data!!.getStringExtra("EdName")
                no = data!!.getStringExtra("EdNo")
                ed=data!!.getStringExtra("EmailId")

                var position = data.getIntExtra("Position", -1)
                var b = data!!.getByteArrayExtra("Image")
                bmp = BitmapFactory.decodeByteArray(b, 0, b.size)
                c.pNumber = no
                c.pName = nm
                c.PImage=bmp
                c.pEmail=ed
                tvName.setText(nm)
                tvNumber.setText(no)

                arrayList_details.set(position, c)

                customListView.notifyDataSetChanged()
                //listView.adapter = customListView

            }
        }


        Log.e("array", "${arrayList_details}")


    }
}
