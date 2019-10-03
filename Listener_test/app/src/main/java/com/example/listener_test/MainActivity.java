package com.example.listener_test;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
//通过内部类方式处理键盘按键事件
public class MainActivity extends AppCompatActivity {
    TextView textView;
    TextView textCode;
    EditText editText;
    String keyCodeStr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.testView);
        textCode=(TextView)findViewById(R.id.textCode);
        editText=(EditText)findViewById(R.id.editText);
        editText.setOnKeyListener(new MyOnKeyListener() );  //注册监听器
    }
    class MyOnKeyListener implements EditText.OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent exent){
            keyCodeStr=keyCodeStr+"键盘码:"+keyCode+"\n";
            textCode.setText(keyCodeStr);
            return false;
        }

    }
}
