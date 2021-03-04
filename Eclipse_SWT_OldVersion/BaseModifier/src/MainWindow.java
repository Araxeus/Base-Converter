import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainWindow {
	
	protected Shell shlBaseModifier;
	private Text numberString;
	private Text outputString;

	/**
	 * Launch the application.
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
		Display display = Display.getDefault();
		createContents();
		shlBaseModifier.open();
		shlBaseModifier.layout();
		while (!shlBaseModifier.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlBaseModifier = new Shell();
		shlBaseModifier.setImage(SWTResourceManager.getImage(MainWindow.class, "/resources/BaseIcon.ico"));
		shlBaseModifier.setBackground(SWTResourceManager.getColor(220, 217, 203));
		shlBaseModifier.setSize(450, 300);
		shlBaseModifier.setText("Base Modifier");
		
		numberString = new Text(shlBaseModifier, SWT.BORDER);
		numberString.setTouchEnabled(true);
		numberString.setToolTipText("Number to modify");
		numberString.setFont(SWTResourceManager.getFont("Segoe UI Emoji", 15, SWT.NORMAL));
		numberString.setBounds(98, 28, 270, 36);
		numberString.setBackground(SWTResourceManager.getColor(255,255,245));
		
		Label lblNumber = new Label(shlBaseModifier, SWT.NONE);
		lblNumber.setBackground(SWTResourceManager.getColor(220, 217, 203));
		lblNumber.setFont(SWTResourceManager.getFont("Tahoma", 15, SWT.NORMAL));
		lblNumber.setBounds(10, 33, 82, 24);
		lblNumber.setText("Number:");
		
		Combo inputBase = new Combo(shlBaseModifier, SWT.NONE);
		inputBase.setItems(new String[] {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36"});
		inputBase.setTouchEnabled(true);
		inputBase.setToolTipText("Original Base");
		inputBase.setBounds(374, 38, 55, 23);
		inputBase.setBackground(SWTResourceManager.getColor(255,255,245));
		
		Label lblBase = new Label(shlBaseModifier, SWT.NONE);
		lblBase.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		lblBase.setBackground(SWTResourceManager.getColor(220, 217, 203));
		lblBase.setBounds(374, 17, 55, 15);
		lblBase.setText("base:");
		
		Label lblToBase = new Label(shlBaseModifier, SWT.NONE);
		lblToBase.setText("to base:");
		lblToBase.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		lblToBase.setBackground(SWTResourceManager.getColor(220, 217, 203));
		lblToBase.setBounds(374, 77, 55, 15);
		
		Combo outputBase = new Combo(shlBaseModifier, SWT.NONE);
		outputBase.setTouchEnabled(true);
		outputBase.setToolTipText("Desired Base");
		outputBase.setItems(new String[] {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36"});
		outputBase.setBounds(374, 98, 55, 23);
		outputBase.setBackground(SWTResourceManager.getColor(255,255,245));
		
		outputString = new Text(shlBaseModifier, SWT.BORDER);
		outputString.setFont(SWTResourceManager.getFont("Segoe UI Emoji", 15, SWT.NORMAL));
		outputString.setEditable(false);
		outputString.setToolTipText("Answer");
		outputString.setTouchEnabled(true);
		outputString.setBackground(SWTResourceManager.getColor(255,255,245));
		outputString.setBounds(10, 176, 414, 75);
		
		Button calcButton = new Button(shlBaseModifier, SWT.BORDER);
		calcButton.setTouchEnabled(true);
		calcButton.setToolTipText("Press after entering values to calculate");
		calcButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int ogBase,resultBase;
				String number,output;
				try {
					ogBase = Integer.parseInt(inputBase.getText());
					if(ogBase<0 || ogBase>36)
						throw new Exception();
				}
				catch (Exception Exception) {
					MessageDialog.openError(shlBaseModifier, "Error", "Input base is invalid, please try again");
					return;
				}
				try {
					number = numberString.getText();
					if(!BaseMethods.isValidString(number,ogBase))
						throw new Exception();
				}
				catch (Exception Exception) {
					MessageDialog.openError(shlBaseModifier, "Error", "Input number invalid, please try again");
					return;
				}
				number = BaseMethods.toUpperCase(number);
				numberString.setText(number);
				try {
					resultBase = Integer.parseInt(outputBase.getText());
					if(resultBase<0 || resultBase>36 || resultBase==ogBase)
						throw new Exception();
				}
				catch (Exception Exception) {
					MessageDialog.openError(shlBaseModifier, "Error", "Output base is invalid, please try again");
					return;
				}
				output=BaseMethods.getBaseXtoY(ogBase, resultBase, number);
				outputString.setText(output);
			}
		});
		calcButton.setForeground(SWTResourceManager.getColor(0, 0, 0));
		calcButton.setFont(SWTResourceManager.getFont("Segoe UI Symbol", 15, SWT.NORMAL));
		calcButton.setBounds(145, 123, 141, 47);
		calcButton.setText("Calculate");
		calcButton.setBackground(SWTResourceManager.getColor(187, 213, 207));
		
		Button infoButton = new Button(shlBaseModifier, SWT.NONE);
		infoButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialog.openInformation(shlBaseModifier, "Instructions", "Enter valid base (2-36) and valid origin number for that base\n"+"(Mouse scroll wheel can be used to change base quickly)\n"+"Program created by: Or Ben Moshe");
			}
		});
		infoButton.setForeground(SWTResourceManager.getColor(0, 100, 0));
		infoButton.setBounds(0, 0, 26, 24);
		infoButton.setText("info");
		infoButton.setBackground(SWTResourceManager.getColor(220, 217, 203));

	}
	
	

}
