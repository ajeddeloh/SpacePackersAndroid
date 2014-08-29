package com.jeddelog.spacepackers;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by andrew on 3/26/14.
 * utility class
 */
public class Util {

    public static int loadShader(Context context, int type, String filename) {
        String content = "";
        AssetManager am = context.getAssets();
        try {
            Scanner scanner = new Scanner(am.open(filename));
            scanner.useDelimiter("\\Z");
            content = scanner.next();
        } catch (IOException e) {
            Log.e("LOAD ERROR", "Failed to load file " + filename);
            e.printStackTrace();
        }
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, content);
        GLES20.glCompileShader(shader);
        int[] compile = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compile, 0);
        Log.e("SHADER STATE", compile[0] == GLES20.GL_TRUE ? "Compiled ok" : "Error compiling");
        Log.e("SHADER_STATE", GLES20.glGetShaderInfoLog(shader));
        return shader;
    }

    public static int createProgram(int vertex, int fragment) {
        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertex);
        GLES20.glAttachShader(program, fragment);
        GLES20.glLinkProgram(program);
        Log.e("SHADER STATE", "Just attached and linked program " + program);
        Util.logGLError();
        Log.e("SHADER STATE", GLES20.glGetProgramInfoLog(program));
        int link[] = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, link, 0);
        Log.e("SHADER STATE", link[0] == GLES20.GL_TRUE ? "Linked ok" : "Error linking");
        return program;
    }

    public static int createProgram(Context context, String vertexFname, String fragmentFname) {
        int vert = loadShader(context, GLES20.GL_VERTEX_SHADER, vertexFname);
        int frag = loadShader(context, GLES20.GL_FRAGMENT_SHADER, fragmentFname);
        Log.e("SHADER STATE", "vert: " + vert +" | frag: " + frag);
        return createProgram(vert, frag);
    }

    public static FloatBuffer createFloatBuffer(float[] src) {
        FloatBuffer result;
        ByteBuffer tmp = ByteBuffer.allocateDirect(src.length * 4); //4 bytes per float
        tmp.order(ByteOrder.nativeOrder());
        result = tmp.asFloatBuffer();
        result.put(src);
        result.position(0);
        return result;
    }

    public static FloatBuffer createFloatBuffer(ArrayList<Float> src) {
        return null;
    }

    public static void logGLError() {
        int err = GLES20.glGetError();
        if(err != GLES20.GL_NO_ERROR) {
            Log.e("GL_ERROR", GLU.gluErrorString(err));
        } else {
            Log.e("GL_ERROR", "NO ERROR");
        }
    }

    public static void getRotationFrom4x4(float[] result, float[] src) {
        result[0] = src[0];
        result[1] = src[1];
        result[2] = src[2];
        result[3] = src[4];
        result[4] = src[5];
        result[5] = src[6];
        result[6] = src[8];
        result[7] = src[9];
        result[8] = src[10];
    }

    public static int[] stringToIntArray(String in, String delimitter) {
        String[] tmp = in.split(delimitter);
        int[] ret = new int[tmp.length];
        for(int i = 0; i < tmp.length; ++i) {
            ret[i] = Integer.parseInt(tmp[i]);
        }
        return ret;
    }

    public static float[] stringToFloatArray(String in, String delimitter) {
        String[] tmp = in.split(delimitter);
        float[] ret = new float[tmp.length];
        for(int i = 0; i < tmp.length; ++i) {
            ret[i] = Float.parseFloat(tmp[i]);
        }
        return ret;
    }
    public static short[] stringToShortArray(String in, String delimitter) {
        String[] tmp = in.split(delimitter);
        short[] ret = new short[tmp.length];
        for(int i = 0; i < tmp.length; ++i) {
            ret[i] = Short.parseShort(tmp[i]);
        }
        return ret;
    }

    public static IntBuffer createIntBuffer(int[] src) {
        IntBuffer result;
        ByteBuffer tmp = ByteBuffer.allocateDirect(src.length * 4); //4 bytes per int
        tmp.order(ByteOrder.nativeOrder());
        result = tmp.asIntBuffer();
        result.put(src);
        result.position(0);
        return result;
    }

    public static ShortBuffer createShortBuffer(short[] src) {
        ShortBuffer result;
        ByteBuffer tmp = ByteBuffer.allocateDirect(src.length * 2); //4 bytes per short
        tmp.order(ByteOrder.nativeOrder());
        result = tmp.asShortBuffer();
        result.put(src);
        result.position(0);
        return result;
    }
}
