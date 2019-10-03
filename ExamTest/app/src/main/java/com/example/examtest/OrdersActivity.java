package com.example.examtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OrdersActivity extends AppCompatActivity {
    ListView lvOrders;
    TextView tvFrom,tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        lvOrders = (ListView)findViewById(R.id.lvOrders);
        tvFrom = (TextView)findViewById(R.id.tv_from);
        tvTotal = (TextView)findViewById(R.id.tv_total);
        int numberApple,numberMelon,numberPear;
        boolean isCheckedApple,isCheckedMelon,isCheckedPear;
        String strFrom = getIntent().getStringExtra("From");
        tvFrom.setText("您的订单由 "+strFrom+" 发货");
        isCheckedApple = getIntent().getBooleanExtra("isCheckedApple",false);
        isCheckedMelon = getIntent().getBooleanExtra("isCheckedMelon",false);
        isCheckedPear = getIntent().getBooleanExtra("isCheckedPear",false);
        numberApple = getIntent().getIntExtra("NumberApple",0);
        numberMelon = getIntent().getIntExtra("NumberMelon",0);
        numberPear = getIntent().getIntExtra("NumberPear",0);

        String strListItems[]=new String[3];
        if(isCheckedApple){
            strListItems[0]="苹果 "+String.valueOf(numberApple)+" 公斤";
        }
        else{
            strListItems[0]="";
        }

        if(isCheckedMelon){
            strListItems[1]="甜瓜 "+String.valueOf(numberMelon)+" 公斤";
        }
        else{
            strListItems[1]="";
        }

        if(isCheckedPear){
            strListItems[2]="梨 "+String.valueOf(numberPear)+" 公斤";
        }
        else{
            strListItems[2]="";
        }
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strListItems);
        lvOrders.setAdapter(adapter);
        String totalString = String.valueOf(numberApple*8+numberMelon*6+numberPear*5);
        tvTotal.setText("订单总金额 "+totalString+" 元");
    }
}
