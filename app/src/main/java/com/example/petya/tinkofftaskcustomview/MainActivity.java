package com.example.petya.tinkofftaskcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CustomViewGroup mViewGroupUp;
    private CustomViewGroup mViewGroupDown;

    private String[] mNameMetro = {"Юго-Западная", "Беляево", "ВДНХ", "Охотный ряд",
                                    "Водный стадион", "Добрынинская", "Тульская",
                                    "Крымская", "Выхино", "Марксисткая"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewGroupUp = findViewById(R.id.custom_view_group_up);
        mViewGroupDown = findViewById(R.id.custom_view_group_down);
        int[] mColorMetro = getResources().getIntArray(R.array.colorMetro);

        for (int i = 0; i < mNameMetro.length / 2; i++) {
            MaterialButton view = (MaterialButton) getLayoutInflater().inflate(R.layout.button, null, false);
            view.setOnClickListener(this);
            view.setText(mNameMetro[i]);
            view.setTextColor(mColorMetro[i]);
            view.setIconTint(ColorStateList.valueOf(mColorMetro[i]));
            mViewGroupUp.addView(view);

            MaterialButton view2 = (MaterialButton) getLayoutInflater().inflate(R.layout.button, null, false);
            view2.setOnClickListener(this);
            view2.setText(mNameMetro[i + 5]);
            view2.setTextColor(mColorMetro[i + 5]);
            view2.setIconTint(ColorStateList.valueOf(mColorMetro[i + 5]));
            mViewGroupDown.addView(view2);
        }
    }

    @Override
    public void onClick(View v) {
        if (mViewGroupDown.isContains(v)) {
            mViewGroupDown.removeView(v);
            mViewGroupUp.addView(v);
        } else if (mViewGroupUp.isContains(v)){
            mViewGroupUp.removeView(v);
            mViewGroupDown.addView(v);
        }
    }
}
