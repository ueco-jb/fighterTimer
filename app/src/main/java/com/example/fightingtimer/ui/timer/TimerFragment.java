package com.example.fightingtimer.ui.timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fightingtimer.R;

import java.util.concurrent.TimeUnit;

public class TimerFragment extends Fragment {
    private CountDownTimer roundTimer;
    private CountDownTimer breakTimer;
    private CountDownTimer currentTimer;
    final private long defaultRoundCountdown = 10000;
    final private long defaultBreakCountdown = 5000;
    final private long defaultCountdownTick = 1000;

    Button start_button;
    Button stop_button;
    Button pause_button;
    Button resume_button;

    TextView round_val_label;
    TextView break_val_label;
    TextView countdown_label;

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

    private void oneTickDelay(){
        try {
            TimeUnit.MILLISECONDS.sleep(defaultCountdownTick);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void updateCountdownLabel(String updated){
        countdown_label.setText(updated);
    }

    private void updateCurrentLabel(int updated){
        countdown_label.setText(updated);
    }

    public void startTimer(View view){
        setButtonVisibility(View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.INVISIBLE);
        currentTimer.start();
        updateCurrentLabel(R.string.round_label);
    }

    public void stopTimer(View view){
        setButtonVisibility(View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
        currentTimer.cancel();
        updateCountdownLabel(millisecondsToSecondsStr(defaultRoundCountdown));
        countdown_label.setText("");
    }

    public void pauseTimer(View view){
        setButtonVisibility(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.VISIBLE);
        currentTimer.pause();
    }

    public void resumeTimer(View view){
        setButtonVisibility(View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.INVISIBLE);
        currentTimer.resume();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timer, container, false);

        start_button = root.findViewById(R.id.Start_b);
        stop_button = root.findViewById(R.id.Stop_b);
        pause_button = root.findViewById(R.id.Pause_b);
        resume_button = root.findViewById(R.id.Resume_b);

        countdown_label = root.findViewById(R.id.Countdown_l);
        round_val_label = root.findViewById(R.id.Round_val);
        round_val_label.setText(millisecondsToSecondsStr(defaultRoundCountdown));
        break_val_label = root.findViewById(R.id.Break_val);
        break_val_label.setText(millisecondsToSecondsStr(defaultBreakCountdown));

        roundTimer = new CountDownTimer(defaultRoundCountdown, defaultCountdownTick) {
            public void onTick ( long millisUntilFinished){
                updateCountdownLabel(millisecondsToSecondsStr(millisUntilFinished));
            }
            public void onFinish () {
                updateCountdownLabel(millisecondsToSecondsStr(defaultBreakCountdown));
                currentTimer = breakTimer;
                oneTickDelay();
                currentTimer.start();
                updateCurrentLabel(R.string.break_label);
            }
        };
        breakTimer = new CountDownTimer(defaultBreakCountdown, defaultCountdownTick) {
            public void onTick ( long millisUntilFinished){
                updateCountdownLabel(millisecondsToSecondsStr(millisUntilFinished));
            }
            public void onFinish () {
                updateCountdownLabel(millisecondsToSecondsStr(defaultRoundCountdown));
                currentTimer = roundTimer;
                oneTickDelay();
                currentTimer.start();
                updateCurrentLabel(R.string.round_label);
            }
        };

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