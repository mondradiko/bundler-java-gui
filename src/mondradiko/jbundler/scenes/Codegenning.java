package mondradiko.jbundler.scenes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class Codegenning extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JProgressBar progressBar = new JProgressBar();
	private final JLabel fileName = new JLabel("Scanning for files");

	/**
	 * Create the dialog.
	 */
	public Codegenning(Runnable cancelHandler) {
		setBounds(100, 100, 500, 140);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblNewLabel = new JLabel("codegen is generating TypeScript environment (may take a couple minutes)");
			contentPanel.add(lblNewLabel, BorderLayout.NORTH);
		}
		{
			contentPanel.add(progressBar, BorderLayout.CENTER);
		}
		{
			JLabel fileName = new JLabel("Scanning for files");
			contentPanel.add(fileName, BorderLayout.SOUTH);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Stop");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelHandler.run();
					}
				});
				cancelButton.setActionCommand("cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public void setProgress(String fileName, int progress, int max) {
		progressBar.setMaximum(max);
		progressBar.setValue(progress);
		progressBar.setToolTipText(fileName + "(" + progress + "/" + max + ")");

		this.fileName.setText(fileName);
	}
}
