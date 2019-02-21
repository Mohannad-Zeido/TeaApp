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
    Tea mTea;
    boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_page);
        Intent intent = getIntent();
        mTea = (Tea) intent.getSerializableExtra("teaObject");

        TextView teaBrewingTemperature = findViewById(R.id.temperature);
        TextView teaNameTextView = findViewById(R.id.name);
        mTimerText = findViewById(R.id.time);
        mStartTimerButton = findViewById(R.id.startButton);
        mStopTimerButton = findViewById(R.id.stopButton);
        mPauseTimeButton = findViewById(R.id.pauseButton);

        mContext = this;
        mTimeLeft = 0;
        
        teaNameTextView.setText(mTea.getTeaName());
        teaBrewingTemperature.setText(getString(R.string.brew_temperature, mTea.getBrewingTemperature()));
        mTimerText.setText(formatTimerText(mTea.getBrewingTime()));
        mStartTimerButton.setOnClickListener(startOnClickListener);
        mStopTimerButton.setOnClickListener(stopOnClickListener);
        mPauseTimeButton.setOnClickListener(pauseOnClickListener);
    }

    private View.OnClickListener pauseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPauseTimeButton.setEnabled(false);
            mStartTimerButton.setEnabled(true);
            mStopTimerButton.setEnabled(true);
            isPaused = true;
            mCountDownTimer.cancel();
            mTimerText.setText(formatTimerText(mTimeLeft));
        }
    };

    private View.OnClickListener stopOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mStartTimerButton.setEnabled(true);
            mStopTimerButton.setEnabled(false);
            mPauseTimeButton.setEnabled(false);
            mCountDownTimer.cancel();
            mTimerText.setText(formatTimerText(mTea.getBrewingTime()));
        }
    };

    private View.OnClickListener startOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!isPaused){
                mPauseTimeButton.setEnabled(true);
                mStartTimerButton.setEnabled(false);
                mStopTimerButton.setEnabled(true);
                mCountDownTimer = createTimer(mTea.getBrewingTime());
                mCountDownTimer.start();
            }else{
                mPauseTimeButton.setEnabled(true);
                mStartTimerButton.setEnabled(false);
                mStopTimerButton.setEnabled(true);
                mCountDownTimer = createTimer(mTimeLeft);
                mCountDownTimer.start();
            }

        }
    };

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
