package com.example.led;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Three extends Fragment {

    private SharedPreferences sp;
    private TimePicker tp1, tp2, tp3;
    private ToggleButton bt1, bt2;
    private Spinner s;
    private int led;

    public static Three newInstance() {
        Three fragment = new Three();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        bt1 = view.findViewById(R.id.button);
        bt2 = view.findViewById(R.id.button1);
        Button bt3 = view.findViewById(R.id.button2);
        s = view.findViewById(R.id.spinner);
        tp1 = view.findViewById(R.id.timepicker1);
        tp2 = view.findViewById(R.id.timepicker2);
        tp3 = view.findViewById(R.id.timepicker3);
        tp1.setIs24HourView(true);
        tp2.setIs24HourView(true);
        tp3.setIs24HourView(true);

        final String[] LED = new String[]{"灯3", "灯4", "灯5", "灯6", "灯7", "灯8"};
        s.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, LED));
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                led = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //读取配置文件
        settingStart();
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //存入数据
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("alert", bt1.isChecked());
                editor.putInt("beginHour", tp1.getCurrentHour());
                editor.putInt("beginMinute", tp1.getCurrentMinute());
                editor.putInt("endHour", tp2.getCurrentHour());
                editor.putInt("endMinute", tp2.getCurrentMinute());
                editor.putBoolean("welcome", bt2.isChecked());
                editor.putInt("led", led);
                editor.putInt("startHour", tp3.getCurrentHour());
                editor.putInt("startMinute", tp3.getCurrentMinute());
                editor.apply();
                Toast.makeText(getContext(), "保存设置成功", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    //读取配置文件
    private void settingStart() {
        sp = getContext().getSharedPreferences("mysetting.txt", Context.MODE_PRIVATE);
        bt1.setChecked(sp.getBoolean("alert", false));
        tp1.setCurrentHour(sp.getInt("beginHour", 8));
        tp1.setCurrentMinute(sp.getInt("beginMinute", 0));
        tp2.setCurrentHour(sp.getInt("endHour", 16));
        tp2.setCurrentMinute(sp.getInt("endMinute", 0));
        bt2.setChecked(sp.getBoolean("welcome", false));
        led = sp.getInt("led", 0);
        s.setSelection(led);
        tp3.setCurrentHour(sp.getInt("startHour", 18));
        tp3.setCurrentMinute(sp.getInt("startMinute", 0));
    }

}