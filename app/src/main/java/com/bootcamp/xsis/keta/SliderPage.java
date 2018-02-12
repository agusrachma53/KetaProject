package com.bootcamp.xsis.keta;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SliderPage extends PagerAdapter{

    private Context context;
    private LayoutInflater layoutInflater;

    private int[] slider = {
            R.mipmap.slider_1_keta,R.mipmap.slider_2_keta,R.mipmap.slider_3_keta
    };

    public SliderPage(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slider.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_slider, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.slider);
        imageView.setImageResource(slider[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
