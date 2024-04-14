package hr.ets.matej.tetris;

import java.awt.Color;

public class TetrominoColor {
	private Color svjetli;
	private Color srednji;
	private Color tamni;
	
	public TetrominoColor(Color svjetli, Color srednji, Color tamni) {
		this.svjetli = svjetli;
		this.srednji = srednji;
		this.tamni = tamni;
	}

	/**
	 * @return the svjetli
	 */
	public Color getSvjetli() {
		return svjetli;
	}

	/**
	 * @return the srednji
	 */
	public Color getSrednji() {
		return srednji;
	}

	/**
	 * @return the tamni
	 */
	public Color getTamni() {
		return tamni;
	}
}
