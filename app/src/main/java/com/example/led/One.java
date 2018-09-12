package com.example.led;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Locale;

public class One extends Fragment {

    static Handler h;
    private final String[] LED = new String[]{"灯3", "灯4", "灯5", "灯6", "灯7", "灯8"};
    private int led3, led4, led5, led6, led7, led8, led, welcome;
    private ToggleButton bt1, bt2, bt3, bt4, bt5, bt6;
    private ImageView im1, im2, im3, im4, im5, im6;
    private TextView tv6;
    //滑动条监听器
    private final SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            tv6.setText(String.format(getString(R.string.light), progress));
        }
    };
    private SeekBar seek;

    public static One newInstance(int a, int b, int c, int d, int e, int f) {
        One fragment = new One();
        Bundle args = new Bundle();
        args.putInt("led3", a);
        args.putInt("led4", b);
        args.putInt("led5", c);
        args.putInt("led6", d);
        args.putInt("led7", e);
        args.putInt("led8", f);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            led3 = getArguments().getInt("led3");
            led4 = getArguments().getInt("led4");
            led5 = getArguments().getInt("led5");
            led6 = getArguments().getInt("led6");
            led7 = getArguments().getInt("led7");
            led8 = getArguments().getInt("led8");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        tv6 = view.findViewById(R.id.textView6);
        Spinner s = view.findViewById(R.id.spinner);
        //设置图片
        im1 = view.findViewById(R.id.imageView1);
        im2 = view.findViewById(R.id.imageView2);
        im3 = view.findViewById(R.id.imageView3);
        im4 = view.findViewById(R.id.imageView4);
        im5 = view.findViewById(R.id.imageView5);
        im6 = view.findViewById(R.id.imageView6);
        //设置按键
        bt1 = view.findViewById(R.id.button1);
        bt2 = view.findViewById(R.id.button2);
        bt3 = view.findViewById(R.id.button3);
        bt4 = view.findViewById(R.id.button4);
        bt5 = view.findViewById(R.id.button5);
        bt6 = view.findViewById(R.id.button6);
        Button bt7 = view.findViewById(R.id.button7);
        Button bt8 = view.findViewById(R.id.button8);
        Button bt9 = view.findViewById(R.id.button12);
        //设置滑动条
        seek = view.findViewById(R.id.seekBar);
        seek.setProgress(led3);
        seek.setOnSeekBarChangeListener(seekListener);
        tv6.setText(String.format(getString(R.string.light), led3));
        //设置监听器
        bt1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) HomeActivity.fasong(isChecked ? "a" : "b");
                im1.setImageResource(isChecked ? R.drawable.on : R.drawable.off);
                led3 = (isChecked ? 100 : 0);
                if (led == 0) seek.setProgress(led3);
            }
        });
        bt2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) HomeActivity.fasong(isChecked ? "c" : "d");
                im2.setImageResource(isChecked ? R.drawable.on : R.drawable.off);
                led4 = (isChecked ? 100 : 0);
                if (led == 1) seek.setProgress(led4);
            }
        });
        bt3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) HomeActivity.fasong(isChecked ? "e" : "f");
                im3.setImageResource(isChecked ? R.drawable.on : R.drawable.off);
                led5 = (isChecked ? 100 : 0);
                if (led == 2) seek.setProgress(led5);
            }
        });
        bt4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) HomeActivity.fasong(isChecked ? "g" : "h");
                im4.setImageResource(isChecked ? R.drawable.on : R.drawable.off);
                led6 = (isChecked ? 100 : 0);
                if (led == 3) seek.setProgress(led6);
            }
        });
        bt5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) HomeActivity.fasong(isChecked ? "i" : "j");
                im5.setImageResource(isChecked ? R.drawable.on : R.drawable.off);
                led7 = (isChecked ? 100 : 0);
                if (led == 4) seek.setProgress(led7);
            }
        });
        bt6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) HomeActivity.fasong(isChecked ? "k" : "l");
                im6.setImageResource(isChecked ? R.drawable.on : R.drawable.off);
                led8 = (isChecked ? 100 : 0);
                if (led == 5) seek.setProgress(led8);
            }
        });
        bt7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//new acp().start();
                HomeActivity.fasong("m");
                bt1.setChecked(true);
                bt2.setChecked(true);
                bt3.setChecked(true);
                bt4.setChecked(true);
                bt5.setChecked(true);
                bt6.setChecked(true);
                Toast.makeText(getContext(), "已为您开启全部灯光", Toast.LENGTH_SHORT).show();
            }
        });
        bt8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//new acp().start();
                HomeActivity.fasong("n");
                bt1.setChecked(false);
                bt2.setChecked(false);
                bt3.setChecked(false);
                bt4.setChecked(false);
                bt5.setChecked(false);
                bt6.setChecked(false);
                Toast.makeText(getContext(), "已为您关闭全部灯光", Toast.LENGTH_SHORT).show();
            }
        });

        s.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, LED));
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                led = position;
                switch (led) {
                    case 0:
                        seek.setProgress(led3);
                        break;
                    case 1:
                        seek.setProgress(led4);
                        break;
                    case 2:
                        seek.setProgress(led5);
                        break;
                    case 3:
                        seek.setProgress(led6);
                        break;
                    case 4:
                        seek.setProgress(led7);
                        break;
                    case 5:
                        seek.setProgress(led8);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bt9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//new acp().start();
                try {
                    int i = seek.getProgress();
                    String progress = String.format(Locale.CHINESE, "%03d", i);
                    HomeActivity.fasong(led + "" + progress);
                    switch (led) {
                        case 0:
                            bt1.setChecked(i != 0);
                            led3 = i;
                            break;
                        case 1:
                            bt2.setChecked(i != 0);
                            led4 = i;
                            break;
                        case 2:
                            bt3.setChecked(i != 0);
                            led5 = i;
                            break;
                        case 3:
                            bt4.setChecked(i != 0);
                            led6 = i;
                            break;
                        case 4:
                            bt5.setChecked(i != 0);
                            led7 = i;
                            break;
                        case 5:
                            bt6.setChecked(i != 0);
                            led8 = i;
                            break;
                    }
                    seek.setProgress(i);
                    String s = "已为您设置灯光" + (led + 3) + "的亮度为" + i;
                    Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        h = new Handler() {
            public void handleMessage(Message msg) {
                // call update gui method.
                switch (msg.what) {
                    case 1:
                        //接受msg传递过来的参数
                        int i = msg.getData().getInt("i");
                        int j = msg.getData().getInt("j");
                        switch (i) {
                            case 0:
                                bt1.setChecked(j == 0);
                                break;
                            case 1:
                                bt2.setChecked(j == 0);
                                break;
                            case 2:
                                bt3.setChecked(j == 0);
                                break;
                            case 3:
                                bt4.setChecked(j == 0);
                                break;
                            case 4:
                                bt5.setChecked(j == 0);
                                break;
                            case 5:
                                bt6.setChecked(j == 0);
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        ledstate();//设置灯光状态
        Welcome();//迎宾功能
        return view;
    }

    //设置灯光状态
    private void ledstate() {
        if (led3 != 0) {
            int i = led3;
            bt1.setChecked(true);
            led3 = i;
        }
        if (led4 != 0) {
            int i = led4;
            bt2.setChecked(true);
            led4 = i;
        }
        if (led5 != 0) {
            int i = led5;
            bt3.setChecked(true);
            led5 = i;
        }
        if (led6 != 0) {
            int i = led6;
            bt4.setChecked(true);
            led6 = i;
        }
        if (led7 != 0) {
            int i = led7;
            bt5.setChecked(true);
            led7 = i;
        }
        if (led8 != 0) {
            int i = led8;
            bt6.setChecked(true);
            led8 = i;
        }
    }

    //迎宾功能
    private void Welcome() {
        if (welcome == 0) {
            welcome++;
            SharedPreferences sp = getContext().getSharedPreferences("mysetting.txt", Context.MODE_PRIVATE);
            if (sp.getBoolean("welcome", false)) {
                Calendar cal = Calendar.getInstance();
                int h = sp.getInt("startHour", 18) - cal.get(Calendar.HOUR_OF_DAY);
                int m = sp.getInt("startMinute", 0) - cal.get(Calendar.MINUTE);
                if (h < 0 || (h == 0 && m <= 0)) {
                    String a[][] = {{"a", "b"}, {"c", "d"}, {"e", "f"}, {"g", "h"}, {"i", "j"}, {"k", "l"}};
                    int i = sp.getInt("led", 0);
                    HomeActivity.fasong(a[i][0]);
                    Message msg = new Message();
                    msg.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putInt("i", i);  //往Bundle中存放数据
                    bundle.putInt("j", 0);  //往Bundle中存放数据
                    msg.setData(bundle);//mes利用Bundle传递数据
                    One.h.sendMessage(msg);//用activity中的handler发送消息
                    Toast.makeText(getContext(), "迎宾功能已为您开启", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}