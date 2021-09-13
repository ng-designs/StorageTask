package ng_designs.storagetask.presentation.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ng_designs.storagetask.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}