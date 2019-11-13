package com.example.opiekun

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_senior.view.*
import kotlinx.android.synthetic.main.senior_list_item.view.*
import kotlin.collections.ArrayList


class SeniorAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var seniorNamesList: List<Senior> = ArrayList()
    var onItemClick: ((Senior) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SeniorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.senior_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return seniorNamesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is SeniorViewHolder ->{
               holder.bind(seniorNamesList[position])
            }
        }
    }
    fun submitList(seniorList: List<Senior>){
        seniorNamesList=seniorList
    }
    inner class SeniorViewHolder constructor(
        itemView : View
    ):RecyclerView.ViewHolder(itemView){
        val seniorName=itemView.senior_name
        val seniorImage=itemView.senior_image
        fun bind(senior: Senior){
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(senior.image)
                .into(seniorImage)
            seniorName.setText(senior.name)
        }
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(seniorNamesList[adapterPosition])
            }
        }
    }


}
data class Senior(

    var name: String,

    var image: String,
    var id:String



) {

    override fun toString(): String {
        return "BlogPost(image='$image', username='$name')"
    }


}

