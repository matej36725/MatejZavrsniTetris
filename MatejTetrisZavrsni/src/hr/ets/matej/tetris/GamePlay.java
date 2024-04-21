/**
 * 
 */
package hr.ets.matej.tetris;

import java.awt.Color;

import java.util.Timer;
import java.util.TimerTask;
/**
 * 
 */
public class GamePlay extends TimerTask {
	/**
	 * index boje praznog polja
	 */
	public static final int INDEX_PRAZNO_POLJE = 7;
	
	/**
	 * Definicija tetris likova.
	 */
	private static Tetromino[] tetromino = new Tetromino[7];

	/**
	 * definicija boja koje se koriste za pojedini lik ili njegove dijelove
	 */
	private static TetrominoColor[] tetrominoColor = new TetrominoColor[8];
	
	/**
	 * Igrače polje
	 */
	private static int[][] polje = new int[10][20];
	
	/**
	 * Definira konstantu za pojedini novi - koliko se linija za 1 frame figura pomakne (vrijenosti su bazirane na 60 fameova u sekundi)
	 */
	private static float[] gravity = new float[] {0.01667f, 0.021017f, 0.026977f, 0.035256f, 0.04693f, 0.06361f, 0.0879f, 0.1236f, 0.1775f, 0.2598f, 0.388f, 0.59f, 0.92f, 1.46f, 2.36f};
	

	/**
	 * Statička inicijalizacija
	 */
	static {
		// definicija boja (prema indeksima likova)
		tetrominoColor[0] = new TetrominoColor(new Color(255, 5, 7), new Color(192, 0, 0), new Color(160, 2, 0));
		tetrominoColor[1] = new TetrominoColor(new Color(255, 8, 255), new Color(191, 0, 192), new Color(152, 2, 164));
		tetrominoColor[2] = new TetrominoColor(new Color(255, 255, 15), new Color(191, 190, 1), new Color(162, 163, 0));
		tetrominoColor[3] = new TetrominoColor(new Color(164, 164, 164), new Color(115, 115, 115),new Color(100, 100, 100));
		tetrominoColor[4] = new TetrominoColor(new Color(16, 255, 255), new Color(2, 192, 192), new Color(4, 133, 139));
		tetrominoColor[5] = new TetrominoColor(new Color(5, 2, 255), new Color(16, 6, 175), new Color(14, 4, 138));
		tetrominoColor[6] = new TetrominoColor(new Color(10, 255, 4), new Color(0, 191, 0), new Color(3, 131, 8));
		//boja praznog polja
		tetrominoColor[INDEX_PRAZNO_POLJE] = new TetrominoColor(new Color(40, 40, 40), new Color(30, 30, 30), new Color(10, 10, 10));

		// definicja likova
		tetromino[0] = new Tetromino(new Point(1, 0, 0), new Point(1, 1, 0), new Point(1, 2, 0), new Point(1, 3, 0)); // I
		tetromino[1] = new Tetromino(new Point(1, 0, 1), new Point(1, 1, 1), new Point(1, 2, 1), new Point(2, 0, 1)); // J
		tetromino[2] = new Tetromino(new Point(1, 0, 2), new Point(1, 1, 2), new Point(1, 2, 2), new Point(2, 2, 2)); // L
		tetromino[3] = new Tetromino(new Point(1, 1, 3), new Point(2, 0, 3), new Point(2, 1, 3), new Point(2, 2, 3)); // T
		tetromino[4] = new Tetromino(new Point(1, 1, 4), new Point(1, 2, 4), new Point(2, 2, 4), new Point(2, 1, 4)); // O
		tetromino[5] = new Tetromino(new Point(1, 1, 5), new Point(1, 2, 5), new Point(2, 2, 5), new Point(2, 3, 5)); // S
		tetromino[6] = new Tetromino(new Point(1, 3, 6), new Point(1, 2, 6), new Point(2, 2, 6), new Point(2, 1, 6)); // Z
	}

	private TetrisPanel tetrisPanel;
	private long score;
	private int nivo;
	private int linije;
	private long highScore;
	private Tetromino next;
	private Tetromino t;
	private int tx, ty;
	private Timer timer = new Timer();
	private float pomakTetromino;

	public GamePlay(TetrisPanel tetrisPanel) {
		newGame();
		highScore = 0;
		this.tetrisPanel = tetrisPanel;
		
		timer.scheduleAtFixedRate(this, 0, 50);
	}

	public void newGame() {
		score = 0L;
		nivo = 0;
		linije = 0;
		
		// postavi vrijednosti polja na prazno
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 20; y++) {
				polje[x][y] = INDEX_PRAZNO_POLJE;
			}
		}
		
		next = odaberiRandomTetromino();
		sljedeciTetromino();
	}
	
	public void sljedeciTetromino() {
		pomakTetromino = 0f;
		t = next;
		next = odaberiRandomTetromino();
		if (provjeriPozicijuTetromina(3, -1, t)) {
			ty = -1;
		} else if (provjeriPozicijuTetromina(3, 0, t)){
			ty = 0;
		} else {
			gameEnd();
		}
		tx = 3;
	}

	public Tetromino odaberiRandomTetromino() {
		int i = (int) (Math.random() * 7.0);
		return tetromino[i].kloniraj();
	}

	public void pomakniLijevo() {
		if (provjeriPozicijuTetromina(tx - 1, ty, t)) {
			tx = tx - 1;
		}
	}

	public void pomakniDesno() {
		if (provjeriPozicijuTetromina(tx + 1, ty, t)) {
			tx = tx + 1;
		}
	}

	public boolean pomakniDole() {
		if (provjeriPozicijuTetromina(tx, ty + 1, t)) {
			ty = ty + 1;
			return true;
		}
		return false;
	}

	public void rotiraj(boolean smjer) {
		t.rotiraj(smjer);
		if (!provjeriPozicijuTetromina(tx, ty, t)) {
			t.rotiraj(!smjer);
		}
	}
	
	public void spusti() {
		while (pomakniDole()) {
		}
		
		// kopiraj figuru u polje
		kopirajTetrominoUPolje(tx, ty, t);
		ukloniPuneLinije();
		sljedeciTetromino();
	}

	public boolean provjeriPozicijuTetromina(int tx, int ty, Tetromino t) {
		Point[] points = t.getPoints();
		for (int i = 0; i < 4; i++) {
			if ((tx + points[i].getX() > 9) || tx + points[i].getX() < 0) {
				return false;
			}
			if ((ty + points[i].getY() > 19) || ty + points[i].getY() < 0) {
				return false;
			}
			if (polje[tx + points[i].getX()][ty + points[i].getY()] != INDEX_PRAZNO_POLJE) {
				return false;
			}
		}
		return true;
	}
	
	public void kopirajTetrominoUPolje(int tx, int ty, Tetromino t) {
		Point[] points = t.getPoints();
		for (int i = 0; i < 4; i++) {
			polje[tx + points[i].getX()][ty + points[i].getY()] = points[i].getColorIndex();
		}
	}
	
	 public void ukloniPuneLinije() {
		 int brLn = 0;
		 for (int y = 19; y > 0; y--) {
			 boolean puno = true;
			 //provjera dali je linija puna
			 for (int x = 0; x <= 9; x++) {
				 if (polje[x][y] == INDEX_PRAZNO_POLJE) {
					 puno = false;
					 break;
				 }
			 }
			 
			 if (puno) {
				 brLn += 1;
				 //spusti sve linije iznad y za 1
				 for (int y1 = y - 1; y1 >= 0; y1--) {
					 for (int x = 0; x <= 9; x++) {
						 polje[x][y1 + 1] = polje[x][y1];
					 }
				 }
			 }
		 }
		 
		 switch (brLn) {
			 case 1:
				 score += 40 * (nivo + 1);
				 break;
			 case 2:
				 score += 100 * (nivo + 1);
				 break;
			 case 3:
				 score += 300 * (nivo + 1);
				 break;
			 case 4:
				 score += 1200 * (nivo + 1);
				 break;
		 }
		 
		 linije += brLn;
				 
		 //for (int i = 0; i <= brLn; i++)
		 //score = 40 * (nivo + 1) 	100 * (nivo + 1) 	300 * (nivo + 1) 	1200 * (nivo + 1);
	 }
	 
	 public void gameEnd() {
		 // provjera da li je moguće postaviti novi lik na vrh ploče
		 System.exit(0);
	 }
	 
	 /**
	  * Metoda koja se zove svakih 50ms preko timera
	  */
	 public void run() {
		 pomakTetromino += gravity[nivo] * 3f;
		 
		 while (pomakTetromino > 1) {
			 if (!pomakniDole()) {
				 spusti();
				 break;         
			 }
			 pomakTetromino -= 1;
		 }
		 
		 tetrisPanel.repaint();
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
	
	/**
	 * Vraća definiciju tetromino boje za dani indeks.
	 * 
	 * @param indeks
	 * @return
	 */
	public static TetrominoColor getTetrominoColor(int indeks) {
		return tetrominoColor[indeks];
	}

	/**
	 * Vraća boju za zadane koordinate.
	 * 
	 * @param index
	 * @return
	 */
	public static TetrominoColor getTetrominoColorXY(int x, int y) {
		return tetrominoColor[polje[x][y]];
	}
	
}
