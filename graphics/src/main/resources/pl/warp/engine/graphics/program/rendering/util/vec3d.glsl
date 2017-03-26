float lineToPointDistance(vec3 point, vec3 line1, vec3 line2){
        float areaTwice = length(cross((point - line1), (point - line2)));
        float baseLength = length(line2 - line1);
        return areaTwice / baseLength;
}