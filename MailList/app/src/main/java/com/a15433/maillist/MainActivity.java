package com.a15433.maillist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase dbRead,dbWriter;
    MyDBOpenHelper dbOpenHelper;
    SimpleCursorAdapter listViewAdapter;
    ListView listView;
    Cursor result;
    TextView stuID;
    SharedPreferences preferences;
    EditText change_name,change_password,change_secondpassword,ed_select,change_ed_resecond;
    Button change_btn_regist,change_btn_back,btn_query;
    MenuItem menuItem;
    String query_msg = "学号";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setBackgroundDrawableResource(R.drawable.background);
        //获得数据库读、写对象
        dbOpenHelper = new MyDBOpenHelper(getApplicationContext(),"SC_Database.db",null,1);
        dbRead = dbOpenHelper.getReadableDatabase();
        dbWriter = dbOpenHelper.getWritableDatabase();

        listView=(ListView)findViewById(R.id.listView);
        btn_query = (Button)findViewById(R.id.main_btn_query);
        ed_select = (EditText)findViewById(R.id.main_ed_select);

        btn_query.getBackground().setAlpha(100);

        preferences = getSharedPreferences("Login",0);
        Spinner spinner = (Spinner)findViewById(R.id.main_spinner);
        //设置下拉列表
        String[] sp = {"学号","姓名","电话"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.list_main,
                sp);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0,true);
        //为下拉列表注册列表选择监听器
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取选择项
                query_msg = sp[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //根据条件查询
        btn_query.setOnClickListener( view ->{
            //为空查所有
            if(ed_select.getText().toString().equals("")) {
                showAll();
            }else{
                //非空模糊查询
                //query_msg所查询列名
                switch (query_msg){
                    case "学号":query_msg = "stuId";break;
                    case "姓名":query_msg = "stuName";break;
                    case "电话":query_msg = "stuTelephone";break;
                }

                result = dbRead.rawQuery("SELECT  * FROM student where "+query_msg+" like '%"+ed_select.getText().toString()+"%'",null);
                if (!result.moveToFirst()){
                    Toast.makeText(getApplicationContext(),"数据表中无数据!",
                            Toast.LENGTH_LONG).show();
                }else{
                    listViewAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.list_item,
                            result,new String[]{"stuId","stuName","stuTelephone"},
                            new int[]{R.id.itemID, R.id.itemName, R.id.itemDianhua},
                            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    listView.setAdapter(listViewAdapter);
                }
            }
        });
        //显示所有
        showAll();
        //为listview添加列表项监听事件
        listView.setOnItemClickListener((parent, view, position, id) -> {
            stuID= (TextView)view.findViewById(R.id.itemID);
            Intent myIntent = new Intent(MainActivity.this, Detail.class);
            myIntent.putExtra("studentData",stuID.getText().toString());
            startActivity(myIntent);
        });
    }
    //初始化菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        //获取登录是否需要密码控件句柄
        //手动设置是否有对勾
        menuItem = menu.findItem(R.id.needpassword);
        menuItem.setChecked(!preferences.getBoolean("flag",false));
        return true;
    }
    //菜单点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = preferences.edit();
        switch (item.getItemId()) {
            case R.id.needpassword: //登录是否需要密码
                editor.putBoolean("flag",!preferences.getBoolean("flag",false));
                editor.commit();
                menuItem.setChecked(!preferences.getBoolean("flag",false));
                break;
            case R.id.changepassword:
                //加载修改密码布局
                setContentView(R.layout.changepassword);

                change_btn_back = (Button)findViewById(R.id.change_btn_back);
                change_btn_regist = (Button)findViewById(R.id.change);
                change_name = (EditText)findViewById(R.id.change_ed_name);
                change_password = (EditText)findViewById(R.id.change_ed_password);
                change_secondpassword = (EditText)findViewById(R.id.change_ed_secongpassword);
                change_ed_resecond = (EditText)findViewById(R.id.change_ed_resecond);
                change_btn_regist.getBackground().setAlpha(100);
                change_btn_back.getBackground().setAlpha(100);
                //返回主界面
                change_btn_back.setOnClickListener(view ->{
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                });
                //提交修改
                change_btn_regist.setOnClickListener((view) ->{
                    if(preferences.getString(change_name.getText().toString(),"-1").equals(change_password.getText().toString())){
                        if(change_ed_resecond.getText().toString().equals(change_secondpassword.getText().toString())) {
                            editor.putString(change_name.getText().toString(), change_secondpassword.getText().toString());
                            editor.commit();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                        }else{
                            Login.setWarnOrTipDialog(MainActivity.this,"提示","两次输入密码不一致！");
                        }
                    }else{
                        Login.setWarnOrTipDialog(MainActivity.this,"提示","原密码错误！");
                    }
                });
                break;
            case R.id.new_contacts:
                //新建联系人
                startActivity(new Intent(MainActivity.this, Create.class));
                break;
            case R.id.output:
                //输出为txt
                write();
                break;
            case R.id.exit:
                //退出   singleTask+BroadcastReceiver
                preferences.edit().putBoolean("exit",true)
                        .commit();
                startActivity(new Intent(MainActivity.this, Login.class));
                break;
        }
        return true;
    }
    //显示所有
    private void showAll(){
        result = dbRead.query("student",null,null,null,null,null,"stuId asc");

        if (!result.moveToFirst()){
            Toast.makeText(getApplicationContext(),"数据表中无数据!",
                    Toast.LENGTH_LONG).show();
        }
        listViewAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.list_item,
                result,new String[]{"stuId","stuName","stuTelephone"},
                new int[]{R.id.itemID, R.id.itemName, R.id.itemDianhua},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(listViewAdapter);
    }
    //向txt中写数据
    protected void write(){
//        if(Environment.MEDIA_MOUNTED.equals(state)){
            File file = new File(Environment.getExternalStorageDirectory()+"/mail.txt");
            try {
                if(file.exists()) {
                    //清空数据
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write("".getBytes());
                    outputStream.flush();
                    outputStream.close();
                }else {
                    file.createNewFile();
                }

                FileOutputStream outputStream = new FileOutputStream(file);
                PrintWriter printWriter = new PrintWriter(outputStream,true);

                result.moveToFirst();

                while (!result.isAfterLast()){
                    String s="";

                    for(int i=2;i<10;i++){
                        s += result.getString(i)+" ";
                    }

                    printWriter.println(s);
                    result.moveToNext();
                }

                Toast.makeText(getApplicationContext(),"成功输出到文件mail.txt中",Toast.LENGTH_LONG).show();

                printWriter.close();
            } catch (Exception e) {
                Login.setWarnOrTipDialog(MainActivity.this,"提示","文件输出失败！\n可能原因：未授予存储权限。");
            }

    }
    protected void onDestroy(){
        super.onDestroy();
        dbWriter.close();
        dbRead.close();
    }
}
