package com.jeddelog.spacepackers;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Scanner;

/**
 * Created by andrew on 4/9/14.
 */
public class PlyModel {
    FloatBuffer vertices;
    FloatBuffer normals;
    FloatBuffer texCoords;
    ShortBuffer indices;
    int numFaces;

    PlyModel(float[] vertices, float[] normals, float[] texCoords, short[] indices) {
        if(vertices != null) {
            this.vertices = Util.createFloatBuffer(vertices);
        }
        if(normals != null) {
            this.normals = Util.createFloatBuffer(normals);
        }
        if(texCoords != null) {
            this.texCoords = Util.createFloatBuffer(texCoords);
        }
        if(indices != null) {
            this.indices = Util.createShortBuffer(indices);
        }
        numFaces = indices.length;
    }

    public static PlyModel loadModel(Context c, String filename) {
        AssetManager am = c.getAssets();
        Scanner s;
        try {
            s = new Scanner(am.open(filename));
        } catch (Exception e) {
            Log.e("FILE_OPEN_ERROR", "Error opening or reading from " + filename);
            return null;
        }
        int numVertices = 0;
        int numFaces = 0;
        s.useDelimiter("\n");
        String line = "";
        while(s.hasNext() && !line.equals("end_header")) {
            line = s.next();
            String[] lines = line.split(" ");
            if(lines[0].equals("element")) {
                if(lines[1].equals("vertex")) {
                    numVertices = Integer.parseInt(lines[2]);
                } else if(lines[1].equals("face")) {
                    numFaces = Integer.parseInt(lines[2]);
                }
            }
        }
        if(numVertices == 0 || numFaces == 0) {
            Log.e("INVALID_FILE", "Errors parsing " + filename);
            return null;
        }
        float[] vertices = new float[numVertices*3];
        float[] normals = new float[numVertices*3];
        float[] texCoords = null;
        short[] indices = new short[numFaces*3];

        boolean processingVertices = true;
        int i = 0;
        while(s.hasNext()) {
            line = s.next();
            if(processingVertices) {
                float[] data = Util.stringToFloatArray(line, " ");
                if(data.length > 2) {
                    vertices[i] = data[0];
                    vertices[i + 1] = data[1];
                    vertices[i + 2] = data[2];
                }
                if(data.length > 5) {
                    normals[i] = data[3];
                    normals[i+1] = data[4];
                    normals[i+2] = data[5];
                }
                if(data.length > 8) {
                    texCoords[i] = data[6];
                    texCoords[i+1] = data[7];
                    texCoords[i+2] = data[8];
                }
                i += 3;
                if(i >= 3 * numVertices) {
                    processingVertices = false;
                    i = 0;
                }
            } else { //processing faces
                short[] data = Util.stringToShortArray(line, " ");
                if(data[0] == 3) { //only trianginles
                    indices[i] = data[1];
                    indices[i+1] = data[2];
                    indices[i+2] = data[3];
                }
                i += 3;
            }
        }
        return new PlyModel(vertices, normals, texCoords, indices);
    }
}
