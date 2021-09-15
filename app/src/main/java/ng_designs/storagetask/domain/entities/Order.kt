package ng_designs.storagetask.domain.entities


data class Order(
    var id:Int = 0,
    var coinName: String = "",
    var openPrice:Float = 0f,
    var closePrice:Float = 0f,
    var orderStatus:String = ""
)
