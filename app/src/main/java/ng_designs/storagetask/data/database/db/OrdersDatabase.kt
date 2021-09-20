package ng_designs.storagetask.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ng_designs.storagetask.data.database.room.OrderDao
import ng_designs.storagetask.data.database.entities.dbOrder

@Database(entities = [dbOrder::class], version = 1)
abstract class OrdersDatabase : RoomDatabase() {
    abstract val dao: OrderDao

    companion object {
        fun getDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                OrdersDatabase::class.java,
                "orders_database"
            ).fallbackToDestructiveMigration().build()
    }
}