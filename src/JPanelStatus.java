import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class JPanelStatus extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5068428330207932091L;
	private JLabel labelStatus;
	
	public JPanelStatus(String texto) {
		labelStatus = new JLabel(texto, SwingConstants.CENTER);
		labelStatus.setPreferredSize(Pantalla.dimPanelStatus());
		add(labelStatus);
	}
	
	/**
	 * @return el objeto etiqueta labelStatus
	 */
	public JLabel getLabelStatus() {
		return labelStatus;
	}
	/**
	 * @param labelStatus the labelStatus to set
	 */
	public void setStatus(String texto) {
		//labelStatus.setText(texto);
	}




}
