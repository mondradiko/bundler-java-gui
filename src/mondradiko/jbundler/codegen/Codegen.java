package mondradiko.jbundler.codegen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import mondradiko.jbundler.Configuration;
import mondradiko.jbundler.JBundler;
import mondradiko.jbundler.scenes.Codegenning;

public class Codegen {

	private static boolean isCancelled = false;

	public static void run(Configuration config, File inDirectory) {
		File targetDirectory = new File(inDirectory, "lib");
		targetDirectory.mkdir();
		
		
		isCancelled = false;

		if (config.getPythonVersion() == null || config.getPythonVersion().isEmpty()
				|| config.getPythonVersion().equalsIgnoreCase("not detected")) {
			JOptionPane.showMessageDialog(null, "Cannot run codegen as python was not detected");
			return;
		}

		if (config.getCodegenDirectory() == null || config.getCodegenDirectory().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Cannot run codegen as codegen directory was not specified");
			return;
		}

		Codegenning dialog = new Codegenning(new Runnable() {
			public void run() {
				isCancelled = true;
			}
		});
		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);

		new Thread() {
			public void run() {
				List<File> toGenerate = findTomlFiles(new File(config.getCodegenDirectory()));
				int size = toGenerate.size();

				int prefixLength = new File(config.getCodegenDirectory()).getAbsolutePath().length();

				for (int i = 0; i < toGenerate.size(); i++) {
					if (isCancelled) {
						break;
					}
					File source = toGenerate.get(i);
					String path = source.getAbsolutePath().substring(prefixLength);
					path = path.substring(0, path.lastIndexOf(".")) + ".ts";
					File destination = new File(targetDirectory, path);

					generateTypescript(new File(config.getCodegenDirectory()), source, destination);

					final String finalPath = path;
					final int index = i;
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dialog.setProgress(finalPath, index, size);
						}
					});
				}
				
				dialog.setVisible(false);
			}
		}.start();
	}

	private static List<File> findTomlFiles(File directory) {
		ArrayList<File> files = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.getName().equalsIgnoreCase("toml"))
				continue;

			if (file.isDirectory()) {
				files.addAll(findTomlFiles(file));
			} else if (file.getName().endsWith(".toml")) {
				files.add(file);
			}
		}
		return files;
	}

	private static boolean generateTypescript(File pythonFile, File source, File target) {
		target.getParentFile().mkdirs();
		try {
			Process p = JBundler.executeCommandInDirectory("python", new String[] {"generate_class.py", "as-binding", source.getAbsolutePath(), target.getAbsolutePath()}, pythonFile).start();
			byte[] error = new byte[8192];
			int l = p.getErrorStream().read(error);
			if (l == -1) {
				return true;
			} else {
				System.err.println(new String(error, 0, l));
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
