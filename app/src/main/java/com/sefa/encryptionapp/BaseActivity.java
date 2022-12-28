package com.sefa.encryptionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.sefa.encryptionapp.databinding.ActivityBaseBinding;

public class BaseActivity extends AppCompatActivity {

    private ActivityBaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base);

        FullScreen();

        binding.cLEnc.setOnClickListener(view -> {
            replaceFragment(new EncFragment());
            binding.cLEnc.setBackgroundResource(R.drawable.button_next);
            binding.cLDec.setBackgroundResource(R.drawable.button_next_gray);
            binding.textViewEnc.setTextColor(Color.WHITE);
            binding.textViewDec.setTextColor(getResources().getColor(R.color.inactive));


        });

        binding.cLDec.setOnClickListener(view -> {
            replaceFragment(new DecFragment());
            binding.cLDec.setBackgroundResource(R.drawable.button_next);
            binding.cLEnc.setBackgroundResource(R.drawable.button_next_gray);
            binding.textViewDec.setTextColor(Color.WHITE);
            binding.textViewEnc.setTextColor(getResources().getColor(R.color.inactive));





        });
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

    public void FullScreen()
    {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}