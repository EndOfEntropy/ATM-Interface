package backend;

import java.util.ArrayList;
import java.util.Random;


public class Bank {
	
	/* Bank name */
	/* The list of account holders for this bank */
	/* The list of accounts for this bank */
	private String name;
	private ArrayList<AccountHolder> holders;
	private ArrayList<Account> accounts;
	private static int holderUidLen = 6, accountUidLen = 8;
	
	
	/**
	 * Creates a new Bank object and an empty list of holders and accounts
	 * @param name		the name of the bank
	 */
	public Bank(String name){
		this.name = name;
		holders = new ArrayList<AccountHolder>();
		accounts = new ArrayList<Account>();
	}
	
	/* Get and Set methods */
	public String getName() {
		return name;
	}
	public ArrayList<AccountHolder> getHolders(){
		return holders;
	}
	public ArrayList<Account> getAccounts(){
		return accounts;
	}
	
	/** add an account to account holder
	 * @param account	the account to add
	 */
	public void addAccount(Account account) {
		accounts.add(account);
	}
	
	/**
	 * Create a new account holder of the bank
	 * @param firstName		the holder's first name
	 * @param lastName		the holder's last name
	 * @param pin			the holder's pin
	 * @return				the new account holder object
	 */
	public AccountHolder addAccountHolder(String firstName, String lastName, String pin) {
		// create a new account holder object and add it to the holder list
		AccountHolder newHolder = new AccountHolder(firstName, lastName, pin, this);
		holders.add(newHolder);
		// create a new account and add it to the holder list and the bank list
		Account newAccount = new Account("Chequing", 1000.0f, newHolder, this);
		newHolder.addAccount(newAccount);
		accounts.add(newAccount);
		
		return newHolder;
	}
	
	/**	Generate a new and unique holder uid
	 * @return		the holder uid
	 */
	public String getNewHolderUid() {
		String newUid = getRandomUid(holderUidLen);
		for(AccountHolder holder : holders) {
			if(holder.getUid().equals(newUid)) {
				newUid = getNewHolderUid();
			}
		}
		
		return newUid;
	}
	
	/**	Generate a new and unique account uid
	 * @return		the account uid
	 */
	public String getNewAccountUid() {
		String newUid = getRandomUid(accountUidLen);
		for(Account account : accounts) {
			if(account.getUid().equals(newUid)) {
				newUid = getNewAccountUid();
			}
		}
		
		return newUid;
	}
	
	/** Generate a new and unique uid
	 * @param uidLength		the length of the uid
	 * @return				the new uid
	 */
	private String getRandomUid(int uidLength) {
		StringBuilder uid = new StringBuilder();
		Random rand = new Random();

		for(int i = 0; i < uidLength; i++) {
			int randDigit = rand.nextInt(0, 10);
			uid.append(randDigit);
		}
		
		return uid.toString();
	}
	
	/**	Get the holder object associated with a specific uid and pin
	 * @param uid	the uid of the account holder used to log in
	 * @param pin	the pin of the account holder used to log in
	 * @return		the account holder object or null if not found
	 */
	public AccountHolder holderLogin(String uid, String pin) {
		for(AccountHolder holder: holders) {
			if(holder.getUid().equals(uid) && holder.checkPin(pin)) {
				return holder;
			}
		}
		return null;
	}
	
	/**	Get the holder object associated with a specific uid and pin
	 * @param pin	the pin of the account holder used to log in
	 * @return		the account holder object or null if not found
	 */
	public AccountHolder holderLoginPinOnly(String pin) {
		for(AccountHolder holder: holders) {
			if(holder.checkPin(pin)) {
				return holder;
			}
		}
		return null;
	}
	
	/** Get the account object associated with a specific account uid
	 * @param uid	the uid of the account
	 * @return		the account object or null if not found
	 */
	public Account getBeneficiaryAccount(String uid) {
		for(Account acc: accounts) {
			if(uid.equals(acc.getUid())) {
				return acc;
			}
		}
		return null;
	}
	
	/** Get the holder object associated with a specific account uid
	 * @param uid	the uid of the account
	 * @return		the account holder object or null if not found
	 */
	public AccountHolder getBeneficiaryAccountHolder(String uid) {
		for(AccountHolder holder : holders) {
			for(Account acc: accounts) {
				if(uid.equals(acc.getUid())) {
					return holder;
				}
			}
		}
		return null;
	}
	
}
