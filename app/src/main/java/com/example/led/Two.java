package com.example.led;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Two extends Fragment {

    private int led, t, oc1, oc2;
    private TimePicker tp;
    private EditText tv;

    public static Two newInstance() {
        Two fragment = new Two();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        tv = view.findViewById(R.id.textView4);
        Button bt1 = view.findViewById(R.id.button1);
        Button bt2 = view.findViewById(R.id.button2);
        tp = view.findViewById(R.id.timepicker);
        Spinner s = view.findViewById(R.id.spinner);
        Spinner s1 = view.findViewById(R.id.spinner1);
        Spinner s2 = view.findViewById(R.id.spinner2);
        Spinner s3 = view.findViewById(R.id.spinner3);
        //设置灯光选择下拉菜单
        final String[] LED = new String[]{"灯3", "灯4", "灯5", "灯6", "灯7", "灯8"};
        s.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, LED));
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                led = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                led = 0;
            }
        });
        //设置开启关闭下拉菜单
        final String[] oc = new String[]{"开启", "关闭"};
        s1.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, oc));
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oc1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                oc1 = 0;
            }
        });
        //设置时间单位下拉菜单
        final String[] time = new String[]{"秒", "分钟", "小时"};
        s2.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, time));
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                t = 0;
            }
        });
        //设置开启关闭下拉菜单
        s3.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, oc));
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oc2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                oc2 = 0;
            }
        });
        final Timer timer = new Timer();
        final String a[][] = {{"a", "b"}, {"c", "d"}, {"e", "f"}, {"g", "h"}, {"i", "j"}, {"k", "l"}};
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//new acp().start();
                final int i = led, j = oc1;
                int k = (oc1 == 0 ? 1 : 0);
                HomeActivity.fasong(a[i][k]);
                Message msg = new Message();
                msg.what = 1;
                Bundle bundle = new Bundle();
                bundle.putInt("i", i);  //往Bundle中存放数据
                bundle.putInt("j", k);  //往Bundle中存放数据
                msg.setData(bundle);    //mes利用Bundle传递数据
                One.h.sendMessage(msg); //用activity中的handler发送消息
                TimerTask task = new TimerTask() {
                    public void run() {
                        HomeActivity.fasong(a[i][j]);
                        final Message msg = new Message();
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putInt("i", i);  //往Bundle中存放数据
                        bundle.putInt("j", j);  //往Bundle中存放数据
                        msg.setData(bundle);    //mes利用Bundle传递数据
                        One.h.sendMessage(msg); //用activity中的handler发送消息
                        final String s = "已为您" + oc[j] + "灯光" + (i + 3);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                };
                int s = Integer.parseInt(tv.getText().toString());
                if (t == 1) s *= 60;
                if (t == 2) s *= 3600;
                timer.schedule(task, s * 1000);
                String str = "已为您" + oc[k] + "灯光" + (i + 3) + "并设置在" + s + time[t] + "后" + oc[j] + "灯光" + (i + 3);
                Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
            }
        });
        //设置时间选择器
        tp.setIs24HourView(true);
        int m = tp.getCurrentMinute() + 5;//默认延迟5分钟
        if (m > 59) {
            tp.setCurrentHour(tp.getCurrentHour() + 1);
            tp.setCurrentMinute(m - 60);
        } else tp.setCurrentMinute(m);

        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//new acp().start();
                final int i = led, j = oc2;
                int tph = tp.getCurrentHour(), tpm = tp.getCurrentMinute();
                Calendar cal = Calendar.getInstance();
                int h = tph - cal.get(Calendar.HOUR_OF_DAY);
                int m = tpm - cal.get(Calendar.MINUTE) - 1;
                int s = 60 - cal.get(Calendar.SECOND);
                if (m < 0) {
                    m += 60;
                    h -= 1;
                }
                if (h < 0) h += 24;
                s += m * 60 + h * 3600;
                TimerTask task = new TimerTask() {
                    public void run() {
                        HomeActivity.fasong(a[i][j]);
                        Message msg = new Message();
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putInt("i", i);  //往Bundle中存放数据
                        bundle.putInt("j", j);  //往Bundle中存放数据
                        msg.setData(bundle);//mes利用Bundle传递数据
                        One.h.sendMessage(msg);//用activity中的handler发送消息
                        final String s = "已为您" + oc[j] + "灯光" + (i + 3);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                };
                timer.schedule(task, s * 1000);
                String str = "已为您设置在" + tph + "点" + tpm + "分" + oc[j] + "灯光" + (i + 3);
                Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}