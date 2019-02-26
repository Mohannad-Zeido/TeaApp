package com.zeido.mohannad.timer.tea.teatimer.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Tea.class}, version = 1)
public abstract class TeaRoomDatabase extends RoomDatabase {
    public abstract TeaDao teaDao();

    private static volatile TeaRoomDatabase INSTANCE;

    static TeaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TeaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TeaRoomDatabase.class,
                            "teas_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
