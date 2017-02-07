package pl.warp.engine.graphics.particles;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 15
 */
public class ParticleSystemStorage {

    private static final int INITIAL_SYSTEMS_AMOUNT = 100;

    private int systemsNumber = 0;
    private List<ParticleSystemData> systems;

    public ParticleSystemStorage() {
        this.systems = Stream.generate(ParticleSystemData::new)
                .limit(INITIAL_SYSTEMS_AMOUNT)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void reset() {
        systemsNumber = 0;
    }

    public void add(ParticleSystem system, Matrix4f matrix) {
        if (systemsNumber == INITIAL_SYSTEMS_AMOUNT - 1)
            growList();
        ParticleSystemData data = systems.get(systemsNumber);
        data.setSystem(system);
        data.setTransformation(matrix);
        systemsNumber++;
    }

    private void growList() {
        systems.addAll(Stream.generate(ParticleSystemData::new)
                .limit(systems.size() / 2)
                .collect(Collectors.toCollection(ArrayList::new)));
    }

    public int getSystemsNumber() {
        return systemsNumber;
    }

    public List<ParticleSystemData> getSystems() {
        return systems;
    }

    static class ParticleSystemData {
        private Matrix4f transformation;
        private ParticleSystem system;

        public ParticleSystemData() {
            this.transformation = new Matrix4f();
        }

        public Matrix4f getTransformation() {
            return transformation;
        }

        public void setTransformation(Matrix4f transformation) {
            this.transformation.set(transformation);
        }

        public ParticleSystem getSystem() {
            return system;
        }

        public void setSystem(ParticleSystem system) {
            this.system = system;
        }
    }
}
