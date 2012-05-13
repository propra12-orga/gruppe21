package audio;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import main.GameConstants;

/*
 * to be used for playing midi sounds
 * No exception handling at the moment!
 */
public class MidiGameSound extends GameSound {

	private String filename;
	private Sequence seq;
	private Sequencer sequencer;

	public MidiGameSound(String filename, Sequencer sequencer) {
		this.filename = filename;
		this.sequencer = sequencer;
		loadSound();
	}
	@Override
	public void loadSound() {
		try {
			seq = MidiSystem.getSequence(new File(GameConstants.SOUNDS_DIR
					+ filename));
		} catch (InvalidMidiDataException e) {
		} catch (IOException e) {
		} catch (Exception e) {	}
	}
	@Override
	public void play(boolean loop) {
		if ((sequencer != null) && (seq != null)) {
			try {
				sequencer.setSequence(seq);
				sequencer.setTickPosition(0);
				if (loop) {
					sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
					// sequencer.setLoopStartPoint(0);
					// sequencer.setLoopEndPoint(seq.getTickLength());
				}
				sequencer.start();
			} catch (InvalidMidiDataException e) {
			}
		}
	}
	@Override
	public void stop() {
		if ((sequencer != null) && (seq != null)) {
			if (!sequencer.isRunning())
				sequencer.start();
			sequencer.setTickPosition(0);
		}
	}
	@Override
	public void pause() {
		if ((sequencer != null) && (seq != null)) {
			if (sequencer.isRunning())
				sequencer.stop();
		}
	}
	@Override
	public void resume() {
		if ((sequencer != null) && (seq != null))
			sequencer.start();
	}
}
