package com.jeddelog.spacepackers;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by andrew on 3/26/14.
 */
public class Surface extends GLSurfaceView {
    Renderer renderer;
    public Surface(Context context) {
        super(context);
        Cube.setModel(PlyModel.loadModel(context, "cube.ply"));
        setEGLContextClientVersion(2);
        renderer = new Game(context);
        setRenderer(renderer);
    }
}
