package com.app.colourlovers.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.colourlovers.R
import com.app.colourlovers.data.ColourResponseItem
import com.app.colourlovers.databinding.RowItemBinding
import com.bumptech.glide.Glide

class ColoursRecyclerAdapter(private val context: Context, private val colourList: ArrayList<ColourResponseItem>) : RecyclerView.Adapter<ColoursRecyclerAdapter.ViewHolder>() {

    lateinit var sharedPreferences: SharedPreferences;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowItemBinding.inflate(inflater)
        sharedPreferences = context.getSharedPreferences("sharedPrefColour", Context.MODE_PRIVATE)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(colourList[position])
    }

    override fun getItemCount(): Int {
        return colourList.size
    }

    inner class ViewHolder(val mBinding: RowItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        fun bind(item: ColourResponseItem) {
            mBinding.hexTv.text = item.hex
            mBinding.titleLbl.text = item.title
            Glide.with(context).load(item.imageUrl).into(mBinding.colorIv)

            mBinding.likeIv.setOnClickListener {
                item.liked = !item.liked

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                if (item.liked) {
                    editor.putBoolean(item.hex, true)
                } else {
                    editor.remove(item.hex)
                }
                editor.apply()
                notifyItemChanged(adapterPosition)
            }

            mBinding.root.setOnClickListener {
                context.startActivity(Intent(context, ColorWebView::class.java).putExtra("URL", item.url))
            }


            if (item.liked) {
                mBinding.likeIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_bookmark_24))
            } else {
                mBinding.likeIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_bookmark_border_24))
            }

        }
    }
}