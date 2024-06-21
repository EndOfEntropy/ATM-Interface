package backend;

import java.util.Date;

public class Transaction {

	/* Transaction amount, date, memo */
	/* The account in which the transaction occurred */
	private double amount;
	private Date trxDate;
	private String memo;
	private Account inAccount;
	
	
	/** Creates a new Transaction object
	 * @param amount		the amount of the transaction
	 * @param inAccount		the account in which the transaction occurred
	 */
	Transaction(double amount, Account inAccount){
		this.amount = amount;
		this.trxDate = new Date();
		this.memo = "";
		this.inAccount = inAccount;
		inAccount.transfer(amount);
	}
	/** Create a new Transaction object by calling the 2 argument constructor 
	 * @param amount		the amount of the transaction
	 * @param memo			the memo of the transaction
	 * @param inAccount		the account in which the transaction occurred
	 */
	Transaction(double amount, String memo, Account inAccount){
		// Call 2 arguments constructor
		this(amount, inAccount);
		this.memo = memo;
	}
	
	/* get methods */
	public double getAmount() {
		return amount;
	}
	public Date getTrxDate() {
		return trxDate;
	}
	public String getMemo() {
		return memo;
	}
	public Account getInAccount() {
		return inAccount;
	}
	
	/**
	 * Print the transaction
	 */
	public void printSummaryLine() {
		if(amount > 0) {
			System.out.printf("Date: %s - Credit - Amount: $%,.2f - Memo: %s \n", 
					trxDate.toString(), amount, memo);
		} else if (amount < 0) {
			System.out.printf("Date: %s - Debit - Amount: $(%,.2f) - Memo: %s \n", 
					trxDate.toString(), -amount, memo);
		} else if (amount == 0.0 || amount == -0.0){
			System.out.printf("Date: %s - N/A - Amount: $%,.2f - Memo: %s \n", 
					trxDate.toString(), amount, memo);
		}
	}
	
	/** get the transaction summary in a string format
	 * @return		the transaction summary
	 */
	public String getSummaryLine() {
		String summary = "";
		if(amount > 0) {
			summary += String.format("Date: %s - Credit - Amount: $%,.2f - Memo: %s \n", 
					trxDate.toString(), amount, memo);
			return summary;
		} else if (amount < 0) {
			summary += String.format("Date: %s - Debit - Amount: $(%,.2f) - Memo: %s \n", 
					trxDate.toString(), amount, memo);
			return summary;
		} else if (amount == 0.0 || amount == -0.0){
			summary += String.format("Date: %s - N/A - Amount: $%,.2f - Memo: %s \n", 
					trxDate.toString(), amount, memo);
			return summary;
		}
		return summary;
	}
}
