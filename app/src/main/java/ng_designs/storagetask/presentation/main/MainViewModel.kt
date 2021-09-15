package ng_designs.storagetask.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ng_designs.storagetask.data.repository.OrdersRepository
import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.domain.utils.locateByLazy

class MainViewModel : ViewModel() {

    private val repository: OrdersRepository by locateByLazy()

    val orders = repository.getAll().asLiveDataFlow()

    fun save(order: Order) {
        viewModelScope.launch { repository.saveOrder(order) }
    }

    fun delete(order: Order) {
        viewModelScope.launch { repository.removeOrder(order) }
    }

    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

}