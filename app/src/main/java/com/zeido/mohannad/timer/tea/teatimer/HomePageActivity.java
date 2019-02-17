package com.zeido.mohannad.timer.tea.teatimer;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zeido.mohannad.timer.tea.teatimer.Database.SampleDataProvider;
import com.zeido.mohannad.timer.tea.teatimer.Database.Tea;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    List<Tea> teaList = SampleDataProvider.teaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final FloatingActionButton timerButton = findViewById(R.id.addTeaButton);
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddTeaActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        TeaItemAdapter adapter = new TeaItemAdapter(this, teaList);

        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setAdapter(adapter);
    }

}
