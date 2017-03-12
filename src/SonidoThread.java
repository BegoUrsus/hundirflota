import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SonidoThread extends Thread {
	boolean abandona;
	boolean tocaAhora;
	static boolean sonidoDisponible;

	public static enum Sonido {EXPLOSION, AGUA, HUNDIDO, ERROR}
	
	SonidoThread() {
		abandona = false;
		tocaAhora = false;
		sonidoDisponible = true;
	}

	public void playNow() {
		tocaAhora = true;
	}

	public void quit() {
		abandona = true;
	}

	private static final String NOM_SONIDOS[] = {"explosion.wav", "agua.wav", "final.wav", "error.wav"}; 
	private static final HashMap<Integer, URL> mapSonidos = new HashMap<>();
	private static Queue<Integer> colaSonidos = new LinkedList<Integer>();	
	
	private void cargaSonidos() {
		if (!sonidoDisponible)
			return;
		int contador = 0;
		for (String nombre: NOM_SONIDOS) {
			URL url = this.getClass().getClassLoader().getResource(nombre);
			mapSonidos.put(contador,  url);
			contador++; 
		}
	}


	/**
	 * Añade un sonido a la cola
	 * @param soundId
	 */
	public static void addSonido(int soundId) {
		if (!sonidoDisponible)
			return;
	  colaSonidos.add(soundId);
	}
	
	public static void addSonido(Sonido sonido) {
		int soundID = sonido.ordinal();
		addSonido(soundID);
	}
	
	/**
	 * Añade un sonido a la cola y da la orden de tocarlo
	 * @param soundID
	 */
	public void playSonido(int soundID) {
		if (!sonidoDisponible)
			return;
		addSonido(soundID);
		playNow();
	}
	
	public void playSonido(Sonido sonido) {
		int soundID = sonido.ordinal();
		playSonido(soundID);
	}
	
	public static void reproduceSonidos() {
		if (!sonidoDisponible)
			return;
		while (!colaSonidos.isEmpty()) {
			Integer soundID = colaSonidos.remove();
			AudioInputStream audioIn = null;
			try {
				audioIn = AudioSystem.getAudioInputStream(mapSonidos.get(soundID));
				// Cogemos un clip de audio
				Clip clip = AudioSystem.getClip();
				// Abrimos el clip y le cargamos el audio del fichero
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
	   public static boolean isLineSupported(boolean bPlayback) {
		   
			//System.out.println("Mixers disponibles:");
			Mixer.Info[]	aInfos = AudioSystem.getMixerInfo();
			for (int i = 0; i < aInfos.length; i++)
			{
				Mixer mixer = AudioSystem.getMixer(aInfos[i]);
				Line.Info lineInfo = new Line.Info(bPlayback ?
												   SourceDataLine.class :
												   TargetDataLine.class);
				if (mixer.isLineSupported(lineInfo))
				{
					//System.out.println(aInfos[i].getName());
				}
			}
			if (aInfos.length == 0)
			{
				//System.out.println("[Ningún mixer disponible]");
				return false;
			}
			return true;
	  }
		   
	public void run() {
		if (!sonidoDisponible)
			return;
		if (!isLineSupported(true)) {
			sonidoDisponible = false;
			return;
		}
		cargaSonidos();
		while (!abandona) {
			// esperamos 10 ms y miramos si se tienen que reproducir los sonidos
			// que hay en la cola
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				return;
			}

			// Si tenemos que reproducir los sonidos, lo hacemos
			if (tocaAhora) {
				tocaAhora = false;
				reproduceSonidos();
			}

			// volvemos y esperamos otros 10 ms
		}
	}

}
