import java.awt.Point;


/**
 * Tipo de posiciones para el tablero contrario
 *
 */
enum TipoCoordenada {
	SIN_DISPARAR,	// Todavía no se ha realizado ningún disparo a esta posición
	AGUA,			// Se ha disparado a esta posiciónn pero no había ningún barco
	TOCADO,			// Se ha disparado a esta posición y había un barco en ella
	PROHIBIDA;		// Rodea a un barco hundido (no puede haber otro barco)
}

enum Direccion {
	NINGUNA (0, 0),
	NORTE (0, -1),
	SUR (0, 1),
	ESTE (1, 0),
	OESTE (-1, 0);
	
	int incX;
	int incY;
	
	Direccion(int incX, int incY) {
		this.incX = incX;
		this.incY = incY;
	}
	
	int getIncX() {
		return incX;
	}

	int getIncY() {
		return incY;
	}
	
	Point getDisparo(int posX, int posY) {
		return new Point(posX + incX, posY + incY);
	}
	
}



public class FlotaOponente {
	TipoCoordenada[][] tablero;
	Direccion dirDisparo = Direccion.NINGUNA;
	boolean dirCorrecta = false;
	Point ultDisparo = new Point(-1, -1);
	Point ultOrgDisparo = new Point(-1, -1);
	
	/**
	 * Inicializamos las variables que hacen referencia a los disparos
	 */
	private void inicializaDisparo() {
		dirDisparo = Direccion.NINGUNA;
		dirCorrecta = false;
		ultOrgDisparo.setLocation(-1, -1);
		ultDisparo.setLocation(-1, -1);
	}

	public void initFlota() {
		inicializaDisparo();
		 for (int i=0; i<tablero.length; i++) {
			for (int j=0; j<tablero[i].length; j++) {
				tablero[i][j] = TipoCoordenada.SIN_DISPARAR;
			}
		}
	}
	
	/**
	 * Constructor
	 * @param ancho		 número de celdas en horizontal
	 * @param alto		número de celdas en vertical
	 */
	public FlotaOponente(int ancho, int alto) {
		 tablero = new TipoCoordenada[ancho][alto];
		 initFlota();
	}
	
	/**
	 * Devuelve verdadero si todavía no se había disparado a esa casilla
	 * @param fila
	 * @param columna
	 * @return
	 */
	public boolean tiroValido(int x, int y) {
		return tablero[y][x] == TipoCoordenada.SIN_DISPARAR;
	}
	
	/**
	 * Después de haber disparado, actualizamos el valor de la celda según hubiese 
	 * un barco en la posición o no
	 * @param fila
	 * @param columna
	 * @param haybarco
	 */
	public void ActualizaPosicion(int x, int y, boolean haybarco) {
		tablero[y][x] = (haybarco ? TipoCoordenada.TOCADO : TipoCoordenada.AGUA);
	}
	
	public Point Dispara() {
		return new Point(-1, -1);
	}
	
	public void BarcoHundido(Barco barco) {
		inicializaDisparo();
		marcaCasillasProhibidas(barco);
	}
	/**
	 * Nos dice si una posicion del tablero propio está sin disparar
	 * @param coord
	 * @return
	 */
	public boolean estaLibre(int posX, int posY) {
		return tablero[posY][posX] == TipoCoordenada.SIN_DISPARAR;
	}


	public void marcaCasillasProhibidas(Barco barco) {
		// marcamos las casillas de alrededor; nos basamos en la primera y última posición del barco
		Point inicio = barco.getPosicion(0);
		Point fin = barco.getPosicion(barco.getLongitud()-1);
		
		// marcamos las casillas de alrededor; nos basamos en la primera y última posición del barco
		for (int y = inicio.y-1; y <=fin.y+1; y++) {
			for (int x = inicio.x - 1; x <= fin.x + 1; x++) {
				// Comprobamos que la casilla no esté fuera del tablero
				if (x >= 0 && x < Pantalla.NUM_CELDAS_ANCHO && 
					y >= 0 && y < Pantalla.NUM_CELDAS_ALTO) {
					// Si está marcada como libre, la marcamos como prohibida
					if (estaLibre(x, y))
						tablero[y][x] = TipoCoordenada.PROHIBIDA;
				}
			}
		}
	}

}
