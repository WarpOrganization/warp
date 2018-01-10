package net.warpgame.engine.graphics.rendering.scene.gbuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL42;

/**
 * @author Jaca777
 * Created 2017-10-31 at 20
 */
public class GBuffer {

    /**
     * ID | TYPE              | SIZE (b) | VALUES (components)...
     * 0    GL_RGB8             24         [diffuse r; diffuse g; diffuse b]
     * 1    GL_R32UI            32         packed[normal_1 in 11; normal_2 in 11; flags in 10]
     * 2    GL_RGB4             12         [roughness; shininess; thread dir]
     * 3    GL_DEPTH32F         32         [log depth]
     *
     * Normals are encoded using:
     * http://johnwhite3d.blogspot.ca/2017/10/signed-octahedron-normal-encoding.html
     * http://jcgt.org/published/0003/02/01/paper.pdf
     *
     * TOTAL BITS: 100 (yay)
     *
     * FLAGS:
     *  0x01       0x02              0x04       0x08         0x10    -
     *  Beckmann | Heidrich-Seidel | Emissive | Subsurface | Metal | -
     *
     */

    private int width;
    private int height;

    private int[] textures = new int[4];

    public void generate() {
        GL11.glGenTextures(textures);
    }

    public void initWithSize(int width, int height) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[0]);
        GL42.glTexStorage2D(GL11.GL_TEXTURE_2D, 1, GL11.GL_RGBA8, width, height);
        setDefaultParams();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[1]);
        GL42.glTexStorage2D(GL11.GL_TEXTURE_2D, 1, GL30.GL_R32UI, width, height);
        setDefaultParams();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[2]);
        GL42.glTexStorage2D(GL11.GL_TEXTURE_2D, 1, GL11.GL_RGB4, width, height);
        setDefaultParams();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[3]);
        GL42.glTexStorage2D(GL11.GL_TEXTURE_2D, 1, GL30.GL_DEPTH_COMPONENT32F, width, height);
        setDefaultParams();
        this.width = width;
        this.height = height;
    }

    private void setDefaultParams() {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
    }


    public void destroy() {
        GL11.glDeleteTextures(textures);
    }

    public int getTextureName(int index) {
        return textures[index];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
