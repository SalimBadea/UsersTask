package com.aait.base.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.getInsetsController
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aait.base.R
import com.aait.base.base.BaseActivity
import com.aait.base.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener{

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private var hasPaddingTop: Boolean = true
    private var hasPaddingBottom: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            binding.navHostFragment.setPadding(
                0,
                0,
                0,
                if (imeInsets.bottom == 0 && hasPaddingBottom) systemBars.bottom else 0
            )
            v.setPadding(
                systemBars.left,
                if (hasPaddingTop) systemBars.top else 0,
                systemBars.right,
                imeInsets.bottom
            )
            insets
        }
        setupNavController()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when (navController.currentDestination?.id) {
                R.id.mainFirstFragment -> navController.popBackStack(R.id.registrationFragment, false)

                R.id.registrationFragment -> finish()

                else -> navController.popBackStack()
            }
        }
    }

    private fun setupNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        handleStatusOnDestination(destination)
        handleTopPaddingOnDestination(destination)
        handleBottomPaddingOnDestination(destination)
    }

    private fun handleStatusOnDestination(destination: NavDestination) {
        when (destination.id) {
            R.id.mainFirstFragment -> {
                getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
                //
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.colorPrimary)
                )
            }

            else -> {
                getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
                //
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.colorWhite)
                )
            }
        }
    }

    private fun handleTopPaddingOnDestination(destination: NavDestination) {
        hasPaddingTop = destination.id !in listOf(
            R.id.registrationFragment
        )
    }

    private fun handleBottomPaddingOnDestination(destination: NavDestination) {
        hasPaddingBottom = destination.id !in listOf(
            R.id.mainFirstFragment,
            R.id.registrationFragment
        )
    }

    enum class ToastType {
        SUCCESS, ERROR, WARNING, INFO
    }
}