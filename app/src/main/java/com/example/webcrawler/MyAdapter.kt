package com.example.webcrawler


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class MyAdapter//생성자
    (//데이터 배열 선언
    val mList: ArrayList<ItemObject>
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView_img: ImageView
        val textView_title: TextView
        val textView_release: TextView
        val texView_director: TextView

        init {

            imageView_img = itemView.findViewById(R.id.imageView_img) as ImageView
            textView_title = itemView.findViewById(R.id.textView_title)
            textView_release = itemView.findViewById(R.id.textView_release)
            texView_director = itemView.findViewById(R.id.textView_director)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.textView_title.text = mList[position].title.toString()
        holder.textView_release.text = mList[position].release.toString()
        holder.texView_director.text = mList[position].director.toString()

        Glide.with(holder.itemView).load(mList[position].img_url)
            .override(300, 400)
            .into(holder.imageView_img)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}