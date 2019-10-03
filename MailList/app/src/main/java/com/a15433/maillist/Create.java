package com.a15433.maillist;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.a15433.maillist.Login;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Create extends AppCompatActivity {
    private  String ID;
    private  String uri;
    private int SELECT_PICTURE = 0x00;
    private int SELECT_CAMERA = 0x01;
    private  Bitmap bitmap=null;
    SQLiteDatabase dbRead, dbWriter;
    ImageView createHeadPortrait;
    MyDBOpenHelper dbOpenHelper;
    Button btn_create,btn_back;
    EditText studentIdEdit,nameEdit,telephoneEdit,classEdit,birthdayEdit,sexEdit,dormitoryEdit,nativePlaceEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        this.getWindow().setBackgroundDrawableResource(R.drawable.background);
        studentIdEdit=(EditText)findViewById(R.id.create_studentIdEdit);
        nameEdit=(EditText)findViewById(R.id.create_nameEdit);
        telephoneEdit=(EditText)findViewById(R.id.create_telephoneEdit);
        classEdit=(EditText)findViewById(R.id.create_classEdit);
        birthdayEdit=(EditText)findViewById(R.id.create_birthdayEdit);
        sexEdit=(EditText)findViewById(R.id.create_sexEdit);
        dormitoryEdit=(EditText)findViewById(R.id.create_dormitoryEdit);
        nativePlaceEdit=(EditText)findViewById(R.id.create_nativePlaceEdit);
        btn_back = (Button)findViewById(R.id.create_btn_back) ;
        btn_create = (Button)findViewById(R.id.create_btn_create);
        createHeadPortrait=(ImageView)findViewById(R.id.createHeadPortrait);
        dbOpenHelper = new MyDBOpenHelper(getApplicationContext(), "SC_Database.db", null, 1);
        dbRead = dbOpenHelper.getReadableDatabase();
        dbWriter = dbOpenHelper.getWritableDatabase();
        createHeadPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myDialog = new AlertDialog.Builder(Create.this);
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
                                            case 1:{ //拍摄
                                                String state = Environment.getExternalStorageState();
                                                if (state.equals(Environment.MEDIA_MOUNTED)) { //sd卡可用 则开启手机拍摄
                                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                    startActivityForResult(intent, SELECT_CAMERA);
                                                }
                                                else{
                                                    Toast.makeText(Create.this,
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
        });

        btn_back.setOnClickListener(view ->{
            uri="";
            startActivity(new Intent(Create.this,MainActivity.class));
        });
        btn_create.setOnClickListener(view ->{
            int flag=0;
            ContentValues cv=new ContentValues();
            if(uri==null){
                uri=Integer.toString(R.drawable.ic_launcher);
            }
            if(studentIdEdit.getText().toString().equals("")||classEdit.getText().toString().equals("")||nameEdit.getText().toString().equals("")||telephoneEdit.getText().toString().equals("")){
                flag=2;
            }else {
                MyDBOpenHelper dbOpenHelper=new MyDBOpenHelper(getApplicationContext(),"SC_Database.db",null,1);
                SQLiteDatabase dbRead = dbOpenHelper.getReadableDatabase();
                Cursor result = dbRead.query("student",null,null,null,null,null,null);
                result.moveToFirst();
                while (!result.isAfterLast()){
                    ID = result.getString(2);
                    result.moveToNext();
                    if (studentIdEdit.getText().toString().equals(ID)){
                        flag=1;

                        break;
                    }
                }
                result.close();
            }

            //像数据表中添加数据，第二个参数射击队空列的填充处理策略
            if(flag==1){
                Toast.makeText(getApplicationContext(),"该学号已存在",Toast.LENGTH_LONG).show();
            }
            else if(flag==2){
                Login.setWarnOrTipDialog(Create.this,"提示","必填项为空！");
            }
            else{
                dbOpenHelper.insertData(dbWriter,uri,studentIdEdit.getText().toString(),nameEdit.getText().toString(),
                        telephoneEdit.getText().toString(),classEdit.getText().toString(),birthdayEdit.getText().toString(),
                        sexEdit.getText().toString(),dormitoryEdit.getText().toString(),nativePlaceEdit.getText().toString()
                );
                AlertDialog.Builder dialog = new AlertDialog.Builder(Create.this);
                dialog.setTitle("提示")
                        .setMessage("新建成功！")
                        .setPositiveButton("确定",(dialogInterface,i) ->{
                            startActivity(new Intent(Create.this,MainActivity.class));
                        });
                AlertDialog alertDialog = dialog.create();
                alertDialog.getWindow().setBackgroundDrawable(Login.setDialogBack(16, 16, 16, 16, 170, 255,255,255));
                alertDialog.show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         RoundTools roundTools=new RoundTools();
        if(requestCode==SELECT_PICTURE){
            try {
                uri = ImgUtil.handleImageOnKitKat(this, data);        //ImgUtil是自己实现的一个工具类
                bitmap=ImgUtil.getImage(uri);
            }catch (SecurityException e){
                Login.setWarnOrTipDialog(Create.this,"提示","新建头像失败！\n原因：未授予存储权限。");
            }
            if(bitmap!=null){
                Bitmap roundAlbum = roundTools.toRoundBitmap(bitmap);
                createHeadPortrait.setImageBitmap(roundAlbum);
            }
        }else if (requestCode == SELECT_CAMERA&&data!=null) {
            Bitmap bitmap_temp;
            try {
                // 两种方式获得拍摄照片的返回值
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    bitmap_temp = (Bitmap) bundle.get("data");
                    try{
                        uri=MediaStore.Images.Media.insertImage(getContentResolver(), bitmap_temp, "", "");//将图片插入到系统图库
                    }catch (SecurityException e){
                        Login.setWarnOrTipDialog(Create.this,"提示","新建头像失败！\n原因：未授予存储权限。");
                    }
                   if(uri!=null){
                       uri=ImgUtil.getImagePath(this,Uri.parse(uri.trim()),null);
                       bitmap_temp=ImgUtil.getImage(uri);  //对图片进行质量压缩
                       Bitmap roundAlbum = roundTools.toRoundBitmap(bitmap_temp);
                       createHeadPortrait.setImageBitmap(roundAlbum);
                       Toast.makeText(Create.this, "保存至本地"+uri, Toast.LENGTH_LONG)
                               .show();
                   }else{
                       Login.setWarnOrTipDialog(Create.this,"提示","新建头像失败！\n原因：未授予存储权限。");
                   }
                } else {
                    Toast.makeText(Create.this, "未拍摄照片", Toast.LENGTH_LONG)
                            .show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        //及时回收bitmap
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
        super.onDestroy();
    }
}
