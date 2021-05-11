package thatcoldtoast.openglGame.io;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import thatcoldtoast.openglGame.Main;
import thatcoldtoast.openglGame.graphics.Camera;
import thatcoldtoast.openglGame.graphics.Transform;
import thatcoldtoast.openglGame.handlers.KeyboardHandler;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINE;

public class InputHandler {
    public float speed = 0.1f;
    public double turnSpeed = 0.05;
    public double turnSpeedQuad = 0.002;
    public Camera camera;

    public InputHandler(Camera cam) {
        camera = cam;
    }

    public void updateMouse()
    {
        double[] cursorPos = camera.getCursorPos(Main.window);
//		Quaternionf rot = new Quaternionf(new AxisAngle4f((float)Math.toRadians((float) (-turnSpeedQuad * cursorPos[1]) * getDeltaTime()), new Vector3f(1,0,0)));
//		rot.add(new Quaternionf(new AxisAngle4f((float)Math.toRadians((float) (-turnSpeedQuad * cursorPos[0]) * getDeltaTime()), new Vector3f(0,1,0))));
//		rot.add(camera.getRotation());
//		camera.setRotation(rot);

//		camera.setRotation(new Quaternionf(new AxisAngle4f((float)Math.toRadians(45), new Vector3f(1,0,0))));

        camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeedQuad) * cursorPos[0] * Main.getDeltaTime()), 0, -1, 0);
        camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeedQuad) * cursorPos[1] * Main.getDeltaTime()), -1, 0, 0);
    }

    public void updateKeys(Transform transform)
    {
        if(KeyboardHandler.getKey(GLFW_KEY_Q)) {
            camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * Main.getDeltaTime()), 0, 0, 1);
        }
        if(KeyboardHandler.getKey(GLFW_KEY_E)) {
            camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * Main.getDeltaTime()), 0, 0, -1);
        }

        Matrix4f invCam = new Matrix4f(camera.getTransformation()).invert();
        Vector3f forward = new Vector3f(0, 0, -1);
        Vector3f right = new Vector3f(1, 0, 0);
//		System.out.printf("Before Forward:   X: %.2f Y: %.2f Z: %.2f\n", forward.x, forward.y, forward.z);
//		System.out.printf("Before Right:     X: %.2f Y: %.2f Z: %.2f\n\n", right.x, right.y, right.z);
        invCam.transformDirection(forward);
        invCam.transformDirection(right);

//		System.out.printf("Forward:   X: %.2f Y: %.2f Z: %.2f\n", forward.x, forward.y, forward.z);
//		System.out.printf("Right:     X: %.2f Y: %.2f Z: %.2f\n\n", right.x, right.y, right.z);

        if(KeyboardHandler.getKey(GLFW_KEY_UP))
        {
            camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * Main.getDeltaTime()), 1, 0, 0);
        }
        if(KeyboardHandler.getKey(GLFW_KEY_DOWN))
        {
            camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * Main.getDeltaTime()), -1, 0, 0);
        }

        if(KeyboardHandler.getKey(GLFW_KEY_LEFT))
        {
            camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * Main.getDeltaTime()), 0, 1, 0);
        }
        if(KeyboardHandler.getKey(GLFW_KEY_RIGHT))
        {
            camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * Main.getDeltaTime()), 0, -1, 0);
        }



        if(KeyboardHandler.getKey(GLFW_KEY_P))
        {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL); // Turn off wireframe mode
        }
        if(KeyboardHandler.getKey(GLFW_KEY_O))
        {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // Turn on wireframe mode
        }



        if(KeyboardHandler.getKey(GLFW_KEY_ESCAPE))
        {
            Main.exitGame();
        }

        if(KeyboardHandler.getKey(GLFW_KEY_A)) //left right
        {
            Vector3f newPos = transform.getPosition();
            newPos = newPos.add(right.mul(speed));
            transform.setPosition(newPos);
        }
        if(KeyboardHandler.getKey(GLFW_KEY_D))
        {
            Vector3f newPos = transform.getPosition();
            newPos = newPos.add(right.mul(-speed));
            transform.setPosition(newPos);
        }

        if(KeyboardHandler.getKey(GLFW_KEY_W)) //front back
        {
            Vector3f newPos = transform.getPosition();
            newPos = newPos.add(forward.mul(-speed));
            transform.setPosition(newPos);


//			Vector3f newPos = transform.getPosition();
//			newPos.z = (float) (newPos.z + speed);
//			transform.setPosition(newPos.mul(forward));
        }
        if(KeyboardHandler.getKey(GLFW_KEY_S))
        {
            Vector3f newPos = transform.getPosition();
            newPos = newPos.add(forward.mul(speed));
            transform.setPosition(newPos);
        }

        if(KeyboardHandler.getKey(GLFW_KEY_R)) //up down
        {
            Vector3f newPos = transform.getPosition();
            newPos.y = (float) (newPos.y - speed);
            transform.setPosition(newPos);
        }
        if(KeyboardHandler.getKey(GLFW_KEY_F))
        {
            Vector3f newPos = transform.getPosition();
            newPos.y = (float) (newPos.y + speed);
            transform.setPosition(newPos);
        }

        if(KeyboardHandler.getKey(GLFW_KEY_1)) //speed
        {
            speed -= 0.01f;
            if (speed < 0.1)
                speed = 0.1f;
        }
        if(KeyboardHandler.getKey(GLFW_KEY_2))
        {
            speed += 0.01f;
            if (speed > 1.0f)
                speed = 1.0f;
        }
    }
}
