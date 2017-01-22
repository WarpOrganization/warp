package pl.warp.engine.graphics.input.glfw;


import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.lwjgl.glfw.GLFW.*;


/**
 * @author Jaca777
 *         Created 2017-01-22 at 11
 */
public class GLFWKeyMapper {

    private static final int PRINTABLE = -1;

    public static int toKeyCode(int glfwKey){
        int stroke = toKeyStrokeUnprintable(glfwKey);
        if(stroke == PRINTABLE)
            return KeyEvent.getExtendedKeyCodeForChar(glfwKey);
        else return stroke;
    }

    private static int toKeyStrokeUnprintable(int glfwKey) {
        switch(glfwKey) {
            case GLFW_KEY_UNKNOWN	: return KeyEvent.VK_UNDEFINED;
            case GLFW_KEY_ESCAPE	: return KeyEvent.VK_ESCAPE;
            case GLFW_KEY_F1	: return KeyEvent.VK_F1;
            case GLFW_KEY_F2	: return KeyEvent.VK_F2;
            case GLFW_KEY_F3	: return KeyEvent.VK_F3;
            case GLFW_KEY_F4	: return KeyEvent.VK_F4;
            case GLFW_KEY_F5	: return KeyEvent.VK_F5;
            case GLFW_KEY_F6	: return KeyEvent.VK_F6;
            case GLFW_KEY_F7	: return KeyEvent.VK_F7;
            case GLFW_KEY_F8	: return KeyEvent.VK_F8;
            case GLFW_KEY_F9	: return KeyEvent.VK_F9;
            case GLFW_KEY_F10       : return KeyEvent.VK_F10;
            case GLFW_KEY_F11       : return KeyEvent.VK_F11;
            case GLFW_KEY_F12       : return KeyEvent.VK_F12;
            case GLFW_KEY_F13       : return KeyEvent.VK_F13;
            case GLFW_KEY_F14       : return KeyEvent.VK_F14;
            case GLFW_KEY_F15       : return KeyEvent.VK_F15;
            case GLFW_KEY_UP        : return KeyEvent.VK_UP;
            case GLFW_KEY_DOWN      : return KeyEvent.VK_DOWN;
            case GLFW_KEY_LEFT      : return KeyEvent.VK_LEFT;
            case GLFW_KEY_RIGHT     : return KeyEvent.VK_RIGHT;
            case GLFW_KEY_LEFT_SHIFT    : return KeyEvent.VK_SHIFT;
            case GLFW_KEY_RIGHT_SHIFT    : return KeyEvent.VK_SHIFT;
            case GLFW_KEY_LEFT_CONTROL     : return KeyEvent.VK_CONTROL;
            case GLFW_KEY_RIGHT_CONTROL     : return KeyEvent.VK_CONTROL;
            case GLFW_KEY_LEFT_ALT      : return KeyEvent.VK_ALT;
            case GLFW_KEY_RIGHT_ALT      : return KeyEvent.VK_ALT;
            case GLFW_KEY_TAB       : return KeyEvent.VK_TAB;
            case GLFW_KEY_ENTER     : return KeyEvent.VK_ENTER;
            case GLFW_KEY_BACKSPACE : return KeyEvent.VK_BACK_SPACE;
            case GLFW_KEY_INSERT    : return KeyEvent.VK_INSERT;
            case GLFW_KEY_DELETE       : return KeyEvent.VK_DELETE;
            case GLFW_KEY_PAGE_UP    : return KeyEvent.VK_PAGE_UP;
            case GLFW_KEY_PAGE_DOWN  : return KeyEvent.VK_PAGE_DOWN;
            case GLFW_KEY_HOME      : return KeyEvent.VK_HOME;
            case GLFW_KEY_END       : return KeyEvent.VK_END;
            case GLFW_KEY_KP_ENTER	: return KeyEvent.VK_ENTER;
            default			: return PRINTABLE;
        }
    }

    public static int toButtonCode(int glfwButton)
    {
        switch(glfwButton)
        {
            case GLFW_MOUSE_BUTTON_LEFT	: return MouseEvent.BUTTON1;
            case GLFW_MOUSE_BUTTON_RIGHT	: return MouseEvent.BUTTON2;
            case GLFW_MOUSE_BUTTON_MIDDLE	: return MouseEvent.BUTTON3;
            default				: return MouseEvent.NOBUTTON;
        }
    }
}
