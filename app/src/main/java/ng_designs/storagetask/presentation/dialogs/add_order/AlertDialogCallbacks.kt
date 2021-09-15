package ng_designs.storagetask.presentation.dialogs.add_order

import ng_designs.storagetask.domain.entities.Order

interface AlertDialogCallbacks {
    fun onOrderDataEntered(order: Order)
}