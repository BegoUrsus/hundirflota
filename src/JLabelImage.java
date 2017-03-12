import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelImage extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9171853555746174457L;

	
	String nombreOK = "vacio.png";
	String nombreTocado = "vacio.png";
	String nombreHundido = "vacio.png";
	BufferedImage imagenOK = null;
	BufferedImage imagenTocado = null;
	BufferedImage imagenHundido = null;

	/** Le cambia el tamaño a una imagen
	 * 
	 * @param imagen
	 * @param nuevoAncho
	 * @param nuevoAlto
	 * @return
	 */
	public static BufferedImage resize(BufferedImage imagen, int nuevoAncho, int nuevoAlto) {
		Image tmp = imagen.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(nuevoAncho, nuevoAlto, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	/** Rota una imagen 90º
	 * 
	 * @param imagen
	 * @return
	 */
	public static BufferedImage rotate(BufferedImage imagen) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(90), imagen.getWidth() / 2, imagen.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		imagen = op.filter(imagen, null);
		return imagen;
	}

	/** Carga una imagen de los recursos
	 * 
	 * @param nombre
	 * @return
	 */
	public BufferedImage cargaImagen(String nombre) {
		BufferedImage image = null;
		InputStream stream = this.getClass().getResourceAsStream(nombre);
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;

	}
	
	/** Carga una imagen de los recursos y la adapta al tamaño
	 * 
	 * @param filename
	 * @param ancho
	 * @param alto
	 * @return
	 */
	
	public BufferedImage cargaImagen(String nombre, int ancho, int alto) {
		BufferedImage image = null;
		InputStream stream = this.getClass().getResourceAsStream(nombre);
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resize(image, ancho, alto);

	}


	public void setImagen(int tipo) {
		if (tipo == 0)
			setIcon(new ImageIcon(imagenOK));
		else if (tipo == 1)
			setIcon(new ImageIcon(imagenTocado));
		else
			setIcon(new ImageIcon(imagenHundido));
	}
	
	public void asignaImagenes(String nombreOK, String nombreTocado, String nombreHundido, boolean horizontal) {
		this.nombreOK = nombreOK;
		this.nombreTocado = nombreTocado;
		this.nombreHundido = nombreHundido;
		imagenOK = cargaImagen(nombreOK, Pantalla.ANCHO_CELDA, Pantalla.ALTO_CELDA);
		imagenTocado = cargaImagen(nombreTocado, Pantalla.ANCHO_CELDA, Pantalla.ALTO_CELDA);
		imagenHundido = cargaImagen(nombreHundido, Pantalla.ANCHO_CELDA, Pantalla.ALTO_CELDA);
		if (!horizontal) {
			imagenOK = rotate(imagenOK);
			imagenTocado = rotate(imagenTocado);
			imagenHundido = rotate(imagenHundido);
		}
	}
	
	public JLabelImage(String nombreOK, String nombreTocado, String nombreHundido, int tipo) {
		asignaImagenes(nombreOK, nombreTocado, nombreHundido, true);
		setImagen(tipo);
	}
	
	

}
