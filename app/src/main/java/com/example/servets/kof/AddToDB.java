package com.example.servets.kof;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddToDB extends AppCompatActivity {
    DataBaseHelper MyDb;
    public Cursor res;

    //private DatabaseReference dbRef;
    private FirebaseDatabase dbFire;

    Button btn, viewData, UpdateBtn, DeleteBtn;
    private RadioGroup rg;
    private RadioButton rb;
    private int radioId;
    private int idSlot = 1;
    private String [] idSelected;

    public String [][] Kofe;
    public String [][] Teaa;
    public String [][] Ice;
    public String [][] Other;
    public String [][] Dod;

    private String [] towar = new String[] {"Cofee", "Tea", "Ice", "Other", "Additional"};

    private EditText name, rodzaj, id, cena, one, two, three, four, five, six;
    private CheckBox avtor;
    private Spinner spinnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_db);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MyDb = new DataBaseHelper(this);

            dbFire = FirebaseDatabase.getInstance();
            try{
                dbFire.setPersistenceEnabled(true);}
            catch (Exception e) {}
            //dbRef = dbFire.getReference();

        name = (EditText)findViewById(R.id.editText2);
        rodzaj = (EditText)findViewById(R.id.editText);
        one = (EditText)findViewById(R.id.editText5);
        two = (EditText)findViewById(R.id.editText6);
        three = (EditText)findViewById(R.id.editText7);
        four = (EditText)findViewById(R.id.editText8);
        five = (EditText)findViewById(R.id.editText9);
        six = (EditText)findViewById(R.id.editText10);
        cena = (EditText)findViewById(R.id.editText3);

        avtor = (CheckBox)findViewById(R.id.checkBox2);
        spinnerId = (Spinner)findViewById(R.id.spinnerID);

        rodzaj.setEnabled(false);
        cena.setEnabled(false);
        avtor.setEnabled(false);

        rg = (RadioGroup)findViewById(R.id.rGroupe);
        setData();
        addListenerOnButton();
        editTextChange();
        /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
    }


    public void rbcClick(View v)
    {
        radioId = rg.getCheckedRadioButtonId();
        rb = (RadioButton)findViewById(radioId);

        if(rb == (RadioButton)findViewById(R.id.radioButton4))
        {
            idSlot = 1;
            rodzaj.setEnabled(false);
            cena.setEnabled(false);
            avtor.setEnabled(false);
            setRadioActFalse(true);
            setData();
        }
        else if(rb == (RadioButton)findViewById(R.id.radioButton5))
        {
            idSlot = 2;
            rodzaj.setEnabled(true);
            cena.setEnabled(true);
            avtor.setEnabled(true);

            setRadioActFalse(false);
            setData();
        }
        else if(rb == (RadioButton)findViewById(R.id.radioButton6))
        {
            idSlot = 3;
            rodzaj.setEnabled(true);
            cena.setEnabled(true);
            avtor.setEnabled(true);

            setRadioActFalse(false);
            setData();
        }
        else if(rb == (RadioButton)findViewById(R.id.radioButton7))
        {
            idSlot = 4;
            cena.setEnabled(true);
            rodzaj.setEnabled(false);
            avtor.setEnabled(false);
            setRadioActFalse(false);
            setData();
        }
        else if(rb == (RadioButton)findViewById(R.id.radioButton8))
        {
            idSlot = 5;
            cena.setEnabled(true);
            rodzaj.setEnabled(false);
            avtor.setEnabled(false);
            setRadioActFalse(false);
            setData();
        }
    }

    private void setRadioActFalse(boolean a)
    {
        one.setEnabled(a);
        two.setEnabled(a);
        three.setEnabled(a);
        four.setEnabled(a);
        five.setEnabled(a);
        six.setEnabled(a);
    }


    private void setData()
    {
        res = MyDb.getAllData(idSlot);
        int num = MyDb.getCountData(idSlot);
        int maxCount = 0;
        switch (idSlot)
        {
            case 1:
                Kofe = new String[num][8];
                break;
            case 2:
                Teaa = new String[num][4];
                break;
            case 3:
                Ice = new String[num][4];
                break;
            case 4:
                Other = new String[num][3];
                break;
            case 5:
                Dod = new String[num][3];
                break;
            default:
                break;
        }

        int i = -1;
        if(res.moveToFirst()){
            do{
                i++;
                switch (idSlot)
                {
                    case 1:
                        Kofe[i][0] = res.getString(0);
                        Kofe[i][1] = res.getString(1);
                        Kofe[i][2] = res.getString(2);
                        Kofe[i][3] = res.getString(3);
                        Kofe[i][4] = res.getString(4);
                        Kofe[i][5] = res.getString(5);
                        Kofe[i][6] = res.getString(6);
                        Kofe[i][7] = res.getString(7);
                        maxCount = Kofe.length;
                        break;
                    case 2:
                        Teaa[i][0] = res.getString(0);
                        Teaa[i][1] = res.getString(1);
                        Teaa[i][2] = res.getString(2);
                        Teaa[i][3] = res.getString(3);
                        maxCount = Teaa.length;
                        break;
                    case 3:
                        Ice[i][0] = res.getString(0);
                        Ice[i][1] = res.getString(1);
                        Ice[i][2] = res.getString(2);
                        Ice[i][3] = res.getString(3);
                        maxCount = Ice.length;
                        break;
                    case 4:
                        Other[i][0] = res.getString(0);
                        Other[i][1] = res.getString(1);
                        Other[i][2] = res.getString(2);
                        maxCount = Other.length;
                        break;
                    case 5:
                        Dod[i][0] = res.getString(0);
                        Dod[i][1] = res.getString(1);
                        Dod[i][2] = res.getString(2);
                        maxCount = Dod.length;
                        break;
                    default:
                        break;
                }
            }
            while(res.moveToNext());
        }
        idSelected = new String[maxCount];
        for(int zxc = 0; zxc < maxCount; zxc++ )
        {
            if(idSlot == 1)
                idSelected[zxc] = Kofe[zxc][0];
            else if(idSlot == 2)
                idSelected[zxc] = Teaa[zxc][0];
            else if(idSlot == 3)
                idSelected[zxc] = Ice[zxc][0];
            else if(idSlot == 4)
                idSelected[zxc] = Other[zxc][0];
            else if(idSlot == 5)
                idSelected[zxc] = Dod[zxc][0];
        }
        ArrayAdapter<String> adapterNazw = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, idSelected);
        adapterNazw.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerId.setAdapter(adapterNazw);



        res.close();
        MyDb.close();
    }

    private void editTextChange()
    {
        Log.d("Spinner", "Start");
        spinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Spinner", "Into the public");
                try {
                    int position = -1;

                    switch (idSlot) {
                        case 1:
                            for (int w = 0; w < Kofe.length; w++)
                                if (spinnerId.getSelectedItem().toString().equals(Kofe[w][0]))
                                    position = w;

                            name.setText(Kofe[position][1]);
                            one.setText(Kofe[position][2]);
                            two.setText(Kofe[position][3]);
                            three.setText(Kofe[position][4]);
                            four.setText(Kofe[position][5]);
                            five.setText(Kofe[position][6]);
                            six.setText(Kofe[position][7]);
                            break;
                        case 2:
                            for (int w = 0; w < Teaa.length; w++)
                                if (spinnerId.getSelectedItem().toString().equals(Teaa[w][0]))
                                    position = w;

                            name.setText(Teaa[position][1]);
                            rodzaj.setText(Teaa[position][2]);
                            cena.setText(Teaa[position][3]);
                            break;
                        case 3:
                            for (int w = 0; w < Ice.length; w++)
                                if (spinnerId.getSelectedItem().toString().equals(Ice[w][0]))
                                    position = w;

                            name.setText(Ice[position][1]);
                            rodzaj.setText(Ice[position][2]);
                            cena.setText(Ice[position][3]);
                            break;
                        case 4:
                            for (int w = 0; w < Other.length; w++)
                                if (spinnerId.getSelectedItem().toString().equals(Other[w][0]))
                                    position = w;

                            name.setText(Other[position][1]);
                            cena.setText(Other[position][2]);
                            break;
                        case 5:
                            for (int w = 0; w < Dod.length; w++)
                                if (spinnerId.getSelectedItem().toString().equals(Dod[w][0]))
                                    position = w;

                            name.setText(Dod[position][1]);
                            cena.setText(Dod[position][2]);
                            break;

                        default:
                            break;
                    }
                }
                catch (Exception e) {}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addListenerOnButton()
    {
        btn = (Button)findViewById(R.id.add);
        viewData = (Button)findViewById(R.id.ViewData);
        DeleteBtn = (Button)findViewById(R.id.Delete);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if(name.getText().toString().isEmpty() || (idSlot != 1 && cena.getText().toString().isEmpty()) || (((idSlot == 2 || idSlot == 3) && rodzaj.getText().toString().isEmpty()) && avtor.isChecked() == false))
                            {
                                Toast.makeText(AddToDB.this, "Заполни пустые поля", Toast.LENGTH_LONG).show();
                            }
                            else {
                                String[] cen = new String[7];
                                String R = "";

                                if(one.getText().toString().isEmpty())
                                    one.setText("0");
                                if(two.getText().toString().isEmpty())
                                    two.setText("0");
                                if(three.getText().toString().isEmpty())
                                    three.setText("0");
                                if(four.getText().toString().isEmpty())
                                    four.setText("0");
                                if(five.getText().toString().isEmpty())
                                    five.setText("0");
                                if(six.getText().toString().isEmpty())
                                    six.setText("0");
                                if(cena.getText().toString().isEmpty())
                                    cena.setText("0");


                                cen[0] = one.getText().toString();
                                cen[1] = two.getText().toString();
                                cen[2] = three.getText().toString();
                                cen[3] = four.getText().toString();
                                cen[4] = five.getText().toString();
                                cen[5] = six.getText().toString();
                                cen[6] = cena.getText().toString();


                                for (int i = 0; i < 6; i++)
                                    if (cen[i] == "0" || cen[i] == "" || cen[i] == null)
                                        cen[i] = "0";

                                if (avtor.isChecked())
                                    R = "Авторское";
                                else R = rodzaj.getText().toString();

                                    boolean isInserted = MyDb.insertData(idSlot, name.getText().toString(), R, cen[6], Integer.parseInt(cen[0]), Integer.parseInt(cen[1]), Integer.parseInt(cen[2]), Integer.parseInt(cen[3]), Integer.parseInt(cen[4]), Integer.parseInt(cen[5]));
                                    addToFirebase(R);
                                    if (isInserted == true)
                                        Toast.makeText(AddToDB.this, "Успешно добавлено", Toast.LENGTH_SHORT).show();
                                    else {
                                        Toast.makeText(AddToDB.this, "Ошибка", Toast.LENGTH_SHORT).show();
                                    }
                            }
                        }catch (Exception e){Toast.makeText(AddToDB.this, String.valueOf(e),Toast.LENGTH_LONG).show();}
                    }
                }
        );

        viewData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Cursor res = MyDb.getAllData(idSlot);
                            if (res.getCount() == 0) {
                                showMessage("Ошибка", "Ничего не найдено");
                                return;
                            }
                            StringBuffer buffer = new StringBuffer();

                            switch (idSlot) {
                                case 1:
                                    while (res.moveToNext()) {
                                        buffer.append("Id: " + res.getString(0) + "\n");
                                        buffer.append("Кофе: " + res.getString(1) + "\n");
                                        buffer.append("Цена за 110: " + res.getString(2) + "\n");
                                        buffer.append("Цена за 175: " + res.getString(3) + "\n");
                                        buffer.append("Цена за 250: " + res.getString(4) + "\n");
                                        buffer.append("Цена за 320: " + res.getString(5) + "\n");
                                        buffer.append("Цена за 400: " + res.getString(6) + "\n");
                                        buffer.append("Цена за 500: " + res.getString(7) + "\n\n");
                                    }
                                    break;
                                case 2:
                                    while (res.moveToNext()) {
                                        buffer.append("Id: " + res.getString(0) + "\n");
                                        buffer.append("Чай: " + res.getString(1) + "\n");
                                        buffer.append("Тип: " + res.getString(2) + "\n");
                                        buffer.append("Цена: " + res.getString(3) + "\n\n");
                                    }
                                    break;
                                case 3:
                                    while (res.moveToNext()) {
                                        buffer.append("Id: " + res.getString(0) + "\n");
                                        buffer.append("Холодные: " + res.getString(1) + "\n");
                                        buffer.append("Тип: " + res.getString(2) + "\n");
                                        buffer.append("Цена: " + res.getString(3) + "\n\n");
                                    }
                                    break;
                                case 4:
                                    while (res.moveToNext()) {
                                        buffer.append("Id: " + res.getString(0) + "\n");
                                        buffer.append("Другое: " + res.getString(1) + "\n");
                                        buffer.append("Цена: " + res.getString(2) + "\n\n");
                                    }
                                    break;
                                case 5:
                                    while (res.moveToNext()) {
                                        buffer.append("Id: " + res.getString(0) + "\n");
                                        buffer.append("Добавки: " + res.getString(1) + "\n");
                                        buffer.append("Цена: " + res.getString(2) + "\n\n");
                                    }
                                    break;
                                default:
                                    Toast.makeText(AddToDB.this, "Ошибка", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            showMessage("Данные", buffer.toString());
                        }
                        catch (Exception e){Toast.makeText(AddToDB.this, "ERROR!", Toast.LENGTH_LONG).show();}
                    }
                }
        );
        DeleteBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {


                            AlertDialog.Builder a_builder = new AlertDialog.Builder(AddToDB.this);
                            a_builder.setMessage("Вы точно ходите удалить хотите удалить " + name.getText().toString() +"?")
                                    .setCancelable(false)
                                    .setPositiveButton("Да", new DialogInterface.OnClickListener(){
                                                @Override
                                                public  void onClick(DialogInterface dialog, int which){
                                                    dbFire.getReference().child(towar[idSlot - 1]).child(name.getText().toString()).setValue(null);
                                                    int deleteRows = MyDb.deleteData(idSlot, spinnerId.getSelectedItem().toString());
                                                    if (deleteRows > 0)
                                                        Toast.makeText(AddToDB.this, "Успешно удалено", Toast.LENGTH_SHORT).show();
                                                    else
                                                        Toast.makeText(AddToDB.this, "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                                                    setData();
                                                }
                                            }
                                    )
                                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = a_builder.create();//отрисовка диалогового окна
                            alert.setTitle("Удалить");
                            alert.show();


                        }catch (Exception e){Toast.makeText(AddToDB.this, String.valueOf(e), Toast.LENGTH_LONG).show();}
                    }
                }
        );
    }




    public void showMessage(String title, String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void addToFirebase(final String rod)
    {
        dbFire.getReference().child(towar[idSlot - 1]).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                switch (idSlot) {
                    case 1:
                        String[] names = {one.getText().toString(), two.getText().toString(), three.getText().toString(), four.getText().toString(), five.getText().toString(), six.getText().toString()};
                        List nameList = new ArrayList<String>(Arrays.asList(names));
                        dbFire.getReference().child(towar[idSlot - 1]).child(name.getText().toString()).setValue(nameList);
                        break;
                    case 2:
                        dbFire.getReference().child(towar[idSlot - 1]).child(name.getText().toString()).child("Kind").setValue(rod);
                        dbFire.getReference().child(towar[idSlot - 1]).child(name.getText().toString()).child("Cena").setValue(cena.getText().toString());
                        break;
                    case 3:
                        dbFire.getReference().child(towar[idSlot - 1]).child(name.getText().toString()).child("Kind").setValue(rod);
                        dbFire.getReference().child(towar[idSlot - 1]).child(name.getText().toString()).child("Cena").setValue(cena.getText().toString());
                        break;
                    case 4:
                        dbFire.getReference().child(towar[idSlot - 1]).child(name.getText().toString()).child("Cena").setValue(cena.getText().toString());
                        break;
                    case 5:
                        dbFire.getReference().child(towar[idSlot - 1]).child(name.getText().toString()).child("Cena").setValue(cena.getText().toString());
                        break;
                    default:
                        break;
                }
                setData();
            }

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
