import java.awt.Point;

public class FlotaPropia {
	/**
	 * Tipo de posiciones para el tablero propio
	 *
	 */
	public enum TipoPosicion {
		BARCO,			
		LIBRE,
		PROHIBIDA;
	}

	TipoPosicion[][] tablero;
	
	public void initFlota() {
		 for (int i=0; i<tablero.length; i++) {
			for (int j=0; j<tablero[i].length; j++) {
				tablero[i][j] = TipoPosicion.LIBRE;
			}
		}
	}
	
	/**
	 * Constructor
	 * @param ancho		 número de celdas en horizontal
	 * @param alto		número de celdas en vertical
	 */
	public FlotaPropia(int ancho, int alto) {
		 tablero = new TipoPosicion[ancho][alto];
		 initFlota();
	}
	
	/**
	 * Nos dice si una posicion del tablero propio está libre
	 * @param coord
	 * @return
	 */
	public boolean estaLibre(int posX, int posY) {
		return tablero[posY][posX] == TipoPosicion.LIBRE;
	}

	/**
	 * Nos dice si una posicion del tablero propio tiene un barco
	 * @param coord
	 * @return
	 */
	public boolean tieneBarco(int posX, int posY) {
		return tablero[posY][posX] == TipoPosicion.BARCO;
	}

	/**
	 * Nos dice si una posicion del tablero propio está prohibida
	 * @param coord
	 * @return
	 */
	public boolean estaProhibida(int posX, int posY) {
		return tablero[posY][posX] == TipoPosicion.PROHIBIDA;
	}

	/** fija como ocupadas todas las posiciones que ocupa el barco
	 * 	también marca como casillas prohibidas las que rodean an barco
	 * 
	 * @param posX 	posición inicial del barco en x
	 * @param posY	posición inicial del barco en y
	 * @param horizontal	true si se coloca horizontalmente
	 * @param longitud		longitud del barco
	 */
	
	public void setOcupados(int posX, int posY, boolean horizontal, int longitud) {
		int incX = 0;
		int incY = 0;
		if (horizontal)
			incX = 1;
		else
			incY = 1;
		Point inicio = new Point(posX, posY);	// Primera posició que ocupa el barco
		Point fin = new Point(posX, posY);		// Ultima posición que ocupa el barco
		for (int i=0; i < longitud; i++) {
			int actX = posX+(incX*i);
			int actY = posY+(incY*i);
			// Marcamos la casilla como que tiene un barco
			tablero[actY][actX] = TipoPosicion.BARCO;
			fin.setLocation(actX, actY);
		}
		// marcamos las casillas de alrededor; nos basamos en la primera y última posición del barco
		for (int y = inicio.y-1; y <=fin.y+1; y++) {
			for (int x = inicio.x - 1; x <= fin.x + 1; x++) {
				// Comprobamos que la casilla no esté fuera del tablero
				if (x >= 0 && x < Pantalla.NUM_CELDAS_ANCHO && 
					y >= 0 && y < Pantalla.NUM_CELDAS_ALTO) {
					// Si está marcada como libre, la marcamos como prohibida
					if (estaLibre(x, y))
						tablero[y][x] = TipoPosicion.PROHIBIDA;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String texto = "";
		for (int y = 0; y < tablero.length; y++) {
			for (int x = 0; x < tablero[y].length; x++) {
				switch (tablero[y][x]) {
				case BARCO:
					texto += "B ";
					break;

				case PROHIBIDA:
					texto += "X ";
					break;
				default:
					texto += ". ";
					break;
				}
			}
			texto += "\n";
		}
		return texto;
	}
	
	
	
}
