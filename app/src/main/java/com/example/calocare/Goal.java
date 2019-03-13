package com.example.calocare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import NonActivityClasses.AppControl;

public class Goal extends AppCompatActivity {
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioGroup goal;

    private Button next;

    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        rb1 = findViewById(R.id.lose);
        rb2 = findViewById(R.id.maintain);
        rb3 = findViewById(R.id.gain);
        next = findViewById(R.id.next);

        // set listener when clicking button
        rb1.setOnCheckedChangeListener(nListener);
        rb2.setOnCheckedChangeListener(nListener);
        rb3.setOnCheckedChangeListener(nListener);

        pref = getSharedPreferences(AppControl.USER_PREF, Activity.MODE_PRIVATE);
        prefEditor = pref.edit();
        goal = findViewById(R.id.goal);

    }

    CompoundButton.OnCheckedChangeListener nListener = new CompoundButton.OnCheckedChangeListener() {
        //If something is checked, change onChecked
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                next.setEnabled(true);
        }
    };


    public void nextActivity(View v) {
        Intent nextActivity = new Intent(this, GiaoDienChinh.class);
        startActivity(nextActivity);
    }


    @Override
    protected void onResume() {
        super.onResume();
        int checkId = pref.getInt("userGoal", -1);

        // Avoid next button is enable while no radio button is checked since there is an onCheckedChangedListener
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
        int goal = 0;

        if (selectedId != -1) {
            if (selectedId == R.id.lose) {
                goal = 1;
            }
            if (selectedId == R.id.maintain) {
                goal = 2;
            }
            if (selectedId == R.id.gain) {
                goal = 3;
            }
        }
        prefEditor.putInt("userGoal", selectedId);
        prefEditor.putInt("userGoalVal", goal);
        prefEditor.commit();
    }

}
