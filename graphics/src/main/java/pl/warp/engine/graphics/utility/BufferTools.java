package pl.warp.engine.graphics.utility;

import org.apache.commons.io.IOUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * @author Jaca777
 */
public class BufferTools {

    /**
     * Stores array in a direct buffer.
     * @param data Array to be stored in a direct buffer.
     * @return Direct buffer containing array.
     */
    public static FloatBuffer toDirectBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length)
                .put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Stores array in a direct buffer.
     * @param data Array to be stored in a direct buffer.
     * @return Direct buffer containing array.
     */
    public static IntBuffer toDirectBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length)
                .put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Stores array in a direct buffer.
     * @param data Array to be stored in a direct buffer.
     * @return Direct buffer containing array.
     */
    public static ShortBuffer toDirectBuffer(short[] data){
        ShortBuffer buffer = BufferUtils.createShortBuffer(data.length)
                .put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Stores array in a direct buffer.
     * @param data Array to be stored in a direct buffer.
     * @return Direct buffer containing array.
     */
    public static ByteBuffer toDirectBuffer(byte[] data) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(data.length)
                .put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Stores stream in a direct buffer.
     * @param stream InputStream to be stored in a direct buffer.
     * @return Direct buffer containing stream.
     */
    public static ByteBuffer toDirectBuffer(InputStream stream) throws IOException {
        byte[] data = IOUtils.toByteArray(stream);
        ByteBuffer buffer = BufferUtils.createByteBuffer(data.length)
                .put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Stores data in the buffer.
     * @param data Data to be stored in the buffer.
     * @param buffer A buffer, where data will be stored.
     */
    public void storeData(float[] data, FloatBuffer buffer){
        buffer.put(data);
        buffer.flip();
    }

    /**
     * Stores data in the buffer.
     * @param data Data to be stored in the buffer.
     * @param buffer A buffer, where data will be stored.
     */
    public void storeData(int[] data, IntBuffer buffer){
        buffer.flip();
        buffer.put(data);
        buffer.flip();
    }

    /**
     * Stores matrix in a direct buffer.
     * @param matrix Matrix to be stored in a direct buffer.
     * @return Direct buffer containing matrix.
     */
    public static FloatBuffer toDirectBuffer(Matrix4f matrix){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        return buffer;
    }

    /**
     * Stores vector in a direct buffer.
     * @param vector Vector to be stored in a direct buffer.
     * @return Direct buffer containing vector.
     */
    public static FloatBuffer toDirectBuffer(Vector4f vector){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        return vector.get(buffer);
    }

    /**
     * Stores matrix in a direct buffer.
     * @param matrix Matrix to be stored in a direct buffer.
     * @return Direct buffer containing matrix.
     */
    public static FloatBuffer toDirectBuffer(Matrix3f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
        return matrix.get(buffer);
    }

    /**
     * Stores matrix in the buffer.
     * @param matrix Matrix to be stored in the buffer.
     * @param buffer A buffer, where matrix will be stored.
     */
    public static FloatBuffer storeMat4(Matrix4f matrix, FloatBuffer buffer){
        return matrix.get(buffer);
    }

    /**
     * Stores vector in the buffer.
     * @param vector Vector to be stored in the buffer.
     * @param buffer A buffer, where vector will be stored.
     */
    public static FloatBuffer storeVec4(Vector4f vector, FloatBuffer buffer){
        return vector.get(buffer);
    }

    /**
     * Stores matrix in the buffer.
     * @param matrix Matrix to be stored in the buffer.
     * @param buffer A buffer, where matrix will be stored.
     */
    public static FloatBuffer storeMat3(Matrix3f matrix, FloatBuffer buffer) {
        return matrix.get(buffer);
    }
}
