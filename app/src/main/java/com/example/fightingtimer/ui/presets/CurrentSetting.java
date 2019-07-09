package com.example.fightingtimer.ui.presets;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CurrentSetting {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "round")
    public int rnd;

    @ColumnInfo(name = "break")
    public int brk;
}