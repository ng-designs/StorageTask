package ng_designs.storagetask.presentation.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.*
import ng_designs.storagetask.R
import ng_designs.storagetask.databinding.MainFragmentBinding
import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.presentation.MainActivity
import ng_designs.storagetask.presentation.adapters.OrdersAdapter
import ng_designs.storagetask.presentation.dialogs.add_order.AddOrderDialog
import ng_designs.storagetask.presentation.dialogs.add_order.AlertDialogCallbacks
import java.lang.Exception
import kotlin.system.exitProcess

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val adapter: OrdersAdapter? get() = views { mainFragmentRecyclerView.adapter as? OrdersAdapter }
    private var binding: MainFragmentBinding? = null

    private val callbackWatcher = object : AlertDialogCallbacks {
        override fun onOrderDataEntered(order: Order) {
            viewModel.save(order)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater).also { binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)



        views {
            mainFragmentRecyclerView.adapter = OrdersAdapter()
//            SwipeHelper(viewModel::delete).attachToRecyclerView(notesList)

            buttonAddNewOrder.setOnClickListener {
                AddOrderDialog(requireActivity() as MainActivity, callbackWatcher)
            }
        }

        Log.i("onCreateView", "Before submitOrders")
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

//    override fun onClickItem(note: AppNote) {
//        val bundle = Bundle()
//        bundle.putSerializable("note", note)
//        findNavController().navigate(R.id.action_mainFragment_to_noteFragment, bundle)
//    }
}