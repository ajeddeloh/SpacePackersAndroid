package com.jeddelog.spacepackers;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.opengl.GLSurfaceView;

/**
 * Created by andrew on 3/26/14.
 */
public class GameActivity extends Activity {
    GLSurfaceView surface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        surface = new Surface(this);
        setContentView(surface);
    }
}
