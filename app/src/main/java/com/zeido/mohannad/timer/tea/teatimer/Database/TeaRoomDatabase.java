package com.zeido.mohannad.timer.tea.teatimer.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Tea.class}, version = 1)
public abstract class TeaRoomDatabase extends RoomDatabase {
    public abstract TeaDao teaDao();

    private static volatile TeaRoomDatabase INSTANCE;

    static TeaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TeaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TeaRoomDatabase.class,
                            "teas_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
        private final TeaDao mTeaDao;

        PopulateDbAsync(TeaRoomDatabase db){
            mTeaDao = db.teaDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            //Todo add something to check if they exist on the DB and if they do dont add.
            Tea tea = new Tea( "Black Tea", null, 10000, 100, "");
            mTeaDao.insert(tea);
            tea = new Tea( "Green Tea", null, 120000, 80, "");
            mTeaDao.insert(tea);
            tea = new Tea( "Oolong Tea", null, 120000, 100, "");
            mTeaDao.insert(tea);
            tea = new Tea( "White Tea", null, 120000, 100, "");
            mTeaDao.insert(tea);
            tea = new Tea( "Earl Grey", null, 120000, 100, "");
            mTeaDao.insert(tea);
            tea = new Tea( "Lady Earl Grey", null, 120000, 100, "");
            mTeaDao.insert(tea);
            return null;
        }
    }
}
