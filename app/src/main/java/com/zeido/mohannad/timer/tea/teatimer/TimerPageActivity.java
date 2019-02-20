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
    TextView mCountdownTimer;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_page);
        mContext = this;
        mCountdownTimer = findViewById(R.id.time);
        Intent intent = getIntent();
        final TextView teaNameTextView = findViewById(R.id.name);
        Tea tea = (Tea) intent.getSerializableExtra("teaObject");
        teaNameTextView.setText(tea.getTeaName());
        TextView teaBrewingTemperature = findViewById(R.id.temperature);
        teaBrewingTemperature.setText(getString(R.string.brew_temperature, tea.getBrewingTemperature()));


        final long brewTimeMillis = tea.getBrewingTime() * 60000; //todo time will probs be saved as long
        mCountdownTimer.setText(formatTimerText(brewTimeMillis));
        final Button startTimerButton = findViewById(R.id.timerButton);
        final Button stopTimerButton = findViewById(R.id.stopTimer);
        final CountDownTimer timer = new CountDownTimer(brewTimeMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                mCountdownTimer.setText(formatTimerText(millisUntilFinished));
            }

            public void onFinish() {
                startTimerButton.setEnabled(true);
                stopTimerButton.setEnabled(true);
                mCountdownTimer.setText(getString(R.string.default_timer));
                Toast.makeText(mContext, "Timer done enjoy the tea!",
                        Toast.LENGTH_SHORT).show();
            }
        };
        stopTimerButton.setEnabled(false);
        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerButton.setEnabled(false);
                stopTimerButton.setEnabled(true);
                timer.start();
            }
        });
        stopTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimerButton.setEnabled(true);
                stopTimerButton.setEnabled(false);
                timer.cancel();
            }
        });



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
