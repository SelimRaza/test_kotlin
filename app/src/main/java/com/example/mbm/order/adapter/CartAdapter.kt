package com.example.mbm.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mbm.R
import com.example.mbm.home.DataSelectionInterface
import com.example.mbm.home.ResponseHome
import com.example.mbm.order.viewModel.OrderVM
import java.util.Collections


class CartAdapter(private var dataSelection: DataSelectionInterface, viewModel : OrderVM) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    var dataList: MutableList<ResponseHome.ResponseItem> = ArrayList()
    private val deletedItems: MutableList<DeletedItem> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_rv1, parent, false)
        return MyViewHolder(itemView)
    }

    fun submitData(list: MutableList<ResponseHome.ResponseItem>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.tvName.text = data.iTEMNAME
        holder.tvPrice.text = data.dISTSALEPRICE
        Glide.with(holder.itemView)
            .load(data.iTEMIMAGE)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView)

        if (data.isChecked) {
            holder.ivCheck.visibility = View.VISIBLE
        }
        else holder.ivCheck.visibility = View.GONE

        holder.itemView.setOnClickListener {
            remove(position)
        }


        if (data.isChecked) {
           holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }
        else  holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))

        holder.imageView.setOnClickListener {
            Toast.makeText(holder.itemView.context, data.iTEMNAME, Toast.LENGTH_SHORT).show()
        }
    }

    fun remove(position: Int) {
        val selectedData = dataList[position]
        if (selectedData.isChecked) {
            dataSelection.onRemove(selectedData)
            selectedData.isChecked = false
        } else {
            selectedData.isChecked = true
            dataSelection.onSelect(selectedData)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val ivCheck: ImageView = itemView.findViewById(R.id.ivCheck)
    }


    fun onItemRemoved(pos: Int) {
        deletedItems.clear()
        val deletedOrderProduct: ResponseHome.ResponseItem = dataList[pos]
        deletedItems.add(DeletedItem(pos, deletedOrderProduct))
        dataList.removeAt(pos)
        notifyItemRemoved(pos)
        dataSelection.onRemove(deletedOrderProduct)
    }

    fun restoreItem() {
        for (deletedItem in deletedItems) {
            dataList.add(Math.min(deletedItem.pos, dataList.size), deletedItem.item)
        }

        notifyDataSetChanged()
        deletedItems.clear()
    }

    internal class DeletedItem(var pos: Int, item: ResponseHome.ResponseItem) {
        var item: ResponseHome.ResponseItem
        init {
            this.item = item
        }
    }
}
