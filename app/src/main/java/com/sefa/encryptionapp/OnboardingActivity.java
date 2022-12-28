package com.sefa.encryptionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.sefa.encryptionapp.databinding.ActivityOnboardingBinding;

public class OnboardingActivity extends AppCompatActivity {

    private ActivityOnboardingBinding binding;
    private TextView[] dots;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding);

            fullScreen();

            viewPagerAdapter=new ViewPagerAdapter(this);
            binding.slideViewPager.setAdapter(viewPagerAdapter);
            binding.slideViewPager.addOnPageChangeListener(viewListener);
            addDots(0);

            if (restorePrefData())
            {
                startActivity(new Intent(OnboardingActivity.this, MainActivity.class));
                finish();
            }

            binding.cLNext.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {

                    if (binding.slideViewPager.getCurrentItem() + 1 < viewPagerAdapter.getCount() )
                        binding.slideViewPager.setCurrentItem(binding.slideViewPager.getCurrentItem()+1,true);
                    else
                    {
                        startActivity(new Intent(OnboardingActivity.this, MainActivity.class));
                        savePrefsData();
                        finish();
                    }
                }
            });
        }

        private boolean restorePrefData()
        {
            SharedPreferences pref= getApplication().getSharedPreferences("myPrefs",MODE_PRIVATE);
            Boolean isIntroActivityOpenedBefore=pref.getBoolean("isIntroOpened",false);
            return isIntroActivityOpenedBefore;
        }

        private void savePrefsData()
        {
            SharedPreferences pref= getApplication().getSharedPreferences("myPrefs",MODE_PRIVATE);
            SharedPreferences.Editor editor=pref.edit();
            editor.putBoolean("isIntroOpened",true);
            editor.commit();
        }

        ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        private void addDots(int position)
        {
            dots = new TextView[3];
            binding.dotsLayout.removeAllViews();

            for (int i = 0 ; i < dots.length ; i++)
            {
                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(getResources().getColor(R.color.inactive));
                binding.dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0)
            {
                dots[position].setTextColor(getResources().getColor(R.color.white));
            }
        }

    private void fullScreen()
    {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
