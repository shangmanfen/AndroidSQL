package com.smf.xxy.androidsql;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.waps.AppConnect;

public class success extends Activity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#F595ABF1"));
        }
        setContentView(R.layout.activity_success);
        pref= PreferenceManager.getDefaultSharedPreferences(this);editor=pref.edit();
        GoToTExpense();
        ImageButton back=findViewById(R.id.back);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setBackgroundResource(R.drawable.backpictruepress);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //改为抬起时的图片
                    v.setBackgroundResource(R.drawable.backpictrue);
                }
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(success.this,Choose_Main.class));
                success.this.finish();FlagEmpty();
            }
        });
    }
    private void GoToTExpense(){
        Boolean GoToTExpense=pref.getBoolean("GoToTExpense",false);
        if(GoToTExpense)
            remindTExpenseIn();
    }
    private void remindTExpenseIn(){
        AlertDialog.Builder builder = new AlertDialog.Builder(success.this);
        builder.setIcon(R.drawable.logo);
        builder.setMessage("是否立即填写“差旅费”？");
        builder.setPositiveButton("立即去填写~", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                FlagEmpty();
                startActivity(new Intent(success.this,TExpenseIn1.class));
                success.this.finish();
            }
        });
        builder.setNegativeButton("不需要了哦。", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                editor.putString("RecordNo","").commit();
            }
        });
        //    显示出该对话框
        builder.show();
    }
    private void FlagEmpty(){
        editor.putBoolean("GoToTExpense",false).commit();
    }
    @Override
    public void onBackPressed() {
        //startActivity(new Intent(success.this,Choose_Main.class));
        success.this.finish();FlagEmpty();
    }
}
