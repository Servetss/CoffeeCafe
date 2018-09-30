package com.example.servets.kof;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import static com.example.servets.kof.DataBaseHelper.TABLE_NAME;

public class MainActivity extends BaseActivity{
    DataBaseHelper MyDb;

    //private DatabaseReference dbRef;
    private FirebaseDatabase dbFire;

    public Cursor res;
    private BottomNavigationViewEx buttom;

    public String [][] Kofe;
    public String [][] Teaa;
    public String [][] Ice;
    public String [][] Other;
    public String [][] Dod;

    public String [] idKofe;
    public String [] idTea;
    public String [] idIce;
    public String [] idOther;
    public String [] idDod;

    public String [][] id; // название вниз, вправо счёт

    private String [] towar = new String[] {"Cofee", "Tea", "Ice", "Other", "Additional"};

    public GridView gridView;

    private RadioGroup rg;
    private RadioButton rb;
    public int radioId;
    public int idSlot = 1;
    private boolean synchronizing = false;

    private TextView textV;

    final int menu_alkaszka_50=1;
    final int menu_alkaszka_100=2;
    final int menu_alkaszka_150=3;
    final int menu_alkaszka_500=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDb = new DataBaseHelper(this);

        //Log.d("DB", "Reference: " + dbFire.toString());
            dbFire = FirebaseDatabase.getInstance();
            try {
            dbFire.setPersistenceEnabled(true);}
            catch (Exception e){}
            //dbRef = dbFire.getReference();


        rg = (RadioGroup)findViewById(R.id.rGroup);
        rb = (RadioButton)findViewById(R.id.KofR);
        textV = (TextView) findViewById(R.id.wodka);
        rb.setMaxLines(1);

        rb.setChecked(true);

        setupBottomNavigation(0);
        buttom = (BottomNavigationViewEx)findViewById(R.id.bottom_navigation_view);
        buttom.setIconSize(36,36);

        Data();

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this, Kofe));

        ClickDataGride();

        textV= findViewById(R.id.wodka);


        registerForContextMenu(textV);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.resmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_item3:
                Cursor res = MyDb.getAllData(7);
                String writepass = null;
                if (res.moveToFirst()) {
                    do {
                        writepass = res.getString(6);
                    }
                    while (res.moveToNext());
                }
                if(writepass != null) {
                    Intent intent = new Intent(MainActivity.this, AddToDB.class);
                    startActivity(intent);
                }
                else Toast.makeText(MainActivity.this, "Для этой функции нужны права администратора", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings:
                Intent in = new Intent(MainActivity.this, Settings.class);
                startActivity(in);
                break;
            case R.id.action_exit:
                AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                a_builder.setMessage("Вы хотите закрыть программу?")
                        .setCancelable(false)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener(){
                                    @Override
                                    public  void onClick(DialogInterface dialog, int which){finishAffinity();}
                                }
                        )
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();//отрисовка диалогового окна
                alert.setTitle("Выход");
                alert.show();

                break;
                case R.id.action_item4:
                    SynchronizeData();
                    break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()){
            case R.id.wodka:
                menu.add(0, menu_alkaszka_50, 0, "50");
                menu.add(0, menu_alkaszka_100, 0, "100");
                menu.add(0, menu_alkaszka_150, 0, "150");
                menu.add(0, menu_alkaszka_500, 0, "500");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case menu_alkaszka_50:
                textV.setText(textV.getText().toString() + " 50ml");
                break;
            case menu_alkaszka_100:
                textV.setText(textV.getText().toString() + " 100ml");
                break;
            case menu_alkaszka_150:
                textV.setText(textV.getText().toString() + " 150ml");
                break;
            case menu_alkaszka_500:
                textV.setText(textV.getText().toString() + " 500ml");
                break;
        }
        return super.onContextItemSelected(item);
    }


    public void rbcClick(View v)
    {
        radioId = rg.getCheckedRadioButtonId();
        rb = (RadioButton)findViewById(radioId);

        if(rb == (RadioButton)findViewById(R.id.KofR))
        {
            idSlot = 1;
            Data();
            gridView.setAdapter(new ImageAdapter(this, Kofe));
        }
        else if(rb == (RadioButton)findViewById(R.id.TeaR))
        {
            idSlot = 2;
            Data();
            gridView.setAdapter(new ImageAdapter(this, Teaa));
        }
        else if(rb == (RadioButton)findViewById(R.id.IceR))
        {
            idSlot = 3;
            Data();
            gridView.setAdapter(new ImageAdapter(this, Ice));
        }
        else if(rb == (RadioButton)findViewById(R.id.OthR))
        {
            idSlot = 4;
            Data();
            gridView.setAdapter(new ImageAdapter(this, Other));
        }
        else if(rb == (RadioButton)findViewById(R.id.AddiR))
        {
            idSlot = 5;
            Data();
            gridView.setAdapter(new ImageAdapter(this, Dod));
        }
    }


    public void ClickDataGride()
    {
        try {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int count = -1;
                    int idCount = -1;
                    String cup = "";
                    switch (idSlot) {
                        case 1:
                            for (int q = 1; q < 7; q++)
                                if (Integer.parseInt(Kofe[i][q]) > 0) {
                                    count++;
                                    idCount = q;
                                }
                            if (count > 0) {
                                Intent intent = new Intent(MainActivity.this, SecondA.class);
                                intent.putExtra("name", Kofe[i][0]);
                                intent.putExtra("110", Kofe[i][1]);
                                intent.putExtra("175", Kofe[i][2]);
                                intent.putExtra("250", Kofe[i][3]);
                                intent.putExtra("320", Kofe[i][4]);
                                intent.putExtra("400", Kofe[i][5]);
                                intent.putExtra("500", Kofe[i][6]);
                                startActivity(intent);
                            } else {
                                switch (idCount) {
                                    case 1:
                                        cup = "110";
                                        break;
                                    case 2:
                                        cup = "175";
                                        break;
                                    case 3:
                                        cup = "250";
                                        break;
                                    case 4:
                                        cup = "320";
                                        break;
                                    case 5:
                                        cup = "400";
                                        break;
                                    case 6:
                                        cup = "500";
                                        break;
                                    default:
                                        break;
                                }
                                boolean isInserted = MyDb.insertDataZamow(Kofe[i][0], cup, Integer.parseInt(Kofe[i][idCount]), "0");
                                if (isInserted == true)
                                    Toast.makeText(MainActivity.this, "Добавлено", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 2:
                            setDataInBase(Teaa[i][0], "Чайная чашка", Integer.parseInt(Teaa[i][2]), Teaa[i][1]);
                            break;
                        case 3:
                            setDataInBase(Ice[i][0], "Пластик", Integer.parseInt(Ice[i][2]), Ice[i][1]);
                            break;
                        case 4:
                            setDataInBase(Other[i][0], "-", Integer.parseInt(Other[i][1]), "-");
                            break;
                        case 5:
                            setDataInBase(Dod[i][0], "-", Integer.parseInt(Dod[i][1]), "-");
                            break;
                        default:
                            break;
                    }
                }
            });
        }
        catch (Exception e){Toast.makeText(MainActivity.this, "Плохо записаны данные, обновите данные этого продукта", Toast.LENGTH_LONG).show();}
    }

    public void setDataInBase(String nazwaDrink, String cupSize, int cenaDrink, String rodzajDrink)
    {
        boolean isInserted = MyDb.insertDataZamow(nazwaDrink, cupSize, cenaDrink, rodzajDrink);
        if(isInserted == true)
            Toast.makeText(MainActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
    }

   public void Data()
    {
        res = MyDb.getAllData(idSlot);
        int num = MyDb.getCountData(idSlot);
        switch (idSlot)
        {
            case 1:
                Kofe = new String[num][7];
                idKofe = new String[num];
                break;
            case 2:
                Teaa = new String[num][3];
                idTea= new String[num];
                break;
            case 3:
                Ice = new String[num][3];
                idIce= new String[num];
                break;
            case 4:
                Other = new String[num][2];
                idOther= new String[num];
                break;
            case 5:
                Dod = new String[num][2];
                idDod= new String[num];
                break;
                default:
                    break;
        }
        int  i = -1;

        if(res.moveToFirst()){
            do{
                i++;
                switch (idSlot)
                {
                    case 1:
                        idKofe[i] = res.getString(0);
                        Kofe[i][0] = res.getString(1);
                        Kofe[i][1] = res.getString(2);
                        Kofe[i][2] = res.getString(3);
                        Kofe[i][3] = res.getString(4);
                        Kofe[i][4] = res.getString(5);
                        Kofe[i][5] = res.getString(6);
                        Kofe[i][6] = res.getString(7);
                        break;
                    case 2:
                        idTea[i] = res.getString(0);
                        Teaa[i][0] = res.getString(1);
                        Teaa[i][1] = res.getString(2);
                        Teaa[i][2] = res.getString(3);
                        break;
                    case 3:
                        idIce[i] = res.getString(0);
                        Ice[i][0] = res.getString(1);
                        Ice[i][1] = res.getString(2);
                        Ice[i][2] = res.getString(3);
                        break;
                    case 4:
                        idOther[i] = res.getString(0);
                        Other[i][0] = res.getString(1);
                        Other[i][1] = res.getString(2);
                        break;
                    case 5:
                        idDod[i] = res.getString(0);
                        Dod[i][0] = res.getString(1);
                        Dod[i][1] = res.getString(2);
                        break;
                        default:
                            break;
                }
            }
            while(res.moveToNext());
        }
        res.close();
        MyDb.close();
    }


    public void showMessage(String title, String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    private void SynchronizeData()
    {
        dbFire.getReference().addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!synchronizing) {
                    synchronizing = true;
                    try {
                        String nam = "";
                        String nam2 = "";
                        String[] cupSizeName = new String[]{"0", "1", "2", "3", "4", "5"};

                        int[] poz = new int[Kofe.length];
                        int rach = -1;

                        int[] cupSize = new int[6];
                        int i = 0;
                        int count = -1;
                        boolean emp = false;

                        //Количество дочерних элементов
                        count = (int) dataSnapshot.child(towar[idSlot - 1]).getChildrenCount();

                        MyDb.deleteAll();

                        Log.d("DB", "START");
                        for (int w = 0; w < towar.length; w++) {
                            Log.d("DB", "W count: " + String.valueOf(w) + "  Towar: " + towar[w]);
                            for (DataSnapshot childDataSnapshot : dataSnapshot.child(towar[w]).getChildren()) {
                                Log.d("DB", "     ");
                                Log.d("DB", "------------------------------------------");

                                nam = childDataSnapshot.getKey();
                                //Log.d("DB", "Name: " + nam);

                                //Запись цен в массив с firebase
                                if (w == 0) {
                                    for (int q = 0; q < cupSize.length; q++)
                                        cupSize[q] = Integer.valueOf(dataSnapshot.child(towar[w]).child(nam).child(cupSizeName[q]).getValue().toString());
                                    MyDb.insertData(1, nam, "", "", cupSize[0], cupSize[1], cupSize[2], cupSize[3], cupSize[4], cupSize[5]);
                                    Log.d("DB", "Add: " + nam + " Cup: " + cupSize[0] + " " + cupSize[1] + " " + cupSize[2] + " " + cupSize[3] + " " + cupSize[4] + " " + cupSize[5]);
                                } else if (w == 1 || w == 2) {
                                    String type = dataSnapshot.child(towar[w]).child(nam).child("Kind").getValue().toString();
                                    String cena1 = dataSnapshot.child(towar[w]).child(nam).child("Cena").getValue().toString();
                                    MyDb.insertData(w + 1, nam, type, cena1, 0, 0, 0, 0, 0, 0);
                                    Log.d("DB", "Add: " + nam + " Kind: " + type + " Cena: " + cena1);
                                } else if (w == 3 || w == 4) {
                                    String cena1 = dataSnapshot.child(towar[w]).child(nam).child("Cena").getValue().toString();
                                    MyDb.insertData(w + 1, nam, "", cena1, 0, 0, 0, 0, 0, 0);
                                    Log.d("DB", "Add: " + nam + " Cena: " + cena1);
                                }


                                //Log.d("DB", "Name: " + nam);




                        /*//Если в firebase записей больше или равно локольным данным
                        if (count >= Kofe.length) {
                            Log.d("DB", "Данных больше или равно");
                            //Если не пустой массив
                            if (Kofe.length != 0 && emp == false) {
                                Log.d("DB", "Массив *Кофе* не равняется нулю");
                                for (int c = 0; c < Kofe.length; c++) {
                                    //Если уже существуют такие данные от проверь что внутри и измени
                                    if (Kofe[c][0].equals(nam)) {
                                        Log.d("DB", "Шашло в локальной базе такое же имя");
                                        for (int e = 0; e < cupSize.length; e++) {
                                            if (!Kofe[c][e + 1].equals(String.valueOf(cupSize[e]))) {
                                                Log.d("DB", "Обновляет данные");
                                                MyDb.updateData(1, idKofe[c], nam, "", "", String.valueOf(cupSize[0]), String.valueOf(cupSize[1]), String.valueOf(cupSize[2]), String.valueOf(cupSize[3]), String.valueOf(cupSize[4]), String.valueOf(cupSize[5]));
                                            }
                                            }
                                        break;
                                    } else if (Kofe.length - 1 == c) {
                                        Log.d("DB", "Добавляет данные; " + nam);
                                        MyDb.insertData(1, nam, "", "", cupSize[0], cupSize[1], cupSize[2], cupSize[3], cupSize[4], cupSize[5]);
                                    }
                                }
                            } else {
                                Log.d("DB", "Массив *Кофе* равняется нулю");
                                emp = true;
                                MyDb.insertData(1, nam, "", "", cupSize[0], cupSize[1], cupSize[2], cupSize[3], cupSize[4], cupSize[5]);
                            }

                            i++;
                        } else {
                            Log.d("DB", "Данных меньше");
                            for (int c = 0; c < Kofe.length; c++) {
                                if (Kofe[c][0].equals(nam)) {
                                    rach++;
                                    poz[rach] = Integer.valueOf(idKofe[c]);
                                }
                            }
                            for (int cc = 0; cc < idKofe.length; cc++) {
                                for (int ccx = 0; ccx < poz.length; ccx++) {
                                    if (Integer.valueOf(idKofe[cc]) == poz[ccx]) {
                                        break;
                                    } else if (ccx == poz.length - 1) {
                                        Log.d("DB", "Удаляет из списка: " + Kofe[Integer.valueOf(idKofe[cc])][0] + " ID: " + String.valueOf(idKofe[cc]));
                                        MyDb.deleteData(idSlot, String.valueOf(idKofe[cc]));
                                    }
                                }
                            }
                        }*/
                            }
                        }

                        Data();
                        if(idSlot == 1)
                            gridView.setAdapter(new ImageAdapter(MainActivity.this, Kofe));
                        else if(idSlot == 2)
                            gridView.setAdapter(new ImageAdapter(MainActivity.this, Teaa));
                        else if(idSlot == 3)
                            gridView.setAdapter(new ImageAdapter(MainActivity.this, Ice));
                        else if(idSlot == 4)
                            gridView.setAdapter(new ImageAdapter(MainActivity.this, Other));
                        else if(idSlot == 5)
                            gridView.setAdapter(new ImageAdapter(MainActivity.this, Dod));

                        emp = false;
                        Toast.makeText(MainActivity.this, "Обновленно", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Ошибка синхронизации", Toast.LENGTH_LONG).show();
                    }
                    synchronizing = false;
                }
            }


            public void onCancelled(DatabaseError databaseError) {

            }
            });
        }



    public class ImageAdapter extends BaseAdapter{
        private Context mContext;
        private String [][] matrix;

        public ImageAdapter(Context c, String[][] mat)
        { mContext = c; matrix = mat;}

        public int getCount()
        {
            int lengh = matrix.length;
            return lengh;
        }

        @Override
        public Object getItem(int position)
        { return null; }

        public long getItemId(int position)
        {return 0; }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            TextView Label = new TextView(mContext);
            switch (idSlot)
            {
                case 1:
                    Label.setBackgroundResource(R.drawable.kofee);
                    Label.setLayoutParams(new GridView.LayoutParams(150,200));
                    break;
                case 2:
                    Label.setBackgroundResource(R.drawable.tea);
                    Label.setLayoutParams(new GridView.LayoutParams(150,200));
                    break;
                case 3:
                    Label.setBackgroundResource(R.drawable.ice);
                    Label.setLayoutParams(new GridView.LayoutParams(150,200));
                    break;
                case 4:
                    Label.setBackgroundResource(R.drawable.othr);
                    Label.setLayoutParams(new GridView.LayoutParams(175,200));
                    break;
                case 5:
                    Label.setBackgroundResource(R.drawable.dod);
                    Label.setLayoutParams(new GridView.LayoutParams(150,200));
                    break;
            }
            Label.setText("\n" + matrix[position][0]);
            Label.setTextSize(13);
            Label.setTypeface(Label.getTypeface(), Typeface.BOLD);
            Label.setGravity(Gravity.CENTER);
            Label.setTextColor(Color.rgb(0,0,0));
            Label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            return Label;
        }

    }
}
