package com.umc.healthper.ui.tutorial.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.R

class tutorialVPAdapter(imglist: ArrayList<Int>) : RecyclerView.Adapter<tutorialVPAdapter.PagerViewHolder>() {
    var item = imglist

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.list.setImageResource(item[position])
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.item_tutorial_img, parent, false)){

        val list = itemView.findViewById<ImageView>(R.id.imageView_tutorial)
    }
}