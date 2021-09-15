package ng_designs.storagetask.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng_designs.storagetask.databinding.MainFragmentBinding
import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.presentation.MainActivity
import ng_designs.storagetask.presentation.adapters.OrdersAdapter
import ng_designs.storagetask.presentation.dialogs.add_order.AddOrderDialog
import ng_designs.storagetask.presentation.dialogs.add_order.AlertDialogCallbacks

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val adapter: OrdersAdapter? get() = views { mainFragmentRecyclerView.adapter as? OrdersAdapter }
    private var binding: MainFragmentBinding? = null

    private val callbackWatcher = object : AlertDialogCallbacks {
        override fun OnOrderDataEntered(order: Order) {
            viewModel.save(order)
            viewModel.orders.onEach(::submitOrders).launchIn(lifecycleScope)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views {
            mainFragmentRecyclerView.adapter = OrdersAdapter()
//            SwipeHelper(viewModel::delete).attachToRecyclerView(notesList)

            buttonAddNewOrder.setOnClickListener {
                AddOrderDialog(requireActivity() as MainActivity, callbackWatcher)
//                viewModel.save(newOrder)
//                Log.i("USER OUTPUT",newOrder.toString())
            }
        }

        viewModel.orders.onEach(::submitOrders).launchIn(lifecycleScope)
    }

    private fun submitOrders(orders: List<Order>) {
        adapter?.submitList(orders)
    }

    private fun <T> views(block: MainFragmentBinding.() -> T): T? = binding?.block()

    override fun onResume() {
        viewModel.orders.onEach(::submitOrders).launchIn(lifecycleScope)
        super.onResume()
    }
}