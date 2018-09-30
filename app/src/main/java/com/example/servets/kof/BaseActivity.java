package com.example.servets.kof;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public abstract class BaseActivity extends AppCompatActivity {


    public void setupBottomNavigation(int navNumber)
    {
        BottomNavigationViewEx bottom = (BottomNavigationViewEx)findViewById(R.id.bottom_navigation_view);
        bottom.setIconSize(53,53);
        bottom.setIconsMarginTop(1);
        bottom.setTextVisibility(false);
        bottom.enableItemShiftingMode(false);
        bottom.enableShiftingMode(false);
        bottom.enableAnimation(false);
        for(int i = 0; i< bottom.getMenu().size(); i++)
            bottom.setIconTintList(i, null);
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                       @Override
                                                       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                           int id = item.getItemId();

                                                           switch (id) {
                                                               case R.id.towar:
                                                                   Intent intent1 = new Intent(BaseActivity.this, MainActivity.class);
                                                                   startActivity(intent1);
                                                                   break;
                                                               case R.id.zamow:
                                                                   Intent intent2 = new Intent(BaseActivity.this, ListZamow.class);
                                                                   startActivity(intent2);
                                                                   break;
                                                               case R.id.send:
                                                                   Intent intent3 = new Intent(BaseActivity.this, DopFunk.class);
                                                                   startActivity(intent3);
                                                                   break;
                                                               case R.id.listall:
                                                                   Intent intent4 = new Intent(BaseActivity.this, AllDataForm.class);
                                                                   startActivity(intent4);
                                                                   break;
                                                               case R.id.AllData:
                                                                   Intent intent5 = new Intent(BaseActivity.this, MonthData.class);
                                                                   startActivity(intent5);
                                                                   break;
                                                               default:
                                                                   Toast.makeText(BaseActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                                                                   break;
                                                           }

                                                           return true;
                                                       }
                                                   }
        );
        bottom.getMenu().getItem(navNumber).setChecked(true);


    }
}
