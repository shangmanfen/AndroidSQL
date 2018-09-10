package com.smf.xxy.androidsql;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.waps.AppConnect;
import cn.waps.AppListener;

public class StartActivity extends Activity {
    public static StartActivity instance=null;
    private SharedPreferences pref;
    String version="V180909";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 让手机屏幕保持直立模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示
        setContentView(R.layout.activity_start);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        AppConnect.getInstance(this);

        LinearLayout adlayout =(LinearLayout)findViewById(R.id.AdLinearLayout);
        AppConnect.getInstance(this).showBannerAd(this, adlayout);
        instance=this;
        findVersion();
    }
    String VersionDetail;
    private void findVersion()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String sql = "SELECT [Version],[Detail] FROM [Version]";
                    try {
                        String ret = FindVersion.QuerySQL(sql);
                        ret=ret.trim();
                        VersionDetail=FindVersion.FindDetail(sql).trim();
                        //version=pref.getString("version","");
                        if(version.equals(ret))
                        {
                            msg.what=1004;
                        }
                        else
                        {
                            msg.what=1005;
                        }
                        Bundle data = new Bundle();
                        data.putString("result", ret);

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
    private void remindNew(){
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        //    设置Title的图标
        builder.setIcon(R.drawable.logo);
        builder.setTitle("检测到新版本");
        //    设置Content来显示一个信息
        builder.setMessage("更新："+VersionDetail);
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Uri uri = Uri.parse("https://pan.baidu.com/s/1N86ljCmSa_je3NoSTBAS0g");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                StartActivity.this.finish();
            }
        });
        //    设置一个NeutralButton
        builder.setNeutralButton("忽略", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                final String account=pref.getString("account","");
                final String password=pref.getString("password","");
                Timer timer=new Timer();
                TimerTask timerTask=new TimerTask() {
                    @Override
                    public void run() {
                        if(!account.equals("") & !password.equals("")){
                            startActivity(new Intent(StartActivity.this,MainChoose1.class));
                            StartActivity.this.finish();}
                        else {
                            startActivity(new Intent(StartActivity.this, MainActivity.class));
                            StartActivity.this.finish();
                        }
                    }
                };timer.schedule(timerTask,2000);
            }
        });
        //    显示出该对话框
        builder.show();
    }
    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1003:
                    Toast.makeText(StartActivity.this,"网络连接失败！",Toast.LENGTH_SHORT).show();
                    break;
                case 1004:
                    final String account=pref.getString("account","");
                    final String password=pref.getString("password","");
                    Timer timer=new Timer();
                    TimerTask timerTask=new TimerTask() {
                        @Override
                        public void run() {
                            if(!account.equals("") & !password.equals("")){
                                startActivity(new Intent(StartActivity.this,MainChoose1.class));
                                StartActivity.this.finish();}
                            else {
                                startActivity(new Intent(StartActivity.this, MainActivity.class));
                                StartActivity.this.finish();
                            }
                        }
                    };timer.schedule(timerTask,2000);
                    break;
                case 1005:
                    //Toast.makeText(StartActivity.this,"请更新至最新版本",Toast.LENGTH_LONG).show();
                    remindNew();
                    break;
            }
        };
    };
}
