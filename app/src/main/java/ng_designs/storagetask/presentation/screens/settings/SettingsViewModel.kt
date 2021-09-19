package ng_designs.storagetask.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ng_designs.storagetask.data.repository.OrdersRepository
import ng_designs.storagetask.domain.utils.locateByLazy

class SettingsViewModel: ViewModel() {

    private val repositoryImpl: OrdersRepository by locateByLazy()

    fun clearTable() {
        viewModelScope.launch { repositoryImpl.clearTable() }
    }
}