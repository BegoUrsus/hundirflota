import java.awt.Point;
import java.util.HashMap;
import java.util.Map;


public class JugadorGenerico {

	private Barco[] flota = new Barco[Pantalla.CANT_TOT_BARCOS];
	private FlotaPropia miTablero  = new FlotaPropia(Pantalla.NUM_CELDAS_ALTO, Pantalla.NUM_CELDAS_ANCHO);
	private FlotaOponente suTablero = new FlotaOponente(Pantalla.NUM_CELDAS_ALTO, Pantalla.NUM_CELDAS_ANCHO);
	private int barcosHundidos = 0;
	
	public void limpiaBarcos() {
		barcosHundidos = 0;
		for (int i = 0; i < Pantalla.CANT_TOT_BARCOS; i++) {
			getBarco(i).initBarco();
			flota[i].initBarco();
		}
	}
	
	public void initFlotas() {
		limpiaBarcos();
		miTablero.initFlota();
		suTablero.initFlota();
	}
	
	public JugadorGenerico(Jugador propietario) {
		flota[0] = new Barco(TipoBarco.PORTAVIONES, propietario);
		flota[1] = new Barco(TipoBarco.ACORAZADO, propietario);
		flota[2] = new Barco(TipoBarco.ACORAZADO, propietario);
		flota[3] = new Barco(TipoBarco.DESTRUCTOR, propietario);
		flota[4] = new Barco(TipoBarco.DESTRUCTOR, propietario);
		flota[5] = new Barco(TipoBarco.SUBMARINO, propietario);
		flota[6] = new Barco(TipoBarco.SUBMARINO, propietario);
		initFlotas();
	}
	
	/**
	 * @return the barcosHundidos
	 */
	public int getBarcosHundidos() {
		return barcosHundidos;
	}

	/**
	 * Incrementa el número de barcos hundidos por el jugador
	 */
	public void incBarcosHundidos() {
		barcosHundidos ++;
	}

	public Barco getBarco(int indice) {
		return flota[indice];
	}
	
	/**
	 * @return the flota
	 */
	public Barco[] getFlota() {
		return flota;
	}

	/**
	 * @return the miTablero
	 */
	public FlotaPropia getMiTablero() {
		return miTablero;
	}

	/**
	 * @return the suTablero
	 */
	public FlotaOponente getSuTablero() {
		return suTablero;
	}

	public Barco barcoTocado(int posX, int posY) {
		for (Barco barco : flota) {
			if (barco.disparoCertero(posX, posY))
				return barco;
		}
		return null;
	}
	
	
	/**
	 * comprueba si hay espacio suficiente para colocar el barco
	 * en la coordenada y orientacion especificadas
	 * @param coord			// Posicion en la que queremos colocar el barco
	 * @param horizontal	// true si en horizontal, false si en vertical
	 * @param longitud		// longitud del barco
	 * @return
	 */
	public boolean hayHueco(int posX, int posY, boolean horizontal, int longitud) {
		int incX = 0;
		int incY = 0;
		if (horizontal) {
			incX = 1;
			if (posX+longitud > Pantalla.NUM_CELDAS_ANCHO)
				return false;
		} else {
			incY = 1;
			if (posY+longitud > Pantalla.NUM_CELDAS_ALTO)
				return false;
		}
		int x = posX;
		int y = posY;
		for (int i = 0; i < longitud; i++) {
			if (miTablero.tieneBarco(x, y))	// Si tiene un barco no se puede colocar
				return false;
			if (miTablero.estaProhibida(x, y))	// Si está prohibida tampoco se puede colocar
				return false;
			x += incX;
			y += incY;
			
		}
			
		return true;
	}
	
	/** 
	 * Quita del HashMap las casillas que ocupa el barco
	 * También quita las casillas de alrededor
	 * @param mapa
	 * @param posX
	 * @param posY
	 * @param horizontal
	 * @param longitud
	 */
	public void quitaDisponibles(HashMap<Integer, Point> mapa, int posX, int posY, boolean horizontal, int longitud) {
		int incX = 0;
		int incY = 0;
		if (horizontal)
			incX = 1;
		else
			incY = 1;
		for (int i=0; i < longitud; i++) {
			int newX = posX + (i*incX);
			int newY = posY + (i*incY);
			int indice = newX + newY * Pantalla.NUM_CELDAS_ANCHO;
			mapa.remove(indice);
		}
		int longitudX = horizontal ? longitud : 1;
		int longitudY = horizontal ? 1 : longitud;
		// marcamos las casillas de alrededor; nos basamos en la primera y última posición del barco
		for (int y = posY-1; y <= (posY + longitudY -1) + 1; y++) {
			for (int x = posX - 1; x <= (posX + longitudX -1) + 1; x++) {
				// Comprobamos que la casilla no esté fuera del tablero
				if (x >= 0 && x < Pantalla.NUM_CELDAS_ANCHO && 
					y >= 0 && y < Pantalla.NUM_CELDAS_ALTO) {
					int indice = x + y * Pantalla.NUM_CELDAS_ANCHO;
					//System.out.println("Antes: " + mapa.keySet());
					mapa.remove(indice);
					//System.out.println("Después: " + mapa.keySet());
				}
			}
		}

	}
	
	public void muestraPosiciones(HashMap<Integer, Point> mapa) {
		for (Map.Entry<Integer, Point> entry : mapa.entrySet()) {
		    Integer indice = entry.getKey();
		    Point valor = entry.getValue();
		    //System.out.println("indice: " + indice + " Punto: " + valor);
		}	
	}
			


	/** Coloca los barcos de forma aleatoria
	 * 
	 */
	public void generaBarcosRadom() {
		HashMap<Integer, Point> posicionesBuscar = new HashMap<>() ; 
		for (int y=0; y<Pantalla.NUM_CELDAS_ALTO; y++)
			for (int x=0; x<Pantalla.NUM_CELDAS_ANCHO; x++) {
				int indice = x+ y*Pantalla.NUM_CELDAS_ANCHO;
				posicionesBuscar.put(indice, new Point(x, y));
			}
				
		boolean encontrado = false;
		limpiaBarcos();
		initFlotas();
		int cantidad = Pantalla.NUM_CELDAS_ANCHO * Pantalla.NUM_CELDAS_ALTO;
		for (int i = 0; i < Pantalla.CANT_TOT_BARCOS; i++) {
			encontrado = false;
			do {
				//int cantidad = posicionesBuscar.size();
				// el size del hashmap actualiza el número de claves activas, pero mantiene la clave
				int indice = 0;
				do {
					indice = Juego.random.nextInt(cantidad);
					//System.out.println("Cantidad: " + cantidad + " Indice: " + indice + " Punto: " + posicionesBuscar.get(indice) );
				} while (posicionesBuscar.get(indice) == null);
				if (posicionesBuscar.containsKey(indice)) {
					Point randPos = posicionesBuscar.get(indice);
					
					//int randX = Juego.random.nextInt(Pantalla.NUM_CELDAS_ANCHO);
					//int randY = Juego.random.nextInt(Pantalla.NUM_CELDAS_ALTO);
					int randX = randPos.x;
					int randY = randPos.y;
					boolean horizontal = Juego.random.nextBoolean();
					//System.out.println(cantidad + " " + randX + " " + randY);
					encontrado = hayHueco(randX, randY, horizontal, getBarco(i).getLongitud());
					if (encontrado) {
						getBarco(i).setPosiciones(randX, randY, horizontal);
						miTablero.setOcupados(randX, randY, horizontal, getBarco(i).getLongitud());
						quitaDisponibles(posicionesBuscar, randX, randY, horizontal, getBarco(i).getLongitud());
					}
					else {
						//System.out.println("No hay hueco");
						//System.out.println(miTablero.toString());
						//muestraPosiciones(posicionesBuscar);
						
					}
				}
				else {
					//muestraPosiciones(posicionesBuscar);
					//System.out.println("Indice ocupado");
				}
			} while (!encontrado);
		}
				
	}
	
	public void dibujaTableroPorConsola(String nombre) {
		System.out.println("JugadorGenerico " + nombre);
		System.out.println(miTablero);
	}

}
