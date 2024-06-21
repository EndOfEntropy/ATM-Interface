package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import net.miginfocom.swing.MigLayout;

public class LoginMenu {
	
	public JPanel panel11, panel12;
	public JLabel message1, message2, errorLabel;
	public String userInput;
	public JPasswordField padField, cardField;
	public Keypad keypad;
	private char mask;
	
	
	LoginMenu() {
		// set the panel layouts
		this.panel11 = new JPanel(new MigLayout("top, center, flowy, debug", "center"));
		this.panel12 = new JPanel(new MigLayout("top, center, flowy, debug", "center"));

		// set the components and strings
		this.userInput = "";
		this.message1 = new JLabel("Please insert access card");
		this.message2 = new JLabel("Please enter PIN:");
		this.message1.setFont(new Font("Dialogue", Font.BOLD, 14));
		this.message2.setFont(new Font("Dialogue", Font.BOLD, 14));
		this.errorLabel = new JLabel("");
		this.cardField = new JPasswordField(9);
		this.mask = this.cardField.getEchoChar();
		this.cardField.setEchoChar(mask);
		this.cardField.setText("0000111122223333");
		this.cardField.setEditable(false);
		this.padField = new JPasswordField(4);
		this.padField.setEditable(false);
		this.keypad = new Keypad();
		
		// add the components to the center panel
		this.panel11.add(this.message1, "center");
		this.panel11.add(this.cardField, "center");
		this.panel11.add(this.message2, "center");
		this.panel11.add(this.padField, "center");
		this.panel11.add(this.errorLabel, "center");
		this.panel12.add(this.keypad.addKeypad(), "center");
		
		// add action listeners to the keypad buttons
		keyPadListeners();
	}
	
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
	
	public void updateMessage(String text) {
		this.message1.setText(text);
	}
	
	public void clearFields() {
		this.userInput = "";
		this.errorLabel.setText("");
		this.padField.setText("");
	}
	
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
	 * class that handles events for the clear/cancel buttons event
	 */
	public class BClear implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// clear the input field.
			clearFields();
		}
	}
	
	/**
	 * class that handles events for the delete buttons event
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
