#version 100

uniform vec3 vLightDir;

varying vec3 vNormalPixel;

void main() {
    float brightness = max(0.0, dot(vNormalPixel, vLightDir));
    gl_FragColor = vec4(brightness, brightness, brightness, 1.0);
}