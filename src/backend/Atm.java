/*	A console-based ATM application

 	Features:
    The customer can deposit funds
    The customer can withdraw funds
    The customer can check his account balance
    The customer can transfer funds between his accounts
    The customer can transfer funds to another customer using the account uid
 */
package backend;

import java.util.Scanner;


public class Atm {
	
	/**
	 * A new Bank object
	 * A Scanner object
	 */
	private Bank bank;
	private Scanner userInput;
	
	/**
	 * Create a new Atm object and the Atm interface
	 */
	Atm(){
		initiate();
		AccountHolder currCust = loginMenu();
		mainMenu(currCust);
		System.exit(0);
	}
	
	/**
	 * Creates a new Bank object and add an account holder and new accounts
	 */
	private void initiate() {
		// create the bank object
		bank = new Bank("New Bank");
		
		// add customers to the bank. A chequing account is created by default
		AccountHolder holder = bank.addAccountHolder("Joe", "Smith", "1234");
		bank.addAccountHolder("Jack", "Son", "0000");
		
		// add a saving account for our holder
		Account newAccount = new Account("Savings", 0.0f, holder, bank);
		holder.addAccount(newAccount);
		bank.addAccount(newAccount);
	}
	
	/**	Prompts a menu for a customer to login
	 * @return		the logged-in holder object
	 */
	public AccountHolder loginMenu() {
		userInput = new Scanner(System.in);
		String uid, pin;
		AccountHolder currCust;
		
		System.out.printf("Welcome to %s.\n", bank.getName());
		// continue looping until login successful
		do {
			System.out.println("Please enter UID: ");
			uid = userInput.nextLine();
			System.out.println("Please enter PIN: ");
			pin = userInput.nextLine();
			// check if the given uid and pin exist
			currCust = bank.holderLogin(uid, pin);
			if(currCust == null) {
				System.out.println("Incorrect uid/pin. Please try again.");
			}
			
		} while (currCust == null);
		
		return currCust;
	}
	
	/** Prompts the logged-in customer to select operations
	 * @param currCust	the logged-in holder object
	 */
	public void mainMenu(AccountHolder currCust) {
		// execute commands from customer deposit/withdraw/check balance until 'exit'
		String selection;
		do {
			System.out.println();
			System.out.println("Your account summary: ");
			currCust.printAccountsSummary();
			System.out.println();
			System.out.printf("Hello %s, please select an operation:\n", currCust.getFirstName());
			System.out.println("Type '1' to show your account transaction history");
			System.out.println("Type '2' to make a deposit");
			System.out.println("Type '3' to make a withdrawal");
			System.out.println("Type '4' to transfer funds between your accounts");
			System.out.println("Type '5' to transfer funds to another account");
			System.out.println("Type '6' to quit");
			selection = userInput.nextLine();
			
		} while (!validateAccountSelectionInput(selection.toString(), 6));
		
		operationSelection(selection, currCust);
		
		if(!selection.equals("6")) {
			mainMenu(currCust);
		}
		
		userInput.close();
	}
	
	/** Dispatch the selected operation to appropriate method
	 * @param selection		the selection
	 * @param currCust		the logged-in holder object
	 */
	private void operationSelection(String selection, AccountHolder currCust) {
		switch (selection) {
		
		case "1": {
			showTransactionsHistory(currCust);
			return;
		}
		case "2": {
			depositFunds(currCust);
			return;
		}
		case "3": {
			withdrawFunds(currCust);
			return;
		}
		case "4": {
			internalTransferOfFunds(currCust);
			return;
		}
		case "5": {
			externalTransferOfFunds(currCust);
			return;
		}
		case "6": {
			System.out.println("Session terminated");
			return;
		}
		}
	}
	
	/** Prints the transaction history of a specific account
	 * @param currCust	the logged-in holder object
	 */
	private void showTransactionsHistory(AccountHolder currCust) {
		String selection;

		do {
			System.out.printf("Please select an account from (1-%d): \n", currCust.numAccounts());
			currCust.printAccountsSummary();
			selection = userInput.nextLine();
		} while (!validateAccountSelectionInput(selection.toString(), currCust.numAccounts()));
		
		currCust.printAccountTrxHistory(Integer.parseInt(selection) - 1);
	}
	
 	/** Process a transfer of funds between the holder's accounts
 	 * @param currCust	the logged-in holder object
 	 */
 	private void internalTransferOfFunds(AccountHolder currCust) {
		String inAcc, outAcc, amount, memo;
		double accBal;

		do {
			System.out.printf("Please select account to transfer from (1-%d): \n", currCust.numAccounts());
			currCust.printAccountsSummary();
			outAcc = userInput.nextLine();
			System.out.printf("Please select account to transfer to (1-%d): \n", currCust.numAccounts());
			currCust.printAccountsSummary();
			inAcc = userInput.nextLine();
		} while (!validateAccountSelectionInput(inAcc.toString(), currCust.numAccounts())
					|| !validateAccountSelectionInput(outAcc.toString(), currCust.numAccounts()));
		
		accBal = currCust.getAccountBalance(Integer.parseInt(outAcc) - 1);
		
		do {
			System.out.printf("Please enter the amount of the transfer (max $%,.2f): ", accBal);
			amount = userInput.nextLine();
			memo = String.format("Transfer to account %s ", currCust.getAccountUid(Integer.parseInt(inAcc)-1));
		} while (!validateDebitAmount(amount, accBal));
		
		currCust.addAccountTransactionIndex(Integer.parseInt(outAcc) - 1, -Double.parseDouble(amount), memo);
		currCust.addAccountTransactionIndex(Integer.parseInt(inAcc) - 1, Double.parseDouble(amount), memo);
 	}
 	
 	/**	Process a transfer of funds to another holder's account
 	 * @param currCust		the logged-in holder object
 	 */
 	private void externalTransferOfFunds(AccountHolder currCust) {
		String inAcc, outAcc, amount, memo;
		double accBal;
		AccountHolder benefHolder;

		do {
			System.out.printf("Please select account to transfer from (1-%d): \n", currCust.numAccounts());
			currCust.printAccountsSummary();
			outAcc = userInput.nextLine();
			System.out.printf("Please enter the account uid (8 digit number) to transfer to: \n");
			currCust.printAccountsSummary();
			inAcc = userInput.nextLine();
			benefHolder = bank.getBeneficiaryAccountHolder(inAcc);
			if(benefHolder.equals(null)) {
				System.out.printf("Account number %s does not exist. Please enter a valid 8 digit "
						+ "account number. \n", inAcc);
			}
			
		} while (!validateAccountUidNumber(inAcc.toString())
					|| !validateAccountSelectionInput(outAcc.toString(), currCust.numAccounts()));
		
		accBal = currCust.getAccountBalance(Integer.parseInt(outAcc) - 1);
		
		do {
			System.out.printf("Please enter the amount of the transfer (max $%,.2f): ", accBal);
			amount = userInput.nextLine();
			memo = String.format("Transfer to account %s ", inAcc);
		} while (!validateDebitAmount(amount, accBal));
		
		currCust.addAccountTransactionIndex(Integer.parseInt(outAcc) - 1, -Double.parseDouble(amount), memo);
		benefHolder.addAccountTransactionIndex(Integer.parseInt(inAcc) - 1, Double.parseDouble(amount), memo);
 	}
	
 	/** Process the deposit of funds to the customer's account
 	 * @param currCust	the logged-in customer object
 	 */
 	private void depositFunds(AccountHolder currCust) {
		String inAcc, amount, memo;
		double accBal;

		do {
			System.out.printf("Please select the account to deposit in (1-%d):\n", currCust.numAccounts());
			currCust.printAccountsSummary();
			inAcc = userInput.nextLine();
		} while (!validateAccountSelectionInput(inAcc.toString(), currCust.numAccounts()));
		
		accBal = currCust.getAccountBalance(Integer.parseInt(inAcc) - 1);
		
		do {
			System.out.printf("Please enter the amount to deposit (balance = $%,.2f): ", accBal);
			amount = userInput.nextLine();
			System.out.print("Please enter the memo of the deposit: ");
			memo = userInput.nextLine();
		} while (!validateCreditAmount(amount, accBal));
		
		currCust.addAccountTransactionIndex(Integer.parseInt(inAcc) - 1, Double.parseDouble(amount), memo);
	}
 	
 	/** Process the withdrawal of funds from the customer's account
 	 * @param currCust	the logged-in customer object
 	 */
 	private void withdrawFunds(AccountHolder currCust) {
		String outAcc, amount, memo;
		double accBal;

		do {
			System.out.printf("Please select the account to withdraw from (1-%d):\n", currCust.numAccounts());
			currCust.printAccountsSummary();
			outAcc = userInput.nextLine();
		} while (!validateAccountSelectionInput(outAcc.toString(), currCust.numAccounts()));
		
		accBal = currCust.getAccountBalance(Integer.parseInt(outAcc) - 1);
		
		do {
			System.out.printf("Please enter the amount to withdraw (max $%,.2f): ", accBal);
			amount = userInput.nextLine();
			System.out.print("Please enter the memo of the withdrawal: ");
			memo = userInput.nextLine();
		} while (!validateDebitAmount(amount, accBal));
		
		currCust.addAccountTransactionIndex(Integer.parseInt(outAcc) - 1, -Double.parseDouble(amount), memo);
	}
 	
	/** Checks that the account number input is valid (between 1 and max number of options)
	 * @param input			the input
	 * @param nbOptions		the menu's number of options
	 * @return				whether the input is valid
	 */
	private boolean validateAccountSelectionInput(String input, int nbOptions) {
		if(input.length() > 1) {
			System.out.println("Invalid selection. Please select a digit from 1 to " + nbOptions);
			return false;
		}
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.out.println("Invalid selection. Please select a digit from 1 to " + nbOptions);
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
			System.out.println("Invalid amount. Please enter a number.");
			return false;
		}
		if(amount < 0) {
			System.out.println("Amount must be greater than 0");
			return false;
		} else if (amount > balance) {
			System.out.printf("Amount must be less than balance $%,.2f\n", balance);
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
			System.out.println("Invalid amount. Please enter a number.");
			return false;
		}
		if(amount < 0) {
			System.out.println("Amount must be greater than 0");
			return false;
		}
		
		return true;
	}
	
	/** Checks that the account uid is valid
	 * @param uid	account the uid
	 * @return		whether the account uid is valid
	 */
	private boolean validateAccountUidNumber(String uid) {
		if(uid.length() != bank.getAccountUidLength()) {
			System.out.println("Invalid account uid. Please enter a valid 8 digit account number.");
			return false;
		}
		try {
			Long.parseLong(uid);
		} catch (NumberFormatException e) {
			System.out.println("Invalid account uid. Please enter a valid 8 digit account number.");
			return false;
		}
		
		return true;
	}
	
	public static void main(String args[]) {
		new Atm();
	}
}
