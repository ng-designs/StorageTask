package ng_designs.storagetask.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ng_designs.storagetask.data.database.db.OrdersDatabase
import ng_designs.storagetask.data.database.utils.toDbOrder
import ng_designs.storagetask.data.database.utils.toOrder
import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.domain.repository.IOrdersRepository

class OrdersRepository(private val db: OrdersDatabase) : IOrdersRepository {
    private val dao get() = db.dao

    override fun getAll(): Flow<List<Order>> = dao.getAll().map { orderList ->
        orderList.map { it.toOrder() }
    }

    override suspend fun saveOrder(order: Order) = dao.insert(order.toDbOrder())

    override suspend fun removeOrder(order: Order) = dao.delete(order.toDbOrder())

    override suspend fun updateOrder(order: Order) = dao.update(order.toDbOrder())

}