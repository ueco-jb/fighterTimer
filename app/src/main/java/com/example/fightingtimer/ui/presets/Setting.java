package com.example.fightingtimer.ui.presets;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Setting {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "round")
    public int rnd;

    @ColumnInfo(name = "break")
    public int brk;
}