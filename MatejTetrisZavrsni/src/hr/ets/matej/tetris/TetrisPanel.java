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
 * 
 */
public class TetrisPanel extends JPanel implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2602049652028138L;

	/**
	 * Definira velicinu podjele terena
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
	 * Gravni prozor
	 */
	private JFrame tetris;
	
	/**
	 * Koristi se za crtanje
	 */
	private Graphics g = null;
	
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
	 * @param title
	 * @throws HeadlessException
	 */
	public TetrisPanel(JFrame tetris) throws HeadlessException {
		gamePlay = new GamePlay(this);
		
		this.tetris = tetris;
		
		addKeyListener(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// pohrani graphics objekt
		this.g = g;

		//g.setColor(Color.BLACK);
		//g.drawRect(GAMEFIELD_POMAK_X * GRID_SIZE, GAMEFIELD_POMAK_Y * GRID_SIZE, 10 * GRID_SIZE, 20 * GRID_SIZE);
		
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				nacrtajKvadratic(x + GAMEFIELD_POMAK_X, y + GAMEFIELD_POMAK_Y, GamePlay.getTetrominoColorXY(x, y));
			}
		}
		
		prikaziRezultat();
		
		prikaziNivo();
		
		prikaziSpusteneLinije();

		Tetromino trenutni = gamePlay.getT();
		trenutni.nacrtaj(this, GAMEFIELD_POMAK_X + gamePlay.getTx(), GAMEFIELD_POMAK_Y + gamePlay.getTy());
		
		// iscrtaj slijedću
		Tetromino slijedeci = gamePlay.getNext();
		slijedeci.nacrtaj(this, GAMEFIELD_POMAK_X + 12, GAMEFIELD_POMAK_Y + 3);
		
	}

	public void nacrtajKvadratic(int x, int y, TetrominoColor color) {
		int xg = x * GRID_SIZE;
		int yg = y * GRID_SIZE;

		g.setColor(color.getSrednji());
		g.fillRect(xg, yg, GRID_SIZE - 1, GRID_SIZE - 1);

		g.setColor(color.getTamni());
		g.drawLine(xg + GRID_SIZE - 1, yg, xg + GRID_SIZE - 1, yg + GRID_SIZE - 1);
		g.drawLine(xg, yg + GRID_SIZE - 1, xg + GRID_SIZE - 1, yg + GRID_SIZE - 1);

		g.setColor(color.getSvjetli());
		g.drawLine(xg, yg, xg, yg + GRID_SIZE - 2);
		g.drawLine(xg, yg, xg + GRID_SIZE - 2, yg);
	}
	
	/**
	 * Metoda radi prikaz trnutnog rezultata. 
	 */
	private void prikaziRezultat() {
		g.setColor(Color.BLACK);
		g.setFont(fontTekst);
		g.drawString("Bodovi:", (GAMEFIELD_POMAK_X + 12) * GRID_SIZE + 10, (GAMEFIELD_POMAK_Y + 1) * GRID_SIZE);
		
		String bodoviTekst = String.format("%08d", gamePlay.getScore());
		int dx = izracunajPomakTeksta(bodoviTekst, fontBodovi);
		
		g.setFont(fontBodovi);
		g.drawString(bodoviTekst, (GAMEFIELD_POMAK_X + 11) * GRID_SIZE + dx, (GAMEFIELD_POMAK_Y + 2) * GRID_SIZE);
	}
	/*
	 * Metoda radi prikaz trnutnog nivoa.
	 */
	private void prikaziNivo() {
		g.setColor(Color.BLACK);
		g.setFont(fontTekst);
		g.drawString("Nivo:", (GAMEFIELD_POMAK_X + 13) * GRID_SIZE, (GAMEFIELD_POMAK_Y + 9)* GRID_SIZE);
		
		String nivoTekst = String.format("%02d", gamePlay.getNivo() + 1);
		int dx = izracunajPomakTeksta(nivoTekst, fontBrojevi);
		
		g.setFont(fontBrojevi);
		g.drawString(nivoTekst, (GAMEFIELD_POMAK_X + 11) * GRID_SIZE + dx, (GAMEFIELD_POMAK_Y + 10) * GRID_SIZE + 6);
	}
	
	private void prikaziSpusteneLinije() {
		g.setColor(Color.BLACK);
		g.setFont(fontTekst);
		g.drawString("Linije:", (GAMEFIELD_POMAK_X + 13) * GRID_SIZE, (GAMEFIELD_POMAK_Y + 13)* GRID_SIZE);
		
		
		String linijeTekst = String.format("%03d", gamePlay.getLinije());
	    int dx = izracunajPomakTeksta(linijeTekst, fontBrojevi);
	    
		g.setFont(fontBrojevi);
		g.drawString(linijeTekst, (GAMEFIELD_POMAK_X + 11) * GRID_SIZE + dx, (GAMEFIELD_POMAK_Y + 14) * GRID_SIZE + 6);
	}
	
	private int izracunajPomakTeksta(String tekst, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		// izracunaj x pomak za tekst
	    return (GRID_SIZE * 6 - metrics.stringWidth(tekst)) / 2;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
		
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
		repaint();
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
