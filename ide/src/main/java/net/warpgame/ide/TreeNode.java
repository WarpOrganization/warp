package net.warpgame.ide;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class TreeNode extends VBox {

    private Button nameArea;
    private HBox childrenInTree;
    private boolean constant = false;

    public TreeNode (String name)
    {
        super();
        setPadding(new Insets(0,10,0,0));
        nameArea = new Button(name);
        nameArea.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard dragboard = nameArea.startDragAndDrop(TransferMode.ANY);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(((TreeNode) nameArea.getParent()).toXML());
                dragboard.setContent(clipboardContent);
                event.consume();
            }
        });
        nameArea.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getGestureSource() != nameArea && event.getDragboard().hasString())
                {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });
        nameArea.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getGestureSource() != nameArea/* && event.getDragboard().hasString()*/)
                {
                    nameArea.setTextFill(Color.GREEN);
                }
                event.consume();
            }
        });
        nameArea.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                nameArea.setTextFill(Color.BLACK);
                event.consume();
            }
        });
        nameArea.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard dragboard = event.getDragboard();
                if(dragboard.hasString())
                {
                    try {
                        addRealChild(TreeLoader.readData(dragboard.getString()));
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Error: " + e.getMessage());
                        alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(e.toString())));
                        alert.showAndWait();
                    }
                }
                event.setDropCompleted(true);
                event.consume();
            }
        });
        nameArea.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getTransferMode() == TransferMode.MOVE)
                {
                    removeYourself();
                }
                event.consume();
            }
        });
        childrenInTree = new HBox();
        getChildren().addAll(nameArea, childrenInTree);
    }

    public ObservableList<TreeNode> getRealChildren () { return (ObservableList)childrenInTree.getChildren(); }

    public void addRealChild(TreeNode node)
    {
        getRealChildren().add(node);
        drawLines();
    }

    public TreeNode getRealParent ()
    {
        return (TreeNode) getParent().getParent();
    }

    public void removeYourself()
    {
        if(!constant) {
            Pane parent = (Pane) getParent();
            parent.getChildren().remove(this);
            //if(parent instanceof HBox && parent.getParent() instanceof  TreeNode)
            ((TreeNode) parent.getParent()).drawLines();
        }
    }

    public String toXML() {
        if(getRealChildren().size() == 0)
        {
            return "<node path=\"" + nameArea.getText() + "\"/>\n";
        }
        else
        {
            String ret = "<node path=\"" + nameArea.getText() + "\">\n";
            for(TreeNode treeNode: getRealChildren())
            {
                ret += treeNode.toXML();
            }
            ret += "</node>\n";
            return ret;
        }
    }

    public void makeConstant(boolean recursive)
    {
        makeConstant();
        if(recursive)
        {
            for(TreeNode child: getRealChildren())
            {
                child.makeConstant(true);
            }
        }
    }

    public void makeConstant()
    {
        constant = true;
        setPadding(new Insets(0,10,10,0));
    }

    public boolean isConstant() {
        return constant;
    }

    public void drawLines()
    {
        Iterator<Node> iterator = getChildren().iterator();
        while(iterator.hasNext())
        {
            Node node = iterator.next();
            if(node instanceof Separator)
            {
                iterator.remove();
            }
        }

        Separator separator;
        if(getRealChildren().size() > 0)
        {
            separator = new Separator();
            separator.setMinHeight(10);
            separator.setPadding(new Insets(2,0,2,10));
            separator.setOrientation(Orientation.VERTICAL);
            getChildren().add(1, separator);

            separator = new Separator();
            separator.setPadding(new Insets(0,14,0,14));
            getChildren().add(2, separator);

            for(TreeNode node : getRealChildren())
            {
                node.drawLines();
            }
        }

        if(!(getParent() instanceof AnchorPane || getParent() instanceof VBox || isConstant()))
        {
            separator = new Separator();
            separator.setMinHeight(10);
            separator.setPadding(new Insets(2,0,2,10));
            separator.setOrientation(Orientation.VERTICAL);
            getChildren().add(0, separator);
        }

    }
}
