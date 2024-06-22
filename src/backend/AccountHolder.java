package backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class AccountHolder {

	/* Account holder first and last name, user ID and pin */
	/* Pin should be encrypted as byte[] later on */
	/* List of account for this user */
	private String firstName, lastName, uid;
	private byte[] pin;
	private ArrayList<Account> accounts;
	

	/**	Create an account holder object and an empty list of accounts
	 * @param firstName		the account holder's first name
	 * @param lastName		the account holder's last name
	 * @param pin			the account holder's pin
	 * @param bank			the bank object
	 */
	AccountHolder(String firstName, String lastName, String pin, Bank bank){
		this.firstName = firstName;
		this.lastName = lastName;
		// get a new unique uid for the account holder
		uid = bank.getNewHolderUid();
		accounts = new ArrayList<Account>();
		
		// store the pin's MD5 hash and not the original value for security reasons
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pin = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error: NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.printf("New account holder %s %s with UID: %s was created.\n", firstName, lastName, uid);
	}
	
	/* Get and Set methods */
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getUid() {
		return uid;
	}
	public byte[] getPin() {
		return pin;
	}
	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	
	/** Get the number of accounts of the account holder
	 * @return	the number of accounts
	 */
	public int numAccounts() {
		return accounts.size();
	}
	
	/** Add an account to account holder
	 * @param account	the account to add
	 */
	public void addAccount(Account account) {
		accounts.add(account);
	}
	
	/**
	 * Check whether a given pin matches account holder pin
	 * @param pin	the pin to check
	 * @return		whether the pin exist or not 
	 */
	public boolean checkPin(String pin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pin);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error: NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(-1);
		}
		return false;
	}
	
	/**
	 * Print the summary of all accounts for the holder
	 */
	public void printAccountsSummary() {	
		for(int i = 0; i < accounts.size(); i++) {
			System.out.printf("%d - %s = $%,.2f \n", 
					i+1, accounts.get(i).getType(), accounts.get(i).getBalance());
		}
	}
	
	/**
	 * Get the summary of all accounts for the holder
	 */
	public String getAccountsSummary() {	
		String summary = "";
		for(int i = 0; i < accounts.size(); i++) {
			summary += String.format("%d) %s = $%,.2f \n", 
					i+1, accounts.get(i).getType(), accounts.get(i).getBalance());
		}
		return summary;
	}
	
	/**	Print the transactions of a specific account
	 * @param accIndex	the index of the account
	 */
	public void printAccountTrxHistory(int accIndex) {
		this.accounts.get(accIndex).printTrxHistory();
	}
	
	/**	Get the transactions of a specific account
	 * @param accIndex	the index of the account
	 */
	public String getAccountTrxHistory(int accIndex) {
		return this.accounts.get(accIndex).getTrxHistory();
	}
	
	/**	Get the balance of a specific account
	 * @param accIndex	the index of the account
	 * @return			the account balance
	 */
	public double getAccountBalance(int accIndex) {
		return this.accounts.get(accIndex).getBalance();
	}
	
	/** Add a transaction to a specific account
	 * @param accIndex		the index of the account
	 * @param amount		the amount of the transaction
	 * @param memo			the transaction memo
	 */
	public void addAccountTransactionIndex(int accIndex, double amount, String memo) {
		this.accounts.get(accIndex).addTransaction(amount, memo);
	}
	
	/** Add a transaction to a specific account
	 * @param accIndex		the index of the account
	 * @param amount		the amount of the transaction
	 * @param memo			the transaction memo
	 */
	public void addAccountTransactionUid(Account accUid, double amount, String memo) {
		accUid.addTransaction(amount, memo);
	}
	
	
	/**	Get the uid of a specific account
	 * @param accIndex	the index of the account
	 * @return			the account uid
	 */
	public String getAccountUid(int accIndex) {
		return this.accounts.get(accIndex).getUid();
	}

}
