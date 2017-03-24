#version 330

layout(location = 0) in vec4 inVertex;
flat out vec3 sphereRelativePos;
flat out int instanceID;

void main(void) {
    gl_Position = inVertex;
    vec3 texPos =  vec3(inVertex.xy, 0.0f);
    vec3 sphereCenter = vec3(0, 0, -1);
    sphereRelativePos = normalize(texPos - sphereCenter);
    instanceID = gl_InstanceID;
}