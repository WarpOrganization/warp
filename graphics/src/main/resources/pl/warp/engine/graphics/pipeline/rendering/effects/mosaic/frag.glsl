#version 330

in vec2 vTexCoord;

uniform sampler2D tex;
uniform float width;
uniform float height;

uniform float tileRadius;
uniform float innerTileRadius;
uniform vec3 backgroundColor = vec3(0.0, 0.0, 0.0);

const float interpolationTileSize = 0.7;

vec4 interpolateColor(vec4 tileColor, float distance){
    if(distance < innerTileRadius) return tileColor;
    else if(distance > innerTileRadius + (1 + interpolationTileSize) * (tileRadius - innerTileRadius)) return vec4(backgroundColor, 1.0f);
    else {
        float k = 1 - (distance - innerTileRadius) / (interpolationTileSize * (tileRadius - innerTileRadius));
        return k * tileColor + (1 - k) * vec4(backgroundColor, 1.0f);
    }
}

void main(){
    float realRadius = tileRadius * width * 2;
    vec2 positionIndex = floor(vTexCoord * vec2(width, height) / realRadius);
    vec2 position = (positionIndex + 0.5) * tileRadius * 2;
    float distance = distance(vTexCoord * vec2(1, height / width), position);
    gl_FragColor = interpolateColor(texture(tex, position), distance);
}