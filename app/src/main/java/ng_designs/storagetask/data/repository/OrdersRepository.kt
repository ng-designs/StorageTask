package ng_designs.storagetask.data.repository

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import ng_designs.storagetask.data.database.cursor.CursorDao
import ng_designs.storagetask.data.database.db.OrdersDatabase
import ng_designs.storagetask.data.database.room.OrderDao
import ng_designs.storagetask.data.database.utils.toDbOrder
import ng_designs.storagetask.data.database.utils.toOrder
import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.domain.repository.IOrdersRepository
import ng_designs.storagetask.domain.utils.locate

class OrdersRepository(private val db: OrdersDatabase) : IOrdersRepository {
    private val prefs : SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(locate()) }
    private val room get() = db.dao
    private val curs get() = CursorDao(locate())

    @ExperimentalCoroutinesApi
    override fun getAllSortedBy(sortBy: String): Flow<List<Order>> = daoFlow()
        .flatMapLatest { dao -> dao.getSorted(sortBy) }
        .map { ordersList -> ordersList.map { it.toOrder() } }

    @ExperimentalCoroutinesApi
    override fun sortFlow(): Flow<String> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPrefs, s ->
            if (s == "sort_by") sharedPrefs.getString("sort_by", null) ?: run { "id" }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)

        offer(prefs.getString("sort_by",null)?: "id")

        awaitClose{
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    @ExperimentalCoroutinesApi
    private fun daoFlow(): Flow<OrderDao> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPrefs, s ->
            if(s == "access_type"){
                when(sharedPrefs.getString(s,null)){
                    "Room" -> room
                    else -> curs
                }
            }
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)

        offer (
            getDaoBySetting(prefs.getString("access_type",null)?: "Room")
        )

        awaitClose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    private fun getDaoBySetting(setting:String) : OrderDao {
        return when(prefs.getString("access_type",null)){
            "Room" -> room
            else -> curs
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun saveOrder(order: Order) =
        getDaoBySetting(prefs.getString("access_type",null)?: "Room")
        .insert(order.toDbOrder())


    override suspend fun removeOrder(order: Order) =
        getDaoBySetting(prefs.getString("access_type",null)?: "Room")
            .delete(order.toDbOrder())

    override suspend fun updateOrder(order: Order) =
        getDaoBySetting(prefs.getString("access_type",null)?: "Room")
            .update(order.toDbOrder())

    override suspend fun clearTable() {
        getDaoBySetting(prefs.getString("access_type",null)?: "Room").drop()
        getDaoBySetting(prefs.getString("access_type",null)?: "Room").resetAutoIncrement()
    }

}