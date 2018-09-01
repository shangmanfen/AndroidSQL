package com.smf.xxy.androidsql;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CompleteRecord2 extends Activity {
    TextView WJobContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        setContentView(R.layout.activity_complete_record2);
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
//横屏
            Button last=findViewById(R.id.last);
            last.setVisibility(View.INVISIBLE);
            Button save=findViewById(R.id.Save);
            save.setVisibility(View.INVISIBLE);
            Button last2=findViewById(R.id.last2);
            last2.setVisibility(View.VISIBLE);
            Button save2=findViewById(R.id.save2);
            save2.setVisibility(View.VISIBLE);
            TextView t=findViewById(R.id.textView3);
            t.setVisibility(View.GONE);
            EditText et=findViewById(R.id.RecordText);
            et.setVisibility(View.GONE);
            TextView tt=findViewById(R.id.textView7);
            tt.setVisibility(View.VISIBLE);
            EditText ett=findViewById(R.id.recordText1);
            ett.setVisibility(View.VISIBLE);
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            Button last2=findViewById(R.id.last2);
            last2.setVisibility(View.INVISIBLE);
            Button save2=findViewById(R.id.save2);
            save2.setVisibility(View.INVISIBLE);
            Button last=findViewById(R.id.last);
            last.setVisibility(View.VISIBLE);
            Button save=findViewById(R.id.Save);
            save.setVisibility(View.VISIBLE);
            TextView t=findViewById(R.id.textView3);
            t.setVisibility(View.VISIBLE);
            EditText et=findViewById(R.id.RecordText);
            et.setVisibility(View.VISIBLE);
            TextView tt=findViewById(R.id.textView7);
            tt.setVisibility(View.GONE);
            EditText ett=findViewById(R.id.recordText1);
            ett.setVisibility(View.GONE);
        }
        WJobContent=findViewById(R.id.WJobContent);
        //String a=CompleteRecord1.WCity;
    }
    public void selectIobContent(View v){
        showMultiDialog();
    }
    public void last(View v){
        startActivity(new Intent(CompleteRecord2.this,CompleteRecord1.class));
        CompleteRecord2.this.finish();
    }
    private void showMultiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CompleteRecord2.this);
        builder.setTitle("工作类型：");
        final String[] hobbies = {"会议", "培训", "交通", "其它","休假"};
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
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                WJobContent.setText(sb.toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
            }
        });
        builder.show();
    }
    //监听屏幕横竖
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            //竖屏
            Button last2=findViewById(R.id.last2);
            last2.setVisibility(View.INVISIBLE);
            Button save2=findViewById(R.id.save2);
            save2.setVisibility(View.INVISIBLE);
            Button last=findViewById(R.id.last);
            last.setVisibility(View.VISIBLE);
            Button save=findViewById(R.id.Save);
            save.setVisibility(View.VISIBLE);
            TextView t=findViewById(R.id.textView3);
            t.setVisibility(View.VISIBLE);
            EditText et=findViewById(R.id.RecordText);
            et.setVisibility(View.VISIBLE);
            TextView tt=findViewById(R.id.textView7);
            tt.setVisibility(View.GONE);
            EditText ett=findViewById(R.id.recordText1);
            ett.setVisibility(View.GONE);
            et.setText(ett.getText().toString());
        }
        else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
//横屏
            TextView tt=findViewById(R.id.textView7);
            tt.setVisibility(View.VISIBLE);
            EditText ett=findViewById(R.id.recordText1);
            ett.setVisibility(View.VISIBLE);
            Button last=findViewById(R.id.last);
            last.setVisibility(View.INVISIBLE);
            Button save=findViewById(R.id.Save);
            save.setVisibility(View.INVISIBLE);
            Button last2=findViewById(R.id.last2);
            last2.setVisibility(View.VISIBLE);
            Button save2=findViewById(R.id.save2);
            save2.setVisibility(View.VISIBLE);
            TextView t=findViewById(R.id.textView3);
            t.setVisibility(View.GONE);
            EditText et=findViewById(R.id.RecordText);
            et.setVisibility(View.GONE);
            ett.setText(et.getText().toString());
        }
    }
    public void save(View v){
        Init();
}
    private void Init(){
        CompleteRecord1.TrafficTime="时:分";
        CompleteRecord1.WCity="";
        CompleteRecord1.WContector="";
        CompleteRecord1.WOutKm="";
        CompleteRecord1.WOut="";
        CompleteRecord1.WArrive="";
        CompleteRecord1.WArriveKm="";
        CompleteRecord1.WPartner="";
        CompleteRecord1.WWorkTimeHour="";
        CompleteRecord1.WWorkTimeMinute="";
        CompleteRecord1.WExtraTimeHour="";
        CompleteRecord1.WExtraTimeMinute="";
        CompleteRecord1.WLeaveDay="选择日期";
        CompleteRecord1.WLeaveTime="时:分";
        CompleteRecord1.WWorkRecordNo="";
        CompleteRecord1.WOutDay="选择日期";
        CompleteRecord1.WOutTime="时:分";
        CompleteRecord1.WArriveDay="选择日期";
        CompleteRecord1.WArriveTime="时:分";
    }
}
