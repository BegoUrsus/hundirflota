import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Pantalla {

	/**
	 * Constantes generales del programa
	 */
	
	public static final int NUM_CELDAS_ANCHO = 10; // N�mero de celdas del tablero en x
	public static final int NUM_CELDAS_ALTO = 10; // N�mero de celdas deltablero en y
	public static final int CANT_TOT_BARCOS = 7; // N�mero total de barcos por jugador
	public static final int MAX_TAM_BARCO = 5; // Longitud m�xima que puede ocupar un barco
	public static final int ALTO_TITULO = 40; // Altura del t�tulo/nombre de cada jugador (Ordenador y Pesona)
	public static final int ALTO_STATUS = 40; // Altura del status
	public static final int ANCHO_CELDA = 40;
	public static final int ALTO_CELDA = 40;
	public static final int SEPARACION = 15;
	
	public static ImageIcon cargaImageIcon(Object origen, String nomImage) {
		BufferedImage image = null;
		InputStream stream = origen.getClass().getResourceAsStream(nomImage);
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ImageIcon(image);
	}
	
	/**
	 * Devuelve las dimensiones de los paneles que contienen la flota
	 * Equivale al ancho/alto de cada una de las celdas, por el n�mero de celdas
	 * m�s un punto por cada l�nea de separaci�n entre las celdas
	 * @return
	 */
	public static Dimension dimPanelFlota() {
		return new Dimension(
				ANCHO_CELDA * NUM_CELDAS_ANCHO + (NUM_CELDAS_ANCHO - 1),
				ALTO_CELDA  * NUM_CELDAS_ALTO  + (NUM_CELDAS_ALTO  - 1));
	}
	
	
	/**
	 * Devuelve las dimensiones de los paneles que contienen los n�meros (horizontal)
	 * Equivale al ancho de cada una de las celdas, por el n�mero de celdas
	 * y la altura al alto de la celda,
	 * m�s un punto por cada l�nea de separaci�n entre las celdas
	 * @return
	 */
	public static Dimension dimPanelNumeros() {
		return new Dimension(
				ANCHO_CELDA * NUM_CELDAS_ANCHO + (NUM_CELDAS_ANCHO - 1),
				ALTO_CELDA  				   + 1);
	}
	
	
	/**
	 * Devuelve las dimensiones de los paneles que contienen las letras (vertical)
	 * Equivale al alto de cada una de las celdas, por el n�mero de celdas m�s 1
	 * y la anchura al ancho de la celda m�s 1,
	 * m�s un punto por cada l�nea de separaci�n entre las celdas
	 * @return
	 */
	public static Dimension dimPanelLetras() {
		return new Dimension(
				ANCHO_CELDA												+ 1,
				ALTO_CELDA  * NUM_CELDAS_ALTO  + (NUM_CELDAS_ALTO  - 1) + (ALTO_CELDA + 1));
	}
	
	

	/**
	 * Devuelve las dimensiones de los paneles la flota y los n�meros y letras
	 * Equivale al ancho/alto del panel flota, m�s el ancho/alto de una celda
	 * donde se sit�an los n�meros
	 * m�s un punto por cada l�nea de separaci�n entre las celdas
	 * @return
	 */
	public static Dimension dimPanelContenedor() {
		Dimension dimFlota = dimPanelFlota();
		return new Dimension(
				dimFlota.width  + ANCHO_CELDA + 1,
				dimFlota.height + ALTO_CELDA  + 1);
	}
	
	
	/**
	 * Devuelve las dimensiones de los paneles de cada uno de los jugadores
	 * es decir, el panel del t�tulo y el del contenedor
	 * @return
	 */
	public static Dimension dimPanelJugador() {
		Dimension dimContenedor = dimPanelContenedor();
		return new Dimension(
				dimContenedor.width,
				dimContenedor.height + ALTO_CELDA  + 1);
	}
	
	public static Dimension dimPanelSeparHorz() {
		Dimension dimJugador = dimPanelJugador();
		return new Dimension(
				dimJugador.width,
				SEPARACION);
	}
	
	public static Dimension dimPanelSeparVert() {
		Dimension dimJugador = dimPanelJugador();
		return new Dimension(
				SEPARACION,
				dimJugador.height);
	}
	
	public static Dimension dimPanelStatus() {
		Dimension dimJugador = dimPanelJugador();
		return new Dimension(
				dimJugador.width * 2,
				ALTO_STATUS);
	}
	
	public static Dimension dimFramePrincipal() {
		Dimension dimJugador = dimPanelJugador();
		return new Dimension(
				dimJugador.width * 2 + SEPARACION * 3,
				dimJugador.height + ALTO_STATUS + SEPARACION);
		
	}
	
	


}
