package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bootcamp.xsis.keta.Adapter.ConvertData;

import java.util.HashMap;

/**
 * Created by TAM on 1/13/2018.
 */

public class SessionManager {

//    private static String KODE_PESANAN = "Agus003";//"Tami002";//"Ike001";

    //shared Preferences
    SharedPreferences pref ;

    //edittor for Sharef Preferences
    Editor editor;

    //Context
    Context _context ;

    //Shared pred mode
    int  PRIVATE_MODE = 0;

    //Shared file name
    private  static final String PREF_NAME ="KetaXsisRestauran";

    //shared prefereces key
    private static final String IS_LOGIN = "IsLoggedIn";

    //phone key
    public  static final  String KEY_PHONE = "phone";

    //password key
    public  static final  String KEY_PASS = "password";

    //Quantity Key
    public static final String KEY_QTY = "0";

    //message
    public static final String KEY_MESSAGE = "message";

    //Total
    public static final String KEY_TOTAL = "0";

    //Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public void sessionChart(String qty){

        editor.putString(KEY_QTY,qty);
        editor.commit();
    }

    public void sessionMessage(String message){

        editor.putString(KEY_MESSAGE,message);
        editor.commit();

    }

    public void total(String total){

        editor.putString(KEY_MESSAGE,total);
        editor.commit();

    }

    //Login Session
    public void createLoginSession (String phone, String password){
        //storing login login value as true
        editor.putBoolean(IS_LOGIN, true);

        //storing name in pref
     //   editor.putString(KEY_NAME, name);

        //storing phone in pref
        editor.putString(KEY_PHONE, phone);

        //storing password in pref
        editor.putString(KEY_PASS, password);

        //commit changes
        editor.commit();
    }

    //login checking
    public void checkLogin(){

        if (!this.isLoggedIn()){

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
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_PASS, pref.getString(KEY_PASS, null));

        return user;

    }

    public String phone(){
        String phone =  pref.getString(KEY_PHONE, null);
        return phone;
    }
    public String pass(){
        String pass =  pref.getString(KEY_PASS, null);
        return pass;
    }

    public String qty(){
        String qty = pref.getString(KEY_QTY,null);
        return qty;
    }

    public String total(){
        String total = pref.getString(KEY_TOTAL,null);
        return total;
    }

    public String message(){
        String message = pref.getString(KEY_MESSAGE,null);
        return message;
    }

    //Clear Session detail
    public void logoutUser(){
        //clearing all data from shared Preferences
        editor.clear();
        editor.commit();

        Intent intent = new Intent(_context, loginActivity.class);

        //close all Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //add flag to new start activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //Starting Login Activity
        _context.startActivity(intent);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
