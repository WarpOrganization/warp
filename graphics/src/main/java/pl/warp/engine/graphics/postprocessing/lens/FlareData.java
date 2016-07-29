package pl.warp.engine.graphics.postprocessing.lens;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 *         Created 2016-07-30 at 00
 */
class FlareData {
    private FloatBuffer offsets;
    private FloatBuffer scales;
    private IntBuffer textureIndices;

    public FlareData(int size) {
        this.offsets = BufferUtils.createFloatBuffer(size);
        this.scales = BufferUtils.createFloatBuffer(size);
        this.textureIndices = BufferUtils.createIntBuffer(size);
    }

    public FloatBuffer getOffsets() {
        return offsets;
    }

    public FloatBuffer getScales() {
        return scales;
    }

    public IntBuffer getTextureIndices() {
        return textureIndices;
    }

    public void clear() {
        offsets.clear();
        scales.clear();
        textureIndices.clear();
    }

    public void store(SingleFlare singleFlare) {
        offsets.put(singleFlare.getOffset());
        scales.put(singleFlare.getScale());
        textureIndices.put(singleFlare.getTextureIndex());
    }

    public void flip() {
        offsets.flip();
        scales.flip();
        textureIndices.flip();
    }
}
