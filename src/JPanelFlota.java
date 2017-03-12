
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class JPanelFlota extends JPanel{
	
	FlotaPropia flota;		// Nos dice las casillas en las que hay un barco
	Jugador propietario;
	Point coordDisparo;
    Cursor cursorInvisible = null;
	Cursor cursorPeriscopio = null;

	/**
	 * Escucha a los eventos del ratón de las JLabels que contienen las celdas de la rejilla
	 */
	class JLabelListener extends MouseInputAdapter{
		int x;
		int y;
		
		JLabelListener(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/** 
		 * Devuelve las coordenadas correspondientes a la casilla que ha generado el evento
		 * @param e
		 * @return
		 */
		private Point cogePosicion(MouseEvent e) {
	    	int posx = -1;
	    	int posy = -1;
	    	String label = ((JLabel)e.getComponent()).getName();
	    	if (label == null || label.isEmpty())
	    		return new Point(posx, posy);
	    	StringTokenizer st = new StringTokenizer(label, "_");
	        while (st.hasMoreTokens()) {
	            String token = st.nextToken();
	            if (posy == -1)
	            	posy =token.charAt(0) - 'A';
	            else
	            	if (posx == -1)
	            		posx = Integer.parseInt(token);
	        }
	        return new Point(x, y);
		}
	 
	    public void mouseMoved(MouseEvent e) {
	    	coordDisparo = cogePosicion(e);
	    	if (propietario == Jugador.PERSONA) {
	    		//System.out.println("Dibuja cursor " + coordDisparo.x + "  " + coordDisparo.y);
	    	}
	    }
	 
	    public void mouseDragged(MouseEvent e) {
	    }
	 
	    /**
	     *	Se ha pulsado el ratón en una de las celdas
	     *	Si ha sido en la flota del ordenador, se procesa el disparo 
	     */
	    public void mouseClicked(MouseEvent e) {
	    	coordDisparo = cogePosicion(e);
	    	if (propietario == Jugador.ORDENADOR) {
	    		if (LogicaJuego.isTurnoPersona())
	    			Juego.persona.procesaPulsacion(coordDisparo.x, coordDisparo.y);
	    	}
	    	else
	    		Juego.soundThread.playSonido(SonidoThread.Sonido.ERROR);
	    		
	
	    }
	 
	    public void mouseEntered(MouseEvent e) {
	    }
	 
	    public void mouseExited(MouseEvent e) {
	    }
	 
	    public void mousePressed(MouseEvent e) {
	    }
	 
	    public void mouseReleased(MouseEvent e) {
	    }
	 
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 9060524660319736427L;

	JLabelImage[][] labels = new JLabelImage[Pantalla.NUM_CELDAS_ALTO + 1][Pantalla.NUM_CELDAS_ANCHO + 1];
	int contMovimientos = 0;
	
	
	
	public void customCursor() {
	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Image cursorImage = toolkit.getImage("target.gif");
	    Point cursorHotSpot = new Point(0,0);
	    Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
	    setCursor(customCursor);
	}
	
	/**
	 * Hacemos que las etiquetas de las celdas respondan generen eventos del ratón
	 */
	public void asignaMouseListeners() {
		String nombre = "";
		for (int fila = 0; fila < Pantalla.NUM_CELDAS_ALTO; fila++) {
			for (int columna = 0; columna < Pantalla.NUM_CELDAS_ANCHO; columna++) {
				nombre = String.format("%c_%d", 'A'+fila, columna);
				labels[fila][columna].setName(nombre);
				labels[fila][columna].addMouseListener(new JLabelListener(columna, fila));
				labels[fila][columna].addMouseMotionListener(new JLabelListener(columna, fila));
			}
		}
	}

	/**
	 * dibujamos el mar en todas las casillas
	 */
	public void dibujaOceano() {
		for (int y=0; y < Pantalla.NUM_CELDAS_ALTO; y++) {
			for (int x=0; x < Pantalla.NUM_CELDAS_ANCHO; x++) {
				labels[y][x] = new JLabelImage("agua.png", "agua_fallo.png", "agua_fallo.png", 0);
				add(labels[y][x]);
			}
		}
	}
	
	public void limpiaOceano() {
		for (int y=0; y < Pantalla.NUM_CELDAS_ALTO; y++) {
			for (int x=0; x < Pantalla.NUM_CELDAS_ANCHO; x++) {
				labels[y][x].asignaImagenes("agua.png", "agua_fallo.png", "agua_fallo.png", true); 
				labels[y][x].setImagen(0);
			}
		}
		invalidate();
		repaint();
	}

	/**
	 * Le asignamos a las celdas coorespondientes a un barco, 
	 * las imágenes que tiene que mostrar según el tipo de barco 
	 * y el propietario que tenga.
	 * @param barco
	 */
	public void asignaImagenesBarco(Barco barco) {
		for (int i = 0; i < barco.getLongitud(); i++) {
			int posX = barco.getPosicion(i).x;
			int posY = barco.getPosicion(i).y;
			labels[posY][posX].asignaImagenes( 
					barco.getNomImagenOK(i), 
					barco.getNomImagenTocado(i),
					barco.getNomImagenHundido(i),
					barco.isHorizontal());
		}
	}
		
		
	public void dibujaImagen(int x, int y, int tipo) {
		labels[y][x].setImagen(tipo);
		//labels[y][x].invalidate();
		//labels[y][x].repaint();
	}
	
	public void pintaCasillaAgua(int x, int y) {
		dibujaImagen(x, y, 1);
		Juego.soundThread.playSonido(SonidoThread.Sonido.AGUA);
	}
	
	public void pintaCasillaTocado(int x, int y) {
		dibujaImagen(x, y, 1);
		Juego.soundThread.playSonido(SonidoThread.Sonido.EXPLOSION);
	}
	
	public void pintaBarcoOK(Barco barco) {
		if (propietario == Jugador.ORDENADOR)
			return;
		for (int i = 0; i < barco.getLongitud(); i++) {
			int posX = barco.getPosicion(i).x;
			int posY = barco.getPosicion(i).y;
			dibujaImagen(posX, posY, 0);
		}
	}
	
	public void pintaBarcoHundido(Barco barco) {
		for (int i = 0; i < barco.getLongitud(); i++) {
			int posX = barco.getPosicion(i).x;
			int posY = barco.getPosicion(i).y;
			dibujaImagen(posX, posY, 2);
		}
		Juego.soundThread.playSonido(SonidoThread.Sonido.HUNDIDO);
	}
	
	
	private void creaCursores() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Point hotSpot = new Point(0,0);
	    BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT); 
	    cursorInvisible = toolkit.createCustomCursor(cursorImage, hotSpot, "Cursor Invisible");        
		cursorPeriscopio = new Cursor(Cursor.CROSSHAIR_CURSOR);
	    setCursor(cursorInvisible);		
	}
	
	public void inicializa() {
		GridLayout gridLayout = new GridLayout(10, 10);
		gridLayout.setHgap(1);
		gridLayout.setVgap(1);
		setLayout(gridLayout);
		setBackground(Color.DARK_GRAY);
		dibujaOceano();
		contMovimientos = 0;
		creaCursores();
	}

	public JPanelFlota() {
		
		inicializa();
		
	}
	
	public JPanelFlota(Jugador propietario) {
		this();
		setPropietario(propietario);
		
	}
	
	public void setPropietario(Jugador propietario) {
		this.propietario = propietario;
		if (propietario == Jugador.ORDENADOR)
			setCursor(cursorPeriscopio);
		else {
			setCursor(cursorInvisible);
		}
	}
	
	public void activaRaton(boolean estado) {
		if (propietario == Jugador.ORDENADOR) {
			if (estado)
				setCursor(cursorPeriscopio);
			else
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}
	}


}
