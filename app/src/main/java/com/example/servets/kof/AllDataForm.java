package com.example.servets.kof;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Calendar;


public class AllDataForm extends BaseActivity {
    DataBaseHelper MyDb;
    private String [] nazwa;
    private int num = -1;
    private ListView lv;
    private String Message = "", Email = "", nameEmp = "";
    private StringBuffer buffer;
    private BottomNavigationViewEx bottom;

    private int suma = 0;
    private int Employee = 0;
    private int Stawka = 0;
    private int costs = 0;
    private int shef = 0;

    private Button btnShow, btnSend, btnWrite;
    String [][] ZamowDay; //Nazwa, Stakan, Cena, Rodzaj, Oplata, Data
    String [] WithoutDublikatenazwa;
    String [] WithoutDublikateCup;
    String [] WithoutDublikateCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data_form);

        setupBottomNavigation(3);
        MyDb  = new DataBaseHelper(this);
        lv = (ListView)findViewById(R.id.AllList);

        bottom = (BottomNavigationViewEx)findViewById(R.id.bottom_navigation_view);
        bottom.setIconSize(36,36);
        setData();

        addListener();

        CreateData();
    }

    public void setData()
    {
        Cursor res = MyDb.getAllDataZamow(2);
        Cursor getEmail = MyDb.getAllData(7);
        num = MyDb.getCountData(8);
        ZamowDay = new String[num][6];
        nazwa = new String[num];
        int i = -1;
        if(res.moveToFirst()){
            do{
                i++;
                nazwa[i] = "Название: " + res.getString(1) +" Чашка: "+ res.getString(2) +" Цена: " + String.valueOf(res.getInt(3))+" Тип: "+ res.getString(4) +" Форма оплаты: " + res.getString(5);


                ZamowDay[i][0] = res.getString(1);
                ZamowDay[i][1] = res.getString(2);
                ZamowDay[i][2] = String.valueOf(res.getInt(3));
                ZamowDay[i][3] = res.getString(4);
                ZamowDay[i][4] = res.getString(5);
                ZamowDay[i][5] = res.getString(6);
            }
            while(res.moveToNext());
        }
        if(getEmail.moveToFirst()){
            do{
                Email = getEmail.getString(3);
                Stawka = getEmail.getInt(4);
                nameEmp = getEmail.getString(7);
            }
            while(getEmail.moveToNext());
        }

        res.close();
        getEmail.close();
        MyDb.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nazwa);

        lv.setAdapter(adapter);
    }


    private void addListener()
    {
        btnShow = (Button)findViewById(R.id.Show);
        btnSend = (Button)findViewById(R.id.SendEmail);
        btnWrite = (Button)findViewById(R.id.Write);
        btnShow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showMessage("Данные",buffer.toString());
                    }
                }
        );
        btnSend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String to = Email;
                            String subject = "Дневной отчёт";
                            String message = Message;

                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                            email.putExtra(Intent.EXTRA_SUBJECT, subject);
                            email.putExtra(Intent.EXTRA_TEXT, message);

                            //для того чтобы запросить email клиент устанавливаем тип
                            email.setType("message/rfc822");

                            startActivity(Intent.createChooser(email, "Выберите email клиент :"));
                        }
                        catch (Exception e)
                        {
                            StringBuffer bufferError = new StringBuffer();
                            showMessage("Ошибка отправки сообщения.", bufferError.toString());
                        }
                    }
                }
        );
        btnWrite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            for (int i = 0; i < ZamowDay.length; i++)
                                MyDb.insertDataAllZamow(1, ZamowDay[i][0], ZamowDay[i][1], Integer.valueOf(ZamowDay[i][2]), ZamowDay[i][3], ZamowDay[i][4], ZamowDay[i][5]);
                            Toast.makeText(AllDataForm.this, "Добавлено", Toast.LENGTH_LONG).show();

                            MyDb.DropTableAllZaow(1);
                            setData();
                        }
                        catch (Exception e){Toast.makeText(AllDataForm.this, String.valueOf(e), Toast.LENGTH_LONG).show();}
                        }
                }
        );
    }

    public void showMessage(String title, String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(AllDataForm.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public String [] setDataWithoutDublikate(String [] wD, int idStrok)
    {
        int powtor = 0;
        int q = 0;
        for (int i = 0; i < ZamowDay.length; i++)
        {
            for (int c = 0; c < wD.length; c++)
            {
                if (ZamowDay[i][idStrok].equals(wD[c]))
                    powtor++;
            }
            if (powtor < 1) {
                wD[q] = ZamowDay[i][idStrok];
                q++;
            }
            powtor = 0;
        }
        return wD;
    }
    public int [] setCountWithoutDublikate(int [] ilosc, String [] tab1, int idStrok)
    {
        for(int i = 0; i<tab1.length;i++)
            for(int c = 0; c<ZamowDay.length; c++)
                if(tab1[i].equals(ZamowDay[c][idStrok]) && tab1[i] != "")
                    ilosc[i]++;
        return ilosc;
    }

    private void CreateData()
    {
        WithoutDublikatenazwa = new String[ZamowDay.length];
        WithoutDublikateCup = new String[ZamowDay.length];
        WithoutDublikateCart = new String[ZamowDay.length];

        for (int i = 0; i < WithoutDublikatenazwa.length; i++)
        { WithoutDublikatenazwa[i] =""; WithoutDublikateCup[i] = ""; WithoutDublikateCart[i] = "";}


        WithoutDublikatenazwa = setDataWithoutDublikate(WithoutDublikatenazwa, 0);
        WithoutDublikateCup = setDataWithoutDublikate(WithoutDublikateCup, 1);
        WithoutDublikateCart = setDataWithoutDublikate(WithoutDublikateCart, 4);

        int [] count = new int [WithoutDublikatenazwa.length];
        int [] countCup = new int[WithoutDublikateCup.length];
        int [] countKarta = new int[WithoutDublikateCart.length];

        for(int i = 0; i<count.length;i++)
        {count[i] = 0; countCup[i] = 0; countKarta[i] = 0;}

        count = setCountWithoutDublikate(count, WithoutDublikatenazwa, 0);
        countCup = setCountWithoutDublikate(countCup, WithoutDublikateCup, 1);
        countKarta = setCountWithoutDublikate(countKarta, WithoutDublikateCart, 4);


        buffer = new StringBuffer();

        buffer.append("Работник: " + nameEmp + "\n");
        Message += "Работник: " + nameEmp + "\n" + "\n";
        for(int i = 0; i < ZamowDay.length; i++ )
            if(WithoutDublikatenazwa[i] != "") {
                buffer.append("Название: " + WithoutDublikatenazwa[i] + " количество: " + String.valueOf(count[i]) + "\n");
                Message += "Название: " + WithoutDublikatenazwa[i] + " количество: " + String.valueOf(count[i]) + "\n";
            }


        buffer.append("----------------------------------------- \n");
        Message +="----------------------------------------- \n";

        for(int i = 0; i < ZamowDay.length; i++ )
            if(WithoutDublikateCup[i] != "") {
                buffer.append("Чашка: " + WithoutDublikateCup[i] + " количество: " + String.valueOf(countCup[i]) + "\n");
                Message += "Чашка: " + WithoutDublikateCup[i] + " количество: " + String.valueOf(countCup[i]) + "\n";
            }

        buffer.append("----------------------------------------- \n");
        Message +="----------------------------------------- \n";

        for(int i = 0; i < ZamowDay.length; i++ )
            if(WithoutDublikateCart[i] != "") {
                buffer.append("Оплата " + WithoutDublikateCart[i] + " количество: " + String.valueOf(countKarta[i]) + "\n");
                Message += "Оплата " + WithoutDublikateCart[i] + " количество: " + String.valueOf(countKarta[i]) + "\n";
            }

        suma = 0;
        Employee = 0;
        costs = 0;
        shef = 0;
        Cursor res = MyDb.getDataCosts();
        if(res.moveToFirst()){
            do
                costs += res.getInt(2);
            while(res.moveToNext());
        }
        res.close();


        for(int i = 0; i< ZamowDay.length; i++)
            suma += Integer.parseInt(ZamowDay[i][2]);

        buffer.append("----------------------------------------- \n");
        Message +="----------------------------------------- \n";
        buffer.append("Касса: "+ String.valueOf(suma) + "\n");
        Message +="Касса: "+ String.valueOf(suma) + "\n";

        buffer.append("----------------------------------------- \n");
        Message +="----------------------------------------- \n";
        Employee = (suma * 10 / 100) + Stawka;
        buffer.append("Сотрудник: "+ String.valueOf(Employee) + "\n");
        Message +="Сотрудник: "+ String.valueOf(Employee) + "\n";

        buffer.append("----------------------------------------- \n");
        Message +="----------------------------------------- \n";
        buffer.append("Расходники: "+ String.valueOf(costs) + "\n");
        Message +="Расходники: "+ String.valueOf(costs) + "\n";

        buffer.append("----------------------------------------- \n");
        Message +="----------------------------------------- \n";
        shef = suma - Employee - costs;
        buffer.append("Шеф: "+ String.valueOf(shef) + "\n");
        Message += "Шеф: "+ String.valueOf(shef) + "\n";




        String timeGone = "";
        Cursor res2 = MyDb.getAllData(7);
        if(res2.moveToFirst()){
            do
                timeGone = res2.getString(1);
            while(res2.moveToNext());
        }
        res2.close();
        MyDb.close();

        Message += "\n Время отсутствия: " + timeGone;
    }
}
