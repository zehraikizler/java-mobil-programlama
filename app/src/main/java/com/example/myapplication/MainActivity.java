package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button convert = findViewById(R.id.convert);
        Button random = findViewById(R.id.random);
        Button sms = findViewById(R.id.sms);

        TextView text = findViewById(R.id.textView);
        TextView text1 = findViewById(R.id.textView2);

        ObjectAnimator fadein = ObjectAnimator.ofFloat(text,"alpha",0f,1f);
        fadein.setDuration(2000);
        fadein.setInterpolator(new AccelerateDecelerateInterpolator());
        fadein.start();

        ObjectAnimator fadein1 = ObjectAnimator.ofFloat(text1,"alpha",0f,1f);
        fadein1.setDuration(3000);
        fadein1.setInterpolator(new AccelerateDecelerateInterpolator());
        fadein1.start();

        animasyonbtn(convert, 0, 800);
        animasyonbtn(random, 0, 900);
        animasyonbtn(sms, 0, 1000);


        try {
            convert.setOnClickListener(new View.OnClickListener(){
                @Override
                public  void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,ConvertActivity.class);
                        startActivity(intent);
                }
            });

            random.setOnClickListener(new View.OnClickListener(){
                @Override
                public  void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,RandomActivity.class);
                    startActivity(intent);
                }
            });

            sms.setOnClickListener(new View.OnClickListener(){
                @Override
                public  void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,SmsActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {}
    }

    private  void animasyonbtn(Button btn, long start, long stop){
        ObjectAnimator translateX = ObjectAnimator.ofFloat(btn,"translationX",0f,400f);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(btn,"translationY",0f,1000f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateX, translateY);
        animatorSet.setStartDelay(start);
        animatorSet.setDuration(stop);
        animatorSet.start();
    }
}