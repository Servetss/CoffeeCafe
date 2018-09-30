package com.example.servets.kof;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SecondA extends AppCompatActivity {
    DataBaseHelper MyDb;
    private Button [] btnCup;
    private String [] cup;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MyDb = new DataBaseHelper(this);
        Intent intent = getIntent();

        cup = new String[6];
        name = intent.getStringExtra("name");
        cup[0] = intent.getStringExtra("110");
        cup[1] = intent.getStringExtra("175");
        cup[2] = intent.getStringExtra("250");
        cup[3] = intent.getStringExtra("320");
        cup[4] = intent.getStringExtra("400");
        cup[5] = intent.getStringExtra("500");

        addListenerOnButton();
    }

    public void addListenerOnButton()
    {
        btnCup = new Button[6];
        btnCup[0] = (Button)findViewById(R.id.one);
        btnCup[1] = (Button)findViewById(R.id.two);
        btnCup[2] = (Button)findViewById(R.id.three);
        btnCup[3] = (Button)findViewById(R.id.four);
        btnCup[4] = (Button)findViewById(R.id.five);
        btnCup[5] = (Button)findViewById(R.id.six);
        for(int i = 0; i<6;i++)
            if(Integer.parseInt(cup[i]) <= 0 || cup[i] == null)
                btnCup[i].setEnabled(false);

        btnCup[0].setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InsertData(name, "110", Integer.parseInt(cup[0]), "0");
                        finish();
                    }
                }
        );
        btnCup[1].setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InsertData(name, "175", Integer.parseInt(cup[1]), "0");
                        finish();
                    }
                }
        );
        btnCup[2].setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InsertData(name, "250", Integer.parseInt(cup[2]), "0");
                        finish();
                    }
                }
        );
        btnCup[3].setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InsertData(name, "320", Integer.parseInt(cup[3]), "0");
                        finish();
                    }
                }
        );
        btnCup[4].setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InsertData(name, "400", Integer.parseInt(cup[4]), "0");
                        finish();
                    }
                }
        );
        btnCup[5].setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InsertData(name, "500", Integer.parseInt(cup[5]), "0");
                        finish();
                    }
                }
        );
    }
    public void InsertData(String nameKofe, String cupSize, int cenaKofe, String rodzajDrink)
    {
        boolean isInserted = MyDb.insertDataZamow(nameKofe,cupSize, cenaKofe, rodzajDrink);
        if(isInserted == true)
            Toast.makeText(SecondA.this, "Successful", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(SecondA.this, "Fail", Toast.LENGTH_SHORT).show();
    }
}
