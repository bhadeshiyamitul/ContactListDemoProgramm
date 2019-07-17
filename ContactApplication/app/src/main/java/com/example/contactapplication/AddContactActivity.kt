package com.example.contactapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.contactapplication.Dataclasses.ContactBook
import java.lang.Exception
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream


class AddContactActivity : AppCompatActivity() {

    lateinit var userProfile:ImageView
     var bitmap:Bitmap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        //var arrayList: ArrayList<String> = ArrayList();
        supportActionBar!!.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#8A606D"))
        )

        userProfile= findViewById<ImageView>(R.id.profile)
        var btnAdd=findViewById<Button>(R.id.btn_add)
        var name=findViewById<EditText>(R.id.ed_nm)
        var phoneNumber=findViewById<EditText>(R.id.ed_phno)
        var email= findViewById<EditText>(R.id.ed_email)


        fun choosePictureFromGallery() {

            var intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        fun takePictureFromCamera() {

            var cIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cIntent, 200)
        }

        fun showPictureDialog() {
            var alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@AddContactActivity)
            alertDialog.setTitle("Select Action::")
            var selectPhotoArray = arrayOf<String>("select picture from gallery", "capture picture from cemera")

            alertDialog.setItems(selectPhotoArray, DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> choosePictureFromGallery()
                    1 -> takePictureFromCamera()
                }
            })
            alertDialog.show()
        }
        userProfile.setOnClickListener {
            showPictureDialog()
        }

        btnAdd.setOnClickListener {
            val drawable = userProfile.drawable
            val bitmap = (drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            var name=name.text.toString()
            var phoneNumber=phoneNumber.text.toString()
            var emailId=email.text.toString()

            var intent= Intent(this@AddContactActivity,ContactListActivity::class.java)
            intent.putExtra("Name",name)
            intent.putExtra("PhoneNo",phoneNumber)
            intent.putExtra("Email",emailId)
            intent.putExtra("UserImg",b)
            setResult(Activity.RESULT_OK,intent)
            onBackPressed()
            finish()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    var uri = data.data
                    Log.e("bbbbb", "second")
                    try {
                        var bitmap: Bitmap =
                            MediaStore.Images.Media.getBitmap(this@AddContactActivity.contentResolver, uri)
                        userProfile.setImageBitmap(bitmap)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } else if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {

                val bitmap = data!!.extras!!.get("data") as Bitmap
                userProfile.setImageBitmap(bitmap)

            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
