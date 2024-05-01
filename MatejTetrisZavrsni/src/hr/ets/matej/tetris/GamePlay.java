/**
 * 
 */
package hr.ets.matej.tetris;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

/**
 * Klasa koja definira logiku igre
 */
public class GamePlay extends TimerTask {
	/**
	 * index boje praznog polja
	 */
	public static final int INDEX_PRAZNO_POLJE = 7;
	
	/**
	 * Definira broj linija za prelazak nivoa.
	 */
	public static final int BROJ_LINIJA_PO_NIVOU = 10;

	/**
	 * Definicija polja tetris likova.
	 */
	private static Tetromino[] tetromino = new Tetromino[7];

	/**
	 * Definicija boja koje se koriste za pojedini lik ili njegove dijelove.
	 */
	private static TetrominoColor[] tetrominoColor = new TetrominoColor[8];

	/**
	 * Igraće polje(čuva indeks boje).
	 */
	private int[][] polje = new int[10][20];

	/**
	 * Definira konstantu za pojedini novi - koliko se linija za 1 frame figura
	 * pomakne (vrijenosti su bazirane na 60 fameova u sekundi)
	 */
	private static float[] gravity = new float[] { 0.01667f, 0.021017f, 0.026977f, 0.035256f, 0.04693f, 0.06361f,
			0.0879f, 0.1236f, 0.1775f, 0.2598f, 0.388f, 0.59f, 0.92f, 1.46f, 2.36f };

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
		
		// boja praznog polja
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

	/**
	 * Glavni panel za iscrtavanje likova.
	 */
	private TetrisPanel tetrisPanel;
	
	/**
	 * Varijabla za pohranu bodova.
	 */
	private long score;
	
	/**
	 * Varijabla za određivanje nivoa.
	 */
	private int nivo;
	
	/**
	 * Varijabla za broj spuštenih linija.
	 */
	private int linije;
	
	/**
	 * Sljedeći tetromino.
	 */
	private Tetromino next;
	
	/**
	 * Trenutni tetromino.
	 */
	private Tetromino t;
	
	/**
	 * Koordinate trenutnog tetromina.
	 */
	private int tx, ty;
	
	/**
	 * Timer klasa koja se koristi za spuštanje lika pomoću "game tick".
	 */
	private Timer timer = new Timer();
	
	/**
	 * Akumulirani pomak tetromino (ažurira se svaki "game tick")
	 */
	private float pomakTetromino;
	
	/**
	 * Početno vrijeme (msec).
	 */
	private long startTime;
	
	/**
	 * Ako je false ništa se ne događa u "game tick"
	 */
	private boolean igraPokrenuta = false;
	
	/**
	 * Broj i određuje prelaz na sljedeći nivo po broju linija.
	 */
	private float i = 1;

	/**
	 * Konstruktor koji pokreće novu igru, pohrani panel, i pokrene timer
	 * 
	 * @param tetrisPanel za crtanje igre
	 */
	public GamePlay(TetrisPanel tetrisPanel) {
		newGame();
		this.tetrisPanel = tetrisPanel;
		
		//pokreće "game tick" timer svakih 50 msec
		timer.scheduleAtFixedRate(this, 0, 50);
	}

	/**
	 * Metoda postavlja sve na nulu za novu igru, krece brojat timer
	 */
	public void newGame() {
		score = 0L;
		nivo = 0;
		linije = 0;
		startTime = System.currentTimeMillis();
		igraPokrenuta = true;

		// postavi vrijednosti polja na prazno
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				polje[x][y] = INDEX_PRAZNO_POLJE;
			}
		}

		next = odaberiRandomTetromino();
		sljedeciTetromino();

	}

	
	/** 
	 * Postavlja slijedeći tetromino na igraću ploču tako da uzme tetromino 
	 * iz next polja, generira novi next i pokuša postaviti trenutni tetromino na vrh ploče.
	 * Ako postavljanje nije moguće, završava igru.
	 * 
	 */
	public void sljedeciTetromino() {
		// reset akumuliranog pomaka
		pomakTetromino = 0f;
		
		// zamjena trenutni i slijdeći
		t = next;
		
		// generiraj novi slijedeći
		next = odaberiRandomTetromino();
		
		// pokušaj smjestiti novi trenutni tetromino na vrh ploče
		//  probaj na y pozicji -1, a ako nije moguće, probaj na poziciji 0
		if (provjeriPozicijuTetromina(3, -1, t)) {
			ty = -1;
		} else if (provjeriPozicijuTetromina(3, 0, t)) {
			ty = 0;
		} else {
			// nije bilo moguće smještanje novog tetromina - završi igru
			gameEnd();
		}
		
		// postavi x poziciju na centar igraće ploče
		tx = 3;
	}

	/**
	 * Generira random tetromino.
	 * 
	 * @return klon odabranog tetromina
	 */
	public Tetromino odaberiRandomTetromino() {
		int i = (int) (Math.random() * 7.0);
		return tetromino[i].kloniraj();
	}

	/**
	 * Metoda provjerava dali je moguć pomak u lijevo te ako je smanjuje vrijednost x kordinate.
	 */
	public void pomakniLijevo() {
		if (provjeriPozicijuTetromina(tx - 1, ty, t)) {
			tx = tx - 1;
		}
	}

	/**
	 * Metoda provjerava dali je moguć pomak u desno te ako je povećava vrijednost x kordinate.
	 */
	public void pomakniDesno() {
		if (provjeriPozicijuTetromina(tx + 1, ty, t)) {
			tx = tx + 1;
		}
	}

	/**
	 * Metoda provjerava dali je moguć pomak u dole te ako je smanjava vrijednost y kordinate(vraća true ako je pomak obavljen).
	 * 
	 * @return true/false ako je pomak bio moguć/nemoguć
	 */
	public boolean pomakniDole() {
		if (provjeriPozicijuTetromina(tx, ty + 1, t)) {
			ty = ty + 1;
			return true;
		}
		return false;
	}

	/**
	 * Metoda za rotiranje tetromina i provjera dali je rotacija moguća(ako nije vraća nazad)
	 * 
	 * @param smjer
	 */
	public void rotiraj(boolean smjer) {
		// smjer true - u smjeru kazaljke
		// smjer false - suprotno od kazaljke
		
		// pokusaj rotirati u jednom smjer
		t.rotiraj(smjer);
		// provjeri poziciju
		if (!provjeriPozicijuTetromina(tx, ty, t)) {
			// vratu natrag - nije uspjela rotacija
			t.rotiraj(!smjer);
		}
	}

	/**
	 * Metoda koja instantno spusti lika na najnižu moguću lokaciju, kopira ga u polje, provjeri dali treba ukloniti linije,
	 *  kreira sljedeći tetromino i doda 1 bod
	 */
	public void spusti() {
		// radi pomak dolje dok god je moguć
		while (pomakniDole());

		// kopiraj figuru u polje
		kopirajTetrominoUPolje(tx, ty, t);
		
		// "sruši" pune linije
		ukloniPuneLinije();
		
		// generiraj i postavi slijedeći tetromino
		sljedeciTetromino();
		
		// dodaj bodove
		addScore(1);
	}

	/**
	 * Provjerava da li svaka točka tetromina zadovoljava uvjete da ne izlazi iz polja i 
	 * dali je lik na trenutnoj poziciju u koliziji sa likovima na ploči.
	 * 
	 * @param tx x kordinata lika
	 * @param ty y kordinata lika
	 * @param t tetromino
	 * @return true/false ako zadovoljava/nezadovoljava uvjete.
	 */
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

	/**
	 * Kopira točke (indeks boje) tetromina u ploču.
	 * 
	 * @param tx kordinata tetromina
	 * @param ty kordinata tetromina
	 * @param t tetromino
	 */
	public void kopirajTetrominoUPolje(int tx, int ty, Tetromino t) {
		Point[] points = t.getPoints();
		for (int i = 0; i < 4; i++) {
			polje[tx + points[i].getX()][ty + points[i].getY()] = points[i].getColorIndex();
		}
	}
	
	/**
	 * Metoda koja pribraja zadane bodove ukupnom rezultatu.
	 * 
	 * @param score količina bodova za pribrajanje
	 */
	public void addScore(int score) {
		this.score += score;
	}

	/**
	 * Skenira cijelu ploču, traži pune linije, miče ih, "ruši" druge linije prema dolje
	 * te povećava br linija za broj srušenih linija.
	 * Zbraja bodove i provjera dali treba preći na sljedeći nivo.
	 */
	public void ukloniPuneLinije() {
		int brLn = 0;
		
		//skeniranje i rušenje linija
		for (int y = 19; y > 0; y--) {
			boolean puno = true;
			// provjera dali je linija puna
			for (int x = 0; x <= 9; x++) {
				if (polje[x][y] == INDEX_PRAZNO_POLJE) {
					puno = false;
					break;
				}
			}

			if (puno) {
				brLn += 1;
				// spusti sve linije iznad y za 1
				for (int y1 = y - 1; y1 >= 0; y1--) {
					for (int x = 0; x <= 9; x++) {
						polje[x][y1 + 1] = polje[x][y1];
					}
				}
			}
		}

		//pribrajanje bodova ovisno o broju srušenih linija
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

		//povećanje broja srušenih linija
		linije += brLn;
		
		// povećaj nivo ako treba
		if (linije / i >= BROJ_LINIJA_PO_NIVOU) {
			nivo += 1;
			i++;
		}
	}

	/**
	 * 
	 */
	public void gameEnd() {
		//zaustavi igru
		igraPokrenuta = false;

		Bodovi bodovi = new Bodovi(tetrisPanel.getTetris());

		//podiže dijalog za upis imena igrača
		String username = JOptionPane.showInputDialog(tetrisPanel, "Unesi korisničko ime:", "Unesi podatke", JOptionPane.PLAIN_MESSAGE);

		//računa koliko je trajala igra(od trenutnog vremena oduzme početno vrijeme u msec)
		Duration d = Duration.ofMillis(System.currentTimeMillis() - startTime);
		
		//vraća formatirano trajanje iz msec u obliku HH:MM:SS
		String trajanje = String.format("%02d:%02d:%02d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart());

		//ako je unio ime igrača dodaj bodove, trajanje i ime u listu
		if (username != null && !username.isBlank()) {
			bodovi.dodajBodoveNaListu(username, score, trajanje);
		}

		// dohvaća bodove iz baze i popuni tablicu s prikazom bodova
		bodovi.prikaziBodove();
		
		// centriraj u odnosu na glavni prozor
		bodovi.setLocationRelativeTo(tetrisPanel);
		
		//detekcija događaaja zatvaranja prozora(pokreće novu igru kad je zatvoren prozor)
		bodovi.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				newGame();
			}
		});
		
		//prikaže prozor s bodovima
		bodovi.setVisible(true);
		
		
	}

	/**
	 * Metoda koja se zove svakih 50ms preko timera.
	 */
	public void run() {
		if (igraPokrenuta) {
			//akumuliranje pomaka tetromina
			// pomak je definiran kao gravity konstnata koja definira pomak u jednom frame-u (konstante kopirane sa stranice o tetrisu)
			// kako 1s sadrži 60 frameova, jedan pomak je svakih MSF = (1/60)*1000ms
			// game tick je 50ms, te je vrijednost potrebno pomnožiti sa 3 tj. sa 50ms/MSF.
			pomakTetromino += gravity[nivo] * 3f;
	
			//pomiče teromino za 1 dok god je akumulirani pomak veći od 1
			while (pomakTetromino > 1) {
				if (!pomakniDole()) { // pokušaj spustiti za 1
					// spuštanje nije uspjelo - pozovi logiku igreka koja se zove ako je korisnik pritisnuo razmak
					spusti();
					break;
				}
				pomakTetromino -= 1;
			}
	
			//iscrtaj novu situaciju na ploči
			tetrisPanel.repaint();
		}
	}

	/**
	 * Vraća trenutne bodove.
	 * 
	 * @return the score
	 */
	public long getScore() {
		return score;
	}

	/**
	 * Vraća trenutni nivo (od 0).
	 * 
	 * @return the nivo
	 */
	public int getNivo() {
		return nivo;
	}

	/**
	 * Vraća trenutni broj srušenih linija.
	 * 
	 * @return the linije
	 */
	public int getLinije() {
		return linije;
	}

	/**
	 * Vraća sljedeći tetromino.
	 * 
	 * @return the next
	 */
	public Tetromino getNext() {
		return next;
	}

	/**
	 * Vraća trenutni tetromino.
	 * 
	 * @return the t
	 */
	public Tetromino getT() {
		return t;
	}

	/**
	 * Vraća x kordinatu trenutnog tetromina.
	 * 
	 * @return the tx
	 */
	public int getTx() {
		return tx;
	}

	/**
	 * Vraća y kordinatu trenutnog tetromina.
	 * 
	 * @return the ty
	 */
	public int getTy() {
		return ty;
	}

	/**
	 * Vraća definiciju tetromino boje za dani indeks.
	 * 
	 * @param indeks boje
	 * @return TetrominoColor za zadani indeks.
	 */
	public static TetrominoColor getTetrominoColor(int indeks) {
		return tetrominoColor[indeks];
	}

	/**
	 * Vraća boju igračeg polja za zadane koordinate.
	 * 
	 * @param x x koordinata polja
	 * @param y y koordinata polja
	 * @return TetrominoColor traženog polja
	 */
	public TetrominoColor getTetrominoColorXY(int x, int y) {
		return tetrominoColor[polje[x][y]];
	}

}
