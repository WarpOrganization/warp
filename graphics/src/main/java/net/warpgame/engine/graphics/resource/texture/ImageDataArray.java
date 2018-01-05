package net.warpgame.engine.graphics.resource.texture;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 2016-07-18 at 15
 */
public final class ImageDataArray {
    private ByteBuffer[] data;
    private int width, height;

    public ImageDataArray(ByteBuffer[] data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public ByteBuffer[] getData() {
        return data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getArraySize() {
        return data.length;
    }
}
