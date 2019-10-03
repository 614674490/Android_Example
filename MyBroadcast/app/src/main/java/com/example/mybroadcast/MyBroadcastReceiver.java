package com.example.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    public MyBroadcastReceiver(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "开始搜索", Toast.LENGTH_SHORT).show();

        Log.d(TAG,"接收器接收了广播");
        String reseive_action = intent.getAction();  //获取广播的Activity
        Log.d(TAG,"接收器收到广播的action:"+reseive_action);
        String reseive_message = intent.getStringExtra("message");
        Log.d(TAG,"接收器收到广播的message:"+reseive_message);
    }
}
