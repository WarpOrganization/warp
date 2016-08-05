package pl.warp.engine.graphics.mesh.shapes;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.mesh.VAOMesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 *         Created 2016-08-05 at 15
 */
public class Ring extends VAOMesh {

    private int divisions;
    private float startRadius;
    private float endRadius;

    public Ring(int divisions, float startRadius, float endRadius) {
        super(divisions * 6, divisions * 2);
        this.divisions = divisions;
        this.startRadius = startRadius;
        this.endRadius = endRadius;
        createShape();
    }

    private void createShape() {
        FloatBuffer vertices = FloatBuffer.allocate(this.vertices * 3);
        IntBuffer indices = IntBuffer.allocate(this.indices);
        for(int i = 0; i < divisions; i++) {
            float angle = (float) (2 * Math.PI * (i / (float) divisions));
            float xDir = (float) Math.cos(angle);
            float yDir = (float) Math.sin(angle);
            vertices.put(xDir * startRadius)
                    .put(yDir * startRadius)
                    .put(0.0f);
            vertices.put(xDir * endRadius)
                    .put(yDir * endRadius)
                    .put(0.0f);

            int offset = i * 4;
            indices.put(offset)
                    .put(offset + 1)
                    .put(offset + 2);
            indices.put(offset + 1)
                    .put(offset + 2)
                    .put(offset + 3);
        }

        vertices.flip();
        indices.flip();

        setVertexData(vertices);
        setIndexData(indices);
    }

    @Override
    public void render() {
        GL11.glDisable(GL11.GL_CULL_FACE);
        super.render();
        GL11.glEnable(GL11.GL_CULL_FACE);
    }
}
