package ng_designs.storagetask.presentation.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import ng_designs.storagetask.R


class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var viewModel: SettingsViewModel

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.activity_toolbar)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back_24,null)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_mainFragment)
            toolbar.navigationIcon = null
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}