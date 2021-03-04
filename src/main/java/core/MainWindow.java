package core;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.MouseWheelListener;
import java.awt.Dimension;
import java.awt.Font;

public class MainWindow {

	private JFrame frame;
	private JButton calcButton;
	private static String err = "Error";
	private static String msg = " is invalid, please try again";

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		JFrame.setDefaultLookAndFeelDecorated(true); // custom window decoration
		// com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme.install();
		com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkHardIJTheme.install();
		createContents();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - frame.getBounds().width) / 2,
				(screenSize.height - frame.getBounds().height) / 2);
		frame.setVisible(true);
		calcButton.requestFocus();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		frame = new JFrame("Base Modifier");
		frame.setIconImage((new ImageIcon(MainWindow.class.getClassLoader().getResource("BaseIcon.ico")).getImage()));
		frame.setSize(450, 260);
		frame.setLayout(null);
		frame.setResizable(false);

		JTextField numberString = new JTextField();
		numberString.setEditable(true);
		numberString.setToolTipText("Number to modify");
		numberString.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
		numberString.setBounds(98, 28, 270, 32);
		frame.add(numberString);

		JLabel lblNumber = new JLabel("Number:", SwingConstants.CENTER);
		// lblNumber.setFont(SWTResourceManager.getFont("Tahoma", 15, SWT.NORMAL));
		lblNumber.setBounds(10, 33, 82, 24);
		frame.add(lblNumber);

		JComboBox inputBase = new JComboBox(new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
				"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36" });
		inputBase.setEnabled(true);
		inputBase.setToolTipText("Original Base");
		inputBase.setBounds(374, 38, 55, 23);
		inputBase.addMouseWheelListener(mouseWheelListener);
		frame.add(inputBase);

		JLabel lblBase = new JLabel("Base:", SwingConstants.CENTER);
		// lblBase.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		lblBase.setBounds(374, 17, 55, 15);
		frame.add(lblBase);

		JLabel lblToBase = new JLabel("to base:", SwingConstants.CENTER);
		// lblToBase.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		lblToBase.setBounds(374, 77, 55, 15);
		frame.add(lblToBase);

		JComboBox outputBase = new JComboBox(new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
				"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36" });
		outputBase.setEnabled(true);
		outputBase.setToolTipText("Desired Base");
		outputBase.setBounds(374, 98, 55, 23);
		outputBase.addMouseWheelListener(mouseWheelListener);
		frame.add(outputBase);

		JTextField outputString = new JTextField("", SwingConstants.CENTER);
		outputString.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 23));
		outputString.setHorizontalAlignment(SwingConstants.CENTER);
		outputString.setEditable(false);
		outputString.setToolTipText("Answer");
		outputString.setEnabled(true);
		outputString.setBounds(10, 136, 414, 75);
		frame.add(outputString);

		calcButton = new JButton("Calculate");
		calcButton.setEnabled(true);
		calcButton.setToolTipText("Press after entering values to calculate");
		calcButton.addActionListener(e -> {
			int ogBase, resultBase;
			String number, output;
			try {
				ogBase = Integer.valueOf((String) inputBase.getSelectedItem());
				if (ogBase < 0 || ogBase > 36)
					throw new Exception();
			} catch (Exception Exception) {
				JOptionPane.showMessageDialog(frame, "Input base" + msg, err, JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				number = numberString.getText();
				if (!BaseMethods.isValidString(number, ogBase))
					throw new Exception();
			} catch (Exception Exception) {
				JOptionPane.showMessageDialog(frame, "Input number" + msg, err, JOptionPane.ERROR_MESSAGE);
				return;
			}
			number = BaseMethods.toUpperCase(number);
			numberString.setText(number);
			try {
				resultBase = Integer.valueOf((String) outputBase.getSelectedItem());
				if (resultBase < 0 || resultBase > 36 || resultBase == ogBase)
					throw new Exception();
			} catch (Exception Exception) {
				JOptionPane.showMessageDialog(frame, "Output base" + msg, err, JOptionPane.ERROR_MESSAGE);
				return;
			}
			output = BaseMethods.getBaseXtoY(ogBase, resultBase, number);
			System.out.println("(" + number + ")"+"Base[" + ogBase + "] = ("+output+")Base[" + resultBase + "]");
			outputString.setText(output);
		});
		calcButton.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
		calcButton.setBounds(145, 83, 141, 47);
		frame.add(calcButton);

		JButton infoButton = new JButton("info");
		infoButton.addActionListener(e -> JOptionPane.showMessageDialog(frame,
				"Enter valid base (2-36) and valid origin number for that base\n"
						+ "(Mouse scroll wheel can be used to change base quickly)\n"
						+ "Program created by: Or Ben Moshe",
				"Instructions", JOptionPane.INFORMATION_MESSAGE));
		infoButton.setBounds(0, 0, 60, 24);
		infoButton.setHorizontalAlignment(SwingConstants.LEFT);
		frame.add(infoButton);
	}

	MouseWheelListener mouseWheelListener = e -> {
		int steps = e.getWheelRotation() * -1;
		JComboBox thisBox = (JComboBox) e.getComponent();
		int newIndex = thisBox.getSelectedIndex() + steps;
		if (newIndex < 0)
			newIndex = 0;
		else if (newIndex > 34)
			newIndex = 34;
		thisBox.setSelectedIndex(newIndex);
	};
	
	

}
