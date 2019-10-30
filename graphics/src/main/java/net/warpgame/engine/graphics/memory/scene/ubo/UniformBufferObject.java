package net.warpgame.engine.graphics.memory.scene.ubo;

import org.joml.Matrix4fc;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * @author MarconZet
 * Created 24.05.2019
 */
public class UniformBufferObject {
    public ByteBuffer data;

    public UniformBufferObject(Matrix4fc modelMatrix, Matrix4fc viewMatrix, Matrix4fc projMatrix) {
        data = BufferUtils.createByteBuffer((int)sizeOf());
        FloatBuffer fb = data.asFloatBuffer();
        modelMatrix.get(fb);
        fb.position(16);
        viewMatrix.get(fb);
        fb.position(32);
        projMatrix.get(fb);
    }

    public static long sizeOf(){
        return 3 * 4 * 4 * 4;
    }
}
