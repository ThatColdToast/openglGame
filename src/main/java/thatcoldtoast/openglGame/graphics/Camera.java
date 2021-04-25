package thatcoldtoast.openglGame.graphics;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import thatcoldtoast.openglGame.io.Window;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class Camera {
	private Vector3f position;
	private Quaternionf rotation;
	private Matrix4f projection;
	
	public Camera() {
		position = new Vector3f();
		rotation = new Quaternionf();
		projection = new Matrix4f();
	}

	public Matrix4f getTransformation() {
		Matrix4f returnValue = new Matrix4f();
		
		returnValue.rotate(rotation.conjugate(new Quaternionf()));
		returnValue.translate(position.mul(-1, new Vector3f()));
		
		return returnValue;
	}
	
	public void setOrthographic(float left, float right, float top, float bottom) {
		projection.setOrtho2D(left, right, bottom, top);
	}
	
	public void setPerspective(float fov, float aspectRatio, float zNear, float zFar) {
		projection.setPerspective(fov, aspectRatio, zNear, zFar);
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Quaternionf getRotation() {
		return rotation;
	}

	public void setRotation(Quaternionf rotation) {
		this.rotation = rotation;
	}

	public Matrix4f getProjection() {
		return projection;
	}

	private static double[] oldPos = new double[2];
	public static double[] getCursorPos(Window window) {
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(window.getWindowId(), xBuffer, yBuffer);
		double[] pos = new double[2];
		pos[0] = xBuffer.get(0);
		pos[1] = yBuffer.get(0);

		double[] calcPos = new double[2];
		calcPos[0] = pos[0] - oldPos[0];
		calcPos[1] = pos[1] - oldPos[1];
		oldPos = pos;

		//System.out.println(Arrays.toString(calcPos));

		return calcPos;
	}
}
