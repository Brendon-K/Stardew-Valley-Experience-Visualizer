import javax.swing.JButton;
import javax.swing.JFileChooser;

public class Main {
	public static void main(String[] args) {
		JButton open = new JButton();
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File(System.getenv("AppData") + "/StardewValley/Saves"));
		fc.setDialogTitle("Choose your character");
		if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
			//
		}
		String p = fc.getSelectedFile().getAbsolutePath();

		MainFrame frame = new MainFrame(p);
	}
}