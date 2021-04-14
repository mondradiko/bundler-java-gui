package mondradiko.jbundler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Configuration {
	
	public String getBundlerPath() {
		return bundlerPath;
	}

	public void setBundlerPath(String bundlerPath) {
		this.bundlerPath = bundlerPath;
	}

	public String getClientPath() {
		return clientPath;
	}

	public void setClientPath(String clientPath) {
		this.clientPath = clientPath;
	}

	public String getAssemblyScriptVersion() {
		return assemblyScriptVersion;
	}
	
	public String getPythonVersion() {
		return pythonVersion;
	}

	public String getCodegenDirectory() {
		return codegenDirectory;
	}

	public void setCodegenDirectory(String codegenDirectory) {
		this.codegenDirectory = codegenDirectory;
	}

	String bundlerPath;
	String clientPath;
	String clientToml;
	
	public String getClientToml() {
		return clientToml;
	}

	public void setClientToml(String clientToml) {
		this.clientToml = clientToml;
	}

	String assemblyScriptVersion;
	String pythonVersion;
	String codegenDirectory;
	String npmVersion;

	public String getNpmVersion() {
		return npmVersion;
	}

	public void loadConfiguration(File file) throws IOException {
		if (!file.isFile()) {
			saveConfiguration(file);
		}
		FileInputStream fIn = new FileInputStream(file);
		BufferedReader rdr = new BufferedReader(new InputStreamReader(fIn));
		
		bundlerPath = rdr.readLine();
		clientPath = rdr.readLine();
		codegenDirectory = rdr.readLine();
		clientToml = rdr.readLine();
		
		fIn.close();
		
		checkAssemblyScriptVersion();
		checkPythonVersion();
		checkNpmVersion();
	}
	
	public String checkAssemblyScriptVersion() {
		try {
			Process p = JBundler.executeCommand("asc --version");
			BufferedReader pRdr = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String version = pRdr.readLine();
			if (version == null || !version.startsWith("Version")) {
				assemblyScriptVersion = "not detected";
			} else {
				assemblyScriptVersion = version;
			}
			p.destroy();
		} catch (IOException e) {
			assemblyScriptVersion = "not detected";
		}
		return assemblyScriptVersion;
	}
	
	public String checkPythonVersion() {
		try {
			Process p = JBundler.executeCommand("python --version");
			BufferedReader pRdr = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String version = pRdr.readLine();
			if (version == null || !version.startsWith("Python")) {
				pythonVersion = "not detected";
			} else {
				pythonVersion = version;
			}
			p.destroy();
		} catch (IOException e) {
			pythonVersion = "not detected";
		}
		return pythonVersion;
	}
	
	public String checkNpmVersion() {
		try {
			Process p = JBundler.executeCommand("npm --version");
			BufferedReader pRdr = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String version = pRdr.readLine();
			if (version == null || !Character.isDigit(version.charAt(0))) {
				npmVersion = "not detected";
			} else {
				npmVersion = version;
			}
			p.destroy();
		} catch (IOException e) {
			npmVersion = "not detected";
		}
		return npmVersion;
	}
	
	public void saveConfiguration(File file) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		
		PrintStream writer = new PrintStream(out);
		writer.println(bundlerPath);
		writer.println(clientPath);
		writer.println(codegenDirectory);
		writer.println(clientToml);
		
		out.close();
	}
}
