package ng_designs.storagetask.data.database.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ng_designs.storagetask.data.database.entities.dbOrder

internal const val SQL_GET_SORTED =
    "SELECT * FROM orders ORDER BY " +
            "CASE WHEN :sortBy = 'id' THEN id END ASC," +
            "CASE WHEN :sortBy = 'coinName' THEN coinName END ASC," +
            "CASE WHEN :sortBy = 'openPrice' THEN openPrice END ASC," +
            "CASE WHEN :sortBy = 'closePrice' THEN closePrice END ASC"

@Dao
interface roomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: dbOrder)

    @Update
    suspend fun update(order: dbOrder)

    @Delete
    suspend fun delete(order: dbOrder)

    @Query("DELETE FROM orders")
    suspend fun drop()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'orders'")
    suspend fun resetAutoIncrement()

    @Query(SQL_GET_SORTED)
    fun getSorted(sortBy: String): Flow<List<dbOrder>>

    @Query("SELECT * FROM orders")
    fun getAll(): Flow<List<dbOrder>>
}