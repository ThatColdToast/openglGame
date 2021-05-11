package thatcoldtoast.openglGame;

import org.joml.Matrix4f;
import thatcoldtoast.openglGame.entities.Player;
import thatcoldtoast.openglGame.graphics.*;
import thatcoldtoast.openglGame.handlers.KeyboardHandler;
import thatcoldtoast.openglGame.io.Window;
import thatcoldtoast.openglGame.world.World;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;


public class Main {
	public static double deltaTime = 0.0;
	public static double oldTime = System.currentTimeMillis();
	public static double currentTime = 0.0;
	public static Window window;
	public static Player MainPlayer;
	public static float speed = 0.1f;
	public static double turnSpeed = 0.05;
	public static double turnSpeedQuad = 0.002;
//	public static Camera camera;
//  public static Transform MainTransform;
	public static Texture texture = new Texture();

	public static void main(String[] args) {

		window = new Window();
		
		window.createWindow(1920, 1080);

		World world = new World();
		long seed = 0;
		world.create(seed);

		Shader shader = new Shader();
		shader.create("basic");
		
		texture.create("/textures/TextureAtlas.png");

		MainPlayer = new Player();
//		camera = new Camera();
//		MainTransform = new Transform();

		MainPlayer.camera.setPerspective((float)Math.toRadians(70), (float) window.width / (float) window.height, 0.01f, 1000.0f);
		MainPlayer.camera.setPosition(new Vector3f(20, 0, 20));
//		camera.setRotation(new Quaternionf(new AxisAngle4f((float)Math.toRadians(0), new Vector3f(1,0,0))));
		
		boolean isRunning = true;
		
		float frameNum = 0;
		
		while (isRunning) { //main game loop
			isRunning = !window.update(); //glfwPollEvents() is in window.update();

			frameNum++;

			//System.out.printf("Delta Time: %.5f\n", getDeltaTime());

			MainPlayer.updatePlayer();
//			updateKeys(MainTransform);
//			updateMouse();

			//transform.setPosition(new Vector3f((float)Math.sin(Math.toRadians((float) frameNum)), 0, 0));
			//transform.getRotation().rotateAxis((float)Math.toRadians(1), 0, 1, 0);

//			Quaternionf newRotation = camera.getRotation(); //clamp rotation of camera
//			newRotation.z = 0.0f;
//			camera.setRotation(newRotation);

			//---------------------------- OPENGL Stuff Below ----------------------------
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			shader.useShader();
			shader.setCamera(MainPlayer.camera);
			shader.setTransform(MainPlayer.MainTransform);
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

//	public static void updateMouse()
//	{
//		double[] cursorPos = camera.getCursorPos(window);
////		Quaternionf rot = new Quaternionf(new AxisAngle4f((float)Math.toRadians((float) (-turnSpeedQuad * cursorPos[1]) * getDeltaTime()), new Vector3f(1,0,0)));
////		rot.add(new Quaternionf(new AxisAngle4f((float)Math.toRadians((float) (-turnSpeedQuad * cursorPos[0]) * getDeltaTime()), new Vector3f(0,1,0))));
////		rot.add(camera.getRotation());
////		camera.setRotation(rot);
//
////		camera.setRotation(new Quaternionf(new AxisAngle4f((float)Math.toRadians(45), new Vector3f(1,0,0))));
//
//		camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeedQuad) * cursorPos[0] * getDeltaTime()), 0, -1, 0);
//		camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeedQuad) * cursorPos[1] * getDeltaTime()), -1, 0, 0);
//	}
//
//	public static void updateKeys(Transform transform)
//	{
//		if(KeyboardHandler.getKey(GLFW_KEY_Q)) {
//			camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * getDeltaTime()), 0, 0, 1);
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_E)) {
//			camera.getRotation().rotateAxis((float) (Math.toRadians(turnSpeed) * getDeltaTime()), 0, 0, -1);
//		}
//
//		Matrix4f invCam = new Matrix4f(camera.getTransformation()).invert();
//		Vector3f forward = new Vector3f(0, 0, -1);
//		Vector3f right = new Vector3f(1, 0, 0);
////		System.out.printf("Before Forward:   X: %.2f Y: %.2f Z: %.2f\n", forward.x, forward.y, forward.z);
////		System.out.printf("Before Right:     X: %.2f Y: %.2f Z: %.2f\n\n", right.x, right.y, right.z);
//		invCam.transformDirection(forward);
//		invCam.transformDirection(right);
//
////		System.out.printf("Forward:   X: %.2f Y: %.2f Z: %.2f\n", forward.x, forward.y, forward.z);
////		System.out.printf("Right:     X: %.2f Y: %.2f Z: %.2f\n\n", right.x, right.y, right.z);
//
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
//
//
//
//		if(KeyboardHandler.getKey(GLFW_KEY_P))
//		{
//			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL); // Turn off wireframe mode
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_O))
//		{
//			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // Turn on wireframe mode
//		}
//
//
//
//		if(KeyboardHandler.getKey(GLFW_KEY_ESCAPE))
//		{
//			exitGame();
//		}
//
//		if(KeyboardHandler.getKey(GLFW_KEY_A)) //left right
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos = newPos.add(right.mul(speed));
//			transform.setPosition(newPos);
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_D))
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos = newPos.add(right.mul(-speed));
//			transform.setPosition(newPos);
//		}
//
//		if(KeyboardHandler.getKey(GLFW_KEY_W)) //front back
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos = newPos.add(forward.mul(-speed));
//			transform.setPosition(newPos);
//
//
////			Vector3f newPos = transform.getPosition();
////			newPos.z = (float) (newPos.z + speed);
////			transform.setPosition(newPos.mul(forward));
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_S))
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos = newPos.add(forward.mul(speed));
//			transform.setPosition(newPos);
//		}
//
//		if(KeyboardHandler.getKey(GLFW_KEY_R)) //up down
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos.y = (float) (newPos.y - speed);
//			transform.setPosition(newPos);
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_F))
//		{
//			Vector3f newPos = transform.getPosition();
//			newPos.y = (float) (newPos.y + speed);
//			transform.setPosition(newPos);
//		}
//
//		if(KeyboardHandler.getKey(GLFW_KEY_1)) //speed
//		{
//			speed -= 0.01f;
//			if (speed < 0.1)
//				speed = 0.1f;
//		}
//		if(KeyboardHandler.getKey(GLFW_KEY_2))
//		{
//			speed += 0.01f;
//			if (speed > 1.0f)
//				speed = 1.0f;
//		}
//	}

	public static void exitGame()
	{
		glfwSetWindowShouldClose(window.getWindowId(), true);
	}
}
