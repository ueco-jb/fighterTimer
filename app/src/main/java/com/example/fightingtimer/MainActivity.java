package com.example.fightingtimer;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.fightingtimer.ui.presets.PresetsFragment;
import com.example.fightingtimer.ui.timer.TimerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements PresetsFragment.UpdateCountdowns {

    @Override
    public void updateCountdowns(final int round_time, final int break_time)
    {
        Log.d("DuPA DUP DUP", round_time + " / " + break_time);
        TimerFragment timer = (TimerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_timer);
        if (timer != null) {
            Log.d("DuPA DUP DUP", ":)");
            timer.updateCountdowns(round_time, break_time);
        } else
        {
            Log.d("DuPA DUP DUP", ":(");
            TimerFragment new_timer = new TimerFragment();
            Bundle args = new Bundle();
            args.putInt("round_time_bundle", round_time);
            args.putInt("break_time_bundle", break_time);
            new_timer.setArguments(args);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.navigation_presets, new_timer, "new_timer")
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment)
    {
        if (fragment instanceof PresetsFragment)
        {
            PresetsFragment presetsFragment = (PresetsFragment) fragment;
            presetsFragment.setUpdateCountdowns(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_timer, R.id.navigation_presets)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
