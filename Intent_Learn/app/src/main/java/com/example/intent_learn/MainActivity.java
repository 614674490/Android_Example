package com.example.intent_learn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Button internet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.btn);
        internet=(Button)findViewById(R.id.btn_Internet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this,Second_Activity.class);
                startActivity(myintent);
            }
        });
        internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater dialogInflater = LayoutInflater.from(MainActivity.this);
                final View myView = dialogInflater.inflate(R.layout.baidu_open, null);
                Button btn1 = myView.findViewById(R.id.btn1);
                Button btn2 = myView.findViewById(R.id.btn2);
               AlertDialog myDialog = new AlertDialog.Builder(MainActivity.this)
                       .setView(myView)
                       .create();
                myDialog.show();

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri myuri = Uri.parse("http://m.baidu.com");  //定义Uri对象
                        Intent myIntent = new Intent(Intent.ACTION_VIEW,myuri);
                        //定义隐式Intent 第一个参数是动作 第二个参数是数据
                        startActivity(myIntent);
                        //启动与Intent匹配的Activity
                    }
                });
            }
        });
    }

}
