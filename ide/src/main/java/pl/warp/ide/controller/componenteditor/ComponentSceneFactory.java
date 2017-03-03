package pl.warp.ide.controller.componenteditor;

import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;

/**
 * @author Jaca777
 *         Created 2017-03-03 at 14
 */
public interface ComponentSceneFactory {
    GameScene createScene(GameComponent component);
}
