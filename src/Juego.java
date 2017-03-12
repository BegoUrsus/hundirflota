
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JWindow;

public class Juego {

	static Persona persona = new Persona(Jugador.PERSONA); // jugador Persona
	static Ordenador ordenador = new Ordenador(Jugador.ORDENADOR); // jugador Ordenandor
	static JFramePrincipal framePrincipal = null;
	static JPanelFlota panelFlotaPersona = null;
	static JPanelFlota panelFlotaOrdenador = null;
	static JPanelStatus panelStatus = null;
	public static SonidoThread soundThread;
	//public static OrdenadorThread juegoThread;
	public static LogicaJuego juegoThread;
	public static JWindow splah;
	public static Random random = new Random();

	public void customCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("target.gif");
		Point cursorHotSpot = new Point(0, 0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		framePrincipal.setCursor(customCursor);
	}

	public static void initJuego() {
		// Creamos un nuevo hilo para el sonido, que comprueba cada 10 ms, si ha
		// de sonar
		soundThread = new SonidoThread();
		soundThread.start();

		//juegoThread = new OrdenadorThread();
		juegoThread = new LogicaJuego();
		juegoThread.start();

		panelFlotaPersona = framePrincipal.getPanelFlotaPersona();
		panelFlotaOrdenador = framePrincipal.getPanelFlotaOrdenador();

		panelFlotaOrdenador.asignaMouseListeners();
		panelFlotaPersona.asignaMouseListeners();

		panelStatus = framePrincipal.getPanelStatus();

		//framePrincipal.setVisible(true);
		LogicaJuego.fijaTurno(Jugador.PERSONA);
		
		//iniciaPartida();
	}
	
	public static void iniciaPartida() {
		// Limpiamos los tableros
		
		panelFlotaPersona.limpiaOceano();
		panelFlotaOrdenador.limpiaOceano();
		
		// Creamos los barcos de los jugadores
		persona.generaBarcosRadom();
		ordenador.generaBarcosRadom();
		
		// Son mis chuletas para ver si el juego funciona correctamente
		//persona.dibujaTableroPorConsola("JUGADOR");
		//ordenador.dibujaTableroPorConsola("ORDENADOR");

		for (int i = 0; i < Pantalla.CANT_TOT_BARCOS; i++) {
			panelFlotaPersona.asignaImagenesBarco(persona.getBarco(i));
			panelFlotaPersona.pintaBarcoOK(persona.getBarco(i));
		}

		for (int i = 0; i < Pantalla.CANT_TOT_BARCOS; i++) {
			panelFlotaOrdenador.asignaImagenesBarco(ordenador.getBarco(i));
		}
		LogicaJuego.fijaTurno(Jugador.PERSONA);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				Thread splashThread = new Thread(new JFrameSplash());
				splashThread.start();
				

			}
		});
		
	} 

}
