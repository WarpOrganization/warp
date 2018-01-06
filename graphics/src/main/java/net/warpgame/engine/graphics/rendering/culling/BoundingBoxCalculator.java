package net.warpgame.engine.graphics.rendering.culling;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.rendering.scene.mesh.MeshProperty;
import net.warpgame.engine.graphics.rendering.scene.mesh.SceneMesh;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * @author Jaca777
 * Created 2017-09-30 at 23
 */


@Service
public class BoundingBoxCalculator {

    private MinMaxCalculator xCalculator = new MinMaxCalculator();
    private MinMaxCalculator zCalculator = new MinMaxCalculator();
    private MinMaxCalculator yCalculator = new MinMaxCalculator();

    /**
     * Returns bounding box of a mesh
     *
     * @param mesh  mesh to calculate bounding box
     * @return      bounding box of a mesh
     */
    public BoundingBox compute(SceneMesh mesh) {
        return computeBoundingBox(mesh);
    }

    private synchronized BoundingBox computeBoundingBox(SceneMesh mesh) {
        int vertexBuff = mesh.getVertexBuff();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuff);
        ByteBuffer bb = GL30.glMapBufferRange(GL15.GL_ARRAY_BUFFER, 0, mesh.getVertexCount()*4*3, GL30.GL_MAP_READ_BIT);
        FloatBuffer vertices = bb.asFloatBuffer();

        xCalculator.reset();
        yCalculator.reset();
        zCalculator.reset();
        while (vertices.hasRemaining()) {
            float x = vertices.get(), y = vertices.get(), z = vertices.get();
            xCalculator.update(x);
            yCalculator.update(y);
            zCalculator.update(z);
        }

        GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        return new BoundingBox(
                new Vector3f(xCalculator.max, yCalculator.max, zCalculator.max),
                new Vector3f(xCalculator.min, yCalculator.min, zCalculator.min)
        );
    }

    private static class MinMaxCalculator {
        private float min;
        private float max;

        public void update(float a) {
            if (a > max)
                max = a;
            if (a < min)
                min = a;
        }

        public float getMin() {
            return min;
        }

        public float getMax() {
            return max;
        }

        public void reset() {
            min = Float.MAX_VALUE;
            max = Float.MIN_VALUE;
        }
    }


}
