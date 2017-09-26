#version 330
precision mediump float;

uniform sampler2D diffuseTexture;

in vec3 oWorldPos;
in vec2 oTexCoord;
in vec3 oNormal;

layout(location = 0) out vec4 fragColor;

void main(void) {
    fragColor = texture(diffuseTexture, oTexCoord);
}
