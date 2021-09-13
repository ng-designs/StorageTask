package ng_designs.storagetask.data.database.utils


import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.domain.entities.dbOrder

fun dbOrder.toOrder() = Order(id, coinName, openPrice, closePrice,orderStatus)
fun Order.toDbOrder() = dbOrder(id, coinName, openPrice, closePrice,orderStatus)