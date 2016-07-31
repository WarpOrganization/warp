package pl.warp.engine.graphics.postprocessing.lens;

import org.joml.Vector3f;
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
    private FloatBuffer flareColors;

    public FlareData(int size) {
        this.offsets = BufferUtils.createFloatBuffer(size);
        this.scales = BufferUtils.createFloatBuffer(size);
        this.textureIndices = BufferUtils.createIntBuffer(size);
        this.flareColors = BufferUtils.createFloatBuffer(size * 3);
    }

    public void clear() {
        offsets.clear();
        scales.clear();
        textureIndices.clear();
        flareColors.clear();
    }

    public void store(SingleFlare singleFlare) {
        offsets.put(singleFlare.getOffset());
        scales.put(singleFlare.getScale());
        textureIndices.put(singleFlare.getTextureIndex());
        Vector3f color = singleFlare.getColor();
        flareColors.put(color.x).put(color.y).put(color.z);
    }

    public void rewind() {
        offsets.rewind();
        scales.rewind();
        textureIndices.rewind();
        flareColors.rewind();
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

    public FloatBuffer getFlareColors() {
        return flareColors;
    }
}
