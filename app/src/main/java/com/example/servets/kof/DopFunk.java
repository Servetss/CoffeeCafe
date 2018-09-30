package com.example.servets.kof;

import android.database.Cursor;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class DopFunk extends BaseActivity {
    private  DataBaseHelper MyDd;
    private BottomNavigationViewEx buttom;
    private Button btnGone, btnAccept, btnAdd, btnMinus, btnMultiply, btnDivide;
    boolean hasGone = false;
    private Chronometer simpleChronometer;
    private TextView firstNumber, secondNumber, result;
    public String time = "0" , resultNum, min, hour ="00", sec, Email, timeInDb = "", pass, writepass, nameEmpl;
    private int stawka, count;
    private Cursor res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dop_funk);

        MyDd = new DataBaseHelper(this);
        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);
        firstNumber = (TextView)findViewById(R.id.FirstNum);
        secondNumber = (TextView)findViewById(R.id.SecondNum);
        result = (TextView)findViewById(R.id.textView3);

        setupBottomNavigation(2);
        buttom = (BottomNavigationViewEx)findViewById(R.id.bottom_navigation_view);
        buttom.setIconSize(36,36);

        setData();
        addListener();
    }
    private void setData()
    {
        count = MyDd.getCountData(7);
        res = MyDd.getAllData(7);

        if (res.moveToFirst()) {
            do {
                timeInDb = res.getString(1);
                resultNum = res.getString(2);
                Email = res.getString(3);
                stawka = res.getInt(4);
                pass = res.getString(5);
                writepass = res.getString(6);
                nameEmpl = res.getString(7);
            }
            while (res.moveToNext());
        }
        //if(!resultNum.isEmpty())
        //{result.setText(resultNum);}
        //Toast.makeText(DopFunk.this, resultNum, Toast.LENGTH_LONG).show();
    }

    private void addListener()
    {
        btnGone = (Button)findViewById(R.id.Gone);
        btnAccept = (Button)findViewById(R.id.button7);
        btnAdd = (Button)findViewById(R.id.button);
        btnMinus = (Button)findViewById(R.id.button5);
        btnMultiply = (Button)findViewById(R.id.button2);
        btnDivide = (Button)findViewById(R.id.button6);
        btnGone.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try
                        {
                        if(!hasGone) {
                            btnGone.setBackgroundColor(Color.rgb(255, 0, 0));
                            btnGone.setText("ПРИШЕЛ");
                            hasGone = true;

                            simpleChronometer.setBase(SystemClock.elapsedRealtime());
                            simpleChronometer.start();
                        }
                        else {
                            btnGone.setBackgroundColor(Color.rgb(0, 255, 0));
                            btnGone.setText("УШЕЛ");
                            hasGone = false;
                            simpleChronometer.stop();
                            setData();
                            time = simpleChronometer.getText().toString();
                            Log.d("Time", "if " +String.valueOf(time.length())+ " <= 5. Time: " + time);
                            if (time.length() <= 5) {
                                min = time.substring(0, 2);
                                sec = time.substring(3, 5);
                            } else {
                                hour = time.substring(0, 2);
                                min = time.substring(3, 5);
                                sec = time.substring(6, 8);
                            }
                            int h, m, s;
                            if(timeInDb != null)
                                {
                                        String hourDb = timeInDb.substring(0, 2);
                                        String minDb = timeInDb.substring(3, 5);
                                        String secDb = timeInDb.substring(6, 8);


                                        h = Integer.valueOf(hour) + Integer.valueOf(hourDb);
                                        m = Integer.valueOf(min) + Integer.valueOf(minDb);
                                        s = Integer.valueOf(sec) + Integer.valueOf(secDb);


                                    String a = String.valueOf(h), b= String.valueOf(m) ,c = String.valueOf(s);

                                        if(String.valueOf(h).length() == 1)
                                            a = "0" + String.valueOf(h);
                                        if(String.valueOf(m).length() == 1)
                                            b = "0" + String .valueOf(m);
                                        if(String.valueOf(s).length() ==1)
                                            c = "0" + String.valueOf(s);


                                        MyDd.insertChlam(2, "1", a + ":" + b + ":" + c, "0", Email, stawka, pass, writepass, nameEmpl);
                                }
                                else
                                    MyDd.insertChlam(2, "1", hour + ":" + min + ":" + sec, "0", Email, stawka, pass, writepass, nameEmpl);

                                res.close();
                        }
                        }
                        catch (Exception e){Toast.makeText(DopFunk.this, String.valueOf(e), Toast.LENGTH_LONG).show();}
                    }
                }
        );
        btnAccept.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            TextView nazwaTow = (TextView) findViewById(R.id.editText12);
                            TextView cenaTow = (TextView) findViewById(R.id.editText13);

                            if(nazwaTow.getText().toString().isEmpty() || cenaTow.getText().toString().isEmpty()) {
                                Toast.makeText(DopFunk.this, "Нужно заполнить пустые поля", Toast.LENGTH_LONG).show();
                            }
                            else {
                                boolean insert = MyDd.insertCost(nazwaTow.getText().toString(), Integer.valueOf(cenaTow.getText().toString()));
                                if (insert == true)
                                    Toast.makeText(DopFunk.this, "Успешно", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(DopFunk.this, "Ошибка", Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e){ Toast.makeText(DopFunk.this, String.valueOf(e), Toast.LENGTH_SHORT).show();}
                    }
                }
        );
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //setData();
                            int plus = Integer.valueOf(firstNumber.getText().toString()) + Integer.valueOf(secondNumber.getText().toString());
                            resultNum = String.valueOf(plus);
                            result.setText(String.valueOf(plus));


                                //MyDd.insertChlam(2, "1", time, timeInDb, Email, stawka);
                        }
                        catch (Exception e) {}
                    }
                }
        );
        btnMinus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //setData();
                            int min = Integer.valueOf(firstNumber.getText().toString()) - Integer.valueOf(secondNumber.getText().toString());
                            resultNum = String.valueOf(min);
                            result.setText(String.valueOf(min));


                                //MyDd.insertChlam(2, "1", timeInDb, resultNum, Email, stawka);
                        }
                        catch (Exception e){}
                    }
                }
        );
        btnMultiply.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //setData();
                            int mult = Integer.valueOf(firstNumber.getText().toString()) * Integer.valueOf(secondNumber.getText().toString());
                        resultNum = String.valueOf(mult);
                        result.setText(String.valueOf(mult));


                            //MyDd.insertChlam(2, "1", timeInDb, resultNum, Email, stawka);
                    }
                    catch (Exception e ){}
                    }
                }
        );
        btnDivide.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (Integer.valueOf(secondNumber.getText().toString()) != 0) {
                                //setData();
                                int divid = Integer.valueOf(firstNumber.getText().toString()) / Integer.valueOf(secondNumber.getText().toString());

                                resultNum = String.valueOf(divid);
                                result.setText(String.valueOf(divid));

                                    //MyDd.insertChlam(2, "1", timeInDb, resultNum, Email, stawka);
                            } else
                                Toast.makeText(DopFunk.this, "На ноль делить нельзя", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){}
                    }
                }
        );
    }
}
