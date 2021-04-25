package thatcoldtoast.openglGame;

import thatcoldtoast.openglGame.gameObjects.Block;
import thatcoldtoast.openglGame.graphics.*;
import thatcoldtoast.openglGame.graphics.shapes.Cube;
import thatcoldtoast.openglGame.handlers.KeyboardHandler;
import thatcoldtoast.openglGame.io.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import thatcoldtoast.openglGame.world.World;

public class Main {
	public static double deltaTime = 0.0;
	public static double oldTime = System.currentTimeMillis();
	public static double currentTime = 0.0;
	public static Window window;
	public static float speed = 0.01f;
	public static double turnSpeed = 0.005;
	public static Camera camera;

	public static void main(String[] args) {

		window = new Window();
		
		window.createWindow(1920, 1080);

		//Block b1 = new Block(0, 0, 0);
//		Block[] blocks = new Block[3];
//		for(int i = 0; i < blocks.length; i++)
//		{
//			blocks[i] = new Block(i, 0, 0);
//		}
		World world = new World();
		world.create(0);

		Shader shader = new Shader();
		shader.create("basic");
		
		Texture texture = new Texture();
		texture.create("/textures/wood.png");
		
		camera = new Camera();
		Transform transform = new Transform();

		camera.setPerspective((float)Math.toRadians(70), (float) window.width / (float) window.height, 0.01f, 1000.0f);
		camera.setPosition(new Vector3f(0, 0, 3));
		camera.setRotation(new Quaternionf(new AxisAngle4f((float)Math.toRadians(0), new Vector3f(1,0,0))));
		
		boolean isRunning = true;
		
		float frameNum = 0;
		
		while (isRunning) { //main game loop
			isRunning = !window.update(); //glfwPollEvents() is in window.update();

			frameNum++;

			//System.out.printf("Delta Time: %.5f\n", getDeltaTime());

			updateKeys(transform);
			updateMouse();

			//transform.setPosition(new Vector3f((float)Math.sin(Math.toRadians((float) frameNum)), 0, 0));
			//transform.getRotation().rotateAxis((float)Math.toRadians(1), 0, 1, 0);

			Quaternionf newRotation = camera.getRotation(); //clamp rotation of camera
			newRotation.z = 0.0f;
			camera.setRotation(newRotation);

			//---------------------------- OPENGL Stuff Below ----------------------------
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			shader.useShader();
			shader.setCamera(camera);
			shader.setTransform(transform);
			shader.setSampleTexture(0);
			texture.bind();
			//b1.update();
//			for(int i = 0; i < blocks.length; i++)
//			{
//				blocks[i].update();
//			}
			world.update();

			window.swapBuffers();

			updateTime();
		}
		
		texture.destroy();
		world.destroy();
		//b1.destroy();
//		for(int i = 0; i < blocks.length; i++)
//		{
//			blocks[i].destroy();
//		}
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

	public static Window getWindow()
	{
		return window;
	}

	public static long getWindowId()
	{
		return window.getWindowId();
	}

	public static void updateMouse()
	{
		double[] cursorPos = camera.getCursorPos(window);
		camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * cursorPos[0] * getDeltaTime()), 0, -1, 0);
		camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * cursorPos[1] * getDeltaTime()), -1, 0, 0);
	}

	public static void updateKeys(Transform transform)
	{
		if(KeyboardHandler.getKey(GLFW_KEY_Q)) {
			System.out.printf("xRot %.2f\n", camera.getRotation().x);
			System.out.printf("yRot %.2f\n", camera.getRotation().y);
			System.out.printf("zRot %.2f\n", camera.getRotation().z);

			System.out.println();
		}

//		if(KeyboardHandler.getKey(GLFW_KEY_UP))
//		{
//			camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * getDeltaTime()), 1, 0, 0);
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_DOWN))
//		{
//			camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * getDeltaTime()), -1, 0, 0);
//		}
//
//		if(KeyboardHandler.getKey(GLFW_KEY_LEFT))
//		{
//			camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * getDeltaTime()), 0, 1, 0);
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_RIGHT))
//		{
//			camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * getDeltaTime()), 0, -1, 0);
//		}

		if(KeyboardHandler.getKey(GLFW_KEY_ESCAPE))
		{
			exitGame();
		}

		if(KeyboardHandler.getKey(GLFW_KEY_A)) //left right
		{
			Vector3f newPos = transform.getPosition();
			newPos.x = (float) (newPos.x + speed);
			transform.setPosition(newPos);

//			Vector3f newPos = transform.getPosition();
//			newPos.rotate(camera.getRotation());
//			transform.setPosition(newPos);
		}
		if(KeyboardHandler.getKey(GLFW_KEY_D))
		{
			Vector3f newPos = transform.getPosition();
			newPos.x = (float) (newPos.x - speed);
			transform.setPosition(newPos);
		}

		if(KeyboardHandler.getKey(GLFW_KEY_W)) //front back
		{
			Vector3f newPos = transform.getPosition();
			newPos.z = (float) (newPos.z + speed);
			transform.setPosition(newPos);
		}
		if(KeyboardHandler.getKey(GLFW_KEY_S))
		{
			Vector3f newPos = transform.getPosition();
			newPos.z = (float) (newPos.z - speed);
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
			speed -= 0.001f;
			if (speed < 0.001)
				speed = 0.001f;
		}
		if(KeyboardHandler.getKey(GLFW_KEY_2))
		{
			speed += 0.001f;
			if (speed > 0.1)
				speed = 0.1f;
		}

//		if(KeyboardHandler.getKey(GLFW_KEY_A)) //left right
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos.x = (float) ((newPos.x - speed) * getDeltaTime());
//			transform.setPosition(newPos);
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_D))
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos.x = (float) ((newPos.x + speed) * getDeltaTime());
//			transform.setPosition(newPos);
//		}
//
//		if(KeyboardHandler.getKey(GLFW_KEY_W)) //front back
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos.z = (float) ((newPos.z - speed) * getDeltaTime());
//			transform.setPosition(newPos);
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_S))
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos.z = (float) ((newPos.z + speed) * getDeltaTime());
//			transform.setPosition(newPos);
//		}
//
//		if(KeyboardHandler.getKey(GLFW_KEY_R)) //up down
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos.y = (float) ((newPos.y + speed) * getDeltaTime());
//			transform.setPosition(newPos);
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_F))
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos.y = (float) ((newPos.y - speed) * getDeltaTime());
//			transform.setPosition(newPos);
//		}

//		if(KeyboardHandler.getKey(GLFW_KEY_1)) //speed
//		{
//			speed -= 0.001f;
//			if (speed < 0.001)
//				speed = 0.001f;
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_2))
//		{
//			speed += 0.001f;
//			if (speed > 0.1)
//				speed = 0.1f;
//		}
	}

	public static void exitGame()
	{
		glfwSetWindowShouldClose(window.getWindowId(), true);
	}
}
