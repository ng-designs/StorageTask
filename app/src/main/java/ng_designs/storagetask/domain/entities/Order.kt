package ng_designs.storagetask.domain.entities


data class Order(
    val id:Int = 0,
    val coinName: String,
    val openPrice:Float,
    val closePrice:Float,
    val orderStatus:String
)
