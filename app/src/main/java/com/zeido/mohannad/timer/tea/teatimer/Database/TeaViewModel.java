package com.zeido.mohannad.timer.tea.teatimer.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TeaViewModel extends AndroidViewModel {

    private TeaRepository mTeaRepository;
    private LiveData<List<Tea>> mAllTeas;

    public TeaViewModel(Application application){
        super(application);
        mTeaRepository = new TeaRepository(application);
        mAllTeas = mTeaRepository.getmAllTeas();
    }

    public LiveData<List<Tea>> getmAllTeas(){
        return mAllTeas;
    }

    public void insert(Tea tea){
        mTeaRepository.insert(tea);
    }
}
