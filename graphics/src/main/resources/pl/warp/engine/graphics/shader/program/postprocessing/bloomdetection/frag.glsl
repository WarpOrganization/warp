#version 330
precision highp float;

uniform sampler2D tex;
in vec2 vTexCoord;

uniform float threshold;
uniform bool cutOff = true;
uniform float maxBrightness = 3.0;

layout(location = 0) out vec4 fragColor;

float maxElem(vec3 vec);

void main(void){
    vec4 color = texture(tex, vTexCoord);
    float brightness = 0.2126 * color.r + 0.7152 * color.g + 0.0722 * color.b;
    if(cutOff){
        if(brightness > threshold) fragColor = color;
        else discard;
    } else fragColor = color * brightness;

    fragColor.r -= min(fragColor.r, 0.2126 * threshold);
    fragColor.g -= min(fragColor.g, 0.7152 * threshold);
    fragColor.b -= min(fragColor.b, 0.0722 * threshold);

    float maxElem = maxElem(fragColor.rgb);
    if(maxElem > maxBrightness)
        fragColor *= (maxBrightness / maxElem);
}

float maxElem(vec3 vec) {
    return max(vec.r, max(vec.g, vec.b));
}