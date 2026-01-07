package com.ahead.assingment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahead.assingment.R
import com.bumptech.glide.Glide

class MenuAdapter(
    private var displayItems: List<DisplayItem>,
    private val onToggleClicked: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADING = 0
        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_TOGGLE = 2
        const val VIEW_TYPE_FULL_WIDTH = 3
    }

    fun updateList(newList: List<DisplayItem>) {
        displayItems = newList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = when (displayItems[position]) {
        is DisplayItem.Heading -> VIEW_TYPE_HEADING
        is DisplayItem.FeatureItem -> VIEW_TYPE_ITEM
        is DisplayItem.AppsToggle -> VIEW_TYPE_TOGGLE
        is DisplayItem.FullWidthItem -> VIEW_TYPE_FULL_WIDTH
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADING -> HeadingViewHolder(
                inflater.inflate(
                    R.layout.item_heading,
                    parent,
                    false
                )
            )

            VIEW_TYPE_ITEM -> ItemViewHolder(
                inflater.inflate(
                    R.layout.item_feature_card,
                    parent,
                    false
                )
            )

            VIEW_TYPE_TOGGLE -> ToggleViewHolder(
                inflater.inflate(
                    R.layout.item_toggle,
                    parent,
                    false
                )
            )

            VIEW_TYPE_FULL_WIDTH -> ItemViewHolder(
                inflater.inflate(
                    R.layout.item_full_width_card,
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = displayItems[position]) {
            is DisplayItem.Heading -> (holder as HeadingViewHolder).headingText.text = item.label
            is DisplayItem.FeatureItem -> {
                (holder as ItemViewHolder).label.text = item.menuItem.label
                Glide.with(holder.itemView.context)
                    .load(item.menuItem.icon)
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .into(holder.icon)
            }

            is DisplayItem.AppsToggle -> {
                holder as ToggleViewHolder
                holder.toggleText.text =
                    if (item.isExpanded) "See Less" else "See All"

                holder.toggleText.setOnClickListener {
                    onToggleClicked()
                }
            }

            is DisplayItem.FullWidthItem -> {
                (holder as ItemViewHolder).label.text = item.menuItem.label
                Glide.with(holder.itemView.context)
                    .load(item.menuItem.icon)
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .into(holder.icon)
            }
        }
    }

    override fun getItemCount(): Int = displayItems.size

    class HeadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headingText: TextView = itemView.findViewById(R.id.headingText)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.featureIcon)
        val label: TextView = itemView.findViewById(R.id.featureLabel)
    }

    class ToggleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val toggleText: TextView = itemView.findViewById(R.id.toggleText)
    }
}
