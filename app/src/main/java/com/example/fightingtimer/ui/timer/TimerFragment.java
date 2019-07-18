package com.example.fightingtimer.ui.timer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.fightingtimer.CurrentSettingDatabase;
import com.example.fightingtimer.R;
import com.example.fightingtimer.ui.presets.CurrentSetting;
import com.example.fightingtimer.ui.presets.CurrentSettingDao;

public class TimerFragment extends Fragment {
    CurrentSettingDatabase database;
    ProgressBar progressBar;

    private Hourglass roundTimer;
    private Hourglass breakTimer;
    private Hourglass currentTimer;
    final private int defaultRoundCountdown = 10000;
    final private int defaultBreakCountdown = 5000;
    final private int defaultCountdownTick = 1000;
    private int roundCountdown = 0;
    private int breakCountdown = 0;

    Button start_button;
    Button stop_button;
    Button pause_button;
    Button resume_button;

    TextView round_val_label;
    TextView break_val_label;
    TextView countdown_label;

    private void newTimer()
    {
        roundTimer = new Hourglass(roundCountdown, defaultCountdownTick) {
            public void onTimerTick ( long millisUntilFinished){
                updateCountdownLabel(millisecondsToSecondsStr(millisUntilFinished));
                progressBar.setProgress(roundCountdown-Math.toIntExact(millisUntilFinished));
            }
            public void onTimerFinish () {
                progressBar.setProgress(roundCountdown);
                MediaPlayer.create(getActivity(), R.raw.boxingbell).start();
                currentTimer = breakTimer;
                currentTimer.startTimer();
                updateCurrentLabel(R.string.break_label);
            }
            public void onTimerCancel(){

            }
        };
        breakTimer = new Hourglass(breakCountdown, defaultCountdownTick) {
            public void onTimerTick ( long millisUntilFinished){
                updateCountdownLabel(millisecondsToSecondsStr(millisUntilFinished));
                progressBar.setProgress(breakCountdown-Math.toIntExact(millisUntilFinished));
            }
            public void onTimerFinish () {
                progressBar.setProgress(breakCountdown);
                MediaPlayer.create(getActivity(), R.raw.boxingbell).start();
                currentTimer = roundTimer;
                currentTimer.startTimer();
                updateCurrentLabel(R.string.round_label);
            }
            public void onTimerCancel(){

            }
        };
    }

    private void setButtonVisibility(final int startb, final int stopb, final int pauseb, final int resumeb)
    {
        start_button.setVisibility(startb);
        stop_button.setVisibility(stopb);
        pause_button.setVisibility(pauseb);
        resume_button.setVisibility(resumeb);
    }

    private String millisecondsToSecondsStr(final long milliseconds)
    {
        return Long.toString(milliseconds/defaultCountdownTick);
    }

    private void updateCountdownLabel(String updated){
        countdown_label.setText(updated);
    }

    private void updateCurrentLabel(int updated){
        countdown_label.setText(updated);
    }

    public void startTimer(View view){
        setButtonVisibility(View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.INVISIBLE);
        currentTimer.startTimer();
        updateCurrentLabel(R.string.round_label);
    }

    public void stopTimer(View view){
        setButtonVisibility(View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
        currentTimer.cancelTimer();
        updateCountdownLabel(millisecondsToSecondsStr(roundCountdown));
        countdown_label.setText("");
    }

    public void pauseTimer(View view){
        setButtonVisibility(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.VISIBLE);
        currentTimer.pauseTimer();
    }

    public void resumeTimer(View view){
        setButtonVisibility(View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.INVISIBLE);
        currentTimer.resumeTimer();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timer, container, false);
        database = Room.databaseBuilder(getActivity(), CurrentSettingDatabase.class, "currentsettingdb")
                .allowMainThreadQueries()
                .build();

        CurrentSettingDao currentSettingDao = database.get();
        CurrentSetting currentSetting = currentSettingDao.get();
        if (currentSetting == null) {
            currentSetting = new CurrentSetting();
            currentSetting.rnd = defaultRoundCountdown;
            currentSetting.brk = defaultBreakCountdown;
            currentSettingDao.insert(currentSetting);
        }
        else {
            roundCountdown = currentSetting.rnd;
            breakCountdown = currentSetting.brk;
        }

        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        progressBar.setProgress(0);

        start_button = root.findViewById(R.id.Start_b);
        stop_button = root.findViewById(R.id.Stop_b);
        pause_button = root.findViewById(R.id.Pause_b);
        resume_button = root.findViewById(R.id.Resume_b);

        countdown_label = root.findViewById(R.id.Countdown_l);
        round_val_label = root.findViewById(R.id.Round_val);
        round_val_label.setText(millisecondsToSecondsStr(roundCountdown));
        break_val_label = root.findViewById(R.id.Break_val);
        break_val_label.setText(millisecondsToSecondsStr(breakCountdown));

        newTimer();

        currentTimer = roundTimer;

        start_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startTimer(v);
            }
        });
        stop_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                stopTimer(v);
            }
        });
        pause_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                pauseTimer(v);
            }
        });
        resume_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resumeTimer(v);
            }
        });

        return root;
    }
}