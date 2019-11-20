package com.example.sample1app

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SampleViewHolder(itemeView: View): RecyclerView.ViewHolder(itemeView) {
    val shopName: TextView = itemeView.findViewById(R.id.textView)
    val shopImage: TextView = itemeView.findViewById(R.id.imageView)
}