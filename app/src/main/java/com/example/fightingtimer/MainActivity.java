package com.example.fightingtimer;

import android.os.Bundle;
import android.util.Log;

import com.example.fightingtimer.ui.presets.PresetsFragment;
import com.example.fightingtimer.ui.timer.TimerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity implements PresetsFragment.UpdateCountdowns {

    @Override
    public void updateCountdowns(final int round_time, final int break_time)
    {
        Log.d("DuPA DUP DUP", "DSADADSADAD");
        TimerFragment timer = (TimerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_timer);
        timer.updateCountdowns(round_time, break_time);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_timer, R.id.navigation_presets)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
