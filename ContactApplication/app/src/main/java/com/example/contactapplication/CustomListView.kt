package com.example.contactapplication

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.contactapplication.Dataclasses.ContactBook

class CustomListView():BaseAdapter() {

    lateinit var context:Context
    lateinit var contactArray:ArrayList<ContactBook>
//lateinit var contact:HashMap<String,String>
  // lateinit var nameArray:ArrayList<String>
   // lateinit var numberArray:ArrayList<String>

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }
    constructor(context: Context,contactArray:ArrayList<ContactBook>):this()
    {
        this.context=context
     this.contactArray=contactArray

    }
    override fun getCount(): Int {
       return contactArray.size
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view= LayoutInflater.from(context).inflate(R.layout.custom_contact_list,parent,false)

        var personName=view.findViewById<TextView>(R.id.txt_name)
        var personNumber=view.findViewById<TextView>(R.id.txt_phone)
        var personImage=view.findViewById(R.id.image) as HexagonMaskView
        var personEmail=view.findViewById(R.id.txt_email) as TextView
        var c=contactArray.get(position)
        if (position % 2 == 1) {
            view.setBackgroundColor(Color.parseColor("#8A606D"));
        } else {
            view.setBackgroundColor(Color.parseColor("#6D3849"));
        }
        personName.setText(c.pName)
        personNumber.setText(c.pNumber)
        personEmail.setText(c.pEmail)
        personImage.setImageBitmap(c.PImage)
        return view
    }
}