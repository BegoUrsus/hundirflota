
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

public class JPanelJugador extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1250876906504473393L;

	private JPanelContenedor panelContenedor;
	private Jugador propietario;
	
	public JPanelContenedor getPanelContenedor() {
		return panelContenedor;
	}

	public JPanelFlota getPanelFlota() {
		return panelContenedor.getPanelFlota();
	}
	
	
	public JPanelJugador(String imageName, Jugador propietario) {
		
		this.propietario = propietario;

		setLayout(new BorderLayout());
		setBackground(new Color(0, 0, 0, 0));
		
		
		//ImageIcon imagen = Pantalla.cargaImageIcon(this, imageName);
		Dimension dimTitulo = new Dimension(Pantalla.dimPanelJugador().width, Pantalla.ALTO_TITULO);
		JLabel labelTitulo = new JLabel();
		
		labelTitulo.setPreferredSize(dimTitulo);
		labelTitulo.setOpaque(true);
		labelTitulo.setBackground(Color.DARK_GRAY);
		labelTitulo.setFont(new Font("Impact", Font.ITALIC, 24));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setForeground(Color.LIGHT_GRAY);
		labelTitulo.setText(propietario == Jugador.PERSONA ? "Flota aliada" : "Flota enemiga");
		add(labelTitulo, BorderLayout.NORTH);
		
		// En el centro ponemos otro JPanel que contendrá:
		// 	en el norte: los números
		//  en el oeste: las letras en vertical
		//  en el centro: el panel con la flota
		panelContenedor = new JPanelContenedor(this.propietario);
		add(panelContenedor, BorderLayout.CENTER);
		
	}
	
	public void cambiaTurno(boolean turnoPersona) {
		panelContenedor.cambiaTurno(turnoPersona);
		if (propietario == Jugador.ORDENADOR)
			getPanelFlota().activaRaton(turnoPersona);
	}



}
