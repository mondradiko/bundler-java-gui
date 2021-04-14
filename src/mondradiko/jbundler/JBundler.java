package mondradiko.jbundler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JBundler {
	
	public static boolean isWindows = false;
	public static File configDirectory = null;
	private static Configuration config;

	public static void main(String[] args) throws IOException {
		isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
		
		if (isWindows) {
			configDirectory = new File(System.getProperty("user.home")+"\\AppData\\Roaming\\mondradiko-bundler");
		} else {
			configDirectory = new File(System.getProperty("user.home")+"/.mondradiko-bundler");
		}
		
		configDirectory.mkdir();
		
		config = new Configuration();
		config.loadConfiguration(new File(configDirectory, "config.dat"));
		
		new MainWindow(config).show();
	}

	public static Process executeCommand(String command) throws IOException {
		return Runtime.getRuntime().exec((isWindows ? "cmd /c " : "") + command);
	}
	
	public static ProcessBuilder executeCommandInDirectory(String command, String[] args, File directory) throws IOException {
		List<String> commandList = new ArrayList<String>();
		
		if (isWindows) {
			commandList.add("cmd");
			commandList.add("/c");
		}
		
		commandList.add(command);
		commandList.addAll(Arrays.asList(args));
		
		ProcessBuilder builder = new ProcessBuilder(commandList.toArray(new String[0]));
		builder.directory(directory);
		
		return builder;
	}
	
	public static ProcessBuilder executeCommandInDirectoryNewTerminal(String command, String[] args, File directory) throws IOException {
		List<String> commandList = new ArrayList<String>();
		
		if (isWindows) {
			commandList.add("cmd");
			commandList.add("/c");
			commandList.add("start");
		}
		
		commandList.add(command);
		commandList.addAll(Arrays.asList(args));
		
		if (isWindows) {
			commandList.add("&&");
			commandList.add("PAUSE");
		}
		
		ProcessBuilder builder = new ProcessBuilder(commandList.toArray(new String[0]));
		builder.directory(directory);
		
		return builder;
	}
	
	public static void saveConfig() {
		try {
			config.saveConfiguration(new File(configDirectory, "config.dat"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
