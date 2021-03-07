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
import javax.swing.WindowConstants;

import java.awt.event.MouseWheelListener;
import java.awt.Dimension;
import java.awt.Font;

public class MainWindow {

	/**
	 *
	 */
	private static final String DEFAULT_FONT = "Segoe UI Emoji";
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
		 com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme.install();
		//com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkHardIJTheme.install();
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
		frame.setSize(435, 250);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JTextField numberString = new JTextField();
		numberString.setEditable(true);
		numberString.setToolTipText("Number to modify");
		numberString.setFont(new Font(DEFAULT_FONT, Font.PLAIN, 20));
		numberString.setBounds(98, 28, 270, 32);
		frame.add(numberString);

		JLabel lblNumber = new JLabel("Number:", SwingConstants.CENTER);
		lblNumber.setFont(new Font(DEFAULT_FONT, Font.PLAIN, 17));

		lblNumber.setBounds(10, 33, 82, 24);
		frame.add(lblNumber);

		String[] baseArray = new String[35];
		for (int i = 0; i < baseArray.length; i++)
			baseArray[i] = String.valueOf(i+2);

		JComboBox<String> inputBase = new JComboBox<>(baseArray);
		inputBase.setEnabled(true);
		inputBase.setToolTipText("Original Base");
		inputBase.setBounds(375, 38, 55, 23);
		inputBase.addMouseWheelListener(mouseWheelListener);
		frame.add(inputBase);

		JLabel lblBase = new JLabel("Base:", SwingConstants.CENTER);
		lblBase.setFont(new Font(DEFAULT_FONT, Font.PLAIN, 12));
		lblBase.setBounds(375, 23, 55, 15);
		frame.add(lblBase);

		JLabel lblToBase = new JLabel("To Base:", SwingConstants.CENTER);
		lblToBase.setFont(new Font(DEFAULT_FONT, Font.PLAIN, 12));
		lblToBase.setBounds(375, 83, 55, 15);
		frame.add(lblToBase);

		JComboBox<String> outputBase = new JComboBox<>(baseArray);
		outputBase.setEnabled(true);
		outputBase.setToolTipText("Desired Base");
		outputBase.setBounds(375, 98, 55, 23);
		outputBase.addMouseWheelListener(mouseWheelListener);
		frame.add(outputBase);

		JTextField outputString = new JTextField("", SwingConstants.CENTER);
		outputString.setFont(new Font(DEFAULT_FONT, Font.PLAIN, 23));
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
		calcButton.setBounds(147, 80, 140, 50);
		frame.add(calcButton);

		JButton infoButton = new JButton("info");
		infoButton.addActionListener(e -> JOptionPane.showMessageDialog(frame,
				"Enter valid base (2-36) and valid origin number for that base\n"
						+ "(Mouse scroll wheel can be used to change base quickly)\n"
						+ "Program created by: Araxeus",
				"Instructions", JOptionPane.INFORMATION_MESSAGE));
		infoButton.setBounds(5, 1, 60, 24);
		infoButton.setHorizontalAlignment(SwingConstants.LEFT);
		frame.add(infoButton);
	}

	MouseWheelListener mouseWheelListener = e -> {
		int steps = e.getWheelRotation() * -1;
		//mousewheel listener is bound to JComboBox and will be only from that type
		JComboBox<String> thisBox = (JComboBox<String>) e.getComponent();
		int newIndex = thisBox.getSelectedIndex() + steps;
		if (newIndex < 0)
			newIndex = 0;
		else if (newIndex > 34)
			newIndex = 34;
		thisBox.setSelectedIndex(newIndex);
	};
	
	

}
