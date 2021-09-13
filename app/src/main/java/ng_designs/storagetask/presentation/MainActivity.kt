package ng_designs.storagetask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ng_designs.storagetask.R
import ng_designs.storagetask.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}