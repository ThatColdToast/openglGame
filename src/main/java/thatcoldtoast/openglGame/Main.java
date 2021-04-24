package thatcoldtoast.openglGame;

import thatcoldtoast.openglGame.graphics.*;
import thatcoldtoast.openglGame.io.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Main {
	public static double deltaTime = 0.0;
	public static double oldTime = System.currentTimeMillis();
	public static double currentTime = 0.0;

	public static void main(String[] args) {

		Window window = new Window();
		
		window.createWindow(1920, 1080);
		
		Mesh mesh = new Mesh();
		mesh.create(new float[] {
				-1, -1, 0,      0,  1, //Bottom Left   Texture coords start at top left
				-1,  1, 0,      0,  0, //Top Left
				 1, -1, 0,      1,  1, //Bottom Right
		});

		Mesh mesh2 = new Mesh();
		mesh2.create(new float[] {
				 1,  1, 0,      1,  0, //Top Right   Texture coords start at top left
				-1,  1, 0,      0,  0, //Top Left
				 1, -1, 0,      1,  1, //Bottom Right
		});

		Shader shader = new Shader();
		shader.create("basic");
		
		Texture texture = new Texture();
		texture.create("/textures/wood.png");
		
		Camera camera = new Camera();
		Transform transform = new Transform();

		camera.setPerspective((float)Math.toRadians(90), (float) window.width / (float) window.height, 0.01f, 1000.0f);
		camera.setPosition(new Vector3f(0, 1, 3));
		camera.setRotation(new Quaternionf(new AxisAngle4f((float)Math.toRadians(-30), new Vector3f(1,0,0))));
		
		boolean isRunning = true;
		
		float frameNum = 0;
		
		while (isRunning) { //main game loop
			isRunning = !window.update(); //glfwPollEvents() is in window.update();

			frameNum++;

			System.out.printf("Delta Time: %.5f\n", getDeltaTime());
			if(glfwGetKey(window.getWindowId(), GLFW_KEY_A) == GL_TRUE)
			{
				Vector3f newPos = transform.getPosition();
				newPos.x = (float) (newPos.x - 0.01);
				transform.setPosition(newPos);
			}
			if(glfwGetKey(window.getWindowId(), GLFW_KEY_D) == GL_TRUE)
			{
				Vector3f newPos = transform.getPosition();
				newPos.x = (float) (newPos.x + 0.01);
				transform.setPosition(newPos);
			}
			//transform.setPosition(new Vector3f((float)Math.sin(Math.toRadians((float) frameNum)), 0, 0));
			//transform.getRotation().rotateAxis((float)Math.toRadians(1), 0, 1, 0);


			//---------------------------- OPENGL Stuff Below ----------------------------
			glClear(GL_COLOR_BUFFER_BIT);
			
			shader.useShader();
			shader.setCamera(camera);
			shader.setTransform(transform);
			shader.setSampleTexture(0);
			texture.bind();
			mesh.draw();
			mesh2.draw();
			
			window.swapBuffers();

			updateTime();

//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		
		texture.destroy();
		mesh.destroy();
		mesh2.destroy();
		shader.destroy();
		
		window.free();
	}

	public static double getDeltaTime()
	{
		return deltaTime;
	}

	public static void updateTime()
	{
		currentTime = System.currentTimeMillis();
		deltaTime = currentTime - oldTime;
		oldTime = currentTime;
	}
}
