package com.app.colourlovers.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.app.colourlovers.R
import com.app.colourlovers.data.ColourResponseItem
import com.bumptech.glide.Glide

class ColorAdapter : BaseAdapter {
    var colourList = ArrayList<ColourResponseItem>()
    var context: Context? = null
    private var layoutInflater: LayoutInflater? = null
    lateinit var sharedPreferences: SharedPreferences;

    constructor(context: Context, colourList: ArrayList<ColourResponseItem>) : super() {
        this.context = context
        this.colourList = colourList

        sharedPreferences = context.getSharedPreferences("sharedPrefColour", Context.MODE_PRIVATE)
    }

    override fun getCount(): Int {
        return colourList.size
    }

    override fun getItem(position: Int): Any {
        return colourList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val colour = this.colourList[position]

        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.row_item, null)
        }

        convertView!!.findViewById<ImageView>(R.id.color_iv)
        convertView.findViewById<TextView>(R.id.color_tv).text = colour.title
        convertView.findViewById<TextView>(R.id.hex_tv).text = colour.hex
        Glide.with(context!!).load(colour.imageUrl).into(convertView.findViewById(R.id.color_iv))

        convertView.findViewById<ImageView>(R.id.like_iv).setOnClickListener {
            colour.liked = !colour.liked
            notifyDataSetChanged()

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            if (colour.liked) {
                editor.putBoolean(colour.hex, true)
            } else {
                editor.remove(colour.hex)
            }
            editor.apply()
        }

        convertView.setOnClickListener {
            context?.startActivity(Intent(context, ColorWebView::class.java).putExtra("URL", colour.url))
        }

        if (colour.liked) {
            convertView.findViewById<ImageView>(R.id.like_iv).setImageDrawable(context?.getDrawable(R.drawable.ic_baseline_bookmark_24))
        } else {
            convertView.findViewById<ImageView>(R.id.like_iv).setImageDrawable(context?.getDrawable(R.drawable.ic_baseline_bookmark_border_24))
        }
        return convertView
    }
}