package com.sefa.encryptionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;

public class ViewPagerAdapter extends PagerAdapter{

    Context context;

    int[] anims = {
            R.raw.lockpage1,
            R.raw.messagepage2,
            R.raw.lock6
    };

    int[] texts = {
            R.string.text1,
            R.string.text2,
            R.string.text3,
    };
    int[] titletop = {

            R.string.title,
            R.string.title,
            R.string.title
    };

    int[] appname = {

            R.string.appname,
            R.string.appname,
            R.string.appname

    };

    public ViewPagerAdapter(Context context){

        this.context = context;

    }

    @Override
    public int getCount() {
        return titletop.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slider_layout,container,false);

        TextView textViewTitleTop=view.findViewById(R.id.textViewTitleTop);
        TextView textViewAppName=view.findViewById(R.id.textViewAppName);
        LottieAnimationView lottieAnimationView=view.findViewById(R.id.animationView);
        TextView textViewText=view.findViewById(R.id.textViewText);

        textViewTitleTop.setText(titletop[position]);
        textViewAppName.setText(appname[position]);
        lottieAnimationView.setAnimation(anims[position]);
        textViewText.setText(texts[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);

    }
}
