package com.madeinnepalandro.QuizPlay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by munchymahesh on 1/20/16.
 */
class MyMenuListAdapter extends BaseAdapter {
    //class resonsible for fetching data from string array

    String[] menuItems; //string values for menu items
    private Context ctx;
    int[] images={R.drawable.ic_drawer,R.drawable.ic_drawer,R.drawable.ic_drawer,R.drawable.ic_drawer,R.drawable.ic_drawer}; //images resources for menu items


    public MyMenuListAdapter(Context ctx){
        this.ctx=ctx;
        menuItems=ctx.getResources().getStringArray(R.array.nav_drawer_items);


    }
    @Override
    public int getCount() {
        return menuItems.length;
    }

    @Override
    public Object getItem(int position) {
        return menuItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //method to return the view for the Drawer
        View row=null;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.custom_drawer_row,parent,false);

        }
        else{
            row=convertView;

        }
        TextView titleTextView=(TextView)row.findViewById(R.id.txtMenuItem);
        ImageView titleImageView = (ImageView)row.findViewById(R.id.imgMenuItem);

        titleTextView.setText(menuItems[position]);
        titleImageView.setImageResource(images[position]);
        return row;
    }

}
