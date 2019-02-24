package com.zeido.mohannad.timer.tea.teatimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddTeaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tea);

        Button submitButton = findViewById(R.id.submitTea);
        Button cancelButton = findViewById(R.id.cancelTea);


        submitButton.setOnClickListener(submitButtonListener);
        cancelButton.setOnClickListener(cancelButtonListener);
    }

    private View.OnClickListener submitButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //todo add to array and return to homePage
        }
    };

    private View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //todo return to homePage
        }
    };
}
