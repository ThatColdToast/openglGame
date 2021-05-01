package thatcoldtoast.openglGame.io;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	private long windowId;

	public int width, height;

	public boolean wireFrame = false;
	
	public Window() {
	}

	public void createWindow(int w, int h) {
		width = w;
		height = h;

		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!");
		}
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		windowId = glfwCreateWindow(width, height, "My OpenGL Game", 0, 0);

		if (windowId == 0) {
			throw new IllegalStateException("Failed to create window!");
		}

		glfwMakeContextCurrent(windowId);
		GL.createCapabilities();
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowId, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

		glEnable(GL_DEPTH_TEST);//only render closest thingy
//		glDepthFunc(GL_LESS); //not necessary?

		glEnable(GL_CULL_FACE); //don't render insides

		glClearColor(0.05f, 0.64f, 1.0f,1.0f);

		glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_DISABLED); //input mode (hide cursor and resets it to center)
		//glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_NORMAL);


		// Turn on wireframe mode
//		glPolygonMode( GL_FRONT_AND_BACK, GL_LINE);

		// Turn off wireframe mode
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);







		glfwShowWindow(windowId);
	}
	
	/**
	 * Cleans up and destroys the window. Also de-initializes GLFW. 
	 */
	public void free() {
		glfwDestroyWindow(windowId);
		
		glfwTerminate();
	}
	
	/**
	 * Polls all events of the window. 
	 * @return
	 * True, if the window should close.
	 */
	public boolean update() {
		glfwPollEvents();
		
		if (glfwWindowShouldClose(windowId))
			return true;
		return false;
	}

	public long getWindowId() {
		return windowId;
	}
	
	/**
	 * Swaps the buffers to display an image.
	 */
	public void swapBuffers() {
		glfwSwapBuffers(windowId);
	}
}
