import java.awt.Point;

public class Ordenador extends JugadorGenerico{
	public Ordenador(Jugador propietario) {
		super(propietario);
	}
	
	private static Point generaCoordenadas() {
		return new Point(Juego.random.nextInt(Pantalla.NUM_CELDAS_ANCHO), Juego.random.nextInt(Pantalla.NUM_CELDAS_ALTO));
	}
	
	/**
	 * Va generando coordenadas aleatorias hasta que encuentra unas en las que no 
	 * se ha realizado ning�n disparo
	 */
	public Point generaTiroAleatorio() {
		Point coordenadas = null;
		boolean tiroValido = false;
		while (!tiroValido) {
			coordenadas = generaCoordenadas();
			int posx = coordenadas.x;
			int posy = coordenadas.y;
			//posicionaCursor(posx, posy);
			tiroValido = getSuTablero().tiroValido(posx, posy);
		}
		return coordenadas;
	}
	
	public void marcaCasillasProhibidas(Barco barco) {
		getSuTablero().marcaCasillasProhibidas(barco);
	}
	
	/**
	 * Comprueba que la coordenada posx, posy
	 * 		1. no est� fuera del tablero
	 * 		2. no haya sido disparada con anterioridad
	 * @param posx
	 * @param posy
	 * @return
	 */
	public boolean compruebaNuevaCoordenada(int posx, int posy) {
		if (posx < 0 || posx >= Pantalla.NUM_CELDAS_ANCHO || 
			posy < 0 || posy >= Pantalla.NUM_CELDAS_ALTO) {
			// Si est� fuera de l�mites, no va en la direcci�n correcta
			return false;
		} else {
			// Si no est� fuera de l�mites, miramos si ya se hab�a disparado en esa casilla
			boolean tiroValido = getSuTablero().tiroValido(posx, posy);
			if (!tiroValido) {
				// Si ya se hab�a disparado, tampoco nos sirve
				return false;
			} else {
				// Hemos encontrado una posici�n v�lida
				posicionaCursor(posx, posy);
				return true;
			}
		}
	}
	
	private void posicionaCursor(int posx, int posy) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
	

			
			

