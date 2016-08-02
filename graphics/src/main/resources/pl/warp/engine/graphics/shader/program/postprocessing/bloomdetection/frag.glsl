#version 330
precision highp float;

uniform sampler2D tex;
in vec2 vTexCoord;

uniform float threshold;
uniform bool cutOff = true;
uniform float maxBrightness = 3.0;
uniform float minBrightness = 0.01;

layout(location = 0) out vec4 fragColor;

float maxElem(vec3 vec);
float minElem(vec3 vec);

void main(void){
    vec4 color = texture(tex, vTexCoord);
    float brightness = 0.2126 * color.r + 0.7152 * color.g + 0.0722 * color.b;
    if(cutOff){
        if(brightness > threshold) fragColor = color;
        else discard;
    } else fragColor = color * brightness;

    fragColor -= normalize(color);

    float maxElem = maxElem(fragColor.rgb);
    if(maxElem > maxBrightness)
        fragColor *= (maxBrightness / maxElem);

    if(!(fragColor.r < 1000000.0)) discard;

    fragColor.a = 1.0f;
}

float maxElem(vec3 vec) {
    return max(vec.r, max(vec.g, vec.b));
}

float minElem(vec3 vec) {
    return min(vec.r, min(vec.g, vec.b));
}