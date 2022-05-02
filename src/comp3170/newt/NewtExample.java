package comp3170.newt;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;

import java.io.File;
import java.io.IOException;

import org.joml.Matrix4f;

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

import comp3170.GLException;
import comp3170.Shader;
import comp3170.newt.sceneobjects.Circle;
import comp3170.newt.sceneobjects.SceneObject;

public class NewtExample implements GLEventListener {

	final private File DIRECTORY = new File("src/comp3170/newt"); 
	final private String VERTEX_SHADER = "vertex.glsl";
	final private String FRAGMENT_SHADER = "fragment.glsl";
	
	private int screenWidth = 600;
	private int screenHeight = 600;
	private InputManager input;
	
	private Shader shader;
	private SceneObject root;
	private Circle circle;
	
	private float cameraSize = 30f;
	private Matrix4f projMat = new Matrix4f();
	
	// no window construction is happening here anymore
	public NewtExample(InputManager input) {
		this.input = input;
	}
	
	@Override
	public void init(GLAutoDrawable d) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glClearColor(0.4f, 0.6f, 0.9f, 1f);
		
		this.shader = compileShader(VERTEX_SHADER, FRAGMENT_SHADER);
		
		// create scene graph root
		this.root = new SceneObject();
		
		// create circle object
		this.circle = new Circle();
		this.circle.setParent(this.root);
	}
	
	@Override
	public void display(GLAutoDrawable d) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		// calculate projection matrix
		float halfSize = cameraSize / 2f;
		float aspect = (float)this.screenWidth / (float)this.screenHeight;
		this.projMat.setOrtho(-aspect * halfSize, aspect * halfSize, -halfSize, halfSize, -1f, 1f);

		// allow the user to move the circle
		this.circle.update(this.input, this.projMat);
		
		// clear screen
		gl.glViewport(0, 0, this.screenWidth, this.screenHeight);
		gl.glClear(GL_COLOR_BUFFER_BIT);
		
		// draw the scene graph
		this.shader.enable();
		this.shader.setUniform("u_projectionMatrix", this.projMat);
		this.root.draw(this.shader);
		
		// update the input manager for this frame
		this.input.clear();
	}

	@Override
	public void dispose(GLAutoDrawable d) {
		// clean up resources on close
		this.circle.delete();
	}
	
	@Override
	public void reshape(GLAutoDrawable d, int x, int y, int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
	}
	
	private Shader compileShader(String vertex, String fragment) {
		try {
			File vertexShader = new File(DIRECTORY, vertex);
			File fragmentShader = new File(DIRECTORY, fragment);
			return new Shader(vertexShader, fragmentShader);
		} catch (IOException | GLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	// the main differences in the program are down here:
	// this method is where the OpenGL context and window are created and configured
	
	// as you can see it is a lot less cluttered and more to the point than swing
	// (but also the API calls are very similar)
	
	public static void main(String[] args) {
		// create the profile and capabilities
		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities capabilities = new GLCapabilities(profile);
		
		// add capability settings here (e.g. MSAA)
		
		// create the window, animator and input manager
		GLWindow window = GLWindow.create(capabilities);
		Animator animator = new Animator(window);
		InputManager input = new InputManager(window);
		
		// create the game instance and configure the window
		NewtExample game = new NewtExample(input);
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
