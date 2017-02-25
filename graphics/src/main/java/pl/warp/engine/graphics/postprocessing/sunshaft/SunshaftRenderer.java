package pl.warp.engine.graphics.postprocessing.sunshaft;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.ComponentRenderer;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.framebuffer.Framebuffer;
import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-02-24 at 21
 */
public class SunshaftRenderer implements Flow<Texture2D, Texture2D> {

    private Texture2D sceneDepthBuffer;
    private SunshaftSource sunshaftSource;
    private RenderingConfig config;
    private Framebuffer resultFramebuffer;
    private ComponentRenderer renderer;

    private Texture2D sunshaftTexture;
    private TextureFramebuffer sunshaftFramebuffer;

    public SunshaftRenderer(Texture2D sceneDepthBuffer, SunshaftSource sunshaftSource, RenderingConfig config, ComponentRenderer renderer) {
        this.sceneDepthBuffer = sceneDepthBuffer;
        this.sunshaftSource = sunshaftSource;
        this.config = config;
        this.renderer = renderer;
    }

    @Override
    public void update(int delta) {
        if(sunshaftSource.getSource() != null) {
            renderer.enterChildren();
            renderer.prepareComponent(sunshaftSource.getSource());
            renderer.renderComponent(sunshaftSource.getSource());

            renderComponent();
            occlude();
            render();
            renderer.leaveChildren();
        }
    }

    private void renderComponent(){
        prepare();
        renderer.renderComponent(sunshaftSource.getSource());

    }

    private void prepare() {
        applyTransform(sunshaftSource.getSource());
    }

    private void applyTransform(Component component) {
        if(component.hasParent()) applyTransform(component.getParent());
        renderer.prepareComponent(component);
    }

    private void occlude() {

    }

    private void blur() {

    }

    private void render() {

    }


    @Override
    public void init() {
        this.sunshaftFramebuffer = new TextureFramebuffer(sunshaftTexture);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        sunshaftFramebuffer.resize(newWidth, newHeight);
    }

    @Override
    public Texture2D getOutput() {
        return null;
    }

    @Override
    public void setInput(Texture2D input) {
        //TODO
        throw new UnsupportedOperationException();
    }

}
