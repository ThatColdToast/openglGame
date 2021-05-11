package thatcoldtoast.openglGame.entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import thatcoldtoast.openglGame.Main;
import thatcoldtoast.openglGame.graphics.Camera;
import thatcoldtoast.openglGame.graphics.Transform;
import thatcoldtoast.openglGame.handlers.KeyboardHandler;
import thatcoldtoast.openglGame.io.InputHandler;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINE;

public class Player {
    public Camera camera;
    public Transform MainTransform;
    public InputHandler inputHandler;


    public Player() {
        camera = new Camera();
        MainTransform = new Transform();

        inputHandler = new InputHandler(camera);
    }

    public void updatePlayer() {
        inputHandler.updateKeys(MainTransform);
        inputHandler.updateMouse();
    }
}
