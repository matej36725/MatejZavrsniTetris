package hr.ets.matej.tetris;

/**
 * Klasa definira tocku u prostoru igre.
 * Točka je definirana x, y kordinatama i indeksom boje
 * 
 */
public class Point {
	// kordinate točke
	private int x, y;
	
	// indeks boje
	private int colorIndex;

	/**
	 * Konstruira point objekt s zadanim kordinatama i indeskom boje.
	 * 
	 * @param x kordinata
	 * @param y kordinata
	 * @param colorIndex indeks boje(0-7)
	 */
	public Point(int x, int y, int colorIndex) {
		this.x = x;
		this.y = y;
		this.colorIndex = colorIndex;
	}
	
	/**
	 * Metoda kreira klon jedne trenutne tocke lika.
	 * (koristi se prilikom kloniranja tetromina)
	 * 
	 * @return klonirani point objekt
	 */
	public Point kloniraj() {
		return new Point(x, y, colorIndex);
	}
	
	/**
	 * Rotira kordinate točke u zadanom smjeru pod predpostavkom polja kordinata 4x4.
	 * 
	 * @param smjer true označava rotaciju u smjeru kazaljke na satu
	 */
	public void rotiraj(boolean smjer) {
		if (smjer) {
			int x1 = x;
			x = 3 - y;
			y = x1;
		} else {
			int x1 = x;
			x = y;
			y = 3 - x1;
		}
	}

	/**
	 * Vraća indeks boje.
	 * 
	 * @return indeks boje.
	 */
	public int getColorIndex() {
		return colorIndex;
	}
	
	/**
	 * Vraća X koordinatu.
	 * 
	 * @return x koordinata.
	 */
	public int getX() {
		return x;
	}
	 
	/**
	 * Vraća Y koordinatu.
	 * 
	 * @return y koodrinata.
	 */
	public int getY() {
		return y;
	}


	/**
	 * Koristi se prilikom debugiranaj - ispisuje koordinate objekta i index boje.
	 */
	@Override
	public String toString() {
		return super.toString() + "[x=" + x + ", y=" + y + ", indexBoje=" + colorIndex +  "]";
	}
}
