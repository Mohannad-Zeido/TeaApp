package com.zeido.mohannad.timer.tea.teatimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zeido.mohannad.timer.tea.teatimer.Database.SampleDataProvider;
import com.zeido.mohannad.timer.tea.teatimer.Database.Tea;

import java.util.List;

public class AddTeaActivity extends AppCompatActivity {

    private EditText mTeaName, mBrewingTime, mTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tea);

        Button submitButton = findViewById(R.id.submitTea);
        Button cancelButton = findViewById(R.id.cancelTea);
        mTeaName = findViewById(R.id.teaNameEditText);
        mBrewingTime = findViewById(R.id.teaBrewingTimeEditText);
        //todo make sure time gets converted to millis
        mTemperature = findViewById(R.id.teaTemperatureEditText);


        submitButton.setOnClickListener(submitButtonListener);
        cancelButton.setOnClickListener(cancelButtonListener);
    }

    private View.OnClickListener submitButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            List<Tea> teaList = SampleDataProvider.teaList;
            String teaName = mTeaName.getText().toString();
            long brewingTime = Long.parseLong(mBrewingTime.getText().toString());
            int temperature = Integer.parseInt(mTemperature.getText().toString());
            teaList.add(new Tea(teaName,"", brewingTime, temperature,null));
            //Todo validate the text sent by user
            setResult(RESULT_OK);
            finish();
        }
    };

    private View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CANCELED);
            finish();
        }
    };
}
