

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class JFramePrincipal extends JFrame implements WindowListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7374776709149103572L;
	
	protected JPanelJugador panelPersona;
	protected JPanelJugador panelOrdenador;
	private JPanelStatus  panelStatus;
	public static Dimension dimPantalla = new Dimension();
	
	public JFramePrincipal() {
		
		dimPantalla = getToolkit().getScreenSize();
		
		setTitle("Hundir la flota");

		Dimension dimFrame = Pantalla.dimFramePrincipal();
		setSize(dimFrame);
		setLocationRelativeTo(null);
	
		setResizable(false);
		setExtendedState(MAXIMIZED_BOTH);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(Pantalla.cargaImageIcon(this, "submarine-icon.png").getImage());
		addWindowListener(this);
	}	
	
	public void initUI() {
		JPanel panelContenido = new JPanel();
		panelContenido.setBackground(Color.DARK_GRAY);
		panelContenido.setLayout(new BorderLayout());
		setContentPane(panelContenido);

//System.out.println("initUI");		
		JPanel panelJugadores = new JPanel();
		GridLayout gl = new GridLayout(1, 2);
		gl.setHgap(Pantalla.SEPARACION);
		panelJugadores.setLayout(gl);
		panelJugadores.setBackground(Color.DARK_GRAY);
		panelPersona = new JPanelJugador("flotaaliada.png", Jugador.PERSONA);
//System.out.println("Panel persona creado");		
		panelOrdenador = new JPanelJugador("flotaenemiga.png", Jugador.ORDENADOR);
//System.out.println("Panel ordenador creado");		
		panelJugadores.add(panelPersona);
		panelJugadores.add(panelOrdenador);
		add(panelJugadores, BorderLayout.CENTER);
//System.out.println("paneles añadidos");
		JLabel labelSep1 = new JLabel();
		JLabel labelSep2 = new JLabel();
		labelSep1.setPreferredSize(Pantalla.dimPanelSeparVert());
		labelSep2.setPreferredSize(Pantalla.dimPanelSeparVert());
		add(labelSep1, BorderLayout.WEST);
		add(labelSep2, BorderLayout.EAST);
		panelStatus = new JPanelStatus(" ");
		panelStatus.setBackground(Color.WHITE);
		panelStatus.setPreferredSize(Pantalla.dimPanelStatus());
		panelStatus.setBackground(Color.LIGHT_GRAY);
		add(panelStatus, BorderLayout.SOUTH);
		
	}


	/**
	 * Devuelve el panel de la flota del jugador Persona
	 */
	public JPanelFlota getPanelFlotaPersona() {
		return panelPersona.getPanelFlota();
	}


	/**
	 * Devuelve el panel de la flota del jugador Ordenador
	 */
	public JPanelFlota getPanelFlotaOrdenador() {
		return panelOrdenador.getPanelFlota();
	}


	public JPanelStatus getPanelStatus() {
		return panelStatus;
	}

	public void setStatus (String texto) {
		panelStatus.setStatus(texto);;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (JOptionPane.showConfirmDialog(
				this, 
				"¿Seguro que quiere abandonar la partida", 
				"Hundir la flota", 
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			System.exit(0);
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
