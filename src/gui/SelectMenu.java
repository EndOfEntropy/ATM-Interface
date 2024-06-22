package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

public class SelectMenu {
	
	/* Swing resources needed to create the select menu object */
	/* The user input and the prompt to display to the user */
	public JPanel panel11, panel12;
	public JLabel message, summary, errorLabel;
	public String userInput, prompt;
	public JTextField padField;
	public Keypad keypad;
	
	/** Create a Select Menu object for the customer to select operations
	 * @param numAcc		the account number
	 * @param accSummary	the account summary
	 * @param prompt		the prompt to display
	 */
	SelectMenu(int numAcc, String accSummary, Optional<String> prompt) {
		// set the panel layouts
		this.panel11 = new JPanel(new MigLayout("top, center, flowy, debug", "center"));
		this.panel12 = new JPanel(new MigLayout("top, center, flowy, debug", "center"));

		// set the components and strings
		this.userInput = "";
		this.prompt = String.format(prompt.isPresent() ? prompt.get() : 
						"Please select an account from (1-%d):", numAcc);
		this.message = new JLabel(this.prompt);
		this.summary = new JLabel(accSummary);
		this.errorLabel = new JLabel("");
		this.padField = new JTextField(10);
		this.keypad = new Keypad();
		
		// add the components to the center panel
		this.panel11.add(this.message, "center");
		this.panel11.add(this.summary, "center");
		this.panel11.add(this.errorLabel, "center");
		this.panel11.add(this.padField, "center");
		this.padField.setEditable(false);
		this.panel12.add(this.keypad.addKeypad(), "center");
		
		// add action listeners to the keypad buttons
		keyPadListeners();
	}
	
	/** Add the created select menu to the panel
	 * @param panel		the JPanel object
	 */
	public void addSelectMenu(JPanel panel) {
		// add the components to the center panel and set the layout
		panel.setLayout(new MigLayout("top, center, flowy, debug", "center"));
		
		// add the panels
		panel.add(this.panel11);
		panel.add(this.panel12);
	}
	
	/**
	 * Add action listeners to the keypad buttons
	 */
	public void keyPadListeners() {
		BCheck BC = new BCheck();
		BClear BC1 = new BClear();
		BDelete BC2 = new BDelete();
		
		this.keypad.b1.addActionListener(BC);
		this.keypad.b2.addActionListener(BC);
		this.keypad.b3.addActionListener(BC);
		this.keypad.b4.addActionListener(BC);
		this.keypad.b5.addActionListener(BC);
		this.keypad.b6.addActionListener(BC);
		this.keypad.b7.addActionListener(BC);
		this.keypad.b8.addActionListener(BC);
		this.keypad.b9.addActionListener(BC);
		this.keypad.b0.addActionListener(BC);
		this.keypad.bDot.addActionListener(BC);
		this.keypad.bClear.addActionListener(BC1);
		this.keypad.bCancel.addActionListener(BC1);
		this.keypad.bDel.addActionListener(BC2);
	}
	
	/** Update the message to display
	 * @param text		the string
	 */
	public void updateMessage(String text) {
		this.message.setText(text);
	}
	
	/**
	 * Clear the fields in the select menu after the user input is successful
	 */
	public void clearFields() {
		this.userInput = "";
		this.errorLabel.setText("");
		this.padField.setText("");
	}
	
	/**
	 * Clear the fields in the select menu after the user input is unsuccessful
	 */
	public void retry() {
		this.userInput = "";
		this.padField.setText("");
	}
	
	/**
	 * class that handles events for the numeric pad buttons event
	 */
	public class BCheck implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// get the pressed button object
			JButton numpad = (JButton)e.getSource();
			String text = numpad.getText();
			userInput += text;
			// update the text fields with the user input
			padField.setText(userInput);
		}
	}
	
	/**
	 * class that handles events for the clear and cancel buttons event
	 */
	public class BClear implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// clear the input field.
			clearFields();
		}
	}
	
	/**
	 * class that handles events for the delete button event
	 */
	public class BDelete implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// delete the last character in the input string
			if(userInput.length() > 0) {
				userInput = userInput.substring(0, userInput.length()-1);
			}
			padField.setText(userInput);
		}
	}
	
}
