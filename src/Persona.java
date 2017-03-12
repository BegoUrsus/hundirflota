import javax.swing.JOptionPane;

public class Persona extends JugadorGenerico{

	public Persona(Jugador propietario) {
		super(propietario);
	}

	
	public void juegaPersona(int posx, int posy) {
		
		boolean tiroValido = false;
		tiroValido = Juego.persona.getSuTablero().tiroValido(posx, posy); 
		if (tiroValido) {
			Barco barco =  (Juego.ordenador.barcoTocado(posx, posy));
			if (barco != null) {
				// Hemos dado en un barco
				Juego.panelFlotaOrdenador.pintaCasillaTocado(posx, posy);
				if (barco.getHundido()) {
					// El barco se ha hundido completamente
					Juego.persona.getSuTablero().ActualizaPosicion(posx, posy, true);
					// Lo redibujamos ardiendo
					Juego.panelFlotaOrdenador.pintaBarcoHundido(barco);
					Juego.persona.incBarcosHundidos();
					if (Juego.persona.getBarcosHundidos() >= Pantalla.CANT_TOT_BARCOS) {
						// Hemos ganado la partida
						// Preguntamos al jugador si quiere jugar otra partida
						if (JOptionPane.showConfirmDialog(
								null,
								"¡Felicidades! Ha ganado la batalla. ¿Jugamos de nuevo?", 
								"Hundir la flota", 
								JOptionPane.YES_NO_OPTION, 
								JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION) {
							LogicaJuego.inicializaEstado();
							Juego.iniciaPartida();
						}
						else
							System.exit(0);
					}

				} else {
					// Todavía quedan partes del barco a flote
					Juego.persona.getSuTablero().ActualizaPosicion(posx, posy, true);
					Juego.panelFlotaOrdenador.invalidate();
					Juego.panelFlotaOrdenador.repaint();
				}
			} else {
				// Hemos hecho un tiro al agua
				Juego.panelFlotaOrdenador.pintaCasillaAgua(posx, posy);
				Juego.persona.getSuTablero().ActualizaPosicion(posx, posy, false);
				Juego.panelFlotaOrdenador.invalidate();
				Juego.panelFlotaOrdenador.repaint();
				// El jugador pierde el turno
				LogicaJuego.cambiaTurno();
				//Juego.juegoThread.disparaOrdenador();
				return;
			}
		} else {
			// System.out.println("JUEGO - ERROR: Ya había disparado a esa casilla");
			Juego.soundThread.playSonido(SonidoThread.Sonido.ERROR);
		}
		return;
	}
	
	public void procesaPulsacion(int posx, int posy) {
		if (LogicaJuego.isTurnoPersona())
			juegaPersona(posx, posy);
	}



}
