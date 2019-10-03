package com.example.examtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    Button btnSubmit;    //提交订单按钮
    EditText edApple,edMelon,edPear;   //水果购买重量输入框
    int numberApple=0,numberMelon=0,numberPear=0;  //购买量
    CheckBox chkApple,chkMelon,chkPear;            //复选框
    boolean isCheckedApple,isCheckedMelon,isCheckedPear;  //单选框
    RadioGroup rgFrom;
    String strFrom;                             //来源地
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSubmit = (Button)findViewById(R.id.btn_ok);
        edApple = (EditText)findViewById(R.id.number_apple);
        edMelon = (EditText)findViewById(R.id.number_melon);
        edPear = (EditText)findViewById(R.id.number_pear);
        chkApple = (CheckBox)findViewById(R.id.chkApple);
        chkMelon = (CheckBox)findViewById(R.id.chkMelon);
        chkPear = (CheckBox)findViewById(R.id.chkPear);
        rgFrom = (RadioGroup)findViewById(R.id.rg_from);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkApple.isChecked()){
                    numberApple = Integer.parseInt(edApple.getText().toString());
                    isCheckedApple = true;
                }
                else{
                    numberApple = 0;
                    isCheckedApple  = false;
                }

                if(chkMelon.isChecked()){
                    numberMelon = Integer.parseInt(edMelon.getText().toString());
                    isCheckedMelon = true;
                }
                else{
                    numberMelon = 0;
                    isCheckedMelon  = false;
                }

                if(chkPear.isChecked()){
                    numberPear = Integer.parseInt(edPear.getText().toString());
                    isCheckedPear = true;
                }
                else{
                    numberPear = 0;
                    isCheckedPear  = false;
                }
                switch(rgFrom.getCheckedRadioButtonId()){
                    case R.id.rb_HeBei:
                        strFrom="河北";
                        break;
                    case R.id.rb_ShanDong:
                        strFrom="山东";
                        break;
                    case R.id.rb_ShanXi:
                        strFrom="陕西";
                        break;
                }
                Intent intent = new Intent(MainActivity.this,OrdersActivity.class);
                intent.putExtra("NumberApple",numberApple);
                intent.putExtra("isCheckedApple",isCheckedApple);
                intent.putExtra("NumberMelon",numberMelon);
                intent.putExtra("isCheckedMelon",isCheckedMelon);
                intent.putExtra("NumberPear",numberPear);
                intent.putExtra("isCheckedPear",isCheckedPear);
                intent.putExtra("From",strFrom);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.reset :{
                chkApple.setChecked(false);
                chkMelon.setChecked(false);
                chkPear.setChecked(false);

                rgFrom.clearCheck();
                rgFrom.check(R.id.rb_HeBei);

                edApple.setText("0");
                edMelon.setText("0");
                edPear.setText("0");

            }
            break;
            case R.id.exit : {
                finish();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
}
