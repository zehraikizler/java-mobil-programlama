package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SmsActivity extends AppCompatActivity {

    private  static  final int izin_verildi=1;
    String tel,mesaj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Button btn = findViewById(R.id.send);
        EditText textt = findViewById(R.id.tel);
        EditText textm = findViewById(R.id.mesaage);

        try {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ContextCompat.checkSelfPermission(SmsActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(SmsActivity.this, new String[]{Manifest.permission.SEND_SMS},izin_verildi);
                    }else{
                        tel = textt.getText().toString();
                        mesaj = textm.getText().toString();

                        if(mesaj.equals("") || mesaj == null){
                            new GetQuoteTask().execute();
                            Toast.makeText(getApplicationContext(), "Guzel Soz Gonderildi!", Toast.LENGTH_SHORT).show();
                        }else{
                            sendSms(mesaj);
                            Toast.makeText(getApplicationContext(), "Sms Gonderildi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } catch (Exception e){}
    }

    private void sendSms(String mes) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(tel, null, mes, null, null);
            Toast.makeText(getApplicationContext(), "Ok!", Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Log.e("SmsActivity", "hata!!!" + e.getMessage());
        }
    }

    private class GetQuoteTask extends AsyncTask<Void, Void, String> {
        @Override
        protected  String doInBackground(Void... params){
            String apiurl = "https://api.quotable.io/random";
            try {
                URL url = new URL(apiurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream =urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String satir;
                while ((satir = bufferedReader.readLine()) != null){
                    stringBuilder.append(satir).append("\n");
                }
                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();
                return stringBuilder.toString();
            }catch ( IOException e) {
                e.printStackTrace();
            }
            return  null;
        }
        @Override
        protected void onPostExecute(String result){
            try {
                JSONObject jsonObject = new JSONObject(result);
                String sonuc = jsonObject.getString("content");
                sendSms(sonuc);
            }catch ( JSONException e) {
                e.printStackTrace();
            }
        }
    }
}