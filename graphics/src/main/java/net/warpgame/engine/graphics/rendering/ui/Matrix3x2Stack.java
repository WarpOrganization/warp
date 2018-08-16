package net.warpgame.engine.graphics.rendering.ui;

import org.joml.Matrix3x2f;

import java.util.EmptyStackException;

/**
 * @author MarconZet
 * Created 16.08.2018
 */
public class Matrix3x2Stack {
    private static final int DEFAULT_SIZE = 10;

    private int size;
    private Matrix3x2f[] array;
    private int top = 0;

    public Matrix3x2Stack(int size) {
        this.size = size + 1;
        array = new Matrix3x2f[this.size];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Matrix3x2f();
        }
        array[0].identity();
    }

    public Matrix3x2Stack() {
        this(DEFAULT_SIZE);
    }

    public Matrix3x2f push(Matrix3x2f matrix){
        if(top == size-1)
            throw new ArrayIndexOutOfBoundsException(
                    String.format("The stack is to small to push %sth element. Increase stack size during creation.", top+1)
            );
        top++;
        array[top-1].mul(matrix, array[top]);
        return array[top];
    }

    public void pop(){
        if(top == 0)
            throw new EmptyStackException();
        top--;
    }

    public Matrix3x2f peek(){
        if(top == 0)
            throw new EmptyStackException();
        return array[top];
    }

    public boolean empty(){
        return top == 0;
    }

}
