package org.xhanka.biblesiswati

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import dagger.hilt.android.AndroidEntryPoint
import org.xhanka.biblesiswati.common.Utils.Companion.toggleDarkMode
import org.xhanka.biblesiswati.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    var idToLoad = 0
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // always toggle dark mode after activity has initialized : called super.onCreate()
        toggleDarkMode(this)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawer = binding.drawerLayout
        val navigationView = binding.navView

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_list_songs, R.id.nav_siswati_ref,
                R.id.nav_notes, R.id.nav_fav_verses, R.id.nav_install_extra_versions,
                R.id.nav_about, R.id.nav_settings
            ), drawer
        )

        navController = findNavController(this, R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(this, navController, appBarConfiguration)
        setupWithNavController(navigationView, navController)

        binding.navView.setNavigationItemSelectedListener { item: MenuItem ->
            idToLoad = item.itemId
            binding.drawerLayout.closeDrawer(GravityCompat.START, true)
            true
        }

        binding.drawerLayout.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {
                if (idToLoad != 0 && navController.currentDestination?.id != idToLoad) {
                    val options: NavOptions = NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(navController.graph.startDestinationId, false)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setEnterAnim(R.anim.nav_default_enter_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .build()
                    navController.navigate(idToLoad, null, options)
                }
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })
        navController.addOnDestinationChangedListener { _: NavController?, navDestination: NavDestination, _: Bundle? ->
            idToLoad = navDestination.id
            binding.navView.setCheckedItem(navDestination.id)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        ///NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}