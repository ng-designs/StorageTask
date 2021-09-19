package ng_designs.storagetask.presentation.dialogs

import androidx.appcompat.app.AlertDialog
import ng_designs.storagetask.presentation.screens.MainActivity
import ng_designs.storagetask.presentation.helpers.SettingsCallbacks

class DropTableAlertDialogDialog(activity: MainActivity, private val callbacks: SettingsCallbacks):
    AlertDialog.Builder(activity) {


    init {
        setTitle("Clear orders table?")

        setPositiveButton("Done") { _, _ ->
            callbacks.onDropTableAllow()
        }
        setNegativeButton("Cancel") { _, _ -> }
        create()
        show()
    }
}