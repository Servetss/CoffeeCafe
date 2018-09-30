package com.example.servets.kof;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Zapros extends AppCompatActivity {
    private DataBaseHelper MyDb;
    private CheckBox naz, cup, opl, cena, typ;
    private EditText day, month, year;
    private Button accept;
    private String SqlMess = "";
    private String dayNow, monthNow, yearNow;
    private Spinner spinKub, spinPlat, spinNazw, spinCena, spinTyp;
    private String spinnerCup[] = {"Ничего", "110","175", "250", "320", "400", "500", "Чайная чашка", "Пластик"}, spinnerOpl[] = {"Ничего", "Карта","Наличка"};
    private String withoutDublicateNazwa[];
    private String withoutDublicateCena[];
    private String withoutDublicateTyp[];
    private String WDNSpin[], WDCSpin[], WDTSpin[];
    private String ZamowDay[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapros);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        //Toast.makeText(Zapros.this, mydate, Toast.LENGTH_LONG).show();

        /*monthNow = mydate.substring(0, 3);
        dayNow = mydate.substring(4,6);
        yearNow = mydate.substring(8,12);     Если английская операционная система       */

        monthNow = mydate.substring(3, 6);// Если русская ОС
        dayNow = mydate.substring(0,2);
        yearNow = mydate.substring(8,12);

        MyDb = new DataBaseHelper(this);


        addListener();
        setData();


    }
    private String [] setDataWithoutDublikate (String nazwa[], int id)
    {
        int powtor = 0;
        int q = 0;
        for (int i = 0; i < ZamowDay.length; i++)
        {
            for (int c = 0; c < nazwa.length; c++)
            {
                if (ZamowDay[i][id].equals(nazwa[c]))
                    powtor++;
            }
            if (powtor < 1) {
                nazwa[q] = ZamowDay[i][id];
                q++;
            }
            powtor = 0;
        }
        return nazwa;
    }

    private void setData()
    {
        Log.d("Zapros", "Start");
        Cursor res = MyDb.getAllData(9);
        int num = res.getCount();
        ZamowDay = new String[num][3];
        int i = -1;
        Log.d("Zapros", "ZamowDay");
        if(res.moveToFirst()){
            do{
                i++;
                ZamowDay[i][0] = res.getString(1);
                ZamowDay[i][1] = String.valueOf(res.getInt(3));
                ZamowDay[i][2] = res.getString(4);
            }
            while(res.moveToNext());
        }
        Log.d("Zapros", "Close DB");
        res.close();
        MyDb.close();
        Log.d("Zapros", "WithoutDublicate");

        withoutDublicateCena = new String[ZamowDay.length];
        withoutDublicateNazwa = new String[ZamowDay.length];
        withoutDublicateTyp = new String[ZamowDay.length];
        Log.d("Zapros", "Empty. Get All Data: " + String.valueOf(ZamowDay.length));
        for (int q = 0; q < ZamowDay.length; q++)
        { withoutDublicateNazwa[q] =""; withoutDublicateCena[q] = ""; withoutDublicateTyp[q] = "";}

        Log.d("Zapros", "Create Without Dublicate");
        withoutDublicateNazwa = setDataWithoutDublikate(withoutDublicateNazwa, 0);
        withoutDublicateCena = setDataWithoutDublikate(withoutDublicateCena, 1);
        withoutDublicateTyp = setDataWithoutDublikate(withoutDublicateTyp, 2);


        //Log.d("Zapros", "Set buffer");
        //buffer = new StringBuffer();
        int countNazwa = 1, countCena = 1, countTyp = 1;

        for (int q = 0; q < withoutDublicateNazwa.length; q++)
            if(withoutDublicateNazwa[q] != "")
                countNazwa++;

        for (int q = 0; q< withoutDublicateCena.length; q++)
            if(withoutDublicateCena[q] != "")
                countCena++;

        for (int q = 0; q< withoutDublicateTyp.length; q++)
            if(withoutDublicateTyp[q] != "")
                countTyp++;

        WDNSpin = new String[countNazwa];
        WDCSpin = new String[countCena];
        WDTSpin = new String[countTyp];

        WDNSpin[0] = "Ничего";
        WDCSpin[0] = "Ничего";
        WDTSpin[0] = "Ничего";
    //----------------------------------------
        for (int q = 0; q < withoutDublicateNazwa.length; q++)
            if(withoutDublicateNazwa[q] != "")
                WDNSpin[q + 1] = withoutDublicateNazwa[q];

        for (int q = 0; q< withoutDublicateCena.length; q++)
            if(withoutDublicateCena[q] != "")
                WDCSpin[q + 1] = withoutDublicateCena[q];

        for (int q = 0; q< withoutDublicateTyp.length; q++)
            if(withoutDublicateTyp[q] != "")
                WDTSpin[q + 1] = withoutDublicateTyp[q];






        Log.d("Zapros", "Adapter");
        ArrayAdapter<String> adapterNazw = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, WDNSpin);
        adapterNazw.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNazw.setAdapter(adapterNazw);

        ArrayAdapter<String> adapterCen = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, WDCSpin);
        adapterCen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCena.setAdapter(adapterCen);

        ArrayAdapter<String> adapterTyp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, WDTSpin);
        adapterTyp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTyp.setAdapter(adapterTyp);
    }

    private void addListener()
    {
        spinKub = (Spinner)findViewById(R.id.spinnerKubek);
        spinPlat = (Spinner)findViewById(R.id.spinner2);
        spinNazw = (Spinner)findViewById(R.id.spinnerName);
        spinCena = (Spinner)findViewById(R.id.spinnerCena);
        spinTyp = (Spinner)findViewById(R.id.spinnerTyp);

        spinKub.setEnabled(false);
        spinPlat.setEnabled(false);
        spinNazw.setEnabled(false);
        spinCena.setEnabled(false);
        spinTyp.setEnabled(false);

        ArrayAdapter<String> adapterKub = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerCup);
        adapterKub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKub.setAdapter(adapterKub);

        ArrayAdapter<String> adapterPlat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerOpl);
        adapterPlat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPlat.setAdapter(adapterPlat);


        naz = (CheckBox)findViewById(R.id.checkBox3);
        cup = (CheckBox)findViewById(R.id.checkBox4);
        opl = (CheckBox)findViewById(R.id.checkBox6);
        cena = (CheckBox)findViewById(R.id.checkBox5);
        typ = (CheckBox)findViewById(R.id.checkBox7);

        day= (EditText)findViewById(R.id.denn);
        month= (EditText)findViewById(R.id.editText20);
        year= (EditText)findViewById(R.id.years);

        day.setText(dayNow);
        month.setText(monthNow);
        year.setText(yearNow);

        accept = (Button)findViewById(R.id.Acce);

        naz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(naz.isChecked())
                    spinNazw.setEnabled(true);
                else
                    spinNazw.setEnabled(false);
            }
        });
        cup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(cup.isChecked())
                            spinKub.setEnabled(true);
                        else
                            spinKub.setEnabled(false);
                }
                }
        );
        opl.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(opl.isChecked())
                            spinPlat.setEnabled(true);
                        else
                            spinPlat.setEnabled(false);
                    }
                }
        );
        cena.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(cena.isChecked())
                            spinCena.setEnabled(true);
                        else
                            spinCena.setEnabled(false);
                    }
                }
        );
        typ.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(typ.isChecked())
                            spinTyp.setEnabled(true);
                        else
                            spinTyp.setEnabled(false);
                    }
                }
        );
        accept.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sNazw = "", sCup = "", sCena = "", sOpl = "", sTyp = "";
                        int i = 1;
                        String [] nazwa = new String[6];
                        SqlMess = "Select ";
                        if(naz.isChecked()) {
                            SqlMess += " Name,";
                            nazwa[i-1] = "Название: ";
                            i++;
                            if(spinNazw.getSelectedItem().toString() != "Ничего")
                                sNazw = " Name = '" + spinNazw.getSelectedItem().toString() + "' AND ";
                        }
                        if(cup.isChecked()) {
                            SqlMess += " Cup,";
                            nazwa[i-1] = "Чашка: ";
                            i++;
                            if(spinKub.getSelectedItem().toString() != "Ничего")
                                sCup = " Cup = '" + spinKub.getSelectedItem().toString() + "' AND ";
                        }
                        if(cena.isChecked()) {
                            SqlMess += " Cena,";
                            nazwa[i-1] = "Цена: ";
                            i++;
                            if(spinCena.getSelectedItem().toString() != "Ничего")
                                sCena = " Cena = '" + spinCena.getSelectedItem().toString() + "' AND ";
                        }
                        if(opl.isChecked()) {
                            SqlMess += " CostGotowka,";
                            nazwa[i-1] = "Оплата: ";
                            i++;
                            if(spinPlat.getSelectedItem().toString() != "Ничего")
                                sOpl = " CostGotowka = '" + spinPlat.getSelectedItem().toString() + "' AND ";
                        }
                        if(typ.isChecked()) {
                            SqlMess += " Rodzaj,";
                            nazwa[i-1] = "Вид: ";
                            i++;
                            if(spinTyp.getSelectedItem().toString() != "Ничего")
                                sTyp = " Rodzaj = '" + spinTyp.getSelectedItem().toString() + "' AND ";
                        }
                        nazwa[i-1] = "Дата: ";
                        //"ZakazAll"
                        SqlMess += " Date from ZakazAll Where " + sNazw + sCup + sCena + sOpl + sTyp;
                        Log.d("ZaprosSQL", "Compile SqlMess");
                        SqlMess += " Date like '" + day.getText().toString() + " " +month.getText().toString() + ". " + year.getText().toString() + "%'";
                        //Toast.makeText(Zapros.this, SqlMess, Toast.LENGTH_LONG).show();


                        Intent inte = new Intent(Zapros.this, MonthData.class);
                        inte.putExtra("SQL", SqlMess);
                        inte.putExtra("CountSQL", String.valueOf(i));
                        inte.putExtra("TabSQL", nazwa);
                        startActivity(inte);
                    }
                }
        );
    }
}
