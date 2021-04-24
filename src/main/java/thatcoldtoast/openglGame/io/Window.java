package thatcoldtoast.openglGame.io;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	private long windowId;

	public int width, height;
	
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

		glEnable(GL_DEPTH_TEST);
		//glDepthFunc(GL_LESS); //not necessary?
		
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
