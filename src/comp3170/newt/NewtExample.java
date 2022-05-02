package comp3170.newt;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import static com.jogamp.opengl.GL4.*;

public class NewtExample implements GLEventListener {

	private int screenWidth = 600;
	private int screenHeight = 600;
	
	@Override
	public void init(GLAutoDrawable d) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glClearColor(0.4f, 0.6f, 0.9f, 1f);
	}
	
	@Override
	public void display(GLAutoDrawable d) {
		GL4 gl = (GL4) GLContext.getCurrentGL();

		gl.glViewport(0, 0, this.screenWidth, this.screenHeight);
		gl.glClear(GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void dispose(GLAutoDrawable d) {
		
	}
	
	@Override
	public void reshape(GLAutoDrawable d, int x, int y, int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
	}

	public static void main(String[] args) {
		// create the profile and capabilities
		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities capabilities = new GLCapabilities(profile);
		
		// add capability settings here (e.g. MSAA)
		
		// create the window and animator
		GLWindow window = GLWindow.create(capabilities);
		Animator animator = new Animator(window);
		
		// create the game instance and configure the window
		NewtExample game = new NewtExample();
		window.addGLEventListener(game);
		window.setSize(game.screenWidth, game.screenHeight);
		window.setTitle("COMP3170 Newt Example");
		window.setResizable(true);
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent e) {
				// program hangs by default, we need to terminate
				// it manually here
				System.exit(0);
			}
		});
		
		// show window and start animating
		window.setVisible(true);
		animator.start();
	}

}
