package com.example.mbm.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mbm.R

class MyAdapter2(private val dataList: MutableList<ResponseHome.ResponseItem>, private var dataSelection: DataSelectionInterface) : RecyclerView.Adapter<MyAdapter2.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_rv1, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.tvName.text = data.iTEMNAME
        holder.tvPrice.text = data.dISTSALEPRICE
        Glide.with(holder.itemView)
            .load(data.iTEMNAME)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView)

        if (data.isChecked) {
            holder.ivCheck.visibility = View.VISIBLE
        }
        else holder.ivCheck.visibility = View.GONE

        holder.itemView.setOnClickListener {
            val selectedData = dataList[position]
            if (selectedData.isChecked) {
                dataSelection.onRemove(selectedData)
                selectedData.isChecked = false
            }
            else {
                selectedData.isChecked = true
                dataSelection.onSelect(selectedData)
            }
            notifyDataSetChanged()
        }
        holder.imageView.setOnClickListener {
            Toast.makeText(holder.itemView.context, data.iTEMNAME, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val ivCheck: ImageView = itemView.findViewById(R.id.ivCheck)

    }
}
