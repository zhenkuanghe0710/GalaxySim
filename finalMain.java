import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;


/**
 * finalMain.java is the main program for the final program option.
 * 
 * @author Zhenkuang He
 * @version 14-May-2013
 * 
 */
public class finalMain implements GLEventListener, KeyListener {

	// Buffer info
	private int vbuffer[];
	private int ebuffer[];
	private int numVerts[];

	// Program ID
	public int ObjectID;

	public finalParams fin;

	// Shape info
	Shape Shape;

	// the Canvas
	GLCanvas myCanvas;

	/**
	 * constructor
	 */
	public finalMain(GLCanvas G) {
		vbuffer = new int[1];
		ebuffer = new int[1];
		numVerts = new int[1];

		myCanvas = G;
		fin = new finalParams();

		G.addGLEventListener(this);
		G.addKeyListener(this);
	}

	/**
	 * Error check method
	 * 
	 * @param gl2
	 */
	@SuppressWarnings("unused")
	private void errorCheck(GL2 gl2) {
		int code = gl2.glGetError();
		if (code == GL.GL_NO_ERROR)
			System.err.println("All is well");
		else
			System.err.println("Problem - error code : " + code);

	}

	/**
	 * Called by the drawable to initiate OpenGL rendering by the client.
	 */
	public void display(GLAutoDrawable drawable) {

		// Get GL
		GL2 gl2 = (drawable.getGL()).getGL2();

		// Create the shape
		createShape(gl2);

		// Clear the frame buffers
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		// Bind the vertex buffer
		gl2.glBindBuffer(GL.GL_ARRAY_BUFFER, vbuffer[0]);

		// Bind the element array buffer
		gl2.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[0]);

		// Set up the attribute variables
		gl2.glUseProgram(ObjectID);

		long vertBsize = Shape.getNVerts() * 4l * 4l;
		long ndataSize = Shape.getNVerts() * 3l * 4l;

		int Position = gl2.glGetAttribLocation(ObjectID, "vPosition");
		gl2.glEnableVertexAttribArray(Position);
		gl2.glVertexAttribPointer(Position, 4, GL.GL_FLOAT, false, 0, 0l);

		int Normal = gl2.glGetAttribLocation(ObjectID, "vNormal");
		gl2.glEnableVertexAttribArray(Normal);
		gl2.glVertexAttribPointer(Normal, 3, GL.GL_FLOAT, false, 0, vertBsize);

		int TexCoord = gl2.glGetAttribLocation(ObjectID, "vTexCoord");
		gl2.glEnableVertexAttribArray(TexCoord);
		gl2.glVertexAttribPointer(TexCoord, 2, GL.GL_FLOAT, false, 0, vertBsize
				+ ndataSize);

		// Setup Sun's texture, view and draw it
		fin.loadTexture("sun");
		fin.setUptexture(ObjectID, gl2, "sun");
		fin.setUpView(ObjectID, gl2, "sun");
		fin.setUpPhong(ObjectID, gl2, "sun");
		gl2.glDrawElements(GL.GL_TRIANGLES, numVerts[0], GL.GL_UNSIGNED_SHORT,
				0l);

		// Setup Mercury's texture, view and draw it
		fin.loadTexture("mercury");
		fin.setUptexture(ObjectID, gl2, "mercury");
		fin.setUpView(ObjectID, gl2, "mercury");
		fin.setUpPhong(ObjectID, gl2, "mercury");
		gl2.glDrawElements(GL.GL_TRIANGLES, numVerts[0], GL.GL_UNSIGNED_SHORT,
				0l);

		// Setup Venus's texture, view and draw it
		fin.loadTexture("venus");
		fin.setUptexture(ObjectID, gl2, "venus");
		fin.setUpView(ObjectID, gl2, "venus");
		fin.setUpPhong(ObjectID, gl2, "venus");
		gl2.glDrawElements(GL.GL_TRIANGLES, numVerts[0], GL.GL_UNSIGNED_SHORT,
				0l);

		// Setup Earth's texture, view and draw it
		fin.loadTexture("earth");
		fin.setUptexture(ObjectID, gl2, "earth");
		fin.setUpView(ObjectID, gl2, "earth");
		fin.setUpPhong(ObjectID, gl2, "earth");
		gl2.glDrawElements(GL.GL_TRIANGLES, numVerts[0], GL.GL_UNSIGNED_SHORT,
				0l);

		// Setup Mars's texture, view and draw it
		fin.loadTexture("mars");
		fin.setUptexture(ObjectID, gl2, "mars");
		fin.setUpView(ObjectID, gl2, "mars");
		fin.setUpPhong(ObjectID, gl2, "mars");
		gl2.glDrawElements(GL.GL_TRIANGLES, numVerts[0], GL.GL_UNSIGNED_SHORT,
				0l);

		// Setup Jupiter's texture, view and draw it
		fin.loadTexture("jupiter");
		fin.setUptexture(ObjectID, gl2, "jupiter");
		fin.setUpView(ObjectID, gl2, "jupiter");
		fin.setUpPhong(ObjectID, gl2, "jupiter");
		gl2.glDrawElements(GL.GL_TRIANGLES, numVerts[0], GL.GL_UNSIGNED_SHORT,
				0l);

	}

	/**
	 * Notifies the listener to perform the release of all OpenGL resources per
	 * GLContext, such as memory buffers and GLSL programs.
	 */
	public void dispose(GLAutoDrawable drawable) {

	}

	/**
	 * Called by the drawable immediately after the OpenGL context is
	 * initialized.
	 */
	public void init(GLAutoDrawable drawable) {
		// Get the gl object
		GL2 gl2 = drawable.getGL().getGL2();

		// Load shaders
		shaderProgram myShaders = new shaderProgram();
		ObjectID = myShaders
				.readAndCompile(gl2, "vshader.glsl", "fshader.glsl");
		if (ObjectID == 0) {
			System.err.println("Error setting up Sunshaders");
			System.exit(1);
		}

		// Set up GLs
		gl2.glEnable(GL.GL_DEPTH_TEST);
		gl2.glEnable(GL.GL_CULL_FACE);
		gl2.glCullFace(GL.GL_BACK);
		gl2.glFrontFace(GL.GL_CCW);
		gl2.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl2.glDepthFunc(GL.GL_LEQUAL);
		gl2.glClearDepth(1.0f);

	}

	/**
	 * Called by the drawable during the first repaint after the component has
	 * been resized.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {

	}

	/**
	 * creates Sun shape
	 */
	public void createShape(GL2 gl2) {

		Shape = new Shape();

		// Clear the old shape
		Shape.clear();

		// Make a shape
		Shape.makeShape();

		// Get The objects, elements, textures and normals points
		Buffer points = Shape.getVerticies();
		Buffer elements = Shape.getElements();
		Buffer texCoords = Shape.getUV();
		Buffer normals = Shape.getNormals();

		// Set up the vertex buffer
		int bf[] = new int[1];
		gl2.glGenBuffers(1, bf, 0);
		vbuffer[0] = bf[0];

		long vertBsize = Shape.getNVerts() * 4l * 4l;
		long ndataSize = Shape.getNVerts() * 3l * 4l;
		long tdataSize = Shape.getNVerts() * 2l * 4l;

		gl2.glBindBuffer(GL.GL_ARRAY_BUFFER, vbuffer[0]);
		gl2.glBufferData(GL.GL_ARRAY_BUFFER, vertBsize + ndataSize + tdataSize,
				null, GL.GL_STATIC_DRAW);

		// Sub data of three different points
		gl2.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, vertBsize, points);
		gl2.glBufferSubData(GL.GL_ARRAY_BUFFER, vertBsize, ndataSize, normals);
		gl2.glBufferSubData(GL.GL_ARRAY_BUFFER, vertBsize + ndataSize,
				tdataSize, texCoords);

		// Set up the elements buffer
		gl2.glGenBuffers(1, bf, 0);
		ebuffer[0] = bf[0];
		long eBuffSize = Shape.getNVerts() * 2l;
		gl2.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[0]);
		gl2.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize, elements,
				GL.GL_STATIC_DRAW);

		// Record the point
		numVerts[0] = Shape.getNVerts();
	}

	/**
	 * Set up Key Listener
	 */
	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Invoked when a key has been pressed.
	 */
	public void keyPressed(KeyEvent e) {
		// Get the key that was pressed
		char key = e.getKeyChar();

		// Respond appropriately
		switch (key) {
		case 'q':
		case 'Q':
			System.exit(0);
			break;
		}

		// do a redraw
		myCanvas.display();
	}

	/**
	 * main program
	 */
	public static void main(String[] args) {
		// GL setup
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(caps);

		// create tessMain
		new finalMain(canvas);

		Frame frame = new Frame("CG1 - Final");
		frame.setSize(768, 768);

		frame.add(canvas);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
