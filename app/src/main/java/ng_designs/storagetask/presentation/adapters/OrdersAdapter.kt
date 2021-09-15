package ng_designs.storagetask.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ng_designs.storagetask.domain.entities.Order

class OrdersAdapter : ListAdapter<Order, OrderViewHolder>(OrderDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder =
        OrderViewHolder.create(parent)

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) =
        holder.onBind(getItem(position))
}