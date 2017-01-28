package pl.warp.ide.controller.component;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.joml.Vector3f;
import pl.warp.engine.physics.RayTester;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.script.GameScriptWithInput;

import java.awt.event.MouseEvent;

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

    private Vector3f cameraPos = new Vector3f(0,0, -100);
    private Vector3f vector = new Vector3f(0, 0, 100);

    @Override
    protected void update(int delta) {
        if (getInputHandler().wasMouseButtonPressed(MouseEvent.BUTTON3))
            select();
    }

    private void select() {
        RayTester rayTester = getContext().getRayTester();
/*        Camera camera = getContext().getCamera();
        camera.getForwardVector().mul(SELECTION_RANGE, vector);
        vector.add(camera.getPosition(cameraPos));*/
        GameComponent component = (GameComponent) rayTester.rayTest(cameraPos, vector);
        System.out.println(component);
        if (component != null) selectComponent(component);
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
