package com.zeido.mohannad.timer.tea.teatimer;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zeido.mohannad.timer.tea.teatimer.Database.SampleDataProvider;
import com.zeido.mohannad.timer.tea.teatimer.Database.Tea;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private List<Tea> teaList = SampleDataProvider.teaList;
    private TeaItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final FloatingActionButton timerButton = findViewById(R.id.addTeaButton);
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddTeaActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        mAdapter = new TeaItemAdapter(this, teaList);

        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Tea Added", Toast.LENGTH_SHORT).show();
        }

    }
}
