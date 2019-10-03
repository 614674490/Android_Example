package com.a15433.maillist;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {
    String stuID,stuName,stuTelephone,stuClass,stuBirthday,stuSex,stuDormitory,stuNativePlace,photoPath;
    SQLiteDatabase dbRead, dbWriter;
    MyDBOpenHelper dbOpenHelper;
    String ID;
    Cursor result;
    Button btnUpdate, btnDeleteDate;
    EditText studentIdEdit,nameEdit,telephoneEdit,classEdit,birthdayEdit,sexEdit,dormitoryEdit,nativePlaceEdit;
    TextView textView;
    ImageView headPortrait;
    private RoundTools roundTools;
    private int SELECT_PICTURE = 0x00;
    private int SELECT_CAMERA = 0x01;
    private static final String  TAG="TipMessage:";
    private Bitmap bitmap=null;
    private String uri=null;
    ArrayList<EditText> et = new ArrayList<>();  //存放EditView
    Boolean modifyFlog=false;           //信息可改标志位
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.getWindow().setBackgroundDrawableResource(R.drawable.background); //设置背景图片
        initData(); //初始化控件
        show();  //显示同学详细信息
        //在EditText不可编辑状态下点击跳转到电话
        telephoneEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //在信息锁死状态下可拨打电话 MotionEvent.ACTION_UP->触摸后抬起触发
                if(event.getAction()==MotionEvent.ACTION_UP&&!modifyFlog){
                    //隐式Intent 发送消息给手机电话 tel:uri形式的data->scheme 还有authority和path构成数据URI的三个部分
                    Uri uri = Uri.parse("tel:"+telephoneEdit.getText().toString()); //定义uri对象
                    Intent intent = new Intent(Intent.ACTION_DIAL, uri);//动作 数据
                    startActivity(intent);  //启动与Intent匹配的Activity
                }
                return false;  //防止不能对内容进行编译
            }
        });
        //两种状态，修改状态和显示状态，通过modifyFlog区分
        //1. 修改           删除   false
        //2  提交修改       取消   true
        btnUpdate.setOnClickListener(v->upData());       //修改信息
        btnDeleteDate.setOnClickListener(v ->deleteData()); //删除信息
    }
    //控件初始化
    public void initData(){
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDeleteDate = (Button) findViewById(R.id.btnDeletedate);
        headPortrait=(ImageView)findViewById(R.id.headPortrait);  //头像
        studentIdEdit=(EditText)findViewById(R.id.studentIdEdit);
        nameEdit=(EditText)findViewById(R.id.nameEdit);
        telephoneEdit=(EditText)findViewById(R.id.telephoneEdit);
        classEdit=(EditText)findViewById(R.id.classEdit);
        birthdayEdit=(EditText)findViewById(R.id.birthdayEdit);
        sexEdit=(EditText)findViewById(R.id.sexEdit);
        dormitoryEdit=(EditText)findViewById(R.id.dormitoryEdit);
        nativePlaceEdit=(EditText)findViewById(R.id.nativePlaceEdit);
        textView = (TextView)findViewById(R.id.detail_tv_phone);
        ID = getIntent().getStringExtra("studentData");
        dbOpenHelper = new MyDBOpenHelper(getApplicationContext(), "SC_Database.db", null, 1);
        dbRead = dbOpenHelper.getReadableDatabase();  //获取读权限
        dbWriter = dbOpenHelper.getWritableDatabase();//获取写权限
        headPortrait.setOnClickListener(v->modifyHeadPortrait()); //图片点击事件
        headPortrait.setClickable(false);  //将头像锁死
        et.add(studentIdEdit);et.add(nameEdit);et.add(telephoneEdit);et.add(classEdit);
        et.add(birthdayEdit);et.add(sexEdit);et.add(dormitoryEdit);et.add(nativePlaceEdit); //将EditText添加到ArrayList中 添加元素
        for(EditText ed:et){   //对列表进行遍历 将每个输入框设置为不可修改 即锁死状态
            ed.setFocusable(false);    //失能控件获得焦点 使焦点无法移动到当前控件
            ed.setFocusableInTouchMode(false);//是否可通过触摸获取焦点
        }
    }
    //修改方法
    public void modify(){
        int flag=0;
        if(studentIdEdit.getText().toString().equals("")||classEdit.getText().toString().equals("")||nameEdit.getText().toString().equals("")||telephoneEdit.getText().toString().equals("")){
            flag=2;
        } else {
            MyDBOpenHelper dbOpenHelper=new MyDBOpenHelper(getApplicationContext(),"SC_Database.db",null,1);
            SQLiteDatabase dbRead = dbOpenHelper.getReadableDatabase();
            Cursor result = dbRead.query("student",null,null,null,null,null,null);
            result.moveToFirst();
            while (!result.isAfterLast()){
                ID = result.getString(2);
                result.moveToNext();
                if (studentIdEdit.getText().toString().equals(ID)){
                    if(!studentIdEdit.getText().toString().equals(stuID))
                        flag=1;
                    else
                        flag=3;
                    break;
                }
            }
            result.close();
        }
        if(flag==1) {
            Toast.makeText(getApplicationContext(),"该学号已存在",Toast.LENGTH_LONG).show();
        }
        else if(flag==2){
            Login.setWarnOrTipDialog(Detail.this,"提示","必填项为空！");
        }
        else{
            ContentValues cv = new ContentValues();
            if(uri!=null){
                cv.put("stuImage",uri);
                Log.d(TAG,"update uri"+uri);
            }
            if(flag!=3){
                cv.put("stuId", studentIdEdit.getText().toString());
            }
                cv.put("stuName", nameEdit.getText().toString());
                cv.put("stuTelephone", telephoneEdit.getText().toString());
                cv.put("stuClass", classEdit.getText().toString());
                cv.put("stuBirthday", birthdayEdit.getText().toString());
                cv.put("stuSex", sexEdit.getText().toString());
                cv.put("stuDormitory", dormitoryEdit.getText().toString());
                cv.put("stuNativePlace", nativePlaceEdit.getText().toString());
                dbWriter.update("student", cv, "stuId=?", new String[]{ID});//更新数据
                Intent myIntent = new Intent(Detail.this, MainActivity.class);
                startActivity(myIntent);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==SELECT_PICTURE){
            if (resultCode == RESULT_OK) {
                try {
                    uri = ImgUtil.handleImageOnKitKat(this, data);        //ImgUtil是自己实现的一个工具类
                }catch (SecurityException e){
                    Login.setWarnOrTipDialog(Detail.this,"提示","更改头像失败！\n原因：未授予存储权限。");
                }
                    bitmap=ImgUtil.getImage(uri);
                if(bitmap!=null){
                    Bitmap roundAlbum = roundTools.toRoundBitmap(bitmap);
                    headPortrait.setImageBitmap(roundAlbum);
                }


            }
        }else if (requestCode == SELECT_CAMERA&&data!=null) {
            Bitmap bitmap_temp;
            Log.i(TAG, "相机");
            if (data.getData() == null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                                bitmap_temp = (Bitmap) bundle.get("data");
                                try{
                                    uri=MediaStore.Images.Media.insertImage(getContentResolver(), bitmap_temp, "", "");//将图片插入到系统图库
                                }catch(SecurityException e){
                                    Login.setWarnOrTipDialog(Detail.this,"提示","更改头像失败！\n原因：未授予存储权限。");
                                }
                                if(uri!=null){
                                    uri=ImgUtil.getImagePath(this,Uri.parse(uri.trim()),null);
                                    bitmap_temp=ImgUtil.getImage(uri);  //对图片进行质量压缩
                                    Bitmap roundAlbum = roundTools.toRoundBitmap(bitmap_temp);
                                    headPortrait.setImageBitmap(roundAlbum);
                                    Toast.makeText(Detail.this, "保存至本地"+uri, Toast.LENGTH_LONG)
                                            .show();
                                }
                                else{
                                    Login.setWarnOrTipDialog(Detail.this,"提示","更改头像失败！\n原因：未授予存储权限。");
                                }
                        }else {
                            Toast.makeText(Detail.this, "未拍摄照片", Toast.LENGTH_LONG)
                                    .show();
                        }
            }
        }
    }
    //显示信息
    private void show() {
        result = dbRead.query("student", null, "stuId=?", new String[]{ID}, null, null, null);
        if (!result.moveToFirst()) {
            Toast.makeText(getApplicationContext(), "数据表中一个数据也没有!",
                    Toast.LENGTH_LONG).show();
        }
        result.moveToFirst();
        photoPath =result.getString(1); //获取保存在数据库中图片路径
        stuID = result.getString(2);
        stuName = result.getString(3);
        stuTelephone = result.getString(4);
        stuClass = result.getString(5);
        stuBirthday = result.getString(6);
        stuSex = result.getString(7);
        stuDormitory = result.getString(8);
        stuNativePlace = result.getString(9);
        result.moveToNext();
        roundTools = new RoundTools();

            if(!photoPath.contains("storage")){ //判断是资源ID还是相册图片路径
                bitmap = BitmapFactory.decodeResource(getResources(), Integer.parseInt(photoPath));
                Bitmap roundAlbum = roundTools.toRoundBitmap(bitmap);
                headPortrait.setImageBitmap(roundAlbum);
            }
            else{  //图片地址
                bitmap = ImgUtil.getImage(photoPath);//位图 图片暂用内存较大 需要算法进行质量压缩
                if(bitmap==null){  //图片不存在  选择默认图片作为头像
                    ContentValues cv = new ContentValues();
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    photoPath=Integer.toString(R.drawable.ic_launcher);
                    cv.put("stuImage",photoPath);
                    dbWriter.update("student", cv, "stuId=?", new String[]{stuID});//更新数据
                    Toast.makeText(getApplicationContext(),"图片不存在",Toast.LENGTH_SHORT).show();
                }
                Bitmap roundAlbum = roundTools.toRoundBitmap(bitmap); //圆形
                headPortrait.setImageBitmap(roundAlbum);
            }


        studentIdEdit.setText(stuID);
        nameEdit.setText(stuName);
        telephoneEdit.setText(stuTelephone);
        classEdit.setText(stuClass);
        birthdayEdit.setText(stuBirthday);
        sexEdit.setText(stuSex);
        dormitoryEdit.setText(stuDormitory);
        nativePlaceEdit.setText(stuNativePlace);
        result.close();

    }

    protected void onDestroy() {
        super.onDestroy();
        dbWriter.close();
        dbRead.close();
        //及时回收bitmap
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }
    //修改图片
    public void modifyHeadPortrait(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(Detail.this);
        myDialog
                .setTitle("更改头像")
                .setItems(
                        R.array.photoSource, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:{  //从相册选择
                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, SELECT_PICTURE);
                                        break;
                                    }
                                    case 1:{ //拍摄 在拍摄之前判断一下SD卡是否可用
                                        String state = Environment.getExternalStorageState();
                                        if (state.equals(Environment.MEDIA_MOUNTED)) { //sd卡可用 则开启手机拍摄
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(intent, SELECT_CAMERA);
                                        }
                                        else{
                                            Toast.makeText(Detail.this,
                                                    "SD卡已满", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    }
                                }
                            }
                        });
        myDialog.setPositiveButton("确认",null);
        myDialog.setNegativeButton("取消",null);
        myDialog.setCancelable(false);  //防止触碰对话框外部范围时 对话框关闭
        myDialog.show();
    }
    //修改信息
    public void upData(){
        if (modifyFlog) {
            //修改状态  点击的是提交修改 提交完之后设为不可修改
            modify();  //执行提交修改方法
            modifyFlog=false;
        }else {
            //显示状态
            modifyFlog=true;
            //将EditText设为可修改
            for(EditText ed:et){
                ed.setFocusableInTouchMode(true);
                ed.setFocusable(true);
                ed.requestFocus(); //请求获取焦点
            }
            //将ImageView设置为可修改
            headPortrait.setClickable(true);
            btnUpdate.setText("提交修改");  //改变显示内容
            btnDeleteDate.setText("取消");
        }
    }
    //删除信息
    public void deleteData(){
        if(modifyFlog) {
            //可修改状态，取消修改状态
            modifyFlog=false;
            for(EditText ed:et){
                ed.setFocusableInTouchMode(false);
                ed.setFocusable(false);
                ed.requestFocus();
            }
            show();
            headPortrait.setClickable(false);
            btnDeleteDate.setText("删除");
            btnUpdate.setText("修改");
            //调用Login类中setWarnOrTipDialog函数显示提示对话框  上下文对象 主题 文字
            Login.setWarnOrTipDialog(Detail.this,"提示","取消修改！");  //提示用户取消修改
        }else {
            //显示状态，删除数据
            dbWriter.delete("student","stuId=?", new String[]{ID});
            Intent myIntent = new Intent(Detail.this,MainActivity.class);
            startActivity(myIntent);  //提交修改之后 自动回到主页面
        }

    }

}