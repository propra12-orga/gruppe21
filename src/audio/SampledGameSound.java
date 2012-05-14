package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.GameConstants;

/*
 * to be used for playing sampled audio
 * needs exception handling
 */
public class SampledGameSound extends GameSound implements LineListener {

	private String filename;
	private Clip sound;
	private boolean looping;

	public SampledGameSound(String filename) {
		this.filename = filename;
		loadSound();
	}
	@Override
	public void loadSound() {
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(new File(
					GameConstants.SOUNDS_DIR + filename));
			AudioFormat format = in.getFormat();
			if ((format.getEncoding() == AudioFormat.Encoding.ULAW)
					|| (format.getEncoding() == AudioFormat.Encoding.ALAW)) {
				AudioFormat newFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						format.getSampleRate(),
						format.getSampleSizeInBits() * 2, format.getChannels(),
						format.getFrameSize() * 2, format.getFrameRate(), true);
				in = AudioSystem.getAudioInputStream(newFormat, in);
				System.out.println("Converted Audio format: " + newFormat);
				format = newFormat;
			}
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			sound = (Clip) AudioSystem.getLine(info);
			sound.addLineListener(this);
			sound.open(in);
			in.close();
		} catch (UnsupportedAudioFileException audioException) {
		} catch (LineUnavailableException noLineException) {
		} catch (IOException ioException) {
		} catch (Exception e) {
		}
	}

	@Override
	public void update(LineEvent event) {
		if (event.getType() == LineEvent.Type.STOP) {
			sound.stop();
			sound.setFramePosition(0);
			if (looping) {
				sound.start();
			}
		}

	}
	
	public void close() {
		if (sound != null) {
			sound.stop();
			sound.close();
		}
	}
	@Override
	public void play(boolean loop) {
		if (sound != null) {
			looping = loop;
			sound.start();
		}
	}
	@Override
	public void stop() {
		if (sound != null) {
			looping = false;
			sound.stop();
			sound.setFramePosition(0);
		}
	}
	@Override
	public void pause() {
		if (sound != null) {
			sound.stop();
		}
	}
	@Override
	public void resume() {
		if (sound != null) {
			sound.start();
		}
	}


}
