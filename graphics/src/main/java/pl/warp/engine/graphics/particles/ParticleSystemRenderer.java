package pl.warp.engine.graphics.particles;

import org.apache.log4j.Logger;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.postprocessing.lens.LensFlareRenderer;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jaca777
 *         Created 2016-07-11 at 13
 */
public class ParticleSystemRenderer implements Flow<MultisampleTexture2D, MultisampleTexture2D> {

    private static final Logger logger = Logger.getLogger(LensFlareRenderer.class);

    public static final int MAX_PARTICLES_NUMBER = 1000;

    private Camera camera;
    private Map<ParticleRendererFactory, ParticleRenderer> renderers = new HashMap<>();
    private ParticleSystemStorage particleSystemStorage;

    private MultisampleTexture2D scene;

    public ParticleSystemRenderer(Camera camera, ParticleSystemStorage particleSystemStorage) {
        this.camera = camera;
        this.particleSystemStorage = particleSystemStorage;
    }


    @Override
    public void init() {

    }


    @Override
    public void update() {
        for (ParticleRenderer renderer : renderers.values())
            renderer.useCamera(camera);

        List<ParticleSystemStorage.ParticleSystemData> systems = particleSystemStorage.getSystems();
        for (int i = 0; i < particleSystemStorage.getSystemsNumber(); i++) {
            ParticleSystemStorage.ParticleSystemData data = systems.get(i);
            ParticleSystem system = data.getSystem();
            ParticleRenderer renderer = getRenderer(system);
            renderer.render(system, data.getTransformation());
        }
    }

    protected ParticleRenderer getRenderer(ParticleSystem system) {
        if (system.getRenderer() == null)
            initializeRenderer(system);
        return system.getRenderer();
    }

    private void initializeRenderer(ParticleSystem system) {
        ParticleRendererFactory typeDescriptor = system.getAttribute().getParticleRendererFactory();
        if (renderers.containsKey(typeDescriptor)) system.setRenderer(renderers.get(typeDescriptor));
        else {
            ParticleRenderer typeRenderer = typeDescriptor.createRenderer();
            typeRenderer.initialize();
            typeRenderer.useCamera(camera);
            renderers.put(typeDescriptor, typeRenderer);
            system.setRenderer(typeRenderer);
        }
    }


    @Override
    public void destroy() {
        for (ParticleRenderer renderer : renderers.values())
            renderer.destroy();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {

    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }


    @Override
    public MultisampleTexture2D getOutput() {
        return scene;
    }

    @Override
    public void setInput(MultisampleTexture2D input) {
        this.scene = input;
    }
}
