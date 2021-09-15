package ng_designs.storagetask.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import ng_designs.storagetask.domain.entities.Order

class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean = oldItem == newItem
}