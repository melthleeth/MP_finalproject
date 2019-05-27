package com.example.webcrawler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class DataAdapter (var arr: ArrayList<Data1>):
        RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.layout_board, p0, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.vTitle.text = arr[p1].dTitle.toString()
        p0.vDate.text = arr[p1].dDate.toString()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var vTitle: TextView
        var vDate: TextView

        init {
            vTitle = itemView.findViewById(R.id.txt_title)
            vDate = itemView.findViewById(R.id.txt_date)
        }
    }
}