package com.zeido.mohannad.timer.tea.teatimer;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zeido.mohannad.timer.tea.teatimer.Database.Tea;

import java.util.Locale;

public class TimerPageActivity extends AppCompatActivity {
    TextView mTimerText;
    Context mContext;
    long mTimeLeft;
    Button mStartTimerButton, mStopTimerButton, mPauseTimeButton;
    CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_page);
        mContext = this;
        mTimerText = findViewById(R.id.time);
        Intent intent = getIntent();
        final TextView teaNameTextView = findViewById(R.id.name);
        Tea tea = (Tea) intent.getSerializableExtra("teaObject");
        teaNameTextView.setText(tea.getTeaName());
        TextView teaBrewingTemperature = findViewById(R.id.temperature);
        teaBrewingTemperature.setText(getString(R.string.brew_temperature, tea.getBrewingTemperature()));
        mTimeLeft = 0;

        final long brewTimeMillis = tea.getBrewingTime() * 60000; //todo time will probs be saved as long
        mTimerText.setText(formatTimerText(brewTimeMillis));

        mStartTimerButton = findViewById(R.id.startButton);
        mStopTimerButton = findViewById(R.id.stopButton);
        mPauseTimeButton = findViewById(R.id.pauseButton);

        mCountDownTimer = createTimer(brewTimeMillis);
        mStopTimerButton.setEnabled(false);
        mStartTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartTimerButton.setEnabled(false);
                mStopTimerButton.setEnabled(true);
                mCountDownTimer.start();
            }
        });
        mStopTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartTimerButton.setEnabled(true);
                mStopTimerButton.setEnabled(false);
                mCountDownTimer.cancel();
                mTimerText.setText(formatTimerText(brewTimeMillis));
            }
        });
        mPauseTimeButton.setEnabled(false);
        mPauseTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo change the text to resume and resume timer
            }
        });
    }

    private CountDownTimer createTimer(final long time){
        return new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                mTimerText.setText(formatTimerText(millisUntilFinished));
                mTimeLeft = millisUntilFinished;
            }

            public void onFinish() {
                mStartTimerButton.setEnabled(true);
                mStopTimerButton.setEnabled(true);
                mTimerText.setText(formatTimerText(time));
                Toast.makeText(mContext, "Timer done enjoy the tea!",
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    private String formatTimerText(long timeInMilliseconds){
//        long timeInMilliseconds = time * 60000;

        long seconds = timeInMilliseconds / 1000;
        long minutes = seconds / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        String secondsD = String.format(Locale.getDefault(), "%02d", seconds);
        String minutesD = String.format(Locale.getDefault(), "%02d", minutes);
        //todo add code to format minutes and hours.
       return /*hoursD + ":" +*/ minutesD + ":" + secondsD;
    }
}
