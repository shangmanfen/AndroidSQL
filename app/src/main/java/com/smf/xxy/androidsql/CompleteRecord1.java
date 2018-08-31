package com.smf.xxy.androidsql;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class CompleteRecord1 extends Activity {
    private static Button WOutDay;
    private static Button WOutTime;
    private static Button WArriveDay;
    private static Button WArriveTime;
    private static Button WLeaveDay;
    private static Button WLeaveTime;
    EditText WName;
    Button TrafficTime;
    private Calendar calendar1;// 用来装日期的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_record1);
        WOutDay=findViewById(R.id.WOutDay);
        WOutTime=findViewById(R.id.WOutTime);
        WArriveDay=findViewById(R.id.WArriveDay);
        WArriveTime=findViewById(R.id.WArriveTime);
        WLeaveDay=findViewById(R.id.WLeaveDay);
        WLeaveTime=findViewById(R.id.WLeaveTime);
        WName=findViewById(R.id.WName);
        WName.setText(MainWindow.account);
        //Arrival=findViewById(R.id.Arrival);
        TrafficTime =(Button) findViewById(R.id.TrafficTime);
    }
    public  void next(View v){
        startActivity(new Intent(CompleteRecord1.this,CompleteRecord2.class));
        CompleteRecord1.this.finish();
    }
    int year,month,day,hour,minute;
    public  void chooseTime(){
    //获取日历的一个对象
    calendar1=Calendar.getInstance();
    //获取年月日时分秒信息
    year=calendar1.get(Calendar.YEAR);
    month=calendar1.get(Calendar.MONTH);
    day=calendar1.get(Calendar.DAY_OF_MONTH);
    hour=calendar1.get(Calendar.HOUR_OF_DAY);
    minute=calendar1.get(Calendar.MINUTE);
}
    // TODO: 选择交通时间
    public  void getTrafficTime(View v){
    chooseTime();
    TimePickerDialog dialog1 = new TimePickerDialog(CompleteRecord1.this, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TrafficTime.setText(hourOfDay+":"+minute);
        }
    }, hour, minute, true);
    dialog1.show();
}
    // TODO: 选择出发时间
    public  void  selectOutTime(View v){
        chooseTime();
        TimePickerDialog dialog1 = new TimePickerDialog(CompleteRecord1.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                WOutTime.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.show();
    }
    // TODO: 选择出发日期
    public  void  selectOutDate(View v){
        chooseTime();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int Month1=month+1;
                WOutDay.setText(year+"/"+Month1+"/"+dayOfMonth+" ");
            }
        }, year, month, day);
        dialog.show();
    }
    // TODO: 选择到达时间
    public  void  selectArriveTime(View v){
        chooseTime();
        TimePickerDialog dialog1 = new TimePickerDialog(CompleteRecord1.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                WArriveTime.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.show();
    }
    // TODO: 选择到达日期
    public  void  selectArriveDate(View v){
        chooseTime();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int Month1=month+1;
                WArriveDay.setText(year+"/"+Month1+"/"+dayOfMonth+" ");
            }
        }, year, month, day);
        dialog.show();
    }
    // TODO: 选择离开时间
    public  void  WLeaveTime(View v){
        chooseTime();
        TimePickerDialog dialog1 = new TimePickerDialog(CompleteRecord1.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                WLeaveTime.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.show();
    }
    // TODO: 选择离开日期
    public  void  WLeaveDay(View v){
        chooseTime();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int Month1=month+1;
                WLeaveDay.setText(year+"/"+Month1+"/"+dayOfMonth+" ");
            }
        }, year, month, day);
        dialog.show();
    }
}
