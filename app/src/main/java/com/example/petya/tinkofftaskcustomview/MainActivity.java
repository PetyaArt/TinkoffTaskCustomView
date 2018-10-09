package com.example.petya.tinkofftaskcustomview;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.chip.Chip;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CustomViewGroup mViewGroupUp;
    private CustomViewGroup mViewGroupDown;

    private String[] mNameMetro = {"Юго-Западная", "Беляево", "ВДНХ", "Охотный ряд",
                                    "Водный стадион", "Добрынинская", "Тульская",
                                    "Крымская", "Выхино", "Марксисткая",
                                    "Шаболовская", "Киевская", "Тульская",
                                    "Ленинский проспект", "ЗИЛ", "Авиамоторная",
                                    "Курская", "Речной вокзал", "Единороги", "Теплый Стан"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewGroupUp = findViewById(R.id.custom_view_group_up);
        mViewGroupDown = findViewById(R.id.custom_view_group_down);
        int[] mColorMetro = getResources().getIntArray(R.array.colorMetro);

        for (int i = 0; i < mNameMetro.length / 2; i++) {
            Chip view = (Chip) getLayoutInflater().inflate(R.layout.chip, mViewGroupUp, false);
            view.setOnCloseIconClickListener(this);
            view.setText(mNameMetro[i]);
            view.setTextColor(mColorMetro[i]);
            view.setChipIconTint(ColorStateList.valueOf(mColorMetro[i]));
            mViewGroupUp.addView(view);

            Chip view2 = (Chip) getLayoutInflater().inflate(R.layout.chip, mViewGroupDown, false);
            view2.setOnCloseIconClickListener(this);
            view2.setText(mNameMetro[i + mNameMetro.length / 2]);
            view2.setTextColor(mColorMetro[i + mNameMetro.length / 2]);
            view2.setChipIconTint(ColorStateList.valueOf(mColorMetro[i + mNameMetro.length / 2]));
            mViewGroupDown.addView(view2);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getParent() == mViewGroupDown) {
            mViewGroupDown.removeView(v);
            mViewGroupUp.addView(v);
        } else {
            mViewGroupUp.removeView(v);
            mViewGroupDown.addView(v);
        }
    }
}
