package ng_designs.storagetask.presentation.helpers

import ng_designs.storagetask.domain.entities.Order

interface DialogCallbacks {
    fun onOrderDataEntered(order: Order)
}