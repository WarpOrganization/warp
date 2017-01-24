#version 330
precision highp float;

uniform sampler2D tex;
uniform float exposure;
in vec2 vTexCoord;

layout(location = 0) out vec4 fragColor;

void main(void) {
    vec4 color = texture(tex, vTexCoord);
    fragColor.rgb = 1 - exp2(-color * exposure);
    fragColor.a = 1.0;
}