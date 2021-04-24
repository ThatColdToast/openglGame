package thatcoldtoast.openglGame.handlers;

import thatcoldtoast.openglGame.Main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class KeyboardHandler {
    public KeyboardHandler() {}

    public static boolean getKey(int key)
    {
        return (glfwGetKey(Main.getWindow().getWindowId(), key) == GL_TRUE);
    }
}