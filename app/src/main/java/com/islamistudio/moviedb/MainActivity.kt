package com.islamistudio.moviedb

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.islamistudio.moviedb.databinding.ActivityMainBinding
import com.islamistudio.moviedb.favorite.FavoriteFragmentDirections
import com.islamistudio.moviedb.home.HomeFragment
import com.islamistudio.moviedb.home.HomeFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.hide()
        setupBottomNavigationBar()

    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || super.onSupportNavigateUp()
    }

    private fun setupBottomNavigationBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navHomeFragment,
            R.id.navFavoriteFragment,
            R.id.navSettingFragment
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.apply {
            navBottom.setupWithNavController(navController)
        }
    }

    fun hideNavBar(state: Boolean) {
        if (state) binding.navBottom.visibility = View.GONE else binding.navBottom.visibility = View.VISIBLE
    }

}