package com.example.servets.kof;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

public class Settings extends AppCompatActivity {
    DataBaseHelper MyDb;
    private FirebaseDatabase dbFire;

    private Button accept, smien;
    private EditText em, staw, name;
    final Context context = this;

    private String timeInDb, resultNum, Email, stawka, pass, writepass, nameEmpl;

    private Switch admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MyDb = new DataBaseHelper(this);
        dbFire = FirebaseDatabase.getInstance();
        try{
            dbFire.setPersistenceEnabled(true);}
        catch (Exception e) {}

        em = (EditText) findViewById(R.id.editText4);
        staw = (EditText) findViewById(R.id.editText11);
        name = (EditText)findViewById(R.id.editText14);
        staw.setEnabled(false);
        admin = (Switch)findViewById(R.id.switch2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setData();
        addListener();
    }

    private void setData() {
        Cursor res = MyDb.getAllData(7);
        if (res.moveToFirst()) {
            do {
                timeInDb = res.getString(1);
                resultNum = res.getString(2);
                Email = res.getString(3);
                stawka = res.getString(4);
                pass = res.getString(5);
                writepass = res.getString(6);
                nameEmpl = res.getString(7);
            }
            while (res.moveToNext());
        }
        if(Email != null)
            em.setText(Email);

        if(nameEmpl != null)
            name.setText(nameEmpl);

        staw.setText(stawka);

        if(writepass != null) {
            admin.setChecked(true);
            staw.setEnabled(true);
        }
        else staw.setEnabled(false);
    }

    private void addListener()
    {
        accept = (Button)findViewById(R.id.acc);
        smien = (Button)findViewById(R.id.button3);
        //admin = (Switch)findViewById(R.id.switch1);
        accept.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            int count = MyDb.getCountData(7);
                            if (!em.getText().toString().isEmpty())
                                Email = em.getText().toString();

                            if (!staw.getText().toString().isEmpty())
                                stawka = staw.getText().toString();

                            if(!name.getText().toString().isEmpty())
                            {
                                nameEmpl = name.getText().toString();
                                if (count > 0)
                                    MyDb.insertChlam(2, "1", timeInDb, resultNum, Email, Integer.valueOf(stawka), pass, writepass, nameEmpl);
                                else
                                    MyDb.insertChlam(1, "0", timeInDb, resultNum, Email, Integer.valueOf(stawka), pass, writepass, nameEmpl);
                                Toast.makeText(Settings.this, "Данные изменены", Toast.LENGTH_LONG).show();
                                setData();
                            }
                            else Toast.makeText(Settings.this, "Пустое поле 'Имя работника' ", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e) {Toast.makeText(Settings.this,"Ошибка", Toast.LENGTH_LONG).show();}
                    }
                }
        );
        admin.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (admin.isChecked()) {
                    if(pass != null) {
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.dialog_password, null);

                        //Создаем AlertDialog
                        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

                        //Настраиваем prompt.xml для нашего AlertDialog:
                        mDialogBuilder.setView(promptsView);

                        //Настраиваем отображение поля для ввода текста в открытом диалоге:
                        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);

                        //Настраиваем сообщение в диалоговом окне:
                        mDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //Вводим текст и отображаем в строке ввода на основном экране:
                                                if(pass.equals(userInput.getText().toString()))
                                                {
                                                    int count = MyDb.getCountData(7);

                                                    if (!em.getText().toString().isEmpty())
                                                        Email = em.getText().toString();
                                                    else Email = "";

                                                    if (!staw.getText().toString().isEmpty())
                                                        stawka = staw.getText().toString();
                                                    else
                                                        stawka = "0";

                                                    if(!name.getText().toString().isEmpty())
                                                        nameEmpl = name.getText().toString();

                                                    if (count > 0)
                                                        MyDb.insertChlam(2, "1", timeInDb, resultNum, Email, Integer.valueOf(stawka), pass, userInput.getText().toString(), nameEmpl);
                                                    else
                                                        MyDb.insertChlam(1, "0", timeInDb, resultNum, Email, Integer.valueOf(stawka), pass, userInput.getText().toString(), nameEmpl);

                                                    setData();
                                                    //Toast.makeText(Settings.this, "ADMIN", Toast.LENGTH_LONG).show();
                                                }
                                                else
                                                    {
                                                        //Toast.makeText(Settings.this, "NIE ADMIN. Pass: " + pass + " Pass Write: " + userInput.getText().toString(), Toast.LENGTH_LONG).show();
                                                        admin.setChecked(false);
                                                    }
                                            }
                                        })
                                .setNegativeButton("Отмена",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        }
                                );
                        //Создаем AlertDialog:
                        AlertDialog alertDialog = mDialogBuilder.create();
                        //и отображаем его:
                        alertDialog.show();
                    }
                    else
                    {
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.dialog_password, null);
                        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

                        mDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
                        final TextView textUser = (TextView)promptsView.findViewById(R.id.tv);
                        textUser.setText("Введите новый пароль");
                        mDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //Вводим текст и отображаем в строке ввода на основном экране:

                                                int count = MyDb.getCountData(7);

                                                if (!em.getText().toString().isEmpty())
                                                    Email = em.getText().toString();
                                                else Email = "";

                                                if (!staw.getText().toString().isEmpty())
                                                    stawka = staw.getText().toString();
                                                else
                                                    stawka = "0";

                                                if(!name.getText().toString().isEmpty())
                                                    nameEmpl = name.getText().toString();

                                                //if(userInput.getText().toString().equals(dbFire.getReference().child("Password").child("StaticPass").getKey().toString())){}

                                                if (count > 0)
                                                    MyDb.insertChlam(2, "1", timeInDb, resultNum, Email, Integer.valueOf(stawka), userInput.getText().toString(), "", nameEmpl);
                                                else
                                                    MyDb.insertChlam(1, "0", timeInDb, resultNum, Email, Integer.valueOf(stawka), userInput.getText().toString(), "", nameEmpl);
                                                setData();
                                                //Toast.makeText(Settings.this, "Новый пароль записан", Toast.LENGTH_LONG).show();

                                            }
                                        })
                                .setNegativeButton("Отмена",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                admin.setChecked(false);
                                                dialog.cancel();
                                            }
                                        }
                                );
                        //Создаем AlertDialog:
                        AlertDialog alertDialog = mDialogBuilder.create();
                        //и отображаем его:
                        alertDialog.show();
                    }
                }
                else
                {
                    int count = MyDb.getCountData(7);

                    if (count > 0)
                        MyDb.insertChlam(2, "1", timeInDb, resultNum, Email, Integer.valueOf(stawka), pass, null, nameEmpl);
                    else
                        MyDb.insertChlam(1, "0", timeInDb, resultNum, Email, Integer.valueOf(stawka), pass, null, nameEmpl);

                    setData();
                }
            }
        });
        smien.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(pass != null)
                        {
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.dialog_change_pass, null);
                        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

                        mDialogBuilder.setView(promptsView);

                        final EditText userInputOld = (EditText) promptsView.findViewById(R.id.input_text_old);
                        final EditText userInputNew = (EditText) promptsView.findViewById(R.id.input_text_new);


                        mDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //Вводим текст и отображаем в строке ввода на основном экране:
                                                if(pass.equals(userInputOld.getText().toString())) {
                                                    int count = MyDb.getCountData(7);

                                                    if (!em.getText().toString().isEmpty())
                                                        Email = em.getText().toString();
                                                    else Email = "";

                                                    if (!staw.getText().toString().isEmpty())
                                                        stawka = staw.getText().toString();
                                                    else
                                                        stawka = "0";

                                                    if (count > 0)
                                                        MyDb.insertChlam(2, "1", timeInDb, resultNum, Email, Integer.valueOf(stawka), userInputNew.getText().toString(), null, nameEmpl);
                                                    else
                                                        MyDb.insertChlam(1, "0", timeInDb, resultNum, Email, Integer.valueOf(stawka), userInputNew.getText().toString(), null, nameEmpl);

                                                    setData();
                                                    Toast.makeText(Settings.this, "Данные изменены", Toast.LENGTH_LONG).show();
                                                }
                                                else
                                                    Toast.makeText(Settings.this, "Неверный старый пароль", Toast.LENGTH_LONG).show();

                                            }
                                        })
                                .setNegativeButton("Отмена",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                admin.setChecked(false);
                                                dialog.cancel();
                                            }
                                        }
                                );
                        //Создаем AlertDialog:
                        AlertDialog alertDialog = mDialogBuilder.create();
                        //и отображаем его:
                        alertDialog.show();
                        }
                        else Toast.makeText(Settings.this, "Еще не создан пароль, чтобы его заменить" ,Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}