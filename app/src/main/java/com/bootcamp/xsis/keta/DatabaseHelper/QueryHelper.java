package com.bootcamp.xsis.keta.DatabaseHelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by TAM on 12/01/2018.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.bootcamp.xsis.keta.Adapter.showMenu;
import com.bootcamp.xsis.keta.ChartPage;
import com.bootcamp.xsis.keta.Konstanta;
import com.bootcamp.xsis.keta.ProductDetail;

/**
 * Created by TAM on 12/01/2018.
 */

public class QueryHelper {

    private SQLiteDbHelper dbHelper;
    private Cursor cursor;
    public QueryHelper(SQLiteDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private void openDatabase() {
        dbHelper.openDatabase();
    }
    private void closeDatabase() {
        dbHelper.close();
    }

    public static String COL_name = "nama_user";
    public static String COL_phone = "phone_user";
    public static String COL_pass = "pass_user";
    public static String id_user = "id_user";
    public static String Alamat = "Alamat";
    public static String ATM = "ATM";
    public static String subtotal = "subtotal";
    public static String total = "total";
    public static String kodepesanan = "kode_pesanan";


    //query show Login
    public Cursor login(String phones, String passwords) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM login WHERE phone_user = '" + phones + "' AND pass_user = '" + passwords + "'", null);
        return cursor;
    }

    //show all data login
    public Cursor loginAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM login ", null);
        return cursor;
    }

    //login based on id
    public Cursor loginId(int IdUser) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM login WHERE id_user = '" + IdUser + "'", null);
        return cursor;
    }

    public boolean signUp(String e_name, String e_phone, String e_password) {
        try {
            SQLiteDatabase sign = dbHelper.getWritableDatabase();
            sign.execSQL(" INSERT INTO login (" +
                    COL_name + ", " +
                    COL_phone + ", " +
                    COL_pass +
                    ") VALUES ('" +
                    e_name + "', '" +
                    e_phone + "', '" +
                    e_password + "')");
        } catch (Exception x) {
            return false;
        } finally {
            return true;
        }
    }


    public void delete(String id, String table){
        Log.d("test","masuk");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(table,"id_produk = '" + id +"'",null);

    }

    public void deletemenu(String id, String table){
        Log.d("test","masuk");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(table,"id_user= '" + id +"'",null);

    }


    public showMenu[] Menunya(String kategory_produk) {
        Log.d("test","masuk");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM produk JOIN kategori ON produk.id_kategori_produk = kategori.id_kategori_produk WHERE kategori.id_kategori_produk = '"+kategory_produk+"'",null);
        showMenu[] menus = new showMenu[cursor.getCount()];
        cursor.moveToFirst();

        Log.d("debug", "count: "+cursor.getCount());
        for (int aa=0; aa < cursor.getCount(); aa++){
            menus[aa] = new showMenu();
            cursor.moveToPosition(aa);
            Log.d("hahahhahahahahhha",cursor.getString(2));
            menus[aa].set_id(cursor.getInt(1));
            menus[aa].setNama_produk(cursor.getString(2));
            menus[aa].setGambar_produk(cursor.getString(3));
            menus[aa].setDesk_produk(cursor.getString(4));
            menus[aa].setId_kategori_produk(cursor.getString(5));
            menus[aa].setHarga_produk(cursor.getInt(6));
        }
        return menus;
    }

    public showMenu[] showDetailMenu(String nama_produk){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM produk JOIN kategori ON produk.id_kategori_produk = kategori.id_kategori_produk WHERE produk.nama_produk = '"+nama_produk+"'",null);
        showMenu[] detailMenu = new showMenu[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            detailMenu[i] = new showMenu();
            cursor.moveToPosition(i);
            detailMenu[i].setId_product(cursor.getString(1));
            detailMenu[i].setNama_produk(cursor.getString(2));
            detailMenu[i].setGambar_produk(cursor.getString(3));
            detailMenu[i].setDesk_produk(cursor.getString(4));
            detailMenu[i].setId_kategori_produk(cursor.getString(5));
            detailMenu[i].setHarga_produk(cursor.getInt(6));
            detailMenu[i].setKategorii_produk(cursor.getString(9));
        }
        return detailMenu;

    }

    public showMenu[] detailUser(String phone){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM login WHERE phone_user = '"+phone+"'",null);
        showMenu[] userDetail = new showMenu[cursor.getCount()];
        Log.d("debugnya","hasilnya "+cursor.getCount());
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            userDetail[i] = new showMenu();
            cursor.moveToPosition(i);
            userDetail[i].setId_user(cursor.getInt(0));
            userDetail[i].setNama_user(cursor.getString(1));
            userDetail[i].setPhoneNumber(cursor.getString(2));
        }
        return userDetail;
    }

    public Cursor cekData(String id_product, int id_user,String table){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM '"+table+"' WHERE id_user = '"+id_user+"' AND id_produk = '"+id_product+"'",null);
        return cursor;
    }

    public showMenu[] tambahQty(String id, String Table,int id_user){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cekData;
        cekData = db.rawQuery("select * from "+Table+" where id_produk = '"+id+"' AND id_user ='"+id_user+"'",null);
        showMenu[] tambahinQTY = new showMenu[cekData.getCount()];
        cekData.moveToFirst();

        for (int i = 0; i < cekData.getCount();i++){
            tambahinQTY[i] = new showMenu();
            cekData.moveToPosition(i);
            tambahinQTY[i].setQuantity(cekData.getInt(5));
        }

        return tambahinQTY;
    }


    public showMenu[] orderPesan(int id_users) {
        Log.d("test","masuk");
        QueryHelper context = this;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from ordermenu join produk on ordermenu.id_produk = produk.id_produk WHERE id_user = '"+id_users+"'",null);
        showMenu[] orderDetail = new showMenu[cursor.getCount()];

        cursor.moveToFirst();
        Log.d("debug", "count: "+cursor.getCount());
        for (int i=0; i < cursor.getCount(); i++){
            orderDetail[i] = new showMenu();
            cursor.moveToPosition(i);
            orderDetail[i].setNama_user(cursor.getString(2));
            orderDetail[i].setNama_produk(cursor.getString(10));
            orderDetail[i].setKategorii_produk(cursor.getString(3));
            orderDetail[i].setId_product(cursor.getString(4));
            orderDetail[i].setQuantity(cursor.getInt(5));
            orderDetail[i].setHarga_produk(cursor.getInt(14));
            orderDetail[i].setSubtotal(cursor.getInt(6));
            orderDetail[i].setGambar_produk(cursor.getString(11));
        }

        return orderDetail;
    }


    public showMenu[] orderPesan2(int id_users,String id_produk) {
        Log.d("test","masuk");
        QueryHelper context = this;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from ordermenu join produk on ordermenu.id_produk = produk.id_produk WHERE ordermenu.id_user = '"+id_users+"' AND ordermenu.id_produk = '"+id_produk+"'",null);
        showMenu[] orderDetail = new showMenu[cursor.getCount()];

        cursor.moveToFirst();
        Log.d("debug", "count: "+cursor.getCount());
        for (int i=0; i < cursor.getCount(); i++){
            orderDetail[i] = new showMenu();
            cursor.moveToPosition(i);
            orderDetail[i].setNama_user(cursor.getString(2));
            orderDetail[i].setNama_produk(cursor.getString(9));
            orderDetail[i].setKategorii_produk(cursor.getString(3));
            orderDetail[i].setId_product(cursor.getString(4));
            orderDetail[i].setQuantity(cursor.getInt(5));
            orderDetail[i].setHarga_produk(cursor.getInt(13));
            orderDetail[i].setSubtotal(cursor.getInt(6));
            orderDetail[i].setGambar_produk(cursor.getString(10));
        }

        return orderDetail;
    }

    public showMenu[] getTotal(int id_users) {
        Log.d("test","masuk");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select nama_user,id_kategori_produk,id_produk,kuantity,(select sum(subtotal) from ordermenu where id_user = '"+id_users+"') from ordermenu where id_user = '"+id_users+"'",null);

        showMenu[] orderDetail = new showMenu[cursor.getCount()];
        cursor.moveToFirst();
        Log.d("debug", "count: "+cursor.getCount());
        for (int i=0; i < cursor.getCount(); i++){
            orderDetail[i] = new showMenu();
            cursor.moveToPosition(i);
            orderDetail[i].setNama_user(cursor.getString(0));
            orderDetail[i].setKategorii_produk(cursor.getString(1));
            orderDetail[i].setId_product(cursor.getString(2));
            orderDetail[i].setQuantity(cursor.getInt(3));
            orderDetail[i].setSubtotal(cursor.getInt(4));
        }
        return orderDetail;
    }

    public showMenu[] refreshListMenu(int id_user) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM ordermenu WHERE id_user='"+id_user+"'",null);
        showMenu[] menus = new showMenu[cursor.getCount()];
        cursor.moveToFirst();

        Log.d("debug", "count: "+cursor.getCount());
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);

            menus[cc] = new showMenu();

            String id_produk = cursor.getString(4).toString();
            Log.d("debug", "id produk : "+id_produk);
            SQLiteDatabase db2 = dbHelper.getReadableDatabase();
            Cursor cursor2 = db2.rawQuery("SELECT * FROM produk WHERE id_produk='"+id_produk+"'",null);
            cursor2.moveToFirst();
            for (int c2=0; c2 < cursor2.getCount(); c2++){
                cursor2.moveToPosition(c2);

                menus[cc].setQuantity(cursor.getInt(5));
                menus[cc].setSubtotal(cursor.getInt(6));

                menus[cc].setNama_produk(cursor2.getString(2).toString());
                menus[cc].setHarga_produk(cursor2.getInt(6));

                Log.d("debug", cursor2.getString(2).toString());
            }

            id_user = cursor.getInt(1);
            Log.d("debug", "id user : "+id_user);
            SQLiteDatabase db3 = dbHelper.getReadableDatabase();
            Cursor cursor3 = db.rawQuery("SELECT * FROM orderpesanan WHERE id_user='"+id_user+"'",null);
            cursor3.moveToFirst();
            for (int c3=0; c3 < cursor3.getCount(); c3++) {
                cursor3.moveToPosition(c3);

                menus[cc].setKode_pesanan(cursor3.getString(2).toString());
            }

        }
        return menus;
    }

    public Cursor order(int id_user, String kodepesanan){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM orderpesanan WHERE id_user='"+ id_user+ "'" + " AND kode_pesanan='" +kodepesanan+ "'", null);

        return cursor1;
    }

    public boolean klikOrder(String Alamat1, String Atm, int Id_user, String KodePesan, int subTotal, int total1){
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO orderpesanan " + "(" + id_user + ", " + kodepesanan + ", " + Alamat + ", " + ATM + ", " + subtotal + ", " + total + ") VALUES ('" +
                    Id_user + "','" +
                    KodePesan + "','" +
                    Alamat1 + "','" +
                    Atm + "','" +
                    subTotal + "','" +
                    total1 + "')");
        }catch (Exception e){
            return false;
        }finally {
            return true;
        }
    }

    public void updateOrder(String Alamat1, String Atm, int Id_user, String KodePesan, int subTotal, int total1){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE orderpesanan SET "+ Alamat + " ='" + Alamat1 + "', " + ATM + " ='" + Atm + "', "+ subtotal + " ='" + subTotal+ "', " + total + " ='" + total1 +
                "' WHERE " + id_user + " ='" + Id_user + "' " + "AND " + kodepesanan + " ='" + KodePesan +"'");
    }

    public void updateHistory(String CodePesananya, String alamat, String atm,String nameUser, int Total){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE history SET kodePesanan ='"+CodePesananya+"', atm ='"+atm+ "', alamat ='"+ alamat+"', total = '"+Total+"' WHERE namaUser = '"+ nameUser +"'");
    }

    public Cursor readOrderPesanan(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orderpesanan", null);

        return cursor;
    }

    public String getOrderKodePesanan(int id_user) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orderpesanan WHERE id_user='"+id_user+"'",null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            return cursor.getString(2).toString();
        }
        else{
            return null;
        }
    }
    public Cursor OrderMenu(int idUserK){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ordermenu  WHERE id_user='"+idUserK+"'", null);

        return cursor;
    }

    public showMenu[] orderpesanan(int idUser) {
        Log.d("test","masuk");
        QueryHelper context = this;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from orderpesanan join login on orderpesanan.id_user = login.id_user where orderpesanan.id_user = '"+idUser+"'" ,null);
        showMenu[] orderDetail = new showMenu[cursor.getCount()];

        cursor.moveToFirst();
        Log.d("debug", "count: "+cursor.getCount());
        for (int i=0; i < cursor.getCount(); i++){
            orderDetail[i] = new showMenu();
            cursor.moveToPosition(i);
            orderDetail[i].setNama_user(cursor.getString(8));
            orderDetail[i].setKode_pesanan(cursor.getString(2));
            orderDetail[i].setSubtotal(cursor.getInt(5));
            orderDetail[i].setTotalAkhir(cursor.getInt(6));
        }
        return orderDetail;
    }

    public void deleteHistory(String kodepesanan){
        String table = "history";
        String table2 = "orderpesanan";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(table,"kodePesanan = '" + kodepesanan +"'",null);
        db.delete(table2,"kode_pesanan = '" + kodepesanan +"'",null);


    }

    public showMenu[] HistoryOrder(int idUser, String kodepesanan) {
        Log.d("test","masuk");
        QueryHelper context = this;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from history join login on history.idUser = login.id_user join orderpesanan on orderpesanan.id_user = login.id_user join produk on produk.id_produk = history.idProduk  WHERE kodePesanan = '"+kodepesanan+"' AND idUser = '"+idUser+"'" ,null);
        showMenu[] orderDetail = new showMenu[cursor.getCount()];

        cursor.moveToFirst();
        Log.d("debug", "count: "+cursor.getCount());
        for (int i=0; i < cursor.getCount(); i++){
            orderDetail[i] = new showMenu();
            cursor.moveToPosition(i);
            orderDetail[i].setId_user(cursor.getInt(1));
            Log.d("id nya brooo",""+cursor.getInt(1));
//            orderDetail[i].setAlamat(cursor.getString(9));
//            orderDetail[i].setKode_pesanan(cursor.getString(8));
//            orderDetail[i].setGambar_produk(cursor.getString(26));
//            orderDetail[i].setNama_produk(cursor.getString(25));
//            orderDetail[i].setNama_produk(cursor.getString(1));
//            Log.d("gambar coy",""+cursor.getString(26));
        }

        return orderDetail;
    }

}