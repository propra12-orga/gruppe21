package mapeditor;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileNavigator {
	private JFileChooser tilefc;
	private JFileChooser enemyfc;
	private File currentFile;

	public FileNavigator() {
		tilefc = new JFileChooser(new File("graphics/game/map"));
		this.setFilter(tilefc, ".xml", "Animation");
		this.setFilter(tilefc, ".png", "Tile");

		enemyfc = new JFileChooser(new File("graphics/game/characters"));
		this.setFilter(enemyfc, ".xml", "Enemy");
	}

	public boolean openTile() {

		int state = tilefc.showOpenDialog(null);

		if (state == JFileChooser.APPROVE_OPTION) {
			currentFile = tilefc.getSelectedFile();
			return true;
		} else {
			System.out.println("abgebrochen");
			return false;
		}
	}

	public boolean openEnemy() {

		int state = enemyfc.showOpenDialog(null);

		if (state == JFileChooser.APPROVE_OPTION) {
			currentFile = enemyfc.getSelectedFile();
			return true;
		} else {
			System.out.println("abgebrochen");
			return false;
		}

	}

	public void setFilter(final JFileChooser jf, final String end,
			final String text) {
		jf.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory()
						|| f.getName().toLowerCase().endsWith(end);
			}

			@Override
			public String getDescription() {
				return text;
			}

		});
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

}
