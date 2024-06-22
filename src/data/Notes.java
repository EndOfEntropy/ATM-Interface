package data;


public class Notes {
	/*
	 * Reference: https://www.youtube.com/watch?v=k0BofouWX-o
	 * 
	 * Disclaimers:
	 * This project is not for production, so we will pretend that the user card is read automatically.
	 * No user input is required to identify the user access card/uid.
	 *
	 * Only one customer can log in the Atm (no GUI) application. 
	 * This means that the transactions between customers cannot be checked for completion.
	 * However, this feature works in the version with the GUI application.
	 * 
	 * Account and holder UIDs are limited to 9 digit long as they are integer variables (+-2,147,483,647)
	 *
	 * Enhancements - TODO
	 * (easy)	add comments in the methods
	 * (high)	add an admin account to manage customers
	 * 			create a method to add an account to an already existing customer
	 * (medium)	enhance the GUI menus color/size/structure etc...
	 * 
	 * Java language notes
	 * printf
	 * System.out.printf("Withdrawal amount = $%,.2f \n", amount);
	 * The , instructs Java printf to add a thousands group separator.
	 * The .3 instructs Java printf to limit output to three decimal places.
	 * %f is the specifier used to format double and floats with printf.
	 *
	 * Account class - Account constructor
	 * Add Account to the account holder and bank list class
	 * The same object (not a copy) is added to the account holder list and the bank list,
	 * as it is the same object in memory that the lists point to.
	 * An addAccount method would have to be created for the bank and the holder class
	 * 
	 * holder.addAccount(this);
	 * bank.addAccount(this);
	 */
	
	
	
	/**	Check if a card number is valid (a 16 digit number)
	 * @param cardNumber	the card number
	 * @return				whether the card number is valid
	 */
	private boolean validateCardNumber(String cardNumber) {
		if(cardNumber.length() != 16) {
			return false;
		}
		try {
			Long.parseLong(cardNumber);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
	
	/**	Check if a pin number is valid (a 4 digit number)
	 * @param pin		the pin
	 * @return			whether the pin number is valid
	 */
	private boolean validatePinCode(String pin) {
		if(pin.length() != 4) {
			return false;
		}
		try {
			Integer.parseInt(pin);
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
}
