package pl.warp.ide.controller.sceneeditor;

import javafx.scene.control.TextInputDialog;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 11
 */
public class ComponentNameDialog extends TextInputDialog {
    public ComponentNameDialog() {
        setTitle("Component name");
        setHeaderText("Naming component");
        setContentText("Enter component name:");
    }
}
