package com.sefa.encryptionapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.sefa.encryptionapp.databinding.FragmentEncBinding;

public class EncFragment extends Fragment {

    private FragmentEncBinding binding;
    public static final String ALPHABET_TR  = "abcçdefgğhıijklmnoöpqrsştuüvwxyz"; //32 harf
    private String enc_text="";
    private int code=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enc,container, false);

        binding.buttonEncSend.setEnabled(false);

        binding.buttonEncrypt.setOnClickListener(view ->
        {
            if (!binding.editTextencMessage.getText().toString().isEmpty())
            {

                View keybordView = this.getActivity().getCurrentFocus();
                if (keybordView != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService((Context.INPUT_METHOD_SERVICE));
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                String metin = binding.editTextencMessage.getText().toString();
                String metinTrimmed = metin.trim();

                metinTrimmed = metinTrimmed.replace(",", "");
                metinTrimmed = metinTrimmed.replace("?", "");
                metinTrimmed = metinTrimmed.replace(".", "");
                metinTrimmed = metinTrimmed.replace("!", "");

                String[] dizi = metinTrimmed.split(" ");
                String[] sifreliDizi = new String[dizi.length];
                StringBuilder sifreliMetin = new StringBuilder();

                char someCharE = 'e';
                int countE = 0;
                char someCharA = 'a';
                int countA = 0;
                int countAE = 0;

                System.out.println(dizi.length);

                for (int i = 0; i < metinTrimmed.length(); i++) {
                    if (metinTrimmed.charAt(i) == someCharE) {
                        countE++;
                    } else if (metinTrimmed.charAt(i) == someCharA) {
                        countA++;
                    }
                }

                System.out.println("A sayısı" + countA + " E sayısı: " + countE);
                countAE = countE + countA;

                System.out.println("ae toplamı öncce" + countAE);

                while (true) {
                    if (isPrime(countAE)) {
                        break;
                    } else {
                        countAE++;
                    }
                }
                System.out.println("ae toplamı sonra" + countAE);
                System.out.println("çarpım" + countAE * dizi.length);

                for (int i = 0; i < dizi.length; i++) {
                    sifreliDizi[i] = encrypt(dizi[i], countAE * dizi.length);
                }

                for (String s : dizi) {
                    System.out.print(s + " ");
                }
                System.out.println("");

                for (String s : sifreliDizi) {
                    sifreliMetin.append(s + " ");
                }

                binding.editTextEncText.setText(sifreliMetin.toString().trim());
                binding.editTextEncCode.setText(String.valueOf(countAE));
                enc_text=sifreliMetin.toString().trim();
                code=countAE;

                binding.buttonEncSend.setEnabled(true);

            }

            else
            {
                Toast.makeText(getContext(),"Please write a something",Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonEncSend.setOnClickListener(view ->
        {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "*Encrypted Text :* " + enc_text + "\n*Code :* " + code);

            try
            {
                if (!binding.editTextencMessage.getText().toString().isEmpty())
                {
                    startActivity(Intent.createChooser(sharingIntent, "Share in..."));
                }
            }
            catch (android.content.ActivityNotFoundException ex)
            {
                Log.e("hata",ex.toString());
            }

        });


        return binding.getRoot();
    }

    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static String encrypt(String message, int shiftKey) {
        message = message.toLowerCase();
        String cipherText = "";
        for (int ii = 0; ii < message.length(); ii++) {
            int charPosition = ALPHABET_TR.indexOf(message.charAt(ii));
            int keyVal = (shiftKey + charPosition) % 32;
            char replaceVal = ALPHABET_TR.charAt(keyVal);
            cipherText += replaceVal;
        }
        return cipherText;
    }


}