package ng_designs.storagetask.presentation.main

import android.annotation.SuppressLint
import android.content.Context
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
import ng_designs.storagetask.domain.utils.locate
import ng_designs.storagetask.domain.utils.locateByLazy

@SuppressLint("RestrictedApi")
class MainViewModel : ViewModel() {
    private val repository: OrdersRepository by locateByLazy()
    private val prefManager : PreferenceManager by lazy { PreferenceManager(locate()) }
    private val sortBy = prefManager.sharedPreferences.all["sort_by"].toString()


    var orders = repository.getAllSortedBy(sortBy).asLiveDataFlow()

    fun save(order: Order) {
        viewModelScope.launch { repository.saveOrder(order) }
    }

    fun delete(order: Order) {
        viewModelScope.launch { repository.removeOrder(order) }
    }

    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

}