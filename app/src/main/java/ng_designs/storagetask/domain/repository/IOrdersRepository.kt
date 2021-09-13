package ng_designs.storagetask.domain.repository

import kotlinx.coroutines.flow.Flow
import ng_designs.storagetask.domain.entities.Order

interface IOrdersRepository {
    val ordersFlow: Flow<List<Order>>
    suspend fun saveOrder(app: Order)
    suspend fun removeOrder(app: Order)
    suspend fun updateOrder(app: Order)
}