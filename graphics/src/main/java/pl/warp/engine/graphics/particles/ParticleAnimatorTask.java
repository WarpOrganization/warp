package pl.warp.engine.graphics.particles;

import pl.warp.engine.core.SimpleEngineTask;

import java.util.List;

/**
 * @author Jaca777
 *         Created 2017-03-15 at 19
 */
public class ParticleAnimatorTask extends SimpleEngineTask {
    private ParticleSystemStorage storage;

    public ParticleAnimatorTask(ParticleSystemStorage storage) {
        this.storage = storage;
    }

    @Override
    public void update(int delta) {
        List<ParticleSystemStorage.ParticleSystemData> systems = storage.getSystems();
        for (int i = 0; i < storage.getSystemsNumber(); i++) {
            ParticleSystemStorage.ParticleSystemData data = systems.get(i);
            ParticleSystem system = data.getSystem();
            system.update(delta);
        }
    }
}
