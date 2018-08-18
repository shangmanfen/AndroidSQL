package com.smf.xxy.androidsql;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 让手机屏幕保持直立模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示
        setContentView(R.layout.activity_start);
        AppConnect.getInstance("53d4be2fd81e6be3da6e149067d4a17a","53d4be2fd81e6be3da6e149067d4a17a",this);

        LinearLayout adlayout =(LinearLayout)findViewById(R.id.AdLinearLayout);
        AppConnect.getInstance(this).showBannerAd(this, adlayout);

        instance=this;

            Timer timer=new Timer();
            TimerTask timerTask=new TimerTask() {
                @Override
                public void run() {
                    //Toast.makeText(StartActivity.this,"aaa",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StartActivity.this,MainActivity.class));
                }
            };timer.schedule(timerTask,2000);
    }
}
