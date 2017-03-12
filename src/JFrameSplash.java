import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class JFrameSplash extends JFrame implements Runnable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5933903899009218610L;
	private JPanel contentPane = null;
	private JProgressBar pb = null;
	ImageIcon imageInicial = null;
	ImageIcon imageJugar = null;
	JLabel labelInicial;
	JLabel labelJugar; 


	private static BufferedImage resize(BufferedImage imagen, int nuevoAncho, int nuevoAlto) {
		Image tmp = imagen.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(nuevoAncho, nuevoAlto, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	private BufferedImage cargaImagen(String nombre, int ancho, int alto) {
		BufferedImage image = null;
		InputStream stream = this.getClass().getResourceAsStream(nombre);
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resize(image, ancho, alto);
	}


	
	/**
	 * Create the frame.
	 */
	public JFrameSplash() {

		//Dimension dim = Pantalla.dimFramePrincipal();
		Dimension dim = new Dimension(900, 600);
		setSize(dim);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Pantalla.cargaImageIcon(this, "submarine-icon.png").getImage());
		setUndecorated(true);
		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);
		
		imageInicial = new ImageIcon(cargaImagen("pantallainicial.gif", dim.width+10, dim.height+10));
		imageJugar =  new ImageIcon(cargaImagen("pantallajugar.gif", dim.width+10, dim.height+10));

		labelInicial = new JLabel("", imageInicial, SwingConstants.CENTER);
		labelJugar = new JLabel("", imageJugar, SwingConstants.CENTER);

		contentPane.add(labelInicial, BorderLayout.CENTER);
		
		pb = new JProgressBar(0, 100);
		pb.setForeground(Color.GREEN);
		pb.setIndeterminate(true);
		
		contentPane.add(pb, BorderLayout.SOUTH);
	}
	
	public void oculta() {
		setVisible(false);
	}
	
	public void run() {
		setLocationRelativeTo(null);
		setVisible(true);
		pb.setIndeterminate(false);
		pb.setStringPainted(true);

		pb.setValue(10);
		pb.setString("10%");
		Juego.framePrincipal = new JFramePrincipal();
		pb.setValue(30);
		pb.setString("30%");
		Juego.framePrincipal.initUI();
		pb.setValue(50);
		pb.setString("50%");
		Juego.initJuego();
		pb.setValue(70);
		pb.setString("70%");
		Juego.iniciaPartida();
		pb.setValue(100);
		pb.setString("100%");
		labelInicial.setIcon(imageJugar);
		labelInicial.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				Juego.framePrincipal.setVisible(true);
				
			}
		});

	}

}
