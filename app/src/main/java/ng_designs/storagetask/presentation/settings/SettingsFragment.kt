package ng_designs.storagetask.presentation.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ng_designs.storagetask.R
import ng_designs.storagetask.presentation.MainActivity
import ng_designs.storagetask.presentation.dialogs.DropTableAlertDialogDialog
import ng_designs.storagetask.presentation.helpers.SettingsCallbacks
import ng_designs.storagetask.presentation.main.MainViewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModels()
    private val mViewModel: MainViewModel by viewModels()

    private val callbackWatcher = object : SettingsCallbacks {

        override fun onDropTableAllow() {
            viewModel.dropTable()
        }

        override fun onPrefValueChanged() {
            Log.i("USER", findPreference<Preference>("sort_by").toString())
//            viewModel.getSortedOrders(findPreference<Preference>("sort_by").toString())
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.activity_toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back_24,null)
//        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_mainFragment)
            toolbar.navigationIcon = null
        }

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val clearDb = findPreference<Preference>("clear_table")
        clearDb!!.setOnPreferenceClickListener {
            DropTableAlertDialogDialog(activity as MainActivity, callbackWatcher)
            true
        }

        findPreference<Preference>("sort_by")!!.setOnPreferenceChangeListener { _, newValue ->
//            mViewModel.getSortedOrders(newValue.toString())
//            Log.i("USER", "Sorting is changed to : $newValue")
            true
        }
    }
}