package com.example.clock2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.clock2.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    private lateinit var navHostFragment : NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setupWithNavController(navController)
        setupNavigationMenu(navController)

    }
    private fun checkId(): Int{
        return navHostFragment.childFragmentManager.fragments[0].id
    }

    private fun setupNavigationMenu(navController: NavController){
        val slideNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        slideNavView.setupWithNavController(navController)
        setEventDrawer(slideNavView)
    }

    private fun setEventDrawer(slideNavView : BottomNavigationView){
        slideNavView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.ic_clock -> {
                    if(checkId() != R.id.clockFragment) navController.navigate(
                        R.id.move_clock
                    )
                    true
                }
                R.id.ic_count -> {
                    if(checkId() != R.id.countFragment) navController.navigate(
                        R.id.move_count
                    )
                    true
                }
                R.id.ic_timer -> {
                    if(checkId() != R.id.timerFragment) navController.navigate(
                        R.id.move_timer
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}