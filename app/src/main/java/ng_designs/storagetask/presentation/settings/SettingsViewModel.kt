package ng_designs.storagetask.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng_designs.storagetask.data.repository.OrdersRepository
import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.domain.utils.locateByLazy

class SettingsViewModel: ViewModel() {

    private val repository: OrdersRepository by locateByLazy()

    fun dropTable() {
        viewModelScope.launch { repository.dropTable() }
    }
}