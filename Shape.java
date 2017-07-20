/**
 * Shape.java is the program of making shape for the final program option.
 * It do the tessellation of a sphere 
 * 
 * @author Zhenkuang He
 * @version 14-May-2013
 * 
 */
public class Shape extends simpleShape {
	
	/**
	 * constructor
	 */
	public Shape() {
	}

	/**
	 * makeSunShape
	 * 
	 * Create a Sun object
	 */
	public void makeShape() {
		float radius = 0.5f;
		float stacks = 50f;
		float slides = 50f;

		float unit = radius;
		double uya = Math.PI / stacks;

		double u = 1 / slides;
		double v = 1 / stacks;

		float ua = 360f / slides;
		float a1, a2, x1, x11, x2, x22, y, yy, z1, z11, z2, z22, ry, ryy, u1, u2, v1, v2;

		for (int i = 0; i < slides; i++) {
			a1 = (float) Math.toRadians(ua * i);
			a2 = (float) Math.toRadians(ua * (i + 1));

			for (int j = 0; j < stacks; j++) {

				y = (float) (Math.sin(0.5 * Math.PI - uya * j) * radius);
				yy = (float) (Math.sin(0.5 * Math.PI - uya * (j + 1)) * radius);

				ry = (float) (Math.sqrt((unit * unit) - (y * y)));
				ryy = (float) (Math.sqrt((unit * unit) - (yy * yy)));

				x1 = (float) (ry * Math.cos(a1));
				x2 = (float) (ry * Math.cos(a2));
				z1 = (float) (ry * Math.sin(a1));
				z2 = (float) (ry * Math.sin(a2));

				x11 = (float) (ryy * Math.cos(a1));
				x22 = (float) (ryy * Math.cos(a2));
				z11 = (float) (ryy * Math.sin(a1));
				z22 = (float) (ryy * Math.sin(a2));

				u1 = (float) (i * u);
				u2 = (float) ((i + 1) * u);
				v1 = (float) (j * v);
				v2 = (float) ((j + 1) * v);

				// Along side up ->
				super.addTriangle(x2, y, z2, u2, v1, x11, yy, z11, u1, v2, x1,
						y, z1, u1, v1);

				// Along side down ->
				super.addTriangle(x2, y, z2, u2, v1, x22, yy, z22, u2, v2, x11,
						yy, z11, u1, v2);
			}
		}
	}
}
