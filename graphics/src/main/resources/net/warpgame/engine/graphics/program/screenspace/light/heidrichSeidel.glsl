float getHeidrichSeidelRadiance(
    float threadDir,
    float specExp,
    vec3 normal,
    vec3 toSource,
    vec3 toCamera
) {
    float specular = dot(normal,toSource);
    vec3 t = cross(normal, normalize(vec3(1.0, 1, 0)));
    float ldott = dot(toSource, t);
    float vdott = dot(toCamera, t);
    return pow(sin(ldott)*sin(vdott) + cos(ldott)*cos(vdott), specExp) * specular;
}