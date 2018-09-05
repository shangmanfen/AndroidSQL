package com.smf.xxy.androidsql;

import android.app.Activity;
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
    String version="V180905";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 让手机屏幕保持直立模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示
        setContentView(R.layout.activity_start);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        AppConnect.getInstance("53d4be2fd81e6be3da6e149067d4a17a","53d4be2fd81e6be3da6e149067d4a17a",this);

        LinearLayout adlayout =(LinearLayout)findViewById(R.id.AdLinearLayout);
        AppConnect.getInstance(this).showBannerAd(this, adlayout);
        instance=this;
        findVersion();
    }
    private void findVersion()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String sql = "SELECT [Version] FROM [Version]";
                    try {
                        String ret = FindVersion.QuerySQL(sql);
                        ret=ret.trim();
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
                            if(!account.equals("") & !password.equals(""))
                                startActivity(new Intent(StartActivity.this,MainChoose.class));
                            else
                                startActivity(new Intent(StartActivity.this,MainActivity.class));
                        }
                    };timer.schedule(timerTask,2000);
                    break;
                case 1005:
                    Toast.makeText(StartActivity.this,"请更新至最新版本",Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse("https://pan.baidu.com/s/1N86ljCmSa_je3NoSTBAS0g");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    StartActivity.this.finish();
                    break;
            }
        };
    };
}
