package net.warpgame.engine.graphics.rendering.culling;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.rendering.scene.mesh.MeshProperty;
import net.warpgame.engine.graphics.rendering.scene.mesh.SceneMesh;

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

    public void compute(Component component) {
        if(component.hasProperty(MeshProperty.NAME)){
            MeshProperty property = component.getProperty(MeshProperty.NAME);
            BoundingBox boundingBox = computeBoundingBox(property.getMesh());
            component.addProperty(new BoundingBoxProperty(boundingBox));
        }
    }

    private synchronized BoundingBox computeBoundingBox(SceneMesh mesh) {
        int vertexBuff = mesh.getVertexBuff();
        FloatBuffer vertices = GL30.glMapBufferRange(vertexBuff, 0, mesh.getVertexCount(), GL30.GL_MAP_READ_BIT)
                .asFloatBuffer();

        xCalculator.reset();
        yCalculator.reset();
        zCalculator.reset();
        while (vertices.hasRemaining()) {
            float x1 = vertices.get(), y1 = vertices.get(), z1 = vertices.get();
            float x2 = vertices.get(), y2 = vertices.get(), z2 = vertices.get();
            xCalculator.update(x1, x2);
            yCalculator.update(y1, y2);
            zCalculator.update(z1, z2);
        }

        return new BoundingBox(
                new Vector3f(xCalculator.max, yCalculator.max, zCalculator.max),
                new Vector3f(xCalculator.min, yCalculator.min, zCalculator.min)
        );
    }

    private static class MinMaxCalculator {
        private float min;
        private float max;

        public void update(float a, float b) {
            if(a > b) {
                if(a > max) max = a;
                if(b < min) min = b;
            } else {
                if(b > max) max = b;
                if(a < min) min = a;
            }
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
