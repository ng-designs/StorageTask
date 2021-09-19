package ng_designs.storagetask.presentation.screens.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ng_designs.storagetask.R
import ng_designs.storagetask.presentation.screens.MainActivity
import ng_designs.storagetask.presentation.dialogs.DropTableAlertDialogDialog
import ng_designs.storagetask.presentation.helpers.SettingsCallbacks

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModels()

    private val callbackWatcher = object : SettingsCallbacks {

        override fun onDropTableAllow() {
            viewModel.clearTable()
        }

        override fun onPrefValueChanged() {
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.activity_toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back_24,null)

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_mainFragment)
            toolbar.navigationIcon = null
        }

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val accessType  = findPreference<Preference>("access_type")

        accessType?.setOnPreferenceChangeListener { preference, newValue ->
            (activity as MainActivity)
                .findViewById<Toolbar>(R.id.activity_toolbar)
                .subtitle = "DB Access type: $newValue"
            true
        }

        val clearDb = findPreference<Preference>("clear_table")
        clearDb!!.setOnPreferenceClickListener {
            DropTableAlertDialogDialog(activity as MainActivity, callbackWatcher)
            true
        }
    }
}