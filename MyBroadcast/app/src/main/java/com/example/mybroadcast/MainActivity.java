package com.example.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Button btn1;
    private  MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"启动动态注册的接收器");
        IntentFilter filter = new IntentFilter();
        filter.addAction("mybroadcast.intent.android.MYBROADTEST");  //注册Receiver监听
        registerReceiver(myBroadcastReceiver,filter);
        btn = (Button)findViewById(R.id.btn);
        btn1 = (Button)findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("mybroadcast.intent.android.MYBROADTEST"); //封装广播消息
                intent.putExtra("message","这是广播中的额外消息");
                sendBroadcast(intent);  //发送广播消息
                Log.d(TAG,"发送广播消息");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterReceiver(myBroadcastReceiver);  //取消Reseiver监听
                Log.d(TAG,"注销广播接收器");
            }
        });
    }
}

