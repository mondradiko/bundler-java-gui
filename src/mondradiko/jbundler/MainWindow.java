package mondradiko.jbundler;

import javax.swing.JFrame;

import mondradiko.jbundler.scenes.MainPanel;

public class MainWindow {
	
	private JFrame frame;
	
	public MainWindow(Configuration config) {
		frame = new JFrame("Mondradiko Bundler Utility");
		
		frame.setContentPane(new MainPanel(config));
	}

	public void show() {
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
