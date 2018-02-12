package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by XSIS-NB on 1/24/2018.
 */

public class SessionManagerKP {

    //shared Preferences
    SharedPreferences prefKP ;

    //edittor for Sharef Preferences
    SharedPreferences.Editor editor;

    //Context
    Context _context ;

    //Shared pred mode
    int  PRIVATE_MODE = 0;

    //Shared file name
    private  static final String PREF_NAME ="KodePesanan";

    //shared prefereces key
    private static final String IS_ORDER = "IsOrderIn";

   // Kode pesan Key
    public  static final  String KEY_KODEPESAN = "kodePesanan";

    //Constructor
    public SessionManagerKP(Context context){
        this._context = context;
        prefKP = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = prefKP.edit();
    }

    //login checking
    public void checkOrder(){

        if (!this.isOrderIn()){

            //is not logged willbe directed to Login Activity
            Intent intent = new Intent(_context, loginActivity.class);

            //closing all the Activities
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //add new flag to start new activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Staring Login Activity
            _context.startActivity(intent);
        }else{
            Intent intent = new Intent(_context, MainActivity.class);
            _context.startActivity(intent);
        }

    }

    //get stored session data
    public HashMap<String, String> getOrderDetails(){
        HashMap<String, String> order = new HashMap<String, String>();
        order.put(KEY_KODEPESAN, prefKP.getString(KEY_KODEPESAN, null));

        return order;

    }

    //order Session
    public void createKodePesanSession (String kodePesanan){
        //storing order value as true
        editor.putBoolean(IS_ORDER, true);

        //storing Kode Pesan in pref
        editor.putString(KEY_KODEPESAN, kodePesanan);

        //commit changes
        editor.commit();
    }

    public String kodePesan(){
        String kodePesan =  prefKP.getString(KEY_KODEPESAN, null);
        return kodePesan;
    }

    //Clear Session order to renew kode pesan
    public void confirm(){
        //clearing all data from shared Preferences
        editor.clear();
        editor.commit();

        Intent intent = new Intent(_context, MainActivity.class);

        //close all Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //add flag to new start activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //Starting Login Activity
        _context.startActivity(intent);
    }

    public boolean isOrderIn() {
        return prefKP.getBoolean(IS_ORDER, false);
    }
}
