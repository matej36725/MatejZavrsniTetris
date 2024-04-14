/**
 * 
 */
package hr.ets.matej.tetris;

import java.awt.Color;

/**
 * 
 */
public class GamePlay {
	/**
	 * Definicija tetris likova.
	 */
	static Tetromino[] tetromino = new Tetromino[7];
	
	/**
	 * definicija boja koje se koriste za pojedini lik ili njegove dijelove
	 */
	static TetrominoColor[] tetrominoColor = new TetrominoColor[7];
	
	/**
	 * Statička inicijalizacija
	 */
	static {
		// definicija boja (prema indeksima likova)
		tetrominoColor[0] = new TetrominoColor(new Color(255, 5, 7), new Color(192, 0, 0), new Color(160, 2, 0));
		tetrominoColor[1] = new TetrominoColor(new Color(255, 8, 255), new Color(191, 0, 192), new Color(152, 2, 164));
		tetrominoColor[2] = new TetrominoColor(new Color(255, 255, 15), new Color(191, 190, 1), new Color(162, 163, 0));
		tetrominoColor[3] = new TetrominoColor(new Color(164, 164, 164), new Color(115, 115, 115), new Color(100, 100, 100));
		tetrominoColor[4] = new TetrominoColor(new Color(16, 255, 255), new Color(2, 192, 192), new Color(4, 133, 139));
		tetrominoColor[5] = new TetrominoColor(new Color(5, 2, 255), new Color(16, 6, 175), new Color(14, 4, 138));
		tetrominoColor[6] = new TetrominoColor(new Color(10, 255, 4), new Color(0, 191, 0), new Color(3, 131, 8));
		
		// definicja likova
		tetromino[0] = new Tetromino(new Point(1, 0, 0), new Point(1, 1, 0), new Point(1, 2, 0), new Point(1, 3, 0)); // I
		tetromino[1] = new Tetromino(new Point(1, 0, 1), new Point(1, 1, 1), new Point(1, 2, 1), new Point(2, 0, 1)); // J
		tetromino[2] = new Tetromino(new Point(1, 0, 2), new Point(1, 1, 2), new Point(1, 2, 2), new Point(2, 2, 2)); // L
		tetromino[3] = new Tetromino(new Point(1, 2, 3), new Point(2, 1, 3), new Point(2, 2, 3), new Point(2, 3, 3)); // T
		tetromino[4] = new Tetromino(new Point(1, 1, 4), new Point(1, 2, 4), new Point(2, 2, 4), new Point(2, 1, 4)); // O
		tetromino[5] = new Tetromino(new Point(1, 1, 5), new Point(1, 2, 5), new Point(2, 2, 5), new Point(2, 3, 5)); // S
		tetromino[6] = new Tetromino(new Point(1, 3, 6), new Point(1, 2, 6), new Point(2, 2, 6), new Point(2, 1, 6)); // Z
	}
	
	private long score;
	private int nivo;
	private int linije;
	private long highScore;
	private Tetromino next;
	private Tetromino t;
	private int tx, ty;
		
	public GamePlay() {
		newGame();
		highScore = 0;
	}
	
	public void newGame() {
		score = 0;
		nivo = 1;
		linije = 0;
		t = odaberiRandomTetromino();
		next = odaberiRandomTetromino();
		tx = 3;
		ty = 0;
	}
	
	public Tetromino odaberiRandomTetromino() {
		int i = (int)(Math.random() * 7.0);
		return tetromino[i];
	}
	
	public void pomakniLijevo() {
		tx = tx - 1;
	}
	
	public void pomakniDesno() {
		tx = tx + 1;
	}
	
	public void pomakniDole() {
		ty = ty + 1;
	}
	
	public void rotiraj(boolean smjer) {
		t.rotiraj(smjer);
	}
	
	/**
	 * @return the score
	 */
	public long getScore() {
		return score;
	}
	/**
	 * @return the nivo
	 */
	public int getNivo() {
		return nivo;
	}
	/**
	 * @return the linije
	 */
	public int getLinije() {
		return linije;
	}
	/**
	 * @return the highScore
	 */
	public long getHighScore() {
		return highScore;
	}
	/**
	 * @return the next
	 */
	public Tetromino getNext() {
		return next;
	}
	/**
	 * @return the t
	 */
	public Tetromino getT() {
		return t;
	}
	/**
	 * @return the tx
	 */
	public int getTx() {
		return tx;
	}
	/**
	 * @return the ty
	 */
	public int getTy() {
		return ty;
	}
}
