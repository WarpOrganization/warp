#version 330

// borrowed from:
// https://www.shadertoy.com/view/ldXGW4

in vec2 vTexCoord;

// change these values to 0.0 to turn off individual effects
uniform float vertJerkOpt = 1.0;
uniform float vertMovementOpt = 1.0;
uniform float bottomStaticOpt = 1.0;
uniform float scalinesOpt = 1.0;
uniform float rgbOffsetOpt = 1.0;
uniform float horzFuzzOpt = 1.0;

uniform sampler2D tex;
uniform int globalTime;

#include "util/noise2d"

float staticV(vec2 uv) {
    float time = globalTime * 0.001;
    float staticHeight = snoise(vec2(9.0,time*1.2+3.0))*0.3+5.0;
    float staticAmount = snoise(vec2(1.0,time*1.2-6.0))*0.1+0.3;
    float staticStrength = snoise(vec2(-9.75,time*0.6-3.0))*2.0+2.0;
    return (1.0-step(snoise(vec2(5.0*pow(time,2.0)+pow(uv.x*7.0,1.2),pow((mod(time,100.0)+100.0)*uv.y*0.3+3.0,staticHeight))),staticAmount))*staticStrength;
}


void main(){
    float time = globalTime * 0.001;
    float jerkOffset = (1.0-step(snoise(vec2(time*1.3,5.0)),0.8))*0.05;

    float fuzzOffset = snoise(vec2(time*15.0,vTexCoord.y*80.0))*0.003;
    float largeFuzzOffset = snoise(vec2(time*1.0,vTexCoord.y*25.0))*0.004;

    float vertMovementOn = (1.0-step(snoise(vec2(time*0.2,8.0)),0.4))*vertMovementOpt;
    float vertJerk = (1.0-step(snoise(vec2(time*1.5,5.0)),0.6))*vertJerkOpt;
    float vertJerk2 = (1.0-step(snoise(vec2(time*5.5,5.0)),0.2))*vertJerkOpt;
    float yOffset = abs(sin(time)*4.0)*vertMovementOn+vertJerk*vertJerk2*0.3;
    float y = mod(vTexCoord.y+yOffset,1.0);


    float xOffset = (fuzzOffset + largeFuzzOffset) * horzFuzzOpt;

    float staticVal = 0.0;

    for (float y = -1.0; y <= 1.0; y += 1.0) {
        float maxDist = 5.0/200.0;
        float dist = y/200.0;
        staticVal += staticV(vec2(vTexCoord.x,vTexCoord.y+dist))*(maxDist-abs(dist))*1.5;
    }

    staticVal *= bottomStaticOpt;

    float red = texture( tex,  vec2(vTexCoord.x + xOffset -0.01*rgbOffsetOpt,y)).r+staticVal;
    float green = texture( tex,  vec2(vTexCoord.x + xOffset,   y)).g+staticVal;
    float blue = texture( tex,  vec2(vTexCoord.x + xOffset +0.01*rgbOffsetOpt,y)).b+staticVal;

    vec3 color = vec3(red,green,blue);
    float scanline = sin(vTexCoord.y*800.0)*0.04*scalinesOpt;
    color -= scanline;

    gl_FragColor = vec4(color,1.0);
}
