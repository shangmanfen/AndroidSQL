package com.smf.xxy.androidsql;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SQL_CompleteRecord3 extends Activity {
    TextView WJobContent;
    TextView WUncompleted;
    TextView RecordText,recordText1;
    TextView WRemark;
    ConstraintLayout record;
    private static String WOutDay="选择日期";
    private static String WOutTime="时:分";
    private static String WArriveDay="选择日期";
    private static String WArriveTime="时:分";
    private static String WLeaveDay="选择日期";
    private static String WLeaveTime="时:分";
    private static String TrafficTime="时:分";
    private static String Contector="0";
    private static String City,WName,WWorkRecordNo,WPartner,RecordContent;
    private static String OutTime,ArriveTime,LeaveTime,Out,Arrive;
    private static  String WorkTime="时:分";
    private static  String ExtraTime="时:分";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        setContentView(R.layout.activity_complete_record3);
        record=findViewById(R.id.Record2);
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
//横屏
            TextView t=findViewById(R.id.textView3);
            t.setVisibility(View.GONE);
            EditText et=findViewById(R.id.RecordText);
            et.setVisibility(View.GONE);
            TextView tt=findViewById(R.id.textView7);
            tt.setVisibility(View.VISIBLE);
            EditText ett=findViewById(R.id.recordText1);
            ett.setVisibility(View.VISIBLE);
            record.setBackgroundResource(R.drawable.hyheng);
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            TextView t=findViewById(R.id.textView3);
            t.setVisibility(View.VISIBLE);
            EditText et=findViewById(R.id.RecordText);
            et.setVisibility(View.VISIBLE);
            TextView tt=findViewById(R.id.textView7);
            tt.setVisibility(View.GONE);
            EditText ett=findViewById(R.id.recordText1);
            ett.setVisibility(View.GONE);
            record.setBackgroundResource(R.drawable.hyshu);
        }
        WJobContent=findViewById(R.id.WJobContent);
        WUncompleted=findViewById(R.id.WUncompleted);
        RecordText=findViewById(R.id.RecordText);
        recordText1=findViewById(R.id.recordText1);
        WRemark=findViewById(R.id.WRemark);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();
        Name=pref.getString("account","");
        WorkTime=pref.getString("WorkTime","");
        ExtraTime=pref.getString("ExtraTime","");
        TrafficTime=pref.getString("TrafficTime","");
        City=pref.getString("City","");
        Contector=pref.getString("Contector","");
        WPartner=pref.getString("WPartner","");
        WLeaveDay=pref.getString("WLeaveDay","");
        WLeaveTime=pref.getString("WLeaveTime","");
        WWorkRecordNo=pref.getString("WWorkRecordNo","");
        WOutDay=pref.getString("WOutDay","");
        WOutTime=pref.getString("WOutTime","");
        WArriveDay=pref.getString("WArriveDay","");
        WArriveTime=pref.getString("WArriveTime","");
        Out=pref.getString("Out","");
        Arrive=pref.getString("Arrive","");
        OutTime=pref.getString("OutTime","");
        ArriveTime=pref.getString("ArriveTime","");
        LeaveTime=pref.getString("LeaveTime","");
    }
    public void selectIobContent(View v){
        showMultiDialog();
    }
    public void last(View v){
        startActivity(new Intent(SQL_CompleteRecord3.this,SQL_CompleteRecord2.class));
        SQL_CompleteRecord3.this.finish();
    }
    private void showMultiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SQL_CompleteRecord3.this);
        builder.setTitle("工作类型：");
        final String[] hobbies = {"其它","会议", "培训", "交通","休假"};
        /**
         * 第一个参数指定我们要显示的一组下拉多选框的数据集合
         * 第二个参数代表哪几个选项被选择，如果是null，则表示一个都不选择，如果希望指定哪一个多选选项框被选择，
         * 需要传递一个boolean[]数组进去，其长度要和第一个参数的长度相同，例如 {true, false, false, true};
         * 第三个参数给每一个多选项绑定一个监听器
         */
        final StringBuffer sb = new StringBuffer(100);
        builder.setMultiChoiceItems(hobbies, null, new DialogInterface.OnMultiChoiceClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked)
            {
                if(isChecked)
                {
                    sb.append(hobbies[which]);
                }
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
                WJobContent.setText(sb.toString());
            }
        });
        builder.show();
    }
    //监听屏幕横竖
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            //竖屏
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            TextView t=findViewById(R.id.textView3);
            t.setVisibility(View.VISIBLE);
            EditText et=findViewById(R.id.RecordText);
            et.setVisibility(View.VISIBLE);
            TextView tt=findViewById(R.id.textView7);
            tt.setVisibility(View.GONE);
            EditText ett=findViewById(R.id.recordText1);
            ett.setVisibility(View.GONE);
            et.setText(ett.getText().toString());
            record.setBackgroundResource(R.drawable.hyshu);
        }
        else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
//横屏
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示
            TextView tt=findViewById(R.id.textView7);
            tt.setVisibility(View.VISIBLE);
            EditText ett=findViewById(R.id.recordText1);
            ett.setVisibility(View.VISIBLE);
            Button last=findViewById(R.id.last);
            last.setVisibility(View.INVISIBLE);
            TextView t=findViewById(R.id.textView3);
            t.setVisibility(View.GONE);
            EditText et=findViewById(R.id.RecordText);
            et.setVisibility(View.GONE);
            ett.setText(et.getText().toString());
            record.setBackgroundResource(R.drawable.hyheng);
        }
    }
    public void save(View v){
        if(RecordText.getText().toString().trim().equals(""))
            RecordContent=recordText1.getText().toString();
        else if(recordText1.getText().toString().trim().equals(""))
            RecordContent=RecordText.getText().toString();
        if(RecordContent.equals(""))
            Toast.makeText(SQL_CompleteRecord3.this,"请填写工作记录！",Toast.LENGTH_SHORT).show();
        else if(WJobContent.getText().toString().trim().equals(""))
            Toast.makeText(SQL_CompleteRecord3.this,"请填写工作类型！",Toast.LENGTH_SHORT).show();
        else {
            JudgeRecordIsExist();
            //JudgeInsertRecord();
        }
    }
    private boolean Insert(){
        SQLiteDatabase db;
        db= SQLiteDatabase.openOrCreateDatabase("/data/data/com.smf.xxy.androidsql/HY.db",null);
        try{
            String sql="update WorkRecord set JobContent='"+WJobContent.getText().toString()+"',Solveway='"+RecordContent+
                    "',Uncompleted='"+WUncompleted.getText().toString()+"',Remark='"+WRemark.getText().toString()+"'";
            db.execSQL(sql);
            return true;
            //Toast.makeText(TExpenseIn2.this,"成功修改数据。",Toast.LENGTH_SHORT).show();
        }
        catch (Exception a){
            return false;
        }
    }
    private void Init(){
        editor.putString("WorkTime","时:分");
        editor.putString("ExtraTime","时:分");
        editor.putString("TrafficTime","时:分");
        editor.putString("Arrive","");
        editor.putString("Out","");
        SQL_CompleteRecord1.IsOut=false;
        editor.putString("OutTime","");
        editor.putString("ArriveTime","");
        editor.putString("LeaveTime","");
        editor.putString("City","");
        editor.putString("Contector","0");
        editor.putString("WPartner","");
        editor.putString("WLeaveDay","年/月/日");
        editor.putString("WLeaveTime","时:分");
        //editor.putString("WWorkRecordNo","");
        editor.putString("WOutDay","年/月/日");
        editor.putString("WOutTime","时:分");
        editor.putString("WArriveDay","年/月/日");
        editor.putString("WArriveTime","时:分");
        editor.commit();
    }
    int year,month,day,hour,minute;
    public  void chooseTime(){
        //获取日历的一个对象
        Calendar calendar1= Calendar.getInstance();
        //获取年月日时分秒信息
        year=calendar1.get(Calendar.YEAR);
        month=calendar1.get(Calendar.MONTH);
        day=calendar1.get(Calendar.DAY_OF_MONTH);
        hour=calendar1.get(Calendar.HOUR_OF_DAY);
        minute=calendar1.get(Calendar.MINUTE);
    }
    Boolean NoRecord=true;
    private void  JudgeRecordIsExist()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    Message msg = new Message();
                    String sql="select * from WorkRecord where Name = '" + Name +"' and RecordNo = '" +WWorkRecordNo +"'";
                    try {
                        String ret = DBUtil.QuerySQL(sql,"Name");
                        if(ret.equals("")){msg.what=1001;}
                        else msg.what=1002;
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
                    msg.what=1003;
                    mHandler.sendMessage(msg);
                }
            }
        };
        new Thread(run).start();
    }

    String RecordNo;
    private void _getData()
    {
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase("/data/data/com.smf.xxy.androidsql/HY.db",null);
        //查询获得游标
        Cursor cursor = db.query ("WorkRecord",null,null,null,null,null,null);
        try{
            if(cursor.moveToFirst()) {
                for(int i=0;i<cursor.getCount();i++){
                    //cursor.move(i);
                    //从hy.db把存的东西取出来
                    String Name=cursor.getString(0);
                    RecordNo=cursor.getString(1);
                    String OutTime=cursor.getString(2);
                    String ArriveTime=cursor.getString(3);
                    String LeaveTime=cursor.getString(4);
                    String WorkTime=cursor.getString(5);
                    String ExtraTime=cursor.getString(6);
                    String TrafficTime=cursor.getString(7);
                    String City=cursor.getString(8);
                    String CShortName=cursor.getString(9);
                    String Contector=cursor.getString(10);
                    String JobContent=cursor.getString(11);
                    String Uncompleted=cursor.getString(12);
                    String Remark=cursor.getString(13);
                    String ReportNo=cursor.getString(14);
                    String Solveway=cursor.getString(15);
                    String TypeDate=cursor.getString(16);
                    String LeaderRemark=cursor.getString(17);
                    String IsLeaderScore=cursor.getString(18);
                    String LeaderScoreDate=cursor.getString(19);
                    String Partner=cursor.getString(20);
                    String Abandoner=cursor.getString(21);
                    String AbandonDate=cursor.getString(22);
                    String Out=cursor.getString(23);
                    int OutKm=cursor.getInt(24);
                    String Arrive=cursor.getString(25);
                    int ArriveKm=cursor.getInt(26);
                    String Actual=cursor.getString(27);
                    String RRemark=cursor.getString(28);
                    String Allowance=cursor.getString(29);
                    String sql="insert into WorkRecord values ('"+Name+"','"+RecordNo+"','"+OutTime+"','"+ArriveTime+"','"+LeaveTime+"','"+WorkTime+"','"+ExtraTime+
                            "','"+TrafficTime+"','"+City+"','"+CShortName+"','"+Contector+"','"+JobContent+"','"+Uncompleted+"','"+Remark+"','"+
                            ReportNo+"','"+Solveway+"','"+TypeDate+"','"+LeaderRemark+"','"+IsLeaderScore+"','"+LeaderScoreDate+"','"+Partner+
                            "','"+Abandoner+"','"+AbandonDate+"','"+Out+"','"+OutKm+"','"+Arrive+"','"+ArriveKm+"','"+Actual+"','"+RRemark+"','"+Allowance+ "')";
                    InsertRecord(sql);
                }
            }
            else
                Toast.makeText(this,"未查询到数据",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private void InsertRecord(final String sql)
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                if(NoRecord){
                    try{
                        Message msg = new Message();
                        if(DBUtil.Record(sql))
                            msg.what=1004;
                        else
                            msg.what=1006;
                        mHandler.sendMessage(msg);
                    }
                    catch (Exception e){
                        Message msg = new Message();
                        Bundle data = new Bundle();
                        data.putString("result", e.getMessage());
                        msg.setData(data);
                        mHandler.sendMessage(msg);
                    }
                    finally { }
                }
                else{
                    Message msg = new Message();
                    msg.what=1005;
                    mHandler.sendMessage(msg);}
            }
        };
        new Thread(run).start();
    }

    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1001:
                    if(Insert()) {
                        //Toast.makeText(SQL_CompleteRecord3.this, "修改成功", Toast.LENGTH_SHORT).show();
                        _getData();
                    }else
                        Toast.makeText(SQL_CompleteRecord3.this,"修改数据失败",Toast.LENGTH_SHORT).show();
                    break;
                case 1002:
                    Toast.makeText(SQL_CompleteRecord3.this,"该工作记录编号已存在！",Toast.LENGTH_SHORT).show();
                    break;
                case 1003:
                    Toast.makeText(SQL_CompleteRecord3.this,"网络连接失败！",Toast.LENGTH_SHORT).show();
                    break;
                case 1004:
                    editor.putBoolean("GoToTExpense",true).commit();//差旅费记号
                    editor.putString("RecordNo",RecordNo).commit();//记住编号，为填差旅费做准备
                    Init();
                    startActivity(new Intent(SQL_CompleteRecord3.this,success.class));//跳转到成功界面
                    SQL_CompleteRecord3.this.finish();
                    break;
                case 1005:
                    Init();editor.putBoolean("GoToTExpense",false).commit();
                    Toast.makeText(SQL_CompleteRecord3.this,"该工作记录已经存在",Toast.LENGTH_SHORT).show();
                    break;
                case 1006:
                    Toast.makeText(SQL_CompleteRecord3.this,"新增失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        };
    };
}
