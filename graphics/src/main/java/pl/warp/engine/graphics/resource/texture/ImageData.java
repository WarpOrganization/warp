package pl.warp.engine.graphics.resource.texture;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 2016-07-18 at 15
 */
public final class ImageData {
    private ByteBuffer buffer;
    private int w, h;

    public ImageData(ByteBuffer buffer, int w, int h) {
        this.buffer = buffer;
        this.w = w;
        this.h = h;
    }

    public ByteBuffer getData() {
        return buffer;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

}
