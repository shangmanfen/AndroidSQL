package com.smf.xxy.androidsql;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class SQL_CompleteRecord1 extends Activity {
    Button WOutDay1,WOutTime1,WArriveDay1,WArriveTime1,WLeaveDay1,WLeaveTime1;
    EditText WName1,WWorkRecordNo1,WCity1,WContector1,WPartner1;
    Button WWorkTime1,WExtraTime1,TrafficTime1;
    private Calendar calendar1;// 用来装日期的
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    String account1;public static boolean IsOut=false;
    private static String OutTime,ArriveTime,LeaveTime,WWorkRecordNo;
    private static  String WorkTime="时:分",TrafficTime="时:分",ExtraTime="时:分";
    int year,month,day,hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        setContentView(R.layout.activity_complete_record1);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();
        pref= getDefaultSharedPreferences(this);
        account1=pref.getString("account","");
        WOutDay1=findViewById(R.id.WOutDay);
        WOutTime1=findViewById(R.id.WOutTime);
        WArriveDay1=findViewById(R.id.WArriveDay);
        WArriveTime1=findViewById(R.id.WArriveTime);
        WLeaveDay1=findViewById(R.id.WLeaveDay);
        WLeaveTime1=findViewById(R.id.WLeaveTime);
        WName1=findViewById(R.id.WName);
        WName1.setText(Choose_WorkRecord.account);
        WExtraTime1=findViewById(R.id.WExtraTime);
        WWorkTime1=findViewById(R.id.WWorkTime);
        WPartner1=findViewById(R.id.WPartner);
        WContector1=findViewById(R.id.WContector);
        WCity1=findViewById(R.id.WCity);
        WWorkRecordNo1=findViewById(R.id.WWorkRecordNo);
        TrafficTime1 =findViewById(R.id.trafficTime);
        chooseTime();
        if(month<9){
            if(day<10)
                WWorkRecordNo = ""+year+"0"+(month+1)+"0"+day+"01";
            else
                WWorkRecordNo = ""+year+"0"+(month+1)+day+"01";
        }
        else{
            if(day<10)
                WWorkRecordNo = ""+year+(month+1)+"0"+day+"01";
            else
                WWorkRecordNo = ""+year+(month+1)+day+"01";
        }
        WWorkRecordNo1.setText(WWorkRecordNo);
        //fuzhi();
    }
    @Override
    public void onBackPressed() {
        SQL_CompleteRecord1.this.finish();
        Choose_WorkRecord.introduce.setClickable(true);
        Choose_WorkRecord.describe.setClickable(true);
    }

    public  void protect(View v){
        if(WOutTime1.getText().toString().trim().equals("时:分"))
            IsOut=false;
        else IsOut=true;
        if(WName1.getText().toString().trim().equals(""))
            Toast.makeText(SQL_CompleteRecord1.this,"用户名不得为空！",Toast.LENGTH_SHORT).show();
        else if(WWorkRecordNo1.getText().toString().trim().equals(""))
            Toast.makeText(SQL_CompleteRecord1.this,"记录编号不得为空！",Toast.LENGTH_SHORT).show();
        else if(WArriveDay1.getText().toString().trim().equals("选择日期")||WArriveTime1.getText().toString().trim().equals("时:分"))
            Toast.makeText(SQL_CompleteRecord1.this,"到达日期为必填项！",Toast.LENGTH_SHORT).show();
        else if(WCity1.getText().toString().trim().equals(""))
            Toast.makeText(SQL_CompleteRecord1.this,"城市不能为空！",Toast.LENGTH_SHORT).show();
        else {
            if(WOutDay1.getText().toString().trim().equals("选择日期")||WOutTime1.getText().toString().trim().equals("时:分"))
                OutTime = "1900-1-1 0:0:0";
            else
                OutTime=WOutDay1.getText().toString().trim()+" " +WOutTime1.getText().toString() + ":0";
            ArriveTime = WArriveDay1.getText().toString().trim() + " " + WArriveTime1.getText().toString()+ ":0";
            if(WLeaveDay1.getText().toString().trim().equals("选择日期")||WLeaveTime1.getText().toString().trim().equals("时:分"))
                LeaveTime = "1900-1-1 0:0:0";
            else
                LeaveTime = WLeaveDay1.getText().toString().trim()+ " " + WLeaveTime1.getText().toString()+ ":0";
            try{
                if(stringToDate(OutTime).getTime()!=stringToDate("1900-1-1 0:0:0").getTime() & stringToDate(LeaveTime).getTime()!=stringToDate("1900-1-1 0:0:0").getTime()){
                    if(stringToDate(OutTime).getTime()>stringToDate(ArriveTime).getTime()){
                        Toast.makeText(SQL_CompleteRecord1.this,"出发时间不能晚于到达时间!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(stringToDate(LeaveTime).getTime()<stringToDate(ArriveTime).getTime()){
                        Toast.makeText(SQL_CompleteRecord1.this,"离开时间不能早于到达时间!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }catch (Exception e){}

            next();
            if(Insert(year+"-"+month+"-"+day,hour+":"+minute+":"+"0")){
                startActivity(new Intent(SQL_CompleteRecord1.this,SQL_CompleteRecord2.class));
                SQL_CompleteRecord1.this.finish();
            }else
                Toast.makeText(this,"未成功插入数据",Toast.LENGTH_SHORT).show();
        }
    }
    private  void next(){
        if (WWorkTime1.getText().toString().trim().equals("时:分")||WWorkTime1.getText().toString().trim().equals("1900-1-1 0:0:0"))
            WorkTime = "1900-1-1 0:0:0";
        else
            WorkTime =WWorkTime1.getText().toString()+ ":0";
        if (WExtraTime1.getText().toString().trim().equals("时:分")||WExtraTime1.getText().toString().trim().equals("1900-1-1 0:0:0"))
            ExtraTime = "1900-1-1 0:0:0";
        else
            ExtraTime = WExtraTime1.getText().toString()+ ":0";
        if(TrafficTime1.getText().toString().trim().equals("时:分")||TrafficTime1.getText().toString().trim().equals("1900-1-1 0:0:0"))
            TrafficTime = "1900-1-1 0:0:0";
        else
            TrafficTime = TrafficTime1.getText().toString().trim()+ ":0";

        Choose_WorkRecord.introduce.setClickable(true);
        Choose_WorkRecord.describe.setClickable(true);
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
        TimePickerDialog dialog1 = new TimePickerDialog(SQL_CompleteRecord1.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                TrafficTime1.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.setButton(DialogInterface.BUTTON_NEUTRAL, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TrafficTime1.setText("时:分");
            }
        });
        dialog1.show();
    }
    // TODO: 选择正常工作时间
    public  void getWWorkTime(View v){
        chooseTime();
        TimePickerDialog dialog1 = new TimePickerDialog(SQL_CompleteRecord1.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                WWorkTime1.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.setButton(DialogInterface.BUTTON_NEUTRAL, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WWorkTime1.setText("时:分");
            }
        });
        dialog1.show();
    }
    // TODO: 选择加班工作时间
    public  void getWExtraTime(View v){
        chooseTime();
        TimePickerDialog dialog1 = new TimePickerDialog(SQL_CompleteRecord1.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                WExtraTime1.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.setButton(DialogInterface.BUTTON_NEUTRAL, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WExtraTime1.setText("时:分");
            }
        });
        dialog1.show();
    }
    // TODO: 选择出发时间
    public  void  selectOutTime(View v){
        chooseTime();
        TimePickerDialog dialog1 = new TimePickerDialog(SQL_CompleteRecord1.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                WOutTime1.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.setButton(DialogInterface.BUTTON_NEUTRAL, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WOutTime1.setText("时:分");
            }
        });
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
                            Toast.makeText(SQL_CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(SQL_CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SQL_CompleteRecord1.this,"年份不得大于今日！",Toast.LENGTH_SHORT).show();
            }
        }, year, month, day);
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WOutDay1.setText("年/月/日");
            }
        });
        dialog.show();
    }
    // TODO: 选择到达时间
    public  void  selectArriveTime(View v){
        chooseTime();
        TimePickerDialog dialog1 = new TimePickerDialog(SQL_CompleteRecord1.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                WArriveTime1.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.setButton(DialogInterface.BUTTON_NEUTRAL, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WArriveTime1.setText("时:分");
            }
        });
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
                            Toast.makeText(SQL_CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(SQL_CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SQL_CompleteRecord1.this,"年份不得大于今日！",Toast.LENGTH_SHORT).show();
            }
        }, year, month, day);
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WArriveDay1.setText("年/月/日");
            }
        });
        dialog.show();
    }
    // TODO: 选择离开时间
    public  void  WLeaveTime(View v){
        chooseTime();
        TimePickerDialog dialog1 = new TimePickerDialog(SQL_CompleteRecord1.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                WLeaveTime1.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);
        dialog1.setButton(DialogInterface.BUTTON_NEUTRAL, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WLeaveTime1.setText("时:分");
            }
        });
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
                            Toast.makeText(SQL_CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(SQL_CompleteRecord1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SQL_CompleteRecord1.this,"年份不得大于今日！",Toast.LENGTH_SHORT).show();
            }
        }, year, month, day);
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WLeaveDay1.setText("年/月/日");
            }
        });
        dialog.show();
    }


    /** 老 传值，赋值
    private void chuanzhi(){
        City=WCity1.getText().toString().trim();
        Contector=WContector1.getText().toString().trim();//差旅补贴
        WPartner=WPartner1.getText().toString().trim();//同去人员
        WLeaveDay=WLeaveDay1.getText().toString().trim();//离开日期
        WLeaveTime=WLeaveTime1.getText().toString().trim();//离开时间
        WName=WName1.getText().toString().trim();
        WWorkRecordNo=WWorkRecordNo1.getText().toString().trim();//记录编号
        WOutDay=WOutDay1.getText().toString().trim();//出发日期
        WOutTime=WOutTime1.getText().toString().trim();//出发时间
        WArriveDay=WArriveDay1.getText().toString().trim();//到达日期
        WArriveTime=WArriveTime1.getText().toString().trim();//到达时间
        editor.putString("WorkTime",WorkTime);
        editor.putString("ExtraTime",ExtraTime);
        editor.putString("TrafficTime",TrafficTime);
        editor.putString("OutTime",OutTime);
        editor.putString("ArriveTime",ArriveTime);
        editor.putString("LeaveTime",LeaveTime);
        editor.putString("City",City);
        editor.putString("Contector",Contector);
        editor.putString("WPartner",WPartner);
        editor.putString("WLeaveDay",WLeaveDay);
        editor.putString("WLeaveTime",WLeaveTime);
        editor.putString("WWorkRecordNo",WWorkRecordNo);
        editor.putString("WOutDay",WOutDay);
        editor.putString("WOutTime",WOutTime);
        editor.putString("WArriveDay",WArriveDay);
        editor.putString("WArriveTime",WArriveTime);
        editor.commit();
    }
    private void fuzhi(){
        if(pref.getString("WorkTime","时:分").equals("时:分")){WorkTime=pref.getString("WorkTime","时:分");}
        else
            WorkTime=pref.getString("WorkTime","时:分").substring(0,pref.getString("WorkTime","时:分").length()-2);
        if(pref.getString("ExtraTime","时:分").equals("时:分")){ExtraTime=pref.getString("ExtraTime","时:分");}
        else
            ExtraTime=pref.getString("ExtraTime","时:分").substring(0,pref.getString("ExtraTime","时:分:0").length()-2);
        if(pref.getString("TrafficTime","时:分").equals("时:分")){TrafficTime=pref.getString("TrafficTime","时:分");}
        else
            TrafficTime=pref.getString("TrafficTime","时:分").substring(0,pref.getString("TrafficTime","时:分:0").length()-2);
    }*/

    private boolean Insert(final String date, final String time){
        try{
            SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase("/data/data/com.smf.xxy.androidsql/HY.db",null);
            //String sql1="Drop table[WorkRecord]";db.execSQL(sql1);
            String sql="create table IF NOT EXISTS WorkRecord(" +
                    "Name char(10),RecordNo char(10)," +
                    "OutTime date,ArriveTime date,LeaveTime date,WorkTime date,ExtraTime date,TrafficTime date," +
                    "City char(20),CShortName char(20),Contector char(20),JobContent char(1000),Uncompleted char(500)," +
                    "Remark char(500),ReportNo char(20),Solveway char(2000),TypeDate date," +
                    "LeaderRemark char(500),IsLeaderScore char(10),LeaderScoreDate date," +
                    "Partner char(100),Abandoner char(10),AbandonDate date," +
                    "Out char(30),OutKm INTEGER,Arrive char(30),ArriveKm INTEGER,Actual INTEGER,RRemark char(300),Allowance INTEGER," +
                    "primary key (Name,RecordNo)" +
                    ")";
            db.execSQL(sql);
            sql="delete from WorkRecord";db.execSQL(sql);
            sql = "insert into WorkRecord " +
                    "([Name],[RecordNo],[OutTime],[ArriveTime],[LeaveTime],[WorkTime],[ExtraTime],[TrafficTime],[City],[CShortName],[Contector],[JobContent],[Uncompleted]" +
                    ",[Remark],[ReportNo],[TypeDate],[LeaderRemark],[IsLeaderScore],[LeaderScoreDate],[Partner],[Abandoner],[AbandonDate],[Out],[OutKm],[Arrive]" +
                    ",[ArriveKm],[Actual],[RRemark],[Allowance],[Solveway]) values " +
                    "('"+account1+"','"+ WWorkRecordNo1.getText().toString()+"',"+
                    "'"+OutTime+"','"+ArriveTime+
                    "','"+LeaveTime+"','"+WorkTime+"','"+ExtraTime+"','"+TrafficTime+"',"+
                    "'"+WCity1.getText().toString()+"','','','工作类型','','','无'," +
                    "'"+date+" "+time+"','"+ "','否','1900-01-01','"+WPartner1.getText().toString()+"','','1900-01-01','',0,'',0,0,'',0,'')";
            db.execSQL(sql);
            return true;
            //Toast.makeText(TExpenseIn1.this,"成功插入数据。",Toast.LENGTH_SHORT).show();
        }
        catch (Exception a){
            return false;
        }
    }
}
