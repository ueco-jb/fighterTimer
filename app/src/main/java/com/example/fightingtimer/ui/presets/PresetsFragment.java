package com.example.fightingtimer.ui.presets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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

    private void addButton(final int round_value, final int break_value)
    {
        Button new_preset = new Button(getActivity());
        new_preset.setText(round_value + "/" + break_value);
        LinearLayout ll = root.findViewById(R.id.Linear_preset_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(new_preset, lp);
    }

//    private void refreshPresets()

    private int edittextToInt(final EditText val)
    {
        String valstr = val.getText().toString();
        return Integer.parseInt(valstr);
    }

    private void addPreset(View view)
    {
        final Context context = getActivity();
        if (context == null) {
            return;
        }
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.preset_dialog);
        dialog.setTitle("Title...");

        Button cancel_button = dialog.findViewById(R.id.Cancel_b);
        cancel_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
            }
        });

        Button add_button = dialog.findViewById(R.id.Add_b);
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText round_value_input = dialog.findViewById(R.id.Round_value_i);
                EditText break_value_input = dialog.findViewById(R.id.Break_value_i);
                addButton(edittextToInt(round_value_input), edittextToInt(break_value_input));

                SettingDao settingDao = database.getAll();
                Setting setting = new Setting();
                setting.name = "Some name";
                setting.rnd = edittextToInt(round_value_input);
                setting.brk = edittextToInt(break_value_input);
                settingDao.insert(setting);

                Log.d("SETTINGS", "Just before all settings");
                List<Setting> settings = settingDao.getAll();
                for (Setting set : settings)
                {
                    Log.d(Integer.toString(set.uid), set.name + " " + Integer.toString(set.rnd) + "/" + Integer.toString(set.brk));
                }
                Log.d("SETTINGS", "DONE");

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

        add_preset_button = root.findViewById(R.id.Add_preset_b);
        add_preset_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addPreset(v);
            }
        });
        return root;
    }
}