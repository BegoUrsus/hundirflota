import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class JPanelLetras extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7569585041853825459L;
	
//	JLabelImage[] labels = new JLabelImage[10];
	JLabel[] labels = new JLabel[10];
	
	private void formateaLabel(int i) {
		labels[i].setForeground(Color.WHITE);
		labels[i].setPreferredSize(new Dimension(Pantalla.ANCHO_CELDA, Pantalla.ALTO_CELDA));
		labels[i].setHorizontalAlignment(SwingConstants.CENTER);
		labels[i].setVerticalAlignment(SwingConstants.CENTER);
		labels[i].setFont(new Font("Console", Font.BOLD, 12));
	}
	
	
	public void dibujaLetras() {
		for (int i = 0; i < Pantalla.NUM_CELDAS_ANCHO; i++) {
//			String nombre = String.format("letter%c.png", 'A'+i);
//			labels[i] = new JLabelImage(nombre, nombre);
			labels[i] = new JLabel(String.format("%c", 'A'+i));
			formateaLabel(i);
			add(labels[i]);
		}
	}

	public JPanelLetras() {
		GridLayout gridLayout = new GridLayout(10, 1);
		gridLayout.setHgap(0);
		gridLayout.setVgap(0);
		dibujaLetras();
		setLayout(gridLayout);
		setBackground(new Color(0, 0, 0, 0));
		//setVisible(true);
	}

}
