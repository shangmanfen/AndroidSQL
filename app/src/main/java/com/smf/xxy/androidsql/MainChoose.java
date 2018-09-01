package com.smf.xxy.androidsql;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import cn.waps.AppConnect;

public class MainChoose extends Activity {
    ImageButton simpleWorkRecord;
    ImageButton completeWorkRecord;
    private SharedPreferences pref1;
    public  static String account;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choose);
        textView=findViewById(R.id.textView1);
        pref1= PreferenceManager.getDefaultSharedPreferences(this);
        account=pref1.getString("account","");
        String tiexinwenhouyu="";
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
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
        simpleWorkRecord=findViewById(R.id.SimpleWorkRecord);
        completeWorkRecord=findViewById(R.id.completeWorkRecord);
        simpleWorkRecord.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //更改为按下时的背景图片
                    v.setBackgroundResource(R.drawable.sdzpress);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //改为抬起时的图片
                    v.setBackgroundResource(R.drawable.sdzunpress);
                }
                return false;
            }
        });
        completeWorkRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    v.setBackgroundResource(R.drawable.pkqpress);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //改为抬起时的图片
                    v.setBackgroundResource(R.drawable.pkqunpress);
                }
                return false;
            }
        });
    }


    public void goSimple(View view){
        startActivity(new Intent(MainChoose.this,MainWindow.class));
    }
    public void goComplete(View view){
        startActivity(new Intent(MainChoose.this,CompleteRecord1.class));
    }
    private long firstTime=0;  //记录第几次点击返回
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()-firstTime>2000){
            Toast.makeText(MainChoose.this,"再次点击返回退出",Toast.LENGTH_SHORT).show();
            firstTime=System.currentTimeMillis();
        }else{
            MainChoose.this.finish();
            StartActivity.instance.finish();
            MainActivity.instance2.finish();
            AppConnect.getInstance(this).close();
            System.exit(0);
        }
    }
}
