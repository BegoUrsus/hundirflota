import java.awt.Point;

public class Barco {
	
	private final String[][] NOM_PNG_EXPLOSION = {
			{"boom.png", "boom.png", "boom.png", "boom.png", "boom.png"},	// Portaaviones
			{"boom.png", "boom.png", "boom.png", "boom.png"}, // Acorazado
			{"boom.png", "boom.png", "boom.png"}, // Fragata
			{"boom.png", "boom.png"},	// Submarino
			{"agua_fallo.png"}
	};
	
	private final String[][] NOM_PNG_BARCOS_OK = {
			{"b50-1.png", "b50-2.png", "b50-3.png", "b50-4.png", "b50-5.png"},	// Portaaviones
			{"b40-1.png", "b40-2.png", "b40-3.png", "b40-4.png"}, // Acorazado
			{"b32-1.png", "b32-2.png", "b32-3.png"}, // Fragata
			{"b20-1.png", "b20-2.png"}, // Submarino
			{"agua.png"}
	};
	
	private final String[][] NOM_PNG_BARCOS_BOOM = {
			{"b50-1e.png", "b50-2e.png", "b50-3e.png", "b50-4e.png", "b50-5e.png"},	// Portaaviones
			{"b40-1e.png", "b40-2e.png", "b40-3e.png", "b40-4e.png"}, // Acorazado
			{"b32-1e.png", "b32-2e.png", "b32-3e.png"}, // Fragata
			{"b20-1e.png", "b20-2e.png"},	// Submarino
			{"agua_fallo.png"}
	};
	
	private Jugador propietario; 
	private TipoBarco tipo;
	private Point[] posiciones;
	private boolean[] posicionesTocadas;
	private boolean estaHundido = false;
	private boolean horizontal = true;
	private String[] nomImagenesOK;
	private String[] nomImagenesTocado;
	private String[] nomImagenesHundido;
	
	public void initBarco() {
		estaHundido = false;
		horizontal = true;
		for (int i = 0; i < posiciones.length; i++) {
			posiciones[i] = new Point(0, 0);
		}
		for (int i = 0; i < posicionesTocadas.length; i++) {
			posicionesTocadas[i] = false;
		}
	}
	
	/**
	 * Construimos el barco según el tipo
	 * @param tipo
	 */
	public Barco(TipoBarco tipo, Jugador propietario) {
		this.propietario = propietario;
		this.tipo = tipo;
		posiciones = new Point[tipo.getLongitud()];				// Coordenadas del barco; tantas como su longitud
		posicionesTocadas = new boolean[tipo.getLongitud()];	// Posiciones que han sido disparadas; tantas como su longitud
		for (int i = 0; i < posiciones.length; i++) {
			posiciones[i] = new Point(0, 0);
		}
		for (int i = 0; i < posicionesTocadas.length; i++) {
			posicionesTocadas[i] = false;
		}
		nomImagenesOK = new String[tipo.getLongitud()];
		nomImagenesTocado = new String[tipo.getLongitud()];
		nomImagenesHundido = new String[tipo.getLongitud()];
		int indice = 0;
		switch (tipo) {
		case PORTAVIONES:
			indice = 0;
			break;
		case ACORAZADO:
			indice = 1;
			break;
		case DESTRUCTOR:
			indice = 2;
			break;
		default:
			indice = 3;
			break;
		}
		if (this.propietario == Jugador.PERSONA) {
			nomImagenesOK = NOM_PNG_BARCOS_OK[indice].clone();
			nomImagenesTocado = NOM_PNG_BARCOS_BOOM[indice].clone();
			nomImagenesHundido = NOM_PNG_BARCOS_BOOM[indice].clone();
		} else {
			nomImagenesOK = NOM_PNG_BARCOS_OK[indice].clone();
			nomImagenesTocado = NOM_PNG_EXPLOSION[indice].clone();
			nomImagenesHundido = NOM_PNG_BARCOS_BOOM[indice].clone();
		}
		estaHundido = false;
	}
	
	
	
	/**
	 * @return the tipo
	 */
	public TipoBarco getTipo() {
		return tipo;
	}

	/**
	 * @return the tipo
	 */
	public Point getPosicion(int indice) {
		return posiciones[indice];
	}



	/**
	 * Devuelve el nombre de la imagen ok del elemento indicado en indice
	 */
	public String getNomImagenOK(int indice ) {
		return nomImagenesOK[indice];
	}



	/**
	 * Devuelve el nombre de la imagen tocado del elemento indicado en indice
	 */
	public String getNomImagenTocado(int indice) {
		return nomImagenesTocado[indice];
	}


	/**
	 * Devuelve el nombre de la imagen tocado del elemento indicado en indice
	 */
	public String getNomImagenHundido(int indice) {
		return nomImagenesHundido[indice];
	}



	public int getLongitud() {
		return tipo.longitud;
	}
	
	public boolean getHundido() {
/*		if (estaHundido)
			Juego.panelStatus.setStatus("¡Hundido!");*/
		return estaHundido;
	}
	
	
	
	/**
	 * Asigna las posiciones al barco una vez colocado por el jugador o creado por el ordenador
	 * @param posX
	 * @param posY
	 * @param horizontal
	 */
	public void setPosiciones(int posX, int posY, boolean horizontal) {
		this.setHorizontal(horizontal);
		int incX = 0;
		int incY = 0;
		if (horizontal)
			incX = 1;
		else
			incY = 1;
		for (int i=0; i < tipo.getLongitud(); i++) {
			posiciones[i].x = posX+(incX*i);
			posiciones[i].y = posY+(incY*i);
		}
		
	}
	
	/**
	 * Nos dice si el barco está en esa posicion
	 * @param posX
	 * @param posY
	 * @return el índice de la posición del barco correspondiente a esa coordenada
	 * 			(-1 si no está)
	 */
	public int estoyAqui(int posX, int posY) {
		for (int i = 0; i < tipo.getLongitud(); i++) {
			if (posiciones[i].getX() == posX && 
				posiciones[i].getY() == posY)
				return i;
		}
		return -1;
	}
	
	/**
	 * Comprueba todas las posiciones para ver si está hundido o no 
	 * y si todas están tocadas, actualiza estáHundido a true
	 * @param indice
	 */
	public void actualizaPosicionesTocadas(int indice) {
		for (int i = 0; i < tipo.getLongitud(); i++)
			if (!posicionesTocadas[i])
				return;
		// Si hemos llegado hasta aquí, es que 
		// todas las posiciones han sido tocadas y, por tanto,
		// se ha hundido
		estaHundido = true;
	}
	
	
	/**
	 * Comprueba si el disparo a la coordenada especificada ha dado
	 * en el barco
	 * @param posX
	 * @param posY
	 * @return true si el barco estaba en esas coordenadas
	 * 		   false si ha fallado el disparo (el barco no estaba en esas coordenadas)
	 */
	public boolean disparoCertero(int posX, int posY) {
		int indice = estoyAqui(posX, posY);
		// Si el índice retornado es -1, es que el barco no estaba ahí
		if (indice == -1) 
			return false;
		// El barco estaba en las coordenadas, así que actualizamos las posicionesTocadas
		posicionesTocadas[indice] = true;
		actualizaPosicionesTocadas(indice);
		return true;
		
	}



	/**
	 * @return the horizontal
	 */
	public boolean isHorizontal() {
		return horizontal;
	}



	/**
	 * @param horizontal the horizontal to set
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}



	
	
	
}
