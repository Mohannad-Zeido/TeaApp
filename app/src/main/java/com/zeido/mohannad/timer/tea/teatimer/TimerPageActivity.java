package com.zeido.mohannad.timer.tea.teatimer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zeido.mohannad.timer.tea.teatimer.Database.Tea;
import com.zeido.mohannad.timer.tea.teatimer.Database.TeaViewModel;

import java.util.Locale;

public class TimerPageActivity extends AppCompatActivity {


    private Context mContext;
    private TeaViewModel mTeaViewModel;
    private Button mStartTimerButton, mStopTimerButton, mPauseTimeButton;
    private TextView mTimerText;
    private CountDownTimer mCountDownTimer;
    private boolean mIsPaused, mIsTimerOn;
    private long mTimeLeft;
    private Tea mTea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_page);
        mContext = this;

        Intent intent = getIntent();
        mTea = (Tea) intent.getSerializableExtra("teaObject");

        TextView teaNameTextView = findViewById(R.id.name);
        teaNameTextView.setText(mTea.getTeaName());

        TextView teaBrewingTemperature = findViewById(R.id.temperature);
        teaBrewingTemperature.setText(getString(R.string.brew_temperature, mTea.getBrewingTemperature()));

        mTimerText = findViewById(R.id.time);
        mTimerText.setText(formatTimerText(mTea.getBrewingTime()));

        mStartTimerButton = findViewById(R.id.startButton);
        mStartTimerButton.setOnClickListener(startOnClickListener);

        mStopTimerButton = findViewById(R.id.stopButton);
        mStopTimerButton.setOnClickListener(stopOnClickListener);

        mPauseTimeButton = findViewById(R.id.pauseButton);
        mPauseTimeButton.setOnClickListener(pauseOnClickListener);

        //todo add description ui item here here?

        mTeaViewModel = ViewModelProviders.of(this).get(TeaViewModel.class);

        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean("TIMER_RUNNING")){
                mStartTimerButton.setEnabled(false);
                mStopTimerButton.setEnabled(true);
                mPauseTimeButton.setEnabled(true);
                mIsTimerOn = true;
                mIsPaused = false;
                mCountDownTimer = createTimer(savedInstanceState.getLong("TIME_LEFT"));
                mCountDownTimer.start();
            }else if (savedInstanceState.getBoolean("TIMER_PAUSED")){
                setTimerPausedStates();
                mTimeLeft = savedInstanceState.getLong("TIME_LEFT");
                mTimerText.setText(formatTimerText(mTimeLeft));
                mCountDownTimer = createTimer(savedInstanceState.getLong("TIME_LEFT"));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_timer_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private View.OnClickListener pauseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setTimerPausedStates();
            mCountDownTimer.cancel();
            mTimerText.setText(formatTimerText(mTimeLeft));
        }
    };

    private View.OnClickListener stopOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setTimerStoppedStates();
            mCountDownTimer.cancel();
            mTimerText.setText(formatTimerText(mTea.getBrewingTime()));
        }
    };

    private View.OnClickListener startOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mIsPaused){
                startTiming(mTimeLeft);
            }else{
                startTiming(mTea.getBrewingTime());
            }
        }
    };

    private void startTiming(long time){
        setTimerRunningStates();
        mCountDownTimer = createTimer(time);
        mCountDownTimer.start();
    }

    private CountDownTimer createTimer(final long time){
        return new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                mTimerText.setText(formatTimerText(millisUntilFinished));
                mTimeLeft = millisUntilFinished;
            }

            public void onFinish() {
                setTimerStoppedStates();
                mTimerText.setText(formatTimerText(time));
                Toast.makeText(mContext, "Timer done enjoy the tea!",
                        Toast.LENGTH_SHORT).show(); //Todo change this to a notification fragment and delete mContext
            }
        };
    }

    private String formatTimerText(long timeInMilliseconds){
//        long timeInMilliseconds = time * 60000;

        long wholeTimeSeconds = timeInMilliseconds / 1000;
        long wholeTimeMinutes = wholeTimeSeconds / 60;
//        long hours = wholeTimeMinutes / 60;


        long seconds = wholeTimeSeconds % 60;
        long minutes = wholeTimeMinutes % 60;

        String secondsD = String.format(Locale.getDefault(), "%02d", seconds);
        String minutesD = String.format(Locale.getDefault(), "%02d", minutes);
//        String hoursD = String.format(Locale.getDefault(), "%02d", hours);
        
       return /*hoursD + ":" + */minutesD + ":" + secondsD;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_tea:
                mTeaViewModel.delete(mTea);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("TIME_LEFT", mTimeLeft);
        outState.putBoolean("TIMER_RUNNING", mIsTimerOn);
        outState.putBoolean("TIMER_PAUSED", mIsPaused);
    }

    //Timer State setting

    private void setTimerRunningStates(){
        mStartTimerButton.setEnabled(false);
        mStopTimerButton.setEnabled(true);
        mPauseTimeButton.setEnabled(true);
        mIsTimerOn = true;
        mIsPaused = false;
    }

    private void setTimerPausedStates(){
        mStartTimerButton.setEnabled(true);
        mStopTimerButton.setEnabled(true);
        mPauseTimeButton.setEnabled(false);
        mIsTimerOn = false;
        mIsPaused = true;
    }

    private void setTimerStoppedStates(){
        mStartTimerButton.setEnabled(true);
        mStopTimerButton.setEnabled(false);
        mPauseTimeButton.setEnabled(false);
        mIsTimerOn = false;
        mIsPaused = false;
    }
}
