package com.zeido.mohannad.timer.tea.teatimer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
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
    private boolean mIsPaused, mIsRunning;
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

        mTeaViewModel = ViewModelProviders.of(this).get(TeaViewModel.class);

        if(savedInstanceState != null){
            restorePreviousState(savedInstanceState);
        }
    }

    private void restorePreviousState(@NonNull Bundle savedInstanceState){
        getDataFromSavedInstanceState(savedInstanceState);
        if(mIsRunning){
            setTimerRunningStates();
            startTiming(mTimeLeft);
        }else if (mIsPaused){
            setTimerPausedStates();
            mTimerText.setText(formatTimerText(mTimeLeft));
            mCountDownTimer = createTimer(mTimeLeft);
        }
    }

    private void getDataFromSavedInstanceState( @NonNull Bundle savedInstanceState){
            mIsRunning = savedInstanceState.getBoolean("TIMER_RUNNING");
            mIsPaused = savedInstanceState.getBoolean("TIMER_PAUSED");
            mTimeLeft = savedInstanceState.getLong("TIME_LEFT");
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


    private String formatTimerText(long timeInMilliseconds){

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

    //Timer State setting

    private void startTiming(long time){
        setTimerRunningStates();
        mCountDownTimer = createTimer(time);
        mCountDownTimer.start();
    }

    private void setTimerRunningStates(){
        mStartTimerButton.setEnabled(false);
        mStopTimerButton.setEnabled(true);
        mPauseTimeButton.setEnabled(true);
        mIsRunning = true;
        mIsPaused = false;
    }

    private void setTimerPausedStates(){
        mStartTimerButton.setEnabled(true);
        mStopTimerButton.setEnabled(true);
        mPauseTimeButton.setEnabled(false);
        mIsRunning = false;
        mIsPaused = true;
    }

    private void setTimerStoppedStates(){
        mStartTimerButton.setEnabled(true);
        mStopTimerButton.setEnabled(false);
        mPauseTimeButton.setEnabled(false);
        mIsRunning = false;
        mIsPaused = false;
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

                NotificationChannel notificationChannel = new NotificationChannel("Tea_Done", "Tea_Timer_Done_Notification", NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.setDescription("description bla bla");
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(notificationChannel);

                Intent intent = new Intent(mContext, TimerPageActivity.class);
                intent.putExtra("teaObject", mTea);

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "Tea_Done")
                        .setSmallIcon(R.drawable.tea)
                        .setContentTitle("Test Title")
                        .setContentText("Test Context")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);
                notificationManager.notify(0, builder.build());

                Toast.makeText(mContext, "Timer done enjoy the tea!",
                        Toast.LENGTH_SHORT).show(); //Todo change this to a notification fragment and delete mContext
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_timer_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete_tea){
                mTeaViewModel.delete(mTea);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("TIME_LEFT", mTimeLeft);
        outState.putBoolean("TIMER_RUNNING", mIsRunning);
        outState.putBoolean("TIMER_PAUSED", mIsPaused);
    }
}
