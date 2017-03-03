package pl.warp.ide.controller;

import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;

/**
 * @author Jaca777
 *         Created 2017-03-03 at 13
 */
public class PanelManager {
    private Pane sceneTreePane;
    private Pane propertyGrid;

    private TreeView<?> componentTree;
    private Pane propertyPane;

    public PanelManager(Pane sceneTreePane, Pane propertyGrid, TreeView<?> componentTree, Pane propertyPane) {
        this.sceneTreePane = sceneTreePane;
        this.propertyGrid = propertyGrid;
        this.componentTree = componentTree;
        this.propertyPane = propertyPane;
    }

    public void showSceneEditor(){
        sceneTreePane.setVisible(true);
        propertyGrid.setVisible(true);
        componentTree.setVisible(false);
        propertyPane.setVisible(false);
    }

    public void showComponentEditor(){
        componentTree.setVisible(true);
        propertyPane.setVisible(true);
        sceneTreePane.setVisible(false);
        propertyGrid.setVisible(false);
    }
}
