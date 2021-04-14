package mondradiko.jbundler.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import mondradiko.jbundler.Configuration;
import mondradiko.jbundler.JBundler;
import mondradiko.jbundler.codegen.Codegen;

public class Project {

	static File directory;

	public static void newProject(Configuration config) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Where would you like to setup a project environment?");
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			directory = chooser.getSelectedFile();
			try {
				generateProject(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void openProject(Configuration config) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogTitle("When is the project's mondradiko.prof file?");
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION
				&& chooser.getSelectedFile().getName().equalsIgnoreCase("mondradiko.proj")) {
			directory = chooser.getSelectedFile().getParentFile();
		} else {
			JOptionPane.showMessageDialog(null, "Not a valid project");
		}
	}

	public static void generateProject(Configuration config) throws IOException {
		new Thread() {
			public void run() {
				BasicDialog dialog = new BasicDialog();
				dialog.setDialogMessage("Creating folders");
				dialog.showMe();
				File mainFile = new File(directory, "mondradiko.proj");
				if (!mainFile.exists()) {
					try {
						mainFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					} // used to browse for a project, contains nothing
				}
				new File(directory, "bin").mkdir();
				new File(directory, "out").mkdir();
				File src = new File(directory, "src");
				src.mkdir();

				dialog.setDialogMessage("Launching codegen");
				Codegen.run(config, directory);

				dialog.setDialogMessage("Creating manifest");
				File manifest = new File(src, "bundler-manifest.toml");
				if (!manifest.exists()) { // don't overwrite one if it already exists
					try {
						PrintStream manifestOut = new PrintStream(new FileOutputStream(manifest));
						manifestOut.println("[bundle]");
						manifestOut.println("name = \"change me\"");
						manifestOut.println("compression = \"LZ4\"");
						manifestOut.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}

				if (config.getNpmVersion() != null && !config.getNpmVersion().isEmpty()
						&& Character.isDigit(config.getNpmVersion().charAt(0))) {
					dialog.setDialogMessage("Generating assemblyscript/typescript environment");
					try {
						Process p = JBundler.executeCommandInDirectory("npm", new String[] {"install", "assemblyscript"}, directory).start();
						int exitCode = p.waitFor();
						if (exitCode == 0) {
							File tsConfig = new File(directory, "tsconfig.json");
							File gitIgnore = new File(directory, ".gitignore");
							PrintStream tsConfigWriter = new PrintStream(tsConfig);
							tsConfigWriter.print(
									"{\n" + 
									"  \"extends\": \"assemblyscript/std/assembly.json\",\n" + 
									"  \"compilerOptions\": {\n" + 
									"    \"baseUrl\": \"lib/\",\n" + 
									"    \"paths\": {}\n" + 
									"  }\n" + 
									"}");
							tsConfigWriter.close();
							
							PrintStream gitIgnoreWriter = new PrintStream(gitIgnore);
							gitIgnoreWriter.print("bin/\nlib/\nnode_modules/\nout/\n");
							gitIgnoreWriter.close();
						} else {
							dialog.setDialogMessage("Error generating assemblyscript/typescript environment. Check the console");
							Thread.sleep(3000);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				dialog.hideMe();
			}
		}.start();
	}

	public static boolean projectIsOpen() {
		return directory != null;
	}

	public static File getProjectDirectory() {
		return directory;
	}
}
