package com.bootcamp.xsis.keta;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Jack Ma on 1/12/2018.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;

    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                Tab1 tab1 = new Tab1();
                System.out.println("Satu");
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                System.out.println("Dua");
                return tab2;
            case 2:
                Tab3 tab3 = new Tab3();
                System.out.println("Tiga");
                return tab3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
