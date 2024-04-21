/**
 * 
 */
package hr.ets.matej.tetris;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	 * Koristi se za crtanje
	 */
	private Graphics g = null;
	
	private GamePlay gamePlay;


	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public TetrisPanel() throws HeadlessException {
		gamePlay = new GamePlay();
		
		addKeyListener(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// pohrani graphics objekt
		this.g = g;

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				nacrtajKvadratic(x + GAMEFIELD_POMAK_X, y + GAMEFIELD_POMAK_Y, GamePlay.getTetrominoColorXY(x, y));
			}
		}

		/*
		for (int r = 0; r < 7; r++) {
			if (tetromino[r] != null) {
				//tetromino[r].nacrtaj(this, 2 + 5 * (r / 4), (r % 4) * 5 + 2 );
				tetromino[r].nacrtaj(this, 2 + 5 * (0), (r) * 5 + 2 );
				 t = tetromino[r].kloniraj();
				t.rotiraj(true);
				t.nacrtaj(this, 2 + 5 * (1), (r) * 5 + 2 );
				t.rotiraj(false);
				t.nacrtaj(this, 2 + 5 * (2), (r) * 5 + 2 );
			}
		}
		*/
		Tetromino trenutni = gamePlay.getT();
		trenutni.nacrtaj(this, GAMEFIELD_POMAK_X + gamePlay.getTx(), GAMEFIELD_POMAK_Y + gamePlay.getTy());
		
		// iscrtaj slijedću
		Tetromino slijedeci = gamePlay.getNext();
		slijedeci.nacrtaj(this, GAMEFIELD_POMAK_X + 12, GAMEFIELD_POMAK_Y + 2);
		
	}

	public void nacrtajKvadratic(int x, int y, TetrominoColor color) {
		int xg = x * GRID_SIZE;
		int yg = y * GRID_SIZE;

		g.setColor(color.getSrednji());
		g.fillRect(xg + 1, yg + 1, GRID_SIZE - 2, GRID_SIZE - 2);

		g.setColor(color.getTamni());
		g.drawLine(xg + GRID_SIZE - 1, yg, xg + GRID_SIZE - 1, yg + GRID_SIZE - 1);
		g.drawLine(xg, yg + GRID_SIZE - 1, xg + GRID_SIZE - 1, yg + GRID_SIZE - 1);

		g.setColor(color.getSvjetli());
		g.drawLine(xg, yg, xg, yg + GRID_SIZE - 2);
		g.drawLine(xg, yg, xg + GRID_SIZE - 2, yg);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
		
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
		}
		
		System.out.println(gamePlay.getT().toString());
		System.out.println("tx: " + gamePlay.getTx() + ", ty: " + gamePlay.getTy());
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
