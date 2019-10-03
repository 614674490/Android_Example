package com.example.menu_learn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //菜单创建方法 该方法在创建菜单时只会调用一次 要返回true
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu,menu);  //调用inflate方法填充布局
        return true;
    }
    //动态创建菜单方法 每次显示菜单时都会调用 不适用此程序

  /*  @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
         super.onPrepareOptionsMenu(menu);
         MenuInflater inflater = this.getMenuInflater();
         inflater.inflate(R.menu.menu,menu);  //调用inflate方法填充布局
         return true;
    }*/
    //处理菜单项点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_setting :{
                Toast.makeText(getApplicationContext(),"您点击了菜单项：设置",Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.menu_save :{
                Toast.makeText(getApplicationContext(),"您点击了菜单项：自动保存",Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.menu_docment :{
                Toast.makeText(getApplicationContext(),"您点击了菜单项：联机文档",Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.menu_search :{
                Toast.makeText(getApplicationContext(),"您点击了菜单项：搜索",Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.menu_about :{
                Toast.makeText(getApplicationContext(),"您点击了菜单项：关于",Toast.LENGTH_LONG).show();
                AlertDialog.Builder exitAlert = new AlertDialog.Builder(MainActivity.this);//建立一个对话框
                exitAlert.setTitle("版权声明");
                exitAlert.setMessage("这是教材的示例程序：\n 版本号：1.0");
                exitAlert.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                exitAlert.create();
                exitAlert.show();
                return true;
            }
            case R.id.menu_exit :{
                MainActivity.this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
