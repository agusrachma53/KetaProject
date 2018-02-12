package com.bootcamp.xsis.keta;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * Created by Jack Ma on 1/13/2018.
 */

public class Konstanta {
    static String ID_EXTRA_0 = "id_0";
    static String ID_EXTRA_1 = "id_1";
    static String ID_EXTRA_2 = "id_2";
    static String ID_EXTRA_3 = "id_3";
    static String ID_EXTRA_4 = "id_4";
    static String ID_EXTRA_5 = "id_5";

    public static String generateOrderKodePesan(String namaUser, int idUser){
        String hasil = "";

        Date currentTime = Calendar.getInstance().getTime();
        String datenya = String.valueOf(currentTime);
        String replacenya = datenya.replace(" ","");
        String getCode = replacenya.substring(0,15);
        String replaceName = namaUser.replace(" ","_");
        hasil = getCode + idUser;

        return  hasil;
    }

}
