package ng_designs.storagetask.presentation.screens

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import ng_designs.storagetask.R
import ng_designs.storagetask.databinding.MainActivityBinding
import ng_designs.storagetask.domain.utils.locate


class MainActivity : AppCompatActivity() {

    private var binding: MainActivityBinding? = null
    private val prefs : SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(
        locate()
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setSupportActionBar(binding?.activityToolbar)
        this.findViewById<Toolbar>(R.id.activity_toolbar)
            .subtitle = "DB Access type: " + prefs.getString("access_type",null)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}