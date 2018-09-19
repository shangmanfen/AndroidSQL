package com.smf.xxy.androidsql;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class TExpenseIn1 extends Activity {
    EditText TRecordNo,TName,TCity,TDestination,TWType,TPartner;String Name,WorkRecordNo;Button TWDate;
    EditText TPlaneTicket,TBoatTicket,TTrainTicket,TBusTicket,THotelExpense,TAllowance,TTaxiTicket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_texpense_in);
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        Name=pref.getString("account","");
        WorkRecordNo=pref.getString("WWorkRecordNo","");
        //WorkRecordNo="2018091401";
        findid();
        findb();
    }
    public void next(View v){
        if (TWDate.getText().toString().equals(""))
            Toast.makeText(TExpenseIn1.this,"日期不能为空！",Toast.LENGTH_SHORT).show();
        else if (TCity.getText().toString().equals(""))
            Toast.makeText(TExpenseIn1.this,"城市不能为空！",Toast.LENGTH_SHORT).show();
        else if(TDestination.getText().toString().equals(""))
            Toast.makeText(TExpenseIn1.this,"目的地不能为空！",Toast.LENGTH_SHORT).show();
        else if(TWType.getText().toString().equals(""))
            Toast.makeText(TExpenseIn1.this,"事由不能为空！",Toast.LENGTH_SHORT).show();
        else{
            Insert();
            startActivity(new Intent(TExpenseIn1.this, TExpenseIn2.class));
            TExpenseIn1.this.finish();
        }
    }
    Runnable bb,cc;String result[]={};
    private void findb()
    {
        bb = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String shuzu[]={"Name","RecordNo","WDate","City","Destination","WType","Partner","PlaneTicket","TrainTicket","BoatTicket","BusTicket","TaxiTicket","HotelExpense","Allowance","Other1Name","Other1","Other2Name","Other2","Other3Name","Other3","Total","TicketNo", "IsDone", "Remark", "EYear", "EMonth"};
                    String sql="SELECT RTRIM(Name)Name, RTRIM(RecordNo)RecordNo, RTRIM(WDate)WDate, RTRIM(City)City, RTRIM(Destination)Destination, RTRIM(WType)WType, RTRIM(Partner)Partner, PlaneTicket, TrainTicket, BoatTicket, BusTicket, TaxiTicket, HotelExpense, Allowance, RTRIM(Other1Name)Other1Name, Other1, RTRIM(Other2Name)Other2Name, Other2, RTRIM(Other3Name)Other3Name, Other3, Total, TicketNo, RTRIM(IsDone)IsDone, RTRIM(Remark)Remark, EYear, EMonth FROM TravelExpense where Name = '"+Name+"' and RecordNo = '"+WorkRecordNo+"'";
                    try {
                        result=DBUtil.FindLot(sql,shuzu);
                        if(result[0]==null){msg.what=1002;}
                        else
                            msg.what=1001;
                        mHandler.sendMessage(msg);
                    }
                    catch(Exception e){
                        e.printStackTrace();
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
            }
        };
        new Thread(bb).start();
    }
    private void findc()
    {
        cc = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String shuzu[]={"ArriveTime","City","Partner"};
                    String sql="select RTRIM(ArriveTime)ArriveTime, RTRIM(City)City, RTRIM(Partner)Partner from WorkRecord where Name = '"+Name+"'and RecordNo = '"+WorkRecordNo+"'";
                    try {
                        result=DBUtil.FindLot(sql,shuzu);
                        if(result[1]==null){}
                        else{
                            msg.what=1005;
                        mHandler.sendMessage(msg);}
                    }
                    catch(Exception e){
                        e.printStackTrace();
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
            }
        };
        new Thread(cc).start();
    }
    int year,month,day,hour,minute;
    private  void chooseTime(){
        //获取日历的一个对象
        Calendar calendar1= Calendar.getInstance();
        //获取年月日时分秒信息
        year=calendar1.get(Calendar.YEAR);
        month=calendar1.get(Calendar.MONTH);
        day=calendar1.get(Calendar.DAY_OF_MONTH);
        hour=calendar1.get(Calendar.HOUR_OF_DAY);
        minute=calendar1.get(Calendar.MINUTE);
    }
    public  void  TWDate(View v){
        chooseTime();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth) {
                int Month1=month1+1;
                if(year1<year)
                    TWDate.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                else if(year1==year){
                    if(month1<month)
                        TWDate.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                    else if(month1==month){
                        if(dayOfMonth<=day)
                            TWDate.setText(year1+"-"+Month1+"-"+dayOfMonth+" ");
                        else
                            Toast.makeText(TExpenseIn1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(TExpenseIn1.this,"日期不得大于今日！",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(TExpenseIn1.this,"年份不得大于今日！",Toast.LENGTH_SHORT).show();
            }
        }, year, month, day);
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TWDate.setText("选择日期");
            }
        });
        dialog.show();
    }
    public void showMultiDialog(View v) {
        if(TDestination.getText().toString().equals("市内")){}
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(TExpenseIn1.this);
            builder.setTitle("类别：");
            final String[] hobbies = {"维修","保养", "巡访", "装机","应用支持","会议","培训"};
            final StringBuffer sb = new StringBuffer(100);
            builder.setSingleChoiceItems(hobbies, 0, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int index) {
                    sb. delete(0,4);
                    sb.append(hobbies[index]);
                }
            });
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    String a=sb.toString();
                    if(a.equals("")){
                        TWType.setText("维修");
                        }
                    else{
                        TWType.setText(sb.toString());
                        //Type=sb.toString();
                    }
                }
            });
            builder.show();
        }
    }
    private void Insert(){
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase("/data/data/com.smf.xxy.androidsql/HY.db",null);
        try{
            String sql="create table IF NOT EXISTS texpense1(" +
                    "name char(10),recordNo char(10),WDate date,City char(15)," +
                    "Destination char(15),WType char(20),Partner char(30)," +
                    "PlaneTicket float,TrainTicket float,BoatTicket float,BusTicket float,TaxiTicket float,HotelExpense float,Allowance float," +
                    "Other1Name char(20),Other1 float,Other2Name char(20),Other2 float,Other3Name char(20),Other3 float,Total float,TicketNo INTEGER," +
                    "IsDone char(3),Remark char(500),EYear INTEGER,EMonth INTEGER," +
                    "primary key (name,recordNo)" +
                    ")";
            db.execSQL(sql);
            sql="delete from texpense1";db.execSQL(sql);
            sql="insert into texpense1 values ('" +Name + "' , '" + WorkRecordNo + "' , '" + TWDate.getText().toString() + "' , '" +
                TCity.getText().toString() + "' , '" + TDestination.getText().toString() + "' , '" + TWType.getText().toString() + "' , '" +
                TPartner.getText().toString() + "' , '" + TPlaneTicket.getText().toString() + "' , '" + TTrainTicket.getText().toString() + "' ,' " +
                TBoatTicket.getText().toString() + "' , '" + TBusTicket.getText().toString() + "' ,'" + TTaxiTicket.getText().toString() +"' , '" +
                THotelExpense.getText().toString() + "' , '"+ TAllowance.getText().toString() +"' , '0' , 0 , '0' , 0 , '0' ,0 , 0 , 0, '0', '0', 0,0"+
                ")";
            db.execSQL(sql);
            //Toast.makeText(TExpenseIn1.this,"成功插入数据。",Toast.LENGTH_SHORT).show();
        }
        catch (Exception a){
            Toast.makeText(TExpenseIn1.this,"未成功插入数据。",Toast.LENGTH_SHORT).show();
        }
    }
    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1001:
                    TName.setText(result[0]);
                    TRecordNo.setText(result[1]);
                    TWDate.setText(result[2]);
                    if( result[2]=="01/01/1900")
                        TWDate.setText("");
                    TCity.setText(result[3]);
                    TDestination.setText(result[4]);
                    TWType.setText(result[5]);
                    TPartner.setText(result[6]);
                    TPlaneTicket.setText(result[7]);
                    TTrainTicket.setText(result[8]);
                    TBoatTicket.setText(result[9]);
                    TBusTicket.setText(result[10]);
                    TTaxiTicket.setText(result[11]);
                    THotelExpense.setText(result[12]);
                    TAllowance.setText(result[13]);
                    mHandler.removeCallbacks(bb);
                    break;
                case 1002:
                    TName.setText(Name);
                    TRecordNo.setText(WorkRecordNo);
                    findc();
                    break;
                case 1003:
                    Toast.makeText(TExpenseIn1.this,"网络连接失败！",Toast.LENGTH_SHORT).show();
                    break;
                case 1004:

                    break;
                case 1005:
                    String a=WorkRecordNo.substring(0,4)+"/"+WorkRecordNo.substring(4,6)+"/"+WorkRecordNo.substring(6,8);
                    TWDate.setText(a);
                    TCity.setText(result[1]);
                    TPartner.setText(result[2]);
                    TDestination.setText(TCity.getText().toString());
                    break;
            }
        };
    };
    private void findid(){
        TName=findViewById(R.id.TOther2Name);TRecordNo=findViewById(R.id.TOther1Name);TCity=findViewById(R.id.TCity);TTaxiTicket=findViewById(R.id.TTotal);
        TDestination=findViewById(R.id.TDestination);TWType=findViewById(R.id.TWType);TPartner=findViewById(R.id.TPartner);
        TPlaneTicket=findViewById(R.id.TPlaneTicket);TBoatTicket=findViewById(R.id.TOther1);TTrainTicket=findViewById(R.id.TTrainTicket);
        TBusTicket=findViewById(R.id.TOther2);THotelExpense=findViewById(R.id.TOther3Name);TAllowance=findViewById(R.id.TOther3);
        TWDate=findViewById(R.id.TWDate);
    }
}
