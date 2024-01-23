package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConvertActivity extends AppCompatActivity {

    private final String[] tabanlar = {"2", "8", "16"};
    private final String[] boyutlar = {"kilobyte", "byte", "kibibit", "bit"};
    String secimt, secimb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        EditText decimal = findViewById(R.id.decimal);
        EditText megabyte = findViewById(R.id.mbyte);
        EditText cel =findViewById(R.id.celcius);

        TextView sonuctaban = findViewById(R.id.tsonuc);
        TextView sonucboyut = findViewById(R.id.bsonuc);
        TextView sonucsicaklik = findViewById(R.id.csonuc);

        RadioButton fah = findViewById(R.id.fah);
        RadioButton kel = findViewById(R.id.kel);

        Spinner spinnertaban, spinnerboyut;
        ArrayAdapter<String> veriAdaptertaban, veriAdapterboyut;

        spinnertaban = findViewById(R.id.taban);
        spinnerboyut = findViewById(R.id.boyut);

        veriAdaptertaban = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tabanlar);
        veriAdapterboyut = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,boyutlar);

        spinnertaban.setAdapter(veriAdaptertaban);
        spinnerboyut.setAdapter(veriAdapterboyut);
        try {
            spinnertaban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        secimt = spinnertaban.getSelectedItem().toString();
                        //Toast.makeText(getBaseContext(),secimt,Toast.LENGTH_SHORT).show();

                        String str = decimal.getText().toString();
                        if(!str.isEmpty()){
                            Double dec = Double.parseDouble(str);
                            String sonuc = tabanHesap(dec,Integer.parseInt(secimt));
                            sonuctaban.setText("SONUC : " + sonuc);
                        }

                    }catch (Exception e){}

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinnerboyut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        secimb = spinnerboyut.getSelectedItem().toString();
                        //Toast.makeText(getBaseContext(),secimb,Toast.LENGTH_SHORT).show();

                        String str = megabyte.getText().toString();
                        if(!str.isEmpty()){
                            Double dec = Double.parseDouble(str);
                            String sonuc = boyutHesap(dec, secimb);
                            sonucboyut.setText("SONUC : " +sonuc);
                        }
                    }catch (Exception e){}
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            fah.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked){
                            Double celsius = Double.parseDouble(cel.getText().toString());
                            double fahrenayt = (celsius * 9/5) + 32;
                            sonucsicaklik.setText("SONUC : " + fahrenayt);
                        }
                    }catch (Exception e){}
                }
            });

            kel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked){
                            Double celsius = Double.parseDouble(cel.getText().toString());
                            double kelvin = celsius +273.15;
                            sonucsicaklik.setText("SONUC : " + kelvin);
                        }
                    }catch (Exception e){}
                }
            });

        }catch (Exception e) {}

    }

    public static String tabanHesap(double decimalv, int taban){
        long intpart = (long) decimalv;
        String result = Long.toString(intpart,taban);
        if(decimalv % 1 != 0){
            result +=".";
            double kesir =decimalv % 1;
            for (int i =0; i <10; i++){
                kesir *=taban;
                int tam =(int) kesir;
                result += Integer.toString(tam,taban);
                kesir -= tam;
            }
        }
        return result;
    }

    public  static  String boyutHesap(double value, String boyut){
        double result = 0;
        String resultString;

        switch (boyut){
            case "kilobyte":
                result = value * 1024 ;
                resultString = String.format("%.0f", result) + "KB";
                break;
            case "byte":
                result = value * 1024 * 1024 ;
                resultString = String.format("%.0f", result) + "BYTE";
                break;
            case "kibibit":
                result = value * 1024 * 8 ;
                resultString = String.format("%.0f", result) + "KİBİBİT";
                break;
            case "bit":
                result = value * 1024 * 8 * 1024 ;
                resultString = String.format("%.0f", result) + "BİT";
                break;
            default:
                resultString = "Geçersiz seçim yaptınız!!!";
        }
        return resultString;
    }
}