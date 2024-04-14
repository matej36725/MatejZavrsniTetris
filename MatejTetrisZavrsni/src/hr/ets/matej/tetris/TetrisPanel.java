/**
 * 
 */
package hr.ets.matej.tetris;

import java.awt.Color;
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

		// nacrtajKvadratic(g, 3, 5, new Color(200, 0, 200), new Color(150, 0, 150), new
		// Color(100, 0, 100));
		TetrominoColor podloga = new TetrominoColor(new Color(40, 40, 40), new Color(30, 30, 30), new Color(10, 10, 10));
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 20; j++) {
				nacrtajKvadratic(i + 2, j + 3, podloga);
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
		
		System.out.println(gamePlay.getT().toString());
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
