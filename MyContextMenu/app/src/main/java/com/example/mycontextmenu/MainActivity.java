package com.example.mycontextmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText et1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.registerForContextMenu(findViewById(R.id.edit1));  //为View对象注册上下文对象
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v==findViewById(R.id.edit1)){
            MenuInflater inflater = this.getMenuInflater();
            inflater.inflate(R.menu.menu,menu);  //引用菜单资源 创建上下文菜单
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        EditText et1 = (EditText)this.findViewById(R.id.edit1);
        switch (item.getItemId()) {
            case R.id.menu_settings: {
                et1.setText("您选择了菜单项：设置");
                break;
            }
            case R.id.menu_save: {
                et1.setText("您选择了菜单项：保存");
                break;
            }
            case R.id.menu_help: {
                et1.setText("您选择了菜单项：帮助");
                break;
            }
        }
            return  true;
    }
}
