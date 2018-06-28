#version 330

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform mat3 rotationMatrix;

uniform mat4 jointTransforms[MAX_JOINTS];
uniform bool animated = false;

layout(location = 0) in vec3 inVertex;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;
layout(location = 3) in vec3 inWeights;
layout(location = 4) in ivec3 inJointIndices;

out vec3 oWorldPos;
out vec2 oTexCoord;
out vec3 oNormal;

void main(void) {

    vec4 totalLocalPos = vec4(0.0);
    vec3 totalNormal = vec3(4.0);

    if(animated) {
        for(int i = 0; i < MAX_WEIGHTS; i++) {
            mat4 jointTransform = jointTransforms[inJointIndices[i]];
            vec4 posePosition = jointTransform * vec4(inVertex, 1.0);
            totalLocalPos += posePosition * inWeights[i];

            vec4 worldNormal = jointTransform * vec4(inNormal, 0.0);
            totalNormal += worldNormal.xyz * inWeights[i];
        }
    } else {
        totalLocalPos = vec4(inVertex, 1.0);
        totalNormal = inNormal;
    }

    vec4 vPos = modelMatrix * totalLocalPos;
    oWorldPos = vPos.xyz / vPos.w;

    oNormal = rotationMatrix * totalNormal.xyz;
    oTexCoord = inTexCoord;

    gl_Position = projectionMatrix * viewMatrix * vPos;
}