package mondradiko.jbundler.bundler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class BinFolder {

	public static void moveAssets(File srcDir, File dstDir) {
		dstDir.mkdir();
		File inputToml = new File(srcDir, "bundler-manifest.toml");
		File outputToml = new File(dstDir, "bundler-manifest.toml");
		if (!inputToml.isFile()) return;
		try {
			Scanner scanner = new Scanner(new FileInputStream(inputToml));
			PrintStream tomlOut = new PrintStream(outputToml);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.trim().startsWith("file = ")) {
					String path = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
					if (path.endsWith(".ts")) {
						String dstPath = path.substring(0, path.lastIndexOf(".")) + ".wat";
						tomlOut.println(line.substring(0, line.indexOf("\"") + 1) + dstPath + "\"");
						
						File src = new File(srcDir, path);
						File dest = new File(dstDir, dstPath);
						AssemblyScript.compileAssemblyScript(src, dest);
					} else {
						String dstPath = "../src/"+path;
						tomlOut.println(line.substring(0, line.indexOf("\"") + 1) + dstPath + "\"");
					}
				} else {
					tomlOut.println(line);
				}
			}
			tomlOut.close();
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
