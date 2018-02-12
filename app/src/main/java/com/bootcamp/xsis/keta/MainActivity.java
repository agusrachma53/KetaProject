package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;
import com.bootcamp.xsis.keta.Adapter.showMenu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentOne.OnFragmentInteractionListener,
        FragmentTwo.OnFragmentInteractionListener,
        AccountFragment.OnFragmentInteractionListener{

    SessionManager session;
    Cursor cursor;
    Context context;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    TextView user_name_header, user_phone_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        session = new SessionManager(getApplicationContext());
        context = this;
        dbHelper = new SQLiteDbHelper(context);
        queryHelper = new QueryHelper(dbHelper);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = FragmentOne.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView=  navigationView.getHeaderView(0);
        String phone = session.phone();
        String pass = session.pass();

        user_name_header = (TextView) navHeaderView.findViewById(R.id.user_name_header);
        user_phone_header = (TextView) navHeaderView.findViewById(R.id.user_phone_header);
             cursor = queryHelper.login(phone,pass);
               if (cursor.getCount()>0){
           cursor.moveToPosition(0);
        user_name_header.setText(cursor.getString(1));
         user_phone_header.setText(cursor.getString(2));
       }else {
           Toast.makeText(this, "Failed Account", Toast.LENGTH_SHORT).show();
        }

   }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
//            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // call Fragment
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        String phonex = session.phone();
        showMenu[] getDataDrawer = queryHelper.detailUser(phonex);
        int idnya = getDataDrawer[0].getId_user();
        if (id == R.id.nav_Menu) {
            fragmentClass = FragmentOne.class;
        } else if (id == R.id.nav_myAccount) {
            fragmentClass = AccountFragment.class;
        } else if (id == R.id.nav_myCart) {
            Intent i = new Intent(getApplicationContext(), ChartPage.class);
            i.putExtra("idnya",idnya);
            startActivity(i);
            fragmentClass = FragmentOne.class;
        } else if (id == R.id.history) {
            Intent u = new Intent(getApplicationContext(), History.class);
            startActivity(u);
            fragmentClass = FragmentOne.class;
        } else if (id == R.id.nav_logout) {
            session.logoutUser();
            fragmentClass = FragmentOne.class;

          Toast.makeText(MainActivity.this, "           you was logout,\n         Please Login! ", Toast.LENGTH_SHORT).show();

        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
