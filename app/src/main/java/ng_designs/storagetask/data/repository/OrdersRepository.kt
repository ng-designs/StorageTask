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

    override val ordersFlow: Flow<List<Order>> = dao.getAll().map { storedList ->
        storedList.map { it.toOrder() }
    }

    override suspend fun saveOrder(app: Order) = dao.insert(app.toDbOrder())

    override suspend fun removeOrder(app: Order) = dao.delete(app.toDbOrder())

    override suspend fun updateOrder(app: Order) = dao.update(app.toDbOrder())

}