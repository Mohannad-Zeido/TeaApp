package com.zeido.mohannad.timer.tea.teatimer.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TeaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Tea tea);

    @Delete
    public void delete(Tea tea);

    @Update
    public void update(Tea tea);

    @Query("DELETE FROM teas")
    public void deleteAll();

    @Query("SELECT * FROM teas")
    public List<Tea> getAllTeas();

}
