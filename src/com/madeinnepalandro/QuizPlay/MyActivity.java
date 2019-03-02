package com.madeinnepalandro.QuizPlay;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MyActivity extends FragmentActivity implements AdapterView.OnItemClickListener {
    /**
     * Called when the activity is first created.
     */

    public DrawerLayout dLayout;
    public ListView menuList;
    public String[] titles;
    //public String[] listData;

    public MyMenuListAdapter myMenuListAdap;
    public ActionBarDrawerToggle drawerListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dLayout=(DrawerLayout)findViewById(R.id.drawerMain);
        titles=getResources().getStringArray(R.array.nav_drawer_items);

        //listData=getResources().getStringArray(R.array.nav_drawer_items);

        menuList=(ListView)findViewById(R.id.drawerList);

        myMenuListAdap=new MyMenuListAdapter(this);
        menuList.setAdapter(myMenuListAdap);
        //menuList.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listData));

        drawerListener=new ActionBarDrawerToggle
                (this,dLayout,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };





        menuList.setOnItemClickListener(this);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onPostCreate(Bundle savedInstancState){
        super.onPostCreate(savedInstancState);
        drawerListener.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerListener.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this,listData[position]+" Was Selected", Toast.LENGTH_LONG).show();
        selectMenuItem(position);
    }

    public void selectMenuItem(int pos) {
        menuList.setItemChecked(pos,true);
        //setActionBarTitle(listData[pos]);   //setting title of the actionbar as per the selected menuitem
        displayView(pos);

    }

    private void displayView(int pos) {
        //update the main content by replacing fragments
        Fragment frg=null;
        switch (pos){
            case 0:
                //Play Quiz
                Intent inte = new Intent(MyActivity.this,QuestionView.class);
                startActivity(inte);
                break;

            case 1:
                //High Score
                break;
            case 2:
                //More Games
                break;
            case 3:
                //Developers
                Intent i = new Intent(MyActivity.this,AboutUs.class);
                startActivity(i);

                break;
            case 4:
                //Exit
                System.exit(0);
                break;

            default:
                //do nothing
                break;

        }
        if(frg!=null){
            FragmentManager fmgr= getFragmentManager();
            fmgr.beginTransaction()
                    .replace(R.id.mainContent,frg).commit();

            //update the selected item and title and close the drawer
            menuList.setItemChecked(pos,true);
            menuList.setSelection(pos);
            setTitle(titles[pos]);
            dLayout.closeDrawer(menuList);
            //setActionBarTitle();


            //Intent intent = new Intent(MyActivity.this,QuestionView.class);
           // startActivity(intent);

          // Intent i= new Intent(MyActivity.this,AddQuestion.class);
           // startActivity(i);
        }

    }

}





