package ng_designs.storagetask.data.database.cursor

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ng_designs.storagetask.data.database.entities.dbOrder
import ng_designs.storagetask.data.database.room.OrderDao


private const val DB_NAME = "orders_database"
private const val TABLE_NAME = "orders"

class CursorDao(context: Context?) : OrderDao,
    SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """CREATE TABLE IF NOT EXISTS $TABLE_NAME(
                    'id' INTEGER PRIMARY KEY,
                    'coinName' TEXT,
                    'openPrice' REAL,
                    'closePrice' TEXT )"""
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun getAllOrders(sortBy: String?): Cursor {

         return readableDatabase.rawQuery(
             "SELECT * FROM $TABLE_NAME ORDER BY $sortBy",
             null
         )
    }

    @SuppressLint("Recycle")
    private fun getLastId():Int{
        return readableDatabase.rawQuery(
            "SELECT * FROM $TABLE_NAME",
            null
        ).count - 1
    }

    override suspend fun insert(order: dbOrder) {
        val lastId = getLastId()
        order.apply {
            writableDatabase.execSQL(
                """INSERT INTO $TABLE_NAME 
                (id, coinName, openPrice, closePrice)
                 VALUES (%d, %s, %f, %f)"""
                    .format(lastId + 1, coinName, openPrice, closePrice)
            )
        }
    }

    override suspend fun update(order: dbOrder) {
        writableDatabase.execSQL(
            """UPDATE $TABLE_NAME SET 'coinName'= %s,
                'openPrice'= %f, 'closePrice'= %f WHERE 'id'= %d"""
                .format( order.coinName, order.openPrice, order.closePrice, order.id ))
    }

    override suspend fun delete(order: dbOrder) {
        val _id = order.id
        writableDatabase.execSQL("DELETE FROM $TABLE_NAME WHERE id = '$_id'")
    }

    override suspend fun drop() {
        writableDatabase.execSQL("DELETE FROM $TABLE_NAME" )
    }

    override suspend fun resetAutoIncrement() {
        writableDatabase.execSQL("DELETE FROM sqlite_sequence WHERE name = '$TABLE_NAME' ")
    }

    override fun getSorted(sortBy: String): Flow<List<dbOrder>> {
        val orders = mutableListOf<dbOrder>()
        val cursor = getAllOrders(sortBy)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val _id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("coinName"))
                    val openPrice = cursor.getFloat(cursor.getColumnIndexOrThrow("openPrice"))
                    val closePrice = cursor.getFloat(cursor.getColumnIndexOrThrow("closePrice"))
                    orders.add( dbOrder( _id, name, openPrice, closePrice ))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.i("CursorDao", e.toString())
        } finally {
            cursor.close()
        }
        return flowOf(orders)
    }
}