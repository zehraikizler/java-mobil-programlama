package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {
    int minvalue, maxvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        LinearLayout linearLayout =findViewById(R.id.linearlayout);
        EditText adetv =findViewById(R.id.adet);
        EditText minv = findViewById(R.id.min);
        EditText maxv =findViewById(R.id.max);
        try {
            maxv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_DONE){
                        int adet = Integer.parseInt(adetv.getText().toString());
                        minvalue = Integer.parseInt(minv.getText().toString());
                        maxvalue = Integer.parseInt(maxv.getText().toString());

                        linearLayout.removeAllViews();

                        for (int i = 0; i < adet; i++){
                            progressBar_ekle(RandomActivity.this,linearLayout);
                        }
                        return true;
                    }
                    return false;
                }
            });

        }catch (Exception e){}
    }
    private void progressBar_ekle(Context context, LinearLayout linearLayout){
        int min, max, nerede;
        do {
            min = minvalue + new Random().nextInt(maxvalue - minvalue);
            max = min + new Random().nextInt(maxvalue - min + 1);
            nerede = min + new Random().nextInt(max - min + 1);
        }while (min == max || min == nerede || nerede ==max);
        String yuzde1 = Double.toString(((double) (nerede - min)/(max - min)) *  100);
        String part[] = yuzde1.split("\\.");
        String yuzde = part[0];

        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));

        //nerede + yuzde text olusturma
        TextView textyuzde = new TextView(context);
        textyuzde.setId(View.generateViewId());
        String str = nerede + " %" + yuzde;
        textyuzde.setText(str);
        ConstraintLayout.LayoutParams paramsyuzde = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsyuzde.topToTop = ConstraintSet.PARENT_ID;
        paramsyuzde.startToStart = ConstraintSet.PARENT_ID;
        paramsyuzde.setMargins(dpToPx(180),dpToPx(30),0,0);
        textyuzde.setLayoutParams(paramsyuzde);
        constraintLayout.addView(textyuzde);

        //mintext olusturma
        TextView textmin = new TextView(context);
        textmin.setId(View.generateViewId());
        textmin.setText(String.valueOf(min));
        ConstraintLayout.LayoutParams paramsmin = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsmin.topToTop = ConstraintSet.PARENT_ID;
        paramsmin.startToStart = ConstraintSet.PARENT_ID;
        paramsmin.setMargins(dpToPx(100),dpToPx(40),0,0);
        textmin.setLayoutParams(paramsmin);
        constraintLayout.addView(textmin);

        //maxtext olusturma
        TextView textmax = new TextView(context);
        textmax.setId(View.generateViewId());
        textmax.setText(String.valueOf(max));
        ConstraintLayout.LayoutParams paramsmax = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsmax.topToTop = ConstraintSet.PARENT_ID;
        paramsmax.endToEnd = ConstraintSet.PARENT_ID;
        paramsmax.setMargins(0,dpToPx(40),dpToPx(100),0);
        textmax.setLayoutParams(paramsmax);
        constraintLayout.addView(textmax);

        //progressBar olusturma
        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsPB = new ConstraintLayout.LayoutParams(
                dpToPx(150),
                dpToPx(20)
        );
        paramsPB.topToTop = ConstraintSet.PARENT_ID;
        paramsPB.startToStart = textmin.getId();
        paramsPB.endToEnd = textmax.getId();
        paramsPB.setMargins(dpToPx(30),dpToPx(40),dpToPx(30),dpToPx(100));
        progressBar.setLayoutParams(paramsPB);
        progressBar.setMin(0);
        progressBar.setProgress(Integer.parseInt(yuzde));
        progressBar.setMax(100);
        constraintLayout.addView(progressBar);

        linearLayout.addView(constraintLayout);
    }
    public int dpToPx(int dp){
        float density =getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}