/**
 * 
 */
package hr.ets.matej.tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Klasa implementira JPanel po kojem se crta tetris polje i tekstovi.
 */
@SuppressWarnings("serial")
public class TetrisPanel extends JPanel implements KeyListener {

	/**
	 * Definira velićinu podjele terena(jedan kvadratič)
	 */
	public static final int GRID_SIZE = 20;
	
	/**
	 * Pomak igrače plohe - X.
	 */
	public static final int GAMEFIELD_POMAK_X = 2;
	
	/**
	 * Pomak igrače plohe - Y.
	 */
	public static final int GAMEFIELD_POMAK_Y = 2;
	
	
	/**
	 * Glavni prozor
	 */
	private JFrame tetris;
	
	/**
	 * Koristi se za crtanje(linije i kvadrata)
	 */
	private Graphics g = null;
	
	/**
	 * Poveznica na objekt koji implementira logiku igre.
	 */
	private GamePlay gamePlay;
	
	/**
	 * Font za ispis brojeva.
	 */
	private Font fontBrojevi = new Font(Font.MONOSPACED, Font.PLAIN, 20);

	/**
	 * Font za ispis tekstova
	 */
	private Font fontTekst = new Font("default", Font.BOLD, 16);
	
	/**
	 * Font za prikaz bodova
	 */
	private Font fontBodovi = new Font(Font.MONOSPACED, Font.PLAIN, 16);
	
	/**
	 * Konstruktor koji stvara novu GamePlay klasu, pohranjuje referencu na glavni prozor i registrira
	 * klasu koja će obrađivati pritiske tipaka.
	 * 
	 * @param tetris Glavni tetris prozor
	 * @throws HeadlessException
	 */
	public TetrisPanel(JFrame tetris) throws HeadlessException {
		// stvara novu GamePlay klasu koja sadrži logiku igre
		gamePlay = new GamePlay(this);
		
		// pohranjuje referencu na glavni prozor
		this.tetris = tetris;
		
		// registracija klase koja obrađuje pritiske tipaka
		addKeyListener(this);
	}

	/**
	 * Metoda za iscrtavanje igračeg polja, trenutnog i slijedećeg tetromina i tekstova sa bodovima, nivoom i brojem linija.
	 */
	@Override
	public void paint(Graphics g) {
		// poziv roditeljske paint metode
		super.paint(g);

		// pohrani graphics objekt
		this.g = g;

		// iscrtavanje igraćeg polja
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				nacrtajKvadratic(x + GAMEFIELD_POMAK_X, y + GAMEFIELD_POMAK_Y, gamePlay.getTetrominoColorXY(x, y));
			}
		}
		
		prikaziRezultat();
		
		prikaziNivo();
		
		prikaziSpusteneLinije();

		// nacrtaj trenutno aktivnu figuru
		Tetromino trenutni = gamePlay.getT();
		trenutni.nacrtaj(this, GAMEFIELD_POMAK_X + gamePlay.getTx(), GAMEFIELD_POMAK_Y + gamePlay.getTy());
		
		// iscrtaj slijedću figuru desno od igračeg polja
		Tetromino slijedeci = gamePlay.getNext();
		slijedeci.nacrtaj(this, GAMEFIELD_POMAK_X + 12, GAMEFIELD_POMAK_Y + 3);
		
	}

	/**
	 * Metoda za nacrtaj kvadaratic 3 boje/nijanse na zadanim kordinatama.
	 * 
	 * @param x kordinata u kvadratićima
	 * @param y kordinata u kvadratićima
	 * @param color tetromino color boja(3 boje)
	 */
	public void nacrtajKvadratic(int x, int y, TetrominoColor color) {
		// preračun kvadarata u kvadaratićima u grafičke kordinate
		int xg = x * GRID_SIZE;
		int yg = y * GRID_SIZE;

		//nacrtaj srednji dio kvadratića
		g.setColor(color.getSrednji());
		g.fillRect(xg, yg, GRID_SIZE - 1, GRID_SIZE - 1);

		//nacrtaj doljnju i desnu liniju
		g.setColor(color.getTamni());
		g.drawLine(xg + GRID_SIZE - 1, yg, xg + GRID_SIZE - 1, yg + GRID_SIZE - 1);
		g.drawLine(xg, yg + GRID_SIZE - 1, xg + GRID_SIZE - 1, yg + GRID_SIZE - 1);

		//nacrtaj gornju i lijevu kao svjetle
		g.setColor(color.getSvjetli());
		g.drawLine(xg, yg, xg, yg + GRID_SIZE - 2);
		g.drawLine(xg, yg, xg + GRID_SIZE - 2, yg);
	}
	
	/**
	 * Metoda radi prikaz trnutnog rezultata.
	 */
	private void prikaziRezultat() {
		//ispisi tekst bodovi u crnoj boji
		g.setColor(Color.BLACK);
		g.setFont(fontTekst);
		g.drawString("Bodovi:", (GAMEFIELD_POMAK_X + 12) * GRID_SIZE + 10, (GAMEFIELD_POMAK_Y + 1) * GRID_SIZE);
		
		//ispisi iznos bodova u obliku osam znamenki s vodećim nulama centrirano
		String bodoviTekst = String.format("%08d", gamePlay.getScore());
		int dx = izracunajPomakTeksta(bodoviTekst, fontBodovi);
		g.setFont(fontBodovi);
		g.drawString(bodoviTekst, (GAMEFIELD_POMAK_X + 11) * GRID_SIZE + dx, (GAMEFIELD_POMAK_Y + 2) * GRID_SIZE);
	}
	
	/*
	 * Metoda radi prikaz trnutnog nivoa.
	 */
	private void prikaziNivo() {
		//ispis teksta nivo
		g.setColor(Color.BLACK);
		g.setFont(fontTekst);
		g.drawString("Nivo:", (GAMEFIELD_POMAK_X + 13) * GRID_SIZE, (GAMEFIELD_POMAK_Y + 9)* GRID_SIZE);
		
		//ispis teksta nivo u obliku dvije znamenke s vodećom nulom
		String nivoTekst = String.format("%02d", gamePlay.getNivo() + 1);
		int dx = izracunajPomakTeksta(nivoTekst, fontBrojevi);
		g.setFont(fontBrojevi);
		g.drawString(nivoTekst, (GAMEFIELD_POMAK_X + 11) * GRID_SIZE + dx, (GAMEFIELD_POMAK_Y + 10) * GRID_SIZE + 6);
	}
	
	/**
	 * Metoda radi prikaz broja spuštenih linija.
	 */
	private void prikaziSpusteneLinije() {
		//ispis teksta linije
		g.setColor(Color.BLACK);
		g.setFont(fontTekst);
		g.drawString("Linije:", (GAMEFIELD_POMAK_X + 13) * GRID_SIZE, (GAMEFIELD_POMAK_Y + 13)* GRID_SIZE);
		
		//ispis broj linija u obliku tri znamenke s vodećim nulama
		String linijeTekst = String.format("%03d", gamePlay.getLinije());
	    int dx = izracunajPomakTeksta(linijeTekst, fontBrojevi);
		g.setFont(fontBrojevi);
		g.drawString(linijeTekst, (GAMEFIELD_POMAK_X + 11) * GRID_SIZE + dx, (GAMEFIELD_POMAK_Y + 14) * GRID_SIZE + 6);
	}
	
	/**
	 * Metoda za izračunavanje pomaka teksta u odnosu na šest kvadratića i zadani font i tekst.
	 * 
	 * @param tekst slova
	 * @param font font kojim ćemo ispisati tekst
	 * @return vraća iznos pomaka
	 */
	private int izracunajPomakTeksta(String tekst, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		// izracunaj x pomak za tekst
	    return (GRID_SIZE * 6 - metrics.stringWidth(tekst)) / 2;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	/**
	 * 
	 * Metoda za osluskivanje tastature.
	 * 
	 * @param e keyevent
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
		
		//detekcija pritisnutih tipki lijevo/desno i gore(rotacija)/dolje i razmaknica(spustanje)
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			gamePlay.pomakniLijevo();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			gamePlay.pomakniDesno();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			gamePlay.pomakniDole();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			gamePlay.rotiraj(true);
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			gamePlay.spusti();
			gamePlay.addScore(4);
		}
		
		//System.out.println(gamePlay.getT().toString());
		//System.out.println("tx: " + gamePlay.getTx() + ", ty: " + gamePlay.getTy());
		repaint();//ponovno pobojaj
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	/**
	 * @return the tetris
	 */
	public JFrame getTetris() {
		return tetris;
	}

}
