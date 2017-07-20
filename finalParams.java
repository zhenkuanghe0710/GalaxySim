import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.*;

/**
 * finalParams.java is the main functional program for the final program option.
 * It do the texture loading, texture mapping, view setting, and color setting
 * jobs.
 * 
 * @author Zhenkuang He
 * @version 14-May-2013
 * 
 */
public class finalParams {

	// Buffer to store pixels data
	IntBuffer sunB, earthB, mercuryB, marsB, jupiterB, venusB;

	// Image's width and height
	int sunW, sunH, earthW, earthH, mercuryW, mercuryH, marsW, marsH, jupiterW,
			jupiterH, venusW, venusH;

	/**
	 * constructor
	 */
	public finalParams() {

	}

	/**
	 * Texture loading depends on the object name
	 * 
	 * @param objectName
	 */
	public void loadTexture(String objectName) {
		BufferedImage bi;

		try {

			switch (objectName) {

			case "sun":
				// Read in image
				bi = ImageIO.read(new File("Sun.jpg"));

				// Get the width and height
				sunW = bi.getWidth();
				sunH = bi.getHeight();

				// Store in buffer
				sunB = IntBuffer.wrap(bi
						.getRGB(0, 0, sunW, sunH, null, 0, sunW));
				break;

			case "mercury":
				// Read in image
				bi = ImageIO.read(new File("Mercury.jpg"));

				// Get the width and height
				mercuryW = bi.getWidth();
				mercuryH = bi.getHeight();

				// Store in buffer
				mercuryB = IntBuffer.wrap(bi.getRGB(0, 0, mercuryW, mercuryH,
						null, 0, mercuryW));
				break;

			case "venus":
				// Read in image
				bi = ImageIO.read(new File("Venus.jpg"));

				// Get the width and height
				venusW = bi.getWidth();
				venusH = bi.getHeight();

				// Store in buffer
				venusB = IntBuffer.wrap(bi.getRGB(0, 0, venusW, venusH, null,
						0, venusW));
				break;

			case "earth":
				// Read in image
				bi = ImageIO.read(new File("Earth.jpg"));

				// Get the width and height
				earthW = bi.getWidth();
				earthH = bi.getHeight();

				// Store in buffer
				earthB = IntBuffer.wrap(bi.getRGB(0, 0, earthW, earthH, null,
						0, earthW));
				break;

			case "mars":
				// Read in image
				bi = ImageIO.read(new File("Mars.jpg"));

				// Get the width and height
				marsW = bi.getWidth();
				marsH = bi.getHeight();

				// Store in buffer
				marsB = IntBuffer.wrap(bi.getRGB(0, 0, marsW, marsH, null, 0,
						marsW));
				break;

			case "jupiter":
				// Read in image
				bi = ImageIO.read(new File("Jupiter.jpg"));

				// Get the width and height
				jupiterW = bi.getWidth();
				jupiterH = bi.getHeight();

				// Store in buffer
				jupiterB = IntBuffer.wrap(bi.getRGB(0, 0, jupiterW, jupiterH,
						null, 0, jupiterW));
				break;

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Texture mapping depends on the object name
	 * 
	 * @param program		The program ID
	 * @param gl2			The OpenGL function
	 * @param objectName	The object name
	 */
	@SuppressWarnings("static-access")
	public void setUptexture(int program, GL2 gl2, String objectName) {

		gl2.glUseProgram(program);

		// Generate texture ID
		int[] textures = new int[1];
		gl2.glGenTextures(1, textures, 0);

		// Get texture location
		int texture = gl2.glGetUniformLocation(program, "texture");

		// Set the unit ID
		gl2.glUniform1i(texture, 0);

		// Active unit
		gl2.glActiveTexture(gl2.GL_TEXTURE0 + 0);

		// Bind texture to ID
		gl2.glBindTexture(gl2.GL_TEXTURE_2D, textures[0]);

		// Set parameters
		gl2.glTexParameterf(gl2.GL_TEXTURE_2D, gl2.GL_TEXTURE_MIN_FILTER,
				gl2.GL_NEAREST);
		gl2.glTexParameterf(gl2.GL_TEXTURE_2D, gl2.GL_TEXTURE_MAG_FILTER,
				gl2.GL_NEAREST);
		gl2.glTexParameterf(gl2.GL_TEXTURE_2D, gl2.GL_TEXTURE_WRAP_S,
				gl2.GL_REPEAT);
		gl2.glTexParameterf(gl2.GL_TEXTURE_2D, gl2.GL_TEXTURE_WRAP_T,
				gl2.GL_REPEAT);
		gl2.glTexEnvi(gl2.GL_TEXTURE_ENV, gl2.GL_TEXTURE_ENV_MODE,
				gl2.GL_MODULATE);

		// Enable texture
		gl2.glEnable(gl2.GL_TEXTURE_2D);

		// Get the texture data
		switch (objectName) {

		case "sun":
			gl2.glTexImage2D(gl2.GL_TEXTURE_2D, 0, gl2.GL_RGB, sunW, sunH, 0,
					gl2.GL_BGRA, gl2.GL_UNSIGNED_BYTE, sunB);
			break;

		case "mercury":
			gl2.glTexImage2D(gl2.GL_TEXTURE_2D, 0, gl2.GL_RGB, mercuryW,
					mercuryH, 0, gl2.GL_BGRA, gl2.GL_UNSIGNED_BYTE, mercuryB);
			break;

		case "venus":
			gl2.glTexImage2D(gl2.GL_TEXTURE_2D, 0, gl2.GL_RGB, venusW, venusH,
					0, gl2.GL_BGRA, gl2.GL_UNSIGNED_BYTE, venusB);
			break;

		case "earth":
			gl2.glTexImage2D(gl2.GL_TEXTURE_2D, 0, gl2.GL_RGB, earthW, earthH,
					0, gl2.GL_BGRA, gl2.GL_UNSIGNED_BYTE, earthB);
			break;

		case "mars":
			gl2.glTexImage2D(gl2.GL_TEXTURE_2D, 0, gl2.GL_RGB, marsW, marsH, 0,
					gl2.GL_BGRA, gl2.GL_UNSIGNED_BYTE, marsB);
			break;

		case "jupiter":
			gl2.glTexImage2D(gl2.GL_TEXTURE_2D, 0, gl2.GL_RGB, jupiterW,
					jupiterH, 0, gl2.GL_BGRA, gl2.GL_UNSIGNED_BYTE, jupiterB);
			break;
		}
		gl2.glUseProgram(program);
	}

	/**
	 * View and model setting depends on the object name
	 * 
	 * @param program		The program ID
	 * @param gl2			The OpenGL function
	 * @param objectName	The object name
	 */
	public void setUpView(int program, GL2 gl2, String objectName) {

		// Translate, scale, and rotate array
		float T[] = new float[3];
		float S[] = new float[3];
		float R[] = new float[3];

		// Set up the transformation parameter
		switch (objectName) {
		case "sun":
			T[0] = -3.5f;
			T[1] = 0.5f;
			T[2] = 0f;
			S[0] = 5f;
			S[1] = 5f;
			S[2] = 5f;
			R[0] = 0f;
			R[1] = 90f;
			R[2] = 0f;
			break;

		case "mercury":
			T[0] = -1.5f;
			T[1] = 0f;
			T[2] = -2f;
			S[0] = 0.25f;
			S[1] = 0.25f;
			S[2] = 0.25f;
			R[0] = 0f;
			R[1] = 0f;
			R[2] = 0f;
			break;

		case "venus":
			T[0] = -0.6f;
			T[1] = 0f;
			T[2] = 0f;
			S[0] = 0.4f;
			S[1] = 0.4f;
			S[2] = 0.4f;
			R[0] = 0f;
			R[1] = 0f;
			R[2] = 0f;
			break;

		case "earth":
			T[0] = 0.2f;
			T[1] = 0f;
			T[2] = 0.7f;
			S[0] = 0.5f;
			S[1] = 0.5f;
			S[2] = 0.5f;
			R[0] = 0f;
			R[1] = 0f;
			R[2] = 0f;
			break;

		case "mars":
			T[0] = -0.2f;
			T[1] = 0f;
			T[2] = -1.8f;
			S[0] = 0.5f;
			S[1] = 0.5f;
			S[2] = 0.5f;
			R[0] = 0f;
			R[1] = 0f;
			R[2] = 0f;
			break;

		case "jupiter":
			T[0] = 1.2f;
			T[1] = 0f;
			T[2] = 0.8f;
			S[0] = 0.6f;
			S[1] = 0.6f;
			S[2] = 0.6f;
			R[0] = 0f;
			R[1] = 0f;
			R[2] = 0f;
			break;
		}

		// Camera view and projection array
		float V[] = View();
		float O[] = orthographic();

		int translate_value = gl2.glGetUniformLocation(program,
				"translate_value");
		gl2.glUniform3fv(translate_value, 1, T, 0);

		int scale_factor = gl2.glGetUniformLocation(program, "scale_factor");
		gl2.glUniform3fv(scale_factor, 1, S, 0);

		int rotate_degree = gl2.glGetUniformLocation(program, "rotate_degree");
		gl2.glUniform3fv(rotate_degree, 1, R, 0);

		int view = gl2.glGetUniformLocation(program, "view");
		gl2.glUniformMatrix4fv(view, 1, false, V, 0);

		int project = gl2.glGetUniformLocation(program, "project");
		gl2.glUniformMatrix4fv(project, 1, false, O, 0);
	}

	/**
	 * View method to generate the view matrix
	 * 
	 * @return Float[]	The view matrix
	 */
	public float[] View() {
		float V[] = new float[16];

		// Camera matrix
		float look[] = { 0.0f, 0.0f, 0.0f };
		float eye[] = { 1.0f, 1.0f, 2.0f };
		float up[] = { 0.0f, 1.0f, 0.0f };

		// Compute the view matrix
		float n[] = new float[4];
		n[0] = eye[0] - look[0];
		n[1] = eye[1] - look[1];
		n[2] = eye[2] - look[2];
		float dn = (float) Math.sqrt(n[0] * n[0] + n[1] * n[1] + n[2] * n[2]);
		n[0] = n[0] / dn;
		n[1] = n[1] / dn;
		n[2] = n[2] / dn;

		float u[] = new float[4];
		u[0] = up[1] * n[2] - up[2] * n[1];
		u[1] = up[2] * n[0] - up[0] * n[2];
		u[2] = up[0] * n[1] - up[1] * n[0];
		float du = (float) Math.sqrt(u[0] * u[0] + u[1] * u[1] + u[2] * u[2]);
		u[0] = u[0] / du;
		u[1] = u[1] / du;
		u[2] = u[2] / du;

		float v[] = new float[4];
		v[0] = n[1] * u[2] - n[2] * u[1];
		v[1] = n[2] * u[0] - n[0] * u[2];
		v[2] = n[0] * u[1] - n[1] * u[0];

		u[3] = -(u[0] * eye[0] + u[1] * eye[1] + u[2] * eye[2]);
		v[3] = -(v[0] * eye[0] + v[1] * eye[1] + v[2] * eye[2]);
		n[3] = -(n[0] * eye[1] + n[1] * eye[1] + n[2] * eye[2]);

		V[0] = u[0];
		V[1] = v[0];
		V[2] = n[0];
		V[3] = 0;

		V[4] = u[1];
		V[5] = v[1];
		V[6] = n[1];
		V[7] = 0;

		V[8] = u[2];
		V[9] = v[2];
		V[10] = n[2];
		V[11] = 0;

		V[12] = u[3];
		V[13] = v[3];
		V[14] = n[3];
		V[15] = 1;

		return V;
	}

	/**
	 * Orthographic method to generate orthographic project matrix
	 * 
	 * @return	Float[]	The orthographic project matrix
	 */
	public float[] orthographic() {
		float O[] = new float[16];
		float l, r, t, b, n, f;
		
		// The project parameters
		l = -1.5f;
		r = 1.0f;
		t = 1.5f;
		b = -1.5f;
		n = 1.0f;
		f = 8.5f;

		// Compute the orthographic matrix
		O[0] = 2 / (r - l);
		O[1] = 0;
		O[2] = 0;
		O[3] = 0;

		O[4] = 0;
		O[5] = 2 / (t - b);
		O[6] = 0;
		O[7] = 0;

		O[8] = 0;
		O[9] = 0;
		O[10] = -2 / (f - n);
		O[11] = 0;

		O[12] = -(r + l) / (r - l);
		O[13] = -(t + b) / (t - b);
		O[14] = -(f + n) / (f - n);
		O[15] = 1;

		return O;
	}

	/**
	 * Color phong setting depends on the object name
	 * 
	 * @param program		The program ID
	 * @param gl2			The OpenGL function
	 * @param objectName	The object name
	 */
	public void setUpPhong(int program, GL2 gl2, String objectName) {

		// If the object is not the SUN,
		// there are diffuse and specular color with a light
		// which locate at the SUN position
		if (objectName != "sun") {

			float lightPosition[] = { -3.5f, 0.5f, 0.0f, 1.0f };
			float lightColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
			float diffuseColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
			float specularColor[] = { 1.0f, 1.0f, 0.9f, 1.0f };
			float specularExponent = 35.0f;
			float ifsun = 0.0f;

			int is = gl2.glGetUniformLocation(program, "ifsun");
			gl2.glUniform1f(is, ifsun);

			// Diffuse component.
			int lp = gl2.glGetUniformLocation(program, "lightPosition");
			int lc = gl2.glGetUniformLocation(program, "lightColor");
			int dc = gl2.glGetUniformLocation(program, "diffuseColor");

			gl2.glUniform4fv(lp, 1, lightPosition, 0);
			gl2.glUniform4fv(lc, 1, lightColor, 0);
			gl2.glUniform4fv(dc, 1, diffuseColor, 0);

			// Specular component
			int sc = gl2.glGetUniformLocation(program, "specularColor");
			int se = gl2.glGetUniformLocation(program, "specularExponent");

			gl2.glUniform4fv(sc, 1, specularColor, 0);
			gl2.glUniform1f(se, specularExponent);
		} 
		
		// If the object is the SUN,
		// there is only the ambient color 
		else {
			float ifsun = 1.0f;
			int is = gl2.glGetUniformLocation(program, "ifsun");
			gl2.glUniform1f(is, ifsun);
		}
	}
}
