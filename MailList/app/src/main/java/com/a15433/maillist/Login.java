package com.a15433.maillist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    Button btn_register,btn_sign;
    SharedPreferences preferences;
    private ExitReceiver exitReceiver = new ExitReceiver();
    EditText et_name,et_password;
    TextView tv_email;
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取preference实例
        preferences = getSharedPreferences("Login",0);
        //动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("action.exit");
        registerReceiver(exitReceiver, filter);
        //判断是否自动登录
        if(preferences.getBoolean("flag",false)){
            //启动主界面
            intent.setClass(Login.this, MainActivity.class);
            startActivity(intent);
        }else{
            //加载登录布局
            setContentView(R.layout.activity_login);
            //加载背景
            this.getWindow().setBackgroundDrawableResource(R.drawable.background);

            et_name = (EditText)findViewById(R.id.login_ed_name);
            et_password = (EditText)findViewById(R.id.login_et_password);
            btn_register = (Button)findViewById(R.id.login_btn_register);
            btn_sign = (Button)findViewById(R.id.login_btn_sign);
            tv_email = (TextView)findViewById(R.id.email);
            //设置button透明度
            btn_register.getBackground().setAlpha(100);
            btn_sign.getBackground().setAlpha(100);
            //为button注册监听器  登录 注册
            btn_sign.setOnClickListener((view) -> {
                if(et_password.getText().toString().equals(preferences.getString(et_name.getText().toString(),"-1"))) {
                    intent.setClass(Login.this, MainActivity.class);
                    startActivity(intent);
                }else
                    Login.setWarnOrTipDialog(Login.this,"提示","用户名或密码错误！");
            });
            btn_register.setOnClickListener((view) -> {
                intent.setClass(Login.this, RegistActivity.class);
                startActivity(intent);
            });
            tv_email.setOnClickListener(view ->{
                //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=614674490";//uin是发送过去的qq号码
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //发送退出广播
        if (preferences.getBoolean("exit",false)){
            preferences.edit().putBoolean("exit",false)
                    .commit();
            sendBroadcast(new Intent("action.exit"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(exitReceiver);
    }

    //设置对话框
    public static void setWarnOrTipDialog(Context context,String msg1,String msg2){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog
                .setIcon(R.drawable.warning)
                .setTitle(msg1)
                .setMessage(msg2)
                .setPositiveButton("确认",null);
        AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setBackgroundDrawable(setDialogBack(16, 16, 16, 16, 170, 255,255,255));
        alertDialog.show();
    }
    /**  *  * @author  * @Description: 圆角布局  * @param cTopLeft  * 布局左上角 圆角半径  * @param cTopRight  * 布局右上角 圆角半径  * @param cBottomLeft  * 布局左下角 圆角半径  * @param cBottomRight  * 布局右下角 圆角半径
     * @param a 背景颜色透明度
     * @param r RGB颜色值中的R值，可用16进制表示
     * @param g RGB颜色值中的G值，可用16进制表示
     * @param b RGB颜色值中的B值，可用16进制表示
     */
    public static Drawable setDialogBack(float cTopLeft, float cTopRight, float cBottomLeft, float cBottomRight, int a, int r, int g, int b) {
        float outRectr[] = new float[] { cTopLeft, cTopLeft, cTopRight, cTopRight, cBottomRight, cBottomRight, cBottomLeft, cBottomLeft };
        RoundRectShape rectShape = new RoundRectShape(outRectr, null, null);
        ShapeDrawable normalDrawable = new ShapeDrawable(rectShape);
        normalDrawable.getPaint().setColor(Color.argb(a, r, g, b));
        return normalDrawable;
    }

    //广播接收类
    class ExitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("????","接收");
            Login.this.finish();
        }

    }
}


