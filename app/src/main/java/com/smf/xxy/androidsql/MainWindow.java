package com.smf.xxy.androidsql;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import cn.waps.AppConnect;

public class MainWindow extends Activity {
    public static MainWindow instance3 = null;
    private SharedPreferences pref;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainwindow);
        instance3=this;
        textView=findViewById(R.id.textView1);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        String account=pref.getString("account","");
        String tiexinwenhouyu="";
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年int year = calendar.get(Calendar.YEAR);
//月int month = calendar.get(Calendar.MONTH)+1;
//日int day = calendar.get(Calendar.DAY_OF_MONTH);
//获取系统时间
//分钟int minute = calendar.get(Calendar.MINUTE);
//秒int second = calendar.get(Calendar.SECOND);
//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour<=5 & hour>3){ tiexinwenhouyu="真早啊~"; }
        else if(hour<=8 & hour>5){ tiexinwenhouyu="早上好~"; }
        else if(hour<=11 & hour>8){ tiexinwenhouyu="上午好~"; }
        else if(hour<=13 & hour>11){ tiexinwenhouyu="中午好~"; }
        else if(hour<=18 & hour>13){ tiexinwenhouyu="下午好~"; }
        else if(hour<=23 & hour>18){ tiexinwenhouyu="晚上好~"; }
        else if(hour>23||hour<=3){ tiexinwenhouyu="夜深啦~"; }
        textView.setText(account+","+tiexinwenhouyu);
    }


    private long firstTime=0;  //记录第几次点击返回
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()-firstTime>2000){
            Toast.makeText(MainWindow.this,"再次点击返回退出",Toast.LENGTH_SHORT).show();
            firstTime=System.currentTimeMillis();
        }else{
            MainWindow.this.finish();
            StartActivity.instance.finish();
            MainActivity.instance2.finish();
            AppConnect.getInstance(this).close();
            System.exit(0);
        }
    }

    public void BackWay(View view) {
        startActivity(new Intent(MainWindow.this,MainActivity.class));
        MainWindow.this.finish();
    }
}
