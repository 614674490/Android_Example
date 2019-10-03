package com.example.myservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button start_service;
    Button stop_service;
    Button bind_service;
    Button unbind_service;
    EditText myDataToService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //成功绑定该服务后 该方法被调用 在该方法中可以获得MyService对象
            Toast.makeText(MainActivity.this,"服务被成功绑定.",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //服务所在进程崩溃或被杀死 或Service意外断开后 该方法被调用
            Toast.makeText(MainActivity.this,"服务连接失败",Toast.LENGTH_LONG).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_service = (Button)findViewById(R.id.start_service);
        stop_service = (Button)findViewById(R.id.stop_service);
        bind_service = (Button)findViewById(R.id.bind_service);
        unbind_service = (Button)findViewById(R.id.unbind_service);
        myDataToService = (EditText)findViewById(R.id.myDataToService);
        start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyService.class);
                intent.putExtra("myData",myDataToService.getText().toString());
                startService(intent); //启动服务
            }
        });
        stop_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this,MyService.class));
            }
        });
        bind_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this,MyService.class);
                serviceIntent.putExtra("myData",myDataToService.getText().toString());
                bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE); //绑定Service
            }
        });
        unbind_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);  //解除绑定Service
            }
        });
    }
}
