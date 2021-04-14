package mondradiko.jbundler.project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class BasicDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JLabel lblBasicdialog = new JLabel("Starting");

	/**
	 * Create the dialog.
	 */
	public BasicDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			contentPanel.add(lblBasicdialog);
		}
	}

	public void showMe() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BasicDialog.this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
				BasicDialog.this.setVisible(true);
			}
		});
	}

	public void hideMe() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BasicDialog.this.setVisible(false);
			}
		});
	}

	public void setDialogMessage(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BasicDialog.this.lblBasicdialog.setText(message);
			}
		});
	}
}
