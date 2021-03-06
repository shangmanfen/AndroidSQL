package com.smf.xxy.androidsql;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import cn.waps.AppConnect;

public class Choose_WorkRecord extends Activity {
    ImageView buttonbg,textbg;
    public static ImageView describe,introduce;
    private SharedPreferences pref1;
    public  static String account,yingdaoflag;
    TextView textView;
    private SharedPreferences.Editor editor;
    ConstraintLayout yingdao3;
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#040814"));
        }
        setContentView(R.layout.activity_workrecord_choose);
        AppConnect.getInstance(this);
        textView=findViewById(R.id.textView1);
        pref1= PreferenceManager.getDefaultSharedPreferences(this);
        yingdao3=findViewById(R.id.yingdao3);
        account=pref1.getString("account","");
        yingdaoflag=pref1.getString("yingdao2","");
        if(yingdaoflag.equals("")){
            yingdao3.setVisibility(View.VISIBLE);
            editor=pref1.edit();
            editor.putString("yingdao2","HasAppear");
            editor.commit();
        }
        String tiexinwenhouyu="";
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour<=5 & hour>3){ tiexinwenhouyu="真早啊~"; }
        else if(hour<=8 & hour>5){ tiexinwenhouyu="早上好~"; }
        else if(hour<=11 & hour>8){ tiexinwenhouyu="上午好~"; }
        else if(hour<=13 & hour>11){ tiexinwenhouyu="中午好~"; }
        else if(hour<=18 & hour>13){ tiexinwenhouyu="下午好~"; }
        else if(hour<=23 & hour>18){ tiexinwenhouyu="晚上好~"; }
        else if(hour>23||hour<=3){ tiexinwenhouyu="夜深啦~"; }
        textView.setText(account+","+tiexinwenhouyu);
        buttonbg=findViewById(R.id.imageButton3);textbg=findViewById(R.id.textbg);
        describe=findViewById(R.id.describe);introduce=findViewById(R.id.introduce);
        introduce.setClickable(false);describe.setClickable(false);
        setontouch();Start();
        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 0f);
        ObjectAnimator.ofPropertyValuesHolder(buttonbg, alpha1).setDuration(100).start();
        ObjectAnimator.ofPropertyValuesHolder(describe, alpha1).setDuration(100).start();
        ObjectAnimator.ofPropertyValuesHolder(textbg, alpha1).setDuration(100).start();
        ObjectAnimator.ofPropertyValuesHolder(introduce, alpha1).setDuration(100).start();
    }
    private void setontouch(){
        ImageButton login=findViewById(R.id.Home);
        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    v.setBackgroundResource(R.drawable.homepress);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //改为抬起时的图片
                    v.setBackgroundResource(R.drawable.home);
                }
                return false;
            }
        });
        describe.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //更改为按下时的背景图片
                    PropertyValuesHolder rotation2 = PropertyValuesHolder.ofFloat("rotation", 0.0f,360f);
                    PropertyValuesHolder translationX3 = PropertyValuesHolder.ofFloat("translationX",0f,20f);
                    ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(buttonbg,  rotation2);
                    //实例化AnimatorSet
                    AnimatorSet animatorSet = new AnimatorSet();
                    //使用play方法把两个动画拼接起来
                    animatorSet.play(objectAnimator3);
                    //时间
                    animatorSet.setDuration(900);
                    //开始执行
                    animatorSet.start();
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //改为抬起时的图片
                    PropertyValuesHolder rotation2 = PropertyValuesHolder.ofFloat("rotation", 360f,0.0f);
                    PropertyValuesHolder translationX3 = PropertyValuesHolder.ofFloat("translationX",0f,20f);
                    ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(buttonbg,  rotation2);
                    //实例化AnimatorSet
                    AnimatorSet animatorSet = new AnimatorSet();
                    //使用play方法把两个动画拼接起来
                    animatorSet.play(objectAnimator3);
                    //时间
                    animatorSet.setDuration(900);
                    //开始执行
                    animatorSet.start();
                }
                return false;
            }
        });
        introduce.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //更改为按下时的背景图片
                    PropertyValuesHolder rotation2 = PropertyValuesHolder.ofFloat("rotation", 0.0f,360f);
                    PropertyValuesHolder translationX3 = PropertyValuesHolder.ofFloat("translationX",0f,20f);
                    ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(buttonbg,  rotation2);
                    //实例化AnimatorSet
                    AnimatorSet animatorSet = new AnimatorSet();
                    //使用play方法把两个动画拼接起来
                    animatorSet.play(objectAnimator3);
                    //时间
                    animatorSet.setDuration(900);
                    //开始执行
                    animatorSet.start();
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //改为抬起时的图片
                    PropertyValuesHolder rotation2 = PropertyValuesHolder.ofFloat("rotation", 360f,0.0f);
                    PropertyValuesHolder translationX3 = PropertyValuesHolder.ofFloat("translationX",0f,20f);
                    ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(buttonbg,  rotation2);
                    //实例化AnimatorSet
                    AnimatorSet animatorSet = new AnimatorSet();
                    //使用play方法把两个动画拼接起来
                    animatorSet.play(objectAnimator3);
                    //时间
                    animatorSet.setDuration(900);
                    //开始执行
                    animatorSet.start();
                }
                return false;
            }
        });
    }
    public int aaa=0;
    public static String bbb="simple";
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();/*if(y1 - y2 > 50) {Toast.makeText(Choose_WorkRecord.this, "向上滑", Toast.LENGTH_SHORT).show();} else if(y2 - y1 > 50) {Toast.makeText(Choose_WorkRecord.this, "向下滑", Toast.LENGTH_SHORT).show();}*/
            if(x1 - x2 > 50) {//Toast.makeText(Choose_WorkRecord.this, "向左滑", Toast.LENGTH_SHORT).show();
                PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha",1f);
                PropertyValuesHolder translationX1 = PropertyValuesHolder.ofFloat("translationX",20f,0f);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(textbg,translationX1, alpha1);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(introduce,translationX1, alpha1);
                AnimatorSet animatorSet = new AnimatorSet();
                //使用play方法把两个动画拼接起来
                animatorSet.play(objectAnimator2).with(objectAnimator1);
                //时间
                animatorSet.setDuration(200);
                //开始执行
                animatorSet.start();
                if(aaa==1){
                    introduce.setBackgroundResource(R.drawable.gosimple);
                    bbb="simple";
                    aaa--;
                }else if(aaa==0){
                    introduce.setBackgroundResource(R.drawable.gocomplete);
                    bbb="complete";
                    aaa++;
                }
            } else if(x2 - x1 > 50) {//Toast.makeText(Choose_WorkRecord.this, "向右滑", Toast.LENGTH_SHORT).show();
                if(yingdao3.getVisibility()==View.VISIBLE){
                    yingdao3.setVisibility(View.GONE);
                }
                PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha",1f);
                PropertyValuesHolder translationX1 = PropertyValuesHolder.ofFloat("translationX",-20f,0f);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(textbg,translationX1, alpha1);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(introduce,translationX1, alpha1);
                AnimatorSet animatorSet = new AnimatorSet();
                //使用play方法把两个动画拼接起来
                animatorSet.play(objectAnimator2).with(objectAnimator1);
                //时间
                animatorSet.setDuration(200);
                //开始执行
                animatorSet.start();
                if(aaa==0){
                    introduce.setBackgroundResource(R.drawable.gocomplete);
                    bbb="complete";
                    aaa++;
                }else if(aaa==1){
                    introduce.setBackgroundResource(R.drawable.gosimple);
                    bbb="simple";
                    aaa--;
                }
            }
        }
        return super.onTouchEvent(event);
    }
    private void Start(){
        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.1f, 0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.1f, 0f);
        //可以直接执行,不过不能拼接动画，这是组合动画
        //ObjectAnimator.ofPropertyValuesHolder(zong, alpha1, scaleX1, scaleY).setDuration(900).start();
        PropertyValuesHolder alpha2 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder scaleX2 = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder scaleY2 = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        PropertyValuesHolder rotation2 = PropertyValuesHolder.ofFloat("rotation", 0.0f, 360f);
        PropertyValuesHolder translationX2 = PropertyValuesHolder.ofFloat("translationX",0f);
        PropertyValuesHolder translationY2 = PropertyValuesHolder.ofFloat("translationY",-240f);
        PropertyValuesHolder translationX3 = PropertyValuesHolder.ofFloat("translationX",-10f,0f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(buttonbg, alpha2, scaleX2, scaleY2, rotation2,translationX2,translationY2);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(describe, alpha2, scaleX2, scaleY2, rotation2,translationX2,translationY2);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofPropertyValuesHolder(textbg,translationX3, alpha2);
        ObjectAnimator objectAnimator5 = ObjectAnimator.ofPropertyValuesHolder(introduce,translationX3, alpha2);
        //PropertyValuesHolder rotation1 = PropertyValuesHolder.ofFloat("rotation", 0.0f, 360f);
        //实例化AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        //使用play方法把两个动画拼接起来
        animatorSet.play(objectAnimator1).with(objectAnimator3).with(objectAnimator4).with(objectAnimator5);
        //时间
        animatorSet.setDuration(800);
        //开始执行
        animatorSet.start();
        introduce.setClickable(true);describe.setClickable(true);
    }

    public void BtnClick(View view){
        //返回
        if(view==findViewById(R.id.Home)){
            startActivity(new Intent(Choose_WorkRecord.this,Choose_Main.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            Choose_WorkRecord.this.finish();
        }
        //选择前往完整版或者贴心版
        else if(view==findViewById(R.id.describe)||view==findViewById(R.id.introduce)){
            introduce.setClickable(false);describe.setClickable(false);
            if(bbb.equals("simple"))
                //前往贴心版
                startActivity(new Intent(Choose_WorkRecord.this,SQL_SimpleRecord.class));
            else
                //前往完整版
                startActivity(new Intent(Choose_WorkRecord.this,SQL_CompleteRecord1.class));
            introduce.setClickable(true);describe.setClickable(true);
        }

    }
    private long firstTime=0;  //记录第几次点击返回
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()-firstTime>2000){
            Toast.makeText(Choose_WorkRecord.this,"再次点击返回退出",Toast.LENGTH_SHORT).show();
            firstTime=System.currentTimeMillis();
        }else{
            Choose_WorkRecord.this.finish();
            AppConnect.getInstance(this).close();
            System.exit(0);
        }
    }
}
