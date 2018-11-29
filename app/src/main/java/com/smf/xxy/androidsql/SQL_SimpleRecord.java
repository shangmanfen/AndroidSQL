package com.smf.xxy.androidsql;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class SQL_SimpleRecord extends Activity {
    public static SQL_SimpleRecord instance3 = null;
    private SharedPreferences pref;
    TextView textView;
    TextView  RecordContent;
    String account1;
    int year;int month;int day;int hour;int minute;String month1;String day1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#F595ABF1"));
        }
        setContentView(R.layout.activity_mainwindow);
        instance3=this;
        textView=findViewById(R.id.textView1);
        RecordContent=findViewById(R.id.RecordText);
        pref= getDefaultSharedPreferences(this);
        account1=pref.getString("account","");
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
        textView.setText(account1+","+tiexinwenhouyu);
    }

    public void BackWay(View view) {
        WorkRecordChoose.describe.setClickable(true);
        WorkRecordChoose.introduce.setClickable(true);
        SQL_SimpleRecord.this.finish();
    }
    public void Insert(View view) {
        if (RecordContent.getText().toString().trim().equals("")){
            Toast.makeText(SQL_SimpleRecord.this,"工作记录不可为空！",Toast.LENGTH_SHORT).show();
        }
        else {
            //获取系统的日期
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            if (month < 10) {
                month1 = "0" + month;
            } else month1 = "" + month + "";
            day = calendar.get(Calendar.DAY_OF_MONTH);
            if (day < 10) {
                day1 = "0" + day;
            } else day1 = "" + day + "";
            minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            String date = year + "-" + month1 + "-" + day1;
            String date1 = year + month1 + day1;
            String time = " " + hour + ":" + minute + ":" + second;
            SharedPreferences.Editor editor1=pref.edit();
            editor1.putString("WWorkRecordNo",year+month1+day1+"01").commit();
            test(date, time, date1);
        }
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
                            "('"+account1+"','"+ date1+"01',"+
                            "'1900-01-01','"+date+" 8:00:0',"+
                            "'"+date +time+"',"+
                            "'1900-01-01','1900-01-01','1900-01-01','宁波','','','其他','','','无'," +
                            "'"+date+time+"',"+
                            "'','否','1900-01-01','','','1900-01-01','',0,'',0,0,'',0," +
                            "'"+RecordContent.getText().toString()+"')";
                    try {
                        String ret = DBUtil.Record(sql);
                        if(ret.equals("操作失败")){msg.what=1002;}
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
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putBoolean("GoToTExpense",true).commit();
                    startActivity(new Intent(SQL_SimpleRecord.this,success.class));
                    SQL_SimpleRecord.this.finish();
                    break;

                case 1002:
                    SharedPreferences.Editor editor1=pref.edit();
                    editor1.putBoolean("GoToTExpense",false).commit();
                    Toast.makeText(SQL_SimpleRecord.this,"今天的工作记录已经保存过啦！",Toast.LENGTH_SHORT).show();
                    //Password.setText("") ;
                    break;
                case 1003:
                    Toast.makeText(SQL_SimpleRecord.this,"网络连接失败！",Toast.LENGTH_SHORT).show();
                    break;
            }
        };
    };

    public void change(View view) {
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("GoToTExpense",true).commit();
        startActivity(new Intent(SQL_SimpleRecord.this,CompleteRecord1.class));
        SQL_SimpleRecord.this.finish();
    }
}
