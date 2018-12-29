package com.smf.xxy.androidsql;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TExpenseIn2 extends AppCompatActivity {
Spinner TIsDone;String a,WorkRecordNo,Name; Button save;SQLiteDatabase db;
EditText TOther1Name,TOther2Name,TOther3Name,TOther1,TOther2,TOther3,TTotal,TTicketNo,TEYear,TEMonth,TRemark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_texpense_in2);
        save=findViewById(R.id.save);
        TOther1Name=findViewById(R.id.TOther1Name);
        TOther2Name=findViewById(R.id.TOther2Name);
        TOther3Name=findViewById(R.id.TOther3Name);
        TOther1=findViewById(R.id.TOther1);
        TOther2=findViewById(R.id.TOther2);
        TOther3=findViewById(R.id.TOther3);
        TIsDone=findViewById(R.id.TIsDone);
        TTotal=findViewById(R.id.TTotal);
        TTicketNo=findViewById(R.id.TTicketNo);
        TEYear=findViewById(R.id.TEYear);
        TEMonth=findViewById(R.id.TEMonth);
        TRemark=findViewById(R.id.TRemark);  //控件匹配
        TIsDone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.spingarr);
                a=languages[pos];
                if(a.equals("是"))
                    a="Yes";
                else if(a.equals("否"))
                    a="No";
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        Name=pref.getString("account","");
        WorkRecordNo=pref.getString("RecordNo","");
        //WorkRecordNo="2018091401";
        findc();
    }
    private void Insert(){
        db=SQLiteDatabase.openOrCreateDatabase("/data/data/com.smf.xxy.androidsql/HY.db",null);
        try{
            //String sql="delete from texpense1";db.execSQL(sql);
            String sql="create table IF NOT EXISTS texpense1(" +"name char(10),recordNo char(10),WDate date,City char(15)," +"Destination char(15),WType char(20),Partner char(30)," +"PlaneTicket float,TrainTicket float,BoatTicket float,BusTicket float,TaxiTicket float,HotelExpense float,Allowance float," + "Other1Name char(20),Other1 float,Other2Name char(20),Other2 float,Other3Name char(20),Other3 float,Total float,TicketNo INTEGER," +"IsDone char(3),Remark char(500),EYear INTEGER,EMonth INTEGER," + "primary key (name,recordNo)" +")";
            db.execSQL(sql);
            sql="update texpense1 set Other1Name='"+TOther1Name.getText().toString()+"',Other2Name='"+TOther2Name.getText().toString()+
                    "',Other3Name='"+TOther3Name.getText().toString()+"',Other1='"+TOther1.getText().toString()+"',Other2='"+TOther2.getText().toString()
                    +"',Other3='"+TOther3.getText().toString()+"',Total='"+TTotal.getText().toString()+"',TicketNo='"+TTicketNo.getText().toString()
                    +"',EYear='"+TEYear.getText().toString()+"',EMonth='"+TEMonth.getText().toString()+"',IsDone='"+a+"',Remark='"+TRemark.getText().toString()
                    +"'";
            db.execSQL(sql);
            //Toast.makeText(TExpenseIn2.this,"成功修改数据。",Toast.LENGTH_SHORT).show();
        }
        catch (Exception a){
            Toast.makeText(TExpenseIn2.this,"未成功修改数据。",Toast.LENGTH_SHORT).show();
        }
    }
    public void getData(View v){
        if(result[0]==null) {
            if(result1[1]==null)
                Toast.makeText(TExpenseIn2.this, "您没有相应的工作记录！", Toast.LENGTH_SHORT).show();
             else if (!TOther1.getText().toString().equals("")& TOther1Name.getText().toString().equals(""))
                Toast.makeText(TExpenseIn2.this, "用途1不能为空！", Toast.LENGTH_SHORT).show();
             else if (!TOther2.getText().toString().equals("")& TOther2Name.getText().toString().equals(""))
                Toast.makeText(TExpenseIn2.this, "用途2不能为空！", Toast.LENGTH_SHORT).show();
             else if (!TOther3.getText().toString().equals("")& TOther3Name.getText().toString().equals(""))
                Toast.makeText(TExpenseIn2.this, "用途3不能为空！", Toast.LENGTH_SHORT).show();
            else if (TTotal.getText().toString().equals(""))
                Toast.makeText(TExpenseIn2.this, "小计不能为空！", Toast.LENGTH_SHORT).show();
            else if (TTicketNo.getText().toString().equals(""))
                Toast.makeText(TExpenseIn2.this, "单据张数不能为空！", Toast.LENGTH_SHORT).show();
            else if (TEYear.getText().toString().equals("") || TEMonth.getText().toString().equals(""))
                Toast.makeText(TExpenseIn2.this, "请补齐单据日期！", Toast.LENGTH_SHORT).show();
            else if (TOther1.getText().toString().equals("")) {
                TOther1Name.setText(""); Insert();
                _getData();
            }else if (TOther2.getText().toString().equals("")) {
                TOther2Name.setText("");Insert();
                _getData();
            }else if (TOther3.getText().toString().equals("")) {
                TOther3Name.setText("");Insert();
                _getData();
            }else{
                _getData();Insert();}
        }
        else
            save.setVisibility(View.GONE);
    }
    String sql;
    private void _getData()
    {
        db=SQLiteDatabase.openOrCreateDatabase("/data/data/com.smf.xxy.androidsql/HY.db",null);
        //查询获得游标
        Cursor cursor = db.query ("texpense1",null,null,null,null,null,null);
        try{
            if(cursor.moveToFirst()) {
                for(int i=0;i<cursor.getCount();i++){
                    //cursor.move(i);
                    //从hy.db把存的东西取出来
                    String EName=cursor.getString(0);
                    String ERecordNo=cursor.getString(1);
                    String WDate=cursor.getString(2);
                    String City=cursor.getString(3);
                    String Destination=cursor.getString(4);
                    String WType=cursor.getString(5);
                    String Partner=cursor.getString(6);
                    float PlaneTicket=cursor.getFloat(7);
                    float TrainTicket=cursor.getFloat(8);
                    float BoatTicket=cursor.getFloat(9);
                    float BusTicket=cursor.getFloat(10);
                    float TaxiTicket=cursor.getFloat(11);
                    float HotelExpense=cursor.getFloat(12);
                    float Allowance=cursor.getFloat(13);
                    String Other1Name=cursor.getString(14);
                    float Other1=cursor.getFloat(15);
                    String Other2Name=cursor.getString(16);
                    float Other2=cursor.getFloat(17);
                    String Other3Name=cursor.getString(18);
                    float Other3=cursor.getFloat(19);
                    float Total=cursor.getFloat(20);
                    int TicketNo=cursor.getInt(21);
                    String IsDone=cursor.getString(22);
                    String Remark=cursor.getString(23);
                    int EYear=cursor.getInt(24);
                    int EMonth=cursor.getInt(25);
                    sql="insert into TravelExpense values ('" + EName + "' , '" + ERecordNo + "' , '" + WDate + "' , '" + City +"' , '" + Destination +
                            "' , '" + WType + "' , '" + Partner + "' , " + PlaneTicket + " , "+ TrainTicket + " , " + BoatTicket +
                            "," + BusTicket +" ," + TaxiTicket +" ," + HotelExpense +" , " + Allowance +" , '" + Other1Name + "' , " + Other1 +" , '" + Other2Name +
                            "' , " + Other2 +" , '" + Other3Name +"' , " + Other3 +" , " + Total +" , " + TicketNo +", '" +IsDone +"', '" + Remark +
                            "', " + EYear +", " +EMonth +")";
                    findb();
                }
            }
            else
                Toast.makeText(TExpenseIn2.this,"未查询到数据",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    Runnable bb,cc,dd;String result[]={};String result1[]={};
    private void findb()
    {
        bb = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    try {
                        boolean a=DBUtil.Record(sql);
                        if(a){msg.what=1001;}
                        else
                            msg.what=1002;
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
                    String shuzu[]={"Name","RecordNo","WDate","City","Destination","WType","Partner","PlaneTicket","TrainTicket","BoatTicket","BusTicket","TaxiTicket","HotelExpense","Allowance","Other1Name","Other1","Other2Name","Other2","Other3Name","Other3","Total","TicketNo", "IsDone", "Remark", "EYear", "EMonth"};
                    String sql="SELECT RTRIM(Name)Name, RTRIM(RecordNo)RecordNo, RTRIM(WDate)WDate, RTRIM(City)City, RTRIM(Destination)Destination, RTRIM(WType)WType, RTRIM(Partner)Partner, PlaneTicket, TrainTicket, BoatTicket, BusTicket, TaxiTicket, HotelExpense, Allowance, RTRIM(Other1Name)Other1Name, Other1, RTRIM(Other2Name)Other2Name, Other2, RTRIM(Other3Name)Other3Name, Other3, Total, TicketNo, RTRIM(IsDone)IsDone, RTRIM(Remark)Remark, EYear, EMonth FROM TravelExpense where Name = '"+Name+"' and RecordNo = '"+WorkRecordNo+"'";
                    try {
                        result=DBUtil.FindLot(sql,shuzu);
                        if(result[0]==null){msg.what=1004;}
                        else
                            msg.what=1005;
                        mHandler.sendMessage(msg);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        msg.what=1003;mHandler.sendMessage(msg);
                        return;
                    }
                }catch (Exception e){
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
    private void findd()
    {
        dd = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String shuzu[]={"ArriveTime","City","Partner"};
                    String sql="select * from WorkRecord where Name = '"+Name+"'and RecordNo = '"+WorkRecordNo+"'";
//                    String sql="select RTRIM(ArriveTime)ArriveTime, RTRIM(City)City, RTRIM(Partner)Partner from WorkRecord where Name = '"+Name+"'and RecordNo = '"+WorkRecordNo+"'";
                    try {
                        result1=DBUtil.FindLot(sql,shuzu);
                        if(result1[1]==null){}
                        else{
                            msg.what=1006;
                            mHandler.sendMessage(msg);}
                    }catch(Exception e){
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
        new Thread(dd).start();
    }
    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1001:
                    //Toast.makeText(TExpenseIn2.this,"保存成功！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TExpenseIn2.this,success.class));
                    TExpenseIn2.this.finish();
                    mHandler.removeCallbacks(bb);
                    break;
                case 1002:
                    Toast.makeText(TExpenseIn2.this,"保存失败！",Toast.LENGTH_SHORT).show();
                    mHandler.removeCallbacks(bb);
                    break;
                case 1003:
                    Toast.makeText(TExpenseIn2.this,"网络连接失败！",Toast.LENGTH_SHORT).show();
                    break;
                case 1004:
                    findd();
                    mHandler.removeCallbacks(cc);
                    break;
                case 1005:
                    TOther1Name.setText(result[14]);
                    TOther1.setText(result[15]);
                    TOther2Name.setText(result[16]);
                    TOther2.setText(result[17]);
                    TOther3Name.setText(result[18]);
                    TOther3.setText(result[19]);
                    TTotal.setText(result[20]);
                    TTicketNo.setText(result[21]);
                    if(result[22].equals("是"))
                        TIsDone.setSelection(0);
                    else
                        TIsDone.setSelection(1);
                    TRemark.setText(result[23]);
                    TEYear.setText(result[24]);
                    TEMonth.setText(result[25]);
                    save.setVisibility(View.GONE);
                    findd();
                    mHandler.removeCallbacks(cc);
                    break;
                case 1006:
                    TEYear.setText(WorkRecordNo.substring(0,4));
                    TEMonth.setText(WorkRecordNo.substring(4,6));
                    mHandler.removeCallbacks(dd);
                    break;
            }
        };
    };
}
