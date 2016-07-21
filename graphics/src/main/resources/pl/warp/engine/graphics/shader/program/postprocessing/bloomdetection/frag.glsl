#version 330
precision highp float;

uniform sampler2D tex;
in vec2 vTexCoord;

uniform float threshold = 0.7;
uniform bool cutOff = true;

layout(location = 0) out vec4 fragColor;

void main(void){
    vec4 color = texture(tex, vTexCoord);
    float brightness = 0.2126 * color.r + 0.7152 * color.g + 0.0722 * color.b;
    if(cutOff){
        if(brightness > threshold) fragColor = color;
        else discard;
    } else fragColor = color * brightness;
}