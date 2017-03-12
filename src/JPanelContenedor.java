import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;

import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.plaf.LayerUI;

public class JPanelContenedor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4632385299158839479L;
	private JPanelLetras panelLetras = new JPanelLetras();
	private JPanelNumeros panelNumeros = new JPanelNumeros();
	private JPanelFlota panelFlota = new JPanelFlota();
	private Jugador propietario;
	private LayerUI<JPanel> layerUI = new SpotlightLayerUI();

	public JPanelFlota getPanelFlota() {
		return panelFlota;
	}

	public JPanelContenedor(Jugador propietario) {
		this.setPropietario(propietario);
		panelFlota.setPropietario(propietario);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(new BorderLayout());
		add(panelLetras, BorderLayout.WEST);
		add(panelNumeros, BorderLayout.NORTH);
		JLayer<JPanel> jlayer = new JLayer<JPanel>(panelFlota, layerUI);
		add(jlayer, BorderLayout.CENTER);
		//setVisible(true);
	}
	
	public void cambiaTurno(boolean turnoPersona) {
		if (propietario == Jugador.ORDENADOR) {
			((SpotlightLayerUI) layerUI).setMiTurno(turnoPersona);
		} else {
			((SpotlightLayerUI) layerUI).setMiTurno(!turnoPersona);
		}
		
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX();
		int y = (int) b.getY();
		Robot r;
		try {
			r = new Robot();
			r.mouseMove(x, y+1);
			r.mouseMove(x, y-1);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the propietario
	 */
	public Jugador getPropietario() {
		return propietario;
	}

	/**
	 * @param propietario
	 *            the propietario to set
	 */
	public void setPropietario(Jugador propietario) {
		this.propietario = propietario;
	}

}

