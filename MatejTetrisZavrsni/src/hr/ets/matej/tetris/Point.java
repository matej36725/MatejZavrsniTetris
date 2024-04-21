package hr.ets.matej.tetris;

public class Point {
	private int x, y;
	private int colorIndex;

	public Point(int x, int y, int colorIndex) {
		this.x = x;
		this.y = y;
		this.colorIndex = colorIndex;
	}
	
	
	public Point kloniraj() {
		return new Point(x, y, colorIndex);
	}
	
	/**
	 * rotira kordinate točke u zadanom smjeru pod predpostavkom polja kordinata 4x4
	 * 
	 * @param smjer true označava rotacaiju u smjeru kazaljke na satu
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

	public int getColorIndex() {
		return colorIndex;
	}
	
	public int getX() {
		return x;
	}
	 
	public int getY() {
		return y;
	}


	@Override
	public String toString() {
		return super.toString() + "[x=" + x + ", y=" + y + "]";
	}
}
