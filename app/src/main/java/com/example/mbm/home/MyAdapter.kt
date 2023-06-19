package com.example.mbm.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mbm.Constants
import com.example.mbm.R
import com.example.mbm.databinding.ItemLayoutBinding


class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var items: List<MenuResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<MenuResponse>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuImage: ImageView = itemView.findViewById(R.id.iv)
        val menuName: TextView = itemView.findViewById(R.id.menuName)
        fun bind(item: MenuResponse) {
            //itemView.iv.text = item.name
            if (item.id == Constants.MENU_SYNC_ID) {
                menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_logo_sync))
            }
            else if (item.id == Constants.MENU_ORDER_ID) {
                menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.order3))
            }
            else {
                menuImage.setImageDrawable(null)
            }
            menuName.text = item.menuName
        }
    }

    class MyDiffCallback(private val oldItems: List<MenuResponse>, private val newItems: List<MenuResponse>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return oldItem == newItem
        }
    }
}
