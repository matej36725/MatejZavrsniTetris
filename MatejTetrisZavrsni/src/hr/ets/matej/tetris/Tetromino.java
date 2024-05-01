package hr.ets.matej.tetris;

/**
 * Klasa koja opisuje svaki pojedini tetris lik. Ukupan broj likova je 7.
 * Boja lika zadana je kao boja svake točke.
 */
public class Tetromino {

	//polje za čuvanje 4 točke tetromina
	private Point[] points = new Point[4];

	/**
	 * Konstruktor za spremanje zadanih parametara u polje.
	 * 
	 * @param point1 dio tetromina
	 * @param point2 dio tetromina
	 * @param point3 dio tetromina
	 * @param point4 dio tetromina
	 */
	public Tetromino(Point point1, Point point2, Point point3, Point point4) {
		points[0] = point1;
		points[1] = point2;
		points[2] = point3;
		points[3] = point4;
	}
	
	/**
	 * Stvara klon tetromina na podacima trenutnog tetromina.
	 * (potrebno da originalni tetromnio iz polja ostane normalno postavljen)
	 * 
	 * @return klonirni tetromino
	 */
	public Tetromino kloniraj() {
		return new Tetromino(points[0].kloniraj(), points[1].kloniraj(), points[2].kloniraj(), points[3].kloniraj());
	}

	/**
	 * Nacrtaj tetromino.
	 * 
	 * @param panel površina za crtanje
	 * @param x kordinata
	 * @param y kordinata
	 */
	public void nacrtaj(TetrisPanel panel, int x, int y) {
		for (int i = 0; i < 4; i++) {
			TetrominoColor bojaKvadratica = GamePlay.getTetrominoColor(points[i].getColorIndex());
			panel.nacrtajKvadratic(x + points[i].getX(), y + points[i].getY(), bojaKvadratica);
		}
	}
	
	/**
	 * Rotira cijeli lik tako da rotira svaku tocku posebnu
	 * 
	 * @param smjer true - u smjeru kazaljke na satu
	 */
	public void rotiraj(boolean smjer) {
		for (int i = 0; i < 4; i++) {
			points[i].rotiraj(smjer);
		}
	}
	
	/**
	 * Vraća polje koje sačinjavaju točke tetrominoa 
	 * 
	 * @return points polje
	 */
	public Point[] getPoints() {
		return points;
	}

	/**
	 * Koristi se za debugging, ispisuje tpčke koje sačinavaju tetromino.
	 */
	@Override
	public String toString() {
		return super.toString() + "[" + points[0].toString() + ", " + points[1].toString() + ", " + points[2].toString() + ", " + points[3].toString() + "]";
	}

}
