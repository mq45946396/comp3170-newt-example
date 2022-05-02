package comp3170.newt.sceneobjects;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import comp3170.GLBuffers;
import comp3170.Shader;

public class Circle extends SceneObject {

	static final int N_SIDES = 16;
	
	private final Vector2f position = new Vector2f(0f, 0f);
	
	private int vertexBuffer;
	private int vertexCount;
	
	public Circle() {
		Vector3f[] vertices = new Vector3f[N_SIDES + 2];
		
		// create center of circle
		vertices[0] = new Vector3f(0f, 0f, 0f);
		
		// generate each point around the perimeter of the circle
		for(int i = 0; i < N_SIDES + 1; i++) {
			double ang = (2d * Math.PI * i) / N_SIDES;
			float sin = (float)Math.sin(ang);
			float cos = (float)Math.cos(ang);
			vertices[i+1] = new Vector3f(cos, sin, 0f);
		}
		
		// send vertex data to GPU
		this.vertexBuffer = GLBuffers.createBuffer(vertices);
		this.vertexCount  = vertices.length;
	}
	
	public void update() {
		this.getMatrix().identity().translate(this.position.x, this.position.y, 0f);
	}
	
	@Override
	protected void drawSelf(Shader shader, Matrix4f modelMatrix) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		// set attributes and uniforms
		shader.setAttribute("a_position", this.vertexBuffer);
		shader.setUniform("u_modelMatrix", modelMatrix);
		
		// draw using a triangle fan, which connects the vertices in
		// a ring to form triangles
		gl.glDrawArrays(GL_TRIANGLE_FAN, 0, this.vertexCount);
	}
	
}