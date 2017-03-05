package pl.warp.ide.controller.componenteditor;

import pl.warp.engine.graphics.resource.texture.ImageDataArray;
import pl.warp.engine.graphics.resource.texture.ImageDecoder;
import pl.warp.engine.graphics.resource.texture.PNGDecoder;
import pl.warp.engine.graphics.skybox.GraphicsSkyboxProperty;
import pl.warp.engine.graphics.texture.Cubemap;
import pl.warp.game.GameContext;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;

/**
 * @author Jaca777
 *         Created 2017-03-03 at 14
 */
public class ComponentViewSceneFactory implements ComponentSceneFactory {
    @Override
    public GameScene createScene(GameComponent component) {
        GameContext context = component.getContext();
        GameScene scene = new GameScene(context);
        scene.addChild(component);
        createSkybox(context);
        return scene;
    }

    protected void createSkybox(GameContext context) {
        context.getGraphics().getThread().scheduleOnce(() -> {
            ImageDataArray decodedCubemap = ImageDecoder.decodeCubemap("pl/warp/test/stars3", PNGDecoder.Format.RGBA);
            Cubemap cubemap = new Cubemap(decodedCubemap.getWidth(), decodedCubemap.getHeight(), decodedCubemap.getData());
            context.getScene().addProperty(new GraphicsSkyboxProperty(cubemap));
        });
    }
}
