package thatcoldtoast.openglGame;

import thatcoldtoast.openglGame.graphics.Camera;
import thatcoldtoast.openglGame.graphics.Mesh;
import thatcoldtoast.openglGame.graphics.Shader;
import thatcoldtoast.openglGame.graphics.Texture;
import thatcoldtoast.openglGame.graphics.Transform;
import thatcoldtoast.openglGame.io.Window;

import static org.lwjgl.opengl.GL11.*;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Main {
	public static long time = System.currentTimeMillis();

	public static void main(String[] args) {
		Window window = new Window();
		
		window.createWindow(1920, 1080);
		
		Mesh mesh = new Mesh();
		mesh.create(new float[] {
				-1, -1, 0,   0,  1,
				-1,  1, 0,   0,  0,
				 1, -1, 0,   1,  1,
		});
		
		Shader shader = new Shader();
		shader.create("basic");
		
		Texture texture = new Texture();
		texture.create("/textures/wood.png");
		
		Camera camera = new Camera();
		Transform transform = new Transform();
		
		camera.setPerspective((float)Math.toRadians(70), 640.0f / 480.0f, 0.01f, 1000.0f);
		camera.setPosition(new Vector3f(0, 1, 3));
		camera.setRotation(new Quaternionf(new AxisAngle4f((float)Math.toRadians(-30), new Vector3f(1,0,0))));
		
		boolean isRunning = true;
		
		float frameNum = 0;
		
		while (isRunning) {
			isRunning = !window.update();

			frameNum++;
			transform.setPosition(new Vector3f((float)Math.sin(Math.toRadians((float) frameNum)), 0, 0));
			transform.getRotation().rotateAxis((float)Math.toRadians(1), 0, 1, 0);
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			shader.useShader();
			shader.setCamera(camera);
			shader.setTransform(transform);
			shader.setSampleTexture(0);
			texture.bind();
			mesh.draw();
			
			window.swapBuffers();

			long now = System.currentTimeMillis();

			if(time % 100 == 0)
				System.out.printf("Framerate: %.2f\n", 1000.0 / (now - time));

			time = now;
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		texture.destroy();
		mesh.destroy();
		shader.destroy();
		
		window.free();
	}
}
