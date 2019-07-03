package com.example.fightingtimer.ui.presets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.fightingtimer.PresetsDatabase;
import com.example.fightingtimer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PresetsFragment extends Fragment {
    FloatingActionButton add_preset_button;
    View root;
    PresetsDatabase database;

    private void printSettings()
    {
        SettingDao settingDao = database.getAll();
        Log.d("SETTINGS", "Just before all settings");
        List<Setting> settings = settingDao.getAll();
        for (Setting set : settings)
        {
            Log.d(Integer.toString(set.uid), set.name + " " + Integer.toString(set.rnd) + "/" + Integer.toString(set.brk));
        }
        Log.d("SETTINGS", "DONE");
    }

    private void addPresetButton(final Setting setting)
    {
        final Button new_preset = new Button(getActivity());
        new_preset.setId(setting.uid);
        new_preset.setText(setting.name + "  " + setting.rnd + " / " + setting.brk);
        LinearLayout ll = root.findViewById(R.id.Linear_preset_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(new_preset, lp);

        new_preset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder remove_dialog = new AlertDialog.Builder(getActivity());
                remove_dialog.setTitle(R.string.remove_preset_question);
                remove_dialog.setCancelable(true);
                remove_dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SettingDao settingDao = database.getAll();
                        settingDao.delete(setting);
                        new_preset.setVisibility(View.GONE);
                    }
                });
                remove_dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        refreshPresets();
                    }
                });
                remove_dialog.show();
                return true;
            }
        });
    }

    private void addPreset(final String name, final int round_val, final int break_val)
    {
        SettingDao settingDao = database.getAll();
        Setting setting = new Setting();
        setting.name = name;
        setting.rnd = round_val;
        setting.brk = break_val;
        settingDao.insert(setting);
    }

    private void refreshPresets()
    {
        SettingDao settingDao = database.getAll();
        List<Setting> settings = settingDao.getAll();
        for (Setting set : settings)
        {
            if (root.findViewById(set.uid) == null)
            {
                addPresetButton(set);
            }
        }
    }

    private int edittextToInt(final EditText val)
    {
        String valstr = val.getText().toString();
        return Integer.parseInt(valstr);
    }

    private void addPreset()
    {
        final Context context = getActivity();
        if (context == null) {
            return;
        }
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.preset_dialog);
        dialog.setTitle("Add preset");

        Button cancel_button = dialog.findViewById(R.id.Cancel_b);
        cancel_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                refreshPresets();
                dialog.dismiss();
            }
        });

        Button add_button = dialog.findViewById(R.id.Add_b);
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText round_value_input = dialog.findViewById(R.id.Round_value_i);
                EditText break_value_input = dialog.findViewById(R.id.Break_value_i);
                addPreset("test name", edittextToInt(round_value_input), edittextToInt(break_value_input));
                refreshPresets();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_presets, container, false);
        database = Room.databaseBuilder(getActivity(), PresetsDatabase.class, "presetsdb")
                .allowMainThreadQueries()
                .build();
        refreshPresets();

        add_preset_button = root.findViewById(R.id.Add_preset_b);
        add_preset_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addPreset();
            }
        });
        return root;
    }
}