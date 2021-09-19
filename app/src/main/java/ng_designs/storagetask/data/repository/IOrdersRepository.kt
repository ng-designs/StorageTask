package ng_designs.storagetask.data.repository

import kotlinx.coroutines.flow.Flow
import ng_designs.storagetask.domain.entities.Order

interface IOrdersRepository {
    fun getAll(): Flow<List<Order>>
    fun getAllSortedBy(sortBy: String): Flow<List<Order>>
    fun sortFlow() : Flow<String>
    suspend fun saveOrder(order: Order)
    suspend fun removeOrder(order: Order)
    suspend fun updateOrder(order: Order)
    suspend fun clearTable() //TODO: Realize table name input
}