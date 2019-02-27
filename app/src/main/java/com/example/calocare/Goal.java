package com.example.calocare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import NonActivityClasses.AppControl;
import NonActivityClasses.UserInfo;

public class Goal extends AppCompatActivity {
    private RadioButton r_1;
    private RadioButton r_2;
    private RadioButton r_3;
    private Button button4;
    private RadioGroup goal;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        r_1 = findViewById(R.id.radioButton);
        r_2 = findViewById(R.id.radioButton2);
        r_3 = findViewById(R.id.radioButton3);
        button4 = findViewById(R.id.next);

        r_1.setOnCheckedChangeListener(nListener);
        r_2.setOnCheckedChangeListener(nListener);
        r_3.setOnCheckedChangeListener(nListener);

        pref = getSharedPreferences(AppControl.PREF, Activity.MODE_PRIVATE);
        prefEditor = pref.edit();
        goal = findViewById(R.id.goal);

    }
    CompoundButton.OnCheckedChangeListener nListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                button4.setEnabled(true);
            }else {
                button4.setEnabled(false);
            }
        }
    };


    public void nextActivity(View v) {
        Intent nextActivity = new Intent(this, SpinnerActivity.class);
        startActivity(nextActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int checkId = pref.getInt("userGoalNha", -1);

        if (checkId == -1) {
            goal.clearCheck();
        } else {
            goal.check(checkId);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        int selectedId = goal.getCheckedRadioButtonId();
        int a = 0;


        if (selectedId != -1) {


            if (selectedId == R.id.radioButton) {
                a = 1;
            }
            if (selectedId == R.id.radioButton2) {
                a = 2;
            }
            if (selectedId == R.id.radioButton3) {
                a = 3;
            }


            UserInfo.getInstance().setGoalStatus(a);
        }

        prefEditor.putInt("userGoal", selectedId);
        prefEditor.commit();
    }
}
