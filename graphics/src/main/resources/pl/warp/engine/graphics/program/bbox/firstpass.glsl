#version 430

layout( std140, binding=4 ) buffer Vert {
    vec3 vertices[ ];
};

layout(local_size_x = %GROUP_SIZE%, local_size_y = 1, local_size_z = 1) in;

layout(location = 0) uniform int verticesCount;

void findExtremum(int offset, int count, int component) {
    int lastPos = offset + stride * (count - 1);
    float min = Vert.vertices[lastPos];
    float max = Vert.vertices[lastPos];
    for(int i = offset; i < lastPos; i += stride * 2) {
        float a = Vert.vertices[i];
        float b = Vert.vertices[i + stride];
        if(a > b) {
           if(a > max) max = a;
           else if(b < min) min = b;
        } else {
           if(b > max) max = b;
           else if(a < min) min = a;
        }
    }
    Vert.vertices[offset][component] = max;
    Vert.vertices[offset + 1][component] = min;
}

int getOffset() {
    return gl_WorkGroupID.x * %GROUP_SIZE% * 3 * %INVOCATION_SIZE% + glLocalInvocationID * %INVOCATION_SIZE% + gl_WorkGroupID.y;
}


void main() {
    int offset = getOffset();
    int vertex = offset / 3;
    int count = min(verticesCount - vertex, %INVOCATION_SIZE%);
    int component = gl_WorkGroupID.y;
    findExtremum(offset, count);
}


