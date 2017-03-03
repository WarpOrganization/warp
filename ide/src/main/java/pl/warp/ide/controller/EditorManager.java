package pl.warp.ide.controller;

import pl.warp.game.scene.GameComponent;
import pl.warp.ide.controller.componenteditor.ComponentEditorController;
import pl.warp.ide.controller.sceneeditor.SceneEditorController;

/**
 * @author Jaca777
 *         Created 2017-03-03 at 13
 */
public class EditorManager {
    private PanelManager panelManager;
    private ViewManager viewManager;
    private SceneEditorController sceneEditorController;
    private ComponentEditorController componentEditorController;

    public EditorManager(PanelManager panelManager, ViewManager viewManager, SceneEditorController sceneEditorController) {
        this.panelManager = panelManager;
        this.viewManager = viewManager;
        this.sceneEditorController = sceneEditorController;
    }

    public void editScene(){
        panelManager.showSceneEditor();
        componentEditorController.suspend();
        sceneEditorController.activate();
        viewManager.showScene();
    }

    public void editComponent(GameComponent component) {
        panelManager.showComponentEditor();
        sceneEditorController.suspend();
        componentEditorController.activateWith(component);
        viewManager.showComponent(component);
    }
}
