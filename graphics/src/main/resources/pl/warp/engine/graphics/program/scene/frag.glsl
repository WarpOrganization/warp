#version 330
precision mediump float;

uniform sampler2D diffuseTexture;

in vec3 fsWorldPos;
in vec2 fsTexCoord;
in vec3 fsNormal;

layout(location = 0) out vec4 fragColor;

void main(void) {
    fragColor = texture(diffuseTexture, fsTexCoord);
}
