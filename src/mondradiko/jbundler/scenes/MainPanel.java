package mondradiko.jbundler.scenes;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import mondradiko.jbundler.Configuration;
import mondradiko.jbundler.JBundler;
import mondradiko.jbundler.bundler.BinFolder;
import mondradiko.jbundler.bundler.Bundler;
import mondradiko.jbundler.codegen.Codegen;
import mondradiko.jbundler.launcher.LaunchClient;
import mondradiko.jbundler.project.BasicDialog;
import mondradiko.jbundler.project.Project;

public class MainPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public MainPanel(Configuration config) {
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		add(splitPane);

		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);

		DefaultMutableTreeNode globalTreeModel = new DefaultMutableTreeNode("Global Configuration (click to edit)");
		globalTreeModel.add(new DefaultMutableTreeNode("AssemblyScript version: " + config.getAssemblyScriptVersion()));
		globalTreeModel.add(new DefaultMutableTreeNode("NPM version (optional): " + config.getNpmVersion()));
		globalTreeModel.add(new DefaultMutableTreeNode("Python version: " + config.getPythonVersion()));
		globalTreeModel.add(new DefaultMutableTreeNode("Bundler executable: " + config.getBundlerPath()));
		globalTreeModel.add(new DefaultMutableTreeNode("Codegen directory: " + config.getCodegenDirectory()));
		globalTreeModel.add(new DefaultMutableTreeNode("Client executable: " + config.getClientPath()));
		globalTreeModel.add(new DefaultMutableTreeNode("Client config.toml (optional): " + config.getClientToml()));
		JTree globaltree = new JTree(globalTreeModel);
		globaltree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) globaltree.getLastSelectedPathComponent();
				int index = globalTreeModel.getIndex(node);
				switch (index) {
				case 0:
					node.setUserObject("NPM version (optional): " + config.checkNpmVersion());
					break;
				case 1:
					node.setUserObject("AssemblyScript version: " + config.checkAssemblyScriptVersion());
					break;
				case 2:
					node.setUserObject("Python version: " + config.checkPythonVersion());
					break;
				case 3:
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser.setDialogTitle(
							"Select your bundler executable (e.g. ./builddir/bundler/mondradiko-bundler.exe)");
					int returnVal = chooser.showOpenDialog(globaltree);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						config.setBundlerPath(file.getAbsolutePath());
						JBundler.saveConfig();
						node.setUserObject("Bundler executable: " + config.getBundlerPath());
						((DefaultTreeModel) globaltree.getModel()).nodeChanged(node);
					}
					break;
				case 4:
					JFileChooser chooser1 = new JFileChooser();
					chooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser1.setDialogTitle("Select your codegen python file (e.g. ./codegen/)");
					int returnVal1 = chooser1.showOpenDialog(globaltree);
					if (returnVal1 == JFileChooser.APPROVE_OPTION) {
						File file = chooser1.getSelectedFile();
						config.setCodegenDirectory(file.getAbsolutePath());
						JBundler.saveConfig();
						node.setUserObject("Codegen directory: " + config.getCodegenDirectory());
						((DefaultTreeModel) globaltree.getModel()).nodeChanged(node);
					}
					break;
				case 5:
					JFileChooser chooser11 = new JFileChooser();
					chooser11.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser11.setDialogTitle(
							"Select your client executable (e.g. ./builddir/client/mondradiko-bundler.exe)");
					int returnVal11 = chooser11.showOpenDialog(globaltree);
					if (returnVal11 == JFileChooser.APPROVE_OPTION) {
						File file = chooser11.getSelectedFile();
						config.setClientPath(file.getAbsolutePath());
						JBundler.saveConfig();
						node.setUserObject("Client executable: " + config.getClientPath());
						((DefaultTreeModel) globaltree.getModel()).nodeChanged(node);
					}
					break;
				case 6:
					JFileChooser chooser111 = new JFileChooser();
					chooser111.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser111.setDialogTitle(
							"Select your client's config.toml file");
					int returnVal111 = chooser111.showOpenDialog(globaltree);
					if (returnVal111 == JFileChooser.APPROVE_OPTION) {
						File file = chooser111.getSelectedFile();
						config.setClientToml(file.getAbsolutePath());
						JBundler.saveConfig();
						node.setUserObject("Client config.toml: " + config.getClientToml());
						((DefaultTreeModel) globaltree.getModel()).nodeChanged(node);
					}
					break;
				}

			}
		});
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(globaltree);

		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblConfiguration = new JLabel("Master Actions");
		lblConfiguration.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblConfiguration = new GridBagConstraints();
		gbc_lblConfiguration.anchor = GridBagConstraints.WEST;
		gbc_lblConfiguration.insets = new Insets(0, 0, 5, 0);
		gbc_lblConfiguration.gridx = 0;
		gbc_lblConfiguration.gridy = 0;
		panel_1.add(lblConfiguration, gbc_lblConfiguration);

		JButton btnBundleProject = new JButton("Build Project");
		btnBundleProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Project.projectIsOpen()) {
					new Thread() {
						public void run() {
							BasicDialog dialog = new BasicDialog();
							dialog.setDialogMessage("Compiling scripts");
							dialog.showMe();
							BinFolder.moveAssets(new File(Project.getProjectDirectory(), "src"),
									new File(Project.getProjectDirectory(), "bin"));
							dialog.setDialogMessage("Bundling assets");
							Bundler.bundle(config, new File(Project.getProjectDirectory(), "bin"),
									new File(Project.getProjectDirectory(), "out"));
							dialog.hideMe();
						}
					}.start();
				} else {
					JOptionPane.showMessageDialog(null, "There is no project open");
				}
			}
		});

		JButton btnRerunCodegen = new JButton("Run Codegen");
		btnRerunCodegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Project.projectIsOpen()) {
					Codegen.run(config, Project.getProjectDirectory());
				} else {
					JOptionPane.showMessageDialog(null, "There is no project open");
				}
			}
		});

		JLabel lblProject = new JLabel("Project Actions");
		GridBagConstraints gbc_lblProject = new GridBagConstraints();
		gbc_lblProject.anchor = GridBagConstraints.WEST;
		gbc_lblProject.insets = new Insets(0, 0, 5, 0);
		gbc_lblProject.gridx = 0;
		gbc_lblProject.gridy = 3;
		panel_1.add(lblProject, gbc_lblProject);
		GridBagConstraints gbc_btnRerunCodegen = new GridBagConstraints();
		gbc_btnRerunCodegen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRerunCodegen.insets = new Insets(0, 0, 5, 0);
		gbc_btnRerunCodegen.gridx = 0;
		gbc_btnRerunCodegen.gridy = 4;
		btnRerunCodegen.setEnabled(false);
		panel_1.add(btnRerunCodegen, gbc_btnRerunCodegen);
		GridBagConstraints gbc_btnBundleProject = new GridBagConstraints();
		gbc_btnBundleProject.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBundleProject.insets = new Insets(0, 0, 5, 0);
		gbc_btnBundleProject.gridx = 0;
		gbc_btnBundleProject.gridy = 5;
		btnBundleProject.setEnabled(false);
		panel_1.add(btnBundleProject, gbc_btnBundleProject);

		JButton btnLaunchMondradikoWithout = new JButton("Launch Latest Version");
		btnLaunchMondradikoWithout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LaunchClient.launch(new File(Project.getProjectDirectory(), "out"), config);
			}
		});
		GridBagConstraints gbc_btnLaunchMondradikoWithout = new GridBagConstraints();
		gbc_btnLaunchMondradikoWithout.insets = new Insets(0, 0, 5, 0);
		gbc_btnLaunchMondradikoWithout.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLaunchMondradikoWithout.gridx = 0;
		gbc_btnLaunchMondradikoWithout.gridy = 6;
		btnLaunchMondradikoWithout.setEnabled(false);
		panel_1.add(btnLaunchMondradikoWithout, gbc_btnLaunchMondradikoWithout);

		JButton btnGenerateEnvironment = new JButton("Setup Project Environment");
		btnGenerateEnvironment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Project.newProject(config);
				if (Project.projectIsOpen()) {
					btnRerunCodegen.setEnabled(true);
					btnBundleProject.setEnabled(true);
					btnLaunchMondradikoWithout.setEnabled(true);
				}
			}
		});
		GridBagConstraints gbc_btnGenerateEnvironment = new GridBagConstraints();
		gbc_btnGenerateEnvironment.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGenerateEnvironment.insets = new Insets(0, 0, 5, 0);
		gbc_btnGenerateEnvironment.gridx = 0;
		gbc_btnGenerateEnvironment.gridy = 1;
		panel_1.add(btnGenerateEnvironment, gbc_btnGenerateEnvironment);

		JButton btnOpenProject = new JButton("Open Project");
		btnOpenProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Project.openProject(config);
				if (Project.projectIsOpen()) {
					btnRerunCodegen.setEnabled(true);
					btnBundleProject.setEnabled(true);
					btnLaunchMondradikoWithout.setEnabled(true);
				}
			}
		});
		GridBagConstraints gbc_btnOpenProject = new GridBagConstraints();
		gbc_btnOpenProject.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOpenProject.insets = new Insets(0, 0, 5, 0);
		gbc_btnOpenProject.gridx = 0;
		gbc_btnOpenProject.gridy = 2;
		panel_1.add(btnOpenProject, gbc_btnOpenProject);
	}

}
