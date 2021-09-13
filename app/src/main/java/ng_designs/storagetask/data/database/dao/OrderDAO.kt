package ng_designs.storagetask.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ng_designs.storagetask.domain.entities.dbOrder


@Dao
interface OrderDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(storedApp: dbOrder)

    @Update
    suspend fun update(storedApp: dbOrder)

    @Delete
    suspend fun delete(storedApp: dbOrder)

    @Query("SELECT * FROM orders")
    fun getAll(): Flow<List<dbOrder>>
}