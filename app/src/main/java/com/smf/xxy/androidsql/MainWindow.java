package com.smf.xxy.androidsql;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainWindow extends Activity {
    public static MainWindow instance3 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainwindow);
        instance3=this;
    }
    /**
     * 保存用户名和密码的业务方法
     */
    public static boolean saveUserInfo(String username, String password) {
        try {
            // 使用当前项目的绝对路径
            File file = new File("data/data/com.smf.xxy.androidsql/info.txt");
            // 创建输出流对象
            FileOutputStream fos = new FileOutputStream(file);
            // 向文件中写入信息
            fos.write((username + "##" + password).getBytes());
            // 关闭输出流对象
            fos.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException();
        }
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
            System.exit(0);
        }
    }
}
