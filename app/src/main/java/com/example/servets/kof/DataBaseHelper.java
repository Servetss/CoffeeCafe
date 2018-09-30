package com.example.servets.kof;
import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Zamowienia.db";

   // -------- Kofe Data Gride View----------
    public static final String TABLE_NAME = "Kofe";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Nazwa";
    public static final String COL_3 = "one";
    public static final String COL_4 = "two";
    public static final String COL_5 = "three";
    public static final String COL_6 = "four";
    public static final String COL_7 = "five";
    public static final String COL_8 = "six";

    //-------TEA DATA GRIDE VIEW-----------
    public static final String TABLE_TEA = "Tea";
    public static final String ID_TEA = "ID";
    public static final String NAZME_TEA = "Name";
    public static final String RODZAJ_TEA = "Rodzaj";
    public static final String CENA_TEA = "Cena";

    //---------ICE GRIDE VIEW--------------
    public static final String TABLE_ICE = "Ice";
    public static final String ID_ICE = "ID";
    public static final String NAZME_ICE = "Name";
    public static final String RODZAJ_ICE = "Rodzaj";
    public static final String CENA_ICE = "Cena";

    //----------OTHER DATA GRIDE VIEW------------
    public static final String TABLE_OTHER = "Other";
    public static final String ID_OTHER = "ID";
    public static final String NAZME_OTHER = "Name";
    public static final String CENA_OTHER = "Cena";

    //----------ADDITIONAL DATA GRIDE VIEW---------
    public static final String TABLE_DATA = "Additional";
    public static final String ID_ADDITIONAL = "ID";
    public static final String NAZME_ADDITIONAL = "Name";
    public static final String CENA_ADDITIONAL = "Cena";

    //-----------------------------------------------
    //-----------TABLE ZAKAZOW IN ZAKAZ LIST---------
    public static final String TABLE_ZAKAZOW = "Zakazow";
    public static final String ID_ZAKAZOW = "ID";
    public static final String NAZME_ZAKAZOW = "Name";
    public static final String CUP_ZAKAZOW = "Cup";
    public static final String CENA_ZAKAZOW = "Cena";
    public static final String RODZAJ_ZAKAZOW = "Rodzaj";

    //----------TABLE ZAKAZ ALL DAY---------------
    public static final String TABLE_ZAKAZADAY = "ZakazDay";
    public static final String ID_ZAKAZADAY = "ID";
    public static final String NAZME_ZAKAZADAY = "Name";
    public static final String CUP_ZAKAZADAY = "Cup";
    public static final String CENA_ZAKAZADAY = "Cena";
    public static final String RODZAJ_ZAKAZADAY = "Rodzaj";
    public static final String COSTG_ZAKAZADAY = "CostGotowka";
    public static final String DATE_ZAKAZADAY = "Date";

    //---------TABLE ALL LIST DATA-----------------
    public static final String TABLE_ZAKAZALL = "ZakazAll";
    public static final String ID_ZAKAZALL = "ID";
    public static final String NAZME_ZAKAZALL = "Name";
    public static final String CUP_ZAKAZALL = "Cup";
    public static final String CENA_ZAKAZALL = "Cena";
    public static final String RODZAJ_ZAKAZALL = "Rodzaj";
    public static final String COSTG_ZAKAZALL = "CostGotowka";
    public static final String DATE_ZAKAZALL = "Date";

    //---------TABLE INCOMES--------------------
    public static final String TABLE_INCOMES = "Incomse";
    public static final String ID_INCOMES = "ID";
    public static final String TOTALSUM_INCOMES = "TotalSum";
    public static final String EMPLOEE_INCOMES = "Emploee";
    public static final String COSTS_INCOMES = "Costs";
    public static final String RECEIVESCHEF_INCOMES = "ReciveSchef";
    public static final String DATR_INCOMES  = "Date";

    //-----------TSBLE COSTS----------------------
    public static final String TABLE_COSTS = "Costs";
    public static final String ID_COSTS = "ID";
    public static final String TOTALSUM_COSTS = "Nazwa";
    public static final String CENA_COSTS = "Cena";

    //-----------TSBLE CHLAM----------------------
    public static final String TABLE_CHLAM = "Chlam";
    public static final String ID_CHLAM = "ID";
    public static final String TIME_CHLAM = "Time";
    public static final String RESULT_CHLAM = "Result";
    public static final String EMAIL_CHLAM = "Email";
    public static final String STAWKA_CHLAM = "Stawka";
    public static final String PASSWORD_CHLAM = "Password";
    public static final String WRITE_PASSWORD_CHLAM = "PasswordWrite";
    public static final String NAME_CHLAM = "NameEmpl";




    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Nazwa TEXT, one INTEGER, two INTEGER, three INTEGER, four INTEGER, five INTEGER, six INTEGER)");
        db.execSQL("create table " + TABLE_TEA + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Rodzaj TEXT, Cena INTEGER)");
        db.execSQL("create table " + TABLE_ICE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Rodzaj TEXT, Cena INTEGER)");
        db.execSQL("create table " + TABLE_OTHER + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Cena INTEGER)");
        db.execSQL("create table " + TABLE_DATA + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Cena INTEGER)");
        db.execSQL("create table " + TABLE_ZAKAZOW + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Cup TEXT, Cena INTEGER, Rodzaj TEXT)");
        db.execSQL("create table " + TABLE_ZAKAZADAY + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Cup TEXT, Cena INTEGER, Rodzaj TEXT,CostGotowka TEXT, Date TEXT)");
        db.execSQL("create table " + TABLE_ZAKAZALL + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Cup TEXT, Cena INTEGER, Rodzaj TEXT,CostGotowka TEXT, Date TEXT)");
        db.execSQL("create table " + TABLE_INCOMES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TotalSum INTEGER, Emploee INTEGER, Costs INTEGER, ReciveSchef INTEGER, Date TEXT)");
        db.execSQL("create table " + TABLE_COSTS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Nazwa TEXT, Cena INTEGER)");

        db.execSQL("create table " + TABLE_CHLAM + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Time TEXT, Result TEXT, Email TEXT, Stawka INTEGER, Password TEXT, PasswordWrite TEXT, NameEmpl TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OTHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZAKAZOW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZAKAZADAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZAKAZALL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHLAM);

        onCreate(db);

    }


    public boolean insertData(int idSlot, String nazwa, String rodzaj, String cena, int one, int two, int three, int four, int five, int six)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        long result = -1;
        Log.d("Insert", "ID: " + String.valueOf(idSlot) +" Nazwa: "+ nazwa + " Rodzaj: " + rodzaj +" Cena: "+ cena +" Cena2:"+ one +" "+ two +" "+ three +" "+ four +" "+ five +" "+ six);
        switch (idSlot)
        {
            case 1:
                CV.put(COL_2, nazwa);
                CV.put(COL_3, one);
                CV.put(COL_4, two);
                CV.put(COL_5, three);
                CV.put(COL_6, four);
                CV.put(COL_7, five);
                CV.put(COL_8, six);
                result = db.insert(TABLE_NAME, null, CV);
                break;
            case 2:
                CV.put(NAZME_TEA, nazwa);
                CV.put(RODZAJ_TEA, rodzaj);
                CV.put(CENA_TEA, cena);
                result = db.insert(TABLE_TEA, null, CV);
                break;
            case 3:
                CV.put(NAZME_ICE, nazwa);
                CV.put(RODZAJ_ICE, rodzaj);
                CV.put(CENA_ICE, cena);
                result = db.insert(TABLE_ICE, null, CV);
                break;
            case 4:
                CV.put(NAZME_OTHER, nazwa);
                CV.put(CENA_OTHER, cena);
                result = db.insert(TABLE_OTHER, null, CV);
                break;
            case 5:
                CV.put(NAZME_ADDITIONAL, nazwa);
                CV.put(CENA_ADDITIONAL, cena);
                result = db.insert(TABLE_DATA, null, CV);
                break;
                default:
                    result = -1;
                    break;
        }
        Log.d("Insert", String.valueOf(result));
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataAllZamow(int idList, String name, String Cup, int Cena, String Rodzaj, String CostGotowka, String Date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        long result = -1;

        if(idList == 0) {
            CV.put(NAZME_ZAKAZADAY, name);
            CV.put(CUP_ZAKAZADAY, Cup);
            CV.put(CENA_ZAKAZADAY, Cena);
            CV.put(RODZAJ_ZAKAZADAY, Rodzaj);
            CV.put(COSTG_ZAKAZADAY, CostGotowka);
            CV.put(DATE_ZAKAZADAY, Date);

            result = db.insert(TABLE_ZAKAZADAY, null, CV);
        }
        else if (idList == 1)
        {
            CV.put(NAZME_ZAKAZALL, name);
            CV.put(CUP_ZAKAZALL, Cup);
            CV.put(CENA_ZAKAZALL, Cena);
            CV.put(RODZAJ_ZAKAZALL, Rodzaj);
            CV.put(COSTG_ZAKAZALL, CostGotowka);
            CV.put(DATE_ZAKAZALL, Date);

            result = db.insert(TABLE_ZAKAZALL, null, CV);
        }
        if(result == -1)
            return false;
        else
            return true;
    }

    public void DropTableAllZaow(int idTab)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if(idTab == 0)
            db.execSQL("delete from " + TABLE_ZAKAZOW);
        else if(idTab == 1)
            db.execSQL("delete from " + TABLE_ZAKAZADAY);
    }

    public void DropAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ZAKAZALL);
    }


    public boolean insertDataZamow(String nazwa, String cup, int cena, String rodzaj)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        CV.put(NAZME_ZAKAZOW, nazwa);
        CV.put(CUP_ZAKAZOW, cup);
        CV.put(CENA_ZAKAZOW, cena);
        CV.put(RODZAJ_ZAKAZOW, rodzaj);
        long result = db.insert(TABLE_ZAKAZOW, null, CV);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllDataZamow(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_ZAKAZOW, null);
        if(id == 1)
            res = db.rawQuery("select * from " + TABLE_ZAKAZOW, null);
        else if(id == 2)
            res = db.rawQuery("select * from " + TABLE_ZAKAZADAY, null);

        return res;

    }

    public Cursor getAllData(int idSlot)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = null;
        switch (idSlot)
        {
            case 1:
                res = db.rawQuery("select * from " + TABLE_NAME, null);
                break;
            case 2:
                res = db.rawQuery("select * from " + TABLE_TEA, null);
                break;
            case 3:
                res = db.rawQuery("select * from " + TABLE_ICE, null);
                break;
            case 4:
                res = db.rawQuery("select * from " + TABLE_OTHER, null);
                break;
            case 5:
                res = db.rawQuery("select * from " + TABLE_DATA, null);
                break;
            case 6:
                res = db.rawQuery("select * from " + TABLE_CHLAM, null);
                break;
            case 7:
                res = db.rawQuery("select * from " + TABLE_CHLAM, null);
                break;
            case 8:
                res = db.rawQuery("select * from " + TABLE_ZAKAZADAY, null);
                break;
            case 9:
                res = db.rawQuery("select * from " + TABLE_ZAKAZALL, null);
                break;
                default:
                    break;
        }
        return res;
    }

    public Cursor getQuery(String SqlQuery)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(SqlQuery, null);

        return res;
    }

    public boolean updateData(int idPoz, String id, String nazwa, String rodzaj,String cena, String one, String two, String three, String four, String five, String six)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues CV = new ContentValues();
            switch (idPoz) {
                case 1:
                    CV.put(COL_1, id);
                    CV.put(COL_2, nazwa);
                    CV.put(COL_3, one);
                    CV.put(COL_4, two);
                    CV.put(COL_5, three);
                    CV.put(COL_6, four);
                    CV.put(COL_7, five);
                    CV.put(COL_8, six);
                    db.update(TABLE_NAME, CV, "ID = ?", new String[]{id});
                    break;
                case 2:
                    CV.put(ID_TEA, id);
                    CV.put(NAZME_TEA, nazwa);
                    CV.put(RODZAJ_TEA, rodzaj);
                    CV.put(CENA_TEA, cena);
                    db.update(TABLE_TEA, CV, "ID = ?", new String[]{id});
                    break;
                case 3:
                    CV.put(ID_ICE, id);
                    CV.put(NAZME_ICE, nazwa);
                    CV.put(RODZAJ_ICE, rodzaj);
                    CV.put(CENA_ICE, cena);
                    db.update(TABLE_ICE, CV, "ID = ?", new String[]{id});
                    break;
                case 4:
                    CV.put(ID_OTHER, id);
                    CV.put(NAZME_OTHER, nazwa);
                    CV.put(CENA_OTHER, cena);
                    db.update(TABLE_OTHER, CV, "ID = ?", new String[]{id});
                    break;
                case 5:
                    CV.put(ID_ADDITIONAL, id);
                    CV.put(NAZME_ADDITIONAL, nazwa);
                    CV.put(CENA_ADDITIONAL, cena);
                    db.update(TABLE_DATA, CV, "ID = ?", new String[]{id});
                    break;
                default:
                    break;
            }
        }catch (Exception e){}
        return true;
    }

    public int deleteData (int idSlot, String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = 0;

        switch (idSlot)
        {
            case 1:
                i = db.delete(TABLE_NAME, "ID = ?", new String[] {id});
                break;
            case 2:
                i = db.delete(TABLE_TEA, "ID = ?", new String[] {id});
                break;
            case 3:
                i = db.delete(TABLE_ICE, "ID = ?", new String[] {id});
                break;
            case 4:
                i = db.delete(TABLE_OTHER, "ID = ?", new String[] {id});
                break;
            case 5:
                i = db.delete(TABLE_DATA, "ID = ?", new String[] {id});
                break;
            case 6:
                i = db.delete(TABLE_ZAKAZOW, "ID = ?", new String[] {id});
                break;
                default:
                    break;
        }
        return i;
    }

    public int getCountData(int idSlot)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int nummRows = 0;
        switch (idSlot)
        {
            case 1:
                nummRows = (int) DatabaseUtils.queryNumEntries(db, "Kofe");
            break;
            case 2:
                nummRows = (int) DatabaseUtils.queryNumEntries(db, "Tea");
                break;
            case 3:
                nummRows = (int) DatabaseUtils.queryNumEntries(db, "Ice");
                break;
            case 4:
                nummRows = (int) DatabaseUtils.queryNumEntries(db, "Other");
                break;
            case 5:
                nummRows = (int) DatabaseUtils.queryNumEntries(db, "Additional");
                break;
            case 6:
                nummRows = (int) DatabaseUtils.queryNumEntries(db, "Zakazow");
                break;
            case 7:
                nummRows = (int) DatabaseUtils.queryNumEntries(db, "Chlam");
                break;
            case 8:
                nummRows = (int) DatabaseUtils.queryNumEntries(db, "ZakazDay");
                break;
            case 9:
                nummRows = (int) DatabaseUtils.queryNumEntries(db, "ZakazAll");
                break;
                default:
                    break;
        }
        return nummRows;
    }



    public boolean insertChlam(int idInsert, String id, String time, String resultNum, String Email, int stawka, String pass, String writepass, String nameE)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();

        if(idInsert == 1) {
            CV.put(TIME_CHLAM, time);
            CV.put(RESULT_CHLAM, resultNum);
            CV.put(EMAIL_CHLAM, Email);
            CV.put(STAWKA_CHLAM, stawka);
            CV.put(PASSWORD_CHLAM, pass);
            CV.put(WRITE_PASSWORD_CHLAM, writepass);
            CV.put(NAME_CHLAM, nameE);

            long result = db.insert(TABLE_CHLAM, null, CV);
            if (result == -1)
                return false;
            else
                return true;
        }
        else if(idInsert == 2)
        {
            CV.put(ID_CHLAM, id);
            CV.put(TIME_CHLAM, time);
            CV.put(RESULT_CHLAM, resultNum);
            CV.put(EMAIL_CHLAM, Email);
            CV.put(STAWKA_CHLAM, stawka);
            CV.put(PASSWORD_CHLAM, pass);
            CV.put(WRITE_PASSWORD_CHLAM, writepass);
            CV.put(NAME_CHLAM, nameE);

            db.update(TABLE_CHLAM, CV, "ID = ?", new String[]{id});
            long result = db.insert(TABLE_CHLAM, null, CV);
            if (result == -1)
                return false;
            else
                return true;

        }
        return true;
    }



    public boolean insertCost(String name, int cena)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();

        CV.put(TOTALSUM_COSTS, name);
        CV.put(CENA_COSTS, cena);

        long result = db.insert(TABLE_COSTS, null, CV);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getDataCosts()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_COSTS, null);
        return res;
    }

    public boolean insertIncomse(int TotalSum, int Employee, int Costs, int ReciveSchef, String Data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();

        CV.put(TOTALSUM_INCOMES, TotalSum);
        CV.put(EMPLOEE_INCOMES, Employee);
        CV.put(COSTS_INCOMES, Costs);
        CV.put(RECEIVESCHEF_INCOMES, ReciveSchef);
        CV.put(DATR_INCOMES, Data);

        long result = db.insert(TABLE_INCOMES, null, CV);
        if(result == -1)
            return false;
        else
            return true;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
        db.execSQL("delete from " + TABLE_TEA);
        db.execSQL("delete from " + TABLE_ICE);
        db.execSQL("delete from " + TABLE_OTHER);
        db.execSQL("delete from " + TABLE_DATA);

    }
}
