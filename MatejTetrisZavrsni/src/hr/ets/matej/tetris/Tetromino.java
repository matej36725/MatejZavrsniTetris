/**
 * 
 */
package hr.ets.matej.tetris;

/**
 * Klasa koja opisuje svaki pojedini tetris lik. Ukupan broj likova je 7.
 * Boja lika zadana je kao boja svake točke.
 */
public class Tetromino {

	private Point[] points = new Point[4];

	public Tetromino(Point point1, Point point2, Point point3, Point point4) {
		points[0] = point1;
		points[1] = point2;
		points[2] = point3;
		points[3] = point4;
	}
	
	public Tetromino kloniraj() {
		return new Tetromino(points[0].kloniraj(), points[1].kloniraj(), points[2].kloniraj(), points[3].kloniraj());
	}

	public void nacrtaj(TetrisPanel mainFrame, int x, int y) {
		for (int i = 0; i < 4; i++) {
			TetrominoColor bojaKvadratica = GamePlay.getTetrominoColor(points[i].getColorIndex());
			mainFrame.nacrtajKvadratic(x + points[i].getX(), y + points[i].getY(), bojaKvadratica);
		}
	}
	
	public void rotiraj(boolean smjer) {
		for (int i = 0; i < 4; i++) {
			points[i].rotiraj(smjer);
		}
	}
	
	public Point[] getPoints() {
		return points;
	}

	@Override
	public String toString() {
		return super.toString() + "[" + points[0].toString() + ", " + points[1].toString() + ", " + points[2].toString() + ", " + points[3].toString() + "]";
	}

}
