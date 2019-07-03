package com.example.fightingtimer;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.fightingtimer.ui.presets.Setting;
import com.example.fightingtimer.ui.presets.SettingDao;

@Database(entities = {Setting.class}, version = 1)
public abstract class PresetsDatabase extends RoomDatabase {
    public abstract SettingDao getAll();
}
