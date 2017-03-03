package pl.warp.ide.controller.sceneeditor;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.joml.Vector2f;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.script.CameraRayTester;
import pl.warp.game.script.GameScriptWithInput;

import java.awt.event.MouseEvent;
import java.util.Optional;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 22
 */
public class ComponentSelectionScript extends GameScriptWithInput<GameScene> {

    private static final float SELECTION_RANGE = 400;
    private TreeView<GameComponent> sceneTree;

    public ComponentSelectionScript(GameScene owner, TreeView<GameComponent> sceneTree) {
        super(owner);
        this.sceneTree = sceneTree;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void update(int delta) {
        if (getInputHandler().wasMouseButtonPressed(MouseEvent.BUTTON3))
            select();
    }

    private void select() {
        CameraRayTester rayTester = getContext().getRayTester();
        Vector2f cursorPosition = getInputHandler().getCursorPosition();
        Optional<GameComponent> gameComponent = rayTester.testCameraRay(cursorPosition.x, cursorPosition.y, SELECTION_RANGE);
        gameComponent.ifPresent(this::selectComponent);
    }

    private void selectComponent(GameComponent component) {
        TreeItem<GameComponent> item = findItem(component, sceneTree.getRoot());
        sceneTree.getSelectionModel().select(item);
    }

    private TreeItem<GameComponent> findItem(GameComponent component, TreeItem<GameComponent> item) {
        if (item.getValue() == component) return item;
        else if (item.getChildren().size() > 0) {
            for (TreeItem<GameComponent> child : item.getChildren()) {
                TreeItem<GameComponent> foundItem = findItem(component, child);
                if (foundItem != null) return foundItem;
            }
        }
        return null;
    }
}
