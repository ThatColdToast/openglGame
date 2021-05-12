package thatcoldtoast.openglGame.entities;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.system.CallbackI;
import thatcoldtoast.openglGame.Main;
import thatcoldtoast.openglGame.graphics.Camera;
import thatcoldtoast.openglGame.graphics.Transform;
import thatcoldtoast.openglGame.handlers.KeyboardHandler;
import thatcoldtoast.openglGame.io.InputHandler;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINE;

public class Player {
    public Camera camera;
    private Transform MainTransform;
    public InputHandler inputHandler;


    public Player() {
        camera = new Camera();
        MainTransform = new Transform();

        inputHandler = new InputHandler(camera);
    }

    public void updatePlayer() {
        inputHandler.updateKeys(MainTransform);
        inputHandler.updateMouse();
//        System.out.println(getTransform().getPosition());
        System.out.println(getPosition());
    }

    public Transform getTransform() {
        return MainTransform;
    }

    public Vector3f getPosition() {
        float xPos = MainTransform.getPosition().x;
        float yPos = MainTransform.getPosition().y;
        float zPos = MainTransform.getPosition().z;
        xPos = -xPos;
        yPos = -yPos;
        zPos = -zPos;
        return new Vector3f(xPos, yPos, zPos);
    }

    public Vector3f getPosition(boolean real) {
        if(!real) {
            float xPos = MainTransform.getPosition().x;
            float yPos = MainTransform.getPosition().y;
            float zPos = MainTransform.getPosition().z;
            xPos = -xPos;
            yPos = -yPos;
            zPos = -zPos;
            return new Vector3f(xPos, yPos, zPos);
        }
        return MainTransform.getPosition();
    }

    public void setPosition(Vector3f pos) {
        MainTransform.setPosition(pos);
    }

//    public void lockCamera() {
//        Quaternionf rot = MainTransform.getRotation();
//        System.out.println(rot);
//    }
}