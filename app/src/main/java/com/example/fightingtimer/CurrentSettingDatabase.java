package com.example.fightingtimer;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.fightingtimer.ui.presets.CurrentSetting;
import com.example.fightingtimer.ui.presets.CurrentSettingDao;

@Database(entities = {CurrentSetting.class}, version = 1)
public abstract class CurrentSettingDatabase extends RoomDatabase {
    public abstract CurrentSettingDao get();
}
