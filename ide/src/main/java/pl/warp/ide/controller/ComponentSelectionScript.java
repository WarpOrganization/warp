package pl.warp.ide.controller;

import javafx.scene.control.TreeView;
import pl.warp.engine.core.scene.Component;
import pl.warp.game.scene.GameScene;
import pl.warp.game.script.GameScript;
import pl.warp.ide.scene.tree.ComponentItem;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 22
 */
public class ComponentSelectionScript extends GameScript<GameScene> {

    private TreeView<ComponentItem<Component>> sceneTree;

    public ComponentSelectionScript(GameScene owner, TreeView<ComponentItem<Component>> sceneTree) {
        super(owner);
        this.sceneTree = sceneTree;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate(int delta) {
        //TODO
        throw new UnsupportedOperationException();
    }
}
