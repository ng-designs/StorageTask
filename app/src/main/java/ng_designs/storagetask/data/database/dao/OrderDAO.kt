package ng_designs.storagetask.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ng_designs.storagetask.data.database.entities.dbOrder


@Dao
interface OrderDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: dbOrder)

    @Update
    suspend fun update(order: dbOrder)

    @Delete
    suspend fun delete(order: dbOrder)

    @Query("SELECT * FROM orders")
    fun getAll(): Flow<List<dbOrder>>
}