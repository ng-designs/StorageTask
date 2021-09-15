package ng_designs.storagetask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ng_designs.storagetask.R
import ng_designs.storagetask.databinding.MainActivityBinding
import ng_designs.storagetask.databinding.MainFragmentBinding
import ng_designs.storagetask.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {

    private var binding: MainActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setSupportActionBar(binding?.activityToolbar)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}