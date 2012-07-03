package mapeditor;

public class FileActions {

	public static void perform(String action, Editor ed) {
		if (action.equals("saveFile")) {

			saveFile();
		}
		if (action.equals("openFile")) {
			openFile();
		}
		if (action.equals("newFile")) {
			newFile();
			ed.canvas.openNew();
		}

	}

	public static void saveFile() {
		System.out.println("file saved");
	}

	public static void openFile() {
		System.out.println("file open");
	}

	public static void newFile() {
		System.out.println("new file");
	}

}
