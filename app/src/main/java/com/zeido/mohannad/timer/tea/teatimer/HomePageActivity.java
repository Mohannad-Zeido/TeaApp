package com.zeido.mohannad.timer.tea.teatimer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zeido.mohannad.timer.tea.teatimer.Database.Tea;
import com.zeido.mohannad.timer.tea.teatimer.Database.TeaViewModel;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private TeaListAdapter mAdapter;
    private TeaViewModel mTeaViewModel;

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

        mTeaViewModel = ViewModelProviders.of(this).get(TeaViewModel.class);

        mTeaViewModel.getmAllTeas().observe(this, new Observer<List<Tea>>() {
            @Override
            public void onChanged(@Nullable List<Tea> teas) {
                mAdapter.setTeas(teas);
            }
        });
        mAdapter = new TeaListAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Tea tea = (Tea) data.getSerializableExtra("teaObject");
            mTeaViewModel.insert(tea);
            Toast.makeText(this, "Tea Added", Toast.LENGTH_SHORT).show();
        }

    }
}
