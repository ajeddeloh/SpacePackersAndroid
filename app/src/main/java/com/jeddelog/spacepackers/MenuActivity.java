package com.jeddelog.spacepackers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuActivity extends Activity implements View.OnClickListener {
    Button playButton, helpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        playButton = (Button)findViewById(R.id.button_play);
        helpButton = (Button)findViewById(R.id.button_help);
        playButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(playButton)) {
            Intent intent = new Intent(this, LevelSelectActivity.class);
            startActivity(intent);
        } else if (view.equals(helpButton)) {
            //go to help screen
        }
    }
}
