package com.smf.xxy.androidsql;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.smf.xxy.androidsql.CompleteRecord1.IsOut;

public class CompleteRecord3 extends Activity {
    public static String OutKm,ArriveKm,Actual,RRemark;
    TextView WOutKm,WArriveKm,WActual,WRRemark;
    EditText WOut;
    EditText WArrive;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示
        setContentView(R.layout.activity_complete_record3);
        record=findViewById(R.id.lalala);
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {//横屏
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示
            record.setBackgroundResource(R.drawable.hyheng);
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            record.setBackgroundResource(R.drawable.hyshu);
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();
        WOut=findViewById(R.id.WOut);
        WArrive=findViewById(R.id.WArrive);
        WOutKm=findViewById(R.id.WOutKm);
        WArriveKm=findViewById(R.id.WArriveKm);
        WActual=findViewById(R.id.WActual);
        WRRemark=findViewById(R.id.WRRemark);
        WActual.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try{
                    Double AKM=convertToDouble(WArriveKm.getText().toString(),0);
                    Double OKM=convertToDouble(WOutKm.getText().toString(),0);
                    Double Actual1=AKM-OKM;
                    WActual.setText(Actual1.toString());
                }catch (Exception e){}
            }
        });
        Button button=findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!IsOut){
                    chuanzhi();
                    startActivity(new Intent(CompleteRecord3.this,CompleteRecord2.class));
                    CompleteRecord3.this.finish();
                }
                else {
                    if(WArriveKm.getText().toString().trim().equals("")||WOutKm.getText().toString().trim().equals("")||WActual.getText().toString().trim().equals(""))
                        Toast.makeText(CompleteRecord3.this,"你已填写到达时间,必须填写本页面全部项目！",Toast.LENGTH_SHORT).show();
                    else{
                        chuanzhi();
                        protect();
                        startActivity(new Intent(CompleteRecord3.this,CompleteRecord2.class));
                        CompleteRecord3.this.finish();
                    }
                }

            }
        });
        Button last=findViewById(R.id.last);
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuanzhi();
                startActivity(new Intent(CompleteRecord3.this,CompleteRecord1.class));
                CompleteRecord3.this.finish();
            }
        });
        fuzhi();
    }
    private void protect()
    {
        if(!(WOut.getText().toString().trim().equals(""))) {
            if (WOutKm.getText().toString().trim().equals("")) {
                Toast.makeText(CompleteRecord3.this, "请补齐出发公里数！", Toast.LENGTH_SHORT).show();
                return;
            } else if (WArrive.getText().toString().trim().equals("")) {
                Toast.makeText(CompleteRecord3.this, "请补齐到达地点！", Toast.LENGTH_SHORT).show();return;
            } else if (WArriveKm.getText().toString().trim().equals("")) {
                Toast.makeText(CompleteRecord3.this, "请补齐到达公里数！", Toast.LENGTH_SHORT).show();return;
            }
        }
    }
    private void fuzhi(){
        Out=pref.getString("Out","");
        Arrive=pref.getString("Arrive","");
        WOutKm.setText(OutKm);
        WArriveKm.setText(OutKm);
        WActual.setText(OutKm);
        WRRemark.setText(OutKm);
        WOut.setText(Out);
        WArrive.setText(Arrive);
    }
    private static String Out,Arrive;
    private void chuanzhi(){
        OutKm=WOutKm.getText().toString().trim();
        ArriveKm=WArriveKm.getText().toString().trim();
        Actual=WActual.getText().toString().trim();
        RRemark=WRRemark.getText().toString().trim();
        Out=WOut.getText().toString().trim();//出发地点
        Arrive=WArrive.getText().toString().trim();//到达地点
        editor.putString("Out",Out);
        editor.putString("Arrive",Arrive);
        editor.commit();
    }
    //把String转化为double
    public static double convertToDouble(String number, double defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(number);
        } catch (Exception e) {
            return defaultValue;
        }

    }
    ConstraintLayout record;
    //监听屏幕横竖
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            //竖屏
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            record.setBackgroundResource(R.drawable.hyshu);
        }
        else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
//横屏
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示
            record.setBackgroundResource(R.drawable.hyheng);
        }
    }
}
