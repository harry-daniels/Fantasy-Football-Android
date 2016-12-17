package com.daniels.harry.assignment.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.daniels.harry.assignment.R;

public class TeamEditorActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_editor);

        getSupportActionBar().setElevation(0);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_select_gk:
                break;
            case R.id.btn_select_lb:
                break;
            case R.id.btn_select_lcb:
                break;
            case R.id.btn_select_rcb:
                break;
            case R.id.btn_select_rb:
                break;
            case R.id.btn_select_lm:
                break;
            case R.id.btn_select_lcm:
                break;
            case R.id.btn_select_rcm:
                break;
            case R.id.btn_select_rm:
                break;
            case R.id.btn_select_ls:
                break;
            case R.id.btn_select_rs:
                break;
        }
    }
}
