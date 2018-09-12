package com.example.led;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private static Handler h;
    private static OutputStream os;
    private static BufferedReader br;
    MenuItem menuItem;
    private int led3, led4, led5, led6, led7, led8;

    //发送函数
    static void fasong(final String s) {//发送函数
        class MyThread extends Thread {
            public void run() {
                try {
                    os.write((s + "\n").getBytes("utf-8"));
                    String str = br.readLine();
                    Message msg = new Message();
                    msg.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("text1", "收到：" + str);  //往Bundle中存放数据
                    msg.setData(bundle);//mes利用Bundle传递数据
                    h.sendMessage(msg);//用activity中的handler发送消息
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        new MyThread().start();
    }

    //获取灯光状态
    private void ledstate(String s) {
        led3 = Integer.parseInt(s.substring(0, 3));
        led4 = Integer.parseInt(s.substring(3, 6));
        led5 = Integer.parseInt(s.substring(6, 9));
        led6 = Integer.parseInt(s.substring(9, 12));
        led7 = Integer.parseInt(s.substring(12, 15));
        led8 = Integer.parseInt(s.substring(15, 18));
    }

    //关灯提醒功能
    private void Alert() {
        SharedPreferences sp = getSharedPreferences("mysetting.txt", Context.MODE_PRIVATE);
        if (sp.getBoolean("alert", false)) {
            int bh = sp.getInt("beginHour", 8);
            int bm = sp.getInt("beginMinute", 0);
            int eh = sp.getInt("endHour", 16);
            int em = sp.getInt("endMinute", 0);
            Calendar cal = Calendar.getInstance();
            int h = cal.get(Calendar.HOUR_OF_DAY);
            int m = cal.get(Calendar.MINUTE);
            if ((bh < h && h < eh) || (bh == h && bm <= m) || (h == eh && m <= em)) {
                String s = "";
                if (led3 != 0)
                    s += "灯光3";
                if (led4 != 0)
                    s += "灯光4";
                if (led5 != 0)
                    s += "灯光5";
                if (led6 != 0)
                    s += "灯光6";
                if (led7 != 0)
                    s += "灯光7";
                if (led8 != 0)
                    s += "灯光8";
                if (!s.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
                    builder.setTitle("关灯提醒功能"); //设置标题
                    builder.setMessage("当前时间" + s + "尚未关闭，请您按需关闭灯光，注意节约用电！"); //设置内容
                    //builder.create();
                    builder.create().show();
                }
            }
        }
    }

    //设置页面
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(One.newInstance(led3, led4, led5, led6, led7, led8));
        adapter.addFragment(new Two());
        adapter.addFragment(new Three());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.item_lib:
                                viewPager.setCurrentItem(0);
                                return true;
                            case R.id.item_find:
                                viewPager.setCurrentItem(1);
                                return true;
                            case R.id.item_more:
                                viewPager.setCurrentItem(2);
                                return true;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        final TextView tv = findViewById(R.id.textView1);
        //连接WiFi
        try {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                    detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                DhcpInfo dhcp = wifi.getDhcpInfo();
                String serverAddress = Formatter.formatIpAddress(dhcp.serverAddress);
                Socket mykehu = new Socket();
                mykehu.connect(new InetSocketAddress(serverAddress, 333), 1000);
                mykehu.setKeepAlive(true);
                os = mykehu.getOutputStream();
                br = new BufferedReader(new InputStreamReader(mykehu.getInputStream()));
                String str = br.readLine();
                ledstate(str);
                tv.setText(R.string.server_connect);
                Alert();//关灯提醒功能
            } else {
                tv.setText(R.string.wifi_disconnect);
                String str = "当前尚未连接系统WiFi，请连接到系统WiFi后再打开本软件";
                Toast.makeText(this, str, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            tv.setText(R.string.server_disconnect);
            String str = "与服务器连接超时，请连接到系统WiFi后再打开本软件";
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }

        setupViewPager(viewPager);

        h = new Handler() {
            public void handleMessage(Message msg) {
                // call update gui method.
                switch (msg.what) {
                    case 1:
                        String str = msg.getData().getString("text1");//接受msg传递过来的参数
                        tv.setText(str);
                        break;
                    default:
                        break;
                }
            }
        };
    }

}