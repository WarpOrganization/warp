package net.warpgame.ide;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class MainStage
{
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox draggableArea;

    private File file;

    public void initialize()
    {
        try {
            ObservableList<TreeNode> treeNodes = TreeLoader.readData(new File(getClass().getResource("all.xml").toURI())).getRealChildren();
            if(treeNodes.size() > 0)
            {
                treeNodes.get(0).makeConstant(true);
                treeNodes.get(0).drawLines();
                draggableArea.getChildren().addAll(treeNodes.get(0).getRealChildren());
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: " + e.getMessage());
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(e.toString())));
            alert.showAndWait();
        }
    }

    public void chooseFile()
    {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML - Extensible Markup Language (.xml)", "*.xml"));
            file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
            if(file != null) {
                TreeNode root = TreeLoader.readData(file);
                root.makeConstant();
                anchorPane.getChildren().clear();
                anchorPane.getChildren().add(root);
                root.drawLines();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: " + e.getMessage());
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(e.toString())));
            alert.showAndWait();
        }

    }

    public void chooseFile(File file)
    {

        try {
            if(file != null) {
                TreeNode root = TreeLoader.readData(file);
                root.makeConstant();
                anchorPane.getChildren().clear();
                anchorPane.getChildren().add(root);
                root.drawLines();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: " + e.getMessage());
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(e.toString())));
            alert.showAndWait();
        }

    }

    public void saveFile()
    {
        try {
            if(file != null && anchorPane.getChildren().size() > 0 && anchorPane.getChildren().get(0) instanceof TreeNode && ((TreeNode) anchorPane.getChildren().get(0)).getRealChildren().size() > 0)
            {
                PrintStream out = new PrintStream(new FileOutputStream(file));
                out.print(((TreeNode) anchorPane.getChildren().get(0)).getRealChildren().get(0).toXML());
                out.flush();
                out.close();
            } else if(file == null) {
                saveFileAs();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: " + e.getMessage());
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(e.toString())));
            alert.showAndWait();
        }
    }

    public void saveFileAs()
    {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file as");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML - Extensible Markup Language (.xml)", "*.xml"));
            file = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
            if(file != null && anchorPane.getChildren().size() > 0 && anchorPane.getChildren().get(0) instanceof TreeNode && ((TreeNode) anchorPane.getChildren().get(0)).getRealChildren().size() > 0)
            {
                PrintStream out = new PrintStream(new FileOutputStream(file));
                out.print(((TreeNode) anchorPane.getChildren().get(0)).getRealChildren().get(0).toXML());
                out.flush();
                out.close();
                chooseFile(file);

            } else {
                if(file != null)
                {
                    PrintStream out = new PrintStream(new FileOutputStream(file));
                    out.print("<node path=\"Expand it!\"/>");
                    out.flush();
                    out.close();
                    chooseFile(file);
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: " + e.getMessage());
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(e.toString())));
            alert.showAndWait();
        }
    }

    public void copy()
    {
        if(anchorPane.getScene().getFocusOwner() instanceof Button)
        {
            if(anchorPane.getScene().getFocusOwner().getParent() instanceof TreeNode)
            {
                System.out.println(anchorPane.getScene().getFocusOwner().toString());
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent clipboardContent= new ClipboardContent();
                clipboardContent.putString(((TreeNode) anchorPane.getScene().getFocusOwner().getParent()).toXML());
                clipboard.setContent(clipboardContent);
            }
        }
    }

    public void paste()
    {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if(anchorPane.getScene().getFocusOwner() instanceof Button && anchorPane.getScene().getFocusOwner().getParent() instanceof TreeNode && clipboard.hasString())
        {
            try {
                ((TreeNode) anchorPane.getScene().getFocusOwner().getParent()).addRealChild(TreeLoader.readData(clipboard.getString()));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: " + e.getMessage());
                alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(e.toString())));
                alert.showAndWait();
            }
        }
    }

    public void delete()
    {
        if(anchorPane.getScene().getFocusOwner() instanceof Button && anchorPane.getScene().getFocusOwner().getParent() instanceof TreeNode)
        {
            ((TreeNode) anchorPane.getScene().getFocusOwner().getParent()).removeYourself();
        }
    }
    public void about()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("About");
        alert.getDialogPane().setContent(new Label("AI Behavior Tree Editor\nWarp Organization 2018"));
        alert.showAndWait();
    }
}