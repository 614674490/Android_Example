package com.a15433.maillist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class RegistActivity extends AppCompatActivity {
    Button btn_regist,btn_back;
    SharedPreferences preferences;
    EditText ed_name,ed_password,ed_secondpassword;
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        this.getWindow().setBackgroundDrawableResource(R.drawable.background);
        //获取SharedPreferences对象
        preferences = getSharedPreferences("Login",0);
        SharedPreferences.Editor editor = preferences.edit();

        ed_name = (EditText)findViewById(R.id.regist_ed_name);
        ed_password = (EditText)findViewById(R.id.regist_ed_password);
        ed_secondpassword = (EditText)findViewById(R.id.regist_ed_secondpassword) ;
        btn_regist = (Button)findViewById(R.id.regist_register);
        btn_back = (Button)findViewById(R.id.regist_back);

        btn_back.getBackground().setAlpha(100);
        btn_regist.getBackground().setAlpha(100);

        btn_regist.setOnClickListener((view) ->{
            if(ed_name.getText().toString().equals("")) {
                Login.setWarnOrTipDialog(RegistActivity.this, "提示", "用户名不能为空！");
            }else if(ed_password.getText().toString().equals("")){
                Login.setWarnOrTipDialog(RegistActivity.this, "提示", "密码不能为空！");
            //用户名已存在
            }else if(!preferences.getString(ed_name.getText().toString(),"-1").equals("-1")){
                Login.setWarnOrTipDialog(RegistActivity.this,"提示","用户名已存在！");
                //两次输入不一致
            }else if(!ed_password.getText().toString().equals(ed_secondpassword.getText().toString())) {
                ed_password.setText("");
                ed_secondpassword.setText("");
                Login.setWarnOrTipDialog(RegistActivity.this, "提示", "两次输入密码不一致！\n请重新输入!");
            }else {
                //通过SharedPreferences.Editor方法添加数据
                editor.putString(ed_name.getText().toString(),ed_password.getText().toString());
                editor.commit();
                //成功显示AlertDialog，点击确定回转到登录界面
                AlertDialog.Builder dialog = new AlertDialog.Builder(RegistActivity.this);
                dialog.setTitle("提示")
                        .setMessage("注册成功！")
                        .setPositiveButton("确定",(dialogInterface,i) ->{
                            intent.setClass(RegistActivity.this, Login.class);
                            startActivity(intent);
                        });
                AlertDialog alertDialog = dialog.create();
                alertDialog.getWindow().setBackgroundDrawable(Login.setDialogBack(16, 16, 16, 16, 170, 255,255,255));
                alertDialog.show();
            }
        });
        //回退按钮
        btn_back.setOnClickListener((view) ->{
            intent.setClass(RegistActivity.this, Login.class);
            startActivity(intent);
        });
    }
}
