package com.jeddelog.spacepackers;

import android.opengl.Matrix;

import java.util.Stack;

/**
 * Created by andrew on 3/26/14.
 */
public class MatrixStack {
    float[] top;
    Stack<float[]> stack;

    public MatrixStack() {
        top = new float[16];
        Matrix.setIdentityM(top, 0);
        stack.push(top);
        top = new float[16];
        System.arraycopy(stack.peek(), 0, top, 0, 16);
    }

    public float[] getTop() {
        return top;
    }

    public void pop() {
        top = stack.pop();
    }
}