#version 430

//todo FUCK IT, JUST REDUCE
//todo RENAME BLOCK => GROUP

layout( std140, binding=4 ) buffer Vert {
    vec3 vertices[ ];
};

layout(local_size_x = %BLOCK_SIZE%, local_size_y = 1, local_size_z = 1) in;

layout(location = 0) uniform int verticesCount;
layout(location = 1) uniform int stride;

void findMinAndMax(int offset, int count) {
    int lastPos = offset + stride * (count - 1);
    float min = Vert.vertices[lastPos];
    float max = Vert.vertices[lastPos];
    for(int i = offset; i < lastPos - stride; i += stride * 2) {
        float a = Vert.vertices[i];
        float b = Vert.vertices[i + stride]
        if(a > b) {
           if(a > max) max = a;
           else if(b < min) min = b;
        } else {
           if(b > max) max = b;
           else if(a < min) min = a;
        }
    }
    Vert.vertices[offset] = max;
    Vert.vertices[offset + 1] = min;
}

int getOffset() {
    return gl_WorkGroupID.x * %BLOCK_SIZE% * 3 * %INVOCATION_SIZE% + gl_WorkGroupID.y;
}


void main() {
    int offset = getOffset();
    if(offset >= verticesCount * 3) return;
    reduceBlock(offset);

    barrier();

    if(gl_LocalInvocationID.x > 0) return;
    reduceGroup(groupOffset);
}


