package pl.warp.ide.controller;

import pl.warp.engine.game.GameContext;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.scene.GameScene;
import pl.warp.ide.controller.componenteditor.ComponentSceneFactory;

/**
 * @author Jaca777
 *         Created 2017-03-03 at 13
 */
public class ViewManager {
    private GameContext context;
    private GameScene gameScene;
    private ComponentSceneFactory factory;

    public ViewManager(GameContext context, GameScene gameScene, ComponentSceneFactory factory) {
        this.context = context;
        this.gameScene = gameScene;
        this.factory = factory;
    }

    public void showScene(){
        context.setScene(gameScene);
    }

    public void showComponent(GameComponent component){
        GameScene scene = factory.createScene(component);
        context.setScene(scene);
    }
}
