package com.jeddelog.spacepackers;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by andrew on 3/26/14.
 */
public class Game implements GLSurfaceView.Renderer {
    private int width, height;
    private float aspect;
    public static float[] projection;
    private Cube cube;
    private Camera cam;
    static int normalShaderProgram;
    Context context;
    long timeLast;
    int frames = 0;

    float[] perspective = new float[16];

    public Game(Context context) {
        cube = new Cube();

        cam = new Camera(2,0,2, 0, 0);
        this.context = context;
        timeLast = System.currentTimeMillis();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        normalShaderProgram = Util.createProgram(context, "normal.vert", "normal.frag");
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glClearColor(.1f,.1f,.1f,0f);

        //setup matrices
        projection = new float[16];
        Matrix.setIdentityM(projection, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        this.width = width;
        this.height = height;
        aspect = (float)width/height;
        GLES20.glViewport(0,0,width,height);
        Matrix.perspectiveM(perspective, 0, 90, aspect, 0.1f, 1000f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.multiplyMM(Game.projection, 0, perspective, 0, cam.getMatrix(), 0);

        cube.render();

        frames ++;
        long thisTime = System.currentTimeMillis();
        if(thisTime - timeLast > 1000) {
            Log.e("FRAMERATE", "" + (frames*1000 / (thisTime-timeLast)));
            timeLast = thisTime;
            frames = 0;
        }
    }
}
