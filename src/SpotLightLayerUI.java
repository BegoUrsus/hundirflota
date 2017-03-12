import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;

@SuppressWarnings("rawtypes")
class SpotlightLayerUI extends LayerUI<JPanel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8068064219774391617L;
	private boolean miTurno = false;
	private boolean mActive = false;
	private int mX, mY;

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		JLayer jlayer = (JLayer) c;
		jlayer.setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
	}

	@Override
	public void uninstallUI(JComponent c) {
		JLayer jlayer = (JLayer) c;
		jlayer.setLayerEventMask(0);
		super.uninstallUI(c);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		Graphics2D g2 = (Graphics2D) g.create();

		// Paint the view.
		super.paint(g2, c);

		if (mActive && miTurno)  {
			 //Create a radial gradient, transparent in the middle.
			 java.awt.geom.Point2D center = new
					 java.awt.geom.Point2D.Float(mX, mY);
		 
             RadialGradientPaint p = new RadialGradientPaint(
	                        center,
	                        90,
	                        new float[]{0, 1f},
	                        new Color[]{new Color(0, 0, 0, 0), new Color(0, 0, 0, 125)});

	            g2.setPaint(p);
	            g2.fillRect(0, 0, c.getWidth(), c.getHeight());
			 
			 
			 
			 
		}

		g2.dispose();
	}

	@Override
	protected void processMouseEvent(MouseEvent e, JLayer l) {
		if (e.getID() == MouseEvent.MOUSE_ENTERED)
			mActive = true;
		if (e.getID() == MouseEvent.MOUSE_EXITED)
			mActive = false;
		l.repaint();
	}

	@Override
	protected void processMouseMotionEvent(MouseEvent e, JLayer l) {
		Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), l);
		mX = p.x;
		mY = p.y;
		l.repaint();
	}

	/**
	 * @return the miTurno
	 */
	public boolean isMiTurno() {
		return miTurno;
	}

	/**
	 * @param miTurno
	 *            the miTurno to set
	 */
	public void setMiTurno(boolean miTurno) {
		this.miTurno = miTurno;
		
	}

	public void muestraCasillas(int x, int y, JLayer l) {
		mActive = true;
		mX = x;
		mY = y;
		l.repaint();
	}

	public void ocultaCasillas(JLayer l) {
		mActive = false;
		l.repaint();
	}

}