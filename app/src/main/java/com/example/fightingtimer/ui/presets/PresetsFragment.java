package com.example.fightingtimer.ui.presets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.example.fightingtimer.CurrentSettingDatabase;
import com.example.fightingtimer.PresetsDatabase;
import com.example.fightingtimer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PresetsFragment extends Fragment {
    FloatingActionButton add_preset_button;
    View root;
    PresetsDatabase presetsDatabase;
    CurrentSettingDatabase currentSettingDatabase;

    private void addPresetButton(final Setting setting) {
        final Button new_preset = new Button(getActivity());
        new_preset.setId(setting.uid);
        new_preset.setText(setting.name + "  " + setting.rnd + " / " + setting.brk);
        LinearLayout ll = root.findViewById(R.id.Linear_preset_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(new_preset, lp);

        new_preset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentSettingDao currentSettingDao = currentSettingDatabase.get();
                CurrentSetting currentSetting = currentSettingDao.get();
                if (currentSetting == null) {
                    currentSetting.rnd = setting.rnd * 1000;
                    currentSetting.brk = setting.brk * 1000;
                    currentSettingDao.insert(currentSetting);
                } else {
                    currentSetting.rnd = setting.rnd * 1000;
                    currentSetting.brk = setting.brk * 1000;
                    currentSettingDao.update(currentSetting);
                }
                Navigation.findNavController(view).navigate(R.id.navigation_timer);
            }
        });
        new_preset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder remove_dialog = new AlertDialog.Builder(getActivity());
                remove_dialog.setTitle(R.string.remove_preset_question);
                remove_dialog.setCancelable(true);
                remove_dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SettingDao settingDao = presetsDatabase.getAll();
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

    private void addPreset(final String name, final int round_val, final int break_val) {
        SettingDao settingDao = presetsDatabase.getAll();
        Setting setting = new Setting();
        setting.name = name;
        setting.rnd = round_val;
        setting.brk = break_val;
        settingDao.insert(setting);
    }

    private void refreshPresets() {
        SettingDao settingDao = presetsDatabase.getAll();
        List<Setting> settings = settingDao.getAll();
        for (Setting set : settings) {
            if (root.findViewById(set.uid) == null) {
                addPresetButton(set);
            }
        }
    }

    private int edittextToInt(final EditText val) {
        String valstr = val.getText().toString();
        return Integer.parseInt(valstr);
    }

    private void addPreset() {
        final Context context = getActivity();
        if (context == null) {
            return;
        }
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.preset_dialog);
        dialog.setTitle("Add preset");

        Button cancel_button = dialog.findViewById(R.id.Cancel_b);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPresets();
                dialog.dismiss();
            }
        });

        Button add_button = dialog.findViewById(R.id.Add_b);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText preset_name_input = dialog.findViewById(R.id.Preset_name_i);
                String preset_name = preset_name_input.getText().toString();
                EditText round_value_input = dialog.findViewById(R.id.Round_value_i);
                String round_value = round_value_input.getText().toString();
                EditText break_value_input = dialog.findViewById(R.id.Break_value_i);
                String break_value = break_value_input.getText().toString();
                if (TextUtils.isEmpty(preset_name)) {
                    preset_name_input.setError(getResources().getString(R.string.field_cannot_be_empty));
                    return;
                } else if (TextUtils.isEmpty(round_value)) {
                    round_value_input.setError(getResources().getString(R.string.field_cannot_be_empty));
                    return;
                } else if (TextUtils.equals(round_value, "0")) {
                    round_value_input.setError(getResources().getString(R.string.value_zero));
                    return;
                } else if (TextUtils.isEmpty(break_value)) {
                    break_value_input.setError(getResources().getString(R.string.field_cannot_be_empty));
                    return;
                } else if (TextUtils.equals(break_value, "0")) {
                    break_value_input.setError(getResources().getString(R.string.value_zero));
                    return;
                }
                addPreset(preset_name, edittextToInt(round_value_input), edittextToInt(break_value_input));
                refreshPresets();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_presets, container, false);
        presetsDatabase = Room.databaseBuilder(getActivity(), PresetsDatabase.class, "presetsdb")
                .allowMainThreadQueries()
                .build();
        refreshPresets();
        currentSettingDatabase = Room.databaseBuilder(getActivity(), CurrentSettingDatabase.class, "currentsettingdb")
                .allowMainThreadQueries()
                .build();

        add_preset_button = root.findViewById(R.id.Add_preset_b);
        add_preset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPreset();
            }
        });

        return root;
    }
}