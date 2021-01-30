package com.mtek.goarenopoc.ui


import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.mtek.goarenopoc.R
import com.mtek.goarenopoc.base.BaseActivity
import com.mtek.goarenopoc.databinding.ActivityMainBinding
import com.mtek.goarenopoc.ui.fragment.splash.SplashViewModel
import com.mtek.goarenopoc.utils.setupWithNavController
import com.mtek.goarenopoc.utils.visible


class MainActivity : BaseActivity<ActivityMainBinding, SplashViewModel>(SplashViewModel::class) {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    private var currentNavController: LiveData<NavController>? = null
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()


        }

    }


    private fun setupBottomNavigationBar() {


        val largeTextView: TextView =
            binding?.bottomAppBar?.findViewById(com.google.android.material.R.id.largeLabel)!!
        val smallTextView: TextView =
            binding?.bottomAppBar?.findViewById(com.google.android.material.R.id.smallLabel)!!
        largeTextView.visible()

        binding?.bottomAppBar?.setOnNavigationItemReselectedListener {
            "Reselect blocked."
        }

        val navGraphIds = listOf(R.navigation.home, R.navigation.dashboard)
        val controller = binding?.bottomAppBar?.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        currentNavController = controller

    }

    override fun onSupportNavigateUp(): Boolean = currentNavController?.value?.navigateUp() ?: false

    fun hideBottomNav() {
        binding.bottomAppBar.visibility = View.GONE
    }

    fun showBottomNav() {
        binding.bottomAppBar.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        val start = Navigation.findNavController(this, R.id.nav_host_container).currentDestination!!.id
        if (start == R.id.homeFragment) {
            if (doubleBackToExitPressedOnce) {
                finishAfterTransition()
                return
            }
            doubleBackToExitPressedOnce = true
            Toast.makeText(this@MainActivity, "Press back again to exits", Toast.LENGTH_SHORT)
                .show()
            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
        }
    }


}