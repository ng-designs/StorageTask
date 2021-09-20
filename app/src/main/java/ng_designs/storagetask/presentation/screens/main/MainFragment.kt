package ng_designs.storagetask.presentation.screens.main

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng_designs.storagetask.R
import ng_designs.storagetask.databinding.MainFragmentBinding
import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.domain.utils.locate
import ng_designs.storagetask.presentation.adapters.OrdersAdapter
import ng_designs.storagetask.presentation.dialogs.AddOrderDialog
import ng_designs.storagetask.presentation.helpers.DialogCallbacks
import ng_designs.storagetask.presentation.helpers.SwipeHelper
import ng_designs.storagetask.presentation.screens.MainActivity
import kotlin.system.exitProcess

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private var binding: MainFragmentBinding? = null
    private val adapter: OrdersAdapter? get() = views { mainFragmentRecyclerView.adapter as? OrdersAdapter }


    private val callbackWatcher = object : DialogCallbacks {

        override fun onOrderDataEntered(order: Order) {
            viewModel.save(order)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater).also { binding = it }.root


    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        views {
            mainFragmentRecyclerView.adapter = OrdersAdapter()
            SwipeHelper(viewModel::delete).attachToRecyclerView(mainFragmentRecyclerView)

            buttonAddNewOrder.setOnClickListener {
                AddOrderDialog(requireActivity() as MainActivity, callbackWatcher)
            }
        }
        viewModel.orders.onEach(::submitOrders).launchIn(lifecycleScope)
    }

    private fun submitOrders(orders: List<Order>) {
        adapter?.submitList(orders)
    }

    private fun <T> views(block: MainFragmentBinding.() -> T): T? = binding?.block()

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_exit -> {
                activity?.finish()
                exitProcess(0)
            }
            R.id.btn_settings -> {
                findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}