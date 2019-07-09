package com.example.fightingtimer.ui.presets;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CurrentSettingDao {
    @Insert
    public void insert(CurrentSetting setting);

    @Update
    public void update(CurrentSetting setting);

    @Query("SELECT * FROM CurrentSetting WHERE uid LIKE 1")
    CurrentSetting get();
}
