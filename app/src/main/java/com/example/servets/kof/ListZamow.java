package com.example.servets.kof;

import android.database.Cursor;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class ListZamow extends BaseActivity {
    DataBaseHelper MyDb;
    private BottomNavigationViewEx buttom;
    private ListView lv;
    private String [] nazwa;
    private String [] IdZakaz;
    private String [][] Data; //Name, Cup, Cena, Rodzaj, CostGotowka, Date
    private TextView suma;
    int num = -1;
    private Button btnAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_zamow);

        setupBottomNavigation(1);

        MyDb  = new DataBaseHelper(this);
        lv = (ListView)findViewById(R.id.ListZamow);
        suma = (TextView)findViewById(R.id.Summ);

        buttom = (BottomNavigationViewEx)findViewById(R.id.bottom_navigation_view);
        buttom.setIconSize(36,36);

        setData();
        listener();
    }

    public void listener()
    {
        btnAcc = (Button)findViewById(R.id.Acc);
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MyDb.deleteData(6, IdZakaz[i]);
                        setData();
                    }
                }
        );
        btnAcc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //if (num < 0) {
                                CheckBox cb = (CheckBox) findViewById(R.id.checkBox);
                                String karta;
                                if (cb.isChecked())
                                    karta = "Карта";
                                else
                                    karta = "Наличка";

                                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                                for (int i = 0; i < num; i++)
                                    MyDb.insertDataAllZamow(0,Data[0][i], Data[1][i], Integer.parseInt(Data[2][i]), Data[3][i], karta, mydate);

                                MyDb.DropTableAllZaow(0);

                                setData();
                            //}
                            //else
                                //Toast.makeText(ListZamow.this, "Empty", Toast.LENGTH_LONG).show();
                        }
                    catch(Exception e){}
                    }
                }
        );
    }

    public void setData()
    {
        int cen = 0;
        Cursor res = MyDb.getAllDataZamow(1);
        num = MyDb.getCountData(6);

        nazwa = new String[num];
        IdZakaz = new String[num];

        Data = new String[4][num];

        int i = -1;
        if(res.moveToFirst()){
            do{
                i++;
                nazwa[i] = "Название: " + res.getString(1) +" Чашка: "+ res.getString(2) +" Цена: " + res.getInt(3) + " Тип: " + res.getString(4) ;
                IdZakaz[i] = res.getString(0);

                Data[0][i] = res.getString(1);
                Data[1][i] = res.getString(2);
                Data[2][i] = res.getString(3);
                Data[3][i] = res.getString(4);

                cen += res.getInt(3);
            }
            while(res.moveToNext());
        }
        res.close();
        MyDb.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nazwa);

        lv.setAdapter(adapter);
        suma.setText(String.valueOf(cen));
    }
}
