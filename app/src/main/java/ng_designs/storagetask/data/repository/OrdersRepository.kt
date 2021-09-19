package ng_designs.storagetask.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import ng_designs.storagetask.data.database.db.OrdersDatabase
import ng_designs.storagetask.data.database.utils.toDbOrder
import ng_designs.storagetask.data.database.utils.toOrder
import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.domain.utils.locate

class OrdersRepository(private val db: OrdersDatabase) : IOrdersRepository {
    private val prefs : SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(locate()) }
    private val dao get() = db.dao

    override fun getAll(): Flow<List<Order>>  = dao.getAll().map { orderList ->
        orderList.map { it.toOrder() }
    }

    override fun getAllSortedBy(sortBy: String): Flow<List<Order>> = dao.getSorted(sortBy).map { orderList ->
        orderList.map { it.toOrder() }
    }
    @ExperimentalCoroutinesApi
    override fun sortFlow(): Flow<String> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPrefs, s ->
            if(s == "sort_by") sharedPrefs.getString("sort_by",null)?: run {
                Log.i("SORT FLOW", "Sort preference is null")
                "id" 
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)
        offer(prefs.getString("sort_by",null)!!)

        awaitClose{
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    override suspend fun saveOrder(order: Order) = dao.insert(order.toDbOrder())

    override suspend fun removeOrder(order: Order) = dao.delete(order.toDbOrder())

    override suspend fun updateOrder(order: Order) = dao.update(order.toDbOrder())

    override suspend fun clearTable() { dao.drop(); dao.resetAutoIncrement() }
}