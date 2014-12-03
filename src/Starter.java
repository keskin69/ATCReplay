import gui.MainWindow;
import utils.Config;

public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// load properties file
		Config.getInstance(args[0]);
		MainWindow.getInstance();

		String ver = System.getProperty("java.vm.version");
		System.out.println("Java VM Version is " + ver);
	}
}
