package com.smf.xxy.androidsql;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ExpenseRecord extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_record);
        FormLoad();
    }

    public void selectIobContent(View v){
        showSingleDialog();
    }
    String No;SharedPreferences pref; String account,UniqueCode,b,c,TypeDate,Type;
    TextView ENo,EEYear,EName,EEMonth,EMoney,EDetails,EType;String EYear,EMonth,Money,Details;
    public void FormLoad(){
        ENo=findViewById(R.id.ENo);EEYear=findViewById(R.id.EEYear);
        EName=findViewById(R.id.EName);EEMonth=findViewById(R.id.EEMonth);
        TextView ETypeDate=findViewById(R.id.ETypeDate);EMoney=findViewById(R.id.EMoney);
        EType=findViewById(R.id.EType);EDetails=findViewById(R.id.EDetails);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        account=pref.getString("account","");
        chooseTime();
        TypeDate=year+"-"+month+"-"+day;
        ETypeDate.setText("填写日期："+TypeDate);
        EName.setText(account);
        findcode();
    }
    public void protect1(View v){
        if(EType.getText().toString().trim().equals(""))
            Toast.makeText(ExpenseRecord.this,"类别不能为空",Toast.LENGTH_SHORT).show();
        else if(EMoney.getText().toString().trim().equals(""))
            Toast.makeText(ExpenseRecord.this,"金额不能为空",Toast.LENGTH_SHORT).show();
        else if(EEYear.getText().toString().trim().equals("")||EEMonth.getText().toString().trim().equals("")){
            Toast.makeText(ExpenseRecord.this,"请补齐单据日期",Toast.LENGTH_SHORT).show();
        }
        else if(month==1){
            if((EEYear.getText().toString().trim().equals(year+"") & EEMonth.getText().toString().trim().equals("1"))||(EEYear.getText().toString().trim().equals((year-1)+"")& EEMonth.getText().toString().trim().equals("12"))){
                EYear=EEYear.getText().toString().trim();EMonth=EEMonth.getText().toString().trim();
                Insert();
            }
            else {Toast.makeText(ExpenseRecord.this,"该月份单据已超时间限制，不能报销",Toast.LENGTH_SHORT).show(); }
        }
        else if(month!=1){
            String a=EEYear.getText().toString().trim();String b=EEMonth.getText().toString().trim();
            String c=(month-1)+"";
            //String c=EEYear.getText().toString().trim();String d=EEMonth.getText().toString().trim();
            if((a.equals(year+"")& b.equals(month+""))||(a.equals(year+"")& b.equals(c))){
                EYear=EEYear.getText().toString().trim();EMonth=EEMonth.getText().toString().trim();
                Insert();
            }
            else {Toast.makeText(ExpenseRecord.this,"该月份单据已超时间限制，不能报销",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void Insert(){
        Money=EMoney.getText().toString().trim();
        Details=EDetails.getText().toString().trim();
        save();
    }
    private void showSingleDialog() {
        final TextView EType=findViewById(R.id.EType);
        EType.setText("");
        AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseRecord.this);
        builder.setTitle("类别：");
        final String[] hobbies = {"餐费","油费", "耗材", "办公用品","会务培训","快递托运","通讯费","差旅费","其它"};
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
                    EType.setText("餐费");
                Type="餐费";}
                else{
                    EType.setText(sb.toString());
                    Type=sb.toString();
                }
            }
        });
        builder.show();
    }
    int year,month,day,hour,minute;
    public  void chooseTime(){
        //获取日历的一个对象
        Calendar calendar1= Calendar.getInstance();
        //获取年月日时分秒信息
        year=calendar1.get(Calendar.YEAR);
        month=calendar1.get(Calendar.MONTH);
        month+=1;
        day=calendar1.get(Calendar.DAY_OF_MONTH);
        hour=calendar1.get(Calendar.HOUR_OF_DAY);
        minute=calendar1.get(Calendar.MINUTE);
    }
    private void save()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String sql="insert into ExpenseRecord values ('"+ No+"' , '" + account+"' , '" +TypeDate +"' ," +
                            EYear +" ," + EMonth+" , '" + Type +"' ," + Money +" , '" +Details +
                            "' , '' , '01/01/1900' , '' , '01/01/1900' , '01/01/1900' , '未审核' , '')";
                    try {
                        DBUtil.Record(sql);
                        msg.what=1005;
                        Bundle data = new Bundle();

                        msg.setData(data);
                        mHandler.sendMessage(msg);
                    }
                    catch(Exception e){
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
        new Thread(run).start();
    }
    private void findb()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String sql="SELECT * FROM ExpenseRecord where No like '"+No+"%'";
                    try {
                        b=DBUtil.QuerySQL(sql,"No");
msg.what=1002;
                        Bundle data = new Bundle();

                        msg.setData(data);
                        mHandler.sendMessage(msg);
                    }
                    catch(Exception e){
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
        new Thread(run).start();
    }
    private void findcode()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String sql2="SELECT RTRIM(Company)Company, RTRIM(UniqueCode)UniqueCode FROM EngineerInfo1 WHERE Name = '"+account+"'";
                    try {
                        UniqueCode=DBUtil.QuerySQL(sql2,"UniqueCode");
                        msg.what=1001;
                        Bundle data = new Bundle();

                        msg.setData(data);
                        mHandler.sendMessage(msg);
                    }
                    catch(Exception e){
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
        new Thread(run).start();
    }
    private void findc()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String sql1="SELECT max(No)No FROM ExpenseRecord where No like '"+No+"%'";
                    try {
                        c=DBUtil.QuerySQL(sql1,"No");
                        c=c.substring(7,9);
                        Bundle data = new Bundle();
                        msg.what=1004;
                        msg.setData(data);
                        mHandler.sendMessage(msg);
                    }
                    catch(Exception e){
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
        new Thread(run).start();
    }
    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1001:
                    int a=year-2000;
                    if(month<10)
                        No="H"+a+"0"+month+UniqueCode;
                    else
                        No = "H" +a+month+UniqueCode;
                    findb();
                    break;

                case 1002:
                    if(b.trim().equals(""))
                        No=No+"01";
                    else
                        findc();
                    break;
                case 1003:
                    Toast.makeText(ExpenseRecord.this,"网络连接失败！",Toast.LENGTH_SHORT).show();
                    break;
                case 1004:
                    if(convertToInt(c,0)>8)
                        c=(convertToInt(c,0)+1)+"";
                    else
                        c="0"+(convertToInt(c,0)+1);
                    No=No+c;
                    ENo.setText(No);
                    break;
                case 1005:
                    startActivity(new Intent(ExpenseRecord.this,success.class));
                    ExpenseRecord.this.finish();
                    //Toast.makeText(ExpenseRecord.this,"保存成功！",Toast.LENGTH_SHORT).show();
                    break;
            }
        };
    };
    //把String转化为int
    public static int convertToInt(String number, int defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            return defaultValue;
        }

    }
}
