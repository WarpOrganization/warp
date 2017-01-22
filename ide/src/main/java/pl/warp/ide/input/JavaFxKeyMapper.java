package pl.warp.ide.input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.awt.event.MouseEvent;


/**
 * @author Jaca777
 *         Created 2017-01-22 at 12
 */
public class JavaFxKeyMapper {
    public static int toAWTKeyCode(KeyCode code){
        return code.impl_getCode();
    }

    public static int toAWTButton(MouseButton button) {
        switch(button) {
            case PRIMARY: return MouseEvent.BUTTON1;
            case MIDDLE: return MouseEvent.BUTTON2;
            case SECONDARY: return MouseEvent.BUTTON3;
            default: return MouseEvent.NOBUTTON;
        }
    }
}
