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

public class Choose_Main extends Activity {
    ImageButton work,other;    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0, x2 = 0,y1 = 0,y2 = 0;
    ConstraintLayout yingdao3;ImageView yingdao,yingdao2;
    public void reLogin(View v){
        editor.remove("count").commit();
        startActivity(new Intent(Choose_Main.this,MainActivity.class));
        Choose_Main.this.finish();
    }
    public void Work(View v){
        editor.putInt("count",count).commit();
        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 0.8f,1f);
        PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofFloat("scaleX", 1.1f,1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.1f,1f);
        //可以直接执行,不过不能拼接动画，这是组合动画
        ObjectAnimator.ofPropertyValuesHolder(work, alpha1, scaleX1, scaleY).setDuration(400).start();
        startActivity(new Intent(Choose_Main.this,Choose_WorkRecord.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Choose_Main.this.finish();
    }
    public void MoneyMange(View v){
        editor.putInt("count",count).commit();
        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 0.8f,1f);
        PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofFloat("scaleX", 1.1f,1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.1f,1f);
        //可以直接执行,不过不能拼接动画，这是组合动画
        ObjectAnimator.ofPropertyValuesHolder(expense, alpha1, scaleX1, scaleY).setDuration(400).start();
        startActivity(new Intent(Choose_Main.this,ExpenseRecord.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        //Choose_Main.this.finish();
    }
    public void Search(View v){
        editor.putInt("count",count).commit();
        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 0.8f,1f);
        PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofFloat("scaleX", 1.1f,1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.1f,1f);
        //可以直接执行,不过不能拼接动画，这是组合动画
        ObjectAnimator.ofPropertyValuesHolder(other, alpha1, scaleX1, scaleY).setDuration(400).start();
        Toast.makeText(Choose_Main.this,"正在开发，敬请期待",Toast.LENGTH_SHORT).show();
        /*startActivity(new Intent(Choose_Main.this,Choose_WorkRecord.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Choose_Main.this.finish();*/
    }
    private int count=0;private SharedPreferences.Editor editor;
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
            y2 = event.getY();
            if(y1 - y2 > 50) {// "向上滑", Toast.LENGTH_SHORT).show();
                if(yingdao3.getVisibility()==View.VISIBLE){
                    yingdao3.setVisibility(View.GONE);
                }
                count++;
                count%=3;switchcount1();
            } else if(y2 - y1 > 50) {//"向下滑", Toast.LENGTH_SHORT).show();
                count--;
                count=(count+3)%3;switchcount2();
            }
        }
        return super.onTouchEvent(event);
    }
    private  void  switchcount1(){
        switch (count){
            case 1:
                split1(work,expense);
                break;
            case 2:
                split1(expense,other);
                break;
            case 0:
                split1(other,work);
                break;
        }
    }
    private  void  switchcount2(){
        switch (count){
            case 0:
                split2(expense ,work);
                break;
            case 1:
                split2(other,expense);
                break;
            case 2:
                split2(work,other);
                break;
        }
    }
    private  void  switchInt(){
        switch (count){
            case 1:
                splitInt(work,expense);
                break;
            case 2:
                splitInt(expense,other);
                break;
            case 0:
                splitInt(other,work);
                break;
        }
    }
    private void splitInt(ImageButton a,ImageButton b){
        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha",0f);
        PropertyValuesHolder alpha2 = PropertyValuesHolder.ofFloat("alpha",1f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(a, alpha1);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(b, alpha2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator2).with(objectAnimator3);
        animatorSet.setDuration(100);
        animatorSet.start();
        a.setClickable(false);b.setClickable(true);
        a.setEnabled(false);b.setEnabled(true);
    }
    private void split1(ImageButton a,ImageButton b){
        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha",1f,0f);
        PropertyValuesHolder translationX1 = PropertyValuesHolder.ofFloat("translationX",0f);
        PropertyValuesHolder translationY1 = PropertyValuesHolder.ofFloat("translationY",0f,-350f);
        PropertyValuesHolder alpha2 = PropertyValuesHolder.ofFloat("alpha",0f,1f);
        //PropertyValuesHolder translationX2 = PropertyValuesHolder.ofFloat("translationX",0f);
        PropertyValuesHolder translationY2 = PropertyValuesHolder.ofFloat("translationY",350f,0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(a,translationX1,translationY1, alpha1);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(b,translationY2, alpha2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator2).with(objectAnimator3);
        animatorSet.setDuration(500);
        animatorSet.start();
        a.setClickable(false);b.setClickable(true);
        a.setEnabled(false);b.setEnabled(true);
    }
    private void split2(ImageButton a,ImageButton b){
        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha",1f,0f);
        PropertyValuesHolder translationX1 = PropertyValuesHolder.ofFloat("translationX",0f);
        PropertyValuesHolder translationY1 = PropertyValuesHolder.ofFloat("translationY",0f,350f);
        PropertyValuesHolder alpha2 = PropertyValuesHolder.ofFloat("alpha",0f,1f);
        PropertyValuesHolder translationY2 = PropertyValuesHolder.ofFloat("translationY",-350f,0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(a,translationX1,translationY1, alpha1);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(b,translationY2, alpha2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator2).with(objectAnimator3);
        animatorSet.setDuration(500);
        animatorSet.start();
        a.setClickable(false);b.setClickable(true);
        a.setEnabled(false);b.setEnabled(true);
    }
    private SharedPreferences pref1;String account,yingdaoflag;ImageButton expense;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if(Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(Color.parseColor("#040814"));
            }
        setContentView(R.layout.activity_main_choose);
            pref1= PreferenceManager.getDefaultSharedPreferences(this);
            editor=pref1.edit();
            TextView textView=findViewById(R.id.textView1);expense=findViewById(R.id.expense1);
            yingdao3=findViewById(R.id.yingdao3);
            account=pref1.getString("account","");
            yingdaoflag=pref1.getString("yingdao1","");
            count=pref1.getInt("count",0);
            if(yingdaoflag.equals("")){
                yingdao3.setVisibility(View.VISIBLE);
                editor.putString("yingdao1","HasAppear");
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
            work=findViewById(R.id.imageButton2);other=findViewById(R.id.search);ImageButton login=findViewById(R.id.Home);
            work.setClickable(true);other.setClickable(false);work.setEnabled(true);other.setEnabled(false);
            expense.setClickable(false);expense.setEnabled(false);
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
            work.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        //更改为按下时的背景图片
                        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 1f,0.8f);
                        PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofFloat("scaleX", 1f,1.1f);
                        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f,1.1f);
                        //可以直接执行,不过不能拼接动画，这是组合动画
                        ObjectAnimator.ofPropertyValuesHolder(work, alpha1, scaleX1, scaleY).setDuration(400).start();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 0.8f,1f);
                        PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofFloat("scaleX", 1.1f,1.0f);
                        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.1f,1f);
                        //可以直接执行,不过不能拼接动画，这是组合动画
                        ObjectAnimator.ofPropertyValuesHolder(work, alpha1, scaleX1, scaleY).setDuration(400).start();
                    }
                    return false;
                }
            });
            other.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        //更改为按下时的背景图片
                        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 1f,0.8f);
                        PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofFloat("scaleX", 1f,1.1f);
                        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f,1.1f);
                        //可以直接执行,不过不能拼接动画，这是组合动画
                        ObjectAnimator.ofPropertyValuesHolder(other, alpha1, scaleX1, scaleY).setDuration(400).start();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 0.8f,1f);
                        PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofFloat("scaleX", 1.1f,1.0f);
                        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.1f,1f);
                        //可以直接执行,不过不能拼接动画，这是组合动画
                        ObjectAnimator.ofPropertyValuesHolder(other, alpha1, scaleX1, scaleY).setDuration(400).start();
                    }
                    return false;
                }
            });
            expense.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        //更改为按下时的背景图片
                        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 1f,0.8f);
                        PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofFloat("scaleX", 1f,1.1f);
                        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f,1.1f);
                        //可以直接执行,不过不能拼接动画，这是组合动画
                        ObjectAnimator.ofPropertyValuesHolder(expense, alpha1, scaleX1, scaleY).setDuration(400).start();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha", 0.8f,1f);
                        PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofFloat("scaleX", 1.1f,1.0f);
                        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.1f,1f);
                        //可以直接执行,不过不能拼接动画，这是组合动画
                        ObjectAnimator.ofPropertyValuesHolder(expense, alpha1, scaleX1, scaleY).setDuration(400).start();
                    }
                    return false;
                }
            });
            PropertyValuesHolder alpha1 = PropertyValuesHolder.ofFloat("alpha",0f);
            ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(other,alpha1);
            ObjectAnimator objectAnimator4 = ObjectAnimator.ofPropertyValuesHolder(expense,alpha1);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(objectAnimator3).with(objectAnimator4);
            animatorSet.setDuration(50);
            animatorSet.start();
            switchInt();
    }
    private long firstTime=0;  //记录第几次点击返回
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()-firstTime>2000){
            Toast.makeText(Choose_Main.this,"再次点击返回退出",Toast.LENGTH_SHORT).show();
            firstTime=System.currentTimeMillis();
        }else{
            editor.remove("count").commit();
            Choose_Main.this.finish();
            AppConnect.getInstance(this).close();
            System.exit(0);
        }
    }
}
