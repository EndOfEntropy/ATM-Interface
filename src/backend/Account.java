package backend;

import java.util.ArrayList;

public class Account {
	
	/* Account type, ID and balance */
	/* The account holder for this account */
	/* List of transactions for this account */
	private String type, uid;
	private double balance;
	private AccountHolder holder;
	private ArrayList<Transaction> trx;
	

	/** Create an new Account object and an empty list of transactions
	 * @param type		the type of the account
	 * @param balance	the balance of the account
	 * @param holder	the holder object
	 * @param bank		the bank object
	 */
	public Account(String type, double balance, AccountHolder holder, Bank bank){
			this.type = type;
			this.uid = bank.getNewAccountUid();
			this.balance = balance;
			this.holder = holder;
			trx = new ArrayList<Transaction>();
			
			System.out.printf("New account %s with UID: %s was created.\n", this.type, this.uid);
	}
	
	/* Get and Set methods */
	public String getType() {
		return type;
	}
	public String getUid() {
		return uid;
	}
	public double getBalance() {
		return balance;
	}
	public AccountHolder getHolder() {
		return holder;
	}
	public ArrayList<Transaction> getTrx(){
		return trx;
	}
	public void transfer(double amount) {
		balance += amount;
	}
	
	public void deposit(double amount) {
		balance += amount;
	}
	public void withdraw(double amount) {
		balance -= amount;
	}
	
	/**
	 * Print the transaction history
	 */
	public void printTrxHistory() {
		System.out.printf("Transaction history for account %s \n", this.uid);
		
		for(int i = trx.size() - 1; i >= 0; i--) {
			this.trx.get(i).printSummaryLine();
		}
		System.out.println();
	}
	
	/** Get the transaction history in a string format
	 * @return		the transaction history
	 */
	public String getTrxHistory() {
		String summary = String.format("Transaction history for account %s \n", this.uid);
		
		for(int i = trx.size() - 1; i >= 0; i--) {
			summary += this.trx.get(i).getSummaryLine();
		}
		
		return summary;
	}
	
	/** Add a new transaction to the account transaction history
	 * @param amount	the amount transacted
	 * @param memo		the transaction memo
	 */
	public void addTransaction(double amount, String memo) {
		this.trx.add(new Transaction(amount, memo, this));
	}
}
