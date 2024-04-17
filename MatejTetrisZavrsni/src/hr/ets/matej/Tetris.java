/**
 * 
 */
package hr.ets.matej;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import hr.ets.matej.tetris.TetrisPanel;

/**
 * 
 */
public class Tetris extends JFrame {
	private static final long serialVersionUID = 1L;

	public Tetris(String title) {
		super(title);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Tetris tetris = new Tetris("Tetris - Matej");
		TetrisPanel tetrisPanel = new TetrisPanel();
		
		// fokusiraj nakon prikazivanja
		tetris.setAutoRequestFocus(true);
		
		// zatvori na x
		tetris.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		tetrisPanel.setPreferredSize(new Dimension(300, 600));
		tetrisPanel.setFocusable(true);
		tetris.add(tetrisPanel);
		
		tetris.pack();
		
		// centriraj
		tetris.setLocationRelativeTo(null);
		tetris.setVisible(true);
	}

}
