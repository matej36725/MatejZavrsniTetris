package hr.ets.matej.tetris;

import java.awt.Color;

/**
 * Klasa za 3 boje za 1 point u tetrominu ili polju.
 * 
 */
public class TetrominoColor {
	// varijable za 3 boje
	private Color svjetli;
	private Color srednji;
	private Color tamni;
	
	/**
	 * Stvara novi objekt sa zadane 3 boje.
	 * 
	 * @param svjetli boja za gornju i lijevu liniju
	 * @param srednji boja za sredinu kvadratića
	 * @param tamni boja za donju i desnu liniju
	 */
	public TetrominoColor(Color svjetli, Color srednji, Color tamni) {
		this.svjetli = svjetli;
		this.srednji = srednji;
		this.tamni = tamni;
	}

	/**
	 * Dohvat svijetle boje.
	 * 
	 * @return the svjetli
	 */
	public Color getSvjetli() {
		return svjetli;
	}

	/**
	 * Dohvat srednje boje.
	 * 
	 * @return the srednji
	 */
	public Color getSrednji() {
		return srednji;
	}

	/**
	 * Dohvat tamne boje.
	 * 
	 * @return the tamni
	 */
	public Color getTamni() {
		return tamni;
	}
}
