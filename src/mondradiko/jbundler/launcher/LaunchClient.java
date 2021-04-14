package mondradiko.jbundler.launcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mondradiko.jbundler.Configuration;
import mondradiko.jbundler.JBundler;

public class LaunchClient {

	public static void launch(File bundlesDir, Configuration config) {
		List<String> args = new ArrayList<String>();
		
		args.add("--serverless");
		args.add("-b");
		args.add(bundlesDir.getAbsolutePath());
		
		File path = new File(config.getClientPath()).getParentFile();
		
		if (config.getClientToml() != null && !config.getClientToml().isEmpty()) {
			args.add("-c");
			args.add(config.getClientToml());
			path = new File(config.getClientToml()).getParentFile();
		}
		
		try {
			JBundler.executeCommandInDirectoryNewTerminal(config.getClientPath(), args.toArray(new String[0]), path).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
