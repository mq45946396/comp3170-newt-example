package comp3170.newt;

import java.util.HashSet;
import java.util.Set;

import org.joml.Vector4f;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;

/**
 * Modified InputManager class to use NEWT input
 * 
 * Usage:
 * 
 * In your program's main() method, add this line after creating the GLWindow:
 * 	InputManager input = new InputManager(window);
 * 
 * Then pass it to your game's constructor.
 * 	NewtExample game = new NewtExample(input);
 *
 * Every frame, call the accessor methods to check for key and mouse:
 * 
 * isMouseDown() - mouse is currently held down wasMouseClicked() - mouse has
 * been clicked since the last frame getMousePosition() - mouse position within
 * the window isKeyDown() - the specified key is currently down wasKeyPressed()
 * - the specified key has been pressed since the last frame
 * 
 * At the end of the frame:
 * 
 * clear() - clear the wasPressed and wasClicked flags
 * 
 * 
 * @author malcolmryan, Jack Davenport
 *
 */

public class InputManager implements KeyListener, MouseListener {

	private Set<Short> keysDown;
	private Set<Short> keysPressed;
	private boolean mouseDown;
	private boolean mouseClicked;
	private Vector4f mousePosition; 
	private GLWindow window;

	public InputManager(GLWindow window) {
		this.window = window;
		this.keysDown = new HashSet<Short>();
		this.keysPressed = new HashSet<Short>();
		this.mousePosition = new Vector4f(0, 0, 0, 1);

		window.addKeyListener(this);
		window.addMouseListener(this);
		window.requestFocus();
	}

	/**
	 * Test if the mouse button is currently pressed
	 * 
	 * @return true if the mouse button is pressed
	 */
	public boolean isMouseDown() {
		return mouseDown;
	}

	/**
	 * Test if the mouse button was clicked this frame
	 * 
	 * @return true if the mouse button is pressed
	 */
	public boolean wasMouseClicked() {
		return mouseClicked;
	}

	/**
	 * Write the current mouse position in NDC into dest as a 3D
	 * homogenous point of the form (x, y, 0, 1)
	 * 
	 * @param dest The destination vector to write the value
	 * @return the mouse position vector in NDC
	 */
	public Vector4f getMousePosition(Vector4f dest) {
		return this.mousePosition.get(dest);
	}

	/**
	 * Test if the specified key is currently pressed. Note: the input is a keycode
	 * value, as specified on the KeyEvent class.
	 * 
	 * So, for instance, to test if the up arrow is pressed call:
	 * 
	 * input.isKeyDown(KeyEvent.VK_UP)
	 * 
	 * @param keyCode The integer keycode for the key
	 * @return true if the key is pressed
	 */

	public boolean isKeyDown(short keyCode) {
		return keysDown.contains(keyCode);
	}

	/**
	 * Test if the specified key has been pressed since the last call to clear().
	 * 
	 * Note: the input is a keycode value, as specified on the KeyEvent class.
	 * 
	 * So, for instance, to test if the up arrow has been pressed since the last
	 * frame:
	 * 
	 * input.wasKeyPressed(KeyEvent.VK_UP)
	 *
	 * @param keyCode The integer keycode for the key
	 * @return true if the key has been pressed
	 */

	public boolean wasKeyPressed(short keyCode) {
		return keysPressed.contains(keyCode);
	}

	public void clear() {
		keysPressed.clear();
		mouseClicked = false;
	}

	// KeyListener methods

	@Override
	public void keyPressed(KeyEvent e) {
		short keyCode = e.getKeyCode();
		this.keysDown.add(keyCode);
		this.keysPressed.add(keyCode);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.keysDown.remove(e.getKeyCode());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		mouseClicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// convert to NDC
		mousePosition.x = 2.0f * e.getX() / window.getWidth() - 1;
		mousePosition.y = 2.0f * e.getY() / window.getHeight() - 1;
		mousePosition.y = -mousePosition.y;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// convert to NDC
		mousePosition.x = 2.0f * e.getX() / window.getWidth() - 1;
		mousePosition.y = 2.0f * e.getY() / window.getHeight() - 1;
		mousePosition.y = -mousePosition.y;
	}

	@Override
	public void mouseWheelMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
