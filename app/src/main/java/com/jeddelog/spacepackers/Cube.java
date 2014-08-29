package com.jeddelog.spacepackers;

import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * Created by andrew on 3/26/14.
 */
public class Cube implements Renderable {
    public final static int NUM_FLOATS_PER_COORD = 3;
    public final static int FLOAT_SIZE = 4;

    float x, y, z;
    float[] transform = new float[16];
    float[] normalRotation = new float[9];
    float[] lightDir = {1f,1f,1f};

    private static PlyModel model;

    public static void setModel(PlyModel model) {
        Cube.model = model;
    }

    public Cube() {
        x = 0;
        y = 0;
        z = 0;
    }

    public void render() {
        Matrix.setIdentityM(transform, 0);
        Matrix.rotateM(transform, 0, (System.currentTimeMillis() / 30) % 360, 0, 1, 0);
        Util.getRotationFrom4x4(normalRotation, transform);
        Matrix.translateM(transform, 0, x,y,z);

        GLES20.glUseProgram(Game.normalShaderProgram);

        int positionHandle = GLES20.glGetAttribLocation(Game.normalShaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, model.vertices);

        int normalHandle = GLES20.glGetAttribLocation(Game.normalShaderProgram, "vNormal");
        GLES20.glEnableVertexAttribArray(normalHandle);
        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false, 0, model.normals);

        int modelViewHandle = GLES20.glGetUniformLocation(Game.normalShaderProgram, "mModelView");
        int projectionHandle = GLES20.glGetUniformLocation(Game.normalShaderProgram, "mProjection");
        int normalRotationHandle = GLES20.glGetUniformLocation(Game.normalShaderProgram, "mNormalRotation");
        int lightDirHandle = GLES20.glGetUniformLocation(Game.normalShaderProgram, "vLightDir");
        GLES20.glUniformMatrix4fv(modelViewHandle, 1, false, transform, 0);
        GLES20.glUniformMatrix4fv(projectionHandle, 1, false, Game.projection, 0);
        GLES20.glUniformMatrix3fv(normalRotationHandle, 1, false, normalRotation, 0);
        GLES20.glUniform3f(lightDirHandle, 0.57735f, 0.57735f, 0.57735f);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.numFaces, GLES20.GL_UNSIGNED_SHORT, model.indices);

        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(normalHandle);
    }
}
