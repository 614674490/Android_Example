package com.example.register_page;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    HashMap<String,String>hashMap=new HashMap<String, String>();
    private String borndate=new String();               //用于获取设置后出生日期
    private String all_hobby="运动 音乐";
    private Calendar calendar = Calendar.getInstance(); //用于获取当前格林威治时间(北京时间多8个小时)
    EditText username_edit;
    EditText id_edit;
    EditText password_edit ;
    RadioButton male ;
    RadioButton female;
    RadioGroup hex_radio;
    CheckBox sport,music,dance,technology;
    int register_flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView born_message=(TextView)findViewById(R.id.born_message);  //该句要放在前两句之后
        final Button born_button=(Button)findViewById(R.id.born_button);
        final Button register_button=(Button)findViewById(R.id.register_button) ;
        username_edit = (EditText) findViewById(R.id.username_edit);
        id_edit = (EditText)findViewById(R.id.id_edit);
        password_edit = (EditText)findViewById(R.id.password_edit);

        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);
        hex_radio = (RadioGroup)findViewById(R.id.hex_radio);
        hashMap.put("性别","男");
        hashMap.put("爱好","");
        sport=(CheckBox)findViewById(R.id.sport);
        music=(CheckBox)findViewById(R.id.music);
        dance=(CheckBox)findViewById(R.id.dance);
        technology=(CheckBox)findViewById(R.id.technology);
        sport.setChecked(true);
        music.setChecked(true);
        username_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(username_edit.getText().toString().isEmpty()) {
                        return false;
                    }
                hashMap.put("用户名",username_edit.getText().toString());
                return false;
            }
        });
        id_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(id_edit.getText().toString().isEmpty()){
                    return false;
                }
                hashMap.put("账号",id_edit.getText().toString());
                return false;
            }
        });
        password_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(password_edit.getText().toString().isEmpty()){
                    return false;
                }
                hashMap.put("密码",password_edit.getText().toString());
                return false;
            }
        });
        hex_radio.setOnCheckedChangeListener(cBoxListener);
        born_button.setOnClickListener(new View.OnClickListener() {
            @Override
            //创建一个日期选择对话框
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog =  new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datePickerDialog.dismiss();
                    }
                });
                datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePickerDialog.getDatePicker().getYear();
                        int month = datePickerDialog.getDatePicker().getMonth();
                        int day = datePickerDialog.getDatePicker().getDayOfMonth();
                        borndate=year+" 年 "+(month+1)+" 月 "+day+" 日 ";
                        hashMap.put("出生日期",borndate);
                        born_message.setText(borndate);
                    }
                });
                datePickerDialog.show();
            }
        });
        sport.setOnCheckedChangeListener(hobbyListener);
        music.setOnCheckedChangeListener(hobbyListener);
        dance.setOnCheckedChangeListener(hobbyListener);
        technology.setOnCheckedChangeListener(hobbyListener);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hashMap.get("用户名")==null||hashMap.get("账号")==null||hashMap.get("密码")==null||hashMap.get("性别")==null||hashMap.get("出生日期")==null||
                hashMap.get("用户名")==""||hashMap.get("账号")==""||hashMap.get("密码")==""||hashMap.get("性别")==""||hashMap.get("出生日期")==""){
                    Toast.makeText(getApplicationContext(),"注册信息不完整",Toast.LENGTH_LONG).show();
                    return ;
                }else if(!testAllUpperCase(hashMap.get("用户名").toString())){
                    Toast.makeText(getApplicationContext(),"用户名格式不对，应为大写字母",Toast.LENGTH_LONG).show();
                    return ;
                }
                LayoutInflater dialogInflater = LayoutInflater.from(MainActivity.this);  //获取一个布局添加器
                final View register_tip = dialogInflater.inflate(R.layout.born_date_set,null);//将布局文件放在pick_tip中
                final TextView username_echo_view = (TextView)register_tip.findViewById(R.id.username_echo_view);
                username_echo_view.setText(hashMap.get("用户名"));
                final TextView account_view = (TextView) register_tip.findViewById(R.id.account_view);
                account_view.setText(hashMap.get("账号"));
                final TextView password_view = (TextView) register_tip.findViewById(R.id.password_view);
                password_view.setText(hashMap.get("密码"));
                final TextView hex_view = (TextView) register_tip.findViewById(R.id.hex_view);
                hex_view.setText(hashMap.get("性别"));
                final TextView born_view = (TextView) register_tip.findViewById(R.id.born_view);
                born_view.setText(hashMap.get("出生日期"));
                final TextView hobby_view = (TextView) register_tip.findViewById(R.id.hobby_view);
                if(all_hobby!="")
                    hobby_view.setText(all_hobby);
                else
                    hobby_view.setText("无爱好");
                AlertDialog register_display = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("注册信息")
                        .setView(register_tip)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast toast =  Toast.makeText(getApplicationContext(),"注册完成",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER,50,50);//先设置内容 后设置位置 这样才能居中
                                toast.show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                register_display.show();

            }
        });

    }
    //单选框监视器
    private RadioGroup.OnCheckedChangeListener cBoxListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(R.id.male == checkedId){
                hashMap.put("性别",male.getText().toString());
            }else if(R.id.female == checkedId){
                hashMap.put("性别",female.getText().toString());
            }
        }
    };
    //多选框监视器
    private CompoundButton.OnCheckedChangeListener hobbyListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(sport.isChecked()){
                hashMap.put("运动","运动 ");
            }
            else{
                hashMap.put("运动","");
            }
            if(music.isChecked()){
                hashMap.put("音乐","音乐 ");
            }
            else{
                hashMap.put("音乐","");
            }
            if(dance.isChecked()){
                hashMap.put("舞蹈","舞蹈 ");
            }
            else{
                hashMap.put("舞蹈","");
            }
            if(technology.isChecked()){
                hashMap.put("科技","科技 ");
            }
            else{
                hashMap.put("科技","");
            }
           all_hobby=hashMap.get("运动")+hashMap.get("音乐")+hashMap.get("舞蹈")+hashMap.get("科技");
        }
    };

    public static boolean testAllUpperCase(String str){
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(!(c >= 'A' && c <= 'Z')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear :{
                hashMap.clear();
                username_edit.setText("");
                id_edit.setText("");
                password_edit.setText("");
                male.setChecked(false);
                female.setChecked(false);
                sport.setChecked(false);
                music.setChecked(false);
                technology.setChecked(false);
                dance.setChecked(false);
                return true;
            }
            case R.id.exit :{
                MainActivity.this.finish();
                return true;
            }
        }
        return true;
    }
}
