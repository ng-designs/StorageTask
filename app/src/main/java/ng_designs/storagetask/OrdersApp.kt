package ng_designs.storagetask

import android.app.Application
import android.content.Context
import ng_designs.storagetask.data.database.db.OrdersDatabase
import ng_designs.storagetask.data.repository.OrdersRepository
import ng_designs.storagetask.domain.utils.ServiceLocator
import ng_designs.storagetask.domain.utils.locate

class OrdersApp:Application() {
    private val locator = ServiceLocator

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this)
        ServiceLocator.register(OrdersDatabase.getDatabase(locate()))
        ServiceLocator.register(OrdersRepository(locate()))
    }
}