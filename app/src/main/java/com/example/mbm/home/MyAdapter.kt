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
    var onItemClickListener: ((MenuResponse) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(items[position])
        }
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

            if (item.id == Constants.MENU_SYNC_ID) {
            menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.snc))
            }
            else if (item.id == Constants.MENU_ORDER_ID) {
                menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.order3))
            }
            else if (item.id == Constants.MENU_ORDER_SR) {
                menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.vansales))
            }

            else if (item.id == Constants.MENU_COLLECTION) {
                menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.collection))
            }

            else if (item.id == Constants.MENU_DASHBOARD) {
                menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.reportd))
            }

            else if (item.id == Constants.MENU_DASHBOARD_A) {
                menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.reports3))
            }
            else if (item.id == Constants.MENU_WORK_NOTE ) {
                menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.worknote))
            }

            else if (item.id == Constants.MENU_SEARCH ) {
                menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.search3))
            }
            else if (item.id == Constants.MENU_DELIVERY ) {
                menuImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.trip))
            }


            else if  (item.id == Constants.MENU_EXIT) {
                menuImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.exit
                    )
                )
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
