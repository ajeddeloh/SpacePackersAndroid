#version 100
attribute vec4 vPosition;
attribute vec3 vNormal;

uniform mat3 mNormalRotation;
uniform mat4 mModelView;
uniform mat4 mProjection;

varying vec3 vNormalPixel;

void main() {
    vNormalPixel = mNormalRotation * vNormal;
    gl_Position = mProjection * mModelView * vPosition;
}