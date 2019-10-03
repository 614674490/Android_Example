package com.example.listener_test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
//通过直接实现接口方式来对按钮点击事件进行响应
public class NewActivity extends AppCompatActivity implements View.OnClickListener {
    int count=0;
    Button myBtn;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        myBtn=(Button)findViewById(R.id.click_button);
        myBtn.setOnClickListener(this);  //注册事件监听器
    }
    //重写接口中的抽象函数
    public void onClick(View v){
        count++;
        if(count==5){
            finish();     //退出执行
        }
        else
            myBtn.setText("我被点击了"+count+"次");
    }
}
