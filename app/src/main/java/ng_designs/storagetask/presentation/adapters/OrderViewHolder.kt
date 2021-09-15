package ng_designs.storagetask.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ng_designs.storagetask.databinding.RecyclerVewItemBinding
import ng_designs.storagetask.domain.entities.Order

class OrderViewHolder (private val binding: RecyclerVewItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    var item: Order? = null
        private set

    fun onBind(item: Order) {
        this.item = item

        views {
            orderId.text = item.id.toString()
            coin.text = item.coinName
            openPrice.text = item.openPrice.toString()
            closePrice.text = item.closePrice.toString()
        }
    }

    private fun <T> views(block: RecyclerVewItemBinding.() -> T): T? = binding.block()

    companion object {
        fun create(parent: ViewGroup) = RecyclerVewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let(::OrderViewHolder)
    }

}