package com.zeido.mohannad.timer.tea.teatimer.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TeaRepository {

    private TeaDao mTeaDao;
    private LiveData<List<Tea>> mAllTeas;

    public TeaRepository(Application application){
        TeaRoomDatabase db = TeaRoomDatabase.getDatabase(application);
        mTeaDao = db.teaDao();
        mAllTeas = mTeaDao.getAllTeas();
    }

    public LiveData<List<Tea>> getmAllTeas(){
        return mAllTeas;
    }

    public void insert(Tea tea){
        new insertAsyncTask(mTeaDao).execute(tea);
    }

    private static class insertAsyncTask extends AsyncTask<Tea, Void, Void> {
        private TeaDao mAsyncTaskDao;

        insertAsyncTask(TeaDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Tea... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
