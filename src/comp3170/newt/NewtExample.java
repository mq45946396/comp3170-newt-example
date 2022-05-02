package comp3170.newt;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class NewtExample implements GLEventListener {

	private int screenWidth = 400;
	private int screenHeight = 400;
	
	@Override
	public void init(GLAutoDrawable d) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void display(GLAutoDrawable d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose(GLAutoDrawable d) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void reshape(GLAutoDrawable d, int x, int y, int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
