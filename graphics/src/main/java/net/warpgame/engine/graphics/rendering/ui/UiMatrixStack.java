package net.warpgame.engine.graphics.rendering.ui;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.service.ServiceBuilder;
import net.warpgame.engine.graphics.rendering.ui.property.RectTransformProperty;
import net.warpgame.engine.graphics.window.Display;
import org.joml.Matrix3x2f;
import org.joml.Vector2f;

import java.util.EmptyStackException;

/**
 * @author MarconZet
 * Created 16.08.2018
 */

@Service
@Profile("graphics")
public class UiMatrixStack {
    private Vector2f[] translationStack;
    private float[] rotationStack;
    private Vector2f[] scaleStack;

    private int size;

    private int top = 0;

    public UiMatrixStack(Config config) {
        this.size = config.getValue("graphics.rendering.ui.stackSize");
        translationStack = new Vector2f[size+1];
        rotationStack = new float[size+1];
        scaleStack = new Vector2f[size+1];
        for (int i = 0; i < size+1; i++) {
            translationStack[i] = new Vector2f();
            rotationStack[i] = 0;
            scaleStack[i] = new Vector2f();
        }
    }

    public Matrix3x2f push(RectTransformProperty rectTransform) {
        if (top == size - 1)
            throw new ArrayIndexOutOfBoundsException(
                    String.format("The stack is to small to push %sth element. Increase stack size during creation.", top + 1)
            );
        top++;
        throw new UnsupportedOperationException("UiMatrixStack not implemented");
    }

    public void pop(){
        if(top == 0)
            throw new EmptyStackException();
        top--;
        throw new UnsupportedOperationException("UiMatrixStack not implemented");
    }

    public Matrix3x2f peek(){
        if(top == 0)
            throw new EmptyStackException();
        throw new UnsupportedOperationException("UiMatrixStack not implemented");
    }

    public boolean isEmpty(){
        return top == 0;
    }

}
