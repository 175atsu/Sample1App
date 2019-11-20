package com.example.sample1app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SampleRecyclerViewAdapter(private val list: List<SampleViewModel>, private val listener: ListListener): RecyclerView.Adapter<SampleViewHolder>() {

    //レイアウト
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowView = layoutInflater.inflate(R.layout.row, parent, false)
        return SampleViewHolder(rowView)
    }

    //データ
    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.shopName.text =  list[position].name
        holder.shopImage.text = list[position].image
        holder.itemView.setOnClickListener {
            listener.onClickRow(it,list[position])
        }
    }

    //リストの数取得
    override fun getItemCount(): Int {
        return list.size
    }

    //質問
    interface ListListener {
        fun onClickRow(tappedView: View, rowModel: SampleViewModel)
    }
}