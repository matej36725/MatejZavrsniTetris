/**
 * 
 */
package hr.ets.matej;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import hr.ets.matej.tetris.TetrisPanel;

/**
 * Glavna klasa za pokretanje tetrisa.
 * 
 */
@SuppressWarnings("serial")
public class Tetris extends JFrame {

	/**
	 * Konstruktor koji stvara novi prozor sa zadanim title-om.
	 * 
	 * @param title Naslov prozora.
	 */
	public Tetris(String title) {
		super(title);
	}

	/**
	 * Main metoda koja stvara glavni prozor i postavlja njegove parametre i pokazuje ga.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//objekt tetris
		Tetris tetris = new Tetris("Tetris - Matej");
		
		//objekt tetrisPanel-koristi se za iscrtavanje igre
		TetrisPanel tetrisPanel = new TetrisPanel(tetris);
		
		// fokusiraj nakon prikazivanja
		tetris.setAutoRequestFocus(true);
		
		// zatvori na x
		tetris.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//dimenzije panela
		tetrisPanel.setPreferredSize(new Dimension(400, 600));
		
		// potrebno zbog čitanja pritisnute tipke
		tetrisPanel.setFocusable(true);
		tetris.add(tetrisPanel);
		
		tetris.pack();
		
		// centriraj
		tetris.setLocationRelativeTo(null);
		tetris.setVisible(true);
	}

}