package com.smf.xxy.androidsql;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CompleteRecord1 extends Activity {
    Button WOutDay1;
    Button WOutTime1;
    Button WArriveDay1;
    Button WArriveTime1;
    Button WLeaveDay1;
    Button WLeaveTime1;
    EditText WName1;
    EditText WWorkRecordNo1;
    EditText WCity1;
    EditText WContector1;
    EditText WOut1;
    EditText WArrive1;
    EditText WOutKm1;
    EditText WArriveKm1;
    EditText WPartner1;
    EditText WWorkTimeHour1;
    EditText WWorkTimeMinute1;
    EditText WExtraTimeHour1;
    EditText WExtraTimeMinute1;
    Button TrafficTime1;
    private Calendar calendar1;// 用来装日期的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_record1);
        WOutDay1=findViewById(R.id.WOutDay);
        WOutTime1=findViewById(R.id.WOutTime);
        WArriveDay1=findViewById(R.id.WArriveDay);
        WArriveTime1=findViewById(R.id.WArriveTime);
        WLeaveDay1=findViewById(R.id.WLeaveDay);
        WLeaveTime1=findViewById(R.id.WLeaveTime);
        WName1=findViewById(R.id.WName);
        WName1.setText(MainChoose.account);
        WExtraTimeMinute1=findViewById(R.id.WExtraTimeMinute);
        WExtraTimeHour1=findViewById(R.id.WExtraTimeHour);
        WWorkTimeMinute1=findViewById(R.id.WWorkTimeMinute);
        WWorkTimeHour1=findViewById(R.id.WWorkTimeHour);
        WPartner1=findViewById(R.id.WPartner);
        WArriveKm1=findViewById(R.id.WArriveKm);
        WOutKm1=findViewById(R.id.WOutKm);
        WArrive1=findViewById(R.id.WArrive);
        WOut1=findViewById(R.id.WOut);
        WContector1=findViewById(R.id.WContector);
        WCity1=findViewById(R.id.WCity);
        WWorkRecordNo1=findViewById(R.id.WWorkRecordNo);
        TrafficTime1 =findViewById(R.id.TrafficTime);
        fuzhi();
    }
    public  void protect(View v){
        try{
            if(Integer.parseInt(WWorkTimeHour1.getText().toString().trim())>23||Integer.parseInt(WWorkTimeHour1.getText().toString().trim())<0)
                Toast.makeText(CompleteRecord1.this,"工作时间--小时有误！",Toast.LENGTH_SHORT).show();
            else if(Integer.parseInt(WWorkTimeMinute1.getText().toString().trim())>59||Integer.parseInt(WWorkTimeMinute1.getText().toString().trim())<0)
                Toast.makeText(CompleteRecord1.this,"工作时间--分钟有误！",Toast.LENGTH_SHORT).show();
            else if(Integer.parseInt(WExtraTimeHour1.getText().toString().trim())>23||Integer.parseInt(WExtraTimeHour1.getText().toString().trim())<0)
                Toast.makeText(CompleteRecord1.this,"加班时间--小时有误！",Toast.LENGTH_SHORT).show();
            else if(Integer.parseInt(WExtraTimeMinute1.getText().toString().trim())>59||Integer.parseInt(WExtraTimeMinute1.getText().toString().trim())<0)
                Toast.makeText(CompleteRecord1.this,"加班时间--分钟有误！",Toast.LENGTH_SHORT).show();
        }catch (Exception e){}
        if(WName1.getText().toString().trim().equals("")){
            Toast.makeText(CompleteRecord1.this,"用户名不得为空！",Toast.LENGTH_SHORT).show();
        }
        else if(WWorkRecordNo1.getText().toString().trim().equals("")){
            Toast.makeText(CompleteRecord1.this,"记录编号不得为空！",Toast.LENGTH_SHORT).show();
        }
        else if(WArriveDay1.getText().toString().trim().equals("选择日期")||WArriveTime1.getText().toString().trim().equals("时:分"))
            Toast.makeText(CompleteRecord1.this,"到达日期为必填项！",Toast.LENGTH_SHORT).show();
        else if(WCity1.getText().toString().trim().equals(""))
            Toast.makeText(CompleteRecord1.this,"城市不能为空！",Toast.LENGTH_SHORT).show();
        else
            if(!(WOut1.getText().toString().trim().equals(""))){
                if(WOutKm1.getText().toString().trim().equals("")){
                    Toast.makeText(CompleteRecord1.this,"请补齐出发公里数！",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(WArrive1.getText().toString().trim().equals("")) {
                    Toast.makeText(CompleteRecord1.this, "请补齐到达地点！", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(WArriveKm1.getText().toString().trim().equals("")){
                Toast.makeText(CompleteRecord1.this,"请补齐到达公里数！",Toast.LENGTH_SHORT).show();
                    return;}
                else if(WOutTime1.getText().toString().trim().equals("时:分")){
                Toast.makeText(CompleteRecord1.this,"请补齐出发时间！",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            next();
    }
    public static String OutTime,ArriveTime,LeaveTime,WorkTime,ExtraTime;
    private  void next(){
            OutTime=WOutDay1.getText().toString()+" " +WOutTime1.getText().toString() + ":0";
            if(WOutDay1.getText().toString().trim().equals("选择日期")||WOutTime1.getText().toString().trim().equals("时:分"))
                OutTime = "1900-1-1 0:0:0";
            ArriveTime = WArriveDay1.getText().toString() + " " + WArriveTime1.getText().toString()+ ":0";
            LeaveTime = WLeaveDay1.getText().toString()+ " " + WLeaveTime1.getText().toString()+ ":0";
            if(WLeaveDay1.getText().toString().trim().equals("选择日期")||WLeaveTime1.getText().toString().trim().equals("时:分"))
                LeaveTime = "1900-1-1 0:0:0";
            try{
                if(stringToDate(OutTime).getTime()>stringToDate(ArriveTime).getTime()){
                    Toast.makeText(CompleteRecord1.this,"出发时间不能晚于到达时间!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(stringToDate(LeaveTime).getTime()<stringToDate(ArriveTime).getTime()){
                    Toast.makeText(CompleteRecord1.this,"离开时间不能早于到达时间!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }catch (Exception e){}
            if (WWorkTimeHour1.getText().toString().trim().equals("")|| WWorkTimeMinute1.getText().toString().trim().equals(""))
                WorkTime = "1900-1-1 0:0:0";
            else
                WorkTime = WWorkTimeHour+ ":" + WWorkTimeMinute+ ":0";
            if (WExtraTimeHour1.getText().toString().trim().equals("")|| WExtraTimeMinute1.getText().toString().trim().equals(""))
                ExtraTime = "1900-1-1 0:0:0";
            else
                ExtraTime = WExtraTimeHour1.getText().toString()+ ":" + WExtraTimeMinute1.getText().toString()+ ":0";
            if(TrafficTime1.getText().toString().trim().equals("时:分"))
                TrafficTime = "1900-1-1 0:0:0";
            else
                TrafficTime = TrafficTime+ ":0";

            chuanzhi();
            startActivity(new Intent(CompleteRecord1.this,CompleteRecord2.class));
            CompleteRecord1.this.finish();
    }
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        date = formatter.parse(strTime);
        return date;
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
            TrafficTime1.setText(hourOfDay+":"+minute);
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
                WOutTime1.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.show();
    }
    // TODO: 选择出发日期
    public  void  selectOutDate(View v){
        chooseTime();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth) {
                int Month1=month1+1;
                if(year1<year)
                    WOutDay1.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                else if(year1==year){
                    if(month1<month)
                        WOutDay1.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                    else if(month1==month){
                        if(dayOfMonth<=day)
                            WOutDay1.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                        else
                            Toast.makeText(CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(CompleteRecord1.this,"年份不得大于今日！",Toast.LENGTH_SHORT).show();
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
                WArriveTime1.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.show();
    }
    // TODO: 选择到达日期
    public  void  selectArriveDate(View v){
        chooseTime();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth) {
                int Month1=month1+1;
                if(year1<year)
                    WArriveDay1.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                else if(year1==year){
                    if(month1<month)
                        WArriveDay1.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                    else if(month1==month){
                        if(dayOfMonth<=day)
                            WArriveDay1.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                        else
                            Toast.makeText(CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(CompleteRecord1.this,"年份不得大于今日！",Toast.LENGTH_SHORT).show();
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
                WLeaveTime1.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.show();
    }
    // TODO: 选择离开日期
    public  void  WLeaveDay(View v){
        chooseTime();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth) {
                int Month1=month1+1;
                if(year1<year)
                    WLeaveDay1.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                else if(year1==year){
                    if(month1<month)
                        WLeaveDay1.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                    else if(month1==month){
                        if(dayOfMonth<=day)
                            WLeaveDay1.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                        else
                            Toast.makeText(CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(CompleteRecord1.this,"年份不得大于今日！",Toast.LENGTH_SHORT).show();
            }
        }, year, month, day);
        dialog.show();
    }
    public static String WOutDay="选择日期";
    public static String WOutTime="时:分";
    public static String WArriveDay="选择日期";
    public static String WArriveTime="时:分";
    public static String WLeaveDay="选择日期";
    public static String WLeaveTime="时:分";
    public static String TrafficTime="时:分";
    public static String Contector="0";
    public static String City,Out,WArrive,OutKm,WName,WWorkRecordNo;
    public static String WArriveKm,WPartner,WWorkTimeHour,WWorkTimeMinute,WExtraTimeHour,WExtraTimeMinute;
    private void chuanzhi(){
        City=WCity1.getText().toString().trim();
        Contector=WContector1.getText().toString().trim();//差旅补贴
        OutKm=WOutKm1.getText().toString().trim();//出发公里数
        Out=WOut1.getText().toString().trim();//出发地点
        WArrive=WArrive1.getText().toString().trim();//到达地点
        WArriveKm=WArriveKm1.getText().toString().trim();//到达公里数
        WPartner=WPartner1.getText().toString().trim();//同去人员
        WWorkTimeHour=WWorkTimeHour1.getText().toString().trim();//正常交通时间，小时
        WWorkTimeMinute=WWorkTimeMinute1.getText().toString().trim();//正常交通时间，分钟
        WExtraTimeHour=WExtraTimeHour1.getText().toString().trim();//加班工作时间，小时
        WExtraTimeMinute=WExtraTimeMinute1.getText().toString().trim();//加班工作时间，分钟
        WLeaveDay=WLeaveDay1.getText().toString().trim();//离开日期
        WLeaveTime=WLeaveTime1.getText().toString().trim();//离开时间
        WName=WName1.getText().toString().trim();
        WWorkRecordNo=WWorkRecordNo1.getText().toString().trim();//记录编号
        WOutDay=WOutDay1.getText().toString().trim();//出发日期
        WOutTime=WOutTime1.getText().toString().trim();//出发时间
        WArriveDay=WArriveDay1.getText().toString().trim();//到达日期
        WArriveTime=WArriveTime1.getText().toString().trim();//到达时间
    }
    private void fuzhi(){
        TrafficTime1.setText(TrafficTime);
        WCity1.setText(City);
        WContector1.setText(Contector);
        WOutKm1.setText(OutKm);
        WOut1.setText(Out);
        WArrive1.setText(WArrive);
        WArriveKm1.setText(WArriveKm);
        WPartner1.setText(WPartner);
        WWorkTimeHour1.setText(WWorkTimeHour);
        WWorkTimeMinute1.setText(WWorkTimeMinute);
        WExtraTimeHour1.setText(WExtraTimeHour);
        WExtraTimeMinute1.setText(WExtraTimeMinute);
        WLeaveDay1.setText(WLeaveDay);
        WLeaveTime1.setText(WLeaveTime);
        WWorkRecordNo1.setText(WWorkRecordNo);
        WOutDay1.setText(WOutDay);
        WOutTime1.setText(WOutTime);
        WArriveDay1.setText(WArriveDay);
        WArriveTime1.setText(WArriveTime);
    }
/*    @Override
    public void onBackPressed() {
        startActivity(new Intent(CompleteRecord1.this,MainChoose.class));
        CompleteRecord1.this.finish();
    }*/
}
