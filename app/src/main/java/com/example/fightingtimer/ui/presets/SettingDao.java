package com.example.fightingtimer.ui.presets;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SettingDao {
    @Insert
    public void insert(Setting... settings);

    @Update
    public void update(Setting... settings);

    @Delete
    public void delete(Setting setting);

    @Query("SELECT * FROM setting")
    List<Setting> getAll();
}