package org.xhanka.biblesiswati;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.xhanka.biblesiswati.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    int idToLoad;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // check if first launch
        /*boolean first_launch = getSharedPreferences(Constants.SHARED_PREF ,MODE_PRIVATE).getBoolean(
                "first_launch", true
        );*/

  /*      // if first launch, change variable and run main code
        if (first_launch) {
            getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE).edit().putBoolean(
                    "first_launch", false
            ).apply();

            new Handler(Looper.getMainLooper()).post(() -> Objects.requireNonNull(new ViewModelProvider(this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())
            ).get(BibleDataBaseViewModel.class)).getAllBooks());
        }*/

        super.onCreate(savedInstanceState);

        org.xhanka.biblesiswati.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_list_songs, R.id.nav_siswati_ref,
                R.id.nav_notes, R.id.nav_fav_verses, R.id.nav_about, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        idToLoad = 0;
        binding.navView.setNavigationItemSelectedListener(item -> {
            idToLoad = item.getItemId();
            binding.drawerLayout.closeDrawer(GravityCompat.START, true);
            return true;
        });

        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull @NotNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull @NotNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull @NotNull View drawerView) {
                if (idToLoad != 0 && Objects.requireNonNull(navController.getCurrentDestination()).getId() != idToLoad) {
                    NavOptions options = new NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(navController.getGraph().getStartDestinationId(), false)
                            .setExitAnim(R.anim.nav_default_exit_anim)
                            .setEnterAnim(R.anim.nav_default_enter_anim)
                            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                            .build();

                    navController.navigate(idToLoad, null, options);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        navController.addOnDestinationChangedListener((controller, navDestination, bundle) -> {
            idToLoad = navDestination.getId();
            binding.navView.setCheckedItem(navDestination.getId());
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    void onFirstLaunch() {

    }
}