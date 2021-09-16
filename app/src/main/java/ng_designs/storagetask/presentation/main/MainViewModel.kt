package ng_designs.storagetask.presentation.main

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ng_designs.storagetask.data.repository.OrdersRepository
import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.domain.utils.locateByLazy

@SuppressLint("RestrictedApi")
class MainViewModel : ViewModel() {
    private val repository: OrdersRepository by locateByLazy()

    var orders = repository.getAllSortedBy("closePrice").asLiveDataFlow()

    fun getSorted(sortBy:String): Flow<List<Order>> {
        return orders.onEach { list ->
            when(sortBy){
                "coinName" -> list.sortedBy { it.coinName }
                "openPrice" -> list.sortedBy { it.openPrice }
                "closePrice" -> list.sortedBy { it.closePrice }
                else -> list.sortedBy { it.id }
            }
        }
    }

    fun getSortedOrders(sortBy:String) {
        orders = repository.getAllSortedBy(sortBy).asLiveDataFlow()
    }

//    fun getSortedOrders(sortBy:String): Flow<List<Order>> {
//        return repository.getAllSortedBy(sortBy).asLiveDataFlow()
//    }

    fun save(order: Order) {
        viewModelScope.launch { repository.saveOrder(order) }
    }

    fun delete(order: Order) {
        viewModelScope.launch { repository.removeOrder(order) }
    }

    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

}