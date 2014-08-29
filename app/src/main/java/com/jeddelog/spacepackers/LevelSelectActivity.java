package com.jeddelog.spacepackers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelSelectActivity extends Activity implements View.OnClickListener {
    Button continueButton, backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        continueButton = (Button)findViewById(R.id.button_continue);
        backButton = (Button)findViewById(R.id.button_back);
        continueButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(continueButton)) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        } else if (view.equals(backButton)) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
    }
}
