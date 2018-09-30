package com.example.servets.kof;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQuery;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MonthData extends BaseActivity {
    private DataBaseHelper MyDb;
    private ListView lv;
    private int num = -1, SQLcount = 0;
    private String nazwa[], SQlQuery = "", SQLc = "", SQLTab[];
    private BottomNavigationViewEx bottom;
    private boolean zapros = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_data);

        Intent inte = getIntent();
        SQlQuery = inte.getStringExtra("SQL");
        SQLc = inte.getStringExtra("CountSQL");
        SQLTab = inte.getStringArrayExtra("TabSQL");
        if(SQlQuery != null) {
            zapros = true;
            SQLcount = Integer.valueOf(SQLc);
        }
        setupBottomNavigation(4);
        MyDb = new DataBaseHelper(this);
        lv = (ListView)findViewById(R.id.dyn);

        bottom = (BottomNavigationViewEx)findViewById(R.id.bottom_navigation_view);
        bottom.setIconSize(36,36);

        setData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_data, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all:
                AlertDialog.Builder a_builder = new AlertDialog.Builder(MonthData.this);
                a_builder.setMessage("Вы хотите безвозвратно удалить все данные?")
                        .setCancelable(false)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener(){
                                    @Override
                                    public  void onClick(DialogInterface dialog, int which){MyDb.DropAllData();
                                        setData();

                                        Toast.makeText(MonthData.this, "Удалено", Toast.LENGTH_LONG).show();}
                                }
                        )
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Удалить");
                alert.show();

                break;
            case R.id.action_view_all:
                zapros = false;
                setData();
                Toast.makeText(MonthData.this, "Запрос обновлен", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_query:
                Intent inten = new Intent(MonthData.this, Zapros.class);
                startActivity(inten);
                break;
        }
        return true;
    }

    private void setData()
    {
        Cursor res = null;
        int co = -1;
        if(zapros) {
            res = MyDb.getQuery(SQlQuery);
            num = res.getCount();
        }
        else {
            res = MyDb.getAllData(9);
            num = MyDb.getCountData(9);
        }



        nazwa = new String[num];
        int i = -1;
        if(res.moveToFirst()){
            do{
                i++;
                if(zapros)
                {
                    nazwa[i] = "";
                    for(int q = 0; q < SQLcount;q++)
                    {
                        nazwa[i] += SQLTab[q] + res.getString(q) + " ";
                    }
                }
                else
                    nazwa[i] = "Название: " + res.getString(1) + " Чашка: " + res.getString(2) + " Цена: " + String.valueOf(res.getInt(3)) + " Тип: " + res.getString(4) + " Форма оплаты: " + res.getString(5) + " Дата: " + res.getString(6);
                }
            while(res.moveToNext());
        }
        res.close();
        MyDb.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nazwa);

        lv.setAdapter(adapter);
    }

}
