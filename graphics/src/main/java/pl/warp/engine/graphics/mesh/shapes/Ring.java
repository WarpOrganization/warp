package pl.warp.engine.graphics.mesh.shapes;

import org.lwjgl.BufferUtils;
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
        FloatBuffer vertices = BufferUtils.createFloatBuffer(this.vertices * 3);
        FloatBuffer texCoords  = BufferUtils.createFloatBuffer(this.vertices * 2);
        FloatBuffer normals  = BufferUtils.createFloatBuffer(this.vertices * 3);
        IntBuffer indices = BufferUtils.createIntBuffer(this.indices);
        for(int i = 0; i < divisions; i++) {
            float angle = (float) (2 * Math.PI * (i / (float) divisions));
            float xDir = (float) Math.cos(angle);
            float zDir = (float) Math.sin(angle);
            vertices.put(xDir * startRadius)
                    .put(0.0f)
                    .put(zDir * startRadius);
            vertices.put(xDir * endRadius)
                    .put(0.0f)
                    .put(zDir * endRadius);

            int offset = i * 2;
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
        setTexCoordData(texCoords);
        setNormalData(normals);
        setIndexData(indices);
    }

    @Override
    public void render() {
        GL11.glDisable(GL11.GL_CULL_FACE);
        super.render();
        GL11.glEnable(GL11.GL_CULL_FACE);
    }
}
