package ng_designs.storagetask.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "orders")
data class dbOrder(
    @PrimaryKey (autoGenerate = true)
    val id:Int = 0,
    val coinName: String,
    val openPrice:Float,
    val closePrice:Float
)