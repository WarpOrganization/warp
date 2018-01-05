
vec3 projectToPlane(vec3 point, vec3 planePoint, vec3 planeNormal) {
    vec3 v = point - planePoint;
    float len = dot(v, planeNormal);
    vec3 d = len * planeNormal;
    return (point - d);
}