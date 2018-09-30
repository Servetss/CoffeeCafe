package com.example.servets.kof;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class Ice extends BaseAdapter {
    private Context mContext;
    private MainActivity ma;

    public Ice(Context c)
    { mContext = c; }

    public int getCount()
    {return ma.Ice.length;}

    @Override
    public Object getItem(int position)
    { return null; }

    public long getItemId(int position)
    {return 0; }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.d("MainActivity" , "Get View3");
        TextView Label = new TextView(mContext);
        Label.setBackgroundResource(R.drawable.sample_6);
        Label.setText("\n " + ma.Ice[position][0]);
        Label.setTextColor(Color.rgb(0,0,0));
        Label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Label.setLayoutParams(new GridView.LayoutParams(200,300));
        return Label;
    }
}
