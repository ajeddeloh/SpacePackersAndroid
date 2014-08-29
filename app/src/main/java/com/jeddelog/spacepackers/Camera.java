package com.jeddelog.spacepackers;

import android.opengl.Matrix;

/**
 * Created by andrew on 3/26/14.
 */
public class Camera {
    private float x, y, z, xAngle, yAngle;
    private float[] matrix = new float[16];

    public Camera(float x, float y, float z, float xAngle, float yAngle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xAngle = xAngle;
        this.yAngle = yAngle;
        updateMatrix();
    }

    private void updateMatrix() {
        Matrix.setIdentityM(matrix,0);
        Matrix.setLookAtM(matrix, 0, x,y,z, 0,0,0, 0,1,0);
    }

    public float[] getMatrix() {
        updateMatrix();
        return matrix;
    }
}
