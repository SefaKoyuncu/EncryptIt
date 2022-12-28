package com.sefa.encryptionapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sefa.encryptionapp.databinding.FragmentDecBinding;

public class DecFragment extends Fragment {

    private FragmentDecBinding binding;
    public static final String ALPHABET_TR  = "abcçdefgğhıijklmnoöpqrsştuüvwxyz"; //32 harf
    private String dec_text="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dec,container, false);

        binding.buttonDecSend.setEnabled(false);

        binding.buttonDecrypt.setOnClickListener(view ->
        {

            if (!binding.editTextencMessage.getText().toString().isEmpty() && !binding.editTextdecCode.getText().toString().isEmpty()) {
                String sifreliMetin = binding.editTextencMessage.getText().toString();
                sifreliMetin = sifreliMetin.trim();

                sifreliMetin = sifreliMetin.replace(",", "");
                sifreliMetin = sifreliMetin.replace("?", "");
                sifreliMetin = sifreliMetin.replace(".", "");
                sifreliMetin = sifreliMetin.replace("!", "");

                String[] sifreliDizi = sifreliMetin.split(" ");
                String[] cozulduDizi = new String[sifreliDizi.length];

                StringBuilder cozulduMetin = new StringBuilder();

                int key_part = Integer.parseInt(binding.editTextdecCode.getText().toString());

                for (int i = 0; i < sifreliDizi.length; i++) {
                    cozulduDizi[i] = decrypt(sifreliDizi[i], key_part * sifreliDizi.length);
                }

                for (String s : cozulduDizi) {
                    cozulduMetin.append(s + " ");
                }

                binding.editTextDecText.setText(cozulduMetin.toString().trim());
                System.out.println(cozulduMetin.toString().trim());
                dec_text=cozulduMetin.toString().trim();

                binding.buttonDecSend.setEnabled(true);

            }
            else
            {
                Toast.makeText(getContext(),"Please write a ciphertext and code",Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonDecSend.setOnClickListener(view -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "*Decrypted Text :* " + dec_text);

            try
            {
                if (!binding.editTextencMessage.getText().toString().isEmpty() && !binding.editTextdecCode.getText().toString().isEmpty())
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

    public static String decrypt(String cipherText, int shiftKey) {
        cipherText = cipherText.toLowerCase();
        String message = "";
        for (int ii = 0; ii < cipherText.length(); ii++) {
            int charPosition = ALPHABET_TR.indexOf(cipherText.charAt(ii));
            int keyVal = (charPosition - shiftKey) % 32;
            if (keyVal < 0) {
                keyVal = ALPHABET_TR.length() + keyVal;
            }
            char replaceVal = ALPHABET_TR.charAt(keyVal);
            message += replaceVal;
        }
        return message;
    }
}