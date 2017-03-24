package pl.warp.engine.graphics.pipeline.rendering;

import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.framebuffer.CubemapFramebuffer;
import pl.warp.engine.graphics.mesh.Quad;
import pl.warp.engine.graphics.texture.Cubemap;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 11
 */
public abstract class CubemapTextureSource<T extends CubemapRenderingProgram> extends ProgramTextureSource<T, Cubemap> {

    private static final Matrix3f[] ROTATIONS = getLayerRotations();

    /*
    Face render order: Top, Right, Front, Bottom, Left, Back
     Quaternion[] rotations = {
         Quaternion.Euler(0, 90, 0), 1
         Quaternion.Euler(0, -90, 0), 2
         Quaternion.Euler(-90, 0, 0), 3
         Quaternion.Euler(90, 0, 0), 4
         Quaternion.Euler(0, 0, 0), 5
         Quaternion.Euler(0, 180, 0) 6
     };
     */
    private static  Matrix3f[] getLayerRotations() {
        Matrix3f[] rotations = new Matrix3f[6];

        setRotation(rotations, 0, 0, (float) (Math.PI / 2), 0f);
        setRotation(rotations, 1, 0f, -(float) (Math.PI / 2), 0f);
        setRotation(rotations, 2, (float) (Math.PI / 2) , 0, 0f);
        setRotation(rotations, 3,(float) -(Math.PI / 2), 0f, 0f);
        setRotation(rotations, 4, 0f,  0, 0);
        setRotation(rotations, 5, 0f, (float) Math.PI, 0);
        return rotations;
    }


    private static void setRotation(Matrix3f[] rotations, int index, float angleX, float angleY, float angleZ) {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotate(angleX, angleY, angleZ);
        rotations[index] = quaternionf.get(new Matrix3f());
    }

    public CubemapTextureSource(T program) {
        super(new CubemapFramebuffer(), program);
    }

    @Override
    protected void drawQuad(Quad quad) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        quad.drawInstanced(6);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    @Override
    public void init(Graphics graphics) {
        super.init(graphics);
    }

    @Override
    protected void prepareProgram(T program) {
        program.useMatrices(ROTATIONS);
    }

    @Override
    public void update() {
        super.update();
    }


}
