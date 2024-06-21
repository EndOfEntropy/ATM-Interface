/**
 * Graphical User Interface that displays a word count application
 * 
 * Features:
 * - Counts number of characters
 * - Counts number of syllables
 * - Counts number of words
 * - Counts number of sentences
 * - Edit text through the user interface
 * - Open a text file
 * - Save a text file
 */
package gui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.Optional;
import javax.swing.*;
import backend.Account;
import backend.AccountHolder;
import backend.Bank;
import net.miginfocom.swing.MigLayout;

/**
 * @author Mickael Grivolat
 */

// Graphical User Interface that displays a word count application
public class GuiAtm extends JFrame implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	private static ImageIcon logo;
	private static JFrame mainFrame;
	private static JPanel panel1, panel2, panel3, panel4, panel5;
	private static JPopupMenu popup;
	private static JMenuItem copy, paste;
	private static JTextArea textArea1;
	private static JLabel bankLabel, trxComplete;
	private static JButton bBack, bRetCard, menu1, menu2, menu3, menu4, menu5, menu6;
	private static JButton b1, b2, b3, b4, b5, b6, b7, b8;
	private static SelectMenu selectMenu;
	private static LoginMenu loginMenu;
	private static Bank bank;
	private static AccountHolder currCust;
	
	private static final int frameW = 800, frameH = 600;

	
	
	GuiAtm() {
		initialize();
		// border components removed as another UI has been selected
		//createBorderComponents();
		loginMenu();
	}
	
	/**
	 * Create and set up the frame, panels and right-click pop up menu
	 */
	private void initialize() {
		// Set the frame of the GUI and load the logo
		logo = new ImageIcon("src\\data\\card.png");
		mainFrame = new JFrame("ATM");
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setBackground(Color.white);
		mainFrame.setSize(frameW, frameH);
		mainFrame.setVisible(true);
		mainFrame.setIconImage(logo.getImage());
		mainFrame.addMouseListener(this);

		// Set the panel components in the frame.
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel1.setBackground(Color.white);
		panel2.setBackground(new Color(0, 230, 118)); // Green
		panel3.setBackground(new Color(0, 230, 118));
		panel4.setBackground(new Color(0, 230, 118));
		panel5.setBackground(new Color(0, 230, 118));

		// central panel (panel1) is dynamic, no need to set size
		panel2.setPreferredSize(new Dimension(100, 0)); // East border, only width needs to be set
		panel3.setPreferredSize(new Dimension(100, 0)); // West border, only width needs to be set
		panel4.setPreferredSize(new Dimension(0, 80)); // North border, only height needs to be set
		panel5.setPreferredSize(new Dimension(0, 80)); // South border only height needs to be set

		/* Layouts setup. MigLayout allows to add layout, row/columns and component constrains.
		 * Align components in the container at the top/center/bottom etc */
		panel2.setLayout(new MigLayout("center, bottom, flowy, gap 15 15"));	// East panel
		panel3.setLayout(new MigLayout("center, bottom, flowy, gap 15 15"));	// West panel
		mainFrame.add(panel1, BorderLayout.CENTER);
		mainFrame.add(panel2, BorderLayout.EAST);
		mainFrame.add(panel3, BorderLayout.WEST);
		mainFrame.add(panel4, BorderLayout.NORTH);
		mainFrame.add(panel5, BorderLayout.SOUTH);
		
		// initialize resources
		bankLabel = new JLabel("New Bank");
		bankLabel.setFont(new Font("Dialogue", Font.BOLD, 30));
		panel4.add(bankLabel); 
		initiateBank();
		trxComplete = new JLabel("Transaction complete!");
		
		// add menu items to popup
		popup = new JPopupMenu();
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		popup.add(copy);
		popup.add(paste);
		copy.addActionListener(this);
		paste.addActionListener(this);
		
		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	
	private void initiateBank() {
		// create the bank object
		bank = new Bank("New Bank");
		
		// add customers to the bank. A chequing account is created by default
		AccountHolder holder = bank.addAccountHolder("Joe", "Smith", "0000");
		
		// add a saving account for our holder
		Account newAccount = new Account("Savings", 0.0f, holder, bank);
		holder.addAccount(newAccount);
		bank.addAccount(newAccount);
		
		bank.addAccountHolder("Jack", "Son", "1111");
	}
	
	/**
	 * Create the border components. They remain unchanged throughout the application.
	 */
	private void createBorderComponents() {
		// initialize the resources
		b1 = new JButton("1");
		b2 = new JButton("2");
		b3 = new JButton("3");
		b4 = new JButton("4");
		b5 = new JButton("5");
		b6 = new JButton("6");
		b7 = new JButton("7");
		b8 = new JButton("8");
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		
		// set buttons preferred size add components to the borders		
		b1.setPreferredSize(new Dimension(70, 40));
		b2.setPreferredSize(new Dimension(70, 40));
		b3.setPreferredSize(new Dimension(70, 40));
		b4.setPreferredSize(new Dimension(70, 40));
		b5.setPreferredSize(new Dimension(70, 40));
		b6.setPreferredSize(new Dimension(70, 40));
		b7.setPreferredSize(new Dimension(70, 40));
		b8.setPreferredSize(new Dimension(70, 40));
		panel3.add(b1, "width 70!, height 40!");
		panel3.add(b2, "width 70!, height 40!");
		panel3.add(b3, "width 70!, height 40!");
		panel3.add(b4, "width 70!, height 40!");
		panel2.add(b5, "width 70!, height 40!");
		panel2.add(b6, "width 70!, height 40!");
		panel2.add(b7, "width 70!, height 40!");
		panel2.add(b8, "width 70!, height 40!");

		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	
	public void loginMenu() {
		// remove all components from the container
		panel1.removeAll();
		
		// create the login menu
		loginMenu = new LoginMenu();
		
		// redefine the action of the Enter button
		removeAllActionListeners(loginMenu.keypad.bEnter);
		BEnterLogin BE = new BEnterLogin();
		loginMenu.keypad.bEnter.addActionListener(BE);
		
		// add the components to the center panel
		loginMenu.addSelectMenu(panel1);
		
		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	
	public void mainMenu() {
		// remove all components from center panel
		panel1.removeAll();
		
		// set the panel layouts
		JPanel panel11 = new JPanel(new MigLayout("top, center, flowy, debug", "center"));
		JPanel panel12 = new JPanel(new MigLayout("bottom, fillx, wrap 2, gap 10 15, debug", 
				"[][]", "[][][][]"));
		
		// initialize the components
		JLabel label1 = new JLabel("Choose a transaction");
		label1.setFont(new Font("Dialogue", Font.BOLD, 14));
		menu1 = new JButton("Account activity");
		menu2 = new JButton("Balances");
		menu3 = new JButton("Deposit");
		menu4 = new JButton("Withdrawal");
		menu5 = new JButton("Transfer between your accounts");
		menu6 = new JButton("Transfer to an external account");
		bBack = new JButton("Back");
		bRetCard = new JButton("Return Card");
		menu1.setBackground(new Color(0, 230, 118)); // look for a darker shade
		menu2.setBackground(new Color(0, 230, 118));
		menu3.setBackground(new Color(0, 230, 118));
		menu4.setBackground(new Color(0, 230, 118));
		menu5.setBackground(new Color(0, 230, 118));
		menu6.setBackground(new Color(0, 230, 118));
		bBack.setBackground(new Color(0, 230, 118));
		bRetCard.setBackground(new Color(0, 230, 118));
		
		// add action listeners to the main menu and Back buttons
		BackCheck bc = new BackCheck();
		bBack.addActionListener(bc);
		RetCardCheck rcc = new RetCardCheck();
		bRetCard.addActionListener(rcc);
		optionsButtonListeners();
		
		// add the components to the center panel
		panel1.add(panel11);
		panel1.add(panel12);
		panel11.add(label1, "center");
		panel12.add(menu1, "height 40!, grow");
		panel12.add(menu2, "height 40!, grow");
		panel12.add(menu3, "height 40!, grow");
		panel12.add(menu4, "height 40!, grow");
		panel12.add(menu5, "height 40!, grow");
		panel12.add(menu6, "height 40!, grow");
		panel12.add(bRetCard, "height 40!, grow");

		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	

	private void showAccountsBalance() {
		// remove all components from center panel
		panel1.removeAll();
		//panel1.setLayout(new GridLayout(0,1));
		
		// set the panel layouts
		JPanel panel11 = new JPanel(new MigLayout("top, center, flowy, debug", "center"));
		JPanel panel12 = new JPanel(new MigLayout("bottom, fillx, wrap 2, gap 10 15, debug", 
				"[][]", "[][][][]"));
		
		// set the buttons
		JLabel label = new JLabel(convertToHTML(currCust.getAccountsSummary()));

		// add the components to the center panel
		panel1.add(panel11);
		panel1.add(panel12);
		panel11.add(label, "center");
		panel12.add(bBack, "height 40!, grow");
		
		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	/** Prints the transaction history of a specific account
	 * @param currCust	the logged-in holder object
	 */
	private void transactionsHistoryMenu() {
		// remove all components from center panel
		panel1.removeAll();
		
		// create the account selection menu
		selectMenu = new SelectMenu(currCust.numAccounts(), convertToHTML(currCust.getAccountsSummary()), 
				Optional.empty());

		// add the components to the center panel
		selectMenu.addSelectMenu(panel1);
		selectMenu.panel12.add(bBack);
		
		// redefine the action of the Enter button
		removeAllActionListeners(selectMenu.keypad.bEnter);
		BEnterTrxHist BE = new BEnterTrxHist();
		selectMenu.keypad.bEnter.addActionListener(BE);

		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	
	private void depositMenu() {
		// remove all components from center panel
		panel1.removeAll();
		
		// create the account selection menu
		selectMenu = new SelectMenu(currCust.numAccounts(), convertToHTML(currCust.getAccountsSummary()), 
				Optional.empty());
		
		// add the components to the center panel
		selectMenu.addSelectMenu(panel1);
		selectMenu.panel12.add(bBack);
		
		// redefine the action of the Enter button
		removeAllActionListeners(selectMenu.keypad.bEnter);
		BEnterDeposit1 BE = new BEnterDeposit1();
		selectMenu.keypad.bEnter.addActionListener(BE);

		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	
	private void withdrawalMenu() {
		// remove all components from center panel
		panel1.removeAll();
		
		// create the account selection menu
		selectMenu = new SelectMenu(currCust.numAccounts(), convertToHTML(currCust.getAccountsSummary()), 
						Optional.empty());
		
		// add the components to the center panel
		selectMenu.addSelectMenu(panel1);
		selectMenu.panel12.add(bBack);
		
		// redefine the action of the Enter button
		removeAllActionListeners(selectMenu.keypad.bEnter);
		BEnterWithdrawal1 BE = new BEnterWithdrawal1();
		selectMenu.keypad.bEnter.addActionListener(BE);

		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	private void internalTransferMenu() {
		// remove all components from center panel
		panel1.removeAll();
		
		// create the account selection menu
		String prompt = "Select From Account (1-%d):";
		selectMenu = new SelectMenu(currCust.numAccounts(), convertToHTML(currCust.getAccountsSummary()), 
							Optional.of(prompt));
		
		// add the components to the center panel
		selectMenu.addSelectMenu(panel1);
		selectMenu.panel12.add(bBack);
		
		// redefine the action of the Enter button
		removeAllActionListeners(selectMenu.keypad.bEnter);
		BEnterInternal1 BE = new BEnterInternal1();
		selectMenu.keypad.bEnter.addActionListener(BE);

		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	private void externalTransferMenu() {
		// remove all components from center panel
		panel1.removeAll();
		
		// create the account selection menu
		String prompt = "Select From Account (1-%d):";
		selectMenu = new SelectMenu(currCust.numAccounts(), convertToHTML(currCust.getAccountsSummary()), 
				Optional.of(prompt));
		// add the components to the center panel
		selectMenu.addSelectMenu(panel1);
		selectMenu.panel12.add(bBack);
		
		// redefine the action of the Enter button
		removeAllActionListeners(selectMenu.keypad.bEnter);
		BEnterExternal1 BE = new BEnterExternal1();
		selectMenu.keypad.bEnter.addActionListener(BE);

		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	/**
	 * show the completion menu to the customer
	 */
	private void completionMenu() {
		// remove all components from center panel
		panel1.removeAll();
		
		// add the completion menu resources
		panel1.add(trxComplete);
		panel1.add(bBack);
		
		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	/**
	 * show the account transaction history to the customer
	 */
	private void showTransactionHistory(JLabel label) {
		// remove all components from center panel
		panel1.removeAll();
		
		// add the completion menu resources
		panel1.add(label);
		panel1.add(bBack);
		
		// repaint the frame for newly added components
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	/**	remove all listeners from a component
	 * @param comp		the component
	 */
	private void removeAllActionListeners(JButton button) {
		for(ActionListener al : button.getActionListeners()) {
			button.removeActionListener(al);
		}
	}
	
	public void optionsButtonListeners() {
		BOptions BO = new BOptions();
		
		menu1.addActionListener(BO);
		menu2.addActionListener(BO);
		menu3.addActionListener(BO);
		menu4.addActionListener(BO);
		menu5.addActionListener(BO);
		menu6.addActionListener(BO);
	}

	
	public class BOptions implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton)e.getSource();
			String text = button.getText();
			dispatchUserInput(text);
		}
	}
	
	/**
	 * class that handles events for the enter button in the login menu
	 */
	public class BEnterLogin implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// check that the pin exists
			currCust = checkLogin();
			if(currCust != null) {
				mainMenu();
				loginMenu.clearFields();
			} else {
				loginMenu.retry();
			}
		}
	}
	
	/**
	 * class that handles events for the enter button in the transactions history menu
	 */
	public class BEnterTrxHist implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String message;
			
			// validate the user input and print the transactions
			if(!validateAccountSelectionInput(selectMenu.userInput, currCust.numAccounts())) {
				selectMenu.errorLabel.setText("Invalid selection. Please select a digit from 1 to " + 
						currCust.numAccounts());
				selectMenu.retry();
			} else {
				// display the transaction history of the selected account
				message = currCust.getAccountTrxHistory(Integer.parseInt(selectMenu.userInput) - 1);
				showTransactionHistory(new JLabel(convertToHTML(message)));
				selectMenu.clearFields();
			}
		}
	}
	
	/**
	 * class that handles events for the enter button in the deposit menu 1
	 */
	public class BEnterDeposit1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String inAcc = selectMenu.userInput, message;
			
			// validate the user input
			if(!validateAccountSelectionInput(selectMenu.userInput, currCust.numAccounts())) {
				selectMenu.errorLabel.setText("Invalid selection. Please select a digit from 1 to " + 
						currCust.numAccounts());
				selectMenu.retry();
			} else {	
				// get the selected account balance, ask for amount
				double accBal = currCust.getAccountBalance(Integer.parseInt(inAcc) - 1);
				message = String.format("Please enter the amount to deposit (balance = $%,.2f): ", accBal);
				selectMenu.message.setText(message);
				
				// redefine the action of the Enter button
				removeAllActionListeners(selectMenu.keypad.bEnter);
				BEnterDeposit2 BE = new BEnterDeposit2(Integer.parseInt(inAcc), accBal);
				selectMenu.keypad.bEnter.addActionListener(BE);
				selectMenu.clearFields();
			}
		}
	}
	
	/**
	 * class that handles events for the enter button in the deposit menu 2
	 */
	public class BEnterDeposit2 implements ActionListener {
		private int inAcc;
		private double accBal;
		
		BEnterDeposit2(int accNb, double balance){
			this.inAcc = accNb;
			this.accBal = balance;
		}
		
		public void actionPerformed(ActionEvent e) {
			String memo = "Deposit, ATM";
			
			// validate the user input and print the transactions
			if(!validateCreditAmount(selectMenu.userInput, this.accBal)) {
				selectMenu.retry();
				return;
			} else {
				// create deposit transaction and show completion
				currCust.addAccountTransactionIndex(this.inAcc - 1, Double.parseDouble(selectMenu.userInput), memo);
								
				// redefine the action of the Enter button
				removeAllActionListeners(selectMenu.keypad.bEnter);
				selectMenu.clearFields();
				completionMenu();
			}
		}
	}
	
	/**
	 * class that handles events for the enter button in the withdrawal menu 1
	 */
	public class BEnterWithdrawal1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String outAcc = selectMenu.userInput, message;
			
			// validate the user input
			if(!validateAccountSelectionInput(selectMenu.userInput, currCust.numAccounts())) {
				selectMenu.errorLabel.setText("Invalid selection. Please select a digit from 1 to " + 
						currCust.numAccounts());
				selectMenu.retry();
			} else {
				// get the selected account balance, ask for amount
				double accBal = currCust.getAccountBalance(Integer.parseInt(outAcc) - 1);
				message = String.format("Please enter the amount to withdraw (balance = $%,.2f): ", accBal);
				selectMenu.message.setText(message);
				
				// redefine the action of the Enter button
				removeAllActionListeners(selectMenu.keypad.bEnter);
				BEnterWithdrawal2 BE = new BEnterWithdrawal2(Integer.parseInt(outAcc), accBal);
				selectMenu.keypad.bEnter.addActionListener(BE);
				selectMenu.clearFields();
			}
		}
	}
	
	/**
	 * class that handles events for the enter button in the withdrawal menu 2
	 */
	public class BEnterWithdrawal2 implements ActionListener {
		private int outAcc;
		private double accBal;
		
		BEnterWithdrawal2(int accNb, double balance){
			this.outAcc = accNb;
			this.accBal = balance;
		}
		
		public void actionPerformed(ActionEvent e) {
			String memo = "Withdrawal, ATM";
			
			// validate the user input
			if(!validateDebitAmount(selectMenu.userInput, this.accBal)) {
				selectMenu.retry();
			} else {
				// create withdrawal transaction
				currCust.addAccountTransactionIndex(this.outAcc-1, -Double.parseDouble(selectMenu.userInput), memo);
				
				// redefine the action of the Enter button
				removeAllActionListeners(selectMenu.keypad.bEnter);
				selectMenu.clearFields();
				completionMenu();
			}
		}
	}
	
	/**
	 * class that handles events for the enter button in the internal transfer menu 1
	 */
	public class BEnterInternal1 implements ActionListener {
		int count = 0, outAcc, inAcc;
		
		public void actionPerformed(ActionEvent e) {
			String message;
			
			// validate the user input
			if(!validateAccountSelectionInput(selectMenu.userInput, currCust.numAccounts())) {
				selectMenu.errorLabel.setText("Invalid selection. Please select a digit from 1 to " + 
						currCust.numAccounts());
				selectMenu.retry();
			} else {
				// Case first prompt
				if(count == 0) {
					// get the originating account
					this.outAcc = Integer.parseInt(selectMenu.userInput);
					message = String.format("Select To Account (1-%d): ", currCust.numAccounts());
					selectMenu.updateMessage(message);
					selectMenu.clearFields();
				}
				// Case second prompt
				if(count == 1) {
					// get the destination account
					this.inAcc = Integer.parseInt(selectMenu.userInput);
					
					// get the selected account balance, ask for amount
					double accBal = currCust.getAccountBalance(this.outAcc - 1);
					message = String.format("Please enter the amount to transfer (balance = $%,.2f): ", accBal);
					selectMenu.message.setText(message);
					selectMenu.clearFields();
					
					// redefine the action of the Enter button
					removeAllActionListeners(selectMenu.keypad.bEnter);
					BEnterInternal2 BE = new BEnterInternal2(this.outAcc, this.inAcc, accBal);
					selectMenu.keypad.bEnter.addActionListener(BE);
					selectMenu.clearFields();
				}
				count++;
			}
		}
	}
	
	/**
	 * class that handles events for the enter button in the internal transfer menu 2
	 */
	public class BEnterInternal2 implements ActionListener {
		private int outAcc, inAcc;
		private double accBal;
		
		BEnterInternal2(int outAcc, int inAcc, double balance){
			this.outAcc = outAcc;
			this.inAcc = inAcc;
			this.accBal = balance;
		}
		
		public void actionPerformed(ActionEvent e) {
			String memo = String.format("Transfer to account %s ", currCust.getAccountUid(inAcc - 1));;
			
			// validate the user input
			if(!validateDebitAmount(selectMenu.userInput, this.accBal)) {
				selectMenu.retry();
			} else {
				// create transfer transaction for both accounts
				double amount = Double.parseDouble(selectMenu.userInput);
				currCust.addAccountTransactionIndex(this.outAcc-1, -amount, memo);
				currCust.addAccountTransactionIndex(this.inAcc-1, amount, memo);
				
				// redefine the action of the Enter button
				removeAllActionListeners(selectMenu.keypad.bEnter);
				selectMenu.clearFields();
				completionMenu();
			}
		}
	}
	
	/**
	 * class that handles events for the enter button in the external transfer menu 1
	 */
	public class BEnterExternal1 implements ActionListener {	
		public void actionPerformed(ActionEvent e) {
			String message;
			int outAcc;
			
			// validate the user input
			if(!validateAccountSelectionInput(selectMenu.userInput, currCust.numAccounts())) {
				selectMenu.errorLabel.setText("Invalid selection. Please select a digit from 1 to " + 
						currCust.numAccounts());
				selectMenu.retry();
			} else {
				// get the originating account
				outAcc = Integer.parseInt(selectMenu.userInput);
				message = "Please enter the account uid (8 digit number) to transfer to: ";
				selectMenu.updateMessage(message);
				selectMenu.clearFields();
				
				// redefine the action of the Enter button
				removeAllActionListeners(selectMenu.keypad.bEnter);
				BEnterExternal2 BE = new BEnterExternal2(outAcc);
				selectMenu.keypad.bEnter.addActionListener(BE);
				selectMenu.clearFields();
			}
			
			
		}
	}
	
	/**
	 * class that handles events for the enter button in the internal transfer menu 2
	 */
	public class BEnterExternal2 implements ActionListener {
		private int outAcc;
		private String inAcc;
		private Account inAccount;
		
		BEnterExternal2(int outAcc){
			this.outAcc = outAcc;
		}
		
		public void actionPerformed(ActionEvent e) {
			String message;
			// get the destination account
			this.inAcc = selectMenu.userInput;
			inAccount = bank.getBeneficiaryAccount(inAcc);
			
			// get the destination account by uid
			if(inAccount == null) {
				String error = String.format("Account number %s does not exist. Please enter"
						+ " a valid 8 digit account number.", this.inAcc);
				selectMenu.errorLabel.setText(error);
				selectMenu.retry();
			} else {
				// get the selected account balance, ask for amount
				double accBal = currCust.getAccountBalance(this.outAcc - 1);
				message = String.format("Please enter the amount to transfer (balance = $%,.2f): ", accBal);
				selectMenu.message.setText(message);
				
				// redefine the action of the Enter button
				removeAllActionListeners(selectMenu.keypad.bEnter);
				BEnterExternal3 BE = new BEnterExternal3(this.outAcc, this.inAcc, this.inAccount, accBal);
				selectMenu.keypad.bEnter.addActionListener(BE);
				selectMenu.clearFields();
			}
		}
	}
	
	/**
	 * class that handles events for the enter button in the internal transfer menu 2
	 */
	public class BEnterExternal3 implements ActionListener {
		private int outAcc;
		private String inAcc;
		private Account inAccount;
		private double accBal;
		
		BEnterExternal3(int outAcc, String inAcc, Account inAccount, double accBal){
			this.outAcc = outAcc;
			this.inAcc = inAcc;
			this.inAccount = inAccount;
			this.accBal = accBal;
		}
		
		public void actionPerformed(ActionEvent e) {
			String memo = String.format("Transfer to account %s ", this.inAcc);
			
			// validate the user input
			if(!validateDebitAmount(selectMenu.userInput, this.accBal)) {
				selectMenu.retry();
			} else {
				// create transfer transaction for both accounts
				double amount = Double.parseDouble(selectMenu.userInput);
				currCust.addAccountTransactionIndex(this.outAcc-1, -amount, memo);
				currCust.addAccountTransactionUid(this.inAccount, amount, memo);
				
				// redefine the action of the Enter button
				removeAllActionListeners(selectMenu.keypad.bEnter);
				selectMenu.clearFields();
				completionMenu();
			}
		}
	}
	
	/**
	 * class that handles events for the back button event
	 */
	public class BackCheck implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
	    	// back to the main menu  
			mainMenu();
		}
	}
	
	/**
	 * class that handles events for the return card button event
	 */
	public class RetCardCheck implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// back to the login menu
			loginMenu();
			loginMenu.clearFields();
			currCust = null;
		}
	}
	
	// Action event handler for copy/paste menu functionalities, open file and save file (button 1-2)
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(copy)) {
			clipboardCopy();
		}
		if(e.getSource().equals(paste)) {
			clipboardPaste();
		}
	}
	
	private void dispatchUserInput(String selection) {
		switch (selection) {
		
		case "Balances": {
			showAccountsBalance();
			return;
		}
		case "Account activity": {
			transactionsHistoryMenu();
			return;
		}
		case "Deposit": {
			depositMenu();
			return;
		}
		case "Withdrawal": {
			withdrawalMenu();
			return;
		}
		case "Transfer between your accounts": {
			internalTransferMenu();
			return;
		}
		case "Transfer to an external account": {
			externalTransferMenu();
			return;
		}
		
		default:
		}
	}
	
	/**	Checks if the customer login is correct
	 * @return		the logged-in holder object
	 */
	private AccountHolder checkLogin() {
		String pin = String.copyValueOf(loginMenu.padField.getPassword());
		AccountHolder currCust;

		// check if the given pin exists
		currCust = bank.holderLoginPinOnly(pin);
		if(currCust == null) {
			loginMenu.errorLabel.setText("Incorrect uid/pin. Please try again.");
		} else {
			loginMenu.errorLabel.setText("");
		}
		
		return currCust;
	}
	
	/** Checks that the account number input is valid (between 1 and max number of options)
	 * @param input			the input
	 * @param nbOptions		the menu's number of options
	 * @return				whether the input is valid
	 */
	private boolean validateAccountSelectionInput(String input, int nbOptions) {
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if(Integer.parseInt(input) < 1 || Integer.parseInt(input) > nbOptions) {
			return false;
		}
		
		return true;
	}
	
	/** Checks that the credit amount is valid (positive number)
	 * @param input		the input
	 * @param balance	the balance
	 * @return			whether the amount is valid
	 */
	private boolean validateCreditAmount(String input, double balance) {
		double amount;
		try {
			amount = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			selectMenu.errorLabel.setText("Invalid input. Amount must be greater than 0");
			return false;
		}
		if(amount < 0) {
			selectMenu.errorLabel.setText("Invalid input. Amount must be greater than 0");
			return false;
		}
		
		return true;
	}
	
	/**	Checks that the debit amount is valid (a positive number that is less than the account balance)
	 * @param input		the input
	 * @param balance	the account balance
	 * @return			whether the amount is valid
	 */
	private boolean validateDebitAmount(String input, double balance) {
		double amount;
		try {
			amount = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			selectMenu.errorLabel.setText("Invalid input. Amount must be greater than 0");
			return false;
		}
		if(amount < 0) {
			selectMenu.errorLabel.setText("Invalid input. Amount must be greater than 0");
			return false;
		} else if (amount > balance) {
			String error = String.format("Amount must be less than balance $%,.2f\n", balance);
			selectMenu.errorLabel.setText(error);
			return false;
		}
		
		return true;
	}
	
	/** convert a printf string to an HTML format
	 * @param printf	the string to convert
	 * @return			the html formatted string
	 */
	private String convertToHTML(String printf) {
		return "<html>" + printf.replaceAll("<","&lt;").replaceAll(">", "&gt;")
				.replaceAll("\n", "<br/>") + "</html>";
	}
	
	private void clipboardCopy() {
		StringSelection ss = new StringSelection(textArea1.getSelectedText());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}
	
	private void clipboardPaste() {
		Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		if (systemClipboard.isDataFlavorAvailable(DataFlavor.stringFlavor))
        {
            try {
				String text = (String)systemClipboard.getData(DataFlavor.stringFlavor);
				textArea1.insert(text, textArea1.getCaretPosition());
				textArea1.validate();
			} catch (Exception e2) {
				//handle exception
			}
        }
	}
	
	// Event manager for mouse actions
	@Override
	public void mouseClicked(MouseEvent e) {
		// Mouse pressed and released
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		mouseReleased(e);
		
	}
	/* isPopupTrigger should be checked in both mousePressedand mouseReleased 
	 * for proper cross-platform functionality.*/
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.isPopupTrigger()) {
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Invoked when a mouse enters a component
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Invoked when a mouse exits a component
		
	}
	
	public static void main(String[] args) {
		new GuiAtm();
		
	}
}
