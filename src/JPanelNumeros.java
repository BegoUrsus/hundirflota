import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class JPanelNumeros extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7569585041853825459L;
	
	//JLabelImage[] labels = new JLabelImage[11];
	JLabel[] labels = new JLabel[11];
	
	private void formateaLabel(int i) {
		labels[i].setForeground(Color.WHITE);
		labels[i].setPreferredSize(new Dimension(Pantalla.ANCHO_CELDA, Pantalla.ALTO_CELDA));
		labels[i].setHorizontalAlignment(SwingConstants.CENTER);
		labels[i].setVerticalAlignment(SwingConstants.CENTER);
		labels[i].setFont(new Font("Console", Font.BOLD, 12));
	}
	public void dibujaNumeros() {
		//labels[0] = new JLabelImage("vacio.png", "vacio.png");
		labels[0] = new JLabel();
		formateaLabel(0);
		add(labels[0]);
		for (int i = 0; i < Pantalla.NUM_CELDAS_ANCHO; i++) {
			//String nombre = String.format("number%d.png", i);
			//labels[i + 1] = new JLabelImage(nombre, nombre);
			labels[i + 1] = new JLabel(String.format("%d", i));
			formateaLabel(i+1);
			add(labels[i + 1]);
		}
	}

	public JPanelNumeros() {
		GridLayout gridLayout = new GridLayout(1, 11);
		gridLayout.setHgap(0);
		gridLayout.setVgap(0);
		setLayout(gridLayout);
		setBackground(new Color(0, 0, 0, 0));
		//setVisible(true);

		dibujaNumeros();
	}

}
