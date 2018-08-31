package com.smf.xxy.androidsql;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
    TextView  RecordContent;
    public  static String account;
    int year;int month;int day;int hour;int minute;String month1;String day1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainwindow);
        instance3=this;
        textView=findViewById(R.id.textView1);
        RecordContent=findViewById(R.id.RecordText);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        account=pref.getString("account","");
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
    public void Insert(View view) {
        //获取系统的日期
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        if(month<10){month1="0"+month;}
        else month1 = ""+ month +"";
        day = calendar.get(Calendar.DAY_OF_MONTH);
        if(day<10){day1="0"+day;}
        else day1 = ""+ day +"";
        minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String date=year+"-"+month1+"-"+day1;
        String date1=year+month1+day1;
        String time=" "+hour+":"+minute+":"+second;
        test(date,time,date1);
    }
    private void test(final String date, final String time,final String date1)
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String sql = "insert into WorkRecord " +
                            "([Name],[RecordNo],[OutTime],[ArriveTime],[LeaveTime],[WorkTime],[ExtraTime],[TrafficTime],[City],[CShortName],[Contector],[JobContent],[Uncompleted]" +
                            ",[Remark],[ReportNo],[TypeDate],[LeaderRemark],[IsLeaderScore],[LeaderScoreDate],[Partner],[Abandoner],[AbandonDate],[Out],[OutKm],[Arrive]" +
                            ",[ArriveKm],[Actual],[RRemark],[Allowance],[Solveway]) values " +
                            "('季杭蕾','"+ date1+"01',"+
                            "'1900-01-01','"+date+" 8:00:0',"+
                            "'"+date +time+"',"+
                            "'1900-01-01','1900-01-01','1900-01-01','宁波','','','其他','','','无'," +
                            "'"+date+time+"',"+
                            "'','否','1900-01-01','','','1900-01-01','',0,'',0,0,'',0," +
                            "'"+RecordContent.getText().toString()+"')";
                    try {
                        String ret = DBUtil.Record(sql);
                        if(ret.equals("查询数据异常!")){msg.what=1002;}
                        else msg.what=1001;
                        Bundle data = new Bundle();
                        msg.setData(data);
                        mHandler.sendMessage(msg);
                    }
                    catch(Exception e){
                        msg.what=1003;mHandler.sendMessage(msg);
                        return;
                    }

                }
                catch (Exception e){
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("result", e.getMessage());
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                }
                finally { }
            }
        };
        new Thread(run).start();
    }
    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1001:
                    Toast.makeText(MainWindow.this,"保存成功~",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(MainActivity.this,MainWindow.class));
                    //MainActivity.this.finish();
                    break;

                case 1002:
                    Toast.makeText(MainWindow.this,"今天的工作记录已经保存过啦！",Toast.LENGTH_SHORT).show();
                    //Password.setText("") ;
                    break;
                case 1003:
                    Toast.makeText(MainWindow.this,"网络连接失败！",Toast.LENGTH_SHORT).show();
                    break;
                case 1004:
                    //Toast.makeText(MainActivity.this,"当前是最新版本~",Toast.LENGTH_SHORT).show();
                    break;

                case 1005:
                    //Toast.makeText(MainActivity.this,"请更新至最新版本",Toast.LENGTH_SHORT).show();
                    break;
            }
        };
    };

    public void change(View view) {
        startActivity(new Intent(MainWindow.this,CompleteRecord1.class));
        //MainWindow.this.finish();
    }
}
