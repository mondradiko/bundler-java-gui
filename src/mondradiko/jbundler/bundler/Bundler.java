package mondradiko.jbundler.bundler;

import java.io.File;
import java.io.IOException;

import mondradiko.jbundler.Configuration;
import mondradiko.jbundler.JBundler;

public class Bundler {

	public static void bundle(Configuration config, File srcDir, File destDir) {
		destDir.mkdir();
		try {
			ProcessBuilder b = JBundler.executeCommandInDirectoryNewTerminal(
					new File(config.getBundlerPath()).getAbsolutePath(),
					new String[] { new File(srcDir, "bundler-manifest.toml").getAbsolutePath() }, destDir);
			b.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
