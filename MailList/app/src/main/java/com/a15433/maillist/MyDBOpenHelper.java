package com.a15433.maillist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by 15433 on 2019/6/20.
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {

    public MyDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //执行SQL语句，创建数据表
        db.execSQL("CREATE TABLE student(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "stuImage TEXT NOT NULL," +
                "stuId TEXT NOT NULL," +
                "stuName TEXT NOT NULL," +
                "stuTelephone TEXT NOT NULL,"+
                "stuClass TEXT,"+
                "stuBirthday TEXT,"+
                "stuSex TEXT,"+
                "stuDormitory TEXT,"+
                "stuNativePlace TEXT);");
        ;
        ////初始化一些数据
                insertData(db,Integer.toString(R.drawable.p10),"170707101","常炳炎","15716807242","物联网171","1998-11-11","男","9C618","河北省邯郸市磁县");
                insertData(db,Integer.toString(R.drawable.p27),"170707102","代晓丽","15630401589","物联网171","2000-03-06","女","6B615","河北省邢台市广宗县");
                insertData(db,Integer.toString(R.drawable.p12),"170707103","冯存帅","15511303375","物联网171","1998-08-12","男","9C618","河北省保定市曲阳县");
                insertData(db,Integer.toString(R.drawable.p28),"170707104","付贵东","13400318947","物联网171","1999-05-15","男","9C619","河北省石家庄市行唐县");
                insertData(db,Integer.toString(R.drawable.p8),"170707105","高  媛","13051633526","物联网171","1997-01-13","女","6B614","河北省石家庄市平山县");
                insertData(db,Integer.toString(R.drawable.p22),"170707106","何月凤","15630457393","物联网171","1999-09-29","女","6B615","河北省衡水市武邑县");
                insertData(db,Integer.toString(R.drawable.p3),"170707107","侯月岭","15613356807","物联网171","1999-04-06","男","9C619","河北省邢台市桥西区");
                insertData(db,Integer.toString(R.drawable.p23),"170707108","胡博谭","13393218771","物联网171","1997-11-26","男","9C619","河北省石家庄市赵县");
                insertData(db,Integer.toString(R.drawable.p1),"170707109","李圭印","18931983446","物联网171","2000-01-16","男","9C619","河北省沧州市盐山县");
                insertData(db,Integer.toString(R.drawable.p11),"170707110","李竹林","15531892365","物联网171","2000-01-06","男","9C620","河北省衡水市冀州区");
                insertData(db,Integer.toString(R.drawable.p24),"170707111","刘建章","15803280327","物联网171","1998-03-27","男","9C620","河北省秦皇岛市卢龙县");
                insertData(db,Integer.toString(R.drawable.p29),"170707112","刘玉航","18713457741","物联网171","1997-03-04","男","9C620","河北省承德市宽城满族自治县");
                insertData(db,Integer.toString(R.drawable.p13),"170707113","路平安","15075820409","物联网171","1997-04-09","男","9C620","河北省衡水市景县");
                insertData(db,Integer.toString(R.drawable.p35),"170707114","吕  唐","15630162878","物联网171","1998-06-01","女","6B615","河北省定州市东留春乡");
                insertData(db,Integer.toString(R.drawable.p25),"170707115","牛秀婷","15630401512","物联网171","1997-09-24","女","6B615","河北省张家口市张北县");
                insertData(db,Integer.toString(R.drawable.p16),"170707116","王  琦","15630162372","物联网171","1998-10-07","女","6B615","河北省张家口市怀来县");
                insertData(db,Integer.toString(R.drawable.p30),"170707117","王玉杰","17678021226","物联网171","1997-12-26","男","9C620","内蒙古呼和浩特市和林格尔县");
                insertData(db,Integer.toString(R.drawable.p31),"170707118","肖奕霖","15630467616","物联网171","1999-05-22","女","6B615","河北省沧州市运河区");
                insertData(db,Integer.toString(R.drawable.p32),"170707119","谢秦宇","18103350219","物联网171","1999-02-19","男","9C621","河北省承德市兴隆县");
                insertData(db,Integer.toString(R.drawable.p4),"170707120","杨霄涵","18633245663","物联网171","1999-07-02","女","6B616","河北省保定市徐水区");
                insertData(db,Integer.toString(R.drawable.p7),"170707121","姚玉婷","15630487729","物联网171","1999-07-29","女","6B616","天津市河东区");
                insertData(db,Integer.toString(R.drawable.p33),"170707122","于佳正","15632656610","物联网171","1998-09-26","男","9C621","河北省三河市齐心庄镇");
                insertData(db,Integer.toString(R.drawable.p34),"170707123","张佳乐","15511312910","物联网171","1998.08.08","男","9C621","河北省河间市束城镇");
                insertData(db,Integer.toString(R.drawable.p20),"170707124","张子强","15103227968","物联网171","1999-12-15","男","9C621","河北省保定市唐县");
                insertData(db,Integer.toString(R.drawable.p25),"170707125","赵佳伟","15630469628","物联网171","1999-01-01","男","9C622","河北省邢台市隆尧县");
                insertData(db,Integer.toString(R.drawable.p26),"170707126","周  麟","15176651758","物联网171","1999-04-24","男","6B616","河北省唐山市开平区");
                insertData(db,Integer.toString(R.drawable.p6),"170707127","庄田佳","13113583075","物联网171","1999-11-07","男","9C618","内蒙古赤峰市宁城县");
                insertData(db,Integer.toString(R.drawable.p21),"170707128","郑飞跃","15630131805","物联网171","1999-01-06","男","9C618","安徽省亳州市谯城区");
                insertData(db,Integer.toString(R.drawable.p9),"170707128","张建成","15630491586","物联网171","1999-05-17","男","9C619","安徽省阜阳市太和县");
                insertData(db,Integer.toString(R.drawable.p5),"170707130","张贺俊","15630169207","物联网171","1998-11-25","男","9C619","江苏省泰兴市宣堡镇");
                insertData(db,Integer.toString(R.drawable.p17),"170707131","叶建如","15061425065","物联网171","1999-07-22","男","9C620","江苏省泰兴市宣堡镇");
                insertData(db,Integer.toString(R.drawable.p26),"170707132","赵  俊","18717473693","物联网171","1998-11-21","男","9C621","陕西省岐山县");
                insertData(db,Integer.toString(R.drawable.p15),"170707133","徐浩天","18629094693","物联网171","1999-05-25","男","9C621","陕西省西安市碑林区");
                insertData(db,Integer.toString(R.drawable.p26),"170707134","冉缰庆","17623805238","物联网171","2002-03-16","男","9C622","重庆市酉阳县");
                insertData(db,Integer.toString(R.drawable.p14),"170707135","阳云梦","15630401585","物联网171","1998-09-27","男","6B616","重庆市大足区");
    }
    @Override
    public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
        _db.execSQL("DROP TABLE IF EXISTS student");
        onCreate(_db);
    }
    //将drawable转换成可以用来存储的byte[]类型
    private byte[] getPicture(Drawable drawable) {
        Log.d("MessageError:","drawable start");
        if (drawable == null) {
            Log.d("MessageError:","drawable null");
            return null;
        }
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream(); //构造一个字节输出流对象，用于保存图片数据
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);//bmp为要保存到数据库中图片，在此图片必须为Bitmap类型
        Log.d("MessageError:",os.toByteArray().toString());
        return os.toByteArray();
    }
    public void insertData(SQLiteDatabase db,String photoPath,String stuId,String stuName,String stuTelephone,String stuClass,String stuBirthday,String stuSex,String stuDormitory,String stuNativePlace){
        ContentValues cv = new ContentValues();
        cv.put("stuImage", photoPath);
        cv.put("stuId", stuId);
        cv.put("stuName", stuName);
        cv.put("stuTelephone", stuTelephone);
        cv.put("stuClass", stuClass);
        cv.put("stuBirthday", stuBirthday);
        cv.put("stuSex", stuSex);
        cv.put("stuDormitory", stuDormitory);
        cv.put("stuNativePlace", stuNativePlace);
        db.insert("student", null, cv);
    }

}
