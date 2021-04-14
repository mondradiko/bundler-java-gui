package mondradiko.jbundler.bundler;

import java.io.File;
import java.io.IOException;

import mondradiko.jbundler.JBundler;
import mondradiko.jbundler.project.Project;

public class AssemblyScript {

	public static void compileAssemblyScript(File sourceFile, File targetFile) {
		File baseDir = Project.getProjectDirectory();

		targetFile.getParentFile().mkdirs();
		File libraryPath = new File(baseDir, "lib");

		try {
			ProcessBuilder b = JBundler.executeCommandInDirectory("asc",
					new String[] { "-t", targetFile.getAbsolutePath(), "-O3", sourceFile.getAbsolutePath(), "--lib",
							libraryPath.getAbsolutePath() },
					baseDir);
			b.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
