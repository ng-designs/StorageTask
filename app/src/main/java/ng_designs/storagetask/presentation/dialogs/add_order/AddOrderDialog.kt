package ng_designs.storagetask.presentation.dialogs.add_order

import androidx.appcompat.app.AlertDialog
import ng_designs.storagetask.databinding.AddOrderDialogBinding
import ng_designs.storagetask.domain.entities.Order
import ng_designs.storagetask.presentation.MainActivity

class AddOrderDialog(activity: MainActivity, private val callbacks:AlertDialogCallbacks):AlertDialog.Builder(activity) {
    private val dialogBinding = AddOrderDialogBinding.inflate(activity.layoutInflater)

    init {
            setView(dialogBinding.root)

            setPositiveButton("Done") { _, _ ->
                run {
                    with(dialogBinding) {
                        val order = Order(0, pairNameEdittext.text.toString(),
                            openPriceEdittext.text.toString().toFloat(),
                            closePriceEdittext.text.toString().toFloat())

                        callbacks.OnOrderDataEntered(order)
                    }
                }
            }
            setNegativeButton("Cancel") { _, _ -> }
            create()
            show()
        }
}