package ng_designs.storagetask.data.database.utils


import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.data.database.entities.dbOrder

fun dbOrder.toOrder() = Order(id, coinName, openPrice, closePrice)
fun Order.toDbOrder() = dbOrder(id, coinName, openPrice, closePrice)