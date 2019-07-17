package com.example.contactapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import java.lang.Exception

class UserProfile : AppCompatActivity() {

    lateinit var userProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        userProfile = findViewById(R.id.user_profile) as ImageView

        fun choosePictureFromGallery() {

            var intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        fun takePictureFromCamera() {

            var cIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cIntent, 200)
        }

        fun showPictureDialog() {
            var alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@UserProfile)
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
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    var uri = data!!.data
                    Log.e("bbbbb", "second")
                    try {
                        var bitmap: Bitmap =
                            MediaStore.Images.Media.getBitmap(this@UserProfile.getContentResolver(), uri)
                        userProfile!!.setImageBitmap(bitmap)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } else if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {

                val bitmap = data!!.extras!!.get("data") as Bitmap
                userProfile!!.setImageBitmap(bitmap)

            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}