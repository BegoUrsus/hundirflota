import java.awt.Point;

import javax.swing.JOptionPane;

public class LogicaJuego  extends Thread {
	
	/** 
	 * Variables generales del programa
	 */
	
	static enum EstadoOrdenador {ESPERANDO, BUSCANDO, FIJANDO, HUNDIENDO_FASE_1, HUNDIENDO_FASE_2, COMPROBANDO}
	static enum DireccionDisparo {NINGUNA, NORTE, SUR, ESTE, OESTE}
	
	private static boolean turnoPersona = true;
	private static DireccionDisparo direccionDisparo = DireccionDisparo.NINGUNA;
	private static EstadoOrdenador estadoOrdenador = EstadoOrdenador.ESPERANDO; 
	private static Point posPrimerDisparo = null;
	private static Point posUltimoDisparo = null;
	private static int barcosHundidosOrdenador = 0;
	private static int barcosHundidosPersona = 0;
	private static boolean finalPartida = false;
	private static boolean salida = false;
	
	public LogicaJuego() {
		inicializaEstado();
	}
	
	public static void inicializaEstado() {
		turnoPersona = true;
		direccionDisparo = DireccionDisparo.NINGUNA;
		estadoOrdenador = EstadoOrdenador.ESPERANDO; 	
		posPrimerDisparo = null;
		posUltimoDisparo = null;
		barcosHundidosOrdenador = 0;
		barcosHundidosPersona = 0;
		finalPartida = false;
		salida = false;
	}


	/**
	 * Cambia el tipo de jugador de PERSONA a ORDENADOR
	 * y viceversa
	 * @param jugador
	 * @return
	 */
	public static Jugador cambiaJugador(Jugador jugador) {
		if (jugador == Jugador.PERSONA) {
			return Jugador.ORDENADOR;
		} else
			return Jugador.PERSONA;
	}
	
	
	
	/** Cambia el turno de ordenador a persona y viceversa
	 * 
	 */
	public static void cambiaTurno() {
		turnoPersona = !turnoPersona;
		if (!turnoPersona && estadoOrdenador==EstadoOrdenador.ESPERANDO)
			estadoOrdenador = EstadoOrdenador.BUSCANDO;
		Juego.framePrincipal.panelOrdenador.cambiaTurno(turnoPersona);
		Juego.framePrincipal.panelPersona.cambiaTurno(turnoPersona);
	}
	
	/** Asigna el turno al jugador especificado
	 * 
	 * @param jugador
	 */
	public static void fijaTurno(Jugador jugador) {
		turnoPersona = jugador == Jugador.PERSONA;
		if (!turnoPersona && estadoOrdenador==EstadoOrdenador.ESPERANDO)
			estadoOrdenador = EstadoOrdenador.BUSCANDO;
		Juego.framePrincipal.panelOrdenador.cambiaTurno(turnoPersona);
		Juego.framePrincipal.panelPersona.cambiaTurno(turnoPersona);
	}
	
	/**
	 * Devuelve true si el turno lo tiene la persona
	 * @return
	 */
	public static boolean isTurnoPersona() {
		return turnoPersona;
	}
	
	/** 
	 * Busca unas nuevas coordenadas para disparar 
	 * que no hayan sido ya utilizadas
	 * @return
	 */
	private static Point buscaPosicion() {
		return Juego.ordenador.generaTiroAleatorio();
	}
	
	/**
	 * Guarda la posicion del primer disparo hecho al barco
	 * @param posicion
	 */
	private static void salvaPosPrimerDisparo(Point posicion) {
		posPrimerDisparo = posicion;
	}
	
	/**
	 * Devuelve la posición del primer disparo hecho al barco
	 * @return
	 */
	private static Point recuperaPosPrimerDisparo() {
		return posPrimerDisparo;
	}
	
	/**
	 * Almacena la posición del útlimo disparo correcto
	 * @param posicion
	 */
	private static void salvaPosUltimoDisparo(Point posicion) {
		posUltimoDisparo = posicion;
	}
	
	/**
	 * Devuelve la posición del último disparo correcto
	 * @return
	 */
	private static Point recuperaPosUltimoDisparo() {
		return posUltimoDisparo;
	}
	
	/**
	 * Redibuja la casilla según se haya tocado un barco o
	 * haya sido un tiro al agua
	 * @param tocado
	 */
	private static void actualizaDisparo(Point posicion, boolean tocado) {
		if (tocado)
			Juego.panelFlotaPersona.pintaCasillaTocado(posicion.x, posicion.y);
		else
			Juego.panelFlotaPersona.pintaCasillaAgua(posicion.x, posicion.y);
		Juego.ordenador.getSuTablero().ActualizaPosicion(posicion.x, posicion.y, tocado);
	}
	
	/** Devuelve el barco que hay en la posicion de disparo 
	 *  o null, si no había ningún barco
	 * @param posicion
	 * @return
	 */
	private static Barco dispara(Point posicion) {
		Barco barco =Juego.persona.barcoTocado(posicion.x, posicion.y);
		actualizaDisparo(posicion, barco != null);
		return barco;
	}
	
	/**
	 * Cambia el sentido del disparo, de norte a sur y de 
	 * este a oeste y viceversa.
	 * Si no había dirección, genera una al azar
	 * @return
	 */
	private static DireccionDisparo cambiaSentidoDisparo() { 
		switch (direccionDisparo) {
		case NORTE:
			return DireccionDisparo.SUR;
		case SUR:
			return DireccionDisparo.NORTE;
		case ESTE:
			return DireccionDisparo.OESTE;
		case OESTE:
			return DireccionDisparo.ESTE;
		case NINGUNA:
		default:
			return generaDireccionDisparoRandom();
		}
	}
	
	/**
	 * Genera una dirección aleatoria
	 * @return
	 */
	private static DireccionDisparo generaDireccionDisparoRandom() {
		int caso = Juego.random.nextInt(4);
		return DireccionDisparo.values()[caso];
	}
	
	/**
	 * Cambia la dirección del diparo en el orden: NSEO
	 * Si no había dirección genera una aleatoria
	 * @param direccion
	 * @return
	 */
	private static DireccionDisparo cambiaDireccionDisparo(DireccionDisparo direccion) { 
		switch (direccion) {
		case NORTE:
			return DireccionDisparo.SUR;
		case SUR:
			return DireccionDisparo.ESTE;
		case ESTE:
			return DireccionDisparo.OESTE;
		case OESTE:
			return DireccionDisparo.NORTE;
		case NINGUNA:
		default:
			return generaDireccionDisparoRandom();
		}
	}
	
	
	/**
	 * Desplaza la posicion una casilla en la dirección especificada
	 * @param posicion
	 * @param direccion
	 * @return
	 */
	private static Point avanza(Point posicion, DireccionDisparo direccion) {
		Point incremento = new Point();
		switch (direccion) {
		case NORTE:
			incremento = new Point(0, -1);
			break;
		case SUR:
			incremento = new Point(0, 1);
			break;
		case ESTE:
			incremento = new Point(1, 0);
			break;
		case OESTE:
			incremento = new Point(-1, 0);
			break;
		case NINGUNA:
		default:
			incremento = new Point(0, 0);
			break;
		}
		return new Point(posicion.x + incremento.x, posicion.y + incremento.y);
	}
	
	/**
	 * Nos devuelve true si el disparo se realiza dentro del tablero
	 * y no ha sido disparada con anterioridad
	 * @param posicion
	 * @return
	 */
	private static boolean coordenadaCorrecta(Point posicion) {
		return Juego.ordenador.compruebaNuevaCoordenada(posicion.x, posicion.y);
	}

	/**
	 * Incrementa el número de barcos que ha hundido el jugador
	 * y devuelve true si ya ha hundido todos los barcos
	 * @param jugador
	 * @return
	 */

	private static boolean incrementaBarcosHundios(Jugador jugador) {
		if (jugador ==Jugador.ORDENADOR) {
			barcosHundidosOrdenador++;
			return barcosHundidosOrdenador >= Pantalla.CANT_TOT_BARCOS;
		} else {
			barcosHundidosPersona++;
			return barcosHundidosPersona >= Pantalla.CANT_TOT_BARCOS;
			
			
		}
	}

	/**
	 * Pausa el thread los milisegundos especificados
	 * @param milis
	 */
	public static void espera(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
//	public static void main(String[] args) {
	public void run() {
		Point posicion;
		Barco barcoTocado = null;
		while (!salida) {
			if (!turnoPersona) {
				switch (estadoOrdenador) {
				case ESPERANDO:
					Juego.framePrincipal.setStatus("Turno del jugador. Dispare a la flota enemiga.");
					break;
					
				case BUSCANDO:
					espera(1500);
					//Fase de LocalizaObjetivo
					// En esta fase no tenemos ningun barco localizado. 
					// Hacemos disparos aleatorios

					// 1. Busca una nueva posicion aleatoria
					posicion = buscaPosicion();
					
					// 2. Salvamos la posicion
					salvaPosPrimerDisparo(posicion);
					
					// 3. Disparamos a la posición generada
					barcoTocado = dispara(posicion);
					
					// 4, Si hay un barco, pasamos a la fase de fijar objetivo
					// Si ha hecho un disparo al agua, perdemos el turno
					// Volveremos a esta misma fase al recuperar el turno
					if (barcoTocado!=null) {
						estadoOrdenador = EstadoOrdenador.FIJANDO;
						Juego.framePrincipal.setStatus("Barco alcanzado. El enemigo vuelve a disparar.");
					} else {
						Juego.framePrincipal.setStatus("Disparo al agua. El enemigo pierde su turno.");
						cambiaTurno();
					}
					break;
				
				case FIJANDO:
					//Fase Fijar objetivo 
					//En esta fase, hemos localizado el barco, pero no como está colocado

					// 1. Recuperamos la posicion del primer disparo al barco
					posicion = recuperaPosPrimerDisparo();
					
					// 2. Cambiamos la posicion (Si anteriormente estaba a ninguna, genera una aleatoria)
					direccionDisparo = cambiaDireccionDisparo(direccionDisparo);
					
					// 3. Avanzamos la posición según la dirección 
					posicion = avanza(posicion, direccionDisparo);

					// 4. Si el disparo generado no está en el tablero, hemos de cambiar de direccion
					if (!coordenadaCorrecta(posicion)) 
						break;
					
					// 5. Si hay un barco, vamos en la direccion correcta.
					// Guardamos la posición de este último disparo y 
					// pasamos a la primera fase de hundir objetivo
					// Si ha hecho un disparo al agua, perdemos el turno
					// Volveremos a esta misma fase al recuperar el turno
					
					barcoTocado = dispara(posicion);
					if (barcoTocado != null) {
						salvaPosUltimoDisparo(posicion);
						estadoOrdenador = EstadoOrdenador.HUNDIENDO_FASE_1;
						Juego.framePrincipal.setStatus("Dirección del barco fijada. El enemigo vuelve a disparar.");
					} else {
						Juego.framePrincipal.setStatus("Disparo al agua. El enemigo pierde su turno.");
						cambiaTurno();
					}
					break;
				case HUNDIENDO_FASE_1:
					// Fase Hundir objetivo (1)	
					// En esta fase, hemos localizado el barco y conocemos su orientacion(vertical u horizontal)
					// La vamos repitiendo hasta que hace un disparo al agua.
					// En ese momento tendremos que pasar a la fase Hundir objetivo (2)
					// para hundir el resto del barco

					// 1. Recuperamos la posición del último disparo realizado correctamente
					posicion = recuperaPosUltimoDisparo();
					
					// 2. Avanza la posición en la dirección que teníamos
					posicion = avanza(posicion, direccionDisparo);
					// Si nos hemos salido del tablero, tenemos que cambiar el sentido del barco y pasar
					// a la fase de Hundir objetivo (2)
					if (!coordenadaCorrecta(posicion)) {
						// marcamos como posicion del último disparo la del disparo original
						// para que no entre en un bucle
						salvaPosUltimoDisparo(recuperaPosPrimerDisparo());
						estadoOrdenador = EstadoOrdenador.HUNDIENDO_FASE_2;
						direccionDisparo = cambiaSentidoDisparo();
						break;
					}

					// 3. Dispara
					barcoTocado = dispara(posicion);
					
					// 4. Si hay un barco, es que no hemos acabado de hundir el primer lado.
					// Guardamos la posición de este último disparo y 
					// seguimos en esta misma fase.
					// Si ha hecho un disparo al agua, perdemos el turno.
					// Tenemos que cambiar el sentido del disparo para hundir el resto del barco.
					// Pasaremos a la fase Hundir objetivo(2)
					if (barcoTocado != null) {
						salvaPosUltimoDisparo(posicion);
						Juego.framePrincipal.setStatus("Barco tocado. El enemigo vuelve a disparar.");
					} else {
						// marcamos como posicion del último disparo la del disparo original
						// para que no entre en un bucle
						salvaPosUltimoDisparo(recuperaPosPrimerDisparo());
						estadoOrdenador = EstadoOrdenador.HUNDIENDO_FASE_2;
						Juego.framePrincipal.setStatus("Disparo al agua. El enemigo pierde su turno.");
						direccionDisparo = cambiaSentidoDisparo();
						cambiaTurno();
					}
					break;
				case HUNDIENDO_FASE_2:
					// Fase Hundir objetivo (2)
					// Esta es la ultima fase para hundir un barco.
					// Al llegar a esta fase, ya hemos localizado el barco, conocemos su orientacion(vertical u horizontal)
					// y hemos hundido una parte del barco y hemos cambiado el sentido del disparo para 
					// hundir el resto del barco en esta fase.
					// La vamos repitiendo hasta que el barco está hundido
					// La comprobación de si el barco está hundido se hace fuera de las fases
					// ya que puede haberse hundido tanto en la fase:
					//		Fijando: si se trata de un submarino
					//		Hundiendo, fases (1) y (2) si hemos empezado en un extremo y en la dirección correcta

					// 1. Recuperamos la posición del primer disparo realizado correctamente
					posicion = recuperaPosUltimoDisparo();
					
					// 2. Avanza la posición en la dirección que teníamos
					// El sentido lo hemos cambiado al finalizar la fase 1
					posicion = avanza(posicion, direccionDisparo);

					// 3. Dispara
					barcoTocado = dispara(posicion);
					
					// 4. Si hay un barco, es que no hemos acabado de hundir el segundo lado
					// Guardamos la posición de este último disparo y 
					// seguimos en esta misma fase.
					// No debería llegar a hacer un disparo al agua, ya que al salir del
					// switch en esta fase, comprueba si el barco se ha hundido o no
					if (barcoTocado!=null) {
						salvaPosUltimoDisparo(posicion);
						Juego.framePrincipal.setStatus("Barco tocado. El enemigo vuelve a disparar.");
					} else {
						Juego.framePrincipal.setStatus("ERROR: No debería estar aquí. Disparo al agua. El enemigo pierde su turno.");
						estadoOrdenador = EstadoOrdenador.BUSCANDO;
						cambiaTurno();
					}
					break;
				default:
					break;
				
				}
				
				// Si hemos tocado un barco en cualquiera de las fases,
				// comprobamos si se ha hundido y, si se ha hundido,
				// comprobamos si ya se han hundido todos, lo que implicaría  
				// que ha finalizado la partida actual.
				if (barcoTocado != null) {
					// Comprueba si el barco está completamente hundido
					if (barcoTocado.getHundido()) {
						// marcamos las casillas de alrededor como prohibidas
						Juego.ordenador.marcaCasillasProhibidas(barcoTocado);
						Juego.framePrincipal.setStatus("¡" + barcoTocado.getTipo() + " de la flota aliada hundido!");
						// Aumenta el número de barcos y comprueba si se
						// han hundido todos los barcos (ha ganado la partida)
						// Si ha acabado la partida pasa al estado de espera (no hace nada)
						// Si no ha acabado, pasa al estado de localizar un nuevo barco
						if (incrementaBarcosHundios(Jugador.ORDENADOR)) {
							finalPartida = true;
							estadoOrdenador = EstadoOrdenador.ESPERANDO;
						} else {
							estadoOrdenador = EstadoOrdenador.BUSCANDO;
						}
					}
				}
			}
			espera(500);
			
			// Si ha acabado la partida preguntamos al jugador si quiere seguir jugando o no
			if (finalPartida) {
				Juego.framePrincipal.setStatus("La flota enemiga ha ganado la batalla.");
				// Preguntamos al jugador si quiere jugar otra partida
				salida = JOptionPane.showConfirmDialog(
						null, 
						"¡Ha perdido la partida! ¿Jugamos de nuevo?", 
						"Hundir la flota", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.PLAIN_MESSAGE) == JOptionPane.NO_OPTION;

				// Si se ha de jugar otra partida, recolocamos el tablero
				if (!salida) {
					inicializaEstado();
					Juego.iniciaPartida();
				}
				else
					System.exit(0);
			}
		
		}
	}



}
