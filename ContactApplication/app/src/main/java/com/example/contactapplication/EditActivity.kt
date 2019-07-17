package com.example.contactapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_contact.*
import kotlinx.android.synthetic.main.activity_edit.*
import java.io.ByteArrayOutputStream
import java.lang.Exception

class EditActivity : AppCompatActivity() {

    lateinit var profile:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        getSupportActionBar()!!.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#8A606D"))
        );
        var edName=findViewById(R.id.ed_nm) as EditText
        var edNumber=findViewById(R.id.ed_phno) as EditText
        var btnEdit=findViewById(R.id.btn_edit) as Button
        var edEmail=findViewById(R.id.ed_email) as EditText
        profile=findViewById(R.id.profile) as ImageView
        fun choosePictureFromGallery() {

            var intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        fun takePictureFromCamera() {

            var cIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cIntent, 200)
        }

        fun showPictureDialog() {
            var alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@EditActivity)
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
        profile.setOnClickListener {
            showPictureDialog()
        }


        var int=intent
        var extras=int.extras
        if(extras!=null)
        {
            var nm=extras.getString("EditName")
            var ph=extras.getString("EditNumber")

            var ed=extras.getString("Email")
            var b = extras.getByteArray("Image")
            var bmp = BitmapFactory.decodeByteArray(b, 0, b.size)

            edName.setText(nm)
            edNumber.setText(ph)
            edEmail.setText(ed)
            profile.setImageBitmap(bmp)
        }

        btnEdit.setOnClickListener {

            val drawable = profile.getDrawable()
            val bitmap = (drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()

            var name=edName.text.toString()
            var no=edNumber.text.toString()
            var ed=edEmail.text.toString()
            var position=extras.getInt("Position")




            var intent=Intent(this@EditActivity,ContactListActivity::class.java)
            intent.putExtra("EdName",name)
            intent.putExtra("EdNo",no)
            intent.putExtra("Position",position)
            intent.putExtra("Image",b)
            intent.putExtra("EmailId",ed)
            setResult(Activity.RESULT_OK,intent)
            onBackPressed()
            finish()
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
                            MediaStore.Images.Media.getBitmap(this@EditActivity.getContentResolver(), uri)
                        profile!!.setImageBitmap(bitmap)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } else if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {

                val bitmap = data!!.extras!!.get("data") as Bitmap
                profile!!.setImageBitmap(bitmap)

            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
