package pl.warp.ide.controller.component;

import com.sun.javafx.collections.ImmutableObservableList;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import pl.warp.ide.scene.tree.prototype.ComponentPrototype;
import pl.warp.ide.scene.tree.prototype.PrototypeRepository;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 11
 */
public class ComponentSelectionDialog extends Dialog<ComponentPrototype> {
    public ComponentSelectionDialog(PrototypeRepository repository) {
        setTitle("Prototypes");
        setHeaderText("Select component prototype");
        ButtonType runButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(runButtonType, ButtonType.CANCEL);
        ObservableList<ComponentPrototype> componentPrototypes = new ImmutableObservableList(repository.getPrototypes().toArray());
        ListView<ComponentPrototype> prototypeList = new ListView<>(componentPrototypes);
        getDialogPane().setContent(prototypeList);
        getDialogPane().setPrefSize(412, 512);
        setResultConverter(b -> {
            if (b.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) return null;
            else return prototypeList.getSelectionModel().getSelectedItem();
        });

    }
}
