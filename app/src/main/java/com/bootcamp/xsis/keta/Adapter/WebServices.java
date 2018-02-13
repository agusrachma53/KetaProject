package com.bootcamp.xsis.keta.Adapter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ASUS Notebook on 13/02/2018.
 */

public class WebServices {


    private static WebServices mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private WebServices(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){

        if(requestQueue ==  null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized WebServices getmInstance(Context context){
        if(mInstance == null){
            mInstance = new WebServices(context);
        }
        return mInstance;
    }

    public<T> void addToRequestque(Request<T> request){
        requestQueue.add(request);
    }


}
