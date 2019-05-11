package net.warpgame.engine.graphics.resource.mesh;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author MarconZet
 * Created 19.09.2018
 */
public class Model {
    private float[] model;
    private int[] index;

    private ByteBuffer vertices;
    private ByteBuffer indices;

    public Model(float[] v, float[] t, float[] n, int[] indices, int size){
        index = indices;
        model = new float[v.length + t.length + n.length];
        int i = 0, j = 0, k = 0;
        int c = 0;
        for (int l = 0; l < size; l++) {
            model[c++] = v[i++];
            model[c++] = v[i++];
            model[c++] = v[i++];
            model[c++] = t[j++];
            model[c++] = t[j++];
            model[c++] = n[k++];
            model[c++] = n[k++];
            model[c++] = n[k++];
        }
        fillBuffers();
    }

    private void fillBuffers() {
        vertices = BufferUtils.createByteBuffer(model.length * Vertex.sizeOf());
        FloatBuffer fb = vertices.asFloatBuffer();
        fb.put(model);

        indices = BufferUtils.createByteBuffer(index.length * 4);
        IntBuffer ib = indices.asIntBuffer();
        ib.put(index);
    }
}
