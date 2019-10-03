package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private  String TAG = "我的提示";
    private boolean running = false;
    String myDataFromActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"MyService:onCreate()被调用");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"MyService:onBind()被调用");
        myDataFromActivity=intent.getStringExtra("myData");
        running = true;
        new Thread(){
            @Override
            public void run() {
                super.run();
                while(running){
                    try {
                        System.out.println("服务已启动,接收到的数据："+myDataFromActivity);
                        sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myDataFromActivity=intent.getStringExtra("myData");
        running = true;
        new Thread(){
            @Override
            public void run() {
                super.run();
                while(running){
                    try {
                        System.out.println("服务已启动,接收到的数据："+myDataFromActivity);
                        sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"MyService:onUnbind()被调用");
        running = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        Log.d(TAG,"MyService:onDestroy()被调用");

    }
}
