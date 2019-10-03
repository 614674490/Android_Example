package com.example.menu_learn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ContextMenu_Learn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu__learn);
        this.registerForContextMenu(findViewById(R.id.editText1)); //为view对象注册上下文菜单

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderIcon(R.mipmap.ic_launcher_round);  //设置图标
        if(v == findViewById(R.id.editText1)){
            MenuInflater inflater = this.getMenuInflater();
            inflater.inflate(R.menu.contextmenu,menu);  //引用菜单资源 创建上下文菜单

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        EditText et1 = (EditText)findViewById(R.id.editText1);
        switch (item.getItemId()){
            case R.id.contextmenu_settings:{
                et1.setText("您选择了菜单项：设置");
                return true;
            }
            case R.id.contextmenu_save:{
                et1.setText("您选择了菜单项：保存");
                return true;
            }
            case R.id.contextmenu_help:{
                et1.setText("您选择了菜单项：帮助");
                return true;
            }
        }
        return true;
    }
}
